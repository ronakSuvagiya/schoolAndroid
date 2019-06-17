package com.apps.smartschoolmanagement.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.bumptech.glide.load.Key;
import com.apps.smartschoolmanagement.R;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

public class HTTPRequest extends AsyncTask<Object, String, String> {
    /* renamed from: c */
    private Context f68c;
    ProgressDialog dialog;
    LinearLayout ll;
    ProgressBar progressBar;
    HTTPCall serviceCall = new HTTPCall();

    public HTTPRequest(Context context) {
        this.f68c = context;
        HTTPCall hTTPCall = this.serviceCall;
        HTTPCall hTTPCall2 = this.serviceCall;
        hTTPCall.setMethodtype(2);
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog = new ProgressDialog(this.f68c, R.style.MyGravity);
        this.dialog.setProgressStyle(0);
        this.dialog.getWindow().setFlags(8, 8);
        this.dialog.getWindow().setBackgroundDrawableResource(17170445);
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected java.lang.String doInBackground(java.lang.Object... r15) {
        /*
        r14 = this;
        r13 = 1;
        r12 = r14.serviceCall;
        r11 = 0;
        r11 = r15[r11];
        r11 = (java.lang.String) r11;
        r12.setUrl(r11);
        r12 = r14.serviceCall;
        r11 = r15[r13];
        r11 = (java.util.HashMap) r11;
        r12.setParams(r11);
        r9 = 0;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r11 = r14.serviceCall;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = r11.getParams();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r12 = r14.serviceCall;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r12 = r12.getMethodtype();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r2 = r14.getDataString(r11, r12);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r8 = new java.net.URL;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = r14.serviceCall;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = r11.getMethodtype();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r12 = r14.serviceCall;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        if (r11 != r13) goto L_0x00d7;
    L_0x0036:
        r11 = new java.lang.StringBuilder;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11.<init>();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r12 = r14.serviceCall;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r12 = r12.getUrl();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = r11.append(r12);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = r11.append(r2);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = r11.toString();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
    L_0x004d:
        r8.<init>(r11);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = r8.openConnection();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r0 = r11;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r9 = r0;
        r11 = r14.serviceCall;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = r11.getMethodtype();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r12 = r14.serviceCall;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        if (r11 != r13) goto L_0x00df;
    L_0x0062:
        r11 = "GET";
    L_0x0064:
        r9.setRequestMethod(r11);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r9.setReadTimeout(r11);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r9.setConnectTimeout(r11);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = r14.serviceCall;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = r11.getParams();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        if (r11 == 0) goto L_0x00a0;
    L_0x0079:
        r11 = r14.serviceCall;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = r11.getMethodtype();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r12 = r14.serviceCall;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r12 = 2;
        if (r11 != r12) goto L_0x00a0;
    L_0x0084:
        r5 = r9.getOutputStream();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r10 = new java.io.BufferedWriter;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = new java.io.OutputStreamWriter;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r12 = java.nio.charset.StandardCharsets.UTF_8;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11.<init>(r5, r12);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r10.<init>(r11);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r10.append(r2);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r10.flush();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r10.close();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r5.close();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
    L_0x00a0:
        r7 = r9.getResponseCode();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r7 != r11) goto L_0x00e2;
    L_0x00a8:
        r1 = new java.io.BufferedReader;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = new java.io.InputStreamReader;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r12 = r9.getInputStream();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11.<init>(r12);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r1.<init>(r11);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
    L_0x00b6:
        r4 = r1.readLine();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        if (r4 == 0) goto L_0x00e2;
    L_0x00bc:
        r6.append(r4);	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        goto L_0x00b6;
    L_0x00c0:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ all -> 0x0117 }
        r11 = "Service URl";
        r12 = r14.serviceCall;
        r12 = r12.getUrl();
        android.util.Log.e(r11, r12);
        r9.disconnect();
    L_0x00d2:
        r11 = r6.toString();
        return r11;
    L_0x00d7:
        r11 = r14.serviceCall;	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        r11 = r11.getUrl();	 Catch:{ UnsupportedEncodingException -> 0x00c0, MalformedURLException -> 0x00f1, IOException -> 0x0104 }
        goto L_0x004d;
    L_0x00df:
        r11 = "POST";
        goto L_0x0064;
    L_0x00e2:
        r11 = "Service URl";
        r12 = r14.serviceCall;
        r12 = r12.getUrl();
        android.util.Log.e(r11, r12);
        r9.disconnect();
        goto L_0x00d2;
    L_0x00f1:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ all -> 0x0117 }
        r11 = "Service URl";
        r12 = r14.serviceCall;
        r12 = r12.getUrl();
        android.util.Log.e(r11, r12);
        r9.disconnect();
        goto L_0x00d2;
    L_0x0104:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ all -> 0x0117 }
        r11 = "Service URl";
        r12 = r14.serviceCall;
        r12 = r12.getUrl();
        android.util.Log.e(r11, r12);
        r9.disconnect();
        goto L_0x00d2;
    L_0x0117:
        r11 = move-exception;
        r12 = "Service URl";
        r13 = r14.serviceCall;
        r13 = r13.getUrl();
        android.util.Log.e(r12, r13);
        r9.disconnect();
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.apps.smartschoolmanagement.utils.HTTPRequest.doInBackground(java.lang.Object[]):java.lang.String");
    }

    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.cancel();
            this.dialog.dismiss();
        }
    }

    public void onResponse(String response) {
    }

    private String getDataString(HashMap<String, String> params, int methodType) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean isFirst = true;
        for (Entry<String, String> entry : params.entrySet()) {
            if (isFirst) {
                isFirst = false;
                HTTPCall hTTPCall = this.serviceCall;
                if (methodType == 1) {
                    result.append("?");
                }
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode((String) entry.getKey(), Key.STRING_CHARSET_NAME));
            result.append("=");
            result.append(URLEncoder.encode(((String) entry.getValue()).toString(), Key.STRING_CHARSET_NAME));
        }
        Log.e("result Info", result.toString());
        return result.toString();
    }
}
