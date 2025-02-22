package com.trtho.lab3_listview3.model;

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
    private int imageId;
}
