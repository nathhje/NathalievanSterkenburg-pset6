package com.example.gebruiker.nathalievansterkenburg_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String email = user.getEmail();

        TextView showEmail = (TextView) findViewById(R.id.useremail);
        showEmail.setText("Ingelogd als " + email);
    }

    public void searchSchools(View view) {
        EditText searchBox = (EditText) findViewById(R.id.search);
        String search = searchBox.getText().toString();
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

        // when list item is clicked, additional info for that movie is retrieved
        final InfoAsyncTask asyncTask = new InfoAsyncTask(this);
        thelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // info must be retrieved for right ID
                asyncTask.execute("Instellingsnaam=" + schools.get(position).replaceAll(" ", "_"));
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

        // InfoActivity
        startActivity(infoIntent);
    }

    public void startNieuwsbrief(View view) {
        startActivity(new Intent(this, NieuwsbriefActivity.class));
    }

}
