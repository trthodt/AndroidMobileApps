package com.trtho.lab9.presenters;

import com.trtho.lab9.contracts.DeviceContract;
import com.trtho.lab9.entities.Device;
import com.trtho.lab9.models.DeviceModel;
import com.trtho.lab9.repositories.DeviceRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DevicePresenter implements DeviceContract.Presenter {

    private DeviceContract.View view;
    private DeviceRepository repository;

    public DevicePresenter(DeviceContract.View view, DeviceRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadDevices() {
        repository.getAllDevices().observeForever(devices -> {
            List<DeviceModel> deviceList = devices.stream().map(device -> new DeviceModel(device.id, device.name, device.description, device.image)).collect(Collectors.toList());
            view.showDevices(deviceList);
        });

    }

    @Override
    public void addDevice(DeviceModel device) {
        Device deviceEntity = new Device(device.getId(), device.getName(), device.getDescription(), device.getImage());
        repository.insertDevice(deviceEntity);
    }

    @Override
    public void updateDevice(DeviceModel device) {
        Device deviceEntity = new Device(device.getId(), device.getName(), device.getDescription(), device.getImage());
        repository.updateDevice(deviceEntity);
    }

    @Override
    public void deleteDevice(DeviceModel device) {
        Device deviceEntity = new Device(device.getId(), device.getName(), device.getDescription(), device.getImage());
        repository.deleteDevice(deviceEntity);
    }
}
