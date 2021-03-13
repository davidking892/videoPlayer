package com.example.player.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.player.R;
import com.example.player.activities.MainActivity;
import com.example.player.adapters.HomeAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final FragmentManager fragmentManager=getChildFragmentManager();
         ViewPager viewPager=Objects.requireNonNull(getActivity().findViewById(R.id.viewpager));
         viewPager.setAdapter(new HomeAdapter(fragmentManager));

        TabLayout tabLayout=getActivity().findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab=getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(view->{
             PlayerFragment playerFragment=new PlayerFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_main,playerFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Home");

    }
}
