package com.trtho.lab3_listview3.model;

import android.net.Uri;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fruit {
    private String name;
    private String description;
    private Integer imageId;
    private Uri uri;

    public Fruit (String name, String description, Integer imageId){
        this.name = name;
        this.description = description;
        this.imageId = imageId;
        this.uri = null;
    }

    public Fruit (String name, String description, Uri uri) {
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.imageId = null;
    }
}
