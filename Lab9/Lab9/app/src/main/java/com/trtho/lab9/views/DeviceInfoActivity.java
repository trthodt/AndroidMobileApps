package com.trtho.lab9.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.trtho.lab9.R;


public class DeviceInfoActivity extends AppCompatActivity {

    private ImageView imgInfo;
    private TextView tvNameInfo, tvDescInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_device_info);

        imgInfo = findViewById(R.id.imgInfo);
        tvNameInfo = findViewById(R.id.tvNameInfo);
        tvDescInfo = findViewById(R.id.tvDescInfo);

        if (getIntent() != null) {
            String name = getIntent().getStringExtra("name");
            String description = getIntent().getStringExtra("description");
            String image = getIntent().getStringExtra("image");
            tvNameInfo.setText(String.format("Tên thiết bị: %s", name));
            tvDescInfo.setText(String.format("Chi tiết: %s", description));
            Picasso.get().load(image).placeholder(R.drawable.image_loading).into(imgInfo);
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent();
                intent.putExtra("message", tvNameInfo.getText().toString().split(":")[1]);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}