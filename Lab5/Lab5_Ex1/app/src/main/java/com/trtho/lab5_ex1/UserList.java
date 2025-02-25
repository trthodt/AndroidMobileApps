package com.trtho.lab5_ex1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trtho.lab5_ex1.adapter.UserAdapter;
import com.trtho.lab5_ex1.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_list);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        List<User> users = new ArrayList<User>();
        users.add(new User("TrTho","Nguyen Truong Tho","Truongthonguyen107@gmail.com"));
        UserAdapter mAdapter = new UserAdapter(users, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new
                LinearLayoutManager(this));


    }
}