package com.moko.ps101m.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.moko.ps101m.activity.Lw006BaseActivity;
import com.moko.ps101m.databinding.FragmentSslDeviceBinding;
import com.moko.ps101m.dialog.BottomDialog;
import com.moko.ps101m.utils.FileUtils;
import com.moko.ps101m.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;

public class SSLDeviceFragment extends Fragment {
    public static final int REQUEST_CODE_SELECT_CA = 0x10;
    public static final int REQUEST_CODE_SELECT_CLIENT_KEY = 0x11;
    public static final int REQUEST_CODE_SELECT_CLIENT_CERT = 0x12;

    private static final String TAG = SSLDeviceFragment.class.getSimpleName();
    private FragmentSslDeviceBinding mBind;
    private Lw006BaseActivity activity;
    private int mConnectMode;
    private ArrayList<String> values;
    private int selected;

    public SSLDeviceFragment() {
    }

    public static SSLDeviceFragment newInstance() {
        return new SSLDeviceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        mBind = FragmentSslDeviceBinding.inflate(inflater, container, false);
        activity = (Lw006BaseActivity) getActivity();
        mBind.clCertificate.setVisibility(mConnectMode > 0 ? View.VISIBLE : View.GONE);
        mBind.cbSsl.setChecked(mConnectMode > 0);
        mBind.cbSsl.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                mConnectMode = 0;
            } else {
                mConnectMode = selected + 1;
            }
            mBind.clCertificate.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
        values = new ArrayList<>();
        values.add("CA signed server certificate");
        values.add("CA certificate file");
        values.add("Self signed certificates");
        if (mConnectMode > 0) {
            selected = mConnectMode - 1;
            mBind.tvCertification.setText(values.get(selected));
        }
        if (selected == 0) {
            mBind.llCa.setVisibility(View.GONE);
            mBind.llClientKey.setVisibility(View.GONE);
            mBind.llClientCert.setVisibility(View.GONE);
        } else if (selected == 1) {
            mBind.llCa.setVisibility(View.VISIBLE);
            mBind.llClientKey.setVisibility(View.GONE);
            mBind.llClientCert.setVisibility(View.GONE);
        } else if (selected == 2) {
            mBind.llCa.setVisibility(View.VISIBLE);
            mBind.llClientKey.setVisibility(View.VISIBLE);
            mBind.llClientCert.setVisibility(View.VISIBLE);
        }
        return mBind.getRoot();
    }

    public void setConnectMode(int connectMode) {
        this.mConnectMode = connectMode;
        if (mBind == null) return;
        mBind.clCertificate.setVisibility(mConnectMode > 0 ? View.VISIBLE : View.GONE);
        if (mConnectMode > 0) {
            selected = mConnectMode - 1;
            mBind.tvCertification.setText(values.get(selected));
        }
        mBind.cbSsl.setChecked(mConnectMode > 0);
        if (selected == 0) {
            mBind.llCa.setVisibility(View.GONE);
            mBind.llClientKey.setVisibility(View.GONE);
            mBind.llClientCert.setVisibility(View.GONE);
        } else if (selected == 1) {
            mBind.llCa.setVisibility(View.VISIBLE);
            mBind.llClientKey.setVisibility(View.GONE);
            mBind.llClientCert.setVisibility(View.GONE);
        } else if (selected == 2) {
            mBind.llCa.setVisibility(View.VISIBLE);
            mBind.llClientKey.setVisibility(View.VISIBLE);
            mBind.llClientCert.setVisibility(View.VISIBLE);
        }
    }

    public void selectCertificate() {
        BottomDialog dialog = new BottomDialog();
        dialog.setDatas(values, selected);
        dialog.setListener(value -> {
            selected = value;
            mBind.tvCertification.setText(values.get(selected));
            if (selected == 0) {
                mConnectMode = 1;
                mBind.llCa.setVisibility(View.GONE);
                mBind.llClientKey.setVisibility(View.GONE);
                mBind.llClientCert.setVisibility(View.GONE);
            } else if (selected == 1) {
                mConnectMode = 2;
                mBind.llCa.setVisibility(View.VISIBLE);
                mBind.llClientKey.setVisibility(View.GONE);
                mBind.llClientCert.setVisibility(View.GONE);
            } else if (selected == 2) {
                mConnectMode = 3;
                mBind.llCa.setVisibility(View.VISIBLE);
                mBind.llClientKey.setVisibility(View.VISIBLE);
                mBind.llClientCert.setVisibility(View.VISIBLE);
            }
        });
        dialog.show(activity.getSupportFragmentManager());
    }

    public void selectCAFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "select file first!"), REQUEST_CODE_SELECT_CA);
        } catch (ActivityNotFoundException ex) {
            ToastUtils.showToast(activity, "install file manager app");
        }
    }

    public void selectKeyFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "select file first!"), REQUEST_CODE_SELECT_CLIENT_KEY);
        } catch (ActivityNotFoundException ex) {
            ToastUtils.showToast(activity, "install file manager app");
        }
    }

    public void selectCertFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "select file first!"), REQUEST_CODE_SELECT_CLIENT_CERT);
        } catch (ActivityNotFoundException ex) {
            ToastUtils.showToast(activity, "install file manager app");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != activity.RESULT_OK) return;
        //得到uri，后面就是将uri转化成file的过程。
        Uri uri = data.getData();
        String filePath = FileUtils.getPath(activity, uri);
        if (TextUtils.isEmpty(filePath)) {
            ToastUtils.showToast(activity, "file path error!");
            return;
        }
        final File file = new File(filePath);
        if (file.exists()) {
            if (requestCode == REQUEST_CODE_SELECT_CA) {
                mBind.tvCaFile.setText(filePath);
            }
            if (requestCode == REQUEST_CODE_SELECT_CLIENT_KEY) {
                mBind.tvClientKeyFile.setText(filePath);
            }
            if (requestCode == REQUEST_CODE_SELECT_CLIENT_CERT) {
                mBind.tvClientCertFile.setText(filePath);
            }
        } else {
            ToastUtils.showToast(activity, "file is not exists!");
        }
    }

    public int getConnectMode() {
        return mConnectMode;
    }

    public String getCaPath() {
        return TextUtils.isEmpty(mBind.tvCaFile.getText()) ? null : mBind.tvCaFile.getText().toString().trim();
    }

    public String getClientKeyPath() {
        return TextUtils.isEmpty(mBind.tvClientKeyFile.getText()) ? null : mBind.tvClientKeyFile.getText().toString().trim();
    }

    public String getClientCertPath() {
        return TextUtils.isEmpty(mBind.tvClientCertFile.getText()) ? null : mBind.tvClientCertFile.getText().toString().trim();
    }
}
