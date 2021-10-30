package com.example.nonograms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemActivity extends AppCompatActivity {
    private ListView listView;
    private ItemAdapter adapter;
    private ArrayList<Item> listData;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        listView = findViewById(R.id.listView);
        listData = new ArrayList<>();
        adapter = new ItemAdapter(this, R.layout.listview_custom, listData, getLayoutInflater());
        listView.setAdapter(adapter);
        mDataBase = FirebaseDatabase.getInstance().getReference();

        getDataFromDB();
        setOnClickItem();
    }

    private void getDataFromDB()
    {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listData.size() > 0) listData.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Item item = ds.getValue(Item.class);
                    assert item != null;
                    listData.add(item);
                }
                Log.e("MyTag", String.valueOf(listData));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }

    private void setOnClickItem()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item =  listData.get(position);
                Intent i = new Intent(ItemActivity.this, GameActivity.class);
                i.putExtra("item_name", item.name);
                i.putExtra("item_picture", item.picture);
                startActivity(i);
            }
        });
    }
}