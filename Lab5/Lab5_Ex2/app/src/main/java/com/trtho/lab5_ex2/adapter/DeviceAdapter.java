package com.trtho.lab5_ex2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trtho.lab5_ex2.R;
import com.trtho.lab5_ex2.model.Device;
import com.trtho.lab5_ex2.presenter.DevicePresenter;
import com.trtho.lab5_ex2.view.DeviceActivity;

import java.util.List;


public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    List<Device> list;
    private DevicePresenter presenter;
    private DeviceActivity deviceActivity;

    public DeviceAdapter(List<Device> userList, DevicePresenter presenter, DeviceActivity deviceActivity) {
        this.list = userList;
        this.presenter = presenter;
        this.deviceActivity = deviceActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Device device = list.get(position);
        holder.tvName.setText(device.getName());
        holder.tvDescription.setText(device.getDescription());
        if (device.getImageId() != null) {
            holder.ivDevice.setImageResource(device.getImageId());
        } else {
            holder.ivDevice.setImageURI(device.getUri());
        }

        holder.itemView.setOnClickListener(view -> {
            deviceActivity.setDevice(position,device);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription;
        ImageView ivDevice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivDevice = itemView.findViewById(R.id.ivDevice);
        }
    }
}
