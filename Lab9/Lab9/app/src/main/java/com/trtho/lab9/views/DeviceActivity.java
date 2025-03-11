package com.trtho.lab9.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trtho.lab9.R;
import com.trtho.lab9.adapters.DeviceAdapter;
import com.trtho.lab9.contracts.DeviceContract;
import com.trtho.lab9.models.DeviceModel;
import com.trtho.lab9.presenters.DevicePresenter;
import com.trtho.lab9.repositories.DeviceRepository;

import java.util.List;

public class DeviceActivity extends AppCompatActivity implements DeviceContract.View, View.OnClickListener {

    private DeviceModel selectedDevice = null;
    private int selectedPosition = -1;
    private DevicePresenter presenter;

    private DeviceAdapter adapter;
    private RecyclerView recyclerView;
    private Button btnAdd, btnUpdate, btnDelete, btnInfo;
    private EditText etName, etDescription, etImageLink;
    private ImageView imgChoose;

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        load();
    }

    private void load(){

        recyclerView = findViewById(R.id.rcvDeviceList);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnInfo = findViewById(R.id.btnInfo);
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etImageLink = findViewById(R.id.etImageLink);
        imgChoose = findViewById(R.id.imgChoose);

        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        imgChoose.setOnClickListener(this);

        presenter = new DevicePresenter(this, new DeviceRepository(this));
        adapter = new DeviceAdapter(this.presenter,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        presenter.loadDevices();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnAdd) {
            DeviceModel device = new DeviceModel();
            device.setName(etName.getText().toString());
            device.setDescription(etDescription.getText().toString());
            device.setImage(etImageLink.getText().toString());
            presenter.addDevice(device);
        } else if (id == R.id.btnUpdate) {
            if (selectedDevice != null) {
                selectedDevice.setName(etName.getText().toString());
                selectedDevice.setDescription(etDescription.getText().toString());
                selectedDevice.setImage(etImageLink.getText().toString());
                presenter.updateDevice(selectedDevice);
            } else {
                Toast.makeText(this, "Please select a device to update", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.btnDelete) {
            if (selectedDevice != null) {
                presenter.deleteDevice(selectedDevice);
            } else {
                Toast.makeText(this, "Please select a device to delete", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.btnInfo) {
            if (selectedDevice != null) {
                Intent intent = new Intent(this, DeviceInfoActivity.class);
                intent.putExtra("name", selectedDevice.getName());
                intent.putExtra("description", selectedDevice.getDescription());
                intent.putExtra("image", selectedDevice.getImage());
                activityResultLauncher.launch(intent);
            }
        }
    }

    @Override
    public void showDevices(List<DeviceModel> devices) {
        adapter.setDevices(devices);
    }

    public void setSelectedDevice(int position, DeviceModel device) {
        this.selectedDevice = device;
        this.selectedPosition = position;
        etName.setText(device.getName());
        etDescription.setText(device.getDescription());
        etImageLink.setText(device.getImage());
        Picasso.get().load(device.getImage()).placeholder(R.drawable.image_loading).into(imgChoose);
    }

}