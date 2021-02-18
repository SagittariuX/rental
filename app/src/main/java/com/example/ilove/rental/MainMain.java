package com.example.ilove.rental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainMain extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private FirebaseAuth mAuth;
    private ImageView editButton, viewButton, calculateButton, tenantButton;
    private Spinner databaseSelection;
    private String database;
    private boolean initalStart = true;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefEditor;
    private static final String PREF_NAME = "database";
    private static final String DATABASE_SELECTION = "database_selection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
        Log.d("Creation","Main Menu Created");

        mAuth = FirebaseAuth.getInstance();
        editButton = (ImageView)findViewById(R.id.edit);
        viewButton = (ImageView)findViewById(R.id.view);
        calculateButton = (ImageView) findViewById(R.id.calculate);
        tenantButton = (ImageView) findViewById(R.id.tenants);
        databaseSelection = (Spinner) findViewById(R.id.databaseSelection);

        sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        prefEditor = sharedPreferences.edit();

        databaseSelection.setSelection(sharedPreferences.getInt(DATABASE_SELECTION,0));
        ((MyGlobals)this.getApplication()).setMode(databaseSelection.getSelectedItem().toString());
        Log.d("Creation",databaseSelection.getSelectedItem().toString());
        Log.d("Creation",((MyGlobals)this.getApplication()).getMode());
        databaseSelection.setOnItemSelectedListener(this);

        editButton.setOnClickListener(this);
        viewButton.setOnClickListener(this);



    }




    @Override
    public void onStart(){
        super.onStart();

    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v == editButton ){
            Intent open = new Intent(getApplicationContext(),EditView.class);
            startActivity(open);

        }
        else if(v == viewButton ){
            Intent open = new Intent(getApplicationContext(),LongView.class);
            startActivity(open);

        }
        else if(v == calculateButton){
            Intent open = new Intent(getApplicationContext(), Calculate.class);
            startActivity(open);
        }
        else if(v == tenantButton ){
            
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == databaseSelection && initalStart == false){
            //Log.d("Spinner","Selection Changed");
            databaseSelection.setSelection(position);
            ((MyGlobals)this.getApplication()).setMode(databaseSelection.getSelectedItem().toString());
            //Log.d("Spinner", ""+position);
            prefEditor.putInt(DATABASE_SELECTION,position);
            prefEditor.apply();
        }
        else if(parent == databaseSelection && initalStart == true){initalStart = false;}
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

