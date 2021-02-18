package com.example.ilove.rental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class EditTenantView extends AppCompatActivity {

    private String version ;

    Spinner spinnerHouseNumber, spinnerStartMonth, spinnerStartYear, spinnerEndMonth, spinnerEndYear;
    EditText editTextName, editTextPhone, editTextEmail, editTextNotes;
    Button submit;

    DatabaseReference databaseRoot;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tenant_view);

        version = ((MyGlobals)this.getApplication()).getMode();

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhone = (EditText) findViewById(R.id.editTextPhoneNumber);
        editTextNotes = (EditText)  findViewById(R.id.editTextNotes);

        spinnerHouseNumber = (Spinner) findViewById(R.id.spinnerHouseNumber);
        spinnerStartMonth = (Spinner) findViewById(R.id.spinnerStartMonth);
        spinnerEndMonth = (Spinner)findViewById(R.id.spinnerEndMonth);
        spinnerStartYear = (Spinner)findViewById(R.id.spinnerStartYear);
        spinnerEndYear = (Spinner) findViewById(R.id.spinnerEndYear);

        submit = (Button)findViewById(R.id.buttonInsert);

        databaseRoot = FirebaseDatabase.getInstance().getReference(version);

        setupInitialSpinners();

    }

    public void setupInitialSpinners(){
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        spinnerHouseNumber.setSelection(0);
        spinnerStartMonth.setSelection(calendar.get(Calendar.MONTH));
        spinnerEndMonth.setSelection(calendar.get(Calendar.MONTH));
        setSpinText(spinnerStartYear, ""+calendar.get(Calendar.YEAR));
        setSpinText(spinnerEndYear, ""+calendar.get(Calendar.YEAR));
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
