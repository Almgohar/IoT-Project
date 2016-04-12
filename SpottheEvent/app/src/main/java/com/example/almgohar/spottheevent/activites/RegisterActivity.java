
package com.example.almgohar.spottheevent.activites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.almgohar.spottheevent.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private EditText mail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText2);
        confirmPassword = (EditText) findViewById(R.id.editText4);
        mail = (EditText) findViewById(R.id.editText);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //if (showError(password.getText().toString(), confirmPassword.getText().toString(), mail.getText().toString())) {
            //TextView errorMsg = (TextView) findViewById(R.id.errorText);
            //errorMsg.setVisibility(View.VISIBLE);
        //}


    }

    private boolean invalidPasswordLength(String name) {
        if (name.length() > 8 && name.length() < 30)
            return false;
        else
            return true;
    }

    private boolean invalidConfrmPassword(String name, String confirm) {
        if (name.equals(confirm))
            return false;
        else
            return true;
    }

    private boolean invalidEmail(String mail) {
        if (mail.endsWith("@student.guc.edu.eg"))
            return false;
        else
            return true;
    }

    private boolean showError(String password, String confirm, String mail) {
        if (invalidPasswordLength(password) || invalidConfrmPassword(password, confirm) || invalidEmail(mail)) {
            return true;
        }
        return false;
    }

}
