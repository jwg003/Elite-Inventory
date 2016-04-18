package jwg.eliteinventory.splashactivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import jwg.eliteinventory.R;
import jwg.eliteinventory.mainactivities.MainActivity;

/**
 * Created by John on 4/11/2016.
 */

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_TIME_OUT = 3000; //Change back to 3000 when ready
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}