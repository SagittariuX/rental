package com.example.ilove.rental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submitButton;
    private EditText username;
    private EditText password;
    private TextView register;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private CheckBox checkBoxRememberMe;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "login";
    private static final String LOGIN_CHECKED = "checked";
    private static final String LOGIN_USERNAME = "username";
    private static final String LOGIN_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton = (Button)findViewById(R.id.submit);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);

        submitButton.setOnClickListener(this);
        register.setOnClickListener(this);

        //remember the password
        checkBoxRememberMe = (CheckBox) findViewById(R.id.checkBoxRememberMe);
        sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getBoolean(LOGIN_CHECKED,false)){
            checkBoxRememberMe.setChecked(true);
        }else{
            checkBoxRememberMe.setChecked(false);
        }


        checkBoxRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rememberInfo();
            }
        });



        username.setText(sharedPreferences.getString(LOGIN_USERNAME,""));
        password.setText(sharedPreferences.getString(LOGIN_PASSWORD,""));

        mAuth = FirebaseAuth.getInstance();

    }

    private void signinUser(){
        String emailString = username.getText().toString().trim();
        String passwordString = password.getText().toString().trim();

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


        progressDialog.setMessage("Signing in User...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(emailString,passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "Sign-in Success");
                            progressDialog.dismiss();
                            rememberInfo();
                            Intent open = new Intent(getApplicationContext(),MainMain.class);
                            startActivity(open);
                        }
                        else{
                            Log.w(TAG, "Sign-in Failure");
                            progressDialog.dismiss();
                        }
                    }
                });

    }

    private void rememberInfo(){
        if(checkBoxRememberMe.isChecked()){
            editor.putBoolean(LOGIN_CHECKED,true);
            editor.putString(LOGIN_USERNAME, username.getText().toString().trim());
            editor.putString(LOGIN_PASSWORD, password.getText().toString().trim());
            editor.apply();
        }
        else if (!checkBoxRememberMe.isChecked()){
            editor.putBoolean(LOGIN_CHECKED, false);
            editor.remove(LOGIN_USERNAME);//editor.putString(KEY_PASS,"");
            editor.remove(LOGIN_PASSWORD);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }



    }

    @Override
    public void onClick(View v) {
        if (v == submitButton){
            signinUser();

        }
        if(v == register){
            Intent open = new Intent(getApplicationContext(),Register.class);
            startActivity(open);
        }
    }

}
