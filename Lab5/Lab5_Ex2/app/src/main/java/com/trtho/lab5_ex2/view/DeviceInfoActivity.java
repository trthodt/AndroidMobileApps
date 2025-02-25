package com.trtho.lab5_ex2.view;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.trtho.lab5_ex2.R;

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
            int imageId = getIntent().getIntExtra("imageId", -1);
            Uri imageUri = getIntent().getParcelableExtra("imageUri");
            if (imageId != -1) {
                imgInfo.setImageResource(imageId);
            } else {
                imgInfo.setImageURI(imageUri);
            }
            tvNameInfo.setText(name);
            tvDescInfo.setText(description);
        }
    }
}