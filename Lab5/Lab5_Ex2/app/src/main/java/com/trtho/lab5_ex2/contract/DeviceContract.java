package com.trtho.lab5_ex2.contract;

import com.trtho.lab5_ex2.model.Device;

import java.util.List;

public interface DeviceContract {
    interface View {
        void showDevices(List<Device> devices);
        void showMessage(String message);
        void setDevice(int position,Device device);
    }
    interface Presenter {
        void loadDevices();
        void addDevice(Device device);
        void deleteDevice(int position);
        void updateDevice(int position, Device device);
    }
}
