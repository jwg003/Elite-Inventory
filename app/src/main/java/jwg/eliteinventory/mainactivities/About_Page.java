package jwg.eliteinventory.mainactivities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

/** Created by John **/
import jwg.eliteinventory.R;

public class About_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        /** Initializes Material Design Bar at top of screen **/
        /* Custom Actionbar */
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button (Back button)
        ab.setDisplayHomeAsUpEnabled(true);



    }

}
