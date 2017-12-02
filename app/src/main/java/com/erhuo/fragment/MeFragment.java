package com.erhuo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.erhuo.erhuo3.R;

/**
 * Created by bruce on 2016/11/1.
 * BaseFragment
 */

public class MeFragment extends Fragment {


    private View view;
    private ImageView xiugaiziliaoGO;
    private ImageView tongzhiGO;
    private ImageView jiaoyijiluGO;
    private ImageView sellCommodityGO;
    private ImageView requireCommodityGO;
    private ImageView favoriteGO;
    private ImageView logoutGO;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
       /* xiugaiziliaoGO = (ImageView) view.findViewById(R.id.my_xiugaiziliaoGO);
        tongzhiGO = (ImageView) view.findViewById(R.id.my_tongzhiGO);
        jiaoyijiluGO = (ImageView) view.findViewById(R.id.my_jiaoyijiluGO);
        sellCommodityGO = (ImageView) view.findViewById(R.id.my_sellCommodityGO);
        requireCommodityGO = (ImageView) view.findViewById(R.id.my_requireCommodityGO);
        favoriteGO = (ImageView) view.findViewById(R.id.my_favoriteGO);
        logoutGO = (ImageView) view.findViewById(R.id.my_LogOutGO);

        xiugaiziliaoGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity =(MainActivity)getActivity();
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivityForResult(intent, 1);
            }
        }); */

        return view;
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("data_return");
                    textview.setText(returnedData);
                    Log.d("MainActivity", returnedData);
                }
                break;
            default:
        } } */
}
