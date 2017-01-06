package com.yikouguaishou.peanutfm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yikouguaishou.peanutfm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnchorDynamicFragment extends Fragment {
    private String baseUrl = "http://fsapp.linker.cc";
    private String anchorId;

    public AnchorDynamicFragment(String anchorId) {
        this.anchorId = anchorId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_anchor_dynamic, container, false);
        getAnchorDynamicList();
        return view;
    }

    /**
     * 获取主播动态
     */
    private void getAnchorDynamicList() {

    }

}
