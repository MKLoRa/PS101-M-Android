package com.moko.ps101m.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.moko.ps101m.activity.DeviceInfoActivity;
import com.moko.ps101m.databinding.Lw006FragmentGeneralBinding;
import com.moko.support.ps101m.LoRaLW006MokoSupport;
import com.moko.support.ps101m.OrderTaskAssembler;

public class GeneralFragment extends Fragment {
    private static final String TAG = GeneralFragment.class.getSimpleName();
    private Lw006FragmentGeneralBinding mBind;

    private DeviceInfoActivity activity;

    public GeneralFragment() {
    }


    public static GeneralFragment newInstance() {
        GeneralFragment fragment = new GeneralFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        mBind = Lw006FragmentGeneralBinding.inflate(inflater, container, false);
        activity = (DeviceInfoActivity) getActivity();
        return mBind.getRoot();
    }

    public void setHeartbeatInterval(int interval) {
        mBind.etHeartbeatInterval.setText(String.valueOf(interval));
        mBind.etHeartbeatInterval.setSelection(mBind.etHeartbeatInterval.getText().length());
    }

    public boolean isValid() {
        if (TextUtils.isEmpty(mBind.etHeartbeatInterval.getText())) return false;
        final String intervalStr = mBind.etHeartbeatInterval.getText().toString();
        final int interval = Integer.parseInt(intervalStr);
        return interval >= 1 && interval <= 14400;
    }

    public void saveParams() {
        final String intervalStr = mBind.etHeartbeatInterval.getText().toString();
        final int interval = Integer.parseInt(intervalStr);
        LoRaLW006MokoSupport.getInstance().sendOrder(OrderTaskAssembler.setHeartBeatInterval(interval));
    }
}