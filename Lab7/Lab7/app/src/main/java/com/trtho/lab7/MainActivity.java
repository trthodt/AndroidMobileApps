package com.trtho.lab7;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnRequestPermission, btnOpenSettings, btnCall;
    private EditText etPhone;
    private String phoneNumber;

    private static final int REQUEST_CALL_PERMISSION = 100;
    private static final int REQUEST_LOCATION_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnOpenSettings = findViewById(R.id.btnOpenSettings);
        btnRequestPermission = findViewById(R.id.btnRequestPermission);
        btnCall = findViewById(R.id.btnCall);
        etPhone = findViewById(R.id.etPhone);

        btnRequestPermission.setOnClickListener(v -> checkLocationPermission());
        btnOpenSettings.setOnClickListener(v -> showSettingsDialog());
        btnCall.setOnClickListener(v -> makeCall());
    }

    private void makeCall() {
        if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            phoneNumber = "tel:" + etPhone.getText().toString();
            intent.setData(Uri.parse(phoneNumber));
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PERMISSION);
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Quyền đã được cấp
            Toast.makeText(this, "PERMISSION Is Granted", Toast.LENGTH_SHORT).show();
        } else {
            // Kiểm tra xem có cần hiển thị lời giải thích không
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                showRationaleDialog();
            } else {
                // Yêu cầu quyền nếu chưa từng bị từ chối
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);
            }
        }
    }

    private void showRationaleDialog() {
        Toast.makeText(this, "Vui lòng cấp quyền trong cài đặt", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Kiểm tra requestCode để xử lý kết quả yêu cầu quyền
        // Quyền vị trí
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION Is Granted", Toast.LENGTH_SHORT).show();
            } else {
                // Kiểm tra nếu người dùng đã chọn "Don't ask again"
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showSettingsDialog();
                } else {
                    Toast.makeText(this, "PERMISSION Is Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }

        // Quyền gọi điện
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
                Toast.makeText(this, "CALL PERMISSION Is Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Bạn cần cấp quyền để gọi điện", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showSettingsDialog() {
        Toast.makeText(this, "Vui lòng cấp quyền trong cài đặt", Toast.LENGTH_SHORT).show();
    }
}