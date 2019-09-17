package com.apps.smartschoolmanagement.adapters;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import androidx.cardview.widget.CardView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apps.smartschoolmanagement.permissions.PermissionHandler;
import com.apps.smartschoolmanagement.permissions.Permissions;
import com.apps.smartschoolmanagement.utils.InputStreamVolleyRequest;
import com.bumptech.glide.load.Key;
import com.google.common.net.HttpHeaders;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.BusTrackingActivity;
import com.apps.smartschoolmanagement.activities.NetworkDialogActivity;
import com.apps.smartschoolmanagement.activities.ProfileViewActivity;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.AnimationSlideUtil;
import com.apps.smartschoolmanagement.utils.AppSingleton;
import com.apps.smartschoolmanagement.utils.Connectivity;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ListViewAdapter extends BaseAdapter {
    int check = 0;
    ProgressDialog dialog = null;
    private ArrayList<ListData> ids = null;
    private Context mContext = null;
    private int resId;
    ListData Datas;

    /* renamed from: com.apps.smartschoolmanagement.adapters.ListViewAdapter$7 */
    class C13247 implements com.apps.smartschoolmanagement.utils.JsonClass.VolleyCallback {
        C13247() {
        }

        public void onSuccess(String result) {
        }
    }

    private static class ViewHolder {
        TextView appointment_class;
        TextView appointment_datetime;
        TextView appointment_name;
        ImageView appointment_profilepic;
        TextView appointment_regarding;
        TextView appointment_roll;
        TextView assignment_assignment;
        Spinner assignment_status;
        TextView assignment_subject;
        TextView assignment_submitdate;
        TextView bus_driver_name;
        TextView bus_driver_phone;
        ImageButton bus_location;
        TextView bus_number;
        TextView exam_date;
        TextView exam_title;
        TextView leave_approve;
        TextView leave_date;
        TextView leave_reason;
        TextView leave_reject;
        TextView leave_studentclass;
        TextView leave_studentname;
        TextView library_book_name;
        TextView library_date_taken;
        ImageView library_status_icon;
        TextView library_status_text;
        TextView library_student_name;
        TextView library_submit_date;
        TextView notify_date;
        TextView notify_message;
        TextView notify_time;
        TextView notify_title;
        LinearLayout reamrks_date_layout;
        TextView remarks_date;
        TextView remarks_message;
        TextView remarks_month;
        TextView remarks_teacher;
        TextView remarks_year;
        TextView staff_class;
        TextView staff_name;
        ImageView staff_photo;
        TextView staff_subject;
        CardView staffprofile_layout;
        TextView student_class;
        TextView student_name;
        ImageView student_photo;
//        TextView student_roll;
        CardView studentprofile_layout,cvExamSchedule;

        private ViewHolder() {
        }
    }

    public interface VolleyCallback {
        void onSuccess(String str);
    }

    public ListViewAdapter(Context c, int resource, ArrayList<ListData> values) {
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
        String[] splitter;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(this.resId, viewGroup, false);
            viewHolder.notify_date = (TextView) convertView.findViewById(R.id.notification_date);
            viewHolder.notify_time = (TextView) convertView.findViewById(R.id.notification_time);
            viewHolder.notify_message = (TextView) convertView.findViewById(R.id.notification_message);
            viewHolder.notify_title = (TextView) convertView.findViewById(R.id.notification_title);
            viewHolder.remarks_message = (TextView) convertView.findViewById(R.id.remark_message);
            viewHolder.remarks_date = (TextView) convertView.findViewById(R.id.reamrk_date);
            viewHolder.remarks_month = (TextView) convertView.findViewById(R.id.reamrk_month);
            viewHolder.remarks_year = (TextView) convertView.findViewById(R.id.reamrk_year);
            viewHolder.remarks_teacher = (TextView) convertView.findViewById(R.id.remark_teacher);
            viewHolder.reamrks_date_layout = (LinearLayout) convertView.findViewById(R.id.remark_type);
            viewHolder.exam_date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.exam_title = (TextView) convertView.findViewById(R.id.exam_title);
            viewHolder.assignment_assignment = (TextView) convertView.findViewById(R.id.assignment);
            viewHolder.assignment_subject = (TextView) convertView.findViewById(R.id.subject_name);
            viewHolder.assignment_submitdate = (TextView) convertView.findViewById(R.id.submission_date);
          //  viewHolder.assignment_status = (Spinner) convertView.findViewById(R.id.spnr_status);
            viewHolder.leave_approve = (TextView) convertView.findViewById(R.id.approve_edit);
            viewHolder.leave_reject = (TextView) convertView.findViewById(R.id.reject_cancel);
            viewHolder.leave_date = (TextView) convertView.findViewById(R.id.leave_date);
            viewHolder.bus_driver_name = (TextView) convertView.findViewById(R.id.busdriver);
            viewHolder.bus_driver_phone = (TextView) convertView.findViewById(R.id.busdriverphone);
            viewHolder.bus_number = (TextView) convertView.findViewById(R.id.busnumber);
            viewHolder.bus_location = (ImageButton) convertView.findViewById(R.id.location);
            viewHolder.library_book_name = (TextView) convertView.findViewById(R.id.book_name);
            viewHolder.library_student_name = (TextView) convertView.findViewById(R.id.student_name);
            viewHolder.library_date_taken = (TextView) convertView.findViewById(R.id.date_taken);
            viewHolder.library_submit_date = (TextView) convertView.findViewById(R.id.submit_date);
            viewHolder.library_status_text = (TextView) convertView.findViewById(R.id.status_text);
            viewHolder.library_status_icon = (ImageView) convertView.findViewById(R.id.status_icon);
            viewHolder.staff_class = (TextView) convertView.findViewById(R.id.staff_class);
            viewHolder.staff_name = (TextView) convertView.findViewById(R.id.staff_name);
            viewHolder.staff_subject = (TextView) convertView.findViewById(R.id.staff_subject);
            viewHolder.staff_photo = (ImageView) convertView.findViewById(R.id.staff_photo);
            viewHolder.staffprofile_layout = (CardView) convertView.findViewById(R.id.layout_staffprofile);
            viewHolder.studentprofile_layout = (CardView) convertView.findViewById(R.id.layout_studentprofile);
            viewHolder.cvExamSchedule = convertView.findViewById(R.id.cvExamSchedule);
            viewHolder.student_name = (TextView) convertView.findViewById(R.id.student_name);
            viewHolder.student_class = (TextView) convertView.findViewById(R.id.student_class);
//            viewHolder.student_roll = (TextView) convertView.findViewById(R.id.student_roll);
            viewHolder.student_photo = (ImageView) convertView.findViewById(R.id.student_photo);
            viewHolder.appointment_profilepic = (ImageView) convertView.findViewById(R.id.profilePic);
            viewHolder.appointment_roll = (TextView) convertView.findViewById(R.id.roll);
            viewHolder.appointment_name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.appointment_class = (TextView) convertView.findViewById(R.id.className);
            viewHolder.appointment_regarding = (TextView) convertView.findViewById(R.id.reason);
            viewHolder.appointment_datetime = (TextView) convertView.findViewById(R.id.appointment_date);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        final ListData data = (ListData) getItem(i);
        if (viewHolder.notify_date != null) {
            viewHolder.notify_date.setText(data.getNotify_date());
        }
        if (viewHolder.notify_time != null) {
            viewHolder.notify_time.setText(data.getNotify_time());
        }
        if (viewHolder.notify_message != null) {
            viewHolder.notify_message.setText(data.getNotify_message());
        }
        if (viewHolder.notify_title != null) {
            viewHolder.notify_title.setText(data.getNotify_title());
        }
        if (viewHolder.remarks_message != null) {
            viewHolder.remarks_message.setText(data.getRemarks());
        }
        if (viewHolder.remarks_teacher != null) {
            viewHolder.remarks_teacher.setText(data.getRemarks_teacher());
        }
        if (!(viewHolder.reamrks_date_layout == null || data.getRemarks_type() == null)) {
            if ("positive".equals(data.getRemarks_type())) {
                viewHolder.reamrks_date_layout.setBackgroundColor(this.mContext.getResources().getColor(17170451));
            } else {
                viewHolder.reamrks_date_layout.setBackgroundColor(this.mContext.getResources().getColor(17170457));
            }
        }
        if (data.getRemarks_date() != null) {
            splitter = data.getRemarks_date().split("-");
            if (viewHolder.remarks_date != null) {
                viewHolder.remarks_date.setText(splitter[2].split(" ")[0]);
            }
            if (viewHolder.remarks_month != null) {
                viewHolder.remarks_month.setText(getMonth(Integer.parseInt(splitter[1])));
            }
            if (viewHolder.remarks_year != null) {
                viewHolder.remarks_year.setText(splitter[0]);
            }
        }
        if (viewHolder.exam_title != null) {
            viewHolder.exam_title.setText("Subject : " + data.getExam_title());
        }
        if (viewHolder.exam_date != null) {
            viewHolder.exam_date.setText(data.getExam_date());
        }
        if (viewHolder.assignment_assignment != null) {
            viewHolder.assignment_assignment.setText(data.getAssignment_assignment());
        }
        if (viewHolder.assignment_subject != null) {
            viewHolder.assignment_subject.setText(data.getAssignment_subject());
        }
        if (viewHolder.assignment_submitdate != null) {
            viewHolder.assignment_submitdate.setText(data.getAssignment_submit_date());
        }
        /*if (viewHolder.assignment_status != null) {
            viewHolder.assignment_status.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    HashMap<String, String> params = new HashMap();
                    String Url = null;
                    if (i > 0) {
                        ListViewAdapter listViewAdapter = ListViewAdapter.this;
                        int i2 = listViewAdapter.check + 1;
                        listViewAdapter.check = i2;
                        if (i2 > 1) {
                            if (i == 1) {
                                Url = URLs.assignments_status_pending;
                            } else if (i == 2) {
                                Url = URLs.assignments_status_completed;
                            }
                            params.put("assignment_id", data.getAssignment_id());
                            params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
                            ListViewAdapter.this.updateStatus(Url, params);
                        }
                    }
                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            viewHolder.assignment_status.setSelection(Integer.parseInt(data.getStatus()) + 1);
        }*/
        if (viewHolder.leave_studentname != null) {
            if (data.getLeave_student() != null) {
                viewHolder.leave_studentname.setText(data.getLeave_student());
            } else {
                viewHolder.leave_studentname.setText(data.getLeave_teacher());
            }
        }
        if (viewHolder.leave_studentclass != null) {
            if (UserStaticData.user_type == 0) {
                viewHolder.leave_studentclass.setText(data.getLeave_studentclass());
            } else {
                result.findViewById(R.id.layout_class).setVisibility(8);
            }
        }
        if (viewHolder.leave_reason != null) {
            viewHolder.leave_reason.setText(data.getLeave_reason());
        }
        if (data.getLeave_date() != null) {
            splitter = data.getLeave_date().split("/");
            if (viewHolder.leave_date != null && splitter.length > 1) {
                viewHolder.leave_date.setText(splitter[0] + " " + getMonth(Integer.parseInt(splitter[1])) + " " + splitter[2]);
            }
        }
        if (viewHolder.leave_approve != null) {
            viewHolder.leave_approve.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    HashMap<String, String> params = new HashMap();
                    params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
                    if (data.getLeave_studentid() != null) {
                        params.put("student_id", data.getLeave_studentid());
                    }
                    params.put("id", data.getLeave_id());
                    params.put(NotificationCompat.CATEGORY_STATUS, "1");
                    ListViewAdapter.this.updateStatus(URLs.acceptleave, params);
                }
            });
        }
        if (viewHolder.leave_reject != null) {
            viewHolder.leave_reject.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    HashMap<String, String> params = new HashMap();
                    params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
                    if (data.getLeave_studentid() != null) {
                        params.put("student_id", data.getLeave_studentid());
                    }
                    params.put("id", data.getLeave_id());
                    params.put(NotificationCompat.CATEGORY_STATUS, "0");
                    ListViewAdapter.this.updateStatus(URLs.rejectleave, params);
                }
            });
        }
        if (viewHolder.bus_driver_name != null) {
            viewHolder.bus_driver_name.setText(data.getBus_driver_name());
        }
        if (viewHolder.bus_driver_phone != null) {
            viewHolder.bus_driver_phone.setText(data.getBus_phone_number());
        }
        if (viewHolder.bus_number != null) {
            viewHolder.bus_number.setText(data.getBus_transport_id());
        }
        if (viewHolder.bus_location != null) {
            viewHolder.bus_location.setOnClickListener(new OnClickListener() {

                /* renamed from: com.apps.smartschoolmanagement.adapters.ListViewAdapter$4$1 */
                class C13201 extends PermissionHandler {
                    C13201() {
                    }

                    public void onGranted() {
                        Intent intent = new Intent(ListViewAdapter.this.mContext, BusTrackingActivity.class);
                        intent.putExtra("lat", data.getBus_latitude());
                        intent.putExtra("long", data.getBus_logitude());
                        ListViewAdapter.this.mContext.startActivity(intent);
                    }

                    public void onDenied(Context context, ArrayList<String> arrayList) {
                        Toast.makeText(ListViewAdapter.this.mContext, "You have to allow Location Permissions to use this service", 0).show();
                    }
                }

                public void onClick(View view) {
                    Permissions.check(ListViewAdapter.this.mContext, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, "Location Permission is required to use this service", new Permissions.Options().setSettingsDialogTitle("Warning!").setRationaleDialogTitle(HttpHeaders.WARNING), new C13201());
                }
            });
        }
        if (viewHolder.library_book_name != null) {
            viewHolder.library_book_name.setText(data.getLibrary_bookName());
        }
        if (viewHolder.library_student_name != null) {
            viewHolder.library_student_name.setText(data.getLibrary_studentName());
        }
        if (viewHolder.library_submit_date != null) {
            viewHolder.library_submit_date.setText(data.getLibrary_dateofsubmit());
        }
        if (viewHolder.library_date_taken != null) {
            viewHolder.library_date_taken.setText(data.getLibrary_dateoftaken());
        }
        if (viewHolder.library_status_text != null) {
            if (data.getLibrary_status_text().equals("0")) {
                viewHolder.library_status_icon.setImageResource(R.drawable.img_book);
                viewHolder.library_status_text.setText("Not Submitted");
            } else {
                viewHolder.library_status_text.setText("Submitted");
                viewHolder.library_status_icon.setImageResource(R.drawable.img_book_green);
            }
        }
        if (viewHolder.staff_photo != null) {
//            Glide.with(this.mContext).load(Uri.parse(data.getStaff_photo())).override(200, 200).placeholder(R.drawable.ic_user).diskCacheStrategy(DiskCacheStrategy.RESULT).centerCrop().into(viewHolder.staff_photo);
        }
        if (viewHolder.staff_name != null) {
            viewHolder.staff_name.setText(data.getStaff_name());
        }
        if (viewHolder.staff_subject != null) {
            viewHolder.staff_subject.setText(data.getStaff_phone());
        }
        if (viewHolder.staff_class != null) {
            viewHolder.staff_class.setText(data.getStaff_class());
        }


//        if (viewHolder.staffprofile_layout != null) {
//            viewHolder.staffprofile_layout.setOnClickListener(new OnClickListener() {
//                public void onClick(View v) {
//                    Intent intent = new Intent(ListViewAdapter.this.mContext, ProfileViewActivity.class);
//                    intent.putExtra("teacherid", data.getStaff_id());
//                    ListViewAdapter.this.mContext.startActivity(intent);
//                }
//            });
//        }


        if (viewHolder.student_photo != null) {
//            Glide.with(this.mContext).load(Uri.parse(data.getStudent_photo())).override(200, 200).diskCacheStrategy(DiskCacheStrategy.RESULT).centerCrop().into(viewHolder.student_photo);
        }
        if (viewHolder.student_name != null) {
            viewHolder.student_name.setText(data.getStudent_name());
        }
        if (viewHolder.student_class != null) {
            viewHolder.student_class.setText(data.getStudent_class());
        }
//        if (viewHolder.student_roll != null) {
//            viewHolder.student_roll.setText(data.getStudent_roll());
//        }x
        if (viewHolder.studentprofile_layout != null) {
            viewHolder.studentprofile_layout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(ListViewAdapter.this.mContext, ProfileViewActivity.class);
                    intent.putExtra("studentid", data.getStudent_id());
                    intent.putExtra("divid", data.getDivid());
                    intent.putExtra("rollno", data.getStudent_roll());
                    intent.putExtra("schoolid", data.getSchool());
                    Log.e("id","send"+ data.getStudent_id());
                    ListViewAdapter.this.mContext.startActivity(intent);
                }
            });
        }

        if (viewHolder.cvExamSchedule != null) {
            viewHolder.cvExamSchedule.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Permissions.check(mContext, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, "Storage Permission is required to use this service", new Permissions.Options().setSettingsDialogTitle("Warning!").setRationaleDialogTitle(HttpHeaders.WARNING), new PermissionHandler() {
                        @Override
                        public void onGranted() {
                            String url = URLs.getExamSchedulepdf + data.getExamScheduleID();
                            Log.e("urufhef",url);
                            openWebPage(url,data.getExam_title());
                        }
                        public void onDenied(Context context, ArrayList<String> arrayList) {
                            Toast.makeText(mContext, "You have to allow Storage Permissions to use this service", 0).show();
                        }
                    });
                }
            });
        }
        if (viewHolder.appointment_name != null) {
            viewHolder.appointment_name.setText(data.getName());
        }
        if (viewHolder.appointment_class != null) {
            viewHolder.appointment_class.setText(data.getClassname());
        }
        if (viewHolder.appointment_roll != null) {
            if (data.getRoll() != null) {
                viewHolder.appointment_roll.setText(data.getRoll());
            } else {
                result.findViewById(R.id.layout_roll).setVisibility(8);
            }
        }
        if (viewHolder.appointment_regarding != null) {
            viewHolder.appointment_regarding.setText(data.getRegarding());
        }
        if (viewHolder.appointment_datetime != null) {
            viewHolder.appointment_datetime.setText(data.getDate() + " " + data.getTime());
        }
        if (!(viewHolder.appointment_profilepic == null || data.getPhoto() == null)) {
//            Glide.with(this.mContext).load(Uri.parse(data.getPhoto())).override(200, 200).placeholder(R.drawable.ic_user).diskCacheStrategy(DiskCacheStrategy.RESULT).centerCrop().into(viewHolder.appointment_profilepic);
        }
        return result;
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getShortMonths()[month - 1];
    }

    public void updateStatus(String url, HashMap<String, String> params) {
        getJsonResponse(url, params, new C13247());
    }

    public void getJsonResponse(String url, HashMap<String, String> params, final com.apps.smartschoolmanagement.utils.JsonClass.VolleyCallback callback) {
        if (Connectivity.isConnected(this.mContext)) {
            final KProgressHUD progressHUD = KProgressHUD.create(this.mContext);
            progressHUD.show();
            final HashMap<String, String> hashMap = params;
            StringRequest strReq = new StringRequest(1, url, new Listener<String>() {
                public void onResponse(String response) {
                    Log.d("jkl", "Response: " + response.toString());
                    progressHUD.dismiss();
                    callback.onSuccess(response);
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.e("jkl", "Error: " + error.getMessage());
                    progressHUD.dismiss();
                    if (!Connectivity.isConnected(ListViewAdapter.this.mContext)) {
                        ListViewAdapter.this.showNetworkDialog("", "");
                    } else if (error != null && error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        Log.d("jkl", "Error Code: " + statusCode);
                        if (statusCode == 404) {
                            ListViewAdapter.this.showToast("URL Not Found");
                        }
                        try {
                            Log.d("jkl", "Error: " + new String(error.networkResponse.data, Key.STRING_CHARSET_NAME));
                        } catch (UnsupportedEncodingException e) {
                        }
                    } else if (error == null || error.getMessage() == null) {
                        ListViewAdapter.this.showToast("Unidentified Server Response");
                    } else {
                        ListViewAdapter.this.showNetworkDialog("Your network speed too slow. Please switch your network", "Switch");
                    }
                }
            }) {
                protected Map<String, String> getParams() {
                    return hashMap;
                }
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1.0f));
            AppSingleton.getInstance(this.mContext).addToRequestQueue(strReq);
            return;
        }
        showNetworkDialog("", "");
    }

    public void showNetworkDialog(String message, String action) {
        Intent intent = new Intent(this.mContext, NetworkDialogActivity.class);
        intent.putExtra("message", message);
        intent.putExtra("action", action);
        this.mContext.startActivity(new Intent(this.mContext, NetworkDialogActivity.class));
        AnimationSlideUtil.activityZoom(this.mContext);
    }

    public void showToast(final String msg) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(ListViewAdapter.this.mContext, msg, 0);
                toast.setView(toast.getView());
                toast.show();
            }
        });
    }

    public void openWebPage(String url,String title) {
//    if (findViewById(R.id.layout_loading) != null) {
//        findViewById(R.id.layout_loading).setVisibility(0);
//    }
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, url,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        // TODO handle the response

                        File root = new File(Environment.getExternalStorageDirectory(), "ExamSchedule");
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        String uniqueString = UUID.randomUUID().toString();
                        File file = new File(root,title);
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
//                    if (ScheduleActivity.this.findViewById(R.id.layout_loading) != null) {
//                        ScheduleActivity.this.findViewById(R.id.layout_loading).setVisibility(8);
//                    }
                        // Here you declare your pdf path
                        Intent pdfViewIntent = new Intent(Intent.ACTION_VIEW);
                        pdfViewIntent.setDataAndType(Uri.fromFile(file),"application/pdf");
                        pdfViewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        Intent intent = Intent.createChooser(pdfViewIntent, "Open File");
                        try {
                            mContext.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(mContext, "PDf Cannot Open", Toast.LENGTH_SHORT).show();
                        }
                    }
                } ,new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//            if (ScheduleActivity.this.findViewById(R.id.layout_loading) != null) {
//                ScheduleActivity.this.findViewById(R.id.layout_loading).setVisibility(8);
//            }
                Toast.makeText(mContext, "Cannot Getting Time Table.", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }, null);
        RequestQueue mRequestQueue = Volley.newRequestQueue(mContext, new HurlStack());
        mRequestQueue.add(request);
    }
//
//    public void openWebPage(String url) {
//
//        Uri webpage = Uri.parse(url);
//
//        if (!url.startsWith("http://") && !url.startsWith("https://")) {
//            webpage = Uri.parse("http://" +"Quickedu.co.in/timeTable/" +  url);
//        }
//
//        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
//        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
//            mContext.startActivity(intent);
////            finish();
//        }
//    }
//
    public void showProgress() {
        this.dialog = new ProgressDialog(this.mContext, R.style.MyGravity);
        this.dialog.setProgressStyle(0);
        this.dialog.getWindow().setFlags(8, 8);
        this.dialog.getWindow().setBackgroundDrawableResource(17170445);
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    public void hideProgress() {
        if (this.dialog.isShowing() && this.dialog != null) {
            this.dialog.dismiss();
        }
    }
}
