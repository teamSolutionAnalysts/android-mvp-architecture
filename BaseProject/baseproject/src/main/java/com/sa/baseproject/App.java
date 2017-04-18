package com.sa.baseproject;

import android.app.Application;

import com.sa.baseproject.db.DBUser;
import com.sa.baseproject.utils.PreferenceUtils;
import com.sa.baseproject.webservice.ApiService;


/**
 * Created by sa on 29/03/17.
 *
 */

public class App extends Application {


    private static App instance;

    private ApiService apiService;

    private PreferenceUtils preferenceUtils;

    private DBUser dbUser;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        apiService = new ApiService();
        dbUser = new DBUser(this);
        preferenceUtils = new PreferenceUtils(this);
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (instance != null) {
            instance = null;
        }
        if (dbUser != null) {
            dbUser.close();
        }
    }

    public ApiService getApiService() {
        return apiService;
    }


    public PreferenceUtils getPreferenceUtils() {
        return preferenceUtils;
    }

    public DBUser getDbUser() {
        return dbUser;
    }

}
