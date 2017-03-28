package com.example.gebruiker.nathalievansterkenburg_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        // email of current user is retrieved
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        // email is displayed to inform which user is signed in
        TextView showEmail = (TextView) findViewById(R.id.useremail);
        showEmail.setText("Ingelogd als " + email);

        // initial list is created upon starting activity
        searchBox = (EditText) findViewById(R.id.search);
        searchBox.setText(getIntent().getStringExtra("search"));
        String urlSearch = getIntent().getStringExtra("search");

        // SearchAsyncTask is executed to retrieve schools and create initial list
        SearchAsyncTask asyncTask = new SearchAsyncTask(this);
        asyncTask.execute("plaatsnaam=" + urlSearch);
    }

    public void searchSchools(View view) {
        // searches for schools in place from EditText

        // the place is adjusted to fit in url
        search = searchBox.getText().toString();
        String urlSearch = search.replaceAll(" ", "-");

        // SearchAsyncTask is executed to retrieve schools and create list
        SearchAsyncTask asyncTask = new SearchAsyncTask(this);
        asyncTask.execute("plaatsnaam=" + urlSearch);
    }

    public void makeSchoolAdapter(final ArrayList<String> schools) {
        // sets adapter to retrieved list

        // adapter is initialized
        ArrayAdapter adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, schools);

        // ListView is initialized and set to adapter
        ListView thelist = (ListView) findViewById(R.id.listofschools);
        assert thelist != null;
        thelist.setAdapter(adapter);

        // when list item is clicked, additional info for that school is retrieved
        final InfoAsyncTask asyncTask = new InfoAsyncTask(this);
        thelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // info must be retrieved for right school
                asyncTask.execute("instellingsnaam=" + schools.get(position).replaceAll(" ", "-"));
            }
        });
    }


    public void detailsStartIntent(School school) {
        // opens InfoActivity when InfoAsyncTask is done

        Intent infoIntent = new Intent(this, InfoActivity.class);

        // all info is put into intent
        infoIntent.putExtra("school", school);

        // remember what user searched for to recreate list upon going back to activity
        infoIntent.putExtra("search", search);

        startActivity(infoIntent);
    }

    public void startNieuwsbrief(View view) {
        // starts new activity when clicked
        startActivity(new Intent(this, NieuwsbriefActivity.class));
    }

    // nothing happens when BackPressed
    public void onBackPressed() {}

}
