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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.load.Key;
import com.google.common.net.HttpHeaders;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity.Utils;
import com.apps.smartschoolmanagement.activities.NetworkDialogActivity;
import com.apps.smartschoolmanagement.adapters.SpinnerrAdapter;
import com.apps.smartschoolmanagement.models.ListData;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonClass extends AppCompatActivity {
    public static final double SPACE_GB = 1.073741824E9d;
    public static final double SPACE_KB = 1024.0d;
    public static final double SPACE_MB = 1048576.0d;
    public static final double SPACE_TB = 1.099511627776E12d;
    boolean cancelled = false;
    Context context;
    int downloadedSize = 0;
    public NoInternetDialog noInternetDialog = null;
    public HashMap<String, String> params = new HashMap();
    public HashMap<String, String> responseParams = new HashMap();
    /* renamed from: t */
    Thread f62t;
    int totalSize = 0;
    public ArrayList<ListData> values = new ArrayList();

    public interface VolleyCallback {
        void onSuccess(String str);
    }

    public interface AsyncResponse {
        void processFinish(String str);
    }

    public interface VolleyCallbackJSONObject {
        void onSuccess(JSONObject jSONObject);
    }

    public interface VolleyCallbackJSONArray {
        void onSuccess(JSONArray jsonArray);
    }

    public class SendHttpRequestTask extends AsyncTask<Object, Void, String> {
        public AsyncResponse delegate = null;

        public SendHttpRequestTask(AsyncResponse asyncResponse) {
            this.delegate = asyncResponse;
        }

        @Override
        protected String doInBackground(Object... objects) {
            return null;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            if (JsonClass.this.findViewById(R.id.layout_loading) != null) {
                JsonClass.this.findViewById(R.id.layout_loading).setVisibility(0);
            }
        }

        protected void onPostExecute(String result) {
            if (JsonClass.this.findViewById(R.id.layout_loading) != null) {
                JsonClass.this.findViewById(R.id.layout_loading).setVisibility(8);
            }
            this.delegate.processFinish(result);
        }
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
        Builder builder = new Builder(this);
        builder.setCancelable(true);
        if (this.noInternetDialog == null) {
            this.noInternetDialog = builder.build();
        }
    }

    public void getJsonResponse(String url, final Context context, final VolleyCallbackJSONArray callback) {
        if (Connectivity.isConnected(context)) {
            if (findViewById(R.id.layout_loading) != null) {
                findViewById(R.id.layout_loading).setVisibility(0);
            }
            JsonArrayRequest strReq = new JsonArrayRequest(Request.Method.GET, url, null, new Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (JsonClass.this.findViewById(R.id.layout_loading) != null) {
                        JsonClass.this.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    callback.onSuccess(response);
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.e("jkl", "Error: " + error.getMessage());
                    if (JsonClass.this.findViewById(R.id.layout_loading) != null) {
                        JsonClass.this.findViewById(R.id.layout_loading).setVisibility(8);
                    }

                    if (error != null && error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        Log.d("jkl", "Error Code: " + statusCode);
                        if (statusCode == 404) {
                            JsonClass.this.showToast("URL Not Found");
                        }
                        try {
                            Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
                        } catch (UnsupportedEncodingException e) {
                        }
                    } else if (error == null || error.getMessage() == null) {
                        JsonClass.this.showToast("Unidentified Server Response");
                    } else if (!MyApplication.isActivityVisible()) {
                        JsonClass.this.showNetworkDialog("Your network speed too slow. Please switch your network", "Switch");
                    }
                }
            }) {
                protected Map<String, String> getParams() {
                    return JsonClass.this.checkParams(JsonClass.this.params);
                }
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1.0f));
            AppSingleton.getInstance(this).addToRequestQueue(strReq);
            return;
        }
        if (findViewById(R.id.layout_loading) != null) {
            findViewById(R.id.layout_loading).setVisibility(8);
        }
    }


    public void getJsonResponse(String url, final Context context, final VolleyCallbackJSONObject callback) {
        if (Connectivity.isConnected(context)) {
            if (findViewById(R.id.layout_loading) != null) {
                findViewById(R.id.layout_loading).setVisibility(0);
            }
            JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, url, null, new Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (JsonClass.this.findViewById(R.id.layout_loading) != null) {
                        JsonClass.this.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    callback.onSuccess(response);
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.e("jkl", "Error: " + error.getMessage());
                    if (JsonClass.this.findViewById(R.id.layout_loading) != null) {
                        JsonClass.this.findViewById(R.id.layout_loading).setVisibility(8);
                    }

                    if (error != null && error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        Log.d("jkl", "Error Code: " + statusCode);
                        if (statusCode == 404) {
                            JsonClass.this.showToast("URL Not Found");
                        }
                        try {
                            Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
                        } catch (UnsupportedEncodingException e) {
                        }
                    } else if (error == null || error.getMessage() == null) {
                        JsonClass.this.showToast("Unidentified Server Response");
                    } else if (!MyApplication.isActivityVisible()) {
                        JsonClass.this.showNetworkDialog("Your network speed too slow. Please switch your network", "Switch");
                    }
                }
            }) {
                protected Map<String, String> getParams() {
                    return JsonClass.this.checkParams(JsonClass.this.params);
                }
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1.0f));
            AppSingleton.getInstance(this).addToRequestQueue(strReq);
            return;
        }
        if (findViewById(R.id.layout_loading) != null) {
            findViewById(R.id.layout_loading).setVisibility(8);
        }
    }

    public void getJsonResponseBackground(String url, final Context context, final VolleyCallback callback) {
        if (Connectivity.isConnected(context)) {
            StringRequest strReq = new StringRequest(1, url, new Listener<String>() {
                public void onResponse(String response) {
                    Log.d("jkl", "Response: " + response.toString());
                    if (JsonClass.this.findViewById(R.id.layout_loading) != null) {
                        JsonClass.this.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    try {
                        if (new JSONObject(response).length() != 0) {
                            callback.onSuccess(response);
                        } else {
                            JsonClass.this.showToast("Failed to Retrieve Data");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                                JsonClass.this.showToast("URL Not Found");
                            }
                            try {
                                Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
                            } catch (UnsupportedEncodingException e) {
                            }
                        } else if (error == null || error.getMessage() == null) {
                            JsonClass.this.showToast("Unidentified Server Response");
                        } else if (!MyApplication.isActivityVisible()) {
                            JsonClass.this.showNetworkDialog("Your network speed too slow. Please switch your network", "Switch");
                        }
                    } else if (JsonClass.this.noInternetDialog == null) {
                        JsonClass.this.showNetworkDialog("", "");
                    } else if (!JsonClass.this.noInternetDialog.isShowing()) {
                        JsonClass.this.noInternetDialog.showDialog();
                    }
                }
            }) {
                protected Map<String, String> getParams() {
                    return JsonClass.this.checkParams(JsonClass.this.params);
                }
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1.0f));
            AppSingleton.getInstance(this).addToRequestQueue(strReq);
        } else if (this.noInternetDialog != null) {
            if (!this.noInternetDialog.isShowing()) {
                this.noInternetDialog.showDialog();
            }
        } else if (!MyApplication.isActivityVisible()) {
            showNetworkDialog("", "");
        }
    }

//    public void getSpinnerData(Context context, String url, Spinner _spinner, final VolleyCallback callback) {
//        if (Connectivity.isConnected(context)) {
//            ArrayList<CodeValue> _CodeValues = new ArrayList();
//            if (ProfileInfo.getInstance().hasCodes(url)) {
//                SpinnerrAdapter adapter = new SpinnerrAdapter(this, R.layout.spinner_selected_item, ProfileInfo.getInstance().getCodeValues(url)) {
//                    public boolean isEnabled(int position) {
//                        if (position == 0) {
//                            return false;
//                        }
//                        return true;
//                    }
//                };
//                adapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
//                _spinner.setAdapter(adapter);
//                SpinnerHelper spinnerHelper = new SpinnerHelper(_spinner, adapter, new VolleyCallback() {
//                    public void onSuccess(String result) {
//                        callback.onSuccess(result);
//                    }
//                });
//                return;
//            }
//            final Context context2 = context;
//            final Spinner spinner = _spinner;
//            final String str = url;
//            final VolleyCallback volleyCallback = callback;
//            getJsonResponse(url, new VolleyCallbackJSONObject() {
//
//                /* renamed from: com.apps.smartschoolmanagement.utils.JsonClass$9$1 */
//                class C14071 implements VolleyCallback {
//                    C14071() {
//                    }
//
//                    public void onSuccess(String result) {
//                        volleyCallback.onSuccess(result);
//                    }
//                }
//
//                public void onSuccess(JSONObject result) {
//                    JsonClass.this.processJSONResult(context2, spinner, result, str, new C14071());
//                }
//            });
//        } else if (this.noInternetDialog != null) {
//            if (!this.noInternetDialog.isShowing()) {
//                this.noInternetDialog.showDialog();
//            }
//        } else if (!MyApplication.isActivityVisible()) {
//            showNetworkDialog("", "");
//        }
//    }

    public void getSpinnerData(Context context, String url, Spinner _spinner, String paramkeyvalue, VolleyCallback callback) {
        this.params.clear();
        this.params.put(paramkeyvalue.split(",")[0], paramkeyvalue.split(",")[1]);
        final Context context2 = context;
        final Spinner spinner = _spinner;
        final String str = url;
        final VolleyCallback volleyCallback = callback;
//        getJsonResponse(url, context, new VolleyCallback() {
//
//            /* renamed from: com.apps.smartschoolmanagement.utils.JsonClass$10$1 */
//            class C13941 implements VolleyCallback {
//                C13941() {
//                }
//
//                public void onSuccess(String result) {
//                    volleyCallback.onSuccess(result);
//                }
//            }
//
////            public void onSuccess(String result) {
////                try {
////                    JsonClass.this.processJSONResult(context2, spinner, new JSONObject(result), str, new C13941());
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////            }
//        });
    }

//    public void processJSONResult(Context context, final Spinner _spinner, JSONObject jsonObject, String url, VolleyCallback callback) {
//        ArrayList<CodeValue> spinnerValues = new ArrayList();
//        final CodeValue[] mCodeValue = new CodeValue[]{new CodeValue()};
//        try {
//            CodeValue c = new CodeValue();
//            c.setCodeValue(jsonObject.getString("title"));
//            spinnerValues.add(c);
//            JSONArray jsonArray = jsonObject.getJSONArray("ResponseList");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                String id = jsonArray.getJSONObject(i).optString("id").toString();
//                String value = jsonArray.getJSONObject(i).optString(Utils.imageName).toString();
//                CodeValue code = new CodeValue();
//                code.setCodeID(id);
//                code.setCodeValue(value);
//                spinnerValues.add(code);
//            }
//            if (jsonArray.length() > 0) {
//                ProfileInfo.getInstance().addCodes(url, spinnerValues);
//            }
//            SpinnerrAdapter adapter = new SpinnerrAdapter(this, R.layout.spinner_selected_item, spinnerValues) {
//                public boolean isEnabled(int position) {
//                    if (position == 0) {
//                        return false;
//                    }
//                    return true;
//                }
//            };
//            adapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
//            _spinner.setAdapter(adapter);
//            final VolleyCallback volleyCallback = callback;
//            _spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                    if (position > 0) {
//                        mCodeValue[0] = (CodeValue) _spinner.getItemAtPosition(position);
//                        volleyCallback.onSuccess(mCodeValue[0].getCodeID());
//                    }
//                }
//
//                public void onNothingSelected(AdapterView<?> adapterView) {
//                }
//            });
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

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
                Toast toast = Toast.makeText(JsonClass.this, msg, 0);
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
                        callback.onSuccess(response);
                    } else {
                        JsonClass.this.showToast("Failed to Retrieve Data");
                    }
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    if (Connectivity.isConnected(JsonClass.this.context)) {
                        if (error != null && error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.d("jkl", "Error Code: " + statusCode);
                            if (statusCode == 404) {
                                JsonClass.this.showToast("URL Not Found");
                            }
                            try {
                                Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
                            } catch (UnsupportedEncodingException e) {
                            }
                        } else if (error == null || error.getMessage() == null) {
                            JsonClass.this.showToast("Unidentified Server Response");
                        } else if (!MyApplication.isActivityVisible()) {
                            JsonClass.this.showNetworkDialog("Your network speed too slow. Please switch your network", "Switch");
                        }
                    } else if (JsonClass.this.noInternetDialog != null) {
                        if (!JsonClass.this.noInternetDialog.isShowing()) {
                            JsonClass.this.noInternetDialog.showDialog();
                        }
                    } else if (!MyApplication.isActivityVisible()) {
                        JsonClass.this.showNetworkDialog("", "");
                    }
                }
            });
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1.0f));
//            AppSingleton.getInstance(this).addToRequestQueue(jsObjRequest);
        } else if (this.noInternetDialog != null && !this.noInternetDialog.isShowing()) {
            this.noInternetDialog.showDialog();
        } else if (!MyApplication.isActivityVisible()) {
            showNetworkDialog("", "");
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
        } else if (this.noInternetDialog != null) {
            if (!this.noInternetDialog.isShowing()) {
                this.noInternetDialog.showDialog();
            }
        } else if (!MyApplication.isActivityVisible()) {
            showNetworkDialog("", "");
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
        this.f62t = new Thread(new Runnable() {

            /* renamed from: com.apps.smartschoolmanagement.utils.JsonClass$17$1 */
            class C13951 implements Runnable {
                C13951() {
                }

                public void run() {
                    JsonClass.this.downloadedSize = 0;
                    if (JsonClass.this.totalSize > 1024) {
                        JsonClass.this.totalSize /= 1024;
                    }
                    pDialog.setMax(JsonClass.this.totalSize);
                    pDialog.show();
                }
            }

            /* renamed from: com.apps.smartschoolmanagement.utils.JsonClass$17$3 */
            class C13983 implements Runnable {
                C13983() {
                }

                public void run() {
                    pDialog.setProgress(JsonClass.this.downloadedSize / 1024);
                    pDialog.setProgressNumberFormat(JsonClass.bytes2String(JsonClass.this.downloadedSize) + "/ " + JsonClass.bytes2String(JsonClass.this.totalSize * 1024));
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
                            JsonClass.this.showToast("File already Exists");
                            pDialog.dismiss();
                            return;
                        }
                        file.createNewFile();
                        final FileOutputStream fileOutput = new FileOutputStream(file);
                        final InputStream inputStream = urlConnection.getInputStream();
                        JsonClass.this.totalSize = urlConnection.getContentLength();
                        handler.post(new C13951());
                        pDialog.setOnCancelListener(new OnCancelListener() {

                            /* renamed from: com.apps.smartschoolmanagement.utils.JsonClass$17$2$1 */
                            class C13961 implements Runnable {
                                C13961() {
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
                                    JsonClass.this.cancelled = true;
                                    Thread thread = new Thread(new C13961());
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
                            JsonClass jsonClass = JsonClass.this;
                            jsonClass.downloadedSize += bufferLength;
                            float per = (((float) JsonClass.this.downloadedSize) / ((float) JsonClass.this.totalSize)) * 100.0f;
                            new Handler(Looper.getMainLooper()).post(new C13983());
                        }
                        pDialog.dismiss();
                        if (!JsonClass.this.cancelled) {
                            fileOutput.close();
                            inputStream.close();
                            JsonClass.this.showFile(str2);
                        }
                    }
                } catch (MalformedURLException e) {
                    pDialog.dismiss();
                    JsonClass.this.showToast("Error : MalformedURLException " + e);
                    e.printStackTrace();
                } catch (IOException e2) {
                    pDialog.dismiss();
                    JsonClass.this.showToast("Error : IOException " + e2.getMessage());
                    e2.printStackTrace();
                } catch (Exception e3) {
                    pDialog.dismiss();
                    JsonClass.this.showToast("Error : Please check your internet connection " + e3);
                }
            }
        });
        this.f62t.start();
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
