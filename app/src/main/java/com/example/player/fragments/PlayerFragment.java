package com.example.player.fragments;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.player.R;
import com.example.player.adapters.SongAdapter;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {

    private MediaPlayer mediaPlayer=new MediaPlayer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle songInfo=getArguments();

        TextView NowPlayingTrack,Album,Artist,Duration;
        ImageView AlbumArt;

        NowPlayingTrack=getActivity().findViewById(R.id.song);
        Album=getActivity().findViewById(R.id.album);
        Artist=getActivity().findViewById(R.id.artist);
        Duration=getActivity().findViewById(R.id.time_duration);
        AlbumArt=getActivity().findViewById(R.id.album_art);

        if(songInfo!=null){
            Long currentSongId=songInfo.getLong(MediaStore.Audio.Media._ID);
            NowPlayingTrack.setText(songInfo.getString(MediaStore.Audio.Media.TITLE));
            Album.setText(songInfo.getString(MediaStore.Audio.Media.ARTIST));
            Artist.setText(songInfo.getString(MediaStore.Audio.Media.ARTIST));
            Duration.setText(songInfo.getString(MediaStore.Audio.Media.DURATION));

            Glide.with(getContext())
                    .load(songInfo.getString(MediaStore.Audio.Albums.ALBUM_ART))
                    .apply(new RequestOptions()
                       .skipMemoryCache(true)
                       .diskCacheStrategy(DiskCacheStrategy.NONE)
                       .placeholder(R.drawable.album_art)
                    )
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(AlbumArt);

            playSongWithoutService(currentSongId);

        }else{
            NowPlayingTrack.setText(getString(R.string.defaultSongTitle));
            Artist.setText(getString(R.string.defaultSongArtist));
            Album.setText(getString(R.string.defaultSongAlbum));
            Duration.setText(getString(R.string.defaultSongDuration));

            Glide.with(getContext())
                    .load("")
                    .apply(new RequestOptions()
                    .placeholder(R.drawable.album_art)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE))
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(AlbumArt);
        }

    }

    private void playSongWithoutService(Long currentSong) {
        Uri trackUri= ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currentSong
        );

        mediaPlayer.setWakeMode(getActivity().getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.reset();

        try{
            mediaPlayer.setDataSource(getActivity().getApplicationContext(),trackUri);
        }catch(Exception e){
            Log.e("sense", "Error setting data source", e);
        }

        try{
            mediaPlayer.prepare();
        }catch (IOException e) {
            Log.e("sense", "Error at media player prepare", e);
        }

        mediaPlayer.start();

    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Player");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.release();
        mediaPlayer=null;
    }
}
