package com.sam.main.student;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sam.R;

/**
 *用于显示未选课时界面的fragment
 */
public class NoClass extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.noclass_frag,container,false);
        return rootView;
    }
}