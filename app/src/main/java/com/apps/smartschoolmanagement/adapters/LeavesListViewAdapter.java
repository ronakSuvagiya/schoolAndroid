package com.apps.smartschoolmanagement.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.fragments.AllLeaves;
import com.apps.smartschoolmanagement.fragments.Leave_Teacher_Approved;
import com.apps.smartschoolmanagement.fragments.Leave_Teacher_Pending;
import com.apps.smartschoolmanagement.fragments.Leave_Teacher_Rejected;
import com.apps.smartschoolmanagement.models.Leaves;
import com.apps.smartschoolmanagement.utils.AppSingleton;
import com.apps.smartschoolmanagement.utils.URLs;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LeavesListViewAdapter extends BaseAdapter {
    ProgressDialog dialog = null;
    Fragment fragment = null;
    private ArrayList<Leaves> ids = null;
    private Context mContext = null;
    private int resId;

    /* renamed from: com.apps.smartschoolmanagement.adapters.LeavesListViewAdapter$3 */
    class C13133 implements com.apps.smartschoolmanagement.utils.JsonClass.VolleyCallback {
        C13133() {
        }

        public void onSuccess(String result) {
            if (LeavesListViewAdapter.this.fragment instanceof Leave_Teacher_Pending) {
                ((FragmentActivity) LeavesListViewAdapter.this.mContext).getSupportFragmentManager().beginTransaction().replace(R.id.container, new Leave_Teacher_Pending()).commit();
            } else if (LeavesListViewAdapter.this.fragment instanceof Leave_Teacher_Approved) {
                ((FragmentActivity) LeavesListViewAdapter.this.mContext).getSupportFragmentManager().beginTransaction().replace(R.id.container, new Leave_Teacher_Approved()).commit();
            } else if (LeavesListViewAdapter.this.fragment instanceof Leave_Teacher_Rejected) {
                ((FragmentActivity) LeavesListViewAdapter.this.mContext).getSupportFragmentManager().beginTransaction().replace(R.id.container, new Leave_Teacher_Rejected()).commit();
            } else if (LeavesListViewAdapter.this.fragment instanceof AllLeaves) {
                ((FragmentActivity) LeavesListViewAdapter.this.mContext).getSupportFragmentManager().beginTransaction().replace(R.id.container, new AllLeaves()).commit();
            }
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.adapters.LeavesListViewAdapter$5 */
    class C13155 implements ErrorListener {
        C13155() {
        }

        public void onErrorResponse(VolleyError error) {
            Log.e("klj", "Login Error: " + error.getMessage());
            LeavesListViewAdapter.this.hideProgress();
        }
    }

    private static class ViewHolder {
        TextView approve_edit;
        TextView clas;
        TextView date;
        TextView name;
        TextView reason;
        TextView reject_cancel;
        TextView status;

        private ViewHolder() {
        }
    }

    public interface VolleyCallback {
        void onSuccess(String str);
    }

    public LeavesListViewAdapter(Context c, int resource, ArrayList<Leaves> values, Fragment fragment) {
        this.mContext = c;
        this.ids = values;
        this.resId = resource;
        this.fragment = fragment;
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
            viewHolder.date = (TextView) convertView.findViewById(R.id.leave_date);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.clas = (TextView) convertView.findViewById(R.id.clas);
            viewHolder.reason = (TextView) convertView.findViewById(R.id.reason);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status);
            viewHolder.approve_edit = (TextView) convertView.findViewById(R.id.approve_edit);
            viewHolder.reject_cancel = (TextView) convertView.findViewById(R.id.reject_cancel);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        if (this.fragment instanceof Leave_Teacher_Pending) {
            viewHolder.approve_edit.setVisibility(0);
            viewHolder.reject_cancel.setVisibility(0);
        } else if (this.fragment instanceof Leave_Teacher_Approved) {
            viewHolder.approve_edit.setVisibility(8);
            viewHolder.reject_cancel.setVisibility(0);
        } else if (this.fragment instanceof Leave_Teacher_Rejected) {
            viewHolder.approve_edit.setVisibility(0);
            viewHolder.reject_cancel.setVisibility(8);
        }
        final Leaves data = (Leaves) getItem(i);
        if (viewHolder.date != null) {
            viewHolder.date.setText(data.getDate() + " (" + data.getDays() + " days)");
        }
        if (viewHolder.name != null) {
            viewHolder.name.setText(data.getName());
        }
        if (viewHolder.clas != null) {
            viewHolder.clas.setText(data.getClas());
        }
        if (viewHolder.reason != null) {
            viewHolder.reason.setText(data.getReason());
        }
        if (viewHolder.status != null) {
            if ("1".equals(data.getStatus())) {
                viewHolder.status.setText("Approved");
                viewHolder.status.setTextColor(-16711936);
            } else if ("2".equals(data.getStatus())) {
                viewHolder.status.setText("Rejected");
                viewHolder.status.setTextColor(-65536);
            } else {
                viewHolder.status.setText("Pending Approval");
                viewHolder.status.setTextColor(this.mContext.getResources().getColor(17170451));
            }
        }
        if (viewHolder.approve_edit != null) {
            viewHolder.approve_edit.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    HashMap<String, String> params = new HashMap();
                    params.put("id", data.getItemid());
                    params.put(NotificationCompat.CATEGORY_STATUS, "1");
                    LeavesListViewAdapter.this.updateStatus(URLs.acceptleave, params);
                }
            });
        }
        if (viewHolder.reject_cancel != null) {
            viewHolder.reject_cancel.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    HashMap<String, String> params = new HashMap();
                    params.put("id", data.getItemid());
                    params.put(NotificationCompat.CATEGORY_STATUS, "0");
                    LeavesListViewAdapter.this.updateStatus(URLs.rejectleave, params);
                }
            });
        }
        if (data.getDate() != null) {
            try {
                if (new Date().after(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(data.getDate()).getTime()))) {
                    if (viewHolder.approve_edit != null) {
                        viewHolder.approve_edit.setVisibility(8);
                    }
                    if (viewHolder.reject_cancel != null) {
                        viewHolder.reject_cancel.setVisibility(8);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void updateStatus(String url, HashMap<String, String> params) {
        getJsonResponse(url, params, new C13133());
    }

    public void getJsonResponse(String url, HashMap<String, String> params, final com.apps.smartschoolmanagement.utils.JsonClass.VolleyCallback callback) {
        final HashMap<String, String> hashMap = params;
        AppSingleton.getInstance(this.mContext).addToRequestQueue(new StringRequest(1, url, new Listener<String>() {
            public void onResponse(String response) {
                Log.d("jkl", "Register Response: " + response.toString());
                LeavesListViewAdapter.this.hideProgress();
                callback.onSuccess(response);
            }
        }, new C13155()) {
            protected Map<String, String> getParams() {
                return hashMap;
            }
        });
        showProgress();
    }

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
