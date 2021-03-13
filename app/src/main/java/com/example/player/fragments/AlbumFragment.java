package com.example.player.fragments;


import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.player.R;
import com.example.player.activities.MainActivity;
import com.example.player.adapters.AlbumAdapter;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {


    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView= Objects.requireNonNull(getActivity().findViewById(R.id.recyclerView_Album));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String projection[]={
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.ALBUM_ART
        };

        ContentResolver contentResolver=getActivity().getContentResolver();
        Cursor cursor=contentResolver.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Audio.Albums.ALBUM+""
        );

        recyclerView.setAdapter(new AlbumAdapter(cursor, getContext()));

    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Albums");

    }
}
