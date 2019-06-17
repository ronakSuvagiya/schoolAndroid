package com.apps.smartschoolmanagement.utils;

import am.appwise.components.ni.NoInternetDialog;
import am.appwise.components.ni.NoInternetDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
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
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonFragment1 extends Fragment {
    boolean cancelled = false;
    int downloadedSize = 0;
    NoInternetDialog noInternetDialog = null;
    public HashMap<String, String> params = new HashMap();
    /* renamed from: t */
    Thread f70t;
    int totalSize = 0;

    public interface VolleyCallback {
        void onSuccess(String str);
    }

    public interface VolleyCallbackJSONObject {
        void onSuccess(JSONObject jSONObject);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.noInternetDialog != null) {
            this.noInternetDialog.onDestroy();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.noInternetDialog != null) {
            this.noInternetDialog.onDestroy();
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        builder.setCancelable(true);
        if (this.noInternetDialog == null) {
            this.noInternetDialog = builder.build();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void changeFontStyle(ViewGroup parent, String name) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                changeFontStyle((ViewGroup) child, name);
            } else if (child instanceof TextView) {
                TextView sp = (TextView) child;
                String tag = null;
                if (sp != null) {
                    try {
                        tag = (String) sp.getTag();
                    } catch (Exception exe) {
                        showToast(exe.getMessage());
                        Log.i("error", exe.getMessage());
                    }
                    if (tag != null && "title".equals(tag)) {
                        sp.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/" + name));
                    }
                }
            }
        }
    }

    public int convertDpToPixels(float dp) {
        return (int) TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
    }

    public String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getResourceId(String pVariableName, String pResourcename, String pPackageName) {
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void getJsonResponse(String url, final View fragmetn_view, final VolleyCallback callback) {
        if (Connectivity.isConnected(getActivity())) {
            if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
                fragmetn_view.findViewById(R.id.layout_loading).setVisibility(0);
            }
            StringRequest strReq = new StringRequest(1, url, new Listener<String>() {
                public void onResponse(String response) {
                    Log.d("jkl", "Register Response: " + response.toString());
                    if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
                        fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
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
                                    Toast.makeText(JsonFragment1.this.getContext(), error, 1).show();
                                    return;
                                }
                                return;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        Toast.makeText(JsonFragment1.this.getContext(), "Failed to Retrieve Data", 1).show();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.e("klj", "Login Error: " + error.getMessage());
                    if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
                        fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    if (Connectivity.isConnected(JsonFragment1.this.getActivity())) {
                        if (error == null || error.networkResponse == null) {
                            JsonFragment1.this.showToast("Unidentified Server Response");
                            return;
                        }
                        int statusCode = error.networkResponse.statusCode;
                        Log.d("jkl", "Error Code: " + statusCode);
                        if (statusCode == 404) {
                            JsonFragment1.this.showToast("URL Not Found");
                        }
                        try {
                            Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
                        } catch (UnsupportedEncodingException e) {
                        }
                    } else if (JsonFragment1.this.noInternetDialog == null) {
                        JsonFragment1.this.showNetworkDialog("", "");
                    } else if (!JsonFragment1.this.noInternetDialog.isShowing()) {
                        JsonFragment1.this.noInternetDialog.showDialog();
                    }
                }
            }) {
                protected Map<String, String> getParams() {
                    return JsonFragment1.this.checkParams(JsonFragment1.this.params);
                }
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1.0f));
            AppSingleton.getInstance(getContext()).addToRequestQueue(strReq);
            return;
        }
        if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
            fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
        }
        if (this.noInternetDialog == null) {
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

    public void getJsonResponse(String url, final View fragmetn_view, final VolleyCallbackJSONObject callback) {
        if (Connectivity.isConnected(getActivity())) {
            if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
                fragmetn_view.findViewById(R.id.layout_loading).setVisibility(0);
            }
            Log.d("jkl", "URL : " + url);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(0, url, null, new Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    Log.d("jkl", "Response: " + response.toString());
                    if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
                        fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    if (response == null || response.length() == 0) {
                        Toast.makeText(JsonFragment1.this.getContext(), "Failed to Retrieve Data", 1).show();
                    } else {
                        callback.onSuccess(response);
                    }
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.e("jkl", "Error: " + error.getMessage());
                    if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
                        fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    if (Connectivity.isConnected(JsonFragment1.this.getActivity())) {
                        if (error == null || error.networkResponse == null) {
                            JsonFragment1.this.showToast("Unidentified Server Response");
                            return;
                        }
                        int statusCode = error.networkResponse.statusCode;
                        Log.d("jkl", "Error Code: " + statusCode);
                        if (statusCode == 404) {
                            JsonFragment1.this.showToast("URL Not Found");
                        }
                        try {
                            Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
                        } catch (UnsupportedEncodingException e) {
                        }
                    } else if (JsonFragment1.this.noInternetDialog == null) {
                        JsonFragment1.this.showNetworkDialog("", "");
                    } else if (!JsonFragment1.this.noInternetDialog.isShowing()) {
                        JsonFragment1.this.noInternetDialog.showDialog();
                    }
                }
            });
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 1.0f));
            AppSingleton.getInstance(getContext()).addToRequestQueue(jsObjRequest);
            return;
        }
        if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
            fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
        }
        if (this.noInternetDialog == null) {
            showNetworkDialog("", "");
        } else if (!this.noInternetDialog.isShowing()) {
            this.noInternetDialog.showDialog();
        }
    }

    private void showNetworkDialog(String message, String action) {
        Intent intent = new Intent(getContext(), NetworkDialogActivity.class);
        intent.putExtra("message", "No Internet Connection or Slow Network");
        intent.putExtra("action", "Switch Network");
        startActivity(new Intent(getContext(), NetworkDialogActivity.class));
        AnimationSlideUtil.activityZoom(getContext());
    }

    public void downloadFile(String url, String name) {
        final Handler handler = new Handler();
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setTitle("Downloading...");
        pDialog.setMax(100);
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(1);
        pDialog.show();
        this.cancelled = false;
        final String str = url;
        final String str2 = name;
        this.f70t = new Thread(new Runnable() {

            /* renamed from: com.apps.smartschoolmanagement.utils.JsonFragment1$6$1 */
            class C14411 implements Runnable {
                C14411() {
                }

                public void run() {
                    JsonFragment1.this.downloadedSize = 0;
                    if (JsonFragment1.this.totalSize > 1024) {
                        JsonFragment1.this.totalSize /= 1024;
                    }
                    pDialog.setMax(JsonFragment1.this.totalSize);
                    pDialog.show();
                }
            }

            public void run() {
                try {
                    File folder = new File(Environment.getExternalStorageDirectory() + "/hospital/");
                    boolean success = true;
                    if (!folder.exists()) {
                        success = folder.mkdir();
                    }
                    if (success) {
                        HttpURLConnection urlConnection = (HttpURLConnection) new URL(str).openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setDoOutput(true);
                        urlConnection.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, "identity");
                        urlConnection.connect();
                        final FileOutputStream fileOutput = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/hospital/" + str2));
                        final InputStream inputStream = urlConnection.getInputStream();
                        JsonFragment1.this.totalSize = urlConnection.getContentLength();
                        handler.post(new C14411());
                        pDialog.setOnCancelListener(new OnCancelListener() {

                            /* renamed from: com.apps.smartschoolmanagement.utils.JsonFragment1$6$2$1 */
                            class C14421 implements Runnable {
                                C14421() {
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
                                    new File(Environment.getExternalStorageDirectory() + "/hospital/" + str2).delete();
                                    JsonFragment1.this.cancelled = true;
                                    Thread thread = new Thread(new C14421());
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
                            JsonFragment1 jsonFragment1 = JsonFragment1.this;
                            jsonFragment1.downloadedSize += bufferLength;
                            float per = (((float) JsonFragment1.this.downloadedSize) / ((float) JsonFragment1.this.totalSize)) * 100.0f;
                            pDialog.setProgress(JsonFragment1.this.downloadedSize / 1024);
                        }
                        pDialog.dismiss();
                        if (!JsonFragment1.this.cancelled) {
                            fileOutput.close();
                            inputStream.close();
                            JsonFragment1.this.showFile(str2);
                        }
                    }
                } catch (MalformedURLException e) {
                    pDialog.dismiss();
                    JsonFragment1.this.showToast("Error : MalformedURLException " + e);
                    e.printStackTrace();
                } catch (IOException e2) {
                    pDialog.dismiss();
                    JsonFragment1.this.showToast("Error : IOException " + e2);
                    e2.printStackTrace();
                } catch (Exception e3) {
                    pDialog.dismiss();
                    JsonFragment1.this.showToast("Error : Please check your internet connection " + e3);
                }
            }
        });
        this.f70t.start();
    }

    public void showToast(final String err) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Toast.makeText(JsonFragment1.this.getActivity(), err, 0).show();
            }
        });
    }

    public void showFile(String filename) {
        File file = new File(Environment.getExternalStorageDirectory() + "/hospital/" + filename);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.getName().substring(file.getName().lastIndexOf(".") + 1));
        if (VERSION.SDK_INT >= 24) {
            intent.setFlags(1);
            intent.setDataAndType(FileProvider.getUriForFile(getActivity(), "in.findlogics.zoomin.FileProvider", file), type);
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
        startActivity(intent);
    }
}
