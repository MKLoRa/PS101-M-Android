package com.moko.ps101m.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.moko.ps101m.BuildConfig;
import com.moko.ps101m.R;
import com.moko.ps101m.databinding.ActivityAboutPs101Binding;
import com.moko.ps101m.utils.Utils;

public class Ps101AboutActivity extends BaseActivity {
    private ActivityAboutPs101Binding mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = ActivityAboutPs101Binding.inflate(getLayoutInflater());
        setContentView(mBind.getRoot());
        if (!BuildConfig.IS_LIBRARY) {
            mBind.appVersion.setText(String.format("APP Version:V%s", Utils.getVersionInfo(this)));
        }
    }

    public void onBack(View view) {
        finish();
    }

    public void onCompanyWebsite(View view) {
        if (isWindowLocked()) return;
        Uri uri = Uri.parse("https://" + getString(R.string.company_website));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
