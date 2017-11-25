package com.example.erhuo3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by bruce on 2016/11/1.
 * BaseFragment
 */

public class SearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        ImageView tvInfo = (ImageView) view.findViewById(R.id.search);
        tvInfo.setImageResource(R.drawable.ic_search);
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity =(MainActivity)getActivity();
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
