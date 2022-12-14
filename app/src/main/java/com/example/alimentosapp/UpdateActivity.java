package com.example.alimentosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText title;
    ImageView img;
    Button update;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title = findViewById(R.id.titleShow);
        img = findViewById(R.id.imageView);
        update = findViewById(R.id.update_note);

        Intent i = getIntent();
        title.setText(i.getStringExtra("title"));
        id=i.getStringExtra("id");

        Toast.makeText(this,i.getStringExtra("title"),
                Toast.LENGTH_LONG).show();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(title.getText().toString())){
                    Database database = new Database(UpdateActivity.this);
                    database.updateNotes(title.getText().toString(),id);
                    startActivity(new Intent(UpdateActivity.this,MainActivity.class));
                } else {
                    Toast.makeText(UpdateActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}