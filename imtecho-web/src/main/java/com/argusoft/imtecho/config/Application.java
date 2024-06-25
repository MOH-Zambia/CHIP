package com.argusoft.imtecho.config;

import com.argusoft.imtecho.common.util.ConstantUtil;
import com.argusoft.imtecho.config.jackson.CustomSQLDateTimeSerializer;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.PortInUseException;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
@EnableTransactionManagement
@ComponentScan(basePackages = "com.argusoft.imtecho")
//@EnableJpaRepositories(basePackages = "com.argusoft.hkg.dao")
//@EntityScan(basePackages = "com.argusoft.imtecho")
@PropertySource({"classpath:jdbc.properties",
        "classpath:root-config.properties"
})
@EnableScheduling
@EnableAsync
public class Application extends SpringBootServletInitializer {
    @Autowired
    private DataSource dataSource;


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        try {
            SpringApplication.run(Application.class, args);
        } catch (PortInUseException e) {
            System.exit(1);
        }
    }

    @Bean(name = "flywayMigrationInitializer", initMethod = "migrate")
    public FlywayMigrationInitializer flywayMigrationInitializer(DataSource dataSource) {
        return new FlywayMigrationInitializer();
    }

    @Bean
    @Primary
    @DependsOn({"flywayMigrationInitializer"})
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(new String[] { "com.argusoft.imtecho" });
        sessionFactory.setAnnotatedPackages(new String[] { "com.argusoft.imtecho" });
        return sessionFactory;
    }

    @Bean(name = "transactionManager")
    @Order(2)
    @Autowired
    @Primary
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) throws IOException {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.modules(new JodaModule());
            builder.serializers(new CustomSQLDateTimeSerializer());
        };
    }

    @Bean(name = "timerEventTaskExecutor")
    @Order(3)
    public TaskExecutor timerEventTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(0);
        executor.initialize();
        return executor;
    }

    @Bean(name = "smsTaskExecutor")
    @Order(4)
    public ThreadPoolTaskExecutor smsTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(1000000);
        executor.initialize();
        return executor;
    }

    @Bean(name = "reportOfflineTaskExecutor")
    @Order(4)
    public ThreadPoolTaskExecutor reportOfflineTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(25);
        executor.setQueueCapacity(10);
        executor.initialize();
        return executor;
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        return taskScheduler;
    }

    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addConnectorCustomizers(connector -> {
            AbstractHttp11Protocol<?> abstractHttp11Protocol = ((AbstractHttp11Protocol<?>) connector.getProtocolHandler());
            abstractHttp11Protocol.setMaxConnections(20000);
            abstractHttp11Protocol.setKeepAliveTimeout(5000);
            abstractHttp11Protocol.setMaxThreads(800);
            abstractHttp11Protocol.setMaxKeepAliveRequests(500);
            abstractHttp11Protocol.setCompression("on");
            abstractHttp11Protocol.setCompressionMinSize(256);
            abstractHttp11Protocol.setCompressibleMimeType("text/html,text/xml,text/plain,application/json,application/xml");
        });

        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    private Connector redirectConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");

        AbstractHttp11Protocol<?> abstractHttp11Protocol = ((AbstractHttp11Protocol<?>) connector.getProtocolHandler());
        abstractHttp11Protocol.setMaxConnections(20000);
        abstractHttp11Protocol.setKeepAliveTimeout(500);
        abstractHttp11Protocol.setMaxThreads(1200);
        abstractHttp11Protocol.setMaxKeepAliveRequests(500);

        connector.setScheme("http");
        connector.setProperty("compression", "on");
        connector.setProperty("compressableMimeType", "text/html,text/xml,text/plain,application/json,application/xml");
        connector.setPort(ConstantUtil.SERVER_REDIRECT_PORT);
        connector.setSecure(ConstantUtil.SERVER_IS_SECURE);
        connector.setRedirectPort(ConstantUtil.SERVER_PORT);
        return connector;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream inputStream;

        if (ConstantUtil.IMPLEMENTATION_TYPE.equals(ConstantUtil.TELANGANA_IMPLEMENTATION)
            || ConstantUtil.IMPLEMENTATION_TYPE.equals(ConstantUtil.MEDPLAT_IMPLEMENTATION)) {
            inputStream = new ClassPathResource("telangana-firebase-service-account.json").getInputStream();
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(inputStream);
            FirebaseOptions firebaseOptions = FirebaseOptions
                    .builder()
                    .setCredentials(googleCredentials)
                    .build();
            FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
            return FirebaseMessaging.getInstance(app);
        }
        return null;
    }
}