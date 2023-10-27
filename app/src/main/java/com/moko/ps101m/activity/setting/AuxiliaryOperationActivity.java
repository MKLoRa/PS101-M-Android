package com.moko.ps101m.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.moko.ble.lib.MokoConstants;
import com.moko.ble.lib.event.ConnectStatusEvent;
import com.moko.ble.lib.event.OrderTaskResponseEvent;
import com.moko.ble.lib.task.OrderTaskResponse;
import com.moko.ps101m.activity.Lw006BaseActivity;
import com.moko.ps101m.databinding.Lw006ActivityAuxiliaryOperationBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AuxiliaryOperationActivity extends Lw006BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lw006ActivityAuxiliaryOperationBinding mBind = Lw006ActivityAuxiliaryOperationBinding.inflate(getLayoutInflater());
        setContentView(mBind.getRoot());
        EventBus.getDefault().register(this);
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
