package jwg.eliteinventory.splashactivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import jwg.eliteinventory.R;
import jwg.eliteinventory.mainactivities.MainActivity;

/** Created by John **/

public class SplashScreen extends Activity {

    /** Showing splash screen with a timer. **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_TIME_OUT = 3000; //The amount of time splash screen is displayed
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}