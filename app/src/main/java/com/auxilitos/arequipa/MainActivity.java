package com.auxilitos.arequipa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnentrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnentrar = findViewById(R.id.btnEntrar);
        btnentrar.setOnClickListener(view -> {
            startActivity(new Intent(this, PublicacionesActivity.class));
        });

    }//Fin onCreate


}//Fin tood