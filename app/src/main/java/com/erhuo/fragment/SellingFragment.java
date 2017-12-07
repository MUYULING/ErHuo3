package com.erhuo.fragment;

/**
 * Created by msi on 2017/12/2.
 */

import android.accounts.AbstractAccountAuthenticator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erhuo.activitiy_erhuo.Me;
import com.erhuo.activitiy_erhuo.R;
import com.erhuo.adapter.ComHomeAdapter;
import com.erhuo.adapter.SellingRecycleAdapter;
import com.erhuo.entity.CommodityHome;

import java.util.ArrayList;
import java.util.List;

public class SellingFragment extends Fragment {

    private List<CommodityHome> commodityHomeList = new ArrayList<>();
    private SellingRecycleAdapter adapter;
    private View view;
    private List<CommodityHome> tmpList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_selling, container, false);
        Me activity = (Me) getActivity();
        adapter = new SellingRecycleAdapter(commodityHomeList, activity, 0);

        return view;
    }
}