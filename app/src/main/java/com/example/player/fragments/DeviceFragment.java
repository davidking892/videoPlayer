package com.example.player.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment {


    public DeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Device");
    }
}
