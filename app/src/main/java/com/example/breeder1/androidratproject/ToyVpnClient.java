package com.example.breeder1.androidratproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.VpnService;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToyVpnClient extends Activity {

    TextView serverAddress;
    TextView serverPort;
    TextView sharedSecret;

    public interface Prefs {
        String NAME = "connection";
        String SERVER_ADDRESS = "server.address";
        String SERVER_PORT = "server.port";
        String SHARED_SECRET = "shared.secret";
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toy_vpn_client);

        serverAddress = (TextView) findViewById(R.id.address);
        serverPort = (TextView) findViewById(R.id.port);
        sharedSecret = (TextView) findViewById(R.id.secret);

        final SharedPreferences prefs = getSharedPreferences(Prefs.NAME, MODE_PRIVATE);

        serverAddress.setText(prefs.getString(Prefs.SERVER_ADDRESS, ""));
        serverPort.setText(prefs.getString(Prefs.SERVER_PORT, ""));
        sharedSecret.setText(prefs.getString(Prefs.SHARED_SECRET, ""));

    }

    public void connectOnClick(View V){
        final SharedPreferences prefs = getSharedPreferences(Prefs.NAME, MODE_PRIVATE);
        prefs.edit()
                .putString(Prefs.SERVER_ADDRESS, serverAddress.getText().toString())
                .putString(Prefs.SERVER_PORT, serverPort.getText().toString())
                .putString(Prefs.SHARED_SECRET, sharedSecret.getText().toString())
                .commit();
        Intent intent = VpnService.prepare(ToyVpnClient.this);
        if (intent != null) {
            startActivityForResult(intent, 0);
        } else {
            onActivityResult(0, RESULT_OK, null);
        }
        Toast.makeText(getApplicationContext(), "fff", Toast.LENGTH_LONG).show();
    }

    public void disconnectOnClick(View V){
        startService(getServiceIntent().setAction(ToyVpnService.ACTION_DISCONNECT));
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        if (result == RESULT_OK) {
            startService(getServiceIntent().setAction(ToyVpnService.ACTION_CONNECT));
        }
    }


    private Intent getServiceIntent() {
        return new Intent(this, ToyVpnService.class);
    }
}
