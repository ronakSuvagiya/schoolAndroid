package com.apps.smartschoolmanagement.utils;

import am.appwise.components.ni.NoInternetDialog;
import am.appwise.components.ni.NoInternetDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.apps.smartschoolmanagement.models.Leaves;
import com.apps.smartschoolmanagement.models.ListData;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonFragment extends Fragment {
    boolean cancelled = false;
    int downloadedSize = 0;
    public ArrayList<Leaves> leaves_values = new ArrayList();
    NoInternetDialog noInternetDialog = null;
    public HashMap<String, String> params = new HashMap();
    public HashMap<String, String> responseParams = new HashMap();
    /* renamed from: t */
    Thread f64t;
    int totalSize = 0;
    public ArrayList<ListData> values = new ArrayList();

    public interface VolleyCallback {
        void onSuccess(String str);
    }

    public interface VolleyCallbackJSONObject {
        void onSuccess(JSONObject jSONObject);
    }

    public interface VolleyCallbackJSONArray {
        void onSuccess(JSONArray jsonArray);
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
//                        showToast(exe.getMessage());
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
    public void postJsonResponse(String url,JSONObject senddata ,final View fragmetn_view, final VolleyCallbackJSONObject callback){
        if (Connectivity.isConnected(getActivity())) {
            if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
                fragmetn_view.findViewById(R.id.layout_loading).setVisibility(0);
            }
            JsonObjectRequest jsonObj = new JsonObjectRequest(url,senddata, new Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("jkl", "Response: " + response.toString());
                    if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
                        fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    callback.onSuccess(response);
                    }
            }, new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("jkl", "Error: " + error.getMessage());
                    if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
                        fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    if (Connectivity.isConnected(JsonFragment.this.getActivity())) {
                        if (error == null || error.networkResponse == null) {
//                            JsonFragment.this.showToast("Unidentified Server Response");
                            return;
                        }
                        int statusCode = error.networkResponse.statusCode;
                        Log.d("jkl", "Error Code: " + statusCode);
                        if (statusCode == 404) {
//                            JsonFragment.this.showToast("URL Not Found");
                        }
                        try {
                            Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
                        } catch (UnsupportedEncodingException e) {
                        }
                    } else if (JsonFragment.this.noInternetDialog != null) {
                        if (!JsonFragment.this.noInternetDialog.isShowing()) {
                            JsonFragment.this.noInternetDialog.showDialog();
                        }
                    } else if (!MyApplication.isActivityVisible()) {
//                        JsonFragment.this.showNetworkDialog("", "");
                    }
                }
            }){
                protected Map<String, String> getParams() {
                    return (Map<String, String>) JsonFragment.this;
//                    .checkParams(JsonFragment.this.params);

                }
            };
            jsonObj.setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1.0f));
            AppSingleton.getInstance(getContext()).addToRequestQueue(jsonObj);
            return;
        }
        if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
            fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
        }
        if (this.noInternetDialog != null) {
            if (!this.noInternetDialog.isShowing()) {
                this.noInternetDialog.showDialog();
            }
        } else if (!MyApplication.isActivityVisible()) {
//            showNetworkDialog("", "");
        }
    }

    public void getJsonResponse(String url, final View fragmetn_view, final VolleyCallbackJSONArray callback) {
        if (Connectivity.isConnected(getActivity())) {
            if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
                fragmetn_view.findViewById(R.id.layout_loading).setVisibility(0);
            }
            JsonArrayRequest strReq = new JsonArrayRequest(Request.Method.GET,url,null, new Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("jkl", "Response: " + response.toString());
                    if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
                        fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    callback.onSuccess(response);
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.e("jkl", "Error: " + error.getMessage());
                    if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
                        fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
                    }
                    if (Connectivity.isConnected(JsonFragment.this.getActivity())) {
                        if (error == null || error.networkResponse == null) {
//                            JsonFragment.this.showToast("Unidentified Server Response");
                            return;
                        }
                        int statusCode = error.networkResponse.statusCode;
                        Log.d("jkl", "Error Code: " + statusCode);
                        if (statusCode == 404) {
//                            JsonFragment.this.showToast("URL Not Found");
                        }
                        try {
                            Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
                        } catch (UnsupportedEncodingException e) {
                        }
                    } else if (JsonFragment.this.noInternetDialog != null) {
                        if (!JsonFragment.this.noInternetDialog.isShowing()) {
                            JsonFragment.this.noInternetDialog.showDialog();
                        }
                    } else if (!MyApplication.isActivityVisible()) {
//                        JsonFragment.this.showNetworkDialog("", "");
                    }
                }
            }) {
                protected Map<String, String> getParams() {
                    return (Map<String, String>) JsonFragment.this;
//                    .checkParams(JsonFragment.this.params);

                }
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1.0f));
            AppSingleton.getInstance(getContext()).addToRequestQueue(strReq);
            return;
        }
        if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
            fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
        }
        if (this.noInternetDialog != null) {
            if (!this.noInternetDialog.isShowing()) {
                this.noInternetDialog.showDialog();
            }
        } else if (!MyApplication.isActivityVisible()) {
//            showNetworkDialog("", "");
        }
    }
}

//    public void getSpinnerData(Context context, View view, String url, Spinner _spinner, final VolleyCallback callback) {
//        ArrayList<CodeValue> _CodeValues = new ArrayList();
//        if (ProfileInfo.getInstance().hasCodes(url)) {
////            SpinnerrAdapter adapter = new SpinnerrAdapter(context, 17367049, ProfileInfo.getInstance().getCodeValues(url));
////            _spinner.setAdapter(adapter);
////            SpinnerHelper spinnerHelper = new SpinnerHelper(_spinner, adapter, new com.apps.smartschoolmanagement.utils.JsonClass.VolleyCallback() {
////                public void onSuccess(String result) {
////                    callback.onSuccess(result);
////                }
////            });
////            return;
////        }
//        final Context context2 = context;
//        final Spinner spinner = _spinner;
//        final String str = url;
//        final VolleyCallback volleyCallback = callback;
////        getJsonResponse(url, view, new VolleyCallbackJSONObject() {
////
////            /* renamed from: com.apps.smartschoolmanagement.utils.JsonFragment$5$1 */
////            class C14291 implements VolleyCallback {
////                C14291() {
////                }
////
////                public void onSuccess(String result) {
////                    volleyCallback.onSuccess(result);
////                }
////            }
////
//////            public void onSuccess(JSONObject result) {
//////                JsonFragment.this.processJSONResult(context2, spinner, result, str, new C14291());
//////            }
////        });
//
//
////    public void getSpinnerData(Context context, View view, String url, Spinner _spinner, String paramkeyvalue, VolleyCallback callback) {
////        this.params.clear();
////        this.params.put(paramkeyvalue.split(",")[0], paramkeyvalue.split(",")[1]);
////        final Context context2 = context;
////        final Spinner spinner = _spinner;
////        final String str = url;
////        final VolleyCallback volleyCallback = callback;
////        getJsonResponse(url, view, new VolleyCallback() {
////
////            /* renamed from: com.apps.smartschoolmanagement.utils.JsonFragment$6$1 */
////            class C14311 implements VolleyCallback {
////                C14311() {
////                }
////
////                public void onSuccess(String result) {
////                    volleyCallback.onSuccess(result);
////                }
////            }
////
////            public void onSuccess(String result) {
////                try {
////                    JsonFragment.this.processJSONResult(context2, spinner, new JSONObject(result), str, new C14311());
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////            }
////        });
////    }
//
////    public void processJSONResult(Context context, final Spinner _spinner, JSONObject jsonObject, String url, VolleyCallback callback) {
////        ArrayList spinnerValues = new ArrayList();
////        final CodeValue[] mCodeValue = new CodeValue[]{new CodeValue()};
////        try {
////            CodeValue c = new CodeValue();
////            c.setCodeValue(jsonObject.getString("title"));
////            spinnerValues.add(c);
////            JSONArray jsonArray = jsonObject.getJSONArray("ResponseList");
////            for (int i = 0; i < jsonArray.length(); i++) {
////                String id = jsonArray.getJSONObject(i).optString("id").toString();
////                String value = jsonArray.getJSONObject(i).optString(Utils.imageName).toString();
////                CodeValue code = new CodeValue();
////                code.setCodeID(id);
////                code.setCodeValue(value);
////                spinnerValues.add(code);
////            }
////            if (jsonArray.length() > 0) {
////                ProfileInfo.getInstance().addCodes(url, spinnerValues);
////            }
//////            _spinner.setAdapter(new SpinnerrAdapter(context, 17367049, spinnerValues));
////            final VolleyCallback volleyCallback = callback;
////            _spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
////                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
////                    if (position > 0) {
////                        mCodeValue[0] = (CodeValue) _spinner.getItemAtPosition(position);
////                        volleyCallback.onSuccess(mCodeValue[0].getCodeID());
////                    }
////                }
////
////                public void onNothingSelected(AdapterView<?> adapterView) {
////                }
////            });
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////    }
//
////    private Map<String, String> checkParams(Map<String, String> map) {
////        for (Entry<String, String> pairs : map.entrySet()) {
////            if (pairs.getValue() == null) {
////                map.put(pairs.getKey(), "");
////            }
////        }
////        return map;
////    }
//
////    public void getJsonResponse(String url, final View fragmetn_view, final VolleyCallbackJSONObject callback) {
////        if (Connectivity.isConnected(getActivity())) {
////            if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
////                fragmetn_view.findViewById(R.id.layout_loading).setVisibility(0);
////            }
////            Log.d("jkl", "URL : " + url);
////            JsonObjectRequest jsObjRequest = new JsonObjectRequest(0, url, null, new Listener<JSONObject>() {
////                public void onResponse(JSONObject response) {
////                    Log.d("jkl", "Response: " + response.toString());
////                    if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
////                        fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
////                    }
////                    if (response == null || response.length() == 0) {
////                        Toast.makeText(JsonFragment.this.getContext(), "Failed to Retrieve Data", 1).show();
////                    } else {
////                        callback.onSuccess(response);
////                    }
////                }
////            }, new ErrorListener() {
////                public void onErrorResponse(VolleyError error) {
////                    Log.e("jkl", "Error: " + error.getMessage());
////                    if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
////                        fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
////                    }
////                    if (Connectivity.isConnected(JsonFragment.this.getActivity())) {
////                        if (error == null || error.networkResponse == null) {
//////                            JsonFragment.this.showToast("Unidentified Server Response");
////                            return;
////                        }
////                        int statusCode = error.networkResponse.statusCode;
////                        Log.d("jkl", "Error Code: " + statusCode);
////                        if (statusCode == 404) {
//////                            JsonFragment.this.showToast("URL Not Found");
////                        }
////                        try {
////                            Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
////                        } catch (UnsupportedEncodingException e) {
////                        }
////                    } else if (JsonFragment.this.noInternetDialog != null) {
////                        if (!JsonFragment.this.noInternetDialog.isShowing()) {
////                            JsonFragment.this.noInternetDialog.showDialog();
////                        }
////                    } else if (!MyApplication.isActivityVisible()) {
//////                        JsonFragment.this.showNetworkDialog("", "");
////                    }
////                }
////            });
////            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 1.0f));
////            AppSingleton.getInstance(getContext()).addToRequestQueue(jsObjRequest);
////            return;
////        }
////        if (!(fragmetn_view == null || fragmetn_view.findViewById(R.id.layout_loading) == null)) {
////            fragmetn_view.findViewById(R.id.layout_loading).setVisibility(8);
////        }
////        if (this.noInternetDialog != null) {
////            if (!this.noInternetDialog.isShowing()) {
////                this.noInternetDialog.showDialog();
////            }
////        } else if (!MyApplication.isActivityVisible()) {
//////            showNetworkDialog("", "");
////        }
////    }
//
////    private void showNetworkDialog(String message, String action) {
////        Intent intent = new Intent(getContext(), NetworkDialogActivity.class);
////        intent.putExtra("message", "No Internet Connection or Slow Network");
////        intent.putExtra("action", "Switch Network");
////        startActivity(new Intent(getContext(), NetworkDialogActivity.class));
////        AnimationSlideUtil.activityZoom(getContext());
////    }
//
////    public void downloadFile(String url, String name) {
////        final Handler handler = new Handler();
////        final ProgressDialog pDialog = new ProgressDialog(getActivity());
////        pDialog.setTitle("Downloading...");
////        pDialog.setMax(100);
////        pDialog.setCancelable(true);
////        pDialog.setProgressStyle(1);
////        pDialog.show();
////        this.cancelled = false;
////        final String str = url;
////        final String str2 = name;
////        this.f64t = new Thread(new Runnable() {
////
////            /* renamed from: com.apps.smartschoolmanagement.utils.JsonFragment$10$1 */
////            class C14221 implements Runnable {
////                C14221() {
////                }
////
////                public void run() {
////                    JsonFragment.this.downloadedSize = 0;
////                    if (JsonFragment.this.totalSize > 1024) {
////                        JsonFragment.this.totalSize /= 1024;
////                    }
////                    pDialog.setMax(JsonFragment.this.totalSize);
////                    pDialog.show();
////                }
////            }
////
////            public void run() {
////                try {
////                    File folder = new File(Environment.getExternalStorageDirectory() + "/hospital/");
////                    boolean success = true;
////                    if (!folder.exists()) {
////                        success = folder.mkdir();
////                    }
////                    if (success) {
////                        HttpURLConnection urlConnection = (HttpURLConnection) new URL(str).openConnection();
////                        urlConnection.setRequestMethod("GET");
////                        urlConnection.setDoOutput(true);
////                        urlConnection.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, "identity");
////                        urlConnection.connect();
////                        final FileOutputStream fileOutput = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/hospital/" + str2));
////                        final InputStream inputStream = urlConnection.getInputStream();
////                        JsonFragment.this.totalSize = urlConnection.getContentLength();
////                        handler.post(new C14221());
////                        pDialog.setOnCancelListener(new OnCancelListener() {
////
////                            /* renamed from: com.apps.smartschoolmanagement.utils.JsonFragment$10$2$1 */
////                            class C14231 implements Runnable {
////                                C14231() {
////                                }
////
////                                public void run() {
////                                    try {
////                                        fileOutput.close();
////                                        inputStream.close();
////                                        Thread.currentThread().interrupt();
////                                    } catch (IOException e) {
////                                        e.printStackTrace();
////                                    }
////                                }
////                            }
////
////                            public void onCancel(DialogInterface dialog) {
////                                try {
////                                    new File(Environment.getExternalStorageDirectory() + "/hospital/" + str2).delete();
////                                    JsonFragment.this.cancelled = true;
////                                    Thread thread = new Thread(new C14231());
////                                } catch (NullPointerException e) {
////                                }
////                            }
////                        });
////                        byte[] buffer = new byte[1024];
////                        while (true) {
////                            int bufferLength = inputStream.read(buffer);
////                            if (bufferLength <= 0) {
////                                break;
////                            }
////                            fileOutput.write(buffer, 0, bufferLength);
////                            JsonFragment jsonFragment = JsonFragment.this;
////                            jsonFragment.downloadedSize += bufferLength;
////                            float per = (((float) JsonFragment.this.downloadedSize) / ((float) JsonFragment.this.totalSize)) * 100.0f;
////                            pDialog.setProgress(JsonFragment.this.downloadedSize / 1024);
////                        }
////                        pDialog.dismiss();
////                        if (!JsonFragment.this.cancelled) {
////                            fileOutput.close();
////                            inputStream.close();
////                            JsonFragment.this.showFile(str2);
////                        }
////                    }
////                } catch (MalformedURLException e) {
////                    pDialog.dismiss();
////                    JsonFragment.this.showToast("Error : MalformedURLException " + e);
////                    e.printStackTrace();
////                } catch (IOException e2) {
////                    pDialog.dismiss();
////                    JsonFragment.this.showToast("Error : IOException " + e2);
////                    e2.printStackTrace();
////                } catch (Exception e3) {
////                    pDialog.dismiss();
////                    JsonFragment.this.showToast("Error : Please check your internet connection " + e3);
////                }
////            }
////        });
////        this.f64t.start();
////    }
//
////    public void showToast(final String err) {
////        new Handler(Looper.getMainLooper()).post(new Runnable() {
////            public void run() {
////                Toast.makeText(JsonFragment.this.getActivity(), err, 0).show();
////            }
////        });
////    }
//
////    public void showFile(String filename) {
////        File file = new File(Environment.getExternalStorageDirectory() + "/hospital/" + filename);
////        Intent intent = new Intent();
////        intent.setAction("android.intent.action.VIEW");
////        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.getName().substring(file.getName().lastIndexOf(".") + 1));
////        if (VERSION.SDK_INT >= 24) {
////            intent.setFlags(1);
////            intent.setDataAndType(FileProvider.getUriForFile(getActivity(), "in.findlogics.zoomin.FileProvider", file), type);
////        } else {
////            intent.setDataAndType(Uri.fromFile(file), type);
////        }
////        startActivity(intent);
////    }
//
