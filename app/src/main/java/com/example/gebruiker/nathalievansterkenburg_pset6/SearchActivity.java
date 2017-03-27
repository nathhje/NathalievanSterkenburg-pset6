package com.example.gebruiker.nathalievansterkenburg_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText searchBox;
    String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Log.i("het lijkt of ik hier", "niet kom");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String email = user.getEmail();

        TextView showEmail = (TextView) findViewById(R.id.useremail);
        showEmail.setText("Ingelogd als " + email);


        searchBox = (EditText) findViewById(R.id.search);
        searchBox.setText(getIntent().getStringExtra("search"));
        String urlSearch = getIntent().getStringExtra("search");

        SearchAsyncTask asyncTask = new SearchAsyncTask(this);
        asyncTask.execute("plaatsnaam=" + urlSearch);
    }

    public void searchSchools(View view) {
        search = searchBox.getText().toString();
        String urlSearch = search.replaceAll(" ", "_");

        SearchAsyncTask asyncTask = new SearchAsyncTask(this);
        asyncTask.execute("plaatsnaam=" + urlSearch);
    }

    public void makeSchoolAdapter(final ArrayList<String> schools) {

        // adapter is initialized
        ArrayAdapter adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, schools);

        // ListView is initialized and set to adapter
        ListView thelist = (ListView) findViewById(R.id.listofschools);
        assert thelist != null;
        thelist.setAdapter(adapter);

        Log.i("ik denk", "hier te komen");

        // when list item is clicked, additional info for that movie is retrieved
        final InfoAsyncTask asyncTask = new InfoAsyncTask(this);
        thelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // info must be retrieved for right ID
                asyncTask.execute("instellingsnaam=" + schools.get(position).replaceAll(" ", "-"));
            }
        });
    }

    // opens InfoActivity when asyncTask is done
    public void detailsStartIntent(String school, String niveaus, String plaats, String adres,
                                   String website, String nummer) {

        Intent infoIntent = new Intent(this, InfoActivity.class);

        infoIntent.putExtra("school", school);
        infoIntent.putExtra("niveaus", niveaus);
        infoIntent.putExtra("plaats", plaats);
        infoIntent.putExtra("adres", adres);
        infoIntent.putExtra("website", website);
        infoIntent.putExtra("nummer", nummer);
        infoIntent.putExtra("search", search);

        // InfoActivity
        startActivity(infoIntent);
    }

    public void startNieuwsbrief(View view) {
        startActivity(new Intent(this, NieuwsbriefActivity.class));
    }

    public void onBackPressed() {}

}
