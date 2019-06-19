package com.apps.smartschoolmanagement.activities;

import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
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

public class MyHealthStatusActivity extends JsonClass {
    HorizontalScrollView horizontalScrollView;
    ArrayList<HashMap<String, String>> jsonValues = new ArrayList();
    LinearLayout layout_horizontal;
    LinearLayout root;
    ArrayList<HashMap<String, String>> tempList = new ArrayList();
    ArrayList<View> views = new ArrayList();

    /* renamed from: com.apps.smartschoolmanagement.activities.MyHealthStatusActivity$1 */
    class C12431 implements VolleyCallback {
        C12431() {
        }

        public void onSuccess(String result) {
            try {
                MyHealthStatusActivity.this.processJSONResult(new JSONObject(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_health_status);
        setTitle("My Health Status");
        KProgressHUD progressHUD = KProgressHUD.create(this).setLabel("Loading");
        findViewById(R.id.layout_action).setVisibility(8);
        this.root = (LinearLayout) findViewById(R.id.root_layout);
        this.horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        this.layout_horizontal = (LinearLayout) findViewById(R.id.layout_horizontal);
        if (UserStaticData.user_type == 0) {
            this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        }
        if (UserStaticData.user_type == 1) {
            this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        }
        if (UserStaticData.user_type == 2) {
            this.params.put("admin_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        }
       // getJsonResponse(URLs.healthStatus, this, new C12431());
    }

    public void processJSONResult(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("ResponseList");
            for (int i = 0; i < jsonArray.length(); i++) {
                this.responseParams.clear();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Iterator<String> keysIterator = jsonObject1.keys();
                while (keysIterator.hasNext()) {
                    String keyStr = (String) keysIterator.next();
                    String valueStr = jsonObject1.getString(keyStr);
                    if ("creation_timestamp".equals(keyStr)) {
                        if (i == 0) {
                            findViewById(R.id.date).setVisibility(0);
                            try {
                                ((TextView) findViewById(R.id.date)).setText(getDateCurrentTimeZone(Long.parseLong(jsonObject1.getString("creation_timestamp"))));
                            } catch (NumberFormatException e) {
                                ((TextView) findViewById(R.id.date)).setText(jsonObject1.getString("creation_timestamp").split(" ")[0]);
                            }
                        }
                        View view = LayoutInflater.from(this).inflate(R.layout.item_date, null);
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
                                if (MyHealthStatusActivity.this.layout_horizontal.getChildCount() > 0) {
                                    for (int j = 0; j < MyHealthStatusActivity.this.layout_horizontal.getChildCount(); j++) {
                                        View child = MyHealthStatusActivity.this.layout_horizontal.getChildAt(j);
                                        if (child instanceof ViewGroup) {
                                            View child1 = ((ViewGroup) child).getChildAt(0);
                                            if (child1 instanceof TextView) {
                                                TextView t = (TextView) child1;
                                                if (t.getText().toString().equals(et.getText().toString())) {
                                                    t.getBackground().setColorFilter(new PorterDuffColorFilter(-16711936, Mode.SRC_IN));
                                                } else {
                                                    t.getBackground().setColorFilter(new PorterDuffColorFilter(MyHealthStatusActivity.this.getResources().getColor(17170451), Mode.SRC_IN));
                                                }
                                            }
                                        }
                                    }
                                }
                                MyHealthStatusActivity.this.tempList.clear();
                                et.getBackground().setColorFilter(new PorterDuffColorFilter(-16711936, Mode.SRC_IN));
                                Iterator it = MyHealthStatusActivity.this.jsonValues.iterator();
                                while (it.hasNext()) {
                                    HashMap<String, String> str = (HashMap) it.next();
                                    if (((String) str.get("creation_timestamp")).split(" ")[0].equals(et.getText().toString())) {
                                        MyHealthStatusActivity.this.tempList.add(str);
                                    }
                                }
                                if (MyHealthStatusActivity.this.tempList.size() > 0) {
                                    MyHealthStatusActivity.this.loadData(MyHealthStatusActivity.this.root, (HashMap) MyHealthStatusActivity.this.tempList.get(0));
                                }
                            }
                        });
                    }
                    this.responseParams.put(keyStr, valueStr);
                }
                HashMap<String, String> temp = new HashMap();
                temp.putAll(this.responseParams);
                this.jsonValues.add(temp);
            }
            addViews(this.views);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
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

    public void loadData(ViewGroup parent, HashMap<String, String> returnData) {
        if (returnData.size() > 0) {
            findViewById(R.id.error).setVisibility(8);
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
        findViewById(R.id.error).setVisibility(0);
    }

    public void clearFields(ViewGroup parent) {
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
