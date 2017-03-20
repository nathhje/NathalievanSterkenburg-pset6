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
 * Created by Gebruiker on 20-3-2017.
 */

public class InfoAsyncTask extends AsyncTask<String, Integer, String> {

    Context context;
    SearchActivity mainAct;
    String ID;

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

        // the information on the movie
        String school = "error";
        String niveaus = "error";
        String plaats = "error";
        String adres = "error";
        String website = "error";
        String nummer = "error";


        try {

            // the result is put in a JSONObject
            JSONObject schoolsearch = new JSONObject(result);

            // the useful information is extracted from "search"
            JSONObject inschoolsearch = schoolsearch.getJSONObject("results");

            school = inschoolsearch.getJSONObject("INSTELLINGSNAAM").toString();
            niveaus = inschoolsearch.getJSONObject("ONDERWIJSSTRUCTUUR").toString();
            plaats = inschoolsearch.getJSONObject("PLAATSNAAM").toString();
            adres = inschoolsearch.getJSONObject("STRAATNAAM").toString() +
                    inschoolsearch.getJSONObject("HUISNUMMER-TOEVOEGING").toString();
            website = inschoolsearch.getJSONObject("INTERNETADRES").toString();
            nummer = inschoolsearch.getJSONObject("TELEFOONNUMMER").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // the new activity is started with the data
        this.mainAct.detailsStartIntent(school, niveaus, plaats, adres, website, nummer);
    }
}
