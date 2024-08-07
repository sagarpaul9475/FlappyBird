package com.example.flappybird;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView playBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppConstants.initialization(this.getApplicationContext());
        playBtn=findViewById(R.id.playBtn);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Play",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,GameActvity.class);
                startActivity(intent);
            }
        });
    }
}