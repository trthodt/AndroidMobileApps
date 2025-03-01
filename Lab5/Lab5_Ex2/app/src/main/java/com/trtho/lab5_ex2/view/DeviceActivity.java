package com.trtho.lab5_ex2.view;

import android.app.Activity;
import android.app.ComponentCaller;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trtho.lab5_ex2.R;
import com.trtho.lab5_ex2.adapter.DeviceAdapter;
import com.trtho.lab5_ex2.contract.DeviceContract;
import com.trtho.lab5_ex2.model.Device;
import com.trtho.lab5_ex2.presenter.DevicePresenter;

import java.util.List;

public class DeviceActivity extends AppCompatActivity implements DeviceContract.View {

    private RecyclerView rcvDeviceList;
    private DeviceAdapter deviceAdapter;
    private DevicePresenter devicePresenter;

    private Button btnAdd, btnUpdate, btnDelete, btnInfo;
    private EditText etName, etDescription;
    private ImageView imgChoose;

    private int selectedPosition = -1;
    private Device selectedDevice = null;

    private Uri imageUri;
    private int rsId = -1;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    imgChoose.setImageURI(imageUri);
                    rsId = -1;
                }
            });

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String resultData = data.getStringExtra("message");
                        Toast.makeText(this, "Thiết bị vừa xem: " + resultData, Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_device);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        imgChoose = findViewById(R.id.imgChoose);
        rcvDeviceList = findViewById(R.id.rcvDeviceList);
        btnInfo = findViewById(R.id.btnInfo);


        rcvDeviceList.setLayoutManager(new LinearLayoutManager(this));
        devicePresenter = new DevicePresenter(this);
        devicePresenter.loadDevices();

        imgChoose.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnAdd.setOnClickListener(view -> {
                    if (etName.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Device device = new Device();
                    device.setName(etName.getText().toString());
                    device.setDescription(etDescription.getText().toString());
                    if (rsId != -1) {
                        device.setImageId(rsId);
                    } else if (imageUri != null) {
                        device.setUri(imageUri);
                    } else {
                        showMessage("Vui lòng chọn ảnh");
                        return;
                    }
                    devicePresenter.addDevice(device);
                    clear();
                }
        );

        btnUpdate.setOnClickListener(view -> {
                    if (selectedPosition == -1) {
                        showMessage("Vui lòng chọn thiết bị để cập nhật");
                        return;
                    }
                    if (etName.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Device newDevice = new Device();
                    newDevice.setName(etName.getText().toString());
                    newDevice.setDescription(etDescription.getText().toString());
                    if (rsId != -1) {
                        newDevice.setImageId(rsId);
                    } else if (imageUri != null) {
                        newDevice.setUri(imageUri);
                    } else {
                        showMessage("Vui lòng chọn ảnh");
                        return;
                    }
                    newDevice.setUri(imageUri);
                    devicePresenter.updateDevice(selectedPosition, newDevice);
                    clear();
                }
        );

        btnInfo.setOnClickListener(view -> {
            if (selectedPosition == -1) {
                showMessage("Vui lòng chọn thiết bị để xem thông tin");
                return;
            }
            Intent intent = new Intent(this, DeviceInfoActivity.class);
            intent.putExtra("name", selectedDevice.getName());
            intent.putExtra("description", selectedDevice.getDescription());
            if (selectedDevice.getImageId() != null) {
                intent.putExtra("imageId", selectedDevice.getImageId());
            } else {
                intent.putExtra("imageUri", selectedDevice.getUri());
            }
            activityResultLauncher.launch(intent);
        });

        btnDelete.setOnClickListener(view -> {
            if (selectedPosition == -1) {
                showMessage("Vui lòng chọn thiết bị để xóa");
                return;
            }
            devicePresenter.deleteDevice(selectedPosition);
            clear();
        });

    }

    @Override
    public void showDevices(List<Device> devices) {
        deviceAdapter = new DeviceAdapter(devices, devicePresenter, this);
        rcvDeviceList.setAdapter(deviceAdapter);
        deviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDevice(int position, Device device) {
        this.selectedPosition = position;
        this.selectedDevice = device;
        etName.setText(device.getName());
        etDescription.setText(device.getDescription());
        if (device.getImageId() != null) {
            imgChoose.setImageResource(device.getImageId());
            imageUri = null;
            rsId = device.getImageId();
        } else {
            imgChoose.setImageURI(device.getUri());
            imageUri = device.getUri();
            rsId = -1;
        }
    }

    @Override
    public void notifyDataChanged() {
        deviceAdapter.notifyDataSetChanged();
    }

    public void clear() {
        etName.setText("");
        etDescription.setText("");
        imageUri = null;
        imgChoose.setImageURI(null);
        imgChoose.setImageResource(R.drawable.device);
        selectedPosition = -1;
        selectedDevice = null;
    }
}