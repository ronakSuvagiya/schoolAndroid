package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.ListView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.ListViewAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BusesListActivity extends JsonClass {
    ListView listView;

    /* renamed from: com.apps.smartschoolmanagement.activities.BusesListActivity$1 */
    class C12031 implements VolleyCallback {
        C12031() {
        }

        public void onSuccess(String result) {
            try {
                BusesListActivity.this.processJSONResult(new JSONObject(result));
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
        setContentView(R.layout.listview);
        setTitle("Buses");
        KProgressHUD progressHUD = KProgressHUD.create(this).setLabel("Loading");
        this.listView = (ListView) findViewById(R.id.listview);
       // getJsonResponse(URLs.buses, this, new C12031());
    }

    public void processJSONResult(JSONObject jsonObject) {
        try {
            this.values.clear();
            JSONArray jsonArray = jsonObject.getJSONArray("Response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                ListData listData = new ListData();
                if (!jsonObject1.isNull("driver_name")) {
                    listData.setBus_driver_name(jsonObject1.getString("driver_name"));
                }
                if (!jsonObject1.isNull("phone_number")) {
                    listData.setBus_phone_number(jsonObject1.getString("phone_number"));
                }
                if (!jsonObject1.isNull("transport_id")) {
                    listData.setBus_transport_id(jsonObject1.getString("transport_id"));
                }
                if (!jsonObject1.isNull("route_name")) {
                    listData.setBus_route_name(jsonObject1.getString("route_name"));
                }
                if (!jsonObject1.isNull("latitude")) {
                    listData.setBus_latitude(jsonObject1.getString("latitude"));
                }
                if (!jsonObject1.isNull("logitude")) {
                    listData.setBus_logitude(jsonObject1.getString("logitude"));
                }
                this.values.add(listData);
            }
            this.listView.setAdapter(new ListViewAdapter(this, R.layout.item_layout_buses, this.values));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
