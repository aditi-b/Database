package com.database;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.database.databaseclass.DataBaseManager;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    int id;                                         // to store the value of id
    EditText etname, etphone, etpassword;
    String nameGet, phoneGet, passwordGet;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etname = findViewById(R.id.editText5);
        etphone = findViewById(R.id.editText6);
        etpassword = findViewById(R.id.editText7);
        btnUpdate = findViewById(R.id.button11);

        sharedPreferences = getApplicationContext().getSharedPreferences("Register", 0);

        // getting the value of id from shared preferences
        id = sharedPreferences.getInt("ID", 0);

        btnUpdate.setOnClickListener(this);

        //cursor for fetching the column at a particular id
        Cursor cursor = DataBaseManager.getInstance().fetchData(id);
        cursor.moveToFirst();

        // setting the data on the text field
        etname.setText(cursor.getString(0));
        etphone.setText(cursor.getString(1));
        etpassword.setText(cursor.getString(2));
        cursor.close();

    }

    /**
     * To update fields in database
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button11:
                nameGet = etname.getText().toString();
                phoneGet = etphone.getText().toString();
                passwordGet = etpassword.getText().toString();

                DataBaseManager.getInstance().updateData(id, nameGet, phoneGet, passwordGet);
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
