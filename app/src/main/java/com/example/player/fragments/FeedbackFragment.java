package com.example.player.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {


    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EditText feedback=getActivity().findViewById(R.id.feed_feedback);
        Button submit=getActivity().findViewById(R.id.feed_submit);
        submit.setOnClickListener(v->{
            Intent sendMessage=new Intent(Intent.ACTION_SEND);
            sendMessage.putExtra(Intent.EXTRA_SUBJECT,"Feedback Sense");
            sendMessage.putExtra(Intent.EXTRA_TEXT,feedback.getText().toString());
            sendMessage.putExtra(Intent.EXTRA_EMAIL,"pawankumar03082000@gmail.com");
            sendMessage.setType("message/rfc822");
            Intent chooser=Intent.createChooser(sendMessage,"Select Email Application");
            startActivity(chooser);
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Feedback");
    }
}
