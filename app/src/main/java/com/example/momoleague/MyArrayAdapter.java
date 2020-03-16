package com.example.momoleague;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyArrayAdapter extends android.widget.ArrayAdapter<ListItem> {


    private Context context;
    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull List<ListItem> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        TextView email = listItem.findViewById(R.id.email);
        TextView games = listItem.findViewById(R.id.games);
        TextView points = listItem.findViewById(R.id.points);
        ListItem item = getItem(position);
        email.setText(item.getEmail());
        games.setText(Integer.toString(item.getGamse()));
        points.setText(Integer.toString(item.getPoints()));

        return listItem;
    }
}
