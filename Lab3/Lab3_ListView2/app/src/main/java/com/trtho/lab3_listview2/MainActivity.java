package com.trtho.lab3_listview2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etLanguage;
    private Button btnAdd, btnUpdate, btnDelete;
    private ListView listView;

    private ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private int selectedIndex = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etLanguage = findViewById(R.id.etLanguage);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        listView = findViewById(R.id.listView);

        itemList.add("Android");

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, itemList
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
                etLanguage.setText(itemList.get(position));
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnAdd) {
            Add();
        } else if (id == R.id.btnUpdate) {
            Update();
        } else if (id == R.id.btnDelete) {
            Delete();
        }
    }

    private void Add(){
        System.out.println("Add");
        if (etLanguage.getText().toString().isEmpty()) {
            etLanguage.setError("Please enter language");
            return;
        }
        String language = etLanguage.getText().toString();
        itemList.add(language);
        adapter.notifyDataSetChanged();
        etLanguage.setText("");
        selectedIndex = -1;
    }

    private void Update() {
        System.out.println("Update");
        if (etLanguage.getText().toString().isEmpty() || selectedIndex == -1) {
            etLanguage.setError("Please enter language");
            return;
        }
        String language = etLanguage.getText().toString();
        itemList.set(selectedIndex, language);
        adapter.notifyDataSetChanged();
        etLanguage.setText("");
        selectedIndex = -1;
    }

    private void Delete() {
        System.out.println("Delete");
        if (selectedIndex == -1) {
            Toast.makeText(this, "Please choose a language to delete!", Toast.LENGTH_SHORT).show();
            return;
        }
        itemList.remove(selectedIndex);
        adapter.notifyDataSetChanged();
        etLanguage.setText("");
        selectedIndex = -1;
    }
}