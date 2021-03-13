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
import com.example.player.adapters.ArtistAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {


    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView=getActivity().findViewById(R.id.recyclerView_Artist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String projection[]={
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        };

        ContentResolver contentResolver=getActivity().getContentResolver();
        Cursor cursor=contentResolver.query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Audio.Artists.ARTIST+""
        );

        recyclerView.setAdapter(new ArtistAdapter(getContext(),cursor));

    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Artist");
    }
}
