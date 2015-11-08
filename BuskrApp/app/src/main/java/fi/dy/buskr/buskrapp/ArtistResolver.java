package fi.dy.buskr.buskrapp;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Janne on 7.11.2015.
 */
public class ArtistResolver extends AsyncTask<Object, Void, Object>{
    protected Object doInBackground(Object... param){


        return null;
    }

    protected void onPostExecute(Object... param) {

    }


    public final static String REMOTE_SERVER_ADDRESS = "http://header.jsontest.com";

    public ArtistResolver() { }

    JSONObject createJSON(int major, int minor) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("major", major);
            jsonObject.put("minor", minor);
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        Log.w("BuskrApp",jsonObject.toString());

        return jsonObject;
    }
}
