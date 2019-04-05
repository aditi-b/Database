package com.database;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.database.databaseclass.DataBaseManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignIn, btnSignUp;                    //buttons for signin and signup
    EditText etName, etPassword;
    String getName, getPassword;
    SharedPreferences sharedPreferences;            //shared preference for storing the id of the user
    SharedPreferences.Editor editor;                // editor to edit the shared preference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.button);
        btnSignUp = findViewById(R.id.button2);
        etName = findViewById(R.id.editText11);
        etPassword = findViewById(R.id.editText12);

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);


        // making a file for shared preference and opening it in private mode
        sharedPreferences = getApplicationContext().getSharedPreferences("Register", 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // fetching the data from database
            case R.id.button:
                    getName = etName.getText().toString();
                    getPassword = etPassword.getText().toString();
                fetch();
                break;

                // Moving to SignupActivity if the user does not exist

            case R.id.button2:
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
                finish();
                break;

        }
    }

    /**
     * fetching the data and showing it on HomeActivity
     */
    public void fetch() {
        Cursor c = DataBaseManager.getInstance().fetchId(getName, getPassword);
        c.moveToFirst();

        // checking if the cursor returns value less than or equal to zero
        if (c.getCount() <= 0) {
            Toast.makeText(this, R.string.invalid_user, Toast.LENGTH_SHORT).show();
        }


        else {

            // storing the id in sharedpreferences
            editor = sharedPreferences.edit();
            editor.putInt("ID", c.getInt(0));
            editor.apply();

            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        }
    }
}


