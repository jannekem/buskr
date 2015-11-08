package fi.dy.buskr.buskrapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.klarna.ondemand.OriginProof;
import com.klarna.ondemand.RegistrationActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;


public class donateActivity extends Activity {

    public double donateAmount = 0.0f;
    private ImageView view;
    private AnimationDrawable frameAnimation;
    private TextView text;
    private Artist artist;

    private static int REGISTRATION_REQUEST_CODE = 1122;
    private static String USER_TOKEN_KEY = "user_token_key";
    private static final String SERVER_PAYMENT_ADDRESS = "http://buskr.cfapps.io/user/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        donateAmount = 0.0f;

        Intent intent = getIntent();

        // Receive artist
        artist = (Artist) intent.getSerializableExtra(StartScreen.EXTRA_ARTIST);
        TextView artistText = (TextView) findViewById(R.id.artistName);
        artistText.setText(artist.getName());
        TextView artistDescriptionText = (TextView) findViewById(R.id.artistTag);
        artistDescriptionText.setText(artist.getDescription());


                text = (TextView) findViewById(R.id.donateAmount);
        String donateAmountText = String.format("%.2f\u20ac", donateAmount);
        text.setText(donateAmountText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donate, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REGISTRATION_REQUEST_CODE) {
            switch (resultCode) {
                case RegistrationActivity.RESULT_OK:
                    // Extract the registration result from the activity's extra data
                    //RegistrationResult registrationResult = (RegistrationResult) data.getSerializableExtra(RegistrationActivity.EXTRA_REGISTRATION_RESULT);
                    //String token = registrationResult.getToken();
                    String token = data.getStringExtra(RegistrationActivity.EXTRA_USER_TOKEN);

                    // Here we store the token assigned to the user
                    // This is for illustrative purposes, we do not supply this method
                    saveUserToken(token);
                    break;
                case RegistrationActivity.RESULT_CANCELED:
                    break;
                case RegistrationActivity.RESULT_ERROR:
                    // You will want to convey this failure to your user.
                    break;
                default:
                    break;
            }
        }
        // Possibly handle other request codes
    }

    private void saveUserToken(String token) {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_TOKEN_KEY, token);
        editor.commit();
    }

    private String getUserToken() {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        String token = preferences.getString(USER_TOKEN_KEY, null);
        return token;
    }

    private boolean hasUserToken() {
        return getUserToken() != null;
    }

    public void checkoutKlarna(View v) {
        if(this.hasUserToken()){
            donatePrepare();
        } else {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivityForResult(intent, REGISTRATION_REQUEST_CODE);
        }
       // Intent intent = new Intent(this, thanksActivity.class);
       // startActivity(intent);
    }

    private void donatePrepare() {
        // Create an origin proof for the order
        final OriginProof originProof = new OriginProof((int)(100*donateAmount), "SEK", getUserToken(), getApplicationContext());

        // Run a background thread to perform the purchase
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    performPurchaseOfItem(artist.getId(),originProof);
                }catch (final Exception e) {

                }
            }
        });
        thread.start();
    }

    private void performPurchaseOfItem(String reference, OriginProof originProof) throws IOException, JSONException {
        // Create a post request to instruct the backend to perform the purchase.
        // For Genymotion devices, use the following path: http://10.0.3.2:9292/pay.
        // Remember that this expects to work with our sample backend: https://github.com/klarna/sample-ondemand-backend.
        Log.w("BuskerPerformPurchase", reference);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("origin_proof", originProof.toString());
        jsonObject.put("amount", (int)(100*donateAmount));
        jsonObject.put("user_token",getUserToken());

        // Get the request queue to put request
        RequestQueue queue = MySingleton.getInstance(donateActivity.this.getApplicationContext()).getRequestQueue();

        // Create request
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,SERVER_PAYMENT_ADDRESS,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Send artist info to the donate activity


                        // GO to the thank you screen
                        Intent intent = new Intent(donateActivity.this, thanksActivity.class);
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

    public void add(View v) {
        switch (v.getId())
        {
            case (R.id.buskr50c):
                donateAmount += 0.5f;
                break;
            case (R.id.buskr1e):
                donateAmount += 1.0f;
                break;
            case (R.id.buskr2e):
                donateAmount += 2.0f;
                break;
            default:
                break;
        }
        updateText();
    }


    public void updateText() {
        text = (TextView) findViewById(R.id.donateAmount);
        String donateAmountText = String.format("%.2f\u20ac", donateAmount);
        text.setText(donateAmountText);
    }


}
