package com.jhtwl.googlestore.Bean;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.jhtwl.googlestore.Utils.UiUtils;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/31  上午10:04
 */
public class JavaScriptObject {
    @JavascriptInterface
    public void showMessage(String msg) {
        Toast.makeText(UiUtils.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
