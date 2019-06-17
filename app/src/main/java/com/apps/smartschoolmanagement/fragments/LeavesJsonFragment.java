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
import com.apps.smartschoolmanagement.utils.JsonFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LeavesJsonFragment extends JsonFragment {
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void loadList(String url, final int resource, final View view, final ListView listView) {
       /* getJsonResponse(url, view, new VolleyCallbackJSONObject() {
            @Override
            public void onSuccess(JSONObject jSONObject) {
                LeavesJsonFragment.this.processJSONResult(jSONObject, resource, view, listView);
            }
        });*/
    }

    public void processJSONResult(JSONObject jsonObject, int resource, View view, ListView listView) {
        try {
            this.leaves_values.clear();
            if ("failed".equals(jsonObject.getString(NotificationCompat.CATEGORY_STATUS))) {
                view.findViewById(R.id.error).setVisibility(0);
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
            listView.setAdapter(new LeavesListViewAdapter(getActivity(), resource, this.leaves_values, this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void refreshFragment() {
        getChildFragmentManager().beginTransaction().replace(R.id.container, new AllLeaves()).commit();
    }
}
