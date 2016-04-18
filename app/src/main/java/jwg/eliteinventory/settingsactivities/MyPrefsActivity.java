package jwg.eliteinventory.settingsactivities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import jwg.eliteinventory.R;

/**
 * Created by John on 4/12/2016.
 */
public class MyPrefsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPrefsFragment())
                .commit();
    }

}
