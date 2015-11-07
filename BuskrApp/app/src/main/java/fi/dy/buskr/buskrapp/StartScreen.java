package fi.dy.buskr.buskrapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

public class StartScreen extends AppCompatActivity {
    // Public app TAG
    public static final String TAG = "BuskrApp";

    // Buskr global UUID
    public static final UUID BUSKER_UUID = UUID.fromString("8EC8B69C-34E3-AC64-7984-7E2538AA7354");


    // Private beacon variables
    private BeaconManager beaconManager;
    private Region region;

    // Artist resolver to find the artist combined with the estimote
    ArtistResolver artistResolver;

    // EXTRA for the artist variable (to be passed on with the intent)
    public final static String EXTRA_ARTIST = "fi.dy.buskr.ARTIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create artist resolver
        artistResolver = new ArtistResolver();

        // Create beacon manager to scan for nearby beacons
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if(!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    int artistId = nearestBeacon.getMajor();
                    Log.w(TAG, "Major: " + String.valueOf(artistId));
                    Artist beaconArtist = artistResolver.getArtist(artistId);

                    // continue if artist is null for some reason
                    if(beaconArtist==null){
                        return;
                    }

                    // Create new intent to navigate to the DonateToArtist screen
                    Intent intent = new Intent(StartScreen.this, DonateToArtistActivity.class);
                    intent.putExtra(EXTRA_ARTIST, beaconArtist);
                    startActivity(intent);
                }
            }
        });

        region = new Region("Buskr region", BUSKER_UUID, null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback(){
            @Override
            public void onServiceReady(){
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
