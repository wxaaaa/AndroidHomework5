package com.example.wxaaaa.homework5;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wxaaaa.homework5.provider.DatabaseHelper;
import com.example.wxaaaa.homework5.provider.StudentProvider;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private Button classNumber;
    private Button update;
    private Button modify;
    private ProgressBar progressBar;
    private Handler handler;
    private SQLiteDatabase db;



    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        classNumber = findViewById(R.id.classNumber);
        update = findViewById(R.id.update);
        modify = findViewById(R.id.modifyClassName);

        classNumber.setOnClickListener(new ButtonListener(ClassNumberActivity.class));

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newClassName = findViewById(R.id.className);
                TextView textView = findViewById(R.id.textView);
                textView.setText(newClassName.getText() + "班管理系统demo");
            }
        });

        // 创建数据库
        db = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
        initData();

    }

    public void initData(){
        Cursor cursor = db.rawQuery("select * from student", null);
        if (cursor.getCount() == 0) {
            setData("张三3", "13", "身高6米6，体重几百斤");
            setData("二愣子", "15", "励志好好学习，天天向上");
        }
    }

    public void setData(String name, String age, String describe){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        values.put("describe", describe);
        db.insert(StudentProvider.STUDENTS_TABLE_NAME, null, values);
    }

    class ButtonListener implements View.OnClickListener {

        private Class clazz;

        ButtonListener(Class clazz) {
            this.clazz = clazz;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, clazz);
            startActivity(intent);
        }
    }
}
