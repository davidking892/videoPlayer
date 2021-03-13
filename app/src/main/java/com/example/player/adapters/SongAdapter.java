package com.example.player.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.player.R;
import com.example.player.fragments.PlayerFragment;

import java.util.HashMap;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private Cursor cursor;
    private Context context;
    private HashMap<String,String> albumartData;
    private Bundle PlayerSongInfo=new Bundle();

    public SongAdapter(Context context, Cursor cursor) {
        this.context=context;
        this.cursor=cursor;

        albumartData=new HashMap<>();

        String[] projection={
               MediaStore.Audio.Albums._ID,
               MediaStore.Audio.Albums.ALBUM_ART
        };

        ContentResolver contentResolver=context.getContentResolver();
        Cursor albumArtCursor=contentResolver.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        while (albumArtCursor.moveToNext()){
                albumartData.put(albumArtCursor.getString(albumArtCursor.getColumnIndex(MediaStore.Audio.Albums._ID)),
                albumArtCursor.getString(albumArtCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
                    );
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.song_name,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(!cursor.moveToPosition(position)){
            throw new IllegalStateException("couldn't move cursor to position"+position);
        }

        final String title,artist,album,albumart;
        final long _id, time;
        final int seconds, min;

        _id=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        album=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
        artist=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        time=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
        albumart=albumartData.get(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)))+"";

        holder.txt_song_name.setText(title);
        holder.txt_artist.setText(artist);
        holder.txt_album.setText(album);

        seconds= (int) (time/1000);
        min=seconds/60;
        holder.txt_duration.setText(min+":"+(seconds%60));

        holder.txt_song_name.setOnClickListener(v->{
                PlayerSongInfo.putLong(MediaStore.Audio.Media._ID,_id);
                PlayerSongInfo.putString(MediaStore.Audio.Media.TITLE,title);
                PlayerSongInfo.putString(MediaStore.Audio.Media.ARTIST,artist);
                PlayerSongInfo.putString(MediaStore.Audio.Media.ALBUM,album);
                PlayerSongInfo.putString(MediaStore.Audio.Media.DURATION,+min+":"+(seconds%60));
                PlayerSongInfo.putString(MediaStore.Audio.Media.ALBUM,album);

                   PlayerFragment playerFragment=new PlayerFragment();
                   playerFragment.setArguments(PlayerSongInfo);
                   FragmentTransaction fragmentTransaction=((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                   fragmentTransaction.replace(R.id.frame_main,playerFragment);
                   fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit();

        });

           Glide.with(context)
                .load(albumart)
                .apply(new RequestOptions()
                   .placeholder(R.drawable.album_art)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                )
                .thumbnail(0.5f)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(holder.img_AlbumArt);

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_song_name,txt_duration,txt_artist,txt_album;
        ImageView img_AlbumArt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_song_name = itemView.findViewById(R.id.SongName);
            txt_duration = itemView.findViewById(R.id.time);
            txt_album = itemView.findViewById(R.id.Album);
            txt_artist = itemView.findViewById(R.id.Artist);
            img_AlbumArt = itemView.findViewById(R.id.imageView_AlbumArt);
        }
    }

}
