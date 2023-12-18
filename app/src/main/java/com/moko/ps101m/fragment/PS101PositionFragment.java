package com.moko.ps101m.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.moko.ps101m.databinding.Ps101mFragmentPosBinding;

public class PS101PositionFragment extends Fragment {
    private static final String TAG = PS101PositionFragment.class.getSimpleName();
    private Ps101mFragmentPosBinding mBind;

    public PS101PositionFragment() {
    }

    public static PS101PositionFragment newInstance() {
        return new PS101PositionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        mBind = Ps101mFragmentPosBinding.inflate(inflater, container, false);
        return mBind.getRoot();
    }
}
