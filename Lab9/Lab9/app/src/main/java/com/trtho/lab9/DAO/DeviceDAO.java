package com.trtho.lab9.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.trtho.lab9.entities.Device;

import java.util.List;

@Dao
public interface DeviceDAO {
    @Query("SELECT * FROM devices")
    LiveData<List<Device>> getAll();

    @Query("SELECT * FROM devices WHERE id = :id")
    Device getById(int id);

    @Insert
    void insert(Device device);

    @Update
    void update(Device device);

    @Delete
    void delete(Device device);

}
