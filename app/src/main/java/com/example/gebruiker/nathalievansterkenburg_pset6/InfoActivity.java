package com.example.gebruiker.nathalievansterkenburg_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    TextView school;
    TextView niveaus;
    TextView plaats;
    TextView adres;
    TextView website;
    TextView nummer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent theIntent = getIntent();

        school = (TextView) findViewById(R.id.school);
        niveaus = (TextView) findViewById(R.id.niveaus);
        plaats = (TextView) findViewById(R.id.plaats);
        adres = (TextView) findViewById(R.id.adres);
        website = (TextView) findViewById(R.id.website);
        nummer = (TextView) findViewById(R.id.nummer);

        school.setText(theIntent.getStringExtra("school"));
        niveaus.setText(theIntent.getStringExtra("niveaus"));
        plaats.setText(theIntent.getStringExtra("plaats"));
        adres.setText(theIntent.getStringExtra("adres"));
        website.setText(theIntent.getStringExtra("website"));
        nummer.setText(theIntent.getStringExtra("nummer"));

    }
}
