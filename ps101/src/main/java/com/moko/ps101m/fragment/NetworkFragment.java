package com.moko.ps101m.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.moko.ps101m.activity.setting.NetworkSettingsActivity;
import com.moko.ps101m.databinding.FragmentNetworkBinding;

public class NetworkFragment extends Fragment {
    private static final String TAG = NetworkFragment.class.getSimpleName();
    private FragmentNetworkBinding mBind;
    private int version;

    public NetworkFragment() {
    }

    public static NetworkFragment newInstance() {
        return new NetworkFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        mBind = FragmentNetworkBinding.inflate(inflater, container, false);
        mBind.tvNetworkSetting.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), NetworkSettingsActivity.class);
            intent.putExtra("version", version);
            startActivity(intent);
        });
        return mBind.getRoot();
    }

    public void setNetworkReconnectInterval(int interval) {
        mBind.etInterval.setText(String.valueOf(interval));
        mBind.etInterval.setSelection(mBind.etInterval.getText().length());
    }

    public void setMqttConnectionStatus(int status) {
        mBind.tvMqttConnectionStatus.setText(status == 1 ? "Connected" : "Connecting");
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setNetworkStatus(int networkCheck) {
        String networkCheckDisPlay = "";
        switch (networkCheck) {
            case 0:
                networkCheckDisPlay = "Connecting";
                break;
            case 1:
                networkCheckDisPlay = "Connected";
                break;
            case 2:
                networkCheckDisPlay = "No SIM";
                break;
        }
        mBind.tvCellularConnectionStatus.setText(networkCheckDisPlay);
    }

    public boolean isValid() {
        if (TextUtils.isEmpty(mBind.etInterval.getText())) return false;
        String intervalStr = mBind.etInterval.getText().toString();
        int interval = Integer.parseInt(intervalStr);
        return interval <= 100;
    }

    public int getReconnectInterval() {
        return Integer.parseInt(mBind.etInterval.getText().toString());
    }
}
