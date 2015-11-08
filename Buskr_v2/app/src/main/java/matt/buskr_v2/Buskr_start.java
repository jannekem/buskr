package matt.buskr_v2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class Buskr_start extends Activity {

        private ImageView view;
        private AnimationDrawable frameAnimation;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_buskr_start);
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
            setContentView(R.layout.activity_buskr_start);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_buskr_start, menu);
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
