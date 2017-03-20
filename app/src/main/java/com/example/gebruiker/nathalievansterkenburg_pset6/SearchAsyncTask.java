package com.example.gebruiker.nathalievansterkenburg_pset6;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Gebruiker on 20-3-2017.
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

    // movies are retrieved
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

        // the information on the movie
        ArrayList<String> schools = new ArrayList<String>();

        try {

            // the result is put in a JSONObject
            JSONObject schoolsearch = new JSONObject(result);

            // the useful information is extracted from "search"
            JSONArray inschoolsearch = schoolsearch.getJSONArray("results");

            // the information is put in lists
            for (int i = 0; i < inschoolsearch.length(); i++) {

                // each result is in turn extracted from the array
                JSONObject listitem = inschoolsearch.getJSONObject(i);

                // all pieces of information are stored in their own array
                String school = listitem.getString("INSTELLINGSNAAM");
                schools.add(school);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // the new activity is started with the information
        this.searchAct.makeSchoolAdapter(schools);
    }
}
