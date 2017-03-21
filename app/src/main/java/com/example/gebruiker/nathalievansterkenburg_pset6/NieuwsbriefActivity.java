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

        Log.i("en waar verschijnt", "dit bericht");
        mAuth = FirebaseAuth.getInstance();
        setListener();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ingeschreven = (TextView) findViewById(R.id.ingeschreven);
        aanmelden = (Button) findViewById(R.id.aanmelden);
        afmelden = (Button) findViewById(R.id.afmelden);

        Log.i("hier ben ik", "als het goed is");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("what about here", email);
                String existsEmail = dataSnapshot.child("nieuwsbrief").child(email.replaceAll(".", "")).getValue(String.class);
                Log.i("gaat dit goed", existsEmail);

                if(existsEmail.equals("aangemeld")) {
                    ingeschreven.setText("Je bent op dit moment aangemeld voor de nieuwsbrief");
                    afmelden.setVisibility(View.VISIBLE);
                    aanmelden.setVisibility(View.GONE);
                }
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
        mDatabase.addValueEventListener(postListener);
    }

    public void setListener() {

        Log.i("nu ben ik benieuwd", "of deze tekst wel verschijnt");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.i("de user niet bestaat", user.getEmail());
                if (user != null) {
                    // User is signed in
                    Log.d("signed in", "onAuthStateChanged:signed_in:" + user.getUid());
                    Log.i("heh", user.getEmail());
                    email = user.getEmail();
                    Log.i("gaat de email goed", email);
                } else {
                    // User is signed out
                    Log.d("signed out", "onAuthStateChanged:signed_out");
                    email = "proxy@proxy.com";
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void meldAan(View view) {
        aanmelden.setVisibility(View.GONE);
        afmelden.setVisibility(View.VISIBLE);
        ingeschreven.setText("Je bent op dit moment aangemeld voor de nieuwsbrief");

        mDatabase.child("nieuwsbrief").child(email.replaceAll(".", "")).setValue("aangemeld");
    }

    public void meldAf(View view) {
        aanmelden.setVisibility(View.VISIBLE);
        afmelden.setVisibility(View.GONE);
        ingeschreven.setText("Je bent op dit moment niet aangemeld voor de nieuwsbrief");

        mDatabase.child("nieuwsbrief").child(email.replaceAll(".", "")).setValue("aangemeld");
    }

    public void backToSearch(View view) {
        startActivity(new Intent(this, SearchActivity.class));
    }
}
