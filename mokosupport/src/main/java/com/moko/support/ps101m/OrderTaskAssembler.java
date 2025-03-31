package com.moko.support.ps101m;

import static com.moko.support.ps101m.entity.ParamsKeyEnum.KEY_AXIS_REPORT_INTERVAL;
import static com.moko.support.ps101m.entity.ParamsKeyEnum.KEY_MQTT_LWT_PAYLOAD;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moko.ble.lib.task.OrderTask;
import com.moko.support.ps101m.entity.ParamsKeyEnum;
import com.moko.support.ps101m.task.GetFirmwareRevisionTask;
import com.moko.support.ps101m.task.GetHardwareRevisionTask;
import com.moko.support.ps101m.task.GetManufacturerNameTask;
import com.moko.support.ps101m.task.GetModelNumberTask;
import com.moko.support.ps101m.task.GetSerialNumberTask;
import com.moko.support.ps101m.task.GetSoftwareRevisionTask;
import com.moko.support.ps101m.task.ParamsTask;
import com.moko.support.ps101m.task.SetPasswordTask;

import java.io.File;
import java.util.ArrayList;

public class OrderTaskAssembler {
    ///////////////////////////////////////////////////////////////////////////
    // READ
    ///////////////////////////////////////////////////////////////////////////

    public static OrderTask getManufacturer() {
        GetManufacturerNameTask getManufacturerTask = new GetManufacturerNameTask();
        return getManufacturerTask;
    }

    public static OrderTask getDeviceModel() {
        GetModelNumberTask getDeviceModelTask = new GetModelNumberTask();
        return getDeviceModelTask;
    }

    public static OrderTask getSerialNumber() {
        GetSerialNumberTask getSerialNumberTask = new GetSerialNumberTask();
        return getSerialNumberTask;
    }

    public static OrderTask getHardwareVersion() {
        GetHardwareRevisionTask getHardwareVersionTask = new GetHardwareRevisionTask();
        return getHardwareVersionTask;
    }

    public static OrderTask getFirmwareVersion() {
        GetFirmwareRevisionTask getFirmwareVersionTask = new GetFirmwareRevisionTask();
        return getFirmwareVersionTask;
    }

    public static OrderTask getSoftwareVersion() {
        GetSoftwareRevisionTask getSoftwareVersionTask = new GetSoftwareRevisionTask();
        return getSoftwareVersionTask;
    }

    public static OrderTask getTimeZone() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_TIME_ZONE);
        return task;
    }

    public static OrderTask getTimeUTC() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_TIME_UTC);
        return task;
    }

    public static OrderTask getLowPowerPercent() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_LOW_POWER_PERCENT);
        return task;
    }

    public static OrderTask getDeviceMode() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_DEVICE_MODE);
        return task;
    }

    public static OrderTask getIndicatorStatus() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_INDICATOR_STATUS);
        return task;
    }

    public static OrderTask getHeartBeatInterval() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_HEARTBEAT_INTERVAL);
        return task;
    }

    public static OrderTask getCustomManufacturer() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MANUFACTURER);
        return task;
    }

    public static OrderTask getShutdownPayloadEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_SHUTDOWN_PAYLOAD_ENABLE);
        return task;
    }

    public static OrderTask getOffByButtonEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_OFF_BY_BUTTON);
        return task;
    }

    public static OrderTask getAutoPowerOn() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_AUTO_POWER_ON_ENABLE);
        return task;
    }

    public static OrderTask getLowPowerPayloadEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_LOW_POWER_PAYLOAD_ENABLE);
        return task;
    }

    public static OrderTask getBuzzerSoundChoose() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_BUZZER_SOUND_CHOOSE);
        return task;
    }

    public static OrderTask getVibrationIntensity() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_VIBRATION_INTENSITY);
        return task;
    }

    public static OrderTask getBattery() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_BATTERY_POWER);
        return task;
    }

    public static OrderTask getContinuityTransferEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_CONTINUITY_TRANSFER_ENABLE);
        return task;
    }

    public static OrderTask getMacAddress() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_CHIP_MAC);
        return task;
    }

    public static OrderTask getPCBAStatus() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_PCBA_STATUS);
        return task;
    }

    public static OrderTask getSelfTestStatus() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_SELFTEST_STATUS);
        return task;
    }

    public static OrderTask getMotorState() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MOTOR_STATE);
        return task;
    }

    public static OrderTask resetMotorState() {
        ParamsTask task = new ParamsTask();
        task.resetMotorState();
        return task;
    }

    public static OrderTask getPasswordVerifyEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_PASSWORD_VERIFY_ENABLE);
        return task;
    }

    public static OrderTask getAdvTimeout() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ADV_TIMEOUT);
        return task;
    }


    public static OrderTask getAdvTxPower() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ADV_TX_POWER);
        return task;
    }

    public static OrderTask getAdvName() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ADV_NAME);
        return task;
    }

    public static OrderTask getAdvInterval() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ADV_INTERVAL);
        return task;
    }

    public static OrderTask getPeriodicPosStrategy() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_PERIODIC_MODE_POS_STRATEGY);
        return task;
    }

    public static OrderTask getPeriodicReportInterval() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_PERIODIC_MODE_REPORT_INTERVAL);
        return task;
    }

    public static OrderTask getTimePosStrategy() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_TIME_MODE_POS_STRATEGY);
        return task;
    }

    public static OrderTask getStandbyPosStrategy() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_STANDBY_MODE_POS_STRATEGY);
        return task;
    }

    public static OrderTask getTimePosReportPoints() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_TIME_MODE_REPORT_TIME_POINT);
        return task;
    }

    public static OrderTask getMotionModeEvent() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MOTION_MODE_EVENT);
        return task;
    }

    public static OrderTask getMotionStartPosStrategy() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MOTION_MODE_START_POS_STRATEGY);
        return task;
    }

    public static OrderTask getMotionTripInterval() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MOTION_MODE_TRIP_REPORT_INTERVAL);
        return task;
    }

    public static OrderTask getMotionTripPosStrategy() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MOTION_MODE_TRIP_POS_STRATEGY);
        return task;
    }


    public static OrderTask getMotionEndTimeout() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MOTION_MODE_END_TIMEOUT);
        return task;
    }

    public static OrderTask getMotionEndNumber() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MOTION_MODE_END_NUMBER);
        return task;
    }

    public static OrderTask getMotionEndInterval() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MOTION_MODE_END_REPORT_INTERVAL);
        return task;
    }

    public static OrderTask getMotionEndPosStrategy() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MOTION_MODE_END_POS_STRATEGY);
        return task;
    }

    public static OrderTask getMotionStationaryPosStrategy() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MOTION_MODE_STATIONARY_POS_STRATEGY);
        return task;
    }

    public static OrderTask getMotionStationaryReportInterval() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MOTION_MODE_STATIONARY_REPORT_INTERVAL);
        return task;
    }

    public static OrderTask getWifiRssiFilter() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_WIFI_RSSI_FILTER);
        return task;
    }

    public static OrderTask getWifiPosMechanism() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_WIFI_POS_MECHANISM);
        return task;
    }

    public static OrderTask getWifiPosTimeout() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_WIFI_POS_TIMEOUT);
        return task;
    }

    public static OrderTask getWifiPosBSSIDNumber() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_WIFI_POS_BSSID_NUMBER);
        return task;
    }

    public static OrderTask getBlePosTimeout() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_BLE_POS_TIMEOUT);
        return task;
    }

    public static OrderTask getBlePosNumber() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_BLE_POS_MAC_NUMBER);
        return task;
    }

    public static OrderTask getFilterRSSI() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_RSSI);
        return task;
    }

    public static OrderTask getFilterBleScanPhy() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BLE_SCAN_PHY);
        return task;
    }

    public static OrderTask getFilterRelationship() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_RELATIONSHIP);
        return task;
    }

    public static OrderTask getFilterMacPrecise() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_MAC_PRECISE);
        return task;
    }

    public static OrderTask getFilterMacReverse() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_MAC_REVERSE);
        return task;
    }

    public static OrderTask getFilterMacRules() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_MAC_RULES);
        return task;
    }

    public static OrderTask getFilterNamePrecise() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_NAME_PRECISE);
        return task;
    }

    public static OrderTask getFilterNameReverse() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_NAME_REVERSE);
        return task;
    }

    public static OrderTask getFilterNameRules() {
        ParamsTask task = new ParamsTask();
        task.getLongData(ParamsKeyEnum.KEY_FILTER_NAME_RULES);
        return task;
    }

    public static OrderTask getFilterRawData() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_RAW_DATA);
        return task;
    }

    public static OrderTask getFilterIBeaconEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_IBEACON_ENABLE);
        return task;
    }

    public static OrderTask getFilterIBeaconMajorRange() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_IBEACON_MAJOR_RANGE);
        return task;
    }

    public static OrderTask getFilterIBeaconMinorRange() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_IBEACON_MINOR_RANGE);
        return task;
    }

    public static OrderTask getFilterIBeaconUUID() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_IBEACON_UUID);
        return task;
    }

    public static OrderTask getFilterBXPIBeaconEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_IBEACON_ENABLE);
        return task;
    }

    public static OrderTask getFilterBXPIBeaconMajorRange() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_IBEACON_MAJOR_RANGE);
        return task;
    }

    public static OrderTask getFilterBXPIBeaconMinorRange() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_IBEACON_MINOR_RANGE);
        return task;
    }

    public static OrderTask getFilterBXPIBeaconUUID() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_IBEACON_UUID);
        return task;
    }

    public static OrderTask getFilterBXPTagEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_TAG_ENABLE);
        return task;
    }

    public static OrderTask getFilterBXPTagPrecise() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_TAG_PRECISE);
        return task;
    }

    public static OrderTask getFilterBXPTagReverse() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_TAG_REVERSE);
        return task;
    }

    public static OrderTask getFilterBXPTagRules() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_TAG_RULES);
        return task;
    }

    public static OrderTask getMkPirEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_MK_PIR_ENABLE);
        return task;
    }

    public static OrderTask getMkPirSensorDetectionStatus() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_MK_PIR_DETECTION_STATUS);
        return task;
    }

    public static OrderTask getMkPirSensorSensitivity() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_MK_PIR_SENSOR_SENSITIVITY);
        return task;
    }

    public static OrderTask getMkPirDoorStatus() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_MK_PIR_DOOR_STATUS);
        return task;
    }

    public static OrderTask getMkPirDelayResStatus() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_MK_PIR_DELAY_RES_STATUS);
        return task;
    }

    public static OrderTask getMkPirMajor() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_MK_PIR_MAJOR);
        return task;
    }

    public static OrderTask getMkPirMinor() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_MK_PIR_MINOR);
        return task;
    }

    public static OrderTask getFilterBXPButtonEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_BUTTON_ENABLE);
        return task;
    }

    public static OrderTask getFilterBXPButtonRules() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_BUTTON_RULES);
        return task;
    }

    public static OrderTask getBlePosMechanism() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_BLE_POS_MECHANISM);
        return task;
    }

    public static OrderTask getFilterEddystoneUidEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_EDDYSTONE_UID_ENABLE);
        return task;
    }

    public static OrderTask getFilterEddystoneUidNamespace() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_EDDYSTONE_UID_NAMESPACE);
        return task;
    }

    public static OrderTask getFilterEddystoneUidInstance() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_EDDYSTONE_UID_INSTANCE);
        return task;
    }

    public static OrderTask getFilterEddystoneUrlEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_EDDYSTONE_URL_ENABLE);
        return task;
    }

    public static OrderTask getFilterEddystoneUrl() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_EDDYSTONE_URL);
        return task;
    }

    public static OrderTask getFilterEddystoneTlmEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_EDDYSTONE_TLM_ENABLE);
        return task;
    }

    public static OrderTask getFilterEddystoneTlmVersion() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_EDDYSTONE_TLM_VERSION);
        return task;
    }

    public static OrderTask getFilterBXPAcc() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_ACC);
        return task;
    }

    public static OrderTask getFilterBXPTH() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_TH);
        return task;
    }

    public static OrderTask getFilterBXPDevice() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_BXP_DEVICE);
        return task;
    }

    public static OrderTask getFilterOtherEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_OTHER_ENABLE);
        return task;
    }

    public static OrderTask getFilterOtherRelationship() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_OTHER_RELATIONSHIP);
        return task;
    }

    public static OrderTask getFilterOtherRules() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_FILTER_OTHER_RULES);
        return task;
    }

    public static OrderTask getGPSPosTimeoutL76() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_GPS_POS_TIMEOUT_L76C);
        return task;
    }

    public static OrderTask getGPSPDOPLimitL76() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_GPS_PDOP_LIMIT_L76C);
        return task;
    }

    public static OrderTask getDownLinkPosStrategy() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_DOWN_LINK_POS_STRATEGY);
        return task;
    }

    public static OrderTask getAccWakeupCondition() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ACC_WAKEUP_CONDITION);
        return task;
    }

    public static OrderTask getAccMotionCondition() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ACC_MOTION_CONDITION);
        return task;
    }

    public static OrderTask getManDownDetectionEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MAN_DOWN_DETECTION_ENABLE);
        return task;
    }

    public static OrderTask getManDownDetectionTimeout() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MAN_DOWN_DETECTION_TIMEOUT);
        return task;
    }

    public static OrderTask getManDownPosStrategy() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MAN_DOWN_POS_STRATEGY);
        return task;
    }

    public static OrderTask getManDownReportInterval() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MAN_DOWN_DETECTION_REPORT_INTERVAL);
        return task;
    }

    public static OrderTask getAlarmType() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ALARM_TYPE);
        return task;
    }

    public static OrderTask getAlarmExitTime() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ALARM_EXIT_TIME);
        return task;
    }

    public static OrderTask getAlarmAlertTriggerType() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ALARM_ALERT_TRIGGER_TYPE);
        return task;
    }

    public static OrderTask getAlarmAlertPosStrategy() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ALARM_ALERT_POS_STRATEGY);
        return task;
    }

    public static OrderTask getAlarmAlertNotifyEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ALARM_ALERT_NOTIFY_ENABLE);
        return task;
    }

    public static OrderTask getAlarmSosTriggerType() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ALARM_SOS_TRIGGER_TYPE);
        return task;
    }

    public static OrderTask getAlarmSosPosStrategy() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ALARM_SOS_POS_STRATEGY);
        return task;
    }

    public static OrderTask getAlarmSosReportInterval() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ALARM_SOS_REPORT_INTERVAL);
        return task;
    }

    public static OrderTask getAlarmSosNotifyEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_ALARM_SOS_NOTIFY_ENABLE);
        return task;
    }

    public static OrderTask getNetworkStatus() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_NETWORK_STATUS);
        return task;
    }

    public static OrderTask getMqttConnectionStatus() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MQTT_CONNECT_STATUS);
        return task;
    }

    public static OrderTask getNetworkReconnectInterval() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_NETWORK_RECONNECT_INTERVAL);
        return task;
    }

    public static OrderTask getMQTTHost() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MQTT_HOST);
        return task;
    }

    public static OrderTask getMQTTPort() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MQTT_PORT);
        return task;
    }

    public static OrderTask getMQTTClientId() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MQTT_CLIENT_ID);
        return task;
    }

    public static OrderTask getMQTTSubscribeTopic() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_SUBSCRIBE_TOPIC);
        return task;
    }

    public static OrderTask getMQTTPublishTopic() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_PUBLISH_TOPIC);
        return task;
    }

    public static OrderTask getMQTTCleanSession() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MQTT_CLEAN_SESSION);
        return task;
    }

    public static OrderTask getMQTTQos() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MQTT_QOS);
        return task;
    }

    public static OrderTask getMQTTKeepAlive() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MQTT_KEEP_ALIVE);
        return task;
    }

    public static OrderTask getApn() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_APN);
        return task;
    }

    public static OrderTask getNetworkFormat() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_NETWORK_FORMAT);
        return task;
    }

    public static OrderTask getMQTTUsername() {
        ParamsTask task = new ParamsTask();
        task.getLongData(ParamsKeyEnum.KEY_MQTT_USERNAME);
        return task;
    }

    public static OrderTask getMQTTPassword() {
        ParamsTask task = new ParamsTask();
        task.getLongData(ParamsKeyEnum.KEY_MQTT_PASSWORD);
        return task;
    }

    public static OrderTask getMQTTConnectMode() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_CONNECT_MODE);
        return task;
    }

    public static OrderTask getMQTTLwtEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MQTT_LWT_ENABLE);
        return task;
    }

    public static OrderTask getMQTTLwtRetain() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MQTT_LWT_RETAIN);
        return task;
    }

    public static OrderTask getMQTTLwtQos() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MQTT_LWT_QOS);
        return task;
    }

    public static OrderTask getMQTTLwtTopic() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_MQTT_LWT_TOPIC);
        return task;
    }

    public static OrderTask getMQTTLwtPayload() {
        ParamsTask task = new ParamsTask();
        task.setData(KEY_MQTT_LWT_PAYLOAD);
        return task;
    }

    public static OrderTask getAxisDataReportInterval() {
        ParamsTask task = new ParamsTask();
        task.setData(KEY_AXIS_REPORT_INTERVAL);
        return task;
    }

    public static OrderTask getAxisDataReportEnable() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_AXIS_REPORT_ENABLE);
        return task;
    }

    public static OrderTask getDataFormat() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_DATA_COMMUNICATION_TYPE);
        return task;
    }

    public static OrderTask getNtpServer() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_NTP_SERVER);
        return task;
    }

    public static OrderTask getNtpSyncInterval() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_NTP_SYNC_INTERVAL);
        return task;
    }


    ///////////////////////////////////////////////////////////////////////////
    // WRITE
    ///////////////////////////////////////////////////////////////////////////
    public static OrderTask setNtpServer(@Nullable String ntpServer) {
        ParamsTask task = new ParamsTask();
        task.setNtpServer(ntpServer);
        return task;
    }

    public static OrderTask setNtpSyncInterval(@IntRange(from = 0, to = 720) int interval) {
        ParamsTask task = new ParamsTask();
        task.setNtpSyncInterval(interval);
        return task;
    }

    public static OrderTask setDataFormat(@IntRange(from = 0, to = 1) int format) {
        ParamsTask task = new ParamsTask();
        task.setDataFormat(format);
        return task;
    }

    public static OrderTask setAxisDataReportInterval(@IntRange(from = 0, to = 65535) int interval) {
        ParamsTask task = new ParamsTask();
        task.setAxisDataReportInterval(interval);
        return task;
    }

    public static OrderTask setAxisDataReportEnable(int enable) {
        ParamsTask task = new ParamsTask();
        task.setAxisDataReportEnable(enable);
        return task;
    }

    public static OrderTask setMQTTHost(String mqttHost) {
        ParamsTask task = new ParamsTask();
        task.setMQTTHost(mqttHost);
        return task;
    }

    public static OrderTask setMQTTPort(@IntRange(from = 1, to = 65535) int port) {
        ParamsTask task = new ParamsTask();
        task.setMQTTPort(port);
        return task;
    }

    public static OrderTask setMQTTClientId(String clientId) {
        ParamsTask task = new ParamsTask();
        task.setMQTTClientId(clientId);
        return task;
    }

    public static OrderTask setMQTTSubscribeTopic(String subtopic) {
        ParamsTask task = new ParamsTask();
        task.setMQTTSubscribeTopic(subtopic);
        return task;
    }

    public static OrderTask setMQTTPublishTopic(String publishTopic) {
        ParamsTask task = new ParamsTask();
        task.setMQTTPublishTopic(publishTopic);
        return task;
    }

    public static OrderTask setMQTTCleanSession(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setMQTTCleanSession(enable);
        return task;
    }

    public static OrderTask setMQTTQos(@IntRange(from = 0, to = 2) int qos) {
        ParamsTask task = new ParamsTask();
        task.setMQTTQos(qos);
        return task;
    }

    public static OrderTask setMQTTKeepAlive(@IntRange(from = 10, to = 120) int keepAlive) {
        ParamsTask task = new ParamsTask();
        task.setMQTTKeepAlive(keepAlive);
        return task;
    }

    public static OrderTask setApn(@Nullable String apn) {
        ParamsTask task = new ParamsTask();
        task.setApn(apn);
        return task;
    }

    public static OrderTask setNetworkFormat(@IntRange(from = 0, to = 3) int networkFormat) {
        ParamsTask task = new ParamsTask();
        task.setNetworkFormat(networkFormat);
        return task;
    }

    public static OrderTask setMQTTUsername(@Nullable String userName) {
        ParamsTask task = new ParamsTask();
        task.setMQTTUsername(userName);
        return task;
    }

    public static OrderTask setMQTTPassword(@Nullable String password) {
        ParamsTask task = new ParamsTask();
        task.setMQTTPassword(password);
        return task;
    }

    public static OrderTask setMQTTConnectMode(@IntRange(from = 0, to = 3) int mode) {
        ParamsTask task = new ParamsTask();
        task.setMQTTConnectMode(mode);
        return task;
    }

    public static OrderTask setMQTTLwtEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setMQTTLwtEnable(enable);
        return task;
    }

    public static OrderTask setMQTTLwtRetain(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setMQTTLwtRetain(enable);
        return task;
    }

    public static OrderTask setMQTTLwtQos(@IntRange(from = 0, to = 2) int qos) {
        ParamsTask task = new ParamsTask();
        task.setMQTTLwtQos(qos);
        return task;
    }

    public static OrderTask setMQTTLwtTopic(String lwtTopic) {
        ParamsTask task = new ParamsTask();
        task.setMQTTLwtTopic(lwtTopic);
        return task;
    }

    public static OrderTask setMQTTLwtPayload(String lwtPayload) {
        ParamsTask task = new ParamsTask();
        task.setMQTTLwtPayload(lwtPayload);
        return task;
    }

    public static OrderTask setCA(@Nullable File file) throws Exception {
        ParamsTask task = new ParamsTask();
        task.setFile(ParamsKeyEnum.KEY_MQTT_CA, file);
        return task;
    }

    public static OrderTask setClientCert(@Nullable File file) throws Exception {
        ParamsTask task = new ParamsTask();
        task.setFile(ParamsKeyEnum.KEY_MQTT_CLIENT_CERT, file);
        return task;
    }

    public static OrderTask setClientKey(@Nullable File file) throws Exception {
        ParamsTask task = new ParamsTask();
        task.setFile(ParamsKeyEnum.KEY_MQTT_CLIENT_KEY, file);
        return task;
    }

    public static OrderTask setNetworkReconnectInterval(@IntRange(from = 0, to = 100) int interval) {
        ParamsTask task = new ParamsTask();
        task.setNetworkReconnectInterval(interval);
        return task;
    }

    public static OrderTask setPassword(String password) {
        SetPasswordTask task = new SetPasswordTask();
        task.setData(password);
        return task;
    }

    public static OrderTask close() {
        ParamsTask task = new ParamsTask();
        task.close();
        return task;
    }

    public static OrderTask restart() {
        ParamsTask task = new ParamsTask();
        task.reboot();
        return task;
    }

    public static OrderTask restore() {
        ParamsTask task = new ParamsTask();
        task.reset();
        return task;
    }

    public static OrderTask setTime() {
        ParamsTask task = new ParamsTask();
        task.setTime();
        return task;
    }

    public static OrderTask setTimeZone(@IntRange(from = -24, to = 28) int timeZone) {
        ParamsTask task = new ParamsTask();
        task.setTimeZone(timeZone);
        return task;
    }

    public static OrderTask setLowPowerPercent(@IntRange(from = 0, to = 5) int percent) {
        ParamsTask task = new ParamsTask();
        task.setLowPowerPercent(percent);
        return task;
    }

    public static OrderTask setBuzzerSound(@IntRange(from = 0, to = 2) int buzzer) {
        ParamsTask task = new ParamsTask();
        task.setBuzzerSound(buzzer);
        return task;
    }

    public static OrderTask setVibrationIntensity(@IntRange(from = 0, to = 100) int intensity) {
        ParamsTask task = new ParamsTask();
        task.setVibrationIntensity(intensity);
        return task;
    }

    public static OrderTask setDeviceMode(@IntRange(from = 0, to = 3) int mode) {
        ParamsTask task = new ParamsTask();
        task.setDeviceMode(mode);
        return task;
    }

    public static OrderTask setIndicatorStatus(int status) {
        ParamsTask task = new ParamsTask();
        task.setIndicatorStatus(status);
        return task;
    }

    public static OrderTask setHeartBeatInterval(@IntRange(from = 1, to = 14400) int interval) {
        ParamsTask task = new ParamsTask();
        task.setHeartBeatInterval(interval);
        return task;
    }

    public static OrderTask setManufacturer(String manufacturer) {
        ParamsTask task = new ParamsTask();
        task.setManufacturer(manufacturer);
        return task;
    }

    public static OrderTask setShutdownInfoReport(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setShutdownInfoReport(enable);
        return task;
    }

    public static OrderTask setLowPowerReportEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setLowPowerReportEnable(enable);
        return task;
    }

    public static OrderTask setPasswordVerifyEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setPasswordVerifyEnable(enable);
        return task;
    }

    public static OrderTask changePassword(String password) {
        ParamsTask task = new ParamsTask();
        task.changePassword(password);
        return task;
    }

    public static OrderTask setAdvTimeout(@IntRange(from = 1, to = 60) int timeout) {
        ParamsTask task = new ParamsTask();
        task.setAdvTimeout(timeout);
        return task;
    }

    public static OrderTask setAdvTxPower(@IntRange(from = -40, to = 8) int txPower) {
        ParamsTask task = new ParamsTask();
        task.setAdvTxPower(txPower);
        return task;
    }

    public static OrderTask setAdvName(@Nullable String advName) {
        ParamsTask task = new ParamsTask();
        task.setAdvName(advName);
        return task;
    }

    public static OrderTask setAdvInterval(@IntRange(from = 1, to = 100) int interval) {
        ParamsTask task = new ParamsTask();
        task.setAdvInterval(interval);
        return task;
    }

    public static OrderTask setPeriodicPosStrategy(@IntRange(from = 0, to = 6) int strategy) {
        ParamsTask task = new ParamsTask();
        task.setPeriodicPosStrategy(strategy);
        return task;
    }

    public static OrderTask setPeriodicReportInterval(@IntRange(from = 1, to = 14400) int interval) {
        ParamsTask task = new ParamsTask();
        task.setPeriodicReportInterval(interval);
        return task;
    }

    public static OrderTask setTimePosStrategy(@IntRange(from = 0, to = 6) int strategy) {
        ParamsTask task = new ParamsTask();
        task.setTimePosStrategy(strategy);
        return task;
    }

    public static OrderTask setStandbyPosStrategy(@IntRange(from = 0, to = 6) int strategy) {
        ParamsTask task = new ParamsTask();
        task.setStandbyPosStrategy(strategy);
        return task;
    }

    public static OrderTask setTimePosReportPoints(@Nullable ArrayList<Integer> timePoints) {
        ParamsTask task = new ParamsTask();
        task.setTimePosReportPoints(timePoints);
        return task;
    }

    public static OrderTask setMotionModeEvent(@IntRange(from = 0, to = 31) int event) {
        ParamsTask task = new ParamsTask();
        task.setMotionModeEvent(event);
        return task;
    }

    public static OrderTask setMotionStartPosStrategy(@IntRange(from = 0, to = 2) int strategy) {
        ParamsTask task = new ParamsTask();
        task.setMotionStartPosStrategy(strategy);
        return task;
    }


    public static OrderTask setMotionTripInterval(@IntRange(from = 10, to = 86400) int interval) {
        ParamsTask task = new ParamsTask();
        task.setMotionTripInterval(interval);
        return task;
    }

    public static OrderTask setMotionTripPosStrategy(@IntRange(from = 0, to = 2) int strategy) {
        ParamsTask task = new ParamsTask();
        task.setMotionTripPosStrategy(strategy);
        return task;
    }

    public static OrderTask setMotionEndTimeout(@IntRange(from = 1, to = 180) int timeout) {
        ParamsTask task = new ParamsTask();
        task.setMotionEndTimeout(timeout);
        return task;
    }

    public static OrderTask setMotionEndNumber(@IntRange(from = 1, to = 255) int number) {
        ParamsTask task = new ParamsTask();
        task.setMotionEndNumber(number);
        return task;
    }

    public static OrderTask setMotionEndInterval(@IntRange(from = 10, to = 300) int interval) {
        ParamsTask task = new ParamsTask();
        task.setMotionEndInterval(interval);
        return task;
    }

    public static OrderTask setMotionEndPosStrategy(@IntRange(from = 0, to = 6) int strategy) {
        ParamsTask task = new ParamsTask();
        task.setMotionEndPosStrategy(strategy);
        return task;
    }

    public static OrderTask setMotionStationaryPosStrategy(@IntRange(from = 0, to = 6) int strategy) {
        ParamsTask task = new ParamsTask();
        task.setMotionStationaryPosStrategy(strategy);
        return task;
    }

    public static OrderTask setMotionStationaryReportInterval(@IntRange(from = 1, to = 14400) int interval) {
        ParamsTask task = new ParamsTask();
        task.setMotionStationaryReportInterval(interval);
        return task;
    }

    public static OrderTask setWifiRssiFilter(@IntRange(from = -127, to = 0) int rssi) {
        ParamsTask task = new ParamsTask();
        task.setWifiRssiFilter(rssi);
        return task;
    }

    public static OrderTask setWifiPosMechanism(@IntRange(from = 0, to = 1) int type) {
        ParamsTask task = new ParamsTask();
        task.setWifiPosMechanism(type);
        return task;
    }

    public static OrderTask setWifiPosTimeout(@IntRange(from = 1, to = 4) int timeout) {
        ParamsTask task = new ParamsTask();
        task.setWifiPosTimeout(timeout);
        return task;
    }

    public static OrderTask setWifiPosBSSIDNumber(@IntRange(from = 1, to = 15) int number) {
        ParamsTask task = new ParamsTask();
        task.setWifiPosBSSIDNumber(number);
        return task;
    }

    public static OrderTask setBlePosTimeout(@IntRange(from = 1, to = 10) int timeout) {
        ParamsTask task = new ParamsTask();
        task.setBlePosTimeout(timeout);
        return task;
    }

    public static OrderTask setBlePosNumber(@IntRange(from = 1, to = 15) int number) {
        ParamsTask task = new ParamsTask();
        task.setBlePosNumber(number);
        return task;
    }

    public static OrderTask setFilterRSSI(@IntRange(from = -127, to = 0) int rssi) {
        ParamsTask task = new ParamsTask();
        task.setFilterRSSI(rssi);
        return task;
    }

    public static OrderTask setFilterBleScanPhy(@IntRange(from = 0, to = 3) int type) {
        ParamsTask task = new ParamsTask();
        task.setFilterBleScanPhy(type);
        return task;
    }

    public static OrderTask setFilterRelationship(@IntRange(from = 0, to = 6) int relationship) {
        ParamsTask task = new ParamsTask();
        task.setFilterRelationship(relationship);
        return task;
    }

    public static OrderTask setFilterMacPrecise(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterMacPrecise(enable);
        return task;
    }

    public static OrderTask setFilterMacReverse(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterMacReverse(enable);
        return task;
    }

    public static OrderTask setFilterMacRules(ArrayList<String> filterMacRules) {
        ParamsTask task = new ParamsTask();
        task.setFilterMacRules(filterMacRules);
        return task;
    }

    public static OrderTask setFilterNamePrecise(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterNamePrecise(enable);
        return task;
    }

    public static OrderTask setFilterNameReverse(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterNameReverse(enable);
        return task;
    }

    public static OrderTask setFilterRawData(int unknown, int ibeacon,
                                             int eddystone_uid, int eddystone_url, int eddystone_tlm,
                                             int bxp_acc, int bxp_th,
                                             int mkibeacon, int mkibeacon_acc) {
        ParamsTask task = new ParamsTask();
        task.setFilterRawData(unknown, ibeacon,
                eddystone_uid, eddystone_url, eddystone_tlm,
                bxp_acc, bxp_th,
                mkibeacon, mkibeacon_acc);
        return task;
    }

    public static OrderTask setFilterIBeaconEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterIBeaconEnable(enable);
        return task;
    }

    public static OrderTask setFilterIBeaconMajorRange(@IntRange(from = 0, to = 65535) int min,
                                                       @IntRange(from = 0, to = 65535) int max) {
        ParamsTask task = new ParamsTask();
        task.setFilterIBeaconMajorRange(min, max);
        return task;
    }

    public static OrderTask setFilterIBeaconMinorRange(@IntRange(from = 0, to = 65535) int min,
                                                       @IntRange(from = 0, to = 65535) int max) {
        ParamsTask task = new ParamsTask();
        task.setFilterIBeaconMinorRange(min, max);
        return task;
    }

    public static OrderTask setFilterIBeaconUUID(String uuid) {
        ParamsTask task = new ParamsTask();
        task.setFilterIBeaconUUID(uuid);
        return task;
    }

    public static OrderTask setFilterMKIBeaconEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPIBeaconEnable(enable);
        return task;
    }

    public static OrderTask setFilterMKIBeaconMajorRange(@IntRange(from = 0, to = 65535) int min,
                                                         @IntRange(from = 0, to = 65535) int max) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPIBeaconMajorRange(min, max);
        return task;
    }

    public static OrderTask setFilterMKIBeaconMinorRange(@IntRange(from = 0, to = 65535) int min,
                                                         @IntRange(from = 0, to = 65535) int max) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPIBeaconMinorRange(min, max);
        return task;
    }

    public static OrderTask setFilterMKIBeaconUUID(String uuid) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPIBeaconUUID(uuid);
        return task;
    }

    public static OrderTask setFilterBXPTagEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPTagEnable(enable);
        return task;
    }

    public static OrderTask setFilterBXPTagPrecise(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPTagPrecise(enable);
        return task;
    }

    public static OrderTask setFilterBXPTagReverse(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPTagReverse(enable);
        return task;
    }

    public static OrderTask setFilterBXPTagRules(ArrayList<String> filterBXPTagRules) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPTagRules(filterBXPTagRules);
        return task;
    }

    public static OrderTask setFilterMkPirEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterMkPirEnable(enable);
        return task;
    }

    public static OrderTask setFilterMkPirSensorDetectionStatus(@IntRange(from = 0, to = 2) int type) {
        ParamsTask task = new ParamsTask();
        task.setFilterMkPirSensorDetectionStatus(type);
        return task;
    }

    public static OrderTask setFilterMkPirSensorSensitivity(@IntRange(from = 0, to = 3) int type) {
        ParamsTask task = new ParamsTask();
        task.setFilterMkPirSensorSensitivity(type);
        return task;
    }

    public static OrderTask setFilterMkPirDoorStatus(@IntRange(from = 0, to = 2) int type) {
        ParamsTask task = new ParamsTask();
        task.setFilterMkPirDoorStatus(type);
        return task;
    }

    public static OrderTask setFilterMkPirDelayResStatus(@IntRange(from = 0, to = 3) int type) {
        ParamsTask task = new ParamsTask();
        task.setFilterMkPirDelayResStatus(type);
        return task;
    }

    public static OrderTask setFilterMkPirMajorRange(@IntRange(from = 0, to = 65535) int min,
                                                     @IntRange(from = 0, to = 65535) int max) {
        ParamsTask task = new ParamsTask();
        task.setFilterMkPirMajorRange(min, max);
        return task;
    }

    public static OrderTask setFilterMkPirMinorRange(@IntRange(from = 0, to = 65535) int min,
                                                     @IntRange(from = 0, to = 65535) int max) {
        ParamsTask task = new ParamsTask();
        task.setFilterMkPirMinorRange(min, max);
        return task;
    }

    public static OrderTask setFilterBXPButtonEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPButtonEnable(enable);
        return task;
    }

    public static OrderTask setFilterBXPButtonRules(@IntRange(from = 0, to = 1) int singleEnable,
                                                    @IntRange(from = 0, to = 1) int doubleEnable,
                                                    @IntRange(from = 0, to = 1) int longEnable,
                                                    @IntRange(from = 0, to = 1) int abnormalEnable) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPButtonRules(singleEnable, doubleEnable, longEnable, abnormalEnable);
        return task;
    }

    public static OrderTask setFilterEddystoneUIDEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterEddystoneUIDEnable(enable);
        return task;
    }

    public static OrderTask setFilterEddystoneUIDNamespace(String namespace) {
        ParamsTask task = new ParamsTask();
        task.setFilterEddystoneUIDNamespace(namespace);
        return task;
    }

    public static OrderTask setFilterEddystoneUIDInstance(String instance) {
        ParamsTask task = new ParamsTask();
        task.setFilterEddystoneUIDInstance(instance);
        return task;
    }

    public static OrderTask setFilterEddystoneUrlEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterEddystoneUrlEnable(enable);
        return task;
    }

    public static OrderTask setFilterEddystoneUrl(String url) {
        ParamsTask task = new ParamsTask();
        task.setFilterEddystoneUrl(url);
        return task;
    }

    public static OrderTask setFilterEddystoneTlmEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterEddystoneTlmEnable(enable);
        return task;
    }

    public static OrderTask setFilterEddystoneTlmVersion(@IntRange(from = 0, to = 2) int version) {
        ParamsTask task = new ParamsTask();
        task.setFilterEddystoneTlmVersion(version);
        return task;
    }

    public static OrderTask setFilterBXPDeviceEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPDeviceEnable(enable);
        return task;
    }

    public static OrderTask setFilterBXPAccEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPAccEnable(enable);
        return task;
    }

    public static OrderTask setFilterBXPTHEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterBXPTHEnable(enable);
        return task;
    }

    public static OrderTask setFilterOtherEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setFilterOtherEnable(enable);
        return task;
    }

    public static OrderTask setFilterOtherRelationship(@IntRange(from = 0, to = 5) int relationship) {
        ParamsTask task = new ParamsTask();
        task.setFilterOtherRelationship(relationship);
        return task;
    }

    public static OrderTask setFilterOtherRules(ArrayList<String> filterOtherRules) {
        ParamsTask task = new ParamsTask();
        task.setFilterOtherRules(filterOtherRules);
        return task;
    }

    public static OrderTask setFilterNameRules(ArrayList<String> filterOtherRules) {
        ParamsTask task = new ParamsTask();
        task.setFilterNameRules(filterOtherRules);
        return task;
    }

    public static OrderTask setGPSPosTimeoutL76C(@IntRange(from = 60, to = 600) int timeout) {
        ParamsTask task = new ParamsTask();
        task.setGPSPosTimeoutL76(timeout);
        return task;
    }


    public static OrderTask setGPSPDOPLimitL76C(@IntRange(from = 25, to = 100) int limit) {
        ParamsTask task = new ParamsTask();
        task.setGPSPDOPLimitL76(limit);
        return task;
    }

    public static OrderTask setBlePosMechanism(@IntRange(from = 0, to = 1) int mechanism) {
        ParamsTask task = new ParamsTask();
        task.setBlePosMechanism(mechanism);
        return task;
    }

    public static OrderTask setDownLinkPosStrategy(@IntRange(from = 0, to = 2) int strategy) {
        ParamsTask task = new ParamsTask();
        task.setDownLinkPosStrategy(strategy);
        return task;
    }

    public static OrderTask setAccWakeupCondition(@IntRange(from = 1, to = 20) int threshold,
                                                  @IntRange(from = 1, to = 10) int duration) {
        ParamsTask task = new ParamsTask();
        task.setAccWakeupCondition(threshold, duration);
        return task;
    }

    public static OrderTask setAccMotionCondition(@IntRange(from = 10, to = 250) int threshold,
                                                  @IntRange(from = 1, to = 50) int duration) {
        ParamsTask task = new ParamsTask();
        task.setAccMotionCondition(threshold, duration);
        return task;
    }

    public static OrderTask setShutdownPayloadEnable(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setShutdownInfoReport(enable);
        return task;
    }

    public static OrderTask setOffByButton(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setOffByButton(enable);
        return task;
    }

    public static OrderTask setAutoPowerOn(@IntRange(from = 0, to = 1) int enable) {
        ParamsTask task = new ParamsTask();
        task.setAutoPowerOn(enable);
        return task;
    }

    public static OrderTask setManDownDetectionEnable(int enable) {
        ParamsTask task = new ParamsTask();
        task.setManDownDetectionEnable(enable);
        return task;
    }

    public static OrderTask setManDownDetectionTimeout(@IntRange(from = 1, to = 120) int timeout) {
        ParamsTask task = new ParamsTask();
        task.setManDownDetectionTimeout(timeout);
        return task;
    }

    public static OrderTask setManDownPosStrategy(@IntRange(from = 0, to = 6) int strategy) {
        ParamsTask task = new ParamsTask();
        task.setManDownPosStrategy(strategy);
        return task;
    }

    public static OrderTask setManDownReportInterval(@IntRange(from = 10, to = 600) int interval) {
        ParamsTask task = new ParamsTask();
        task.setManDownReportInterval(interval);
        return task;
    }

    public static OrderTask setAlarmType(@IntRange(from = 0, to = 2) int type) {
        ParamsTask task = new ParamsTask();
        task.setAlarmType(type);
        return task;
    }

    public static OrderTask setAlarmExitTime(@IntRange(from = 5, to = 15) int time) {
        ParamsTask task = new ParamsTask();
        task.setAlarmExitTime(time);
        return task;
    }

    public static OrderTask setAlarmAlertTriggerType(@IntRange(from = 0, to = 4) int type) {
        ParamsTask task = new ParamsTask();
        task.setAlarmAlertTriggerType(type);
        return task;
    }

    public static OrderTask setAlarmAlertPosStrategy(@IntRange(from = 0, to = 6) int strategy) {
        ParamsTask task = new ParamsTask();
        task.setAlarmAlertPosStrategy(strategy);
        return task;
    }

    public static OrderTask setAlarmAlertNotifyEnable(int enable) {
        ParamsTask task = new ParamsTask();
        task.setAlarmAlertNotifyEnable(enable);
        return task;
    }

    //
    public static OrderTask setAlarmSosTriggerType(@IntRange(from = 0, to = 4) int type) {
        ParamsTask task = new ParamsTask();
        task.setAlarmSosTriggerType(type);
        return task;
    }

    public static OrderTask setAlarmSosPosStrategy(@IntRange(from = 0, to = 6) int strategy) {
        ParamsTask task = new ParamsTask();
        task.setAlarmSosPosStrategy(strategy);
        return task;
    }

    public static OrderTask setAlarmSosReportInterval(@IntRange(from = 10, to = 600) int interval) {
        ParamsTask task = new ParamsTask();
        task.setAlarmSosReportInterval(interval);
        return task;
    }

    public static OrderTask setAlarmSosNotifyEnable(int enable) {
        ParamsTask task = new ParamsTask();
        task.setAlarmSosNotifyEnable(enable);
        return task;
    }

    public static OrderTask readStorageData(int time) {
        ParamsTask task = new ParamsTask();
        task.readStorageData(time);
        return task;
    }

    public static OrderTask setSyncEnable(int enable) {
        ParamsTask task = new ParamsTask();
        task.setSyncEnable(enable);
        return task;
    }

    public static OrderTask clearStorageData() {
        ParamsTask task = new ParamsTask();
        task.clearStorageData();
        return task;
    }

    public static OrderTask setContinuityTransferEnable(int enable) {
        ParamsTask task = new ParamsTask();
        task.setContinuityTransferEnable(enable);
        return task;
    }

    //蓝牙网关参数

    public static OrderTask getGatewaySwitch() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_GATEWAY_SWITCH);
        return task;
    }

    public static OrderTask getGracePeriod() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_GRACE_PERIOD);
        return task;
    }

    public static OrderTask getBeaconMinDuration() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_BEACON_MIN_DURATION);
        return task;
    }

    public static OrderTask getDisplayMinDuration() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_DISPLAY_MIN_DURATION);
        return task;
    }

    public static OrderTask getBeaconMinRssi() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_BEACON_MIN_RSSI);
        return task;
    }

    public static OrderTask getBeaconFilter1() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_BEACON_FILTER1);
        return task;
    }

    public static OrderTask getBeaconFilter2() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_BEACON_FILTER2);
        return task;
    }

    public static OrderTask getDisplayMinRssi() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_DISPLAY_MIN_RSSI);
        return task;
    }

    public static OrderTask getDisplayFilter1() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_DISPLAY_FILTER1);
        return task;
    }

    public static OrderTask getDisplayFilter2() {
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_DISPLAY_FILTER2);
        return task;
    }

    public static OrderTask getDisplayUpdateMode(){
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_DISPLAY_UPDATE_MODE);
        return task;
    }

    public static OrderTask getDisplayUpdatePins(){
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_DISPLAY_UPDATE_PINS);
        return task;
    }

    public static OrderTask getDisplayUpdateDuration(){
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_DISPLAY_UPDATE_DURATION);
        return task;
    }
    public static OrderTask getDisplayUpdateInterval(){
        ParamsTask task = new ParamsTask();
        task.setData(ParamsKeyEnum.KEY_DISPLAY_UPDATE_INTERVAL);
        return task;
    }

    public static OrderTask setGatewaySwitch(int enable) {
        ParamsTask task = new ParamsTask();
        task.setGatewaySwitch(enable);
        return task;
    }

    public static OrderTask setGracePeriod(@IntRange(from = 0, to = 65535) int period) {
        ParamsTask task = new ParamsTask();
        task.setGracePeriod(period);
        return task;
    }

    public static OrderTask setBeaconMinDuration(@IntRange(from = 0, to = 255) int duration) {
        ParamsTask task = new ParamsTask();
        task.setBeaconMinDuration(duration);
        return task;
    }

    public static OrderTask setDisplayMinDuration(@IntRange(from = 0, to = 255) int duration) {
        ParamsTask task = new ParamsTask();
        task.setDisplayMinDuration(duration);
        return task;
    }

    public static OrderTask setBeaconMinRssi(@IntRange(from = -127, to = 0) int rssi) {
        ParamsTask task = new ParamsTask();
        task.setGatewayMinRssi(rssi, ParamsKeyEnum.KEY_BEACON_MIN_RSSI);
        return task;
    }

    public static OrderTask setBeaconFilter1(@Nullable String filter) {
        ParamsTask task = new ParamsTask();
        task.setGatewayFilter(filter, ParamsKeyEnum.KEY_BEACON_FILTER1);
        return task;
    }

    public static OrderTask setBeaconFilter2(@Nullable String filter) {
        ParamsTask task = new ParamsTask();
        task.setGatewayFilter(filter, ParamsKeyEnum.KEY_BEACON_FILTER2);
        return task;
    }

    public static OrderTask setDisplayMinRssi(@IntRange(from = -127, to = 0) int rssi) {
        ParamsTask task = new ParamsTask();
        task.setGatewayMinRssi(rssi, ParamsKeyEnum.KEY_DISPLAY_MIN_RSSI);
        return task;
    }

    public static OrderTask setDisplayFilter1(@Nullable String filter) {
        ParamsTask task = new ParamsTask();
        task.setGatewayFilter(filter, ParamsKeyEnum.KEY_DISPLAY_FILTER1);
        return task;
    }

    public static OrderTask setDisplayFilter2(@Nullable String filter) {
        ParamsTask task = new ParamsTask();
        task.setGatewayFilter(filter, ParamsKeyEnum.KEY_DISPLAY_FILTER2);
        return task;
    }

    public static OrderTask setDisplayUpdateMode(int mode) {
        ParamsTask task = new ParamsTask();
        task.setDisplayUpdateMode(mode);
        return task;
    }

    public static OrderTask setDisplayUpdatePins(int pins) {
        ParamsTask task = new ParamsTask();
        task.setDisplayUpdatePins(pins);
        return task;
    }

    public static OrderTask setDisplayUpdateDuration(int duration) {
        ParamsTask task = new ParamsTask();
        task.setDisplayUpdateDuration(duration);
        return task;
    }
    public static OrderTask setDisplayUpdateInterval(int interval) {
        ParamsTask task = new ParamsTask();
        task.setDisplayUpdateInterval(interval);
        return task;
    }

}
