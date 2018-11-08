package com.example.wxaaaa.homework5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabsActivity extends android.app.TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tab);

        TabHost tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("list").setIndicator("班级信息")
                .setContent(new Intent(this, MainActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("list").setIndicator("本机设置")
                .setContent(new Intent(this, PreferencesActivity.class)));
    }
}
