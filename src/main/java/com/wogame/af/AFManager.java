package com.wogame.af;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;

/**
 * 需要在build.gradle 里的 添加渠道 manifestPlaceholders = [AF_CHANNEL:"xiaomi"]
 *
 */
public class AFManager {
    private static AFManager instance;
    private Activity mAppActivity;

    public static AFManager getInstance() {
        if (instance == null) {
            instance = new AFManager();
        }
        return instance;
    }

    public void initApplication(Application application,final String afDevKey) {
        //If you want to show debug log.
//        AppsFlyerLib.getInstance().setDebugLog(true);
        //Or use the two APIs below let AppsFlyer SDK collect IMEI & android id
        //no matter if the Google Play Service exists or not.
        AppsFlyerLib.getInstance().setCollectIMEI(true);
        AppsFlyerLib.getInstance().setCollectAndroidID(true);
        //Configure the min time between two sessions, the recommendation is 2 seconds。
        AppsFlyerLib.getInstance().setMinTimeBetweenSessions(2);

        AppsFlyerConversionListener conversionDataListener = new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                Log.e("AF","33333");
            }

            @Override
            public void onConversionDataFail(String s) {
                Log.e("AF","33333");
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {
                Log.e("AF","33333");
            }

            @Override
            public void onAttributionFailure(String s) {
                Log.e("AF","33333");
            }
        };
        AppsFlyerLib.getInstance().init(afDevKey, conversionDataListener, application.getApplicationContext());
        AppsFlyerLib.getInstance().startTracking(application);
    }

    public void initActivity(Activity activity) {
        mAppActivity = activity;
//        AppsFlyerLib.getInstance().sendDeepLinkData(mAppActivity);
    }

    /**
     *
     * @param var MyApplication.getAppInstance()
     */
    public void reportTrackSession(Context var){
        AppsFlyerLib.getInstance().setCollectIMEI(true);
        AppsFlyerLib.getInstance().setCollectAndroidID(true);
        //NOTE: Here the report session API is reportTrackSession() not the startTracking()
        AppsFlyerLib.getInstance().reportTrackSession(var);
    }

    public void trackEventAdClick(final int type){
        Map<String,Object> eventValues = new HashMap<>();
        eventValues.put(AFInAppEventParameterName.REVENUE, type);
        AppsFlyerLib.getInstance().trackEvent(mAppActivity, AFInAppEventType.AD_CLICK, eventValues);

    }

    public void trackEventAdView(final int type){
        Map<String,Object> eventValues = new HashMap<>();
        eventValues.put(AFInAppEventParameterName.REVENUE, type);
        AppsFlyerLib.getInstance().trackEvent(mAppActivity, AFInAppEventType.AD_VIEW, eventValues);
    }
}
