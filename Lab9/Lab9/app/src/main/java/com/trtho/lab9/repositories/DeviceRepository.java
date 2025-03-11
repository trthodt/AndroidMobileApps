package com.trtho.lab9.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.trtho.lab9.AppDatabase;
import com.trtho.lab9.DAO.DeviceDAO;
import com.trtho.lab9.entities.Device;

import java.util.List;
import java.util.concurrent.Executors;

public class DeviceRepository {
    private DeviceDAO deviceDao;
    private LiveData<List<Device>> allDevices;

    public DeviceRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        deviceDao = db.deviceDAO();
        allDevices = deviceDao.getAll();
    }

    public LiveData<List<Device>> getAllDevices() {
        return allDevices;
    }

    public void insertDevice(Device device) {
        Executors.newSingleThreadExecutor().execute(() -> deviceDao.insert(device));
    }

    public void updateDevice(Device device) {
        Executors.newSingleThreadExecutor().execute(() -> deviceDao.update(device));
    }

    public void deleteDevice(Device device) {
        Executors.newSingleThreadExecutor().execute(() -> deviceDao.delete(device));
    }
}
