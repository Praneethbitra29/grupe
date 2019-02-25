package com.example.rgukt.grupee_fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class LikedImages extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ArrayList<Image> imageList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_show_liked_images, container, false);

        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        imageList = new ArrayList<>();


        final DatabaseHelper mydb = new DatabaseHelper(getContext());
        Cursor result = mydb.getAllData();
        if(result.getCount() != 0) {
            while (result.moveToNext()) {
                String timestamp,url;
                timestamp = result.getString(1);
                url = result.getString(0);
                Image image = new Image(timestamp,"Liked",url);
                imageList.add(image);
            }
        }else{

        }

        adapter = new ImageAdapter(imageList,getContext());
        recyclerView.setAdapter(adapter);

        return root;
    }
}
