package com.erhuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erhuo.erhuo3.MainActivity;
import com.erhuo.erhuo3.R;
import com.erhuo.erhuo3.SearchActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by bruce on 2016/11/1.
 * BaseFragment
 */

public class SearchFragment extends Fragment {

    private View view;
    private ImageView imageview;
    private TextView textview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, null);
        imageview = (ImageView) view.findViewById(R.id.search);
        textview = (TextView) view.findViewById(R.id.search_text);
        imageview.setImageResource(R.drawable.ic_search);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity =(MainActivity)getActivity();
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    textview.setText(returnedData);
                    Log.d("FirstActivity", returnedData);
                }
                break;
            default:
        } }
}
