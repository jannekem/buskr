package matt.buskr_v2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class donateActivity extends Activity {

    public float donateAmount = 0.0f;
    private ImageView view;
    private AnimationDrawable frameAnimation;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        donateAmount = 0.0f;
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

    public void enterThanks(View v) {
        Intent intent = new Intent(this, thanksActivity.class);
        startActivity(intent);
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
        text.setText(Float.toString(donateAmount));
    }


}
