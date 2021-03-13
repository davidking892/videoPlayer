package com.example.player.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.player.R;

import java.util.HashMap;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private Context context;
    private Cursor cursor;
    private HashMap<String,String> albumartData;

    public ArtistAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;

        albumartData=new HashMap<>();

        String[] projection={
           MediaStore.Audio.Albums.ARTIST,
           MediaStore.Audio.Albums.ALBUM_ART
        };

        ContentResolver contentResolver=context.getContentResolver();
        try(Cursor albumArtCursor=contentResolver.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        )){
            while (albumArtCursor.moveToNext()){
                albumartData.put(albumArtCursor.getString(albumArtCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)),
                       albumArtCursor.getString(albumArtCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
            }
        }

    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.artist_name,parent,false);
       return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        if(! cursor.moveToPosition(position)){
            throw new IllegalStateException("couldn't move cursor to position"+position);
        }

        holder.txt_artistName.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
        holder.txt_no_of_songs.setText(String.format("%s Songs",cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS))));

        Glide.with(context)
                .load(albumartData.get(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST))))
                .apply(new RequestOptions()
                .placeholder(R.drawable.album_art)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true))
                .thumbnail(0.5f)
                .transition(new DrawableTransitionOptions()
                .crossFade())
                .into(holder.img_ArtistAlbumArt);

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder{

        TextView txt_artistName,txt_no_of_songs;
        ImageView img_ArtistAlbumArt;


        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_artistName = itemView.findViewById(R.id.artistName);
            txt_no_of_songs = itemView.findViewById(R.id.no_of_songs);
            img_ArtistAlbumArt = itemView.findViewById(R.id.imageView_Artist_AlbumArt);
        }
    }

}
