package com.trtho.lab9;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.trtho.lab9.DAO.DeviceDAO;
import com.trtho.lab9.entities.Device;

@Database(entities = {Device.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract DeviceDAO deviceDAO();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class, "app_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}

