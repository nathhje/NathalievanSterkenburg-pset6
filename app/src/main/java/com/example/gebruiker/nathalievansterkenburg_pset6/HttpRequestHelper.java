package com.example.gebruiker.nathalievansterkenburg_pset6;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Gebruiker on 20-3-2017.
 */

public class HttpRequestHelper {

    protected static synchronized String downloadFromServer(String... parameters) throws MalformedURLException {

        // important values are initialized
        String result = "";
        String searchterm = parameters[0];
        String link;

        Log.i("hier zou het", "niet fout moeten gaan");

        // link is completed
        link = "https://api.duo.nl/v0/datasets/01.-hoofdvestigingen-vo/search?" + searchterm;

        Log.i("hieraan zou het wel", "kunnen liggen");
        // and turned into a url
        URL url = new URL(link);

        HttpURLConnection connect;

        // url must exist
        if (url != null) {

            Log.i("kom ik hier?", url.toString());
            // data is retrieved
            try {
                connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");

                Log.i("en hier", "dan");
                // makes sure search was succesful
                Integer responseCode = connect.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {

                    Log.i("wat kan ik", "nog meer verzinnen");
                    // data is retrieved
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    String line;

                    Log.i("laatste", "log");
                    // data is put in result
                    while((line = bReader.readLine()) != null) {
                        result += line;
                    }
                }
            }
            catch (ProtocolException e) {
                Log.i("ik weet niet", "waarom ik hier zou komen");
                e.printStackTrace();
            }
            catch (IOException e) {
                Log.i("of waarom", "ik hier zou komen");
                e.printStackTrace();
            }
        }

        Log.i("im gonna be mad", "if its here");
        // result is returned
        return result;
    }
}
