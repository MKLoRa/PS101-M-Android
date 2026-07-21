# PS101-M Android

Android configuration app for the PS101-M cellular positioning device. It configures cellular and device-side MQTT parameters, Wi-Fi / Bluetooth / GPS positioning, operating modes, alarms, BLE scanning filters, and system settings over BLE. After provisioning, the device connects to the cellular network and MQTT broker independently.

Cross-platform reference (same protocol): [PS101-M Flutter](https://github.com/MKLoRa/PS101-M-Flutter).

---

## 1. Overview

```
┌────────────┐  BLE only   ┌────────────┐  Cellular + MQTT  ┌─────────────┐
│  PS101-M   │◄───────────►│  PS101-M   │──────────────────►│ MQTT Broker │
│    APP     │  provision  │   device   │                   └─────────────┘
└────────────┘             └─────┬──────┘
                                 │
                      ┌──────────┼──────────┐
                      ▼          ▼          ▼
                    GNSS       Wi-Fi    BLE beacons
                         positioning sources
```

Main flow:

1. **Scan and connect** — Scan for Service Data `0xAA10`; select a connectable PS101-M and verify its password when enabled.
2. **Configure network** — Write cellular APN / network mode and device-side MQTT settings over BLE.
3. **Configure positioning** — Set Wi-Fi, Bluetooth, and GPS positioning parameters and BLE scan filters.
4. **Configure operating modes** — Set standby, timing, periodic, or motion mode and each mode's positioning strategy.
5. **Configure functions** — Set heartbeat, alarms, ManDown detection, 3-axis behavior, BLE advertising, indicators, power options, and other device parameters.
6. **Device leaves the BLE session** — PS101-M applies the settings and communicates with the cellular network / MQTT broker on its own.

---

## 2. Project Structure

| Module | Description |
|--------|-------------|
| `ps101` | UI and business flow: BLE scan, connect, network, positioning, modes, alarms, data export, and device settings |
| `mokosupport` | SDK: BLE scan/connection, command assembly, `ParamsKeyEnum`, entities, tasks, and callbacks |

Package names: `com.moko.ps101m` (app), `com.moko.support.ps101m` (SDK).

The project uses compile/target SDK 35, min SDK 28, Java 8, AndroidX, and View Binding.

Initialize in `Ps101MainActivity`:

```java
MokoSupport.getInstance().init(getApplicationContext());
```

Include the modules:

```gradle
include ':ps101'
include ':mokosupport'
```

```gradle
implementation project(path: ':mokosupport')
```

---

## 3. Business Flow

### 3.1 BLE Scan & Connect

Entry: `Ps101MainActivity`

Scan for devices advertising Service Data UUID `0xAA10`. The parser accepts a 16-byte Service Data payload:

- Byte `[7]`: advertising TX power
- Byte `[8]`: battery percentage
- Bytes `[9..10]`: battery voltage
- Byte `[11]`: password verification (`1` = password required)
- Only **connectable** devices can be selected
- The last successful password is remembered locally

Custom BLE service `0xAA00`:

| Characteristic | Purpose |
|----------------|---------|
| `0xAA00` | Password verification |
| `0xAA01` | Disconnect reason notification |
| `0xAA02` | Device parameter read/write |
| `0xAA04` | Stored data notification |
| `0xAA05` | Device debug log |

After connection and optional password verification, the app opens `DeviceInfoActivity`, the main configuration hub with four bottom tabs.

### 3.2 Network Configuration

Tab: **Network** (`NetworkFragment`)

The tab displays cellular and MQTT connection status and configures the network reconnect interval. `NetworkSettingsActivity` contains the full cellular and MQTT configuration:

| Area | Purpose |
|------|---------|
| Cellular | APN, APN username/password, and network mode (`NB-IoT`, `eMTC`, or priority order) |
| General MQTT | Host, port, Client ID, publish/subscribe topics, Clean Session, QoS, Keep Alive |
| User | MQTT username and password |
| SSL | Connection mode and CA/client certificate/client key files |
| LWT | Last Will enable, QoS, retain, topic, and payload |

These MQTT settings are written **to the PS101-M firmware**; they are not the Android app's own MQTT client settings. The screen also supports importing/exporting device network settings.

When both cellular and MQTT are connected, the app can synchronize the device identity and topics with the MOKO IoT Device Management service.

### 3.3 Positioning Configuration

Tab: **Positioning Strategy** (`PositionFragment`)

| Screen | Purpose |
|--------|---------|
| `PosWifiFixActivity` | Wi-Fi RSSI filter, positioning mechanism, timeout, and required BSSID count |
| `PosBleFixActivity` | BLE positioning mechanism, timeout, required MAC count, PHY/RSSI, and advertisement filters |
| `PosGpsFixActivity` | GPS timeout and PDOP limit |

BLE positioning filters:

| Screen | Filter |
|--------|--------|
| `FilterMacAddressActivity` | MAC address rules, precise/reverse matching |
| `FilterAdvNameActivity` | Advertising name rules, precise/reverse matching |
| `FilterRawDataSwitchActivity` | Raw advertisement type filter hub |
| `FilterIBeaconActivity` | iBeacon UUID, Major, and Minor |
| `FilterUIDActivity` / `FilterUrlActivity` / `FilterTLMActivity` | Eddystone UID, URL, and TLM |
| `FilterBXPIBeaconActivity` | BXP-iBeacon |
| `FilterBXPButtonActivity` / `FilterBXPTagIdActivity` | BXP Button and BXP Tag |
| `FilterMkPirActivity` | MK-PIR status and Major/Minor |
| `FilterOtherActivity` | Other/raw byte rules |

### 3.4 Operating Modes

Entry: `DeviceModeActivity`

| Screen | Purpose |
|--------|---------|
| `StandbyModeActivity` | Positioning strategy while the device is in standby |
| `TimingModeActivity` | Positioning strategy and scheduled report time points |
| `PeriodicModeActivity` | Periodic positioning strategy and report interval |
| `MotionModeActivity` | Motion start/trip/end/stationary events, intervals, and positioning strategies |

The selected mode determines when PS101-M performs positioning and reports data.

### 3.5 General / Auxiliary Functions

Tab: **General Settings** (`GeneralFragment`)

- Heartbeat report interval
- Continuity transfer enable

Additional function screens:

| Screen | Purpose |
|--------|---------|
| `AuxiliaryOperationActivity` | Hub for downlink positioning, ManDown, alarms, and 3-axis data reporting |
| `DownlinkForPosActivity` | Positioning strategy used by a downlink request |
| `ManDownDetectionActivity` | ManDown enable, timeout, report interval, and positioning strategy |
| `AlarmFunctionActivity` | Alert and SOS alarm configuration hub |
| `AlertAlarmSettingActivity` | Alert trigger, positioning strategy, and event notification |
| `AlarmSosSettingActivity` | SOS trigger, positioning strategy, report interval, and event notification |
| `ThreeAxisDataReportActivity` | 3-axis data report enable and interval |
| `AxisSettingActivity` | Accelerometer wake-up and motion detection conditions |

### 3.6 Device / System Configuration

Tab: **Device Settings** (`DeviceFragment`)

| Screen / setting | Purpose |
|------------------|---------|
| Data format | Device uplink payload format: JSON or HEX |
| `NtpSeverSettingActivity` | NTP server and synchronization interval |
| Time zone | UTC offset |
| Low-power settings | Threshold and low-power payload enable |
| Buzzer / vibration | Sound type and vibration intensity |
| `BleSettingsActivity` | Password verification and BLE advertising parameters |
| `IndicatorSettingsActivity` | Device indicator state |
| `OnOffSettingsActivity` | Shutdown payload, button shutdown, and charging auto-power-on |
| `ExportDataActivity` | Read, pause/resume, export, and clear locally stored device data |
| `SystemInfoActivity` | Device identifiers, versions, battery, network information, OTA, and debug entry |
| `LogDataActivity` | Read and export device debug logs |

Factory reset, password change, firmware update, and device information are available from the same configuration hub. OTA uses Nordic DFU and accepts a ZIP firmware package.

---

## 4. BLE Parameter Protocol Summary

Most configuration uses `CHAR_PARAMS` (`0xAA02`) with key-byte addressing defined by `ParamsKeyEnum`.
During connection setup, the SDK requests MTU 247 and enables the custom-characteristic notifications.

Common frame envelope:

**Single packet** `HEAD=0xED`: `HEAD + FLAG + KEY + LEN + DATA`

**Multi packet** `HEAD=0xEE`: `HEAD + FLAG + KEY + PACKET_COUNT + PACKET_INDEX + CHUNK_LEN + DATA`

| FLAG | Meaning |
|------|---------|
| `0x00` | Read |
| `0x01` | Write |
| `0x02` | Device notification, such as a disconnect reason |

A parameter write succeeds when the returned result byte is `0x01`.

Key parameter groups:

| Key range | Category | Examples |
|-----------|----------|----------|
| `0x10`–`0x2F` | System / power / status / NTP | Reboot, time, battery, mode, heartbeat, indicators, network/MQTT status |
| `0x30`–`0x35` | BLE | Password verification, password, advertising timeout/TX power/name/interval |
| `0x3F`–`0x4E` | Operating modes | Standby, timing, periodic, and motion strategies |
| `0x50`–`0x7B` | BLE scan filters | PHY, RSSI, MAC/name, iBeacon, Eddystone, BXP, MK-PIR, Other |
| `0x7F`–`0x87` | Positioning | Wi-Fi, BLE, and GPS positioning parameters |
| `0x88`–`0x94` | Cellular / data | APN credentials, network mode, payload format, reconnect interval, IMEI |
| `0x95`–`0xA7` | MQTT | Broker, credentials, topics, QoS, LWT, SSL certificates |
| `0xB0`–`0xBF` | Auxiliary / alarms | Downlink positioning, ManDown, Alert/SOS, 3-axis report |
| `0xC0`–`0xC2` | Stored data | Read, clear, and pause/resume transfer |

---

## 5. BLE Frame Format Summary

**Password** (`CHAR_PASSWORD`):

```
[0xED, 0x01, 0x01, len, passwordBytes..., zeroPadding...]
```

`SetPasswordTask` allocates a fixed 12-byte frame; password-length validation is handled outside the SDK task.

**Parameter read** (short):

```
[0xED, 0x00, key, 0x00]
```

**Parameter read** (long):

```
[0xEE, 0x00, key, 0x00]
```

Long reads are used for values such as MQTT credentials, filter rules, and certificate files.

**Parameter write**:

```
[0xED, 0x01, key, length, payload...]
```

**Multi-packet write**:

```
[0xEE, 0x01, key, packetCount, packetIndex, chunkLength, chunkData...]
```

Multi-packet writes use payload chunks of up to 176 bytes for long strings, filter rules, and TLS certificate files. Command assembly is implemented by `OrderTaskAssembler` and `ParamsTask`.

---

## 6. SDK Quick Start

### 6.1 BLE Scan

```java
MokoBleScanner mokoBleScanner = new MokoBleScanner(this);
mokoBleScanner.startScanDevice(new MokoScanDeviceCallback() {
    @Override public void onStartScan() { }
    @Override public void onScanDevice(DeviceInfo device) { }
    @Override public void onStopScan() { }
});
```

Scan identification uses service UUID `0xAA10` (`OrderServices.SERVICE_ADV`). Advertisement parsing is implemented by `AdvInfoAnalysisImpl`.

### 6.2 BLE Connect & Orders

- Connect: `MokoSupport.getInstance().connDevice(mac)`
- Connection status: EventBus `ConnectStatusEvent`
- Send: `MokoSupport.getInstance().sendOrder(OrderTask...)`
- Response: EventBus `OrderTaskResponseEvent`

Typical flow: scan → connect → verify password (`SetPasswordTask`) → read/write parameters through `OrderTaskAssembler` → disconnect.

```java
// Read the device-side MQTT host
MokoSupport.getInstance().sendOrder(OrderTaskAssembler.getMQTTHost());

// Write the cellular APN
MokoSupport.getInstance().sendOrder(OrderTaskAssembler.setApn(apn));
```

### 6.3 Logging

App logging is based on [xLog](https://github.com/elvishew/xLog). `BaseApplication` writes `PS101M/PS101M.txt` and keeps the current log plus its backup according to `ClearLogBackStrategy`.

Device-side debug logs are read from `CHAR_LOG` (`0xAA05`) and can be exported through `LogDataActivity`.

```java
XLog.d("log info");
```

---

## 7. Main Screen Index

```
GuideActivity                         Splash / permissions
Ps101MainActivity                     BLE scan, filter, connect
DeviceInfoActivity                    Configuration hub (4 tabs)
  Network tab                         Cellular and device-side MQTT status/settings
    NetworkSettingsActivity             APN, MQTT, SSL, LWT, import/export
  Positioning Strategy tab            Wi-Fi / BLE / GPS positioning
    PosWifiFixActivity                  Wi-Fi positioning
    PosBleFixActivity                   BLE positioning and scan filters
      filter/*                          Individual BLE advertisement filters
    PosGpsFixActivity                   GPS timeout / PDOP
  General Settings tab                Heartbeat / continuity transfer
  Device Settings tab                 Payload, time, power, buzzer, vibration
    DeviceModeActivity                  Standby / timing / periodic / motion modes
    AuxiliaryOperationActivity          Downlink / ManDown / alarms / 3-axis report
    BleSettingsActivity                 BLE advertising and password verification
    AxisSettingActivity                 Accelerometer conditions
    IndicatorSettingsActivity           Indicator state
    OnOffSettingsActivity               Shutdown and auto-power-on
    ExportDataActivity                  Stored data export
    SystemInfoActivity                  Device info / OTA / debug entry
      LogDataActivity                   Device debug log export
Ps101AboutActivity                    About / app information
```

---

## 8. References

- Parameter keys: `mokosupport/src/main/java/com/moko/support/ps101m/entity/ParamsKeyEnum.java`
- Order assembly: `mokosupport/src/main/java/com/moko/support/ps101m/OrderTaskAssembler.java`
- Parameter frames: `mokosupport/src/main/java/com/moko/support/ps101m/task/ParamsTask.java`
- BLE connection setup: `mokosupport/src/main/java/com/moko/support/ps101m/MokoBleConfig.java`
- BLE UUIDs: `mokosupport/src/main/java/com/moko/support/ps101m/entity/OrderServices.java`, `OrderCHAR.java`
- Advertisement parser: `ps101/src/main/java/com/moko/ps101m/utils/AdvInfoAnalysisImpl.java`
