package com.apps.smartschoolmanagement.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity.Utils;
import com.apps.smartschoolmanagement.adapters.LeavesListViewAdapter;
import com.apps.smartschoolmanagement.models.Leaves;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AllLeaves extends JsonFragment {
    ListView listView;
    View view;

    /* renamed from: com.apps.smartschoolmanagement.fragments.AllLeaves$1 */
    class C13381 implements VolleyCallback {
        C13381() {
        }

        public void onSuccess(String result) {
            try {
                AllLeaves.this.processJSONResult(new JSONObject(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.listview, container, false);
        this.listView = (ListView) this.view.findViewById(R.id.listview);
        if (UserStaticData.user_type == 1) {
            this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        } else if (UserStaticData.user_type == 0) {
            this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
        }
//        getJsonResponse(URLs.allleaves, this.view, new C13381());
        return this.view;
    }

    public void processJSONResult(JSONObject jsonObject) {
        try {
            this.leaves_values.clear();
            if ("failed".equals(jsonObject.getString(NotificationCompat.CATEGORY_STATUS))) {
                this.view.findViewById(R.id.error).setVisibility(0);
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("Response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Leaves listData = new Leaves();
                if (!jsonObject1.isNull("itemid")) {
                    listData.setItemid(jsonObject1.getString("itemid"));
                }
                if (!jsonObject1.isNull(Utils.imageName)) {
                    listData.setName(jsonObject1.getString(Utils.imageName));
                }
                if (!jsonObject1.isNull("class")) {
                    listData.setClas(jsonObject1.getString("class"));
                }
                if (!jsonObject1.isNull("startdate")) {
                    listData.setDate(jsonObject1.getString("startdate"));
                }
                if (!jsonObject1.isNull("days")) {
                    listData.setDays(jsonObject1.getString("days"));
                }
                if (!jsonObject1.isNull("reason")) {
                    listData.setReason(jsonObject1.getString("reason"));
                }
                if (!jsonObject1.isNull(NotificationCompat.CATEGORY_STATUS)) {
                    listData.setStatus(jsonObject1.getString(NotificationCompat.CATEGORY_STATUS));
                }
                this.leaves_values.add(listData);
            }
            this.listView.setAdapter(new LeavesListViewAdapter(getActivity(), R.layout.item_layout_leaves_teacher_history, this.leaves_values, this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
