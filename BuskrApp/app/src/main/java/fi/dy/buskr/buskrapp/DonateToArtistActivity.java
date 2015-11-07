package fi.dy.buskr.buskrapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DonateToArtistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_to_artist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        // Receive artist
        Artist artist = (Artist) intent.getSerializableExtra(StartScreen.EXTRA_ARTIST);

        TextView textView = new TextView(this);
        textView.setText(artist.getName());

        setContentView(textView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
