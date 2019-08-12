package com.apps.smartschoolmanagement.activities;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.apps.smartschoolmanagement.BuildConfig;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.Downloader;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends JsonClass {
    SharedPreferences sp;
    List<String> divName = new ArrayList<>();
    List<Integer> divId = new ArrayList<>();
    int divid;
    String pdf;
    String div;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        sp = PreferenceManager.getDefaultSharedPreferences(ScheduleActivity.this);
        String channel = (sp.getString("schoolid", ""));
        String teacherId = String.valueOf((sp.getInt("teachermaster", 0)));
        String stdid = (sp.getString("stdId", ""));
        String usertype = sp.getString("usertype","");
        if (usertype != null) {
            if ("student".equals(usertype)) {
                getJsonResponse(URLs.getDiv + stdid , ScheduleActivity.this, new ScheduleActivity.getDivApi());
            } else {
                getJsonResponse(URLs.getTeacherTimeTable + teacherId, ScheduleActivity.this, new ScheduleActivity.getTeacherApi());
            }
        }
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

    class getDivApi implements VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    ScheduleActivity.this.findViewById(R.id.layout_loading).setVisibility(0);
                    JSONObject obj = jsonArray.getJSONObject(i);
                    divName.add(obj.getString("name"));
                    divId.add(obj.getInt("id"));
                     div = String.valueOf(obj.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            getJsonResponse(URLs.getTimeTable + div, ScheduleActivity.this, new ScheduleActivity.getTimeTableApi());
            Log.e("divData", jsonArray.toString());
        }
    }

    class getTimeTableApi implements VolleyCallbackJSONObject {
        @Override
        public void onSuccess(JSONObject jSONObject) {
            try {
                ScheduleActivity.this.findViewById(R.id.layout_loading).setVisibility(0);
                pdf = jSONObject.getString("pdfName");
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
//                getApplicationContext().startActivity(browserIntent);
                openWebPage(pdf);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ScheduleActivity.this, "Somethings is wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
    class getTeacherApi implements VolleyCallbackJSONObject {
        @Override
        public void onSuccess(JSONObject jSONObject) {
            try {
                ScheduleActivity.this.findViewById(R.id.layout_loading).setVisibility(0);
                pdf = jSONObject.getString("pdfName");
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
//                getApplicationContext().startActivity(browserIntent);
                openWebPage(pdf);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ScheduleActivity.this, "Somethings is wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void openWebPage(String url) {

        Uri webpage = Uri.parse(url);

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            webpage = Uri.parse("http://" +"Quickedu.co.in/timeTable/" +  url);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            this.finish();
        }
    }
}
