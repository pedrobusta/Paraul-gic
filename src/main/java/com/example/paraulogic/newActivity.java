package com.example.paraulogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class newActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.idMessage);

        TextView textView = findViewById(R.id.idTextView);
        textView.setText(Html.fromHtml(message), TextView.BufferType.SPANNABLE);
    }
}