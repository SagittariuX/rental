package com.example.ilove.rental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText email, password, doubleCheck;
    private Button register;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        email = (EditText)findViewById(R.id.email);
        password = (EditText) findViewById(R.id.register_password);
        doubleCheck = (EditText)findViewById(R.id.doubleCheck);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

    }

    private void registerUser(){
        String emailString = email.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        String doubleCheckString = doubleCheck.getText().toString().trim();
        if (TextUtils.isEmpty(emailString)){
            //if email is empty
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwordString)){
            //if password is empty
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(doubleCheckString)){
            //if password recheck is empty
            Toast.makeText(this, "Please enter the password again", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!passwordString.equals(doubleCheckString)){
            //if the password and double check are not the same
            Toast.makeText(this, "The passwords are not the same", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(emailString,passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "Create Success");

                            Toast.makeText(Register.this, "Authentication Success", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent open = new Intent(getApplicationContext(),MainMain.class);
                            startActivity(open);
                        }
                        else{
                            Log.w(TAG, "Create Failure");
                            Toast.makeText(Register.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }


    @Override
    public void onClick(View v) {
        if (v==register){
            registerUser();
        }
    }
}
