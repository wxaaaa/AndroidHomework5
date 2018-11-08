package com.example.wxaaaa.homework5;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.wxaaaa.homework5.provider.StudentProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassNumberActivity extends AppCompatActivity {

    private List<Map<String, Object>> itemList;

    private Button addButton;
    private Button searchButton;
    private EditText searchName;
    ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_number);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        OutOnclickListener outOnclickListener = new OutOnclickListener();
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(outOnclickListener);
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(outOnclickListener);
        resolver = getContentResolver();
        searchName = findViewById(R.id.searchName);

        showStudentList();
        setAdapter();
    }

    protected void onResume() {
        super.onResume();
        // 更新列表;
        showStudentList();
        setAdapter();
    }

    private void showStudentList(){
        itemList = new ArrayList<>();

        Cursor result = resolver.query(StudentProvider.CONTENT_URI, null, null, null, "name");
        result.moveToFirst();
        do {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("id", result.getInt(result.getColumnIndex("id")));
            listItem.put("name", result.getString(result.getColumnIndex("name")));
            listItem.put("age", result.getString(result.getColumnIndex("age")));
            listItem.put("describe", result.getString(result.getColumnIndex("describe")));
            itemList.add(listItem);
        } while (result.moveToNext());
    }

    private void setAdapter(){
        SimpleAdapter adapter = new MySimpleAdapter(this, itemList, R.layout.item_layout,
                new String[]{"name", "age", "describe"}, new int[]{R.id.name, R.id.age, R.id.describe});
        ListView list = findViewById(R.id.numberList);
        list.setAdapter(adapter);
    }

    private class MySimpleAdapter extends SimpleAdapter{

        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            Button modifyButton = v.findViewById(R.id.modifyButton);
            modifyButton.setTag(position);
            modifyButton.setOnClickListener(onClickListener);
            Button deleteButton = v.findViewById(R.id.deleteButton);
            deleteButton.setTag(position);
            deleteButton.setOnClickListener(onClickListener);
            return v;
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer index = Integer.parseInt(String.valueOf(v.getTag()));
                if (v.getId() == R.id.modifyButton){
                    Intent intent = new Intent();
                    intent.putExtra("id", (Integer) itemList.get(index).get("id"))
                    .putExtra("name", (String) itemList.get(index).get("name"))
                    .putExtra("age", (String) itemList.get(index).get("age"))
                    .putExtra("describe", (String) itemList.get(index).get("describe"));
                    intent.setClass(ClassNumberActivity.this, StudentModifyActivity.class);
                    startActivity(intent);
                }
                else if (v.getId() == R.id.deleteButton){
                    resolver.delete(StudentProvider.CONTENT_URI, "id=?", new String[]{String.valueOf(itemList.get(index).get("id"))});
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_LONG).show();
                    onResume();
                }
            }
        };
    }

    class OutOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.addButton){
                Intent intent = new Intent();
                intent.setClass(ClassNumberActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
            else if (v.getId() == R.id.searchButton){
                String nameLike = searchName.getText().toString();
                showStudentList();
                for (int i = 0; i < itemList.size(); i++) {
                    if (!((String) itemList.get(i).get("name")).contains(nameLike)){
                        itemList.remove(i);
                        i--;
                    }
                }
                setAdapter();
            }
        }
    };
}
