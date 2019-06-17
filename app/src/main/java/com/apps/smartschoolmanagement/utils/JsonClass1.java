package com.apps.smartschoolmanagement.utils;

import am.appwise.components.ni.NoInternetDialog;
import am.appwise.components.ni.NoInternetDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.load.Key;
import com.google.common.net.HttpHeaders;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.NetworkDialogActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonClass1 extends AppCompatActivity {
    public static final double SPACE_GB = 1.073741824E9d;
    public static final double SPACE_KB = 1024.0d;
    public static final double SPACE_MB = 1048576.0d;
    public static final double SPACE_TB = 1.099511627776E12d;
    boolean cancelled = false;
    Context context;
    int downloadedSize = 0;
    public NoInternetDialog noInternetDialog = null;
    public HashMap<String, String> params = new HashMap();
    /* renamed from: t */
    Thread f69t;
    int totalSize = 0;

    public interface AsyncResponse {
        void processFinish(String str);
    }

    /* renamed from: com.apps.smartschoolmanagement.utils.JsonClass1$9 */
    class C14219 implements ErrorListener {
        C14219() {
        }

        public void onErrorResponse(VolleyError error) {
            if (Connectivity.isConnected(JsonClass1.this.context)) {
                if (error != null && error.networkResponse != null) {
                    int statusCode = error.networkResponse.statusCode;
                    Log.d("jkl", "Error Code: " + statusCode);
                    if (statusCode == 404) {
                        JsonClass1.this.showToast("URL Not Found");
                    }
                    try {
                        Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
                    } catch (UnsupportedEncodingException e) {
                    }
                } else if (error == null || error.getMessage() == null) {
                    JsonClass1.this.showToast("Unidentified Server Response");
                } else {
                    JsonClass1.this.showNetworkDialog("Your network speed too slow. Please switch your network", "Switch");
                }
            } else if (JsonClass1.this.noInternetDialog == null) {
                JsonClass1.this.showNetworkDialog("", "");
            } else if (!JsonClass1.this.noInternetDialog.isShowing()) {
                JsonClass1.this.noInternetDialog.showDialog();
            }
        }
    }

    public class SendHttpRequestTask extends AsyncTask<Object, Void, String> {
        public AsyncResponse delegate = null;

        public SendHttpRequestTask(AsyncResponse asyncResponse) {
            this.delegate = asyncResponse;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            if (JsonClass1.this.findViewById(R.id.layout_loading) != null) {
                JsonClass1.this.findViewById(R.id.layout_loading).setVisibility(0);
            }
        }

        protected void onPostExecute(String result) {
            if (JsonClass1.this.findViewById(R.id.layout_loading) != null) {
                JsonClass1.this.findViewById(R.id.layout_loading).setVisibility(8);
            }
            this.delegate.processFinish(result);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected java.lang.String doInBackground(java.lang.Object... r22) {
            /*
            r21 = this;
            r18 = 0;
            r17 = r22[r18];
            r17 = (java.lang.String) r17;
            r18 = 2;
            r8 = r22[r18];
            r8 = (java.util.HashMap) r8;
            r18 = 1;
            r6 = r22[r18];
            r6 = (java.util.List) r6;
            r18 = "JSONCLASS1";
            r19 = new java.lang.StringBuilder;
            r19.<init>();
            r20 = "params : ";
            r19 = r19.append(r20);
            r0 = r19;
            r19 = r0.append(r8);
            r19 = r19.toString();
            android.util.Log.d(r18, r19);
            r18 = "JSONCLASS1";
            r19 = new java.lang.StringBuilder;
            r19.<init>();
            r20 = "fileslist : ";
            r19 = r19.append(r20);
            r0 = r19;
            r19 = r0.append(r6);
            r19 = r19.toString();
            android.util.Log.d(r18, r19);
            r5 = "userfile";
            r18 = 3;
            r18 = r22[r18];
            r18 = (java.lang.String) r18;
            r18 = android.text.TextUtils.isEmpty(r18);
            if (r18 != 0) goto L_0x005a;
        L_0x0054:
            r18 = 3;
            r5 = r22[r18];
            r5 = (java.lang.String) r5;
        L_0x005a:
            r2 = "UTF-8";
            r15 = 0;
            r12 = 0;
            r13 = new com.apps.smartschoolmanagement.utils.MultipartUtility;	 Catch:{ IOException -> 0x0150 }
            r0 = r17;
            r13.<init>(r0, r2);	 Catch:{ IOException -> 0x0150 }
            r18 = r6.size();	 Catch:{ FileNotFoundException -> 0x00da }
            if (r18 != 0) goto L_0x00be;
        L_0x006b:
            r18 = r8.entrySet();	 Catch:{ IOException -> 0x00b8 }
            r9 = r18.iterator();	 Catch:{ IOException -> 0x00b8 }
        L_0x0073:
            r18 = r9.hasNext();	 Catch:{ IOException -> 0x00b8 }
            if (r18 == 0) goto L_0x011c;
        L_0x0079:
            r14 = r9.next();	 Catch:{ IOException -> 0x00b8 }
            r14 = (java.util.Map.Entry) r14;	 Catch:{ IOException -> 0x00b8 }
            r18 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00b8 }
            r18.<init>();	 Catch:{ IOException -> 0x00b8 }
            r19 = "";
            r18 = r18.append(r19);	 Catch:{ IOException -> 0x00b8 }
            r19 = r14.getKey();	 Catch:{ IOException -> 0x00b8 }
            r18 = r18.append(r19);	 Catch:{ IOException -> 0x00b8 }
            r18 = r18.toString();	 Catch:{ IOException -> 0x00b8 }
            r19 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00b8 }
            r19.<init>();	 Catch:{ IOException -> 0x00b8 }
            r20 = "";
            r19 = r19.append(r20);	 Catch:{ IOException -> 0x00b8 }
            r20 = r14.getValue();	 Catch:{ IOException -> 0x00b8 }
            r19 = r19.append(r20);	 Catch:{ IOException -> 0x00b8 }
            r19 = r19.toString();	 Catch:{ IOException -> 0x00b8 }
            r0 = r18;
            r1 = r19;
            r13.addFormField(r0, r1);	 Catch:{ IOException -> 0x00b8 }
            r9.remove();	 Catch:{ IOException -> 0x00b8 }
            goto L_0x0073;
        L_0x00b8:
            r3 = move-exception;
            r12 = r13;
        L_0x00ba:
            r3.printStackTrace();
        L_0x00bd:
            return r15;
        L_0x00be:
            r18 = r6.size();	 Catch:{ FileNotFoundException -> 0x00da }
            r19 = 1;
            r0 = r18;
            r1 = r19;
            if (r0 != r1) goto L_0x00e7;
        L_0x00ca:
            r18 = 0;
            r0 = r18;
            r18 = r6.get(r0);	 Catch:{ FileNotFoundException -> 0x00da }
            r18 = (java.io.File) r18;	 Catch:{ FileNotFoundException -> 0x00da }
            r0 = r18;
            r13.addFilePart(r5, r0);	 Catch:{ FileNotFoundException -> 0x00da }
            goto L_0x006b;
        L_0x00da:
            r3 = move-exception;
            r0 = r21;
            r0 = com.apps.smartschoolmanagement.utils.JsonClass1.this;	 Catch:{ IOException -> 0x00b8 }
            r18 = r0;
            r19 = "You are trying to upload a file from cache, which is not supported";
            r18.showToast(r19);	 Catch:{ IOException -> 0x00b8 }
            goto L_0x006b;
        L_0x00e7:
            r18 = r6.iterator();	 Catch:{ FileNotFoundException -> 0x00da }
        L_0x00eb:
            r19 = r18.hasNext();	 Catch:{ FileNotFoundException -> 0x00da }
            if (r19 == 0) goto L_0x006b;
        L_0x00f1:
            r4 = r18.next();	 Catch:{ FileNotFoundException -> 0x00da }
            r4 = (java.io.File) r4;	 Catch:{ FileNotFoundException -> 0x00da }
            r19 = r6.size();	 Catch:{ FileNotFoundException -> 0x00da }
            if (r19 == 0) goto L_0x00eb;
        L_0x00fd:
            r19 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x00da }
            r19.<init>();	 Catch:{ FileNotFoundException -> 0x00da }
            r0 = r19;
            r19 = r0.append(r5);	 Catch:{ FileNotFoundException -> 0x00da }
            r20 = r6.indexOf(r4);	 Catch:{ FileNotFoundException -> 0x00da }
            r20 = r20 + 1;
            r19 = r19.append(r20);	 Catch:{ FileNotFoundException -> 0x00da }
            r19 = r19.toString();	 Catch:{ FileNotFoundException -> 0x00da }
            r0 = r19;
            r13.addFilePart(r0, r4);	 Catch:{ FileNotFoundException -> 0x00da }
            goto L_0x00eb;
        L_0x011c:
            r15 = r13.finish();	 Catch:{ IOException -> 0x00b8 }
            r18 = android.text.TextUtils.isEmpty(r15);	 Catch:{ IOException -> 0x00b8 }
            if (r18 != 0) goto L_0x0148;
        L_0x0126:
            r7 = r15;
            r18 = "jkl";
            r0 = r18;
            android.util.Log.d(r0, r7);	 Catch:{ IOException -> 0x00b8 }
            r10 = 0;
            r11 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x014b }
            r11.<init>(r15);	 Catch:{ JSONException -> 0x014b }
            r18 = "status";
            r0 = r18;
            r16 = r11.getString(r0);	 Catch:{ JSONException -> 0x0153 }
            r18 = "success";
            r0 = r18;
            r1 = r16;
            r18 = r0.equals(r1);	 Catch:{ JSONException -> 0x0153 }
            if (r18 == 0) goto L_0x0148;
        L_0x0148:
            r12 = r13;
            goto L_0x00bd;
        L_0x014b:
            r3 = move-exception;
        L_0x014c:
            r3.printStackTrace();	 Catch:{ IOException -> 0x00b8 }
            goto L_0x0148;
        L_0x0150:
            r3 = move-exception;
            goto L_0x00ba;
        L_0x0153:
            r3 = move-exception;
            r10 = r11;
            goto L_0x014c;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.apps.smartschoolmanagement.utils.JsonClass1.SendHttpRequestTask.doInBackground(java.lang.Object[]):java.lang.String");
        }
    }

    public interface VolleyCallback {
        void onSuccess(String str);
    }

    public interface VolleyCallbackJSONObject {
        void onSuccess(JSONObject jSONObject);
    }

    public boolean isJsonObject(String res) {
        Object json = null;
        try {
            json = new JSONTokener(res).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json instanceof JSONObject) {
            return true;
        }
        return false;
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.noInternetDialog != null) {
            this.noInternetDialog.onDestroy();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        if (savedInstanceState == null) {
            Builder builder = new Builder(this);
            builder.setCancelable(true);
            if (this.noInternetDialog == null) {
                this.noInternetDialog = builder.build();
            }
        }
    }

    public void getJsonResponse(String url, final Context context, final VolleyCallback callback) {
        if (Connectivity.isConnected(context)) {
            if (findViewById(R.id.layout_loading) != null) {
                findViewById(R.id.layout_loading).setVisibility(0);
            }
            StringRequest strReq = new StringRequest(1, url, new Listener<String>() {
                public void onResponse(String response) {
                    Log.d("jkl", "Response: " + response.toString());
                    if (JsonClass1.this.findViewById(R.id.layout_loading) != null) {
                        JsonClass1.this.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.length() != 0) {
                            try {
                                if (jsonObject.isNull("error")) {
                                    callback.onSuccess(response);
                                    return;
                                }
                                String error = jsonObject.getString("error");
                                if (error != null) {
                                    JsonClass1.this.showToast(error);
                                    return;
                                }
                                return;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        JsonClass1.this.showToast("Failed to Retrieve Data");
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.e("jkl", "Error: " + error.getMessage());
                    if (JsonClass1.this.findViewById(R.id.layout_loading) != null) {
                        JsonClass1.this.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    if (Connectivity.isConnected(context)) {
                        if (error != null && error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.d("jkl", "Error Code: " + statusCode);
                            if (statusCode == 404) {
                                JsonClass1.this.showToast("URL Not Found");
                            }
                            try {
                                Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
                            } catch (UnsupportedEncodingException e) {
                            }
                        } else if (error == null || error.getMessage() == null) {
                            JsonClass1.this.showToast("Unidentified Server Response");
                        } else {
                            JsonClass1.this.showNetworkDialog("Your network speed too slow. Please switch your network", "Switch");
                        }
                    } else if (JsonClass1.this.noInternetDialog == null) {
                        JsonClass1.this.showNetworkDialog("", "");
                    } else if (!JsonClass1.this.noInternetDialog.isShowing()) {
                        JsonClass1.this.noInternetDialog.showDialog();
                    }
                }
            }) {
                protected Map<String, String> getParams() {
                    return JsonClass1.this.checkParams(JsonClass1.this.params);
                }
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1.0f));
            AppSingleton.getInstance(this).addToRequestQueue(strReq);
            return;
        }
        if (findViewById(R.id.layout_loading) != null) {
            findViewById(R.id.layout_loading).setVisibility(8);
        }
        if (this.noInternetDialog == null) {
            showNetworkDialog("", "");
        } else if (!this.noInternetDialog.isShowing()) {
            this.noInternetDialog.showDialog();
        }
    }

    public void getJsonResponseBackground(String url, final Context context, final VolleyCallback callback) {
        if (Connectivity.isConnected(context)) {
            StringRequest strReq = new StringRequest(1, url, new Listener<String>() {
                public void onResponse(String response) {
                    Log.d("jkl", "Response: " + response.toString());
                    if (JsonClass1.this.findViewById(R.id.layout_loading) != null) {
                        JsonClass1.this.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.length() != 0) {
                            try {
                                if (jsonObject.isNull("error")) {
                                    callback.onSuccess(response);
                                    return;
                                }
                                String error = jsonObject.getString("error");
                                if (error != null) {
                                    JsonClass1.this.showToast(error);
                                    return;
                                }
                                return;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        JsonClass1.this.showToast("Failed to Retrieve Data");
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.e("jkl", "Error: " + error.getMessage());
                    if (Connectivity.isConnected(context)) {
                        if (error != null && error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.d("jkl", "Error Code: " + statusCode);
                            if (statusCode == 404) {
                                JsonClass1.this.showToast("URL Not Found");
                            }
                            try {
                                Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
                            } catch (UnsupportedEncodingException e) {
                            }
                        } else if (error == null || error.getMessage() == null) {
                            JsonClass1.this.showToast("Unidentified Server Response");
                        } else {
                            JsonClass1.this.showNetworkDialog("Your network speed too slow. Please switch your network", "Switch");
                        }
                    } else if (JsonClass1.this.noInternetDialog == null) {
                        JsonClass1.this.showNetworkDialog("", "");
                    } else if (!JsonClass1.this.noInternetDialog.isShowing()) {
                        JsonClass1.this.noInternetDialog.showDialog();
                    }
                }
            }) {
                protected Map<String, String> getParams() {
                    return JsonClass1.this.checkParams(JsonClass1.this.params);
                }
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 1.0f));
            AppSingleton.getInstance(this).addToRequestQueue(strReq);
        } else if (this.noInternetDialog == null) {
            showNetworkDialog("", "");
        } else if (!this.noInternetDialog.isShowing()) {
            this.noInternetDialog.showDialog();
        }
    }

    private Map<String, String> checkParams(Map<String, String> map) {
        for (Entry<String, String> pairs : map.entrySet()) {
            if (pairs.getValue() == null) {
                map.put(pairs.getKey(), "");
            }
        }
        return map;
    }

    public void showNetworkDialog(String message, String action) {
        Intent intent = new Intent(this, NetworkDialogActivity.class);
        intent.putExtra("message", message);
        intent.putExtra("action", action);
        startActivity(new Intent(this, NetworkDialogActivity.class));
        AnimationSlideUtil.activityZoom(this);
    }

    public void showToast(final String msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(JsonClass1.this, msg, 0);
                toast.setView(toast.getView());
                toast.show();
            }
        });
    }

    public void getJsonResponse(String url, final VolleyCallbackJSONObject callback) {
        if (Connectivity.isConnected(this.context)) {
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(0, url, null, new Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    if (response.length() != 0) {
                        try {
                            if (response.isNull("error")) {
                                callback.onSuccess(response);
                                return;
                            }
                            String error = response.getString("error");
                            if (error != null) {
                                JsonClass1.this.showToast(error);
                                return;
                            }
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    JsonClass1.this.showToast("Failed to Retrieve Data");
                }
            }, new C14219());
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1.0f));
            AppSingleton.getInstance(this).addToRequestQueue(jsObjRequest);
        } else if (this.noInternetDialog == null || this.noInternetDialog.isShowing()) {
            showNetworkDialog("", "");
        } else {
            this.noInternetDialog.showDialog();
        }
    }

    public void loadData(ViewGroup parent, HashMap<String, String> returnData) {
        if (returnData.size() > 0) {
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof ViewGroup) {
                    loadData((ViewGroup) child, returnData);
                } else if (parent.getChildAt(i) instanceof TextView) {
                    TextView et = (TextView) parent.getChildAt(i);
                    if (et.getId() > 0 && returnData.containsKey(getResources().getResourceEntryName(et.getId()))) {
                        et.setText((CharSequence) returnData.get(getResources().getResourceEntryName(et.getId())));
                    }
                }
            }
            return;
        }
        showToast("No Data found");
    }

    public void uploadMultipartData(List<File> fileslist, String name, HashMap<String, String> hashmap, String url, final VolleyCallback volleyCallback) throws UnsupportedEncodingException {
        if (Connectivity.isConnected(this.context)) {
            SendHttpRequestTask t = new SendHttpRequestTask(new AsyncResponse() {
                public void processFinish(String output) {
                    volleyCallback.onSuccess(output);
                }
            });
            if (TextUtils.isEmpty(name)) {
                t.execute(new Object[]{url, fileslist, hashmap});
                return;
            }
            t.execute(new Object[]{url, fileslist, hashmap, name});
        } else if (this.noInternetDialog == null) {
            showNetworkDialog("", "");
        } else if (!this.noInternetDialog.isShowing()) {
            this.noInternetDialog.showDialog();
        }
    }

    public void downloadFile(String url, String name) {
        final Handler handler = new Handler();
        final ProgressDialog pDialog = new ProgressDialog(new ContextThemeWrapper(this, R.style.ProgressDialogTheme));
        pDialog.setMessage("Downloading...");
        pDialog.requestWindowFeature(1);
        pDialog.setMax(100);
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(1);
        pDialog.show();
        this.cancelled = false;
        final String str = url;
        final String str2 = name;
        this.f69t = new Thread(new Runnable() {

            /* renamed from: com.apps.smartschoolmanagement.utils.JsonClass1$11$1 */
            class C14091 implements Runnable {
                C14091() {
                }

                public void run() {
                    JsonClass1.this.downloadedSize = 0;
                    if (JsonClass1.this.totalSize > 1024) {
                        JsonClass1.this.totalSize /= 1024;
                    }
                    pDialog.setMax(JsonClass1.this.totalSize);
                    pDialog.show();
                }
            }

            /* renamed from: com.apps.smartschoolmanagement.utils.JsonClass1$11$3 */
            class C14123 implements Runnable {
                C14123() {
                }

                public void run() {
                    pDialog.setProgress(JsonClass1.this.downloadedSize / 1024);
                    pDialog.setProgressNumberFormat(JsonClass1.bytes2String(JsonClass1.this.downloadedSize) + "/ " + JsonClass1.bytes2String(JsonClass1.this.totalSize * 1024));
                }
            }

            public void run() {
                try {
                    File folder = new File(Environment.getExternalStorageDirectory() + "/Enggific/");
                    boolean success = true;
                    if (!folder.exists()) {
                        success = folder.mkdir();
                    }
                    if (success) {
                        HttpURLConnection urlConnection = (HttpURLConnection) new URL(str).openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setRequestProperty(HttpHeaders.ACCEPT, "*/*");
                        urlConnection.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, "identity");
                        urlConnection.connect();
                        final File file = new File(Environment.getExternalStorageDirectory() + "/Enggific/" + str2);
                        if (file.exists()) {
                            JsonClass1.this.showToast("File already Exists");
                            pDialog.dismiss();
                            return;
                        }
                        file.createNewFile();
                        final FileOutputStream fileOutput = new FileOutputStream(file);
                        final InputStream inputStream = urlConnection.getInputStream();
                        JsonClass1.this.totalSize = urlConnection.getContentLength();
                        handler.post(new C14091());
                        pDialog.setOnCancelListener(new OnCancelListener() {

                            /* renamed from: com.apps.smartschoolmanagement.utils.JsonClass1$11$2$1 */
                            class C14101 implements Runnable {
                                C14101() {
                                }

                                public void run() {
                                    try {
                                        fileOutput.close();
                                        inputStream.close();
                                        Thread.currentThread().interrupt();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            public void onCancel(DialogInterface dialog) {
                                try {
                                    file.delete();
                                    JsonClass1.this.cancelled = true;
                                    Thread thread = new Thread(new C14101());
                                } catch (NullPointerException e) {
                                }
                            }
                        });
                        byte[] buffer = new byte[1024];
                        while (true) {
                            int bufferLength = inputStream.read(buffer);
                            if (bufferLength <= 0) {
                                break;
                            }
                            fileOutput.write(buffer, 0, bufferLength);
                            JsonClass1 jsonClass1 = JsonClass1.this;
                            jsonClass1.downloadedSize += bufferLength;
                            float per = (((float) JsonClass1.this.downloadedSize) / ((float) JsonClass1.this.totalSize)) * 100.0f;
                            new Handler(Looper.getMainLooper()).post(new C14123());
                        }
                        pDialog.dismiss();
                        if (!JsonClass1.this.cancelled) {
                            fileOutput.close();
                            inputStream.close();
                            JsonClass1.this.showFile(str2);
                        }
                    }
                } catch (MalformedURLException e) {
                    pDialog.dismiss();
                    JsonClass1.this.showToast("Error : MalformedURLException " + e);
                    e.printStackTrace();
                } catch (IOException e2) {
                    pDialog.dismiss();
                    JsonClass1.this.showToast("Error : IOException " + e2.getMessage());
                    e2.printStackTrace();
                } catch (Exception e3) {
                    pDialog.dismiss();
                    JsonClass1.this.showToast("Error : Please check your internet connection " + e3);
                }
            }
        });
        this.f69t.start();
    }

    public static String bytes2String(int sizeInBytes) {
        NumberFormat nf = new DecimalFormat();
        nf.setMaximumFractionDigits(2);
        if (((double) sizeInBytes) < 1024.0d) {
            try {
                return nf.format((long) sizeInBytes) + " Byte(s)";
            } catch (Exception e) {
                return sizeInBytes + " Byte(s)";
            }
        } else if (((double) sizeInBytes) < 1048576.0d) {
            return nf.format(((double) sizeInBytes) / 1024.0d) + " KB";
        } else {
            if (((double) sizeInBytes) < 1.073741824E9d) {
                return nf.format(((double) sizeInBytes) / 1048576.0d) + " MB";
            }
            if (((double) sizeInBytes) < 1.099511627776E12d) {
                return nf.format(((double) sizeInBytes) / 1.073741824E9d) + " GB";
            }
            return nf.format(((double) sizeInBytes) / 1.099511627776E12d) + " TB";
        }
    }

    public void showFile(String filename) {
        File file = new File(Environment.getExternalStorageDirectory() + "/Enggific/" + filename);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.getName().substring(file.getName().lastIndexOf(".") + 1));
        if (VERSION.SDK_INT >= 24) {
            intent.setFlags(1);
            intent.setDataAndType(FileProvider.getUriForFile(this, "in.findlogics.zoomin.FileProvider", file), type);
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
        startActivity(intent);
    }

    public long getFreeMemory() {
        long bytesAvailable;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        if (VERSION.SDK_INT >= 18) {
            bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } else {
            bytesAvailable = ((long) stat.getBlockSize()) * ((long) stat.getAvailableBlocks());
        }
        long megAvailable = bytesAvailable / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
        Log.d("Saving File : ", "Available MB : " + megAvailable);
        return megAvailable;
    }

    public static String floatForm(double d) {
        return new DecimalFormat("#.##").format(d);
    }

    public static String bytesToHuman(long size) {
        long Mb = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        long Gb = Mb * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        long Tb = Gb * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        long Pb = Tb * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        long Eb = Pb * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        if (size < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            return floatForm((double) size) + " byte";
        }
        if (size >= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID && size < Mb) {
            return floatForm(((double) size) / ((double) 1024)) + " Kb";
        }
        if (size >= Mb && size < Gb) {
            return floatForm(((double) size) / ((double) Mb)) + " Mb";
        }
        if (size >= Gb && size < Tb) {
            return floatForm(((double) size) / ((double) Gb)) + " Gb";
        }
        if (size >= Tb && size < Pb) {
            return floatForm(((double) size) / ((double) Tb)) + " Tb";
        }
        if (size >= Pb && size < Eb) {
            return floatForm(((double) size) / ((double) Pb)) + " Pb";
        }
        if (size >= Eb) {
            return floatForm(((double) size) / ((double) Eb)) + " Eb";
        }
        return "???";
    }
}
