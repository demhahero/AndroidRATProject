package com.example.breeder1.androidratproject;

import android.app.AppOpsManager;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvSupported, tvDataUsageWiFi, tvDataUsageMobile, tvDataUsageTotal;
    TextView note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        note = (TextView) findViewById(R.id.note);

        tvDataUsageWiFi = (TextView) findViewById(R.id.tvDataUsageWiFi);
        tvDataUsageMobile = (TextView) findViewById(R.id.tvDataUsageMobile);
        tvDataUsageTotal = (TextView) findViewById(R.id.tvDataUsageTotal);

        //Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        //startActivity(intent);

        note.setText("ssss");

        if (TrafficStats.getTotalRxBytes() != TrafficStats.UNSUPPORTED && TrafficStats.getTotalTxBytes() != TrafficStats.UNSUPPORTED) {
            handler.postDelayed(runnable, 0);
        }


    }

    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        public void run() {
            long mobile = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes();
            long total = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();
            tvDataUsageWiFi.setText("" + (total - mobile) / 1024 + " Kb");
            tvDataUsageMobile.setText("" + mobile / 1024 + " Kb");
            tvDataUsageTotal.setText("" + total / 1024 + " Kb");

            handler.postDelayed(runnable, 1000);
        }
    };
}
