package com.example.android.sunshine.app;
/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;


        import android.os.Bundle;
        import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.android.sunshine.app.R.id.container;

public class DetailActivity extends ActionBarActivity {

    public DetailActivity(){


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(container, new DetailFragment())
                    .commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
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
            startActivity(new Intent(this,SettingsActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        //strings to provide sharing as plain text
        private static final String FORECAST_HASHTAG = " #SunshineApp";
        private String mForecastString;

        public DetailFragment() {

            //must be enabled to produce menu
            setHasOptionsMenu(true);



        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            //provide menu inflation with selected item
            inflater.inflate(R.menu.detailfragment,menu);
            //find the selected menuitem via id
            MenuItem menuItem = menu.findItem(R.id.action_share);
            //if it is an action, true
            ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            if(shareActionProvider!=null){
                shareActionProvider.setShareIntent(createShareForecastIntent());
            }else{
                Log.d("Activity","share is null");
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent intent = getActivity().getIntent();
            if(intent!=null&&intent.hasExtra(Intent.EXTRA_TEXT)) {
                TextView forecastSummary = (TextView) rootView.findViewById(R.id.textview_forecast_summary);
                 mForecastString = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
                forecastSummary.setText(mForecastString);
            }
            return rootView;
        }


        private Intent createShareForecastIntent(){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,mForecastString + FORECAST_HASHTAG);
            return shareIntent;
        }
    }
}