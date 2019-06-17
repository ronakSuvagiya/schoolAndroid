package com.apps.smartschoolmanagement.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.BaseFinishActivity;

public class WebViewActivity extends BaseFinishActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        final KProgressHUD progressHUD = KProgressHUD.create(this).setLabel("Loading..Please Wait");
        progressHUD.setCancellable(false);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressHUD.show();
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressHUD.dismiss();
            }
        });
        webView.loadUrl("http://www.findlogics.in/Myschool/");
    }
}
