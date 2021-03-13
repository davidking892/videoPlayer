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
import com.example.player.adapters.SongAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongFragment extends Fragment {


    public SongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView=getActivity().findViewById(R.id.recyclerView_songs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String selection= MediaStore.Audio.Media.IS_MUSIC;

        String[] projection={
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID
        };

        ContentResolver contentResolver=getActivity().getContentResolver();
        Cursor cursor=contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                MediaStore.Audio.Media.TITLE
        );

        recyclerView.setAdapter(new SongAdapter(getContext(),cursor));

    }
}
