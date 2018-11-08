package com.example.wxaaaa.homework5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.widget.Toast;

public class PreferencesActivity extends PreferenceActivity {

    public static String[] wifiItems = new String[]{"免费wifi1", "免费wifi2", "免费wifi3", "免费wifi4"};
    public static String[] bluetoothItems = new String[]{"开", "门", "大", "吉"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_preferences);

//        addPreferencesFromResource(R.xml.preference);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
        private Preference wifi_setting;
        private SwitchPreference wifi;
        private SwitchPreference set4G;
        private SwitchPreference vpn;
        private SwitchPreference bluetooth;
        private Preference bluetooth_setting;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            getView().setBackgroundColor(Color.BLACK);
            addPreferencesFromResource(R.xml.preference);

            wifi = (SwitchPreference) findPreference("wifi");
            wifi.setOnPreferenceChangeListener(this);
            wifi_setting = findPreference("wifi_setting");
            wifi_setting.setOnPreferenceClickListener(this);
            set4G = (SwitchPreference) findPreference("set4G");
            set4G.setOnPreferenceChangeListener(this);
            vpn = (SwitchPreference) findPreference("vpn");
            vpn.setOnPreferenceChangeListener(this);
            bluetooth = (SwitchPreference) findPreference("bluetooth");
            bluetooth.setOnPreferenceChangeListener(this);
            bluetooth_setting = findPreference("bluetooth_setting");
            bluetooth_setting.setOnPreferenceClickListener(this);



        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            System.out.println(preference);
            if (preference.getKey().equals("wifi_setting")) {
                AlertDialog dialog = new AlertDialog.Builder(this.getContext()).setTitle("选择一个wifi")
                        .setSingleChoiceItems(wifiItems, -1, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(this.getContext(), wifiItems[which], Toast.LENGTH_SHORT).show();
                                wifi_setting.setSummary("已连接wifi:" + wifiItems[which]);
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
            if (preference.getKey().equals("bluetooth_setting")){
                AlertDialog dialog = new AlertDialog.Builder(this.getContext()).setTitle("选择一台设备进行连接")
                        .setSingleChoiceItems(bluetoothItems, -1, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(this.getContext(), wifiItems[which], Toast.LENGTH_SHORT).show();
                                bluetooth_setting.setSummary("已连接蓝牙:" + bluetoothItems[which]);
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
            return true;
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (newValue instanceof Boolean){
                if ((boolean) newValue) {
                    Toast.makeText(this.getContext(), preference.getTitle() + "已启用", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this.getContext(), preference.getTitle() + "已禁用", Toast.LENGTH_LONG).show();
                    if (preference.equals(wifi)){
                        wifi_setting.setSummary("设置和管理无线接入点");
                    }
                    if (preference.equals(bluetooth)){
                        bluetooth_setting.setSummary("管理连接、设备设备名称和可检测性");
                    }
                }

            }
            return true;
        }
    }
}
