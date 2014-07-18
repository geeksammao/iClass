package com.sam.main.student;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sam.R;

/**
 * 显示已选课程的fragment
 */
public class ClassSelected extends Fragment {
    public ClassSelected(){

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.class_slecred_frag, container, false);
    }
}