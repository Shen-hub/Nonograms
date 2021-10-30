package com.example.nonograms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends ArrayAdapter
{
    private LayoutInflater inflater;
    private List<Item> listItem = new ArrayList<>();
    private Context context;
    public ItemAdapter(@NonNull Context context, int resource, List<Item> listItem, LayoutInflater inflater) {
        super(context, resource, listItem);
        this.inflater = inflater;
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Item listItemMain = listItem.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_custom,null,false); //Определяем, какой лэйаут берём для отрисовки
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.textViewList);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(listItemMain.getName()); //Определеем текст

        return convertView;
    }
    private class ViewHolder{
        TextView name;
    }

}
