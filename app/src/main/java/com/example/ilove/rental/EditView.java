package com.example.ilove.rental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditView extends AppCompatActivity {

    /*
    Test - is where i do the practice
    Rental - is actual data is stored
     */


    private String version;
    private Spinner spinnerMonth, spinnerHouseNumber, spinnerYear;
    private EditText editTextRoomNumber, editTextAmountPaid, editTextCollector, editTextMethodOfPayment, editTextNotes;
    private Record toBeEdited;
    private Button buttonInsert;

    DatabaseReference databaseRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_view);

        Intent i = getIntent();//receive anything from LongView
        toBeEdited = i.getParcelableExtra("record");

        version = ((MyGlobals)this.getApplication()).getMode();
        spinnerHouseNumber = (Spinner) findViewById(R.id.spinnerHouseNumber);
        spinnerMonth = (Spinner)findViewById(R.id.spinnerMonth);
        editTextRoomNumber = (EditText)findViewById(R.id.editTextRoomNumber);
        editTextAmountPaid = (EditText)findViewById(R.id.editTextAmountPaid);
        editTextCollector = (EditText)findViewById(R.id.editTextCollector);
        editTextMethodOfPayment = (EditText)findViewById(R.id.editTextMethodOfPayment);
        editTextNotes = (EditText)findViewById(R.id.editTextNotes);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        buttonInsert = (Button) findViewById(R.id.buttonInsert);
        databaseRoot = FirebaseDatabase.getInstance().getReference(version);

        setupInitialSpinners();
        if(toBeEdited != null){//if user wants to edit

            setSpinText(spinnerHouseNumber,toBeEdited.getHouseNumber());
            setSpinText(spinnerMonth,toBeEdited.getMonth());
            editTextRoomNumber.setText(toBeEdited.getRoomNumber());
            setSpinText(spinnerYear,toBeEdited.getYear());
            editTextAmountPaid.setText(toBeEdited.getAmountPaid());
            editTextCollector.setText(toBeEdited.getCollector());
            editTextMethodOfPayment.setText(toBeEdited.getMethodOfPayment());
            editTextNotes.setText(toBeEdited.getNotes());
            buttonInsert.setText("Update");
        }

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRecord();
                if(toBeEdited == null) {//only reset information if user is trying to enter new entries
                                        //This way if the user makes another mistake they dont have to find the field again
                    setupInitialSpinners();
                    editTextRoomNumber.setText(null);
                    editTextAmountPaid.setText(null);
                    editTextCollector.setText(null);
                    editTextMethodOfPayment.setText(null);
                    editTextNotes.setText(null);
                }
            }
        });
    }
    private void insertRecord(){ //used for both inserting new entries and updating current ones
                                 //to update, the old record is deleted and a new record is inserted
        String houseNumber = spinnerHouseNumber.getSelectedItem().toString();
        String month = spinnerMonth.getSelectedItem().toString();
        String roomNumber = editTextRoomNumber.getText().toString().trim();
        String year = spinnerYear.getSelectedItem().toString();
        String amountPaid = editTextAmountPaid.getText().toString().trim();
        String collector = editTextCollector.getText().toString().trim();
        String methodOfPayment = editTextMethodOfPayment.getText().toString().trim();
        String notes = editTextNotes.getText().toString().trim();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference(version).child(generatePath(year,month,houseNumber));
        if(TextUtils.isEmpty(roomNumber)){
            Toast.makeText(this,"Please enter room number",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(amountPaid)){
            Toast.makeText(this, "Please enter amount of payment", Toast.LENGTH_SHORT).show();
        }
        else{
            String id;
            if(toBeEdited == null) {//inserting a new entry
                 id = database.push().getKey();
                 Record record = new Record(id,houseNumber,month,roomNumber,year,amountPaid,collector,methodOfPayment,notes,editTime());
                 database.child(id).setValue(record);
                 Toast.makeText(this, "Record added", Toast.LENGTH_SHORT).show();
            }
            else{//editing an existing entry
                //must delete the existing entry
                //and then insert a new entry with the same information
                String createdOn = toBeEdited.getDateRecorded();
                databaseRoot.child(toBeEdited.generatePath()).child(toBeEdited.getId()).removeValue();//existing record is deleted

                id = database.push().getKey();//a new entry with the same info is recorded
                toBeEdited = new Record(id,houseNumber,month,roomNumber,year,amountPaid,collector,methodOfPayment,notes,createdOn,editTime());
                database.child(id).setValue(toBeEdited);
                Toast.makeText(this, "Record edited", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private String editTime(){
        Calendar rightnow = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(rightnow.getTime());
    }

    public String generatePath (String y,String m,String h){//generates a precise path to store the records
        return y+"/"+m+"/"+h;
    }

    public void setupInitialSpinners(){
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        spinnerHouseNumber.setSelection(0);
        spinnerMonth.setSelection(calendar.get(Calendar.MONTH));
        setSpinText(spinnerYear,""+calendar.get(Calendar.YEAR));
    }

    public void setSpinText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().equals(text))
            {
                spin.setSelection(i);
            }
        }

    }



}
