package com.argusoft.imtecho.common.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * An util class to create thread pool
 *
 * @author harshit
 * @since 31/08/2020 4:30
 */
public class TechoCompletableFuture {

    private TechoCompletableFuture() {

    }

    public static ThreadPoolTaskExecutor mobileGetDetailThreadPool;
    public static ThreadPoolTaskExecutor mobileGetDetailAshaThreadPool;

    static {
        mobileGetDetailThreadPool = new ThreadPoolTaskExecutor();
        mobileGetDetailThreadPool.setCorePoolSize(100);
        mobileGetDetailThreadPool.setMaxPoolSize(150);
        mobileGetDetailThreadPool.setKeepAliveSeconds(1000 * 60);
        mobileGetDetailThreadPool.initialize();

        mobileGetDetailAshaThreadPool = new ThreadPoolTaskExecutor();
        mobileGetDetailAshaThreadPool.setCorePoolSize(25);
        mobileGetDetailAshaThreadPool.setMaxPoolSize(50);
        mobileGetDetailAshaThreadPool.setKeepAliveSeconds(1000 * 60);
        mobileGetDetailAshaThreadPool.initialize();
    }
}
