package com.trtho.lab3_listview3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.trtho.lab3_listview3.adapter.FruitAdapter;
import com.trtho.lab3_listview3.model.Fruit;

import java.util.ArrayList;

public class ListView_3 extends AppCompatActivity {

    private ListView listView;
    private FruitAdapter adapter;
    private ArrayList<Fruit> fruitList;
    private int selectedPosition = -1; // Vị trí item đang được chọn

    private EditText etName;
    private EditText etDescription;
    private ImageView ivFruit;

    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    ivFruit.setImageURI(imageUri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_3);

        listView = findViewById(R.id.listView);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnClose = findViewById(R.id.btnClose);
        etName = findViewById(R.id.editTextText3);
        etDescription = findViewById(R.id.etDescription);
        ivFruit = findViewById(R.id.ivFruit);

        // Dữ liệu ban đầu
        fruitList = new ArrayList<>();
        fruitList.add(new Fruit("Chuối tiêu", "Chuối tiêu Long An", R.drawable.chuoitieu));
        fruitList.add(new Fruit("Thanh Long", "Thanh long ruột đỏ", R.drawable.thanhlong));
        fruitList.add(new Fruit("Dâu tây", "Dâu tây Đà Lạt", R.drawable.dautay));

        adapter = new FruitAdapter(this, fruitList);
        listView.setAdapter(adapter);

        // Chọn item trong ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            Fruit fruit = fruitList.get(position);
            etName.setText(fruit.getName());
            etDescription.setText(fruit.getDescription());
            if (fruit.getImageId() != null) {
                ivFruit.setImageResource(fruit.getImageId());
            } else if (fruit.getUri() != null) {
                ivFruit.setImageURI(fruit.getUri());
            }
        });

        // Thêm item mới
        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String description = etDescription.getText().toString();
            if (name.isEmpty() || description.isEmpty()) {
                Toast.makeText(ListView_3.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            fruitList.add(new Fruit(name, description, imageUri));
            adapter.notifyDataSetChanged();
            Toast.makeText(ListView_3.this, "Đã thêm " + name, Toast.LENGTH_SHORT).show();
        });

        // Sửa item
        btnEdit.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                if (etName.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty()) {
                    Toast.makeText(ListView_3.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                Fruit fruit = fruitList.get(selectedPosition);
                fruit.setName(etName.getText().toString());
                fruit.setDescription(etDescription.getText().toString());
                if (imageUri != null) {
                    fruit.setUri(imageUri);
                    fruit.setImageId(null);
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(ListView_3.this, "Đã cập nhật " + fruit.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ListView_3.this, "Vui lòng chọn item để sửa", Toast.LENGTH_SHORT).show();
            }
        });

        // Xóa item
        btnDelete.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                new AlertDialog.Builder(ListView_3.this)
                        .setTitle("Xóa Item")
                        .setMessage("Bạn có chắc muốn xóa " + fruitList.get(selectedPosition).getName() + " không?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            fruitList.remove(selectedPosition);
                            adapter.notifyDataSetChanged();
                            selectedPosition = -1; // Reset vị trí
                            Toast.makeText(ListView_3.this, "Đã xóa item", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            } else {
                Toast.makeText(ListView_3.this, "Vui lòng chọn item để xóa", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nút Đóng
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivFruit.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });
    }
}