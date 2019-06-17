package com.apps.smartschoolmanagement.adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import androidx.core.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.smartschoolmanagement.permissions.PermissionHandler;
import com.apps.smartschoolmanagement.permissions.Permissions;
import com.google.common.net.HttpHeaders;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.OnlineMaterial;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MaterialAdapter extends BaseAdapter {
    boolean cancelled = false;
    TextView cur_val;
    Dialog dialog;
    int downloadedSize = 0;
    final Handler handler = new Handler();
    private ArrayList<OnlineMaterial> ids = null;
    private Context mContext = null;
    ProgressBar pb;
    private int resId;
    /* renamed from: t */
    Thread f63t;
    int totalSize = 0;

    private static class ViewHolder {
        TextView className;
        ImageButton download;
        TextView fileName;
        TextView subjectName;

        private ViewHolder() {
        }
    }

    public MaterialAdapter(Context c, int resource, ArrayList<OnlineMaterial> values) {
        this.mContext = c;
        this.ids = values;
        this.resId = resource;
    }

    public int getCount() {
        return this.ids.size();
    }

    public Object getItem(int i) {
        return this.ids.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(this.resId, viewGroup, false);
            viewHolder.subjectName = (TextView) convertView.findViewById(R.id.subject);
            viewHolder.fileName = (TextView) convertView.findViewById(R.id.attachment);
            viewHolder.download = (ImageButton) convertView.findViewById(R.id.download);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        final OnlineMaterial data = (OnlineMaterial) getItem(i);
        if (viewHolder.subjectName != null) {
            viewHolder.subjectName.setText(data.getSubjectName());
        }
        if (viewHolder.fileName != null) {
            viewHolder.fileName.setText(data.getFileName());
        }
        if (viewHolder.download != null) {
            viewHolder.download.setOnClickListener(new OnClickListener() {

                /* renamed from: com.apps.smartschoolmanagement.adapters.MaterialAdapter$1$1 */
                class C13281 extends PermissionHandler {
                    C13281() {
                    }

                    public void onGranted() {
                        MaterialAdapter.this.downloadFile(data.getFilepath() + data.getFileName(), data.getFileName());
                    }

                    public void onDenied(Context context, ArrayList<String> arrayList) {
                        Toast.makeText(MaterialAdapter.this.mContext, "You have to allow Storage Permissions to use this service", 0).show();
                    }
                }

                public void onClick(View view) {
                    Permissions.check(MaterialAdapter.this.mContext, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, "storage permissioN is required because...", new Permissions.Options().setSettingsDialogTitle("Warning!").setRationaleDialogTitle("Info"), new C13281());
                }
            });
        }
        return result;
    }

    public void downloadFile(String url, String name) {
        final Handler handler = new Handler();
        final ProgressDialog pDialog = new ProgressDialog(this.mContext);
        pDialog.setTitle("Downloading...");
        pDialog.setMax(100);
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(1);
        pDialog.show();
        this.cancelled = false;
        final String str = url;
        final String str2 = name;
        this.f63t = new Thread(new Runnable() {

            /* renamed from: com.apps.smartschoolmanagement.adapters.MaterialAdapter$2$1 */
            class C13301 implements Runnable {
                C13301() {
                }

                public void run() {
                    MaterialAdapter.this.downloadedSize = 0;
                    if (MaterialAdapter.this.totalSize > 1024) {
                        MaterialAdapter.this.totalSize /= 1024;
                    }
                    pDialog.setMax(MaterialAdapter.this.totalSize);
                    pDialog.show();
                }
            }

            public void run() {
                try {
                    File folder = new File(Environment.getExternalStorageDirectory() + "/School Downloads");
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
                        final FileOutputStream fileOutput = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/School Downloads/" + str2));
                        final InputStream inputStream = urlConnection.getInputStream();
                        MaterialAdapter.this.totalSize = urlConnection.getContentLength();
                        handler.post(new C13301());
                        pDialog.setOnCancelListener(new OnCancelListener() {

                            /* renamed from: com.apps.smartschoolmanagement.adapters.MaterialAdapter$2$2$1 */
                            class C13311 implements Runnable {
                                C13311() {
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
                                    new File(Environment.getExternalStorageDirectory() + "/School Downloads/" + str2).delete();
                                    MaterialAdapter.this.cancelled = true;
                                    Thread thread = new Thread(new C13311());
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
                            MaterialAdapter materialAdapter = MaterialAdapter.this;
                            materialAdapter.downloadedSize += bufferLength;
                            float per = (((float) MaterialAdapter.this.downloadedSize) / ((float) MaterialAdapter.this.totalSize)) * 100.0f;
                            pDialog.setProgress(MaterialAdapter.this.downloadedSize / 1024);
                        }
                        pDialog.dismiss();
                        if (!MaterialAdapter.this.cancelled) {
                            fileOutput.close();
                            inputStream.close();
                            MaterialAdapter.this.showPdf(str2);
                        }
                    }
                } catch (MalformedURLException e) {
                    MaterialAdapter.this.showError("Error : MalformedURLException " + e);
                    e.printStackTrace();
                } catch (IOException e2) {
                    MaterialAdapter.this.showError("Error : IOException " + e2);
                    e2.printStackTrace();
                } catch (Exception e3) {
                    MaterialAdapter.this.showError("Error : Please check your internet connection " + e3);
                }
            }
        });
        this.f63t.start();
    }

    public void showPdf(String filename) {
        File file = new File(Environment.getExternalStorageDirectory() + "/School Downloads/" + filename);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.getName().substring(file.getName().lastIndexOf(".") + 1));
        if (VERSION.SDK_INT >= 24) {
            intent.setFlags(1);
            intent.setDataAndType(FileProvider.getUriForFile(this.mContext, "com.apps.smartschoolmanagement.FileProvider", file), type);
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
        this.mContext.startActivity(intent);
    }

    void showError(final String err) {
        this.handler.post(new Runnable() {
            public void run() {
                Toast.makeText(MaterialAdapter.this.mContext, err, 1).show();
            }
        });
    }
}
