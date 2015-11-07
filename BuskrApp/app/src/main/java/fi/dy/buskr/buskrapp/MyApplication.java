package fi.dy.buskr.buskrapp;

import android.app.Application;
import com.estimote.sdk.BeaconManager;

/**
 * Created by Janne on 7.11.2015.
 */
public class MyApplication extends Application {
    private BeaconManager beaconManager;

    @Override
    public void onCreate(){
        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());
    }
}
