package com.example.gebruiker.nathalievansterkenburg_pset6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    String email = "kort";
    String password = "kort";
    EditText emailBox;
    EditText passwordBox;
    EditText loginEmail;
    EditText loginPassword;
    TextView inlogError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inlogError = (TextView) findViewById(R.id.inlogerror);

        mAuth = FirebaseAuth.getInstance();

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, SearchActivity.class));
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
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

    public void createUser(View view) {

        EditText emailBox = (EditText) findViewById(R.id.email);
        EditText passwordBox = (EditText) findViewById(R.id.password);

        email = emailBox.getText().toString();
        password = passwordBox.getText().toString();

        Log.i("er gebeurt iets raars", "met het wachtwoord");
        Log.i("deze shit", password);
        
        if(TextUtils.isEmpty(email)) {
            Log.i("ff", "checken");
            email = " ";
        }
        if(TextUtils.isEmpty(password)) {
            Log.i("ff", "checkuh");
            password = " ";
        }

        Log.i("track", email);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("creating user", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            if(password.length()<6){
                                inlogError.setText("Wachtwoord moet uit minstens 6 tekens bestaan");
                            }
                            if(!email.contains("@") || !email.contains(".")){
                                inlogError.setText("Voer een geldig emailadres in");
                            }
                            else {
                                inlogError.setText("Emailadres of wachtwoord is al in gebruik");
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "created: " + email,
                                    Toast.LENGTH_SHORT).show();
                            //mDatabase.child("nieuwsbrief").child(email.replaceAll(".", "")).setValue("afgemeld");
                        }

                        // ...
                    }
                });
    }

    public void logIn(View view) {

        loginEmail = (EditText) findViewById(R.id.loginemail);
        loginPassword = (EditText) findViewById(R.id.loginpassword);

        email = loginEmail.getText().toString();
        password = loginPassword.getText().toString();

        if(TextUtils.isEmpty(email)) {
            Log.i("ff", "checken");
            email = " ";
        }
        if(TextUtils.isEmpty(password)) {
            Log.i("ff", "checkuh");
            password = " ";
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("logging in", "signInWithEmail:onComplete:" + task.isSuccessful());

                        Log.i("ik ben hier", "en de taak is succesvol");
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("failed to log in", "signInWithEmail", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            inlogError.setText("Fout emailadres of wachtwoord");
                        }
                        else {
                            Log.i("maar kom", "ik hier nog");
                            Toast.makeText(MainActivity.this, "signed in: " + email,
                                    Toast.LENGTH_SHORT).show();
                            startSearch();
                        }

                        // ...
                    }
                });
    }

    public void startSearch() {

        Log.i("is dit", "het?");

        Log.i("hier is alles", "nog oke");

        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("search", " ");

        Log.i("maar anders", "zit het hier");
        startActivity(intent);
    }

}
