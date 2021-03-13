package com.example.player.adapters;

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

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private Cursor cursor;
    private Context context;

    public AlbumAdapter(Cursor cursor, Context context) {
        this.cursor = cursor;
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.album_name,parent,false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)){
            throw new IllegalStateException("couldn't move to position"+position);
        }

        holder.txt_AlbumName.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
        holder.txt_ArtistName.setText(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));

        String album_art=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));

        Glide.with(context)
                .load(album_art)
                .apply(new RequestOptions()
                .placeholder(R.drawable.album_art)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(holder.Album_ART);

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder{

        TextView txt_ArtistName,txt_AlbumName;
        ImageView Album_ART;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ArtistName = itemView.findViewById(R.id.albumArtist);
            txt_AlbumName = itemView.findViewById(R.id.AlbumName);
            Album_ART = itemView.findViewById(R.id.imageView_Album_AlbumArt);
        }
    }

}
