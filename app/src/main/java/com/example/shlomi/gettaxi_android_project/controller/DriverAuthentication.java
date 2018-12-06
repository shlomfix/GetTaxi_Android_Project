package com.example.shlomi.gettaxi_android_project.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shlomi.gettaxi_android_project.R;
import com.example.shlomi.gettaxi_android_project.model.datasource.FactoryDataBase;
import com.example.shlomi.gettaxi_android_project.model.datasource.IDataBase;

public class DriverAuthentication extends AppCompatActivity {


    //region Fields
    IDataBase dataBase;
    Button loginBtn;
    EditText emailEditText;
    EditText passwordEditText;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_authentication);
        initializeFields();
    }

    protected void initializeFields(){
        dataBase = FactoryDataBase.getDataBase();
        loginBtn = (Button)findViewById(R.id.loginBtn);
        emailEditText = (EditText) findViewById(R.id.emailEditText_DriverAuthentication);
        passwordEditText = (EditText) findViewById(R.id.myPasswordEditText_DriverAuthentication);
    }

    public void loginOnClick(View view){
        try {


            loginBtn.setEnabled(false);
            IDataBase.Action action = new IDataBase.Action() {
                @Override
                public void onSuccess() {
                    loginBtn.setEnabled(true);
                    Toast.makeText(getBaseContext(), "ההתחברת בהצלחה", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Exception exception) {
                    loginBtn.setEnabled(true);
                    Toast.makeText(getBaseContext(), exception.getMessage().toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onProgress(String status, double percent) {
                    if (percent != 100)
                        loginBtn.setEnabled(false);
                }
            };
            dataBase.isValidDriverAuthentication(emailEditText.getText().toString(), passwordEditText.getText().toString(), action);
        }catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
            loginBtn.setEnabled(true);
        }
    }

    public void toSignUpActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }
}
