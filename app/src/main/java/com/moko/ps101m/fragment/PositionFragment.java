package com.moko.ps101m.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.moko.ps101m.databinding.Lw006FragmentPosBinding;

public class PositionFragment extends Fragment {
    private static final String TAG = PositionFragment.class.getSimpleName();
    private Lw006FragmentPosBinding mBind;

    public PositionFragment() {
    }

    public static PositionFragment newInstance() {
        return new PositionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        mBind = Lw006FragmentPosBinding.inflate(inflater, container, false);
        return mBind.getRoot();
    }
}
