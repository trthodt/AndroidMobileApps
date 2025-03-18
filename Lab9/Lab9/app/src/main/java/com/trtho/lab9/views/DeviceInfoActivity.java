package com.trtho.lab9.views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.trtho.lab9.R;


public class DeviceInfoActivity extends AppCompatActivity {

    private ImageView imgInfo;
    private TextView tvNameInfo, tvDescInfo;
    private Button btnChooseDevice;

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String name = data.getStringExtra("name");
                        String description = data.getStringExtra("description");
                        String image = data.getStringExtra("image");
                        tvNameInfo.setText(String.format("Tên thiết bị: %s", name));
                        tvDescInfo.setText(String.format("Chi tiết: %s", description));
                        Picasso.get().load(image).placeholder(R.drawable.image_loading).into(imgInfo);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_device_info);

        imgInfo = findViewById(R.id.imgInfo);
        tvNameInfo = findViewById(R.id.tvNameInfo);
        tvDescInfo = findViewById(R.id.tvDescInfo);
        btnChooseDevice = findViewById(R.id.btnChooseDevice);
        btnChooseDevice.setOnClickListener(view -> {
            Intent intent = new Intent(this, DeviceActivity.class);
            activityResultLauncher.launch(intent);
        });

//        if (getIntent() != null) {
//            String name = getIntent().getStringExtra("name");
//            String description = getIntent().getStringExtra("description");
//            String image = getIntent().getStringExtra("image");
//            tvNameInfo.setText(String.format("Tên thiết bị: %s", name));
//            tvDescInfo.setText(String.format("Chi tiết: %s", description));
//            Picasso.get().load(image).placeholder(R.drawable.image_loading).into(imgInfo);
//        }
    }
}