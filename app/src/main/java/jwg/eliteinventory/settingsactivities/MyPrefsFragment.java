package jwg.eliteinventory.settingsactivities;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import jwg.eliteinventory.R;

/** Created by John **/
public class MyPrefsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
