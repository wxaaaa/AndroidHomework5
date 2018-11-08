package com.example.wxaaaa.homework5;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wxaaaa.homework5.provider.DatabaseHelper;
import com.example.wxaaaa.homework5.provider.StudentProvider;

public class AddStudentActivity extends AppCompatActivity {

    private EditText addName;
    private EditText addAge;
    private EditText addDescribe;
    private Button addButton;
    private Button cancelButton;
    private int id;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        db = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
        OnClickListener onClickListener = new OnClickListener();

        addName = findViewById(R.id.addName);
        addAge = findViewById(R.id.addAge);
        addDescribe = findViewById(R.id.addDescribe);
        addButton = findViewById(R.id.addStudentButton);
        addButton.setOnClickListener(onClickListener);
        cancelButton = findViewById(R.id.addCancelButton);
        cancelButton.setOnClickListener(onClickListener);

    }

    class OnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.addStudentButton){
                ContentValues values = new ContentValues();
                values.put("name", addName.getText().toString());
                values.put("age", addAge.getText().toString());
                values.put("describe", addDescribe.getText().toString());
                db.insert(StudentProvider.STUDENTS_TABLE_NAME, null, values);
                Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();
                finish();
            }
            else if (v.getId() == R.id.addCancelButton){
                finish();
            }
        }
    }
}
