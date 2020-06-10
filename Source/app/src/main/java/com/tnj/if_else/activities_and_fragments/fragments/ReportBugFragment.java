package com.tnj.if_else.activities_and_fragments.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tnj.if_else.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportBugFragment extends Fragment {


    public ReportBugFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_bug, container, false);
    }

}
