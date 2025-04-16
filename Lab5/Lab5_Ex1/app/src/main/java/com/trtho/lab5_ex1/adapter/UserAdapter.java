package com.trtho.lab5_ex1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trtho.lab5_ex1.R;
import com.trtho.lab5_ex1.model.User;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private Context context;
    private LayoutInflater mInflater;

    public UserAdapter(List<User> users, Context context) {
        this.users = users;
        mInflater = LayoutInflater.from(context);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView etUserName;
        public TextView etFullName;
        public TextView etEmail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etUserName = itemView.findViewById(R.id.tvUserName);
            etFullName = itemView.findViewById(R.id.tvFullName);
            etEmail = itemView.findViewById(R.id.tvEmail);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        // Add the data to the view
        holder.etUserName.setText(user.getUserName());
        holder.etFullName.setText(user.getFullName());
        holder.etEmail.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
