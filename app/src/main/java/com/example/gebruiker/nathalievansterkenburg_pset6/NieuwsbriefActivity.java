package com.example.gebruiker.nathalievansterkenburg_pset6;

import android.content.Intent;
import android.support.annotation.NonNull;
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

public class NieuwsbriefActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    TextView ingeschreven;
    Button aanmelden;
    Button afmelden;
    String email = "ehm@ehm.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nieuwsbrief);

        // gets instance of database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ingeschreven = (TextView) findViewById(R.id.ingeschreven);
        aanmelden = (Button) findViewById(R.id.aanmelden);
        afmelden = (Button) findViewById(R.id.afmelden);

        // listener for changes to database
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // checks if user is "aangemeld" or not and makes appropriate widgets visible

                // if user is "aangemeld", hides "aanmelden" button and shows "afmelden"
                if(dataSnapshot.child("nieuwsbrief").hasChild(email.replaceAll(".", ""))) {
                    ingeschreven.setText("Je bent op dit moment aangemeld voor de nieuwsbrief");
                    afmelden.setVisibility(View.VISIBLE);
                    aanmelden.setVisibility(View.GONE);
                }

                // if user is "afgemeld", hides "afmelden" button and shows "aanmelden"
                else {
                    ingeschreven.setText("Je bent op dit moment niet aangemeld voor de nieuwsbrief");
                    afmelden.setVisibility(View.GONE);
                    aanmelden.setVisibility(View.VISIBLE);
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

    public void meldAan(View view) {
        // sets user to "aangemeld" in database

        // shows appropriate widgets
        aanmelden.setVisibility(View.GONE);
        afmelden.setVisibility(View.VISIBLE);
        ingeschreven.setText("Je bent op dit moment aangemeld voor de nieuwsbrief");

        // puts "aanmelding" in database
        mDatabase.child("nieuwsbrief").child(email.replaceAll(".", "")).setValue(email);
    }

    public void meldAf(View view) {
        // removes "aanmelding" from database

        // shows appropriate widgets
        aanmelden.setVisibility(View.VISIBLE);
        afmelden.setVisibility(View.GONE);
        ingeschreven.setText("Je bent op dit moment niet aangemeld voor de nieuwsbrief");

        // removes "aanmelding" from database
        mDatabase.child("nieuwsbrief").child(email.replaceAll(".", "")).removeValue();
    }

    // goes back to search activity
    public void backToSearch(View view) {
        startActivity(new Intent(this, SearchActivity.class));
    }
}
