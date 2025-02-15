package com.trtho.lab3_listview1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnShow;
    private TextView tvLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnShow = findViewById(R.id.btnShow);
        tvLanguage = findViewById(R.id.tvLanguage);

        btnShow.setOnClickListener(this);

        String value = getIntent().getStringExtra("value");
        if (value != null) {
            tvLanguage.setVisibility(View.VISIBLE);
            tvLanguage.setText(String.format("Selected Language: %s", value));
        } else {
            tvLanguage.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnShow) {
            showList();
        }
    }

    private void showList(){
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
        finish();
    }
}