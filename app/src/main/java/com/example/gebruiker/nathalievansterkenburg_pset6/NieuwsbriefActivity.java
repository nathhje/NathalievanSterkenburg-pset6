package com.example.gebruiker.nathalievansterkenburg_pset6;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NieuwsbriefActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //private DatabaseReference mDatabase;

    TextView ingeschreven;
    Button aanmelden;
    Button afmelden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nieuwsbrief);

        mAuth = FirebaseAuth.getInstance();
        setListener();

        ingeschreven = (TextView) findViewById(R.id.ingeschreven);
        aanmelden = (Button) findViewById(R.id.aanmelden);
        afmelden = (Button) findViewById(R.id.afmelden);
    }

    public void setListener() {

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("signed in", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("signed out", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    public void meldAan(View view) {
        aanmelden.setVisibility(View.GONE);
        afmelden.setVisibility(View.VISIBLE);
        ingeschreven.setText("Je bent op dit moment aangemeld voor de nieuwsbrief");
    }

    public void meldAf(View view) {
        aanmelden.setVisibility(View.VISIBLE);
        afmelden.setVisibility(View.GONE);
        ingeschreven.setText("Je bent op dit moment niet aangemeld voor de nieuwsbrief");
    }
}
