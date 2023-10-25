package com.moko.ps101m.activity.setting;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.moko.ble.lib.MokoConstants;
import com.moko.ble.lib.event.ConnectStatusEvent;
import com.moko.ble.lib.event.OrderTaskResponseEvent;
import com.moko.ble.lib.task.OrderTask;
import com.moko.ble.lib.task.OrderTaskResponse;
import com.moko.ble.lib.utils.MokoUtils;
import com.moko.ps101m.R;
import com.moko.ps101m.activity.LoRaLW006MainActivity;
import com.moko.ps101m.activity.Lw006BaseActivity;
import com.moko.ps101m.adapter.NetworkFragmentAdapter;
import com.moko.ps101m.databinding.ActivityNetworkSettingBinding;
import com.moko.ps101m.dialog.AlertMessageDialog;
import com.moko.ps101m.dialog.BottomDialog;
import com.moko.ps101m.entity.NetworkSettings;
import com.moko.ps101m.fragment.GeneralDeviceFragment;
import com.moko.ps101m.fragment.LWTFragment;
import com.moko.ps101m.fragment.SSLDeviceFragment;
import com.moko.ps101m.fragment.UserDeviceFragment;
import com.moko.ps101m.utils.FileUtils;
import com.moko.ps101m.utils.ToastUtils;
import com.moko.ps101m.utils.Utils;
import com.moko.support.ps101m.LoRaLW006MokoSupport;
import com.moko.support.ps101m.OrderTaskAssembler;
import com.moko.support.ps101m.entity.OrderCHAR;
import com.moko.support.ps101m.entity.ParamsKeyEnum;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class NetworkSettingsActivity extends Lw006BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private ActivityNetworkSettingBinding mBind;
    private final String FILTER_ASCII = "[ -~]*";
    private GeneralDeviceFragment generalFragment;
    private UserDeviceFragment userFragment;
    private SSLDeviceFragment sslFragment;
    private LWTFragment lwtFragment;
    private ArrayList<Fragment> fragments;
    private boolean mSavedParamsError;
    private String expertFilePath;
    private boolean isFileError;
    private final String[] netWorkFormatArray = {"NB-IOT", "eMTC", "NB-IOT->eMTC", "eMTC->NB-IOT"};
    private int networkFormatSelect;
    private NetworkSettings networkSettings = new NetworkSettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = ActivityNetworkSettingBinding.inflate(getLayoutInflater());
        setContentView(mBind.getRoot());
        InputFilter filter = (source, start, end, dest, dStart, dEnd) -> {
            if (!(source + "").matches(FILTER_ASCII)) {
                return "";
            }
            return null;
        };
        mBind.etMqttHost.setFilters(new InputFilter[]{new InputFilter.LengthFilter(64), filter});
        mBind.etMqttClientId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(64), filter});
        mBind.etMqttSubscribeTopic.setFilters(new InputFilter[]{new InputFilter.LengthFilter(128), filter});
        mBind.etMqttPublishTopic.setFilters(new InputFilter[]{new InputFilter.LengthFilter(128), filter});
        createFragment();
        NetworkFragmentAdapter adapter = new NetworkFragmentAdapter(this);
        adapter.setFragmentList(fragments);
        mBind.vpMqtt.setAdapter(adapter);
        mBind.vpMqtt.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mBind.rbGeneral.setChecked(true);
                } else if (position == 1) {
                    mBind.rbUser.setChecked(true);
                } else if (position == 2) {
                    mBind.rbSsl.setChecked(true);
                } else if (position == 3) {
                    mBind.rbLwt.setChecked(true);
                }
            }
        });
        mBind.vpMqtt.setOffscreenPageLimit(4);
        mBind.rgMqtt.setOnCheckedChangeListener(this);
        expertFilePath = LoRaLW006MainActivity.PATH_LOGCAT + File.separator + "export" + File.separator + "Settings for Device.xlsx";
        showSyncingProgressDialog();
        mBind.title.postDelayed(() -> {
            ArrayList<OrderTask> orderTasks = new ArrayList<>();
            orderTasks.add(OrderTaskAssembler.getMQTTHost());
            orderTasks.add(OrderTaskAssembler.getMQTTPort());
            orderTasks.add(OrderTaskAssembler.getMQTTClientId());
            orderTasks.add(OrderTaskAssembler.getMQTTSubscribeTopic());
            orderTasks.add(OrderTaskAssembler.getMQTTPublishTopic());
            orderTasks.add(OrderTaskAssembler.getMQTTCleanSession());
            orderTasks.add(OrderTaskAssembler.getMQTTQos());
            orderTasks.add(OrderTaskAssembler.getMQTTKeepAlive());
            orderTasks.add(OrderTaskAssembler.getApn());
            orderTasks.add(OrderTaskAssembler.getNetworkFormat());
            orderTasks.add(OrderTaskAssembler.getMQTTUsername());
            orderTasks.add(OrderTaskAssembler.getMQTTPassword());
            orderTasks.add(OrderTaskAssembler.getMQTTConnectMode());
            orderTasks.add(OrderTaskAssembler.getMQTTLwtEnable());
            orderTasks.add(OrderTaskAssembler.getMQTTLwtRetain());
            orderTasks.add(OrderTaskAssembler.getMQTTLwtQos());
            orderTasks.add(OrderTaskAssembler.getMQTTLwtTopic());
            orderTasks.add(OrderTaskAssembler.getMQTTLwtPayload());
            LoRaLW006MokoSupport.getInstance().sendOrder(orderTasks.toArray(new OrderTask[]{}));
        }, 500);
        mBind.tvNetworkFormat.setOnClickListener(v -> onNetworkFormatClick());
    }

    /**
     * 网络制式选择
     */
    private void onNetworkFormatClick() {
        if (isWindowLocked()) return;
        BottomDialog dialog = new BottomDialog();
        dialog.setDatas(new ArrayList<>(Arrays.asList(netWorkFormatArray)), networkFormatSelect);
        dialog.setListener(value -> {
            networkFormatSelect = value;
            mBind.tvNetworkFormat.setText(netWorkFormatArray[value]);
        });
        dialog.show(getSupportFragmentManager());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectStatusEvent(ConnectStatusEvent event) {
        String action = event.getAction();
        if (MokoConstants.ACTION_DISCONNECTED.equals(action)) {
            dismissSyncProgressDialog();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderTaskResponseEvent(OrderTaskResponseEvent event) {
        final String action = event.getAction();
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
                    if (header == 0xEE) {
                        ParamsKeyEnum configKeyEnum = ParamsKeyEnum.fromParamKey(cmd);
                        if (configKeyEnum == null) return;
                        if (flag == 0x01) {
                            // write
                            int result = value[4] & 0xFF;
                            switch (configKeyEnum) {
                                case KEY_MQTT_CA:
                                case KEY_MQTT_CLIENT_CERT:
                                    if (result != 1) {
                                        mSavedParamsError = true;
                                    }
                                    break;
                                case KEY_MQTT_CLIENT_KEY:
                                    if (mSavedParamsError) {
                                        ToastUtils.showToast(this, "Setup failed！");
                                    } else {
                                        ToastUtils.showToast(this, "Setup succeed！");
                                    }
                                    break;
                            }
                        }
                    }
                    if (header == 0xED) {
                        ParamsKeyEnum configKeyEnum = ParamsKeyEnum.fromParamKey(cmd);
                        if (configKeyEnum == null) return;
                        int length = value[3] & 0xFF;
                        if (flag == 0x01) {
                            // write
                            int result = value[4] & 0xFF;
                            switch (configKeyEnum) {
                                case KEY_MQTT_HOST:
                                case KEY_MQTT_PORT:
                                case KEY_MQTT_CLIENT_ID:
                                case KEY_SUBSCRIBE_TOPIC:
                                case KEY_PUBLISH_TOPIC:
                                case KEY_MQTT_CLEAN_SESSION:
                                case KEY_MQTT_QOS:
                                case KEY_MQTT_KEEP_ALIVE:
                                case KEY_APN:
                                case KEY_NETWORK_FORMAT:
                                case KEY_MQTT_USERNAME:
                                case KEY_MQTT_PASSWORD:
                                case KEY_CONNECT_MODE:
                                case KEY_MQTT_LWT_ENABLE:
                                case KEY_MQTT_LWT_RETAIN:
                                case KEY_MQTT_LWT_QOS:
                                case KEY_MQTT_LWT_TOPIC:
                                    if (result != 1) {
                                        mSavedParamsError = true;
                                    }
                                    break;
                                case KEY_MQTT_LWT_PAYLOAD:
                                    if (result != 1) {
                                        mSavedParamsError = true;
                                    }
                                    if (mSavedParamsError) {
                                        ToastUtils.showToast(this, "Setup failed！");
                                    } else {
                                        ToastUtils.showToast(this, "Setup succeed！");
                                    }
                                    break;
                            }
                        }
                        if (flag == 0x00) {
                            if (length == 0) return;
                            // read
                            switch (configKeyEnum) {
                                case KEY_MQTT_HOST:
                                    mBind.etMqttHost.setText(new String(Arrays.copyOfRange(value, 4, value.length)));
                                    mBind.etMqttHost.setSelection(mBind.etMqttHost.getText().length());
                                    break;
                                case KEY_MQTT_PORT:
                                    mBind.etMqttPort.setText(String.valueOf(MokoUtils.toInt(Arrays.copyOfRange(value, 4, value.length))));
                                    mBind.etMqttPort.setSelection(mBind.etMqttPort.getText().length());
                                    break;
                                case KEY_MQTT_CLIENT_ID:
                                    String clientId = new String(Arrays.copyOfRange(value, 4, value.length));
                                    mBind.etMqttClientId.setText(clientId);
                                    mBind.etMqttClientId.setSelection(mBind.etMqttClientId.getText().length());
                                    break;
                                case KEY_SUBSCRIBE_TOPIC:
                                    mBind.etMqttSubscribeTopic.setText(new String(Arrays.copyOfRange(value, 4, value.length)));
                                    mBind.etMqttSubscribeTopic.setSelection(mBind.etMqttSubscribeTopic.getText().length());
                                    break;
                                case KEY_PUBLISH_TOPIC:
                                    mBind.etMqttPublishTopic.setText(new String(Arrays.copyOfRange(value, 4, value.length)));
                                    mBind.etMqttPublishTopic.setSelection(mBind.etMqttPublishTopic.getText().length());
                                    break;
                                case KEY_APN:
                                    mBind.etApn.setText(new String(Arrays.copyOfRange(value, 4, value.length)));
                                    mBind.etApn.setSelection(mBind.etApn.getText().length());
                                    break;
                                case KEY_NETWORK_FORMAT:
                                    networkFormatSelect = value[4] & 0xff;
                                    mBind.tvNetworkFormat.setText(netWorkFormatArray[networkFormatSelect]);
                                    break;
                                case KEY_MQTT_CLEAN_SESSION:
                                    generalFragment.setCleanSession((value[4] & 0xff) == 1);
                                    break;
                                case KEY_MQTT_QOS:
                                    generalFragment.setQos(value[4] & 0xff);
                                    break;
                                case KEY_MQTT_KEEP_ALIVE:
                                    generalFragment.setKeepAlive(value[4] & 0xff);
                                    break;
                                case KEY_MQTT_USERNAME:
                                    userFragment.setUserName(new String(Arrays.copyOfRange(value, 4, value.length)));
                                    break;
                                case KEY_MQTT_PASSWORD:
                                    userFragment.setPassword(new String(Arrays.copyOfRange(value, 4, value.length)));
                                    break;
                                case KEY_CONNECT_MODE:
                                    sslFragment.setConnectMode(value[4] & 0xff);
                                    break;
                                case KEY_MQTT_LWT_ENABLE:
                                    lwtFragment.setLwtEnable((value[4] & 0xff) == 1);
                                    break;
                                case KEY_MQTT_LWT_RETAIN:
                                    lwtFragment.setLwtRetain((value[4] & 0xff) == 1);
                                    break;
                                case KEY_MQTT_LWT_TOPIC:
                                    lwtFragment.setTopic(new String(Arrays.copyOfRange(value, 4, value.length)));
                                    break;
                                case KEY_MQTT_LWT_PAYLOAD:
                                    lwtFragment.setPayload(new String(Arrays.copyOfRange(value, 4, value.length)));
                                    break;
                                case KEY_MQTT_LWT_QOS:
                                    lwtFragment.setQos(value[4] & 0xff);
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void createFragment() {
        fragments = new ArrayList<>();
        generalFragment = GeneralDeviceFragment.newInstance();
        userFragment = UserDeviceFragment.newInstance();
        sslFragment = SSLDeviceFragment.newInstance();
        lwtFragment = LWTFragment.newInstance();
        fragments.add(generalFragment);
        fragments.add(userFragment);
        fragments.add(sslFragment);
        fragments.add(lwtFragment);
    }

    public void onBack(View view) {
        back();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.rb_general) {
            mBind.vpMqtt.setCurrentItem(0);
        } else if (checkedId == R.id.rb_user) {
            mBind.vpMqtt.setCurrentItem(1);
        } else if (checkedId == R.id.rb_ssl) {
            mBind.vpMqtt.setCurrentItem(2);
        } else if (checkedId == R.id.rb_lwt) {
            mBind.vpMqtt.setCurrentItem(3);
        }
    }

    public void onSave(View view) {
        if (isWindowLocked()) return;
        if (isParaError()) return;
        setMQTTDeviceConfig();
    }

    private boolean isParaError() {
        if (TextUtils.isEmpty(mBind.etMqttHost.getText())) {
            ToastUtils.showToast(this, getString(R.string.mqtt_verify_host));
            return true;
        }
        if (TextUtils.isEmpty(mBind.etMqttPort.getText())) {
            ToastUtils.showToast(this, getString(R.string.mqtt_verify_port_empty));
            return true;
        }
        String port = mBind.etMqttPort.getText().toString();
        if (Integer.parseInt(port) < 1 || Integer.parseInt(port) > 65535) {
            ToastUtils.showToast(this, getString(R.string.mqtt_verify_port));
            return true;
        }
        if (TextUtils.isEmpty(mBind.etMqttClientId.getText())) {
            ToastUtils.showToast(this, getString(R.string.mqtt_verify_client_id_empty));
            return true;
        }
        if (TextUtils.isEmpty(mBind.etMqttSubscribeTopic.getText())) {
            ToastUtils.showToast(this, getString(R.string.mqtt_verify_topic_subscribe));
            return true;
        }
        if (TextUtils.isEmpty(mBind.etMqttPublishTopic.getText())) {
            ToastUtils.showToast(this, getString(R.string.mqtt_verify_topic_publish));
            return true;
        }
        String topicSubscribe = mBind.etMqttSubscribeTopic.getText().toString();
        String topicPublish = mBind.etMqttPublishTopic.getText().toString();
        if (topicPublish.equals(topicSubscribe)) {
            ToastUtils.showToast(this, "Subscribed and published topic can't be same !");
            return true;
        }
        return !generalFragment.isValid() || !lwtFragment.isValid();
    }

    private void setMQTTDeviceConfig() {
        try {
            showSyncingProgressDialog();
            ArrayList<OrderTask> orderTasks = new ArrayList<>(16);
            orderTasks.add(OrderTaskAssembler.setMQTTHost(mBind.etMqttHost.getText().toString().trim()));
            orderTasks.add(OrderTaskAssembler.setMQTTPort(Integer.parseInt(mBind.etMqttPort.getText().toString().trim())));
            orderTasks.add(OrderTaskAssembler.setMQTTClientId(mBind.etMqttClientId.getText().toString().trim()));
            orderTasks.add(OrderTaskAssembler.setMQTTSubscribeTopic(mBind.etMqttSubscribeTopic.getText().toString().trim()));
            orderTasks.add(OrderTaskAssembler.setMQTTPublishTopic(mBind.etMqttPublishTopic.getText().toString().trim()));
            orderTasks.add(OrderTaskAssembler.setMQTTCleanSession(generalFragment.isCleanSession() ? 1 : 0));
            orderTasks.add(OrderTaskAssembler.setMQTTQos(generalFragment.getQos()));
            orderTasks.add(OrderTaskAssembler.setMQTTKeepAlive(generalFragment.getKeepAlive()));
            String apn = TextUtils.isEmpty(mBind.etApn.getText()) ? null : mBind.etApn.getText().toString().trim();
            orderTasks.add(OrderTaskAssembler.setApn(apn));
            orderTasks.add(OrderTaskAssembler.setNetworkFormat(networkFormatSelect));
            orderTasks.add(OrderTaskAssembler.setMQTTUsername(userFragment.getUsername()));
            orderTasks.add(OrderTaskAssembler.setMQTTPassword(userFragment.getPassword()));
            orderTasks.add(OrderTaskAssembler.setMQTTConnectMode(sslFragment.getConnectMode()));
            if (sslFragment.getConnectMode() == 2) {
                //ca证书
                File file = null;
                if (null != sslFragment.getCaPath()) file = new File(sslFragment.getCaPath());
                orderTasks.add(OrderTaskAssembler.setCA(file));
            } else if (sslFragment.getConnectMode() == 3) {
                File caFile = null;
                if (null != sslFragment.getCaPath()) caFile = new File(sslFragment.getCaPath());
                orderTasks.add(OrderTaskAssembler.setCA(caFile));
                File clientKeyFile = null;
                if (null != sslFragment.getClientKeyPath())
                    clientKeyFile = new File(sslFragment.getClientKeyPath());
                orderTasks.add(OrderTaskAssembler.setClientKey(clientKeyFile));
                File clientCertFile = null;
                if (null != sslFragment.getClientCertPath())
                    clientCertFile = new File(sslFragment.getClientCertPath());
                orderTasks.add(OrderTaskAssembler.setClientCert(clientCertFile));
            }
            orderTasks.add(OrderTaskAssembler.setMQTTLwtEnable(lwtFragment.getLwtEnable() ? 1 : 0));
            orderTasks.add(OrderTaskAssembler.setMQTTLwtRetain(lwtFragment.getLwtRetain() ? 1 : 0));
            orderTasks.add(OrderTaskAssembler.setMQTTLwtQos(lwtFragment.getQos()));
            orderTasks.add(OrderTaskAssembler.setMQTTLwtTopic(lwtFragment.getTopic()));
            orderTasks.add(OrderTaskAssembler.setMQTTLwtPayload(lwtFragment.getPayload()));
            LoRaLW006MokoSupport.getInstance().sendOrder(orderTasks.toArray(new OrderTask[]{}));
        } catch (Exception e) {
            ToastUtils.showToast(this, "File is missing");
        }
    }

    public void selectCertificate(View view) {
        if (isWindowLocked()) return;
        sslFragment.selectCertificate();
    }

    public void selectCAFile(View view) {
        if (isWindowLocked()) return;
        sslFragment.selectCAFile();
    }

    public void selectKeyFile(View view) {
        if (isWindowLocked()) return;
        sslFragment.selectKeyFile();
    }

    public void selectCertFile(View view) {
        if (isWindowLocked()) return;
        sslFragment.selectCertFile();
    }

    public void onExportSettings(View view) {
        if (isWindowLocked()) return;
        if (isParaError()) return;
        String host = mBind.etMqttHost.getText().toString();
        String port = mBind.etMqttPort.getText().toString();
        String clientId = mBind.etMqttClientId.getText().toString();
        String topicSubscribe = mBind.etMqttSubscribeTopic.getText().toString();
        String topicPublish = mBind.etMqttPublishTopic.getText().toString();
        boolean cleanSession = generalFragment.isCleanSession();
        int qos = generalFragment.getQos();
        int keepAlive = generalFragment.getKeepAlive();
        String username = userFragment.getUsername();
        String password = userFragment.getPassword();
        int connectMode = sslFragment.getConnectMode();
        boolean lwtEnable = lwtFragment.getLwtEnable();
        boolean lwtRetain = lwtFragment.getLwtRetain();
        int lwtQos = lwtFragment.getQos();
        String lwtTopic = lwtFragment.getTopic();
        String lwtPayload = lwtFragment.getPayload();
        String apn = TextUtils.isEmpty(mBind.etApn.getText()) ? "" : mBind.etApn.getText().toString();
        showLoadingProgressDialog();
        final File expertFile = new File(expertFilePath);
        try {
            if (!expertFile.getParentFile().exists()) {
                expertFile.getParentFile().mkdirs();
            }
            if (!expertFile.exists()) {
                expertFile.delete();
                expertFile.createNewFile();
            }
            new Thread(() -> {
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
                XSSFSheet sheet = xssfWorkbook.createSheet();
                XSSFRow row0 = sheet.createRow(0);
                row0.createCell(0).setCellValue("Config_Item");
                row0.createCell(1).setCellValue("Config_value");
                row0.createCell(2).setCellValue("Remark");

                XSSFRow row1 = sheet.createRow(1);
                row1.createCell(0).setCellValue("Host");
                if (!TextUtils.isEmpty(host))
                    row1.createCell(1).setCellValue(String.format("value:%s", host));
                row1.createCell(2).setCellValue("1-64 characters");

                XSSFRow row2 = sheet.createRow(2);
                row2.createCell(0).setCellValue("Port");
                if (!TextUtils.isEmpty(port))
                    row2.createCell(1).setCellValue(String.format("value:%s", port));
                row2.createCell(2).setCellValue("Range: 1-65535");

                XSSFRow row3 = sheet.createRow(3);
                row3.createCell(0).setCellValue("Client id");
                if (!TextUtils.isEmpty(clientId))
                    row3.createCell(1).setCellValue(String.format("value:%s", clientId));
                row3.createCell(2).setCellValue("1-64 characters");

                XSSFRow row4 = sheet.createRow(4);
                row4.createCell(0).setCellValue("Subscribe Topic");
                if (!TextUtils.isEmpty(topicSubscribe))
                    row4.createCell(1).setCellValue(String.format("value:%s", topicSubscribe));
                row4.createCell(2).setCellValue("1-128 characters");

                XSSFRow row5 = sheet.createRow(5);
                row5.createCell(0).setCellValue("Publish Topic");
                if (!TextUtils.isEmpty(topicPublish))
                    row5.createCell(1).setCellValue(String.format("value:%s", topicPublish));
                row5.createCell(2).setCellValue("1-128 characters");

                XSSFRow row6 = sheet.createRow(6);
                row6.createCell(0).setCellValue("Clean Session");
                row6.createCell(1).setCellValue(String.format("value:%s", cleanSession ? "1" : "0"));
                row6.createCell(2).setCellValue("Range: 0/1 0:NO 1:YES");

                XSSFRow row7 = sheet.createRow(7);
                row7.createCell(0).setCellValue("Qos");
                row7.createCell(1).setCellValue(String.format("value:%d", qos));
                row7.createCell(2).setCellValue("Range: 0/1/2 0:qos0 1:qos1 2:qos2");

                XSSFRow row8 = sheet.createRow(8);
                row8.createCell(0).setCellValue("Keep Alive");
                row8.createCell(1).setCellValue(String.format("value:%d", keepAlive));
                row8.createCell(2).setCellValue("Range: 10-120, unit: second");

                XSSFRow row9 = sheet.createRow(9);
                row9.createCell(0).setCellValue("MQTT Username");
                if (!TextUtils.isEmpty(username))
                    row9.createCell(1).setCellValue(String.format("value:%s", username));
                row9.createCell(2).setCellValue("0-256 characters");

                XSSFRow row10 = sheet.createRow(10);
                row10.createCell(0).setCellValue("MQTT Password");
                if (!TextUtils.isEmpty(password))
                    row10.createCell(1).setCellValue(String.format("value:%s", password));
                row10.createCell(2).setCellValue("0-256 characters");

                XSSFRow row11 = sheet.createRow(11);
                row11.createCell(0).setCellValue("SSL/TLS");
                XSSFRow row12 = sheet.createRow(12);
                row12.createCell(0).setCellValue("Certificate type");
                if (connectMode > 0) {
                    row11.createCell(1).setCellValue("value:1");
                    row12.createCell(1).setCellValue(String.format("value:%d", connectMode));
                } else {
                    row11.createCell(1).setCellValue(String.format("value:%d", connectMode));
                    row12.createCell(1).setCellValue("value:1");
                }
                row11.createCell(2).setCellValue("Range: 0/1 0:Disable SSL (TCP mode) 1:Enable SSL");
                row12.createCell(2).setCellValue("Valid when SSL is enabled, range: 1/2/3 1: CA certificate file 2: CA certificate file 3: Self signed certificates");

                XSSFRow row13 = sheet.createRow(13);
                row13.createCell(0).setCellValue("LWT");
                row13.createCell(1).setCellValue(lwtEnable ? "value:1" : "value:0");
                row13.createCell(2).setCellValue("Range: 0/1 0:Disable 1:Enable");

                XSSFRow row14 = sheet.createRow(14);
                row14.createCell(0).setCellValue("LWT Retain");
                row14.createCell(1).setCellValue(lwtRetain ? "value:1" : "value:0");
                row14.createCell(2).setCellValue("Range: 0/1 0:NO 1:YES");

                XSSFRow row15 = sheet.createRow(15);
                row15.createCell(0).setCellValue("LWT Qos");
                row15.createCell(1).setCellValue(String.format("value:%d", lwtQos));
                row15.createCell(2).setCellValue("Range: 0/1/2 0:qos0 1:qos1 2:qos2");

                XSSFRow row16 = sheet.createRow(16);
                row16.createCell(0).setCellValue("LWT Topic");
                if (!TextUtils.isEmpty(lwtTopic))
                    row16.createCell(1).setCellValue(String.format("value:%s", lwtTopic));
                row16.createCell(2).setCellValue("1-128 characters");

                XSSFRow row17 = sheet.createRow(17);
                row17.createCell(0).setCellValue("LWT Payload");
                if (!TextUtils.isEmpty(lwtPayload))
                    row17.createCell(1).setCellValue(String.format("value:%s", lwtPayload));
                row17.createCell(2).setCellValue("1-128 characters");

                XSSFRow row18 = sheet.createRow(18);
                row18.createCell(0).setCellValue("APN");
                if (!TextUtils.isEmpty(apn)) {
                    row18.createCell(1).setCellValue(String.format("value:%s", apn));
                }
                row18.createCell(2).setCellValue("0-100 Characters");

                XSSFRow row19 = sheet.createRow(19);
                row19.createCell(0).setCellValue("Network Priority");
                if (!TextUtils.isEmpty(apn)) {
                    row19.createCell(1).setCellValue(String.format("value:%s", networkFormatSelect));
                }
                row19.createCell(2).setCellValue("Range: 0/1/2/3");

                Uri uri = Uri.fromFile(expertFile);
                try {
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);
                    xssfWorkbook.write(outputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    isFileError = true;
                }
                runOnUiThread(() -> {
                    dismissLoadingProgressDialog();
                    if (isFileError) {
                        isFileError = false;
                        ToastUtils.showToast(NetworkSettingsActivity.this, "Export error!");
                        return;
                    }
                    ToastUtils.showToast(NetworkSettingsActivity.this, "Export success!");
                    Utils.sendEmail(NetworkSettingsActivity.this, "", "", "Settings for Device", "Choose Email Client", expertFile);

                });
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToast(this, "Export error!");
        }
    }

    public void onImportSettings(View view) {
        if (isWindowLocked()) return;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "select file first!"), 200);
        } catch (ActivityNotFoundException ex) {
            ToastUtils.showToast(this, "install file manager app");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                //得到uri，后面就是将uri转化成file的过程。
                Uri uri = data.getData();
                String paramFilePath = FileUtils.getPath(this, uri);
                if (TextUtils.isEmpty(paramFilePath)) {
                    return;
                }
                if (!paramFilePath.endsWith(".xlsx")) {
                    ToastUtils.showToast(this, "Please select the correct file!");
                    return;
                }
                final File paramFile = new File(paramFilePath);
                if (paramFile.exists()) {
                    showLoadingProgressDialog();
                    new Thread(() -> {
                        try {
                            Workbook workbook = WorkbookFactory.create(paramFile);
                            Sheet sheet = workbook.getSheetAt(0);
                            int rows = sheet.getPhysicalNumberOfRows();
                            int columns = sheet.getRow(0).getPhysicalNumberOfCells();
                            // 从第二行开始
                            if (rows < 20 || columns < 3) {
                                runOnUiThread(() -> {
                                    dismissLoadingProgressDialog();
                                    ToastUtils.showToast(NetworkSettingsActivity.this, "Please select the correct file!");
                                });
                                return;
                            }
                            Cell hostCell = sheet.getRow(1).getCell(1);
                            if (hostCell != null)
                                networkSettings.host = hostCell.getStringCellValue().replaceAll("value:", "");
                            Cell postCell = sheet.getRow(2).getCell(1);
                            if (postCell != null)
                                networkSettings.port = postCell.getStringCellValue().replaceAll("value:", "");
                            Cell clientCell = sheet.getRow(3).getCell(1);
                            if (clientCell != null)
                                networkSettings.clientId = clientCell.getStringCellValue().replaceAll("value:", "");
                            Cell topicSubscribeCell = sheet.getRow(4).getCell(1);
                            if (topicSubscribeCell != null) {
                                networkSettings.subscribe = topicSubscribeCell.getStringCellValue().replaceAll("value:", "");
                            }
                            Cell topicPublishCell = sheet.getRow(5).getCell(1);
                            if (topicPublishCell != null) {
                                networkSettings.publish = topicPublishCell.getStringCellValue().replaceAll("value:", "");
                            }
                            Cell cleanSessionCell = sheet.getRow(6).getCell(1);
                            if (cleanSessionCell != null)
                                networkSettings.cleanSession = "1".equals(cleanSessionCell.getStringCellValue().replaceAll("value:", ""));
                            Cell qosCell = sheet.getRow(7).getCell(1);
                            if (qosCell != null)
                                networkSettings.qos = Integer.parseInt(qosCell.getStringCellValue().replaceAll("value:", ""));
                            Cell keepAliveCell = sheet.getRow(8).getCell(1);
                            if (keepAliveCell != null)
                                networkSettings.keepAlive = Integer.parseInt(keepAliveCell.getStringCellValue().replaceAll("value:", ""));
                            Cell usernameCell = sheet.getRow(9).getCell(1);
                            if (usernameCell != null) {
                                networkSettings.userName = usernameCell.getStringCellValue().replaceAll("value:", "");
                            }
                            Cell passwordCell = sheet.getRow(10).getCell(1);
                            if (passwordCell != null) {
                                networkSettings.password = passwordCell.getStringCellValue().replaceAll("value:", "");
                            }
                            Cell connectModeCell = sheet.getRow(11).getCell(1);
                            if (connectModeCell != null) {
                                // 0/1
                                networkSettings.connectMode = Integer.parseInt(connectModeCell.getStringCellValue().replaceAll("value:", ""));
                                if (networkSettings.connectMode > 0) {
                                    Cell cell = sheet.getRow(12).getCell(1);
                                    if (cell != null)
                                        // 1/2/3
                                        networkSettings.connectMode = Integer.parseInt(cell.getStringCellValue().replaceAll("value:", ""));
                                }
                            }
                            Cell lwtEnableCell = sheet.getRow(13).getCell(1);
                            if (lwtEnableCell != null)
                                networkSettings.lwtEnable = "1".equals(lwtEnableCell.getStringCellValue().replaceAll("value:", ""));
                            Cell lwtRetainCell = sheet.getRow(14).getCell(1);
                            if (lwtRetainCell != null)
                                networkSettings.lwtRetain = "1".equals(lwtRetainCell.getStringCellValue().replaceAll("value:", ""));
                            Cell lwtQosCell = sheet.getRow(15).getCell(1);
                            if (lwtQosCell != null)
                                networkSettings.lwtQos = Integer.parseInt(lwtQosCell.getStringCellValue().replaceAll("value:", ""));
                            Cell topicCell = sheet.getRow(16).getCell(1);
                            if (topicCell != null) {
                                networkSettings.lwtTopic = topicCell.getStringCellValue().replaceAll("value:", "");
                            }
                            Cell payloadCell = sheet.getRow(17).getCell(1);
                            if (payloadCell != null) {
                                networkSettings.lwtPayload = payloadCell.getStringCellValue().replaceAll("value:", "");
                            }
                            Cell apnCell = sheet.getRow(18).getCell(1);
                            if (null != apnCell) {
                                networkSettings.apn = apnCell.getStringCellValue().replaceAll("value:", "");
                            }
                            Cell netCell = sheet.getRow(19).getCell(1);
                            if (null != netCell) {
                                networkSettings.networkFormat = Integer.parseInt(netCell.getStringCellValue().replaceAll("value:", ""));
                            }
                            runOnUiThread(() -> {
                                dismissLoadingProgressDialog();
                                if (isFileError) {
                                    ToastUtils.showToast(NetworkSettingsActivity.this, "Import failed!");
                                    return;
                                }
                                ToastUtils.showToast(NetworkSettingsActivity.this, "Import success!");
                                initData();
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            isFileError = true;
                        }
                    }).start();
                } else {
                    ToastUtils.showToast(this, "File is not exists!");
                }
            }
        }
    }

    private void initData() {
        mBind.etMqttHost.setText(networkSettings.host);
        mBind.etMqttPort.setText(networkSettings.port);
        mBind.etMqttClientId.setText(networkSettings.clientId);
        mBind.etMqttSubscribeTopic.setText(networkSettings.subscribe);
        mBind.etMqttPublishTopic.setText(networkSettings.publish);
        generalFragment.setCleanSession(networkSettings.cleanSession);
        generalFragment.setQos(networkSettings.qos);
        generalFragment.setKeepAlive(networkSettings.keepAlive);
        mBind.etApn.setText(networkSettings.apn);
        mBind.tvNetworkFormat.setText(networkSettings.networkFormat);
        userFragment.setUserName(networkSettings.userName);
        userFragment.setPassword(networkSettings.password);
        sslFragment.setConnectMode(networkSettings.connectMode);
        lwtFragment.setLwtEnable(networkSettings.lwtEnable);
        lwtFragment.setLwtRetain(networkSettings.lwtRetain);
        lwtFragment.setQos(networkSettings.lwtQos);
        lwtFragment.setTopic(networkSettings.lwtTopic);
        lwtFragment.setPayload(networkSettings.lwtPayload);
    }

    public void onClearConfig(View view) {
        if (isWindowLocked()) return;
        AlertMessageDialog dialog = new AlertMessageDialog();
        dialog.setMessage("Please confirm whether to delete all configurations in this page?");
        dialog.setConfirm("YES");
        dialog.setCancel("NO");
        dialog.setOnAlertConfirmListener(() -> {
            networkSettings = new NetworkSettings();
            initData();
        });
        dialog.show(getSupportFragmentManager());
    }
}
