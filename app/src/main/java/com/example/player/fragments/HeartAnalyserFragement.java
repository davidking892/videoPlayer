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
public class HeartAnalyserFragement extends Fragment {


    public HeartAnalyserFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_heart_analyser_fragement, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Heart Analyser");
    }
}