package com.example.gebruiker.nathalievansterkenburg_pset6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // the firebase
    private FirebaseAuth mAuth;

    String email;
    String password;
    TextView inlogError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // used to communicate what went wrong with user
        inlogError = (TextView) findViewById(R.id.inlogerror);

        // instance of authentication is retrieved
        mAuth = FirebaseAuth.getInstance();
    }

    public void createUser(View view) {
        // creates new user

        EditText emailBox = (EditText) findViewById(R.id.email);
        EditText passwordBox = (EditText) findViewById(R.id.password);

        // user data is retrieved from EditTexts
        email = emailBox.getText().toString();
        password = passwordBox.getText().toString();

        // handles lack of user input
        if(TextUtils.isEmpty(email)) {
            email = " ";
        }
        if(TextUtils.isEmpty(password)) {
            password = " ";
        }

        // attempt at creating new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("creating user", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // informs user creation was unsuccesful
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            // message to user varies depending on error made
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

                        // informs user that creation was succesful
                        else {
                            Toast.makeText(MainActivity.this, "created: " + email,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void logIn(View view) {

        EditText loginEmail = (EditText) findViewById(R.id.loginemail);
        EditText loginPassword = (EditText) findViewById(R.id.loginpassword);

        // user data is retrieved from EditTexts
        email = loginEmail.getText().toString();
        password = loginPassword.getText().toString();

        // handles lack of user input
        if(TextUtils.isEmpty(email)) {
            Log.i("ff", "checken");
            email = " ";
        }
        if(TextUtils.isEmpty(password)) {
            Log.i("ff", "checkuh");
            password = " ";
        }

        // attempts to sign in user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("logging in", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // informs user sign in was unsuccesful
                        if (!task.isSuccessful()) {

                            Log.w("failed to log in", "signInWithEmail", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            // informs user what went wrong
                            inlogError.setText("Fout emailadres of wachtwoord");
                        }

                        // informs user sign in was succesful
                        else {
                            Toast.makeText(MainActivity.this, "signed in: " + email,
                                    Toast.LENGTH_SHORT).show();

                            // continues to search screen
                            startSearch();
                        }

                        // ...
                    }
                });
    }

    public void startSearch() {

        // intent is created
        Intent intent = new Intent(this, SearchActivity.class);

        // ensures all schools are listed when activity is started
        intent.putExtra("search", "");

        startActivity(intent);
    }

}
