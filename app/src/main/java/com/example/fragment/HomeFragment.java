package com.example.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.erhuo3.MainActivity;
import com.example.erhuo3.R;

/**
 * Created by Gary on 2017/11/25.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.home_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                Toast.makeText(activity, "hahha", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


}
