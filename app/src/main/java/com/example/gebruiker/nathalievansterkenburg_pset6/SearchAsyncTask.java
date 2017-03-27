package com.example.gebruiker.nathalievansterkenburg_pset6;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by Nathalie on 20-3-2017.
 */

public class SearchAsyncTask extends AsyncTask<String, Integer, String> {

    Context context;
    SearchActivity searchAct;

    // context and mainAct are initialized
    public SearchAsyncTask(SearchActivity search) {
        this.searchAct = search;
        this.context = this.searchAct.getApplicationContext();
    }

    // let's user know program is waiting for result
    protected void onPreExecute() {
        Toast.makeText(context, "searching for schools", Toast.LENGTH_SHORT).show();
    }

    // schools are retrieved
    protected String doInBackground(String... parameters) {

        try {
            // with use of HttpRequestHelper
            Log.i("ik kom hier", "denk ik wel");
            return HttpRequestHelper.downloadFromServer(parameters);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return "";
    }

    // processes result from HttpRequestHelper
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // list of schools
        ArrayList<String> schools = new ArrayList<String>();

        try {

            // the result is put in a JSONObject
            JSONObject schoolsearch = new JSONObject(result);

            // the schools are extracted from "results"
            JSONArray inschoolsearch = schoolsearch.getJSONArray("results");

            // the schools are put in a list
            for (int i = 0; i < inschoolsearch.length(); i++) {

                // each school is extracted from the result
                JSONObject listitem = inschoolsearch.getJSONObject(i);

                // and the shools name is stored in the array
                String school = listitem.getString("INSTELLINGSNAAM");
                schools.add(school);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // a list of the schools is made
        this.searchAct.makeSchoolAdapter(schools);
    }
}
