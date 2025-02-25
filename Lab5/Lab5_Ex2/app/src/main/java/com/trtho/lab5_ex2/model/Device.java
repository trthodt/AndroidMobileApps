package com.trtho.lab5_ex2.model;

import android.net.Uri;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    private String name;
    private String description;
    private Uri uri;
    private Integer imageId;

    public Device(String name, String description, int imageId) {
        this.name = name;
        this.description = description;
        this.imageId = imageId;
        this.uri = null;
    }

    public Device(String name, String description, Uri uri) {
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.imageId = null;
    }
}
