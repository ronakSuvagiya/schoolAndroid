package com.apps.smartschoolmanagement.fragments;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TeacherHealthStatus extends JsonFragment {
    HorizontalScrollView horizontalScrollView;
    ArrayList<HashMap<String, String>> jsonValues = new ArrayList();
    LinearLayout layout_horizontal;
    LinearLayout root;
    View rootView;
    ArrayList<HashMap<String, String>> tempList = new ArrayList();
    ArrayList<View> views = new ArrayList();

    /* renamed from: com.apps.smartschoolmanagement.fragments.TeacherHealthStatus$1 */
    class C13791 implements VolleyCallback {
        C13791() {
        }

        public void onSuccess(String result) {
            if (TextUtils.isEmpty(result)) {
                TeacherHealthStatus.this.responseParams.clear();
                TeacherHealthStatus.this.layout_horizontal.removeAllViews();
                TeacherHealthStatus.this.clearFields(TeacherHealthStatus.this.root);
                return;
            }
            TeacherHealthStatus.this.processJSONResult(result);
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.layout_health_status, container, false);
        this.root = (LinearLayout) this.rootView.findViewById(R.id.root_layout);
        this.horizontalScrollView = (HorizontalScrollView) this.rootView.findViewById(R.id.horizontalScrollView);
        this.layout_horizontal = (LinearLayout) this.rootView.findViewById(R.id.layout_horizontal);
        return this.rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
//        getJsonResponse(URLs.healthStatus, this.rootView, new C13791());
        this.rootView.findViewById(R.id.btn_search).setVisibility(8);
        this.rootView.findViewById(R.id.layout_action).setVisibility(8);
    }

    public void processJSONResult(String result) {
        this.responseParams.clear();
        this.tempList.clear();
        this.jsonValues.clear();
        this.layout_horizontal.removeAllViews();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONObject(result).getJSONArray("ResponseList");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            this.responseParams.clear();
            JSONObject jsonObject1 = null;
            try {
                jsonObject1 = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Iterator<String> keysIterator = jsonObject1.keys();
            while (keysIterator.hasNext()) {
                String keyStr = (String) keysIterator.next();
                String valueStr = null;
                try {
                    valueStr = jsonObject1.getString(keyStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ("creation_timestamp".equals(keyStr)) {
                    if (i == 0) {
                        this.rootView.findViewById(R.id.date).setVisibility(0);
                        try {
                            try {
                                ((TextView) this.rootView.findViewById(R.id.date)).setText(getDateCurrentTimeZone(Long.parseLong(jsonObject1.getString("creation_timestamp"))));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (NumberFormatException e) {
                            try {
                                ((TextView) this.rootView.findViewById(R.id.date)).setText(jsonObject1.getString("creation_timestamp").split(" ")[0]);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    try {
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_date, null);
                        TextView date = (TextView) view.findViewById(R.id.date_text);
                        date.setText(jsonObject1.getString("creation_timestamp").split(" ")[0]);
                        if (i == 0) {
                            this.tempList.add(this.responseParams);
                            date.getBackground().setColorFilter(new PorterDuffColorFilter(-16711936, Mode.SRC_IN));
                        }
                        if (this.layout_horizontal.getChildCount() > 0) {
                            for (int j = 0; j < this.layout_horizontal.getChildCount(); j++) {
                                View child = this.layout_horizontal.getChildAt(j);
                                if (child instanceof ViewGroup) {
                                    View child1 = ((ViewGroup) child).getChildAt(0);
                                    if (!(child1 instanceof TextView)) {
                                        continue;
                                    } else if (((TextView) child1).getText().toString().equals(date.getText().toString())) {
                                        this.tempList.add(this.responseParams);
                                    } else {
                                        this.views.add(view);
                                    }
                                }
                            }
                        } else {
                            this.layout_horizontal.addView(view);
                        }
                        final TextView et = (TextView) view.findViewById(R.id.date_text);
                        et.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                if (TeacherHealthStatus.this.layout_horizontal.getChildCount() > 0) {
                                    for (int j = 0; j < TeacherHealthStatus.this.layout_horizontal.getChildCount(); j++) {
                                        View child = TeacherHealthStatus.this.layout_horizontal.getChildAt(j);
                                        if (child instanceof ViewGroup) {
                                            View child1 = ((ViewGroup) child).getChildAt(0);
                                            if (child1 instanceof TextView) {
                                                TextView t = (TextView) child1;
                                                if (t.getText().toString().equals(et.getText().toString())) {
                                                    t.getBackground().setColorFilter(new PorterDuffColorFilter(-16711936, Mode.SRC_IN));
                                                } else {
                                                    t.getBackground().setColorFilter(new PorterDuffColorFilter(TeacherHealthStatus.this.getResources().getColor(17170451), Mode.SRC_IN));
                                                }
                                            }
                                        }
                                    }
                                }
                                TeacherHealthStatus.this.tempList.clear();
                                et.getBackground().setColorFilter(new PorterDuffColorFilter(-16711936, Mode.SRC_IN));
                                Iterator it = TeacherHealthStatus.this.jsonValues.iterator();
                                while (it.hasNext()) {
                                    HashMap<String, String> str = (HashMap) it.next();
                                    if (((String) str.get("creation_timestamp")).split(" ")[0].equals(et.getText().toString())) {
                                        TeacherHealthStatus.this.tempList.add(str);
                                    }
                                }
                                if (TeacherHealthStatus.this.tempList.size() > 0) {
                                    TeacherHealthStatus.this.loadData(TeacherHealthStatus.this.root, (HashMap) TeacherHealthStatus.this.tempList.get(0));
                                }
                            }
                        });
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                this.responseParams.put(keyStr, valueStr);
            }
            HashMap<String, String> temp = new HashMap();
            temp.putAll(this.responseParams);
            this.jsonValues.add(temp);
        }
        addViews(this.views);
        if (this.jsonValues.size() > 0) {
            loadData(this.root, (HashMap) this.jsonValues.get(0));
        }
    }

    public void addViews(ArrayList<View> v) {
        Iterator it = v.iterator();
        while (it.hasNext()) {
            this.layout_horizontal.addView((View) it.next());
        }
    }

    @SuppressLint("ResourceType")
    public void loadData(ViewGroup parent, HashMap<String, String> returnData) {
        if (returnData.size() > 0) {
            this.rootView.findViewById(R.id.error).setVisibility(8);
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof ViewGroup) {
                    loadData((ViewGroup) child, returnData);
                } else if (parent.getChildAt(i) instanceof TextView) {
                    TextView et = (TextView) parent.getChildAt(i);
                    if (et.getId() > 0 && returnData.containsKey(getResources().getResourceEntryName(et.getId()))) {
                        String m = getResources().getResourceEntryName(et.getId());
                        if (m.equals("blood_group")) {
                            et.setText(((String) returnData.get(m)).toUpperCase());
                        } else {
                            et.setText((CharSequence) returnData.get(getResources().getResourceEntryName(et.getId())));
                        }
                    }
                }
            }
            return;
        }
        clearFields(this.root);
    }

    @SuppressLint("ResourceType")
    public void clearFields(ViewGroup parent) {
        this.rootView.findViewById(R.id.date).setVisibility(8);
        this.rootView.findViewById(R.id.error).setVisibility(0);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                clearFields((ViewGroup) child);
            } else if (parent.getChildAt(i) instanceof TextView) {
                TextView et = (TextView) parent.getChildAt(i);
                if (et.getId() > 0) {
                    et.setText("");
                }
            }
        }
    }

    public String getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(1000 * timestamp);
            calendar.add(14, tz.getOffset(calendar.getTimeInMillis()));
            return new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
        } catch (Exception e) {
            return "";
        }
    }
}
