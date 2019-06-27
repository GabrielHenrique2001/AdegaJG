package com.example.adegajg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botaolista = findViewById(R.id.buttonlista);
        botaolista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LinkLista = new Intent(MainActivity.this, listaActivity.class);
                startActivity(LinkLista);
            }
        });
    }
}
