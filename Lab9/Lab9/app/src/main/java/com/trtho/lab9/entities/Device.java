package com.trtho.lab9.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(tableName = "devices")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Device {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String description;
    public String image;
}
