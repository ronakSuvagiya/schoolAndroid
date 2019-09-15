package com.apps.smartschoolmanagement.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.permissions.PermissionHandler;
import com.apps.smartschoolmanagement.permissions.Permissions;
import com.apps.smartschoolmanagement.utils.InputStreamVolleyRequest;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;
import com.google.android.gms.common.util.IOUtils;
import com.google.common.net.HttpHeaders;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends JsonClass {
    SharedPreferences sp;
    List<String> divName = new ArrayList<>();
    List<Integer> divId = new ArrayList<>();
    int divid;
    String pdf;
    String div;
    InputStream test;
    String url,channel,teacherId,stdid,DivId,usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        sp = PreferenceManager.getDefaultSharedPreferences(ScheduleActivity.this);
         channel = (sp.getString("schoolid", ""));
         teacherId = String.valueOf((sp.getInt("teachermaster", 0)));
         stdid = (sp.getString("stdId", ""));
         DivId = (sp.getString("DivId", ""));
         usertype = sp.getString("usertype","");

        Permissions.check(ScheduleActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, "Storage Permission is required to use this service", new Permissions.Options().setSettingsDialogTitle("Warning!").setRationaleDialogTitle(HttpHeaders.WARNING), new ScheduleActivity.C12551());
/*
        if (usertype != null) {
            if ("student".equals(usertype)) {
                url =  URLs.getTimeTable + DivId;
                openWebPage(url);
            } else {
                url = URLs.getTeacherTimeTable + teacherId;
                openWebPage(url);
            }
        }*/
        //        WebView pdf_url = findViewById(R.id.webview);
//        pdf_url.getSettings().setJavaScriptEnabled(true);
//        pdf_url.loadUrl("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf");
//        String extStorageDirectory = Environment.getExternalStorageDirectory()
//                .toString();

//        File folder = new File(extStorageDirectory, "pdf");
//        folder.mkdir();
//        File file = new File(folder, "Read.pdf");
//        try {
//            file.createNewFile();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        Downloader.DownloadFile("http://Quickedu.co.in/timeTable/121.pdf", file);
//
//        showPdf();

    }
//    public void showPdf()
//    {
//        File file = new File(Environment.getExternalStorageDirectory()+"/pdf/Read.pdf");
//        PackageManager packageManager = getPackageManager();
//        Intent testIntent = new Intent(Intent.ACTION_VIEW);
//        testIntent.setType("application/pdf");
//        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
////        Uri uri = Uri.fromFile(file);
////        Uri uri = FileProvider.getUriForFile(ScheduleActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
//        Uri uri = FileProvider.getUriForFile(ScheduleActivity.this, getApplicationContext().getPackageName() + ".FileProvider", file);
//
//        intent.setDataAndType(uri, "application/pdf");
//        startActivity(intent);
//    }

    class C12551 extends PermissionHandler {
        C12551() {
        }

        public void onGranted() {
            if (usertype != null) {
                if ("student".equals(usertype)) {
                    url = URLs.getTimeTable + DivId;
                    openWebPage(url);
                } else {
                    url = URLs.getTeacherTimeTable + teacherId;
                    openWebPage(url);
                }
            }        }

        public void onDenied(Context context, ArrayList<String> arrayList) {
            Toast.makeText(ScheduleActivity.this, "You have to allow Storage Permissions to use this service", 0).show();
        }
    }

    public void openWebPage(String url) {
        if (findViewById(R.id.layout_loading) != null) {
            findViewById(R.id.layout_loading).setVisibility(0);
        }
        InputStreamVolleyRequest request = new     InputStreamVolleyRequest(Request.Method.GET, url,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        // TODO handle the response


                        File root = new File(Environment.getExternalStorageDirectory(), "TimeTable");
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        File file = new File(root, "timeTable.pdf");
                        try {
                            file.createNewFile();
                            BufferedOutputStream salida = new BufferedOutputStream(new FileOutputStream(file));
                            salida.write(response);
                            salida.flush();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (ScheduleActivity.this.findViewById(R.id.layout_loading) != null) {
                            ScheduleActivity.this.findViewById(R.id.layout_loading).setVisibility(8);
                        }
                        // Here you declare your pdf path
                        Intent pdfViewIntent = new Intent(Intent.ACTION_VIEW);
                        pdfViewIntent.setDataAndType(Uri.fromFile(file),"application/pdf");
                        pdfViewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        Intent intent = Intent.createChooser(pdfViewIntent, "Open File");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(ScheduleActivity.this, "PDf Cannot Open", Toast.LENGTH_SHORT).show();
                        }
                    }
                } ,new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (ScheduleActivity.this.findViewById(R.id.layout_loading) != null) {
                    ScheduleActivity.this.findViewById(R.id.layout_loading).setVisibility(8);
                }
                Toast.makeText(ScheduleActivity.this, "Cannot Getting Time Table.", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }, null);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        mRequestQueue.add(request);
    }
}
