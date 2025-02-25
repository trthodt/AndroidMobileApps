package com.trtho.lab5_ex2.presenter;

import com.trtho.lab5_ex2.R;
import com.trtho.lab5_ex2.contract.DeviceContract;
import com.trtho.lab5_ex2.model.Device;

import java.util.ArrayList;
import java.util.List;

public class DevicePresenter implements DeviceContract.Presenter{

    private DeviceContract.View view;
    private List<Device> list;
    public DevicePresenter(DeviceContract.View view) {
        this.view = view;
        list = new ArrayList<>();
        list.add(new Device("Laptop", "Description 1", R.drawable.laptop));
        list.add(new Device("Phone", "Description 2",R.drawable.phone));
        list.add(new Device("Tablet", "Description 3",R.drawable.tablet));
    }
    @Override
    public void loadDevices() {
        view.showDevices(list);
    }

    @Override
    public void addDevice(Device device) {
        list.add(device);
        view.showMessage("Thêm thành công");
        view.showDevices(list);
    }

    @Override
    public void deleteDevice(int position) {
        list.remove(position);
        view.showMessage("Xóa thành công");
        view.showDevices(list);
    }

    @Override
    public void updateDevice(int position, Device device) {
        list.set(position, device);
        view.showMessage("Cập nhật thành công");
        view.showDevices(list);
    }
}
