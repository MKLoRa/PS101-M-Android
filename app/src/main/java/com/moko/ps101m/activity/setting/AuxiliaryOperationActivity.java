package com.moko.ps101m.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.moko.ble.lib.MokoConstants;
import com.moko.ble.lib.event.ConnectStatusEvent;
import com.moko.ps101m.activity.BaseActivity;
import com.moko.ps101m.databinding.ActivityAuxiliaryOperationBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AuxiliaryOperationActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAuxiliaryOperationBinding mBind = ActivityAuxiliaryOperationBinding.inflate(getLayoutInflater());
        setContentView(mBind.getRoot());
        EventBus.getDefault().register(this);
        mBind.tvAxisDataReport.setOnClickListener(v -> {
            if (isWindowLocked()) return;
            startActivity(new Intent(this, ThreeAxisDataReportActivity.class));
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 200)
    public void onConnectStatusEvent(ConnectStatusEvent event) {
        final String action = event.getAction();
        runOnUiThread(() -> {
            if (MokoConstants.ACTION_DISCONNECTED.equals(action)) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onBack(View view) {
        finish();
    }

    public void onDownlinkForPos(View view) {
        if (isWindowLocked()) return;
        startActivity(new Intent(this, DownlinkForPosActivity.class));
    }

    public void onManDownDetection(View view) {
        if (isWindowLocked()) return;
        startActivity(new Intent(this, ManDownDetectionActivity.class));
    }

    public void onAlarmFunction(View view) {
        if (isWindowLocked()) return;
        startActivity(new Intent(this, AlarmFunctionActivity.class));
    }

}
