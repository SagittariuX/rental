package com.example.ilove.rental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewDetails extends AppCompatActivity implements View.OnClickListener {
    /*
    Test - is where i do the practice
    Rental - is actual data is stored
     */
    private String version ;
    private TextView houseNumber,amountPaid, datePaid, collector,notes, dateCreated, dateEdited;
    private ImageButton delete, edit;

    private Record toBeViewed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        Intent i = getIntent();//receive anything from LongView
        toBeViewed = i.getParcelableExtra("record");
        version = ((MyGlobals)this.getApplication()).getMode();

        houseNumber = (TextView)findViewById(R.id.viewDetails_HouseNumberAndRoomNumber);
        amountPaid = (TextView)findViewById(R.id.viewDetails_AmountPaid);
        collector = (TextView)findViewById(R.id.viewDetails_Collector);
        datePaid = (TextView)findViewById(R.id.viewDetails_DatePaid);
        notes = (TextView)findViewById(R.id.viewDetails_Notes);
        dateCreated = (TextView)findViewById(R.id.viewDetails_DateCreated);
        dateEdited = (TextView)findViewById(R.id.viewDetails_DateEdited);
        delete = (ImageButton) findViewById(R.id.imageButtonDelete);
        edit = (ImageButton) findViewById(R.id.imageButtonEdit);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);




        if(toBeViewed!=null){

            houseNumber.setText(toBeViewed.getHouseNumber()+ " Rm: " +toBeViewed.getRoomNumber());
            amountPaid.setText("Paid $"+toBeViewed.getAmountPaid()+" "+toBeViewed.getMethodOfPayment());
            Log.d("mydebug",""+toBeViewed.getAmountPaid());
            collector.setText("Collected by "+toBeViewed.getCollector());

            datePaid.setText("Paid for "+ toBeViewed.getMonth()+", "+ toBeViewed.getYear());
            notes.setText(toBeViewed.getNotes());
            dateCreated.setText("Created on: "+toBeViewed.getDateRecorded());
            dateEdited.setText("Edited on: "+toBeViewed.getDateEdited());
        }





    }
    @Override
    public void onClick(View v) {
        if(v == delete) deleteRecord(toBeViewed);
        else if(v == edit) updateRecord(toBeViewed);
    }
    /*
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent open = new Intent(getApplicationContext(),LongView.class);
        startActivity(open);
    }
    */
    public void deleteRecord(Record r){
        FirebaseDatabase.getInstance().getReference(version).child(r.generatePath()).child(r.getId()).removeValue();
        finish();
    }

    public void updateRecord(Record r){
        Intent open = new Intent(getApplicationContext(),EditView.class);
        open.putExtra("record", r);
        startActivity(open);
    }



}
