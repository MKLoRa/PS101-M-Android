package com.moko.ps101m.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moko.ps101m.databinding.Lw006FragmentNetworkBinding;

public class NetworkFragment extends Fragment {
    private static final String TAG = NetworkFragment.class.getSimpleName();
    private Lw006FragmentNetworkBinding mBind;

    public NetworkFragment() {
    }

    public static NetworkFragment newInstance() {
        return new NetworkFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        mBind = Lw006FragmentNetworkBinding.inflate(inflater, container, false);
        return mBind.getRoot();
    }

    public void setLoRaInfo(String loraInfo) {
        mBind.tvLoraInfo.setText(loraInfo);
    }

    public void setLoraStatus(int networkCheck) {
        String networkCheckDisPlay = "";
        switch (networkCheck) {
            case 0:
                networkCheckDisPlay = "Connecting";
                break;
            case 1:
                networkCheckDisPlay = "Connected";
                break;
        }
        mBind.tvLoraStatus.setText(networkCheckDisPlay);
    }
}
