package com.argusoft.sewa.android.app.activity;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.argusoft.sewa.android.app.BuildConfig;
import com.argusoft.sewa.android.app.R;
import com.argusoft.sewa.android.app.component.MyAlertDialog;
import com.argusoft.sewa.android.app.component.MyProcessDialog;
import com.argusoft.sewa.android.app.constants.IdConstants;
import com.argusoft.sewa.android.app.constants.LabelConstants;
import com.argusoft.sewa.android.app.core.impl.LocationMasterServiceImpl;
import com.argusoft.sewa.android.app.core.impl.MoveToProductionServiceImpl;
import com.argusoft.sewa.android.app.core.impl.SewaFhsServiceImpl;
import com.argusoft.sewa.android.app.core.impl.SewaServiceImpl;
import com.argusoft.sewa.android.app.core.impl.SewaServiceRestClientImpl;
import com.argusoft.sewa.android.app.core.impl.TechoServiceImpl;
import com.argusoft.sewa.android.app.datastructure.SharedStructureData;
import com.argusoft.sewa.android.app.db.DBConnection;
import com.argusoft.sewa.android.app.model.FormAccessibilityBean;
import com.argusoft.sewa.android.app.model.LoginBean;
import com.argusoft.sewa.android.app.model.VersionBean;
import com.argusoft.sewa.android.app.restclient.RestHttpException;
import com.argusoft.sewa.android.app.restclient.impl.ApiManager;
import com.argusoft.sewa.android.app.service.GPSTracker;
import com.argusoft.sewa.android.app.transformer.SewaTransformer;
import com.argusoft.sewa.android.app.util.BackgroundDownloadUtils;
import com.argusoft.sewa.android.app.util.DynamicUtils;
import com.argusoft.sewa.android.app.util.GlobalTypes;
import com.argusoft.sewa.android.app.util.Log;
import com.argusoft.sewa.android.app.util.RootUtil;
import com.argusoft.sewa.android.app.util.SewaConstants;
import com.argusoft.sewa.android.app.util.SewaUtil;
import com.argusoft.sewa.android.app.util.UtilBean;
import com.argusoft.sewa.android.app.util.WSConstants;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author kelvin
 */
@EActivity
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Bean
    SewaServiceImpl sewaService;
    @Bean
    MoveToProductionServiceImpl moveToProductionService;
    @Bean
    TechoServiceImpl techoService;
    @Bean
    LocationMasterServiceImpl locationMasterService;
    @Bean
    SewaFhsServiceImpl sewaFhsService;
    @Bean
    ApiManager apiManager;
    @Bean
    SewaServiceRestClientImpl restClient;

    @OrmLiteDao(helper = DBConnection.class)
    Dao<LoginBean, Integer> loginDao;
    @OrmLiteDao(helper = DBConnection.class)
    Dao<VersionBean, Integer> versionDao;

    private final Context context = this;
    private EditText etUsername;
    private EditText etPassword;
    private CheckBox cbRememberMe;
    private Button loginButton;
    private MyProcessDialog processDialog;
    private boolean isResume = true;
    private String loginUsername;
    private String loginPassword;
    private MyAlertDialog myDialog;
    private MyAlertDialog dialogForExit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //To change body of generated methods, choose Tools | Templates.
        Intent serviceIntent = new Intent(this, GPSTracker.class);
        startService(serviceIntent);
        setUpViewContent();
        etUsername = findViewById(R.id.txtUsername);
        etPassword = findViewById(R.id.txtPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        loginButton = findViewById(R.id.loginButton);
        injectLoginBean();
    }

    private void setUpViewContent() {
        if (GlobalTypes.FLAVOUR_UTTARAKHAND.equals(BuildConfig.FLAVOR)) {
            setContentView(R.layout.activity_login_uttarakhand);
        } else if (GlobalTypes.FLAVOUR_DNHDD.equals(BuildConfig.FLAVOR)) {
            setContentView(R.layout.activity_login_dnhdd);
        } else if (GlobalTypes.FLAVOUR_TELANGANA.equals(BuildConfig.FLAVOR)) {
            setContentView(R.layout.activity_login_telangana);
        } else if (GlobalTypes.FLAVOUR_CHIP.equals(BuildConfig.FLAVOR)) {
            setContentView(R.layout.activity_login_chip);
        }  else if (GlobalTypes.FLAVOUR_IMOMCARE.equals(BuildConfig.FLAVOR)) {
            setContentView(R.layout.activity_login_imomcare);
        } else {
            setContentView(R.layout.activity_login_medplat);
        }
    }

    @UiThread
    public void showProcessDialog(String msg) {
        if (processDialog != null && processDialog.isShowing()) {
            processDialog.dismiss();
        }
        processDialog = new MyProcessDialog(this, UtilBean.getMyLabel(msg));
        processDialog.show();
    }

    @Background
    public void injectLoginBean() {
        SharedStructureData.setDbContext(context);
        if (loginDao != null) {
            Log.i(getClass().getSimpleName(), "TeCHO - Injecting Login Bean Dao");
            try {
                List<LoginBean> queryForAll = loginDao.queryForAll();
                if (queryForAll != null && !queryForAll.isEmpty()) {
                    SewaTransformer.loginBean = queryForAll.get(0);
                    if (!SewaTransformer.loginBean.isTrainingUser()) {
                        WSConstants.setLiveContextUrl();
                    } else {
                        WSConstants.setTrainingContextUrl();
                    }
                    apiManager.createApiService();
                }
                Log.i(getClass().getSimpleName(), "TeCHO - Login Bean Dao Is Injected");
            } catch (SQLException ex) {
                Log.e(getClass().getSimpleName(), "TeCHO - Login Bean Dao Is not Injected, Exception Occurs", ex);
            }
        } else {
            Log.w(getClass().getSimpleName(), "TeCHO - Login Bean Dao Is Not Injected, It Is Null");
        }

        if (!SharedStructureData.dbDowngraded) {
            showProcessDialog(GlobalTypes.MSG_STARTUP_APPLICATION);
            setAdditionalData();
        }
    }

    private void setAdditionalData() {
        SewaConstants.setDirectoryPath(context);
        if (sewaService != null) {
            Log.i(getClass().getSimpleName(), "TeCHO - sewaService Is Injected");
            SharedStructureData.sewaService = sewaService;
            sewaService.initLabelBeanMap();
        } else {
            Log.w(getClass().getSimpleName(), "TeCHO - Sewa Service Is Not Injected");
        }

        if (techoService != null) {
            SharedStructureData.techoService = techoService;
        }

        if (sewaFhsService != null) {
            SharedStructureData.sewaFhsService = sewaFhsService;
        }
        if (locationMasterService != null) {
            SharedStructureData.locationMasterService = locationMasterService;
        }
        checkIfDeviceIsBlocked();
    }

    @Background
    public void checkDeviceRootAccess() {
        if (RootUtil.isDeviceRooted()) {
            showAlertAndExit(LabelConstants.NOT_ALLOW_ON_ROOTED_DEVICE_ALERT);
        }
    }

    @Background
    public void checkIfDeviceIsBlocked() {
        Map<String, Boolean> deviceState = techoService.checkIfDeviceIsBlockedOrDeleteDatabase(context);
        if (Boolean.TRUE.equals(deviceState.get("isBlocked"))) {
            showAlertAndExit(LabelConstants.DEVICE_HAS_BEEN_BLOCKED_ALERT);
        } else if (Boolean.TRUE.equals(deviceState.get("isDatabaseDeleted"))) {
            showAlertAndExit(LabelConstants.DEVICE_HAS_BEEN_RESET_ALERT);
        } else {
            onCheckUpdateComplete();
        }
    }

    @UiThread
    public void showAlertAndExit(final String msg) {
        hideProcessDialog();
        View.OnClickListener listener = v -> {
            dialogForExit.dismiss();
            android.os.Process.killProcess(android.os.Process.myPid());
            Runtime.getRuntime().exit(0);
        };
        dialogForExit = new MyAlertDialog(this, false, msg, listener, DynamicUtils.BUTTON_OK);
        dialogForExit.show();
    }

    private void onCheckUpdateComplete() {
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String from = extras.getString("From");
                if (from != null && from.equalsIgnoreCase("logout")
                        && SewaTransformer.loginBean != null) {
                    SewaTransformer.loginBean.setFirstLogin(false);
                    SewaTransformer.loginBean.setLoggedIn(false);
                    SewaTransformer.loginBean.setHidden(false);
                    loginDao.update(SewaTransformer.loginBean);
                    isResume = false;
                }
            }
        } catch (SQLException e) { // come if not from announcement
            Log.e(getClass().getName(), null, e);
        }

        String minMaxVersions = sewaService.getAndroidVersion();
//        String minMaxVersions = "107-106";
        if (minMaxVersions == null) {
            continueProcess();
            return;
        }

        String currentVersion = minMaxVersions.split("-")[0];
        if (currentVersion == null) {
            continueProcess();
            return;
        }

        boolean isMajor = false;
        Log.i(getClass().getSimpleName(), "#### DB SEWA APP VERSION :" + currentVersion);
        if (currentVersion.contains("M") || currentVersion.contains("m")) {
            isMajor = true;
            currentVersion = currentVersion.replace("M", "").replace("m", "");
        }

        final String apkName = BuildConfig.APK_NAME + "_" + currentVersion + ".apk";

        if (!checkAuthenticityOfCurrentVersion(minMaxVersions, apkName)) {
            return;
        }

        if (Integer.parseInt(currentVersion) > BuildConfig.VERSION_CODE) {
            Log.i(getClass().getSimpleName(), "#### APP NAME (apk name): " + apkName);
            Log.i(getClass().getSimpleName(), "#### Current Version :" + currentVersion + " & Config Local Version : " + BuildConfig.VERSION_CODE);

            if (BackgroundDownloadUtils.isApkExistsOnLocal(context, apkName)) {
                hideProcessDialog();
                BackgroundDownloadUtils.showInstallAppDialog(this, apkName);
            } else if (BackgroundDownloadUtils.isDownloadNotInProgress(this, apkName)) {
                String downloadPath = null;
                try {
                    downloadPath = restClient.getSystemConfigurationByKey("ANDROID_APP_LINK");
                } catch (RestHttpException e) {
                    Log.e(getClass().getSimpleName(), e.getMessage(), e);
                }
                if (downloadPath == null) {
                    continueProcess();
                    return;
                }

                if (isMajor) {
                    String finalDownloadPath = downloadPath;
                    showAlert(UtilBean.getMyLabel(LabelConstants.APP_UPDATE_STATUS),
                            UtilBean.getMyLabel(LabelConstants.NEW_APPLICATION_AVAILABLE),
                            view -> {
                                myDialog.dismiss();
                                BackgroundDownloadUtils.deleteAllFiles(getContext(),
                                        new File(SewaConstants.getDirectoryPath(context, SewaConstants.DIR_APK_DOWNLOADED)));
                                BackgroundDownloadUtils.startAppDownloading(getContext(), apkName, finalDownloadPath);
                                Intent downloadProgress = new Intent(context,
                                        DownloadProgressActivity.class);
                                startActivity(downloadProgress);
                                finish();
                            },
                            DynamicUtils.BUTTON_OK);
                } else {
                    BackgroundDownloadUtils.deleteAllFiles(context,
                            new File(SewaConstants.getDirectoryPath(context, SewaConstants.DIR_APK_DOWNLOADED)));
                    BackgroundDownloadUtils.startAppDownloading(context, apkName, downloadPath);
                    continueProcess();
                }
            } else {
                continueProcess();
            }
        } else {
            Log.i(getClass().getSimpleName(), "#### Latest version.... So, Deleting all the files");
            BackgroundDownloadUtils.deleteAllFiles(this, new File(
                    SewaConstants.getDirectoryPath(context, SewaConstants.DIR_APK_DOWNLOADED)));
            continueProcess();
        }
    }

    private void continueProcess() {
        //method to set is training flag
        try {
            SewaUtil.isUserInTraining = loginDao.queryForAll().get(0).isTrainingUser();
        } catch (Exception e) {
            SewaUtil.isUserInTraining = true;
        }
        if (isResume) {
            if (SewaTransformer.loginBean != null && SewaTransformer.loginBean.isHidden()) {
                int setContextUrl = sewaService.isUserInProduction(SewaTransformer.loginBean.getUsername());
                setUserInProductionOrTraining(setContextUrl);
                doAfterLogin(true);
            } else {
                setLoginScreen();
            }
        } else {
            setLoginScreen();
        }
    }

    @UiThread
    public void showAlert(String title, String msg, View.OnClickListener listner, int buttonType) {
        hideProcessDialog();
        myDialog = new MyAlertDialog(this, false, msg, listner, buttonType);
        myDialog.show();
    }

    @UiThread
    public void generateToaster(String title) {
        SewaUtil.generateToast(this, title);
    }

    @UiThread
    public void setUserInProductionOrTraining(int value) {
        if (value == 0) {
            if (SharedStructureData.NETWORK_MESSAGE != null && SharedStructureData.NETWORK_MESSAGE.equalsIgnoreCase(SewaConstants.NETWORK_NOT_PROPER)) {
                SewaUtil.generateToast(this, LabelConstants.USER_SERVER_CHECKING_FAIL);
            } else {
                SewaUtil.generateToast(this, LabelConstants.USER_IS_NOT_AVAILABLE_ON_SERVER);
            }
        } else if (value == 1) {
            SewaUtil.CURRENT_THEME = R.style.techo_app;
        } else if (value == 2) {
            SewaUtil.generateToast(this, LabelConstants.WORKING_ON_TRAINING);
            SewaUtil.CURRENT_THEME = R.style.techo_training_app;
        } else if (value == 3) {
            if (SewaUtil.isUserInTraining) {
                SewaUtil.CURRENT_THEME = R.style.techo_training_app;
            } else {
                SewaUtil.CURRENT_THEME = R.style.techo_app;
            }
        }
    }

    @UiThread
    public void setLoginScreen() {
        hideProcessDialog();
        setUpViewContent();
        etUsername = findViewById(R.id.txtUsername);
        etPassword = findViewById(R.id.txtPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        loginButton = findViewById(R.id.loginButton);
        TextView textView = findViewById(R.id.versionView);
        String version = "v. " + BuildConfig.VERSION_NAME;
        textView.setText(version);
        loginButton.setOnClickListener(this);
        if (SewaTransformer.loginBean != null) {
            String username = SewaTransformer.loginBean.getUsername();
            if (username.endsWith("_t")) {
                username = username.substring(0, username.length() - 2);
            }
            etUsername.setText(username);
            if (BuildConfig.DEBUG) {
                cbRememberMe.setChecked(SewaTransformer.loginBean.isRemember());
                if (SewaTransformer.loginBean.isRemember()) {
                    etPassword.setText(SewaTransformer.loginBean.getPasswordPlain());
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        v.setEnabled(false);
        int id = v.getId();
        if (id == BUTTON_POSITIVE) {
            if (myDialog != null) {
                myDialog.dismiss();
                finish();
            }
        } else if (id == BUTTON_NEGATIVE) {
            if (myDialog != null) {
                myDialog.dismiss();
            }
        } else if (id == loginButton.getId()) {
            loginButton.setEnabled(false);
            loginUsername = etUsername.getText().toString();
            loginPassword = etPassword.getText().toString();
            if (loginUsername == null || loginUsername.trim().length() <= 0) {
                SewaUtil.generateToast(this, LabelConstants.PLEASE_ENTER_USERNAME);
                loginButton.setEnabled(true);
            } else if (loginPassword == null || loginPassword.trim().length() <= 0) {
                SewaUtil.generateToast(this, LabelConstants.PLEASE_ENTER_PASS_WORD);
                loginButton.setEnabled(true);
            } else {
                if (processDialog == null || !processDialog.isShowing()) {
                    if (sewaService.isOnline()) {
                        processDialog = new MyProcessDialog(this, GlobalTypes.MSG_STARTUP_APPLICATION);
                        processDialog.show();
                    }
                    new Thread(() -> {
                        int inProduction = sewaService.isUserInProduction(loginUsername);
                        if (inProduction == 1) {
                            String loginResponse = sewaService.validateUser(loginUsername, loginPassword, inProduction, cbRememberMe.isChecked());
                            hideProcessDialog();
                            onLoginComplete(loginResponse, inProduction);
                        } else if (inProduction == 2) {
                            if (!loginUsername.endsWith("_t")) {
                                String loginResponse = sewaService.validateUser(loginUsername, loginPassword, inProduction, cbRememberMe.isChecked());
                                onLoginComplete(loginResponse, inProduction);
                            } else {
                                runOnUiThread(() -> SewaUtil.generateToast(context, "Please login with your regular User ID"));
                                hideProcessDialog();
                            }

                        } else if (inProduction == 3) {
                            //Production Server is down // Do Offline Login
                            String loginResponse = sewaService.validateUser(loginUsername, loginPassword, inProduction, cbRememberMe.isChecked());
                            onLoginComplete(loginResponse, inProduction);
                        } else {
                            runOnUiThread(() ->{
                                if (SharedStructureData.NETWORK_MESSAGE != null && SharedStructureData.NETWORK_MESSAGE.equalsIgnoreCase(SewaConstants.NETWORK_NOT_PROPER)) {
                                    SewaUtil.generateToast(this, LabelConstants.USER_SERVER_CHECKING_FAIL);
                                } else {
                                    SewaUtil.generateToast(this, LabelConstants.USER_IS_NOT_AVAILABLE_ON_SERVER);
                                }
                                hideProcessDialog();
                            });
                        }
                    }).start();
                }
            }
        }
        v.setEnabled(true);
    }

    private Context getContext() {
        return this;
    }

    @UiThread
    public void onLoginComplete(String loginResponse, int contextUrlInfo) {
        loginButton.setEnabled(true);
        if (loginResponse.isEmpty() && SharedStructureData.NETWORK_MESSAGE != null && SharedStructureData.NETWORK_MESSAGE.equalsIgnoreCase(SewaConstants.NETWORK_NOT_PROPER)) {
            loginResponse = SharedStructureData.NETWORK_MESSAGE;
        }
        if (!loginResponse.isEmpty()) {
            SewaUtil.generateToast(this, loginResponse);
        }
        if (loginResponse.equalsIgnoreCase(SewaConstants.LOGIN_SUCCESS_WEB)
                || loginResponse.contains(GlobalTypes.MOBILE_DATE_NOT_SAME_SERVER)) {
            if (SewaTransformer.loginBean != null) {
                try {
                    SewaTransformer.loginBean.setLoggedIn(false);
                    loginDao.update(SewaTransformer.loginBean);
                } catch (Exception e) {
                    Log.e(getClass().getName(), null, e);
                }
            }
            setUserInProductionOrTraining(contextUrlInfo);
            doAfterLogin(true);
        } else if (loginResponse.equalsIgnoreCase(SewaConstants.LOGIN_SUCCESS_LOCAL) || (SewaTransformer.loginBean != null &&
                SewaTransformer.loginBean.getUsername().equalsIgnoreCase(loginUsername) &&
                SharedStructureData.NETWORK_MESSAGE != null &&
                SharedStructureData.NETWORK_MESSAGE.equalsIgnoreCase(SewaConstants.NETWORK_NOT_PROPER))) {
            setUserInProductionOrTraining(contextUrlInfo);
            doAfterLogin(false);
        } else {
            hideProcessDialog();
        }
    }

    @UiThread
    public void doAfterLogin(boolean flag) {
        if (sewaService.isOnline()) {
            if (processDialog != null) {
                TextView message = processDialog.findViewById(IdConstants.PROGRESS_DIALOG_MESSAGE_ID); // that is id given to message text box
                if (message != null) {
                    message.setText(UtilBean.getMyLabel(GlobalTypes.MSG_LOGIN_SCREEN));
                }
            } else {
                processDialog = new MyProcessDialog(this, GlobalTypes.MSG_LOGIN_SCREEN);
            }
            processDialog.show();
        }
        doAfterSuccessfulLogin(flag);
    }

    @Background
    public void doAfterSuccessfulLogin(boolean flag) {
        if (SewaTransformer.loginBean != null) {
            try {
                SewaTransformer.loginBean.setLoggedIn(true);
                loginDao.update(SewaTransformer.loginBean);
            } catch (Exception e) {
                Log.e(getClass().getName(), null, e);
            }
        }
        sewaService.doAfterSuccessfulLogin(flag);
        if (!SharedStructureData.NETWORK_MESSAGE.equalsIgnoreCase(SewaConstants.NETWORK_AVAILABLE) &&
                !SharedStructureData.NETWORK_MESSAGE.equalsIgnoreCase(SewaConstants.NETWORK_FAILURE)) {
            if (SharedStructureData.NETWORK_MESSAGE.equalsIgnoreCase(SewaConstants.SQL_EXCEPTION)) {
                showAlert(LabelConstants.NETWORK,
                        LabelConstants.APP_DATA_ERROR,
                        v -> {
                            techoService.deleteDatabaseFileFromLocal(context);
                            UtilBean.restartApplication(context);
                        },
                        DynamicUtils.BUTTON_OK);
            } else {
                showAlert(LabelConstants.NETWORK,
                        SharedStructureData.NETWORK_MESSAGE,
                        v -> {
                            if (v.getId() == BUTTON_POSITIVE) {
                                onDoAfterLoginComplete();
                            } else {
                                myDialog.dismiss();
                            }
                        },
                        DynamicUtils.LOGIN_BUTTON_ALERT);
            }

            if (SharedStructureData.NETWORK_MESSAGE.equalsIgnoreCase(SewaConstants.NETWORK_NOT_PROPER)
                    || SharedStructureData.NETWORK_MESSAGE.equalsIgnoreCase(SewaConstants.EXCEPTION_FETCHING_DATA_FOR_USER)) {
                generateToaster(SharedStructureData.NETWORK_MESSAGE);
            }

        } else {
            VersionBean bean = new VersionBean();
            bean.setKey(SewaConstants.TIME_STAMP_LAST_REFRESH);
            bean = SharedStructureData.sewaService.getSheetStatusByFiletr(bean);
            if (bean == null) {
                bean = new VersionBean();
                bean.setKey(SewaConstants.TIME_STAMP_LAST_REFRESH);
            }
            bean.setValue(Calendar.getInstance().getTimeInMillis() + "");
            SharedStructureData.sewaService.updateVersionBean(bean);
            onDoAfterLoginComplete();
        }

    }

    private void onDoAfterLoginComplete() {
        hideProcessDialog();
        Intent intent = null;
        if (SewaUtil.isUserInTraining) {
            List<FormAccessibilityBean> formAccessibilityBeans = moveToProductionService.isAnyFormTrainingCompleted();
            if (formAccessibilityBeans != null && !formAccessibilityBeans.isEmpty()) {
                intent = new Intent(this, MoveToProductionActivity_.class);
                SharedStructureData.isLogin = true;
                startActivity(intent);
                finish();
                return;
            }
        }

        if (SewaTransformer.loginBean != null && SewaTransformer.loginBean.getUserRole() != null && !SewaTransformer.loginBean.getUserRole().trim().isEmpty()) {
            intent = new Intent(this, GenericHomeScreenActivity_.class);
            intent.putExtra(SewaConstants.IS_FROM_LOGIN_FLOW, Boolean.TRUE);
        }

        if (intent != null) {
            SharedStructureData.isLogin = true;
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        myDialog = new MyAlertDialog(this, GlobalTypes.MSG_CANCEL_APPLICATION, this, DynamicUtils.BUTTON_YES_NO);
        myDialog.show();
    }

    private boolean checkAuthenticityOfCurrentVersion(String minMaxVersion, final String apkName) {
        String[] versions = minMaxVersion.split("-");
        if (versions.length == 1) {
            return true;
        }

        int installedAppVersion = BuildConfig.VERSION_CODE;
        int minSupportedVersion = Integer.parseInt(versions[1]);

        if (BackgroundDownloadUtils.isApkExistsOnLocal(context, apkName)) {
            hideProcessDialog();
            BackgroundDownloadUtils.showInstallAppDialog(getContext(), apkName);
        } else if (installedAppVersion < minSupportedVersion) {
            String downloadPath = null;
            try {
                downloadPath = restClient.getSystemConfigurationByKey("ANDROID_APP_LINK");
            } catch (RestHttpException e) {
                Log.e(getClass().getSimpleName(), e.getMessage(), e);
            }
            if (downloadPath == null) {
                continueProcess();
                return true;
            }

            String finalDownloadPath = downloadPath;
            showAlert(UtilBean.getMyLabel(LabelConstants.APP_UPDATE_STATUS), UtilBean.getMyLabel(
                            LabelConstants.NEW_APPLICATION_AVAILABLE),
                    v -> {
                        myDialog.dismiss();
                        BackgroundDownloadUtils.deleteAllFiles(getContext(), new File(SewaConstants.getDirectoryPath(context, SewaConstants.DIR_APK_DOWNLOADED)));
                        BackgroundDownloadUtils.startAppDownloading(getContext(), apkName, finalDownloadPath);
                        Intent downloadProgress = new Intent(context, DownloadProgressActivity.class);
                        startActivity(downloadProgress);
                        finish();
                    }, DynamicUtils.BUTTON_OK);
            return false;
        }
        return true;
    }

    @UiThread
    public void hideProcessDialog() {
        if (processDialog != null && processDialog.isShowing()) {
            processDialog.dismiss();
        }
    }
}
