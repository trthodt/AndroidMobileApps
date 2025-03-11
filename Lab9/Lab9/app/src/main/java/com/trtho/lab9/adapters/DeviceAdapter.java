package com.trtho.lab9.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trtho.lab9.R;
import com.trtho.lab9.models.DeviceModel;
import com.trtho.lab9.presenters.DevicePresenter;
import com.trtho.lab9.views.DeviceActivity;

import java.util.ArrayList;
import java.util.List;


public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    List<DeviceModel> list = new ArrayList<>();
    private DevicePresenter presenter;
    private DeviceActivity deviceActivity;

    public DeviceAdapter(DevicePresenter presenter, DeviceActivity deviceActivity) {
        this.presenter = presenter;
        this.deviceActivity = deviceActivity;
    }

    public void setDevices(List<DeviceModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeviceModel device = list.get(position);
        holder.tvName.setText(device.getName());
        holder.tvDescription.setText(device.getDescription());
        Picasso.get().load(device.getImage()).placeholder(R.drawable.image_loading)
                .error(R.drawable.img_device)
                .into(holder.ivDevice);

        holder.itemView.setOnClickListener(view -> {
            deviceActivity.setSelectedDevice(position,device);
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
