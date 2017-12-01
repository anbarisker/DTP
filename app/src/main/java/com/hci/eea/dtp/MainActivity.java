package com.hci.eea.dtp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(getApplicationContext(), PushUpCounter.class);
        startActivity(intent);
    }
/*
    public void goToProximitySensor(View view) {
        Intent intent = new Intent(this, PushUpCounter.class);
        startActivity(intent);
    }*/
}
