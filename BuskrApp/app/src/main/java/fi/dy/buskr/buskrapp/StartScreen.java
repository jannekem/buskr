package fi.dy.buskr.buskrapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;
import com.android.volley.*;

import org.json.JSONObject;

public class StartScreen extends AppCompatActivity {
    // Public app TAG
    public static final String TAG = "BuskrApp";

    // Buskr global UUID
    public static final UUID BUSKER_UUID = UUID.fromString("8EC8B69C-34E3-AC64-7984-7E2538AA7354");


    // Private beacon variables
    private BeaconManager beaconManager;
    private Region region;

    // display vars
    private ImageView view;
    private AnimationDrawable frameAnimation;

    // Artist resolver to find the artist combined with the estimote
    ArtistResolver artistResolver;

    // EXTRA for the artist variable (to be passed on with the intent)
    public final static String EXTRA_ARTIST = "fi.dy.buskr.ARTIST";

    // SERVER ADDRESS
    public final static String SERVER_ADDRESS = "http://buskr.cfapps.io/user/";
    //public final static String SERVER_ADDRESS = "http://validate.jsontest.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Create artist resolver
        artistResolver = new ArtistResolver();

        // Create beacon manager to scan for nearby beacons
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if(!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    JSONObject jsonObject = artistResolver.createJSON(nearestBeacon.getMajor(), nearestBeacon.getMinor());

                    // Get the request queue to put request
                    RequestQueue queue = MySingleton.getInstance(StartScreen.this.getApplicationContext()).getRequestQueue();

                    // Create request
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,SERVER_ADDRESS,jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Send artist info to the donate activity
                                    Log.w("BuskerApp",response.toString());
                                    Artist artist = new Artist("James Elliot", new BankAccountInfo(), "I'm the king of brick lane");
                                    Intent intent = new Intent(StartScreen.this, donateActivity.class);
                                    intent.putExtra(EXTRA_ARTIST, artist);
                                    startActivity(intent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            });
                    Log.w("BuskerObjectRequest",jsObjRequest.toString());
                    queue.add(jsObjRequest);
                }
            }
        });

        region = new Region("Buskr region", BUSKER_UUID, null, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        view = (ImageView) findViewById(R.id.frontpageAnim);
        view.setBackgroundResource(R.drawable.animation_list);
        frameAnimation = (AnimationDrawable) view.getBackground();
        frameAnimation.start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.content_start_screen);
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

    public void enterSettings(View v) {
        Intent intent = new Intent(this, settingsActivity.class);
        startActivity(intent);
    }

    public void enterMusic(View v) {
        Intent intent = new Intent(this, musicActivity.class);
        startActivity(intent);
    }

    public void enterMap(View v) {
        Intent intent = new Intent(this, mapActivity.class);
        startActivity(intent);
    }

    public void enterDonate() {
        Intent intent = new Intent(this, donateActivity.class);
        startActivity(intent);
    }

    public void bypass(View v) {
        enterDonate();
    }


}
