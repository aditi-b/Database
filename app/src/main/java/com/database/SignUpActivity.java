package com.database;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.database.databaseclass.DataBaseManager;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etPassword, etPhone, etConfirmPassword;         // to get the content in the edit text fields
    Button btnSignUp;                                                // signup button
    String nameget, phoneget, passwordget, confirmPassword;
    TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = findViewById(R.id.button3);

        etName = findViewById(R.id.editText1);
        etPhone = findViewById(R.id.editText2);
        etPassword = findViewById(R.id.editText3);
        etConfirmPassword = findViewById(R.id.editText4);

        btnSignUp.setOnClickListener(this);

        textInputLayout = findViewById(R.id.textinput1);
        etPassword.addTextChangedListener(textWatcher);

    }

    /**
     * Text watcher for checking the minimum password length
     */

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s.length() < 8) {
                textInputLayout.setError("Password should be minimum of 8 characters");
            } else {
                textInputLayout.setError(null);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.button3:
                nameget = etName.getText().toString();
                phoneget = etPhone.getText().toString();
                passwordget = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();

                // checking for null fields
                if (nameget.matches("") || phoneget.matches("") || passwordget.matches("")) {
                    Toast.makeText(this, getString(R.string.enter_fields), Toast.LENGTH_SHORT).show();
                }

                // checking for password fileds to be equal
                else if (!passwordget.equals(confirmPassword)) {
                    Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }

                // checking phone length to be equal to 10
                else if (phoneget.length() < 10) {
                    Toast.makeText(this, getString(R.string.phone_length), Toast.LENGTH_SHORT).show();
                }

                else {
                    //  To move to MainActivity on button click

                    insertData();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                    break;
                }


        }

    }

    /**
     * Inserting data in database
     */

    private void insertData() {
        nameget = etName.getText().toString();
        phoneget = etPhone.getText().toString();
        passwordget = etPassword.getText().toString();
        DataBaseManager.getInstance().insertData(nameget, phoneget, passwordget);
        Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();

    }
}
