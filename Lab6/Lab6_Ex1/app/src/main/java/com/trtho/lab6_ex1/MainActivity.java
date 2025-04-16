package com.trtho.lab6_ex1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnPopupMenu, btnChooseColor;
    private ConstraintLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnPopupMenu = findViewById(R.id.btnPopupMenu);
        btnChooseColor = findViewById(R.id.btnChooseColor);
        mainLayout = findViewById(R.id.main);
        btnPopupMenu.setOnClickListener(v -> showPopupMenu());
        registerForContextMenu(btnChooseColor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.option_search) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.option_settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.option_favorites) {
            Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.option_email) {
            Toast.makeText(this, "Email", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.option_phone) {
            Toast.makeText(this, "Phone", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void showPopupMenu(){
        PopupMenu popupMenu = new PopupMenu(this, btnPopupMenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.option_them) {
                    btnPopupMenu.setText("Menu Thêm");
                    return true;
                } else if (id == R.id.option_sua) {
                    btnPopupMenu.setText("Menu Sửa");
                    return true;
                } else if (id == R.id.option_xoa) {
                    btnPopupMenu.setText("Menu Xóa");
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_color, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.red) {
            mainLayout.setBackgroundColor(Color.RED);
            return true;
        } else if (id == R.id.green) {
            mainLayout.setBackgroundColor(Color.GREEN);
            return true;
        } else if (id == R.id.blue) {
            mainLayout.setBackgroundColor(Color.BLUE);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}