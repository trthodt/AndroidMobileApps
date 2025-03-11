package com.trtho.lab9.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
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
    private EditText etName, etDescription;
    private ImageView imgChoose;

    ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Picasso.get().load(result.getData().getData()).placeholder(R.drawable.image_loading).into(imgChoose);
                    selectedDevice.setImage(result.getData().getData().toString());
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
            device.setImage(String.valueOf(imgChoose.getImageAlpha()));
            presenter.addDevice(device);
        } else if (id == R.id.btnUpdate) {
            if (selectedDevice != null) {
                selectedDevice.setName(etName.getText().toString());
                selectedDevice.setDescription(etDescription.getText().toString());
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
        } else if (id == R.id.imgChoose) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
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
        Picasso.get().load(device.getImage()).placeholder(R.drawable.image_loading).into(imgChoose);

    }

}