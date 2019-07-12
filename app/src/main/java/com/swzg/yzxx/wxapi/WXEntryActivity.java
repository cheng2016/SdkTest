package com.swzg.yzxx.wxapi;

import android.os.Bundle;
import android.util.Log;

import com.icloud.sdk.BaseWXEntryActivity;

/**
 * Created by admin on 2019/5/17.
 */

public class WXEntryActivity extends BaseWXEntryActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("WXEntryActivity","onCreate");
    }
}
