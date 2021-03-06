package com.example.gebruiker.nathalievansterkenburg_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoActivity extends AppCompatActivity {

    // the firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    TextView theSchool;
    TextView niveaus;
    TextView plaats;
    TextView adres;
    TextView website;
    TextView nummer;

    String rememberPlace;
    String email;
    Button aanmelden;
    TextView aangemeld;
    School school = new School();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // gets instance of authentication
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();

        // gets instance of database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // info on school is stored in intent
        final Intent theIntent = getIntent();
        school = (School) theIntent.getSerializableExtra("school");

        // puts school info on screen
        setInfo();

        // the last thing the user searched, so they can return there
        rememberPlace = theIntent.getStringExtra("search");

        // sets listener for changes to database
        watchForChanges(theIntent, school);
    }

    public void setInfo() {
        // puts school info on screen

        aanmelden = (Button) findViewById(R.id.meeloopaanmelding);
        aangemeld = (TextView) findViewById(R.id.aangemeldmeelopen);

        theSchool = (TextView) findViewById(R.id.school);
        niveaus = (TextView) findViewById(R.id.niveaus);
        plaats = (TextView) findViewById(R.id.plaats);
        adres = (TextView) findViewById(R.id.adres);
        website = (TextView) findViewById(R.id.website);
        nummer = (TextView) findViewById(R.id.nummer);

        // info is put in TextViews
        theSchool.setText(school.getSchool());
        niveaus.setText("Niveaus: " + school.getNiveaus());
        plaats.setText("Plaats: " + school.getPlaats());
        adres.setText("Adres: " + school.getAdres());
        website.setText("Website: " + school.getWebsite());
        nummer.setText("Telefoonnummer: " + school.getNummer());
    }

    public void watchForChanges(final Intent theIntent, final School school){
        // listener for changes to database

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // hides "aanmelden" button when user is "aangemeld"

                // checks if user is "aangemeld"
                if(dataSnapshot.child(school.getSchool()).hasChild(email.replaceAll("\\.", ""))) {
                    aangemeld.setVisibility(View.VISIBLE);
                    aanmelden.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("uhm", "something went wrong", databaseError.toException());
            }
        };

        // listener is set
        mDatabase.addValueEventListener(postListener);
    }

    public void meeloopMeldAan(View view) {
        // puts email inside the school in database to indicate "aangemeld"

        aanmelden.setVisibility(View.GONE);
        aangemeld.setVisibility(View.VISIBLE);

        // the item is put in the database
        String theChild = email.replaceAll("\\.", "");
        mDatabase.child(school.getSchool()).child(theChild).setValue(email);
    }

    @Override
    public void onBackPressed() {

        // goes back to previous search on Back Pressed
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("search", rememberPlace);
        startActivity(intent);
    }

}
