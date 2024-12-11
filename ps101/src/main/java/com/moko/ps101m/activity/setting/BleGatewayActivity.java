package com.moko.ps101m.activity.setting;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.SeekBar;

import com.moko.ble.lib.MokoConstants;
import com.moko.ble.lib.event.ConnectStatusEvent;
import com.moko.ble.lib.event.OrderTaskResponseEvent;
import com.moko.ble.lib.task.OrderTask;
import com.moko.ble.lib.task.OrderTaskResponse;
import com.moko.ble.lib.utils.MokoUtils;
import com.moko.ps101m.R;
import com.moko.ps101m.activity.BaseActivity;
import com.moko.ps101m.databinding.ActivityBleGatewayBinding;
import com.moko.ps101m.utils.ToastUtils;
import com.moko.support.ps101m.MokoSupport;
import com.moko.support.ps101m.OrderTaskAssembler;
import com.moko.support.ps101m.entity.OrderCHAR;
import com.moko.support.ps101m.entity.ParamsKeyEnum;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: jun.liu
 * @date: 2024/12/11 9:47
 * @des:
 */
public class BleGatewayActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {
    private ActivityBleGatewayBinding mBind;
    private boolean mReceiverTag = false;
    private final String FILTER_ASCII = "[ -~]*";
    private boolean isParamsError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = ActivityBleGatewayBinding.inflate(getLayoutInflater());
        setContentView(mBind.getRoot());
        EventBus.getDefault().register(this);
        // 注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        mReceiverTag = true;
        InputFilter inputFilter = (source, start, end, dest, dStart, dEnd) -> {
            if (!(source + "").matches(FILTER_ASCII)) return "";
            return null;
        };
        mBind.etBeaconFilter1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10), inputFilter});
        mBind.etBeaconFilter2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10), inputFilter});
        mBind.etDisplayFilter1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10), inputFilter});
        mBind.etDisplayFilter2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10), inputFilter});
        mBind.sbBeaconMinRssi.setOnSeekBarChangeListener(this);
        mBind.sbDisplayMinRssi.setOnSeekBarChangeListener(this);
        showSyncingProgressDialog();
        List<OrderTask> orderTasks = new ArrayList<>(10);
        orderTasks.add(OrderTaskAssembler.getGatewaySwitch());
        orderTasks.add(OrderTaskAssembler.getGracePeriod());
        orderTasks.add(OrderTaskAssembler.getBeaconMinDuration());
        orderTasks.add(OrderTaskAssembler.getBeaconMinRssi());
        orderTasks.add(OrderTaskAssembler.getBeaconFilter1());
        orderTasks.add(OrderTaskAssembler.getBeaconFilter2());
        orderTasks.add(OrderTaskAssembler.getDisplayMinDuration());
        orderTasks.add(OrderTaskAssembler.getDisplayMinRssi());
        orderTasks.add(OrderTaskAssembler.getDisplayFilter1());
        orderTasks.add(OrderTaskAssembler.getDisplayFilter2());
        MokoSupport.getInstance().sendOrder(orderTasks.toArray(new OrderTask[0]));
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 300)
    public void onConnectStatusEvent(ConnectStatusEvent event) {
        final String action = event.getAction();
        runOnUiThread(() -> {
            if (MokoConstants.ACTION_DISCONNECTED.equals(action)) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 300)
    public void onOrderTaskResponseEvent(OrderTaskResponseEvent event) {
        final String action = event.getAction();
        if (!MokoConstants.ACTION_CURRENT_DATA.equals(action))
            EventBus.getDefault().cancelEventDelivery(event);
        runOnUiThread(() -> {
            if (MokoConstants.ACTION_ORDER_FINISH.equals(action)) {
                dismissSyncProgressDialog();
            }
            if (MokoConstants.ACTION_ORDER_RESULT.equals(action)) {
                OrderTaskResponse response = event.getResponse();
                OrderCHAR orderCHAR = (OrderCHAR) response.orderCHAR;
                byte[] value = response.responseValue;
                if (orderCHAR == OrderCHAR.CHAR_PARAMS) {
                    if (value.length >= 4) {
                        int header = value[0] & 0xFF;// 0xED
                        int flag = value[1] & 0xFF;// read or write
                        int cmd = value[2] & 0xFF;
                        if (header != 0xED) return;
                        ParamsKeyEnum configKeyEnum = ParamsKeyEnum.fromParamKey(cmd);
                        if (configKeyEnum == null) return;
                        int length = value[3] & 0xFF;
                        if (flag == 0x01) {
                            // write
                            int result = value[4] & 0xFF;
                            switch (configKeyEnum) {
                                case KEY_GATEWAY_SWITCH:
                                case KEY_GRACE_PERIOD:
                                case KEY_BEACON_MIN_DURATION:
                                case KEY_BEACON_MIN_RSSI:
                                case KEY_BEACON_FILTER1:
                                case KEY_BEACON_FILTER2:
                                case KEY_DISPLAY_MIN_DURATION:
                                case KEY_DISPLAY_MIN_RSSI:
                                case KEY_DISPLAY_FILTER1:
                                    if (result != 0x01) isParamsError = true;
                                    break;

                                case KEY_DISPLAY_FILTER2:
                                    if (result != 0x01) isParamsError = true;
                                    if (isParamsError) {
                                        ToastUtils.showToast(BleGatewayActivity.this, "Opps！Save failed. Please check the input characters and try again.");
                                    } else {
                                        ToastUtils.showToast(this, "Save Successfully！");
                                    }
                                    break;
                            }
                        } else if (flag == 0x00) {
                            // read
                            if (length == 0) return;
                            switch (configKeyEnum) {
                                case KEY_GATEWAY_SWITCH:
                                    mBind.cbFunSwitch.setChecked(value[4] == 1);
                                    break;

                                case KEY_GRACE_PERIOD:
                                    mBind.etGracePeriod.setText(String.valueOf(MokoUtils.toInt(Arrays.copyOfRange(value, 4, value.length))));
                                    mBind.etGracePeriod.setSelection(mBind.etGracePeriod.getText().length());
                                    break;

                                case KEY_BEACON_MIN_DURATION:
                                    mBind.etBeaconMinDuration.setText(String.valueOf(value[4] & 0xff));
                                    mBind.etBeaconMinDuration.setSelection(mBind.etBeaconMinDuration.getText().length());
                                    break;

                                case KEY_BEACON_MIN_RSSI:
                                    mBind.sbBeaconMinRssi.setProgress(value[4] + 127);
                                    mBind.tvBeaconMinRssi.setText(value[4] + "dBm");
                                    break;

                                case KEY_BEACON_FILTER1:
                                    mBind.etBeaconFilter1.setText(new String(Arrays.copyOfRange(value, 4, value.length)));
                                    mBind.etBeaconFilter1.setSelection(mBind.etBeaconFilter1.getText().length());
                                    break;

                                case KEY_BEACON_FILTER2:
                                    mBind.etBeaconFilter2.setText(new String(Arrays.copyOfRange(value, 4, value.length)));
                                    mBind.etBeaconFilter2.setSelection(mBind.etBeaconFilter2.getText().length());
                                    break;

                                case KEY_DISPLAY_MIN_DURATION:
                                    mBind.etDisplayMinDuration.setText(String.valueOf(value[4] & 0xff));
                                    mBind.etDisplayMinDuration.setSelection(mBind.etDisplayMinDuration.getText().length());
                                    break;

                                case KEY_DISPLAY_MIN_RSSI:
                                    mBind.sbDisplayMinRssi.setProgress(value[4] + 127);
                                    mBind.tvDisplayMinRssi.setText(value[4] + "dBm");
                                    break;

                                case KEY_DISPLAY_FILTER1:
                                    mBind.etDisplayFilter1.setText(new String(Arrays.copyOfRange(value, 4, value.length)));
                                    mBind.etDisplayFilter1.setSelection(mBind.etDisplayFilter1.getText().length());
                                    break;

                                case KEY_DISPLAY_FILTER2:
                                    mBind.etDisplayFilter2.setText(new String(Arrays.copyOfRange(value, 4, value.length)));
                                    mBind.etDisplayFilter2.setSelection(mBind.etDisplayFilter2.getText().length());
                                    break;
                            }
                        }
                    }
                }
            }
        });
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    if (blueState == BluetoothAdapter.STATE_TURNING_OFF) {
                        dismissSyncProgressDialog();
                        finish();
                    }
                }
            }
        }
    };

    public void onSave(View v) {
        if (isValid()) {
            isParamsError = false;
            showSyncingProgressDialog();
            List<OrderTask> orderTasks = new ArrayList<>(10);
            orderTasks.add(OrderTaskAssembler.setGatewaySwitch(mBind.cbFunSwitch.isChecked() ? 1 : 0));
            orderTasks.add(OrderTaskAssembler.setGracePeriod(Integer.parseInt(mBind.etGracePeriod.getText().toString())));
            orderTasks.add(OrderTaskAssembler.setBeaconMinDuration(Integer.parseInt(mBind.etBeaconMinDuration.getText().toString())));
            orderTasks.add(OrderTaskAssembler.setBeaconMinRssi(mBind.sbBeaconMinRssi.getProgress() - 127));
            String beaconFilter1 = TextUtils.isEmpty(mBind.etBeaconFilter1.getText()) ? null : mBind.etBeaconFilter1.getText().toString();
            String beaconFilter2 = TextUtils.isEmpty(mBind.etBeaconFilter2.getText()) ? null : mBind.etBeaconFilter2.getText().toString();
            orderTasks.add(OrderTaskAssembler.setBeaconFilter1(beaconFilter1));
            orderTasks.add(OrderTaskAssembler.setBeaconFilter2(beaconFilter2));
            orderTasks.add(OrderTaskAssembler.setDisplayMinDuration(Integer.parseInt(mBind.etDisplayMinDuration.getText().toString())));
            orderTasks.add(OrderTaskAssembler.setDisplayMinRssi(mBind.sbDisplayMinRssi.getProgress() - 127));
            String displayFilter1 = TextUtils.isEmpty(mBind.etDisplayFilter1.getText()) ? null : mBind.etDisplayFilter1.getText().toString();
            String displayFilter2 = TextUtils.isEmpty(mBind.etDisplayFilter2.getText()) ? null : mBind.etDisplayFilter2.getText().toString();
            orderTasks.add(OrderTaskAssembler.setDisplayFilter1(displayFilter1));
            orderTasks.add(OrderTaskAssembler.setDisplayFilter2(displayFilter2));
            MokoSupport.getInstance().sendOrder(orderTasks.toArray(new OrderTask[0]));
        } else {
            ToastUtils.showToast(this, "params error");
        }
    }

    private boolean isValid() {
        if (TextUtils.isEmpty(mBind.etGracePeriod.getText())) return false;
        if (TextUtils.isEmpty(mBind.etBeaconMinDuration.getText())) return false;
        return !TextUtils.isEmpty(mBind.etDisplayMinDuration.getText());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiverTag) {
            mReceiverTag = false;
            // 注销广播
            unregisterReceiver(mReceiver);
        }
        EventBus.getDefault().unregister(this);
    }

    public void onBack(View view) {
        finish();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.sbBeaconMinRssi) {
            mBind.tvBeaconMinRssi.setText((progress - 127) + "dBm");
        } else if (seekBar.getId() == R.id.sbDisplayMinRssi) {
            mBind.tvDisplayMinRssi.setText((progress - 127) + "dBm");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
