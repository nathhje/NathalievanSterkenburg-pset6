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

public class InfoAsyncTask extends AsyncTask<String, Integer, String> {

    Context context;
    SearchActivity mainAct;

    // context and mainAct are initialized
    public InfoAsyncTask(SearchActivity main) {
        this.mainAct = main;
        this.context = this.mainAct.getApplicationContext();
    }

    // let's user know program is waiting for result
    protected void onPreExecute() {
        Toast.makeText(context, "retrieving info", Toast.LENGTH_SHORT).show();
    }

    // information is retrieved
    protected String doInBackground(String... parameters) {

        try {
            Log.i("dus ik", "kom hier");
            // with use of HttpRequestHelper
            return HttpRequestHelper.downloadFromServer(parameters);
        } catch (MalformedURLException e) {
            Log.i("maar mss", "ook hier");
            e.printStackTrace();
        }

        Log.i("als ik hier zou komen", "zou er geen fout zijn");
        return "";
    }

    // processes result from HttpRequestHelper
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // the information on the school
        String school = "error";
        String niveaus = "error";
        String plaats = "error";
        String adres = "error";
        String website = "error";
        String nummer = "error";


        try {

            // the result is put in a JSONObject
            JSONObject schoolsearch = new JSONObject(result);

            // the useful information is extracted from "results"
            JSONArray inschool = schoolsearch.getJSONArray("results");
            JSONObject inschoolsearch = inschool.getJSONObject(0);

            // all pieces of information are stored in a string
            school = inschoolsearch.getString("INSTELLINGSNAAM");
            niveaus = inschoolsearch.getString("ONDERWIJSSTRUCTUUR");
            plaats = inschoolsearch.getString("PLAATSNAAM");
            adres = inschoolsearch.getString("STRAATNAAM") + " " +
                    inschoolsearch.getString("HUISNUMMER-TOEVOEGING");
            website = inschoolsearch.getString("INTERNETADRES");
            nummer = inschoolsearch.getString("TELEFOONNUMMER");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // the new activity is started with the data
        this.mainAct.detailsStartIntent(school, niveaus, plaats, adres, website, nummer);
    }
}
