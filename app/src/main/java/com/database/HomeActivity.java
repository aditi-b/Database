package com.database;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.database.databaseclass.DataBaseManager;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    TextView tvName, tvPhone, tvPassword;
    int id;                                             // int to store the value of id
    SharedPreferences.Editor editor;
    Button btnEdit, btnLogout, btnDelete;               // button for edit, logout and delete


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvName = findViewById(R.id.textView7);
        tvPhone = findViewById(R.id.textView8);
        tvPassword = findViewById(R.id.textView9);
        btnEdit = findViewById(R.id.button5);
        btnLogout = findViewById(R.id.button4);
        btnDelete = findViewById(R.id.button6);

        btnEdit.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        sharedPreferences = getApplicationContext().getSharedPreferences("Register",0);
        id = sharedPreferences.getInt("ID", 0);

        // cursor to fetch the row from the database
        Cursor cursor= DataBaseManager.getInstance().fetchData(id);
        cursor.moveToFirst();

        // setting the data in the text field
            tvName.setText(cursor.getString(0));
            tvPhone.setText(cursor.getString(1));
            tvPassword.setText(cursor.getString(2));


             cursor.close();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button4:                        // to clear the shared preference on log out
                editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.button6:                       // to delete a user
                DataBaseManager.getInstance().deleteRecord(id);
                editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                finish();
                break;



            case R.id.button5:                       //  to move to EditActivity
                Intent in = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(in);
                finish();
                break;

        }

    }
}
