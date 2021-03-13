package com.example.player.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.player.R;
import com.example.player.fragments.AlbumFragment;
import com.example.player.fragments.ArtistFragment;
import com.example.player.fragments.SongFragment;

public class HomeAdapter extends FragmentPagerAdapter {

    private String tabTitle[] ={"Songs","Album","Artist"};

    public HomeAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
              case 0:
               return new SongFragment();
              case 1:
                return new AlbumFragment();
              case 2:
                return new ArtistFragment();
              default:
                return new SongFragment();
        }


    }

    @Override
    public int getCount() {
        return tabTitle.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }





}
