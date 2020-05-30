package com.meuapp.starpasseios;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PrimeiroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeiro);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMainActivity();
            }
        }, 4000);
    }

    private void mostrarMainActivity() {
        Intent intent = new Intent(
                PrimeiroActivity.this,LoginActivity.class
        );
        startActivity(intent);
        finish();
    }
}
