package com.example.ruyn.loadcontact.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruyn.loadcontact.R;

/**
 * Created by Ruyn on 11/4/2015.
 */
public class GroupContactFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.groupcontact_fragment, container, false);

    }
}
