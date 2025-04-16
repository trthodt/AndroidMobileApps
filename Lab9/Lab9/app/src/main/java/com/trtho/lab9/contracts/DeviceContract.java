package com.trtho.lab9.contracts;

import com.trtho.lab9.models.DeviceModel;

import java.util.List;

public interface DeviceContract {
    interface Presenter {
        void loadDevices();

        void addDevice(DeviceModel device);
        void updateDevice(DeviceModel device);
        void deleteDevice(DeviceModel device);
    }
    interface View {
        void showDevices(List<DeviceModel> devices);
    }

}
