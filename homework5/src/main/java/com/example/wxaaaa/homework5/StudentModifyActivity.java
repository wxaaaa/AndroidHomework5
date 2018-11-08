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

public class StudentModifyActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editAge;
    private EditText editDescribe;
    private Button updateButton;
    private Button cancelButton;
    private int id;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_modify);

        db = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
        OnClickListener onClickListener = new OnClickListener();

        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editDescribe = findViewById(R.id.editDescribe);
        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(onClickListener);
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(onClickListener);

        Intent intent = getIntent();
        editName.setText(intent.getStringExtra("name"));
        editAge.setText(intent.getStringExtra("age"));
        editDescribe.setText(intent.getStringExtra("describe"));
        id = intent.getIntExtra("id", 0);

    }

    class OnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.updateButton){
                ContentValues values = new ContentValues();
                values.put("name", editName.getText().toString());
                values.put("age", editAge.getText().toString());
                values.put("describe", editDescribe.getText().toString());
                db.update(StudentProvider.STUDENTS_TABLE_NAME, values, "id=?", new String[]{String.valueOf(id)});
                Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_LONG).show();
                finish();
            }
            else if (v.getId() == R.id.cancelButton){
                finish();
            }
        }
    }
}
