package com.example.matchmaker.View.Activity.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.matchmaker.R;
import com.example.matchmaker.View.Activity.Model.User;

import java.util.List;

public class CustomAdapter extends android.widget.ArrayAdapter<User> {

    Context context;

    public CustomAdapter(@NonNull Context context, int resourceId, @NonNull List<User> items) {
        super(context, resourceId, items);
    }

    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {

        User userItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);

        }

        TextView name = convertView.findViewById(R.id.name);
        ImageView image = convertView.findViewById(R.id.imageView);

        name.setText(userItem.getUserName());
        image.setImageResource(R.drawable.pro);

        return convertView;

    }
}
