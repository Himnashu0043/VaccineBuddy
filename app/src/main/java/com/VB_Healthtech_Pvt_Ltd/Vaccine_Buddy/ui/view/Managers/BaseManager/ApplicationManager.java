package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Managers.BaseManager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ApplicationManager extends MultiDexApplication {

    //instagram integration
    public static final String CLIENT_ID = "0dab53293c6d43198cf57cca8baa2221";
    public static final String REDIRECT_URL = "https://www.mobulous.com/";

    //Static Properties
    private static Context _Context;

    protected Location mLastLocation;



    public static Context getContext() {
        return _Context;
    }

    public static ApplicationManager getInstance() {

        return (ApplicationManager) _Context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _Context = getApplicationContext();
       // FacebookSdk.sdkInitialize(this);
        // LocaleHelper.onCreate(this, "ar");

        // ZohoSalesIQ.init(this, "YsM2PIqULGyCPGw%2BoDX%2FHowpcmNEPf%2B%2BUlKLTAop9faEcg4FOMExWEhfQekReAtHbP5dwiisEhCcERWbymT1wQ%3D%3D_in", "otHqns%2BYqEP7fV3JRIlumPBTUhhuGHcCIPKNb0IPVpcEYviJgx3j10paeTN4Imp0NE0UwHfPLqtZ7NjVtzahVBsol%2B%2BrGdCFYq5xbV%2BjlgFt61qan6BJHrAHWUySnKanBgFQK8Gs8YvzAyEtkJvH3RPCANC9LnQa" );
        // ZohoSalesIQ.showLauncher(true);

        //We must initialize the ModelManager singleton object at the time of application launch
        printKeyHash(this);




    }




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }   

    /**
     * detects things you might be doing by accident and brings them to your attention so you can fix them
     *
     * @link https://developer.android.com/reference/android/os/StrictMode.html
     */
    private void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
       /* StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());*/
    }

    public void printKeyHash(Context context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            // getting application package name, as defined in manifest
            String packageName = context.getApplicationContext()
                    .getPackageName();

            // Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext()
                    .getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Hash Key ==> ", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }



}