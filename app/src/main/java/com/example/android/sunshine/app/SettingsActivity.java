package com.example.android.sunshine.app;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adds the general preference layout
        addPreferencesFromResource(R.xml.pref_general);

        //for all preferences, attach a onpreferencechangelistener so that the ui can react accordingly
        //any changes in activity will be saved through binding
        bindPreferenceSummaryToValue(findPreference(getString(R.string.location_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_units_key)));


    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
        return true;
    }
}
