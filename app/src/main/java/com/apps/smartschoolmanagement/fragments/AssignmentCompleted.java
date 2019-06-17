package com.apps.smartschoolmanagement.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.AddPhotoGalleryActivity.Utils;
import com.apps.smartschoolmanagement.adapters.ListViewAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AssignmentCompleted extends JsonFragment {
    ListView listView;
    View rootView;

    /* renamed from: com.apps.smartschoolmanagement.fragments.AssignmentCompleted$1 */
    class C13411 implements VolleyCallback {
        C13411() {
        }

        public void onSuccess(String result) {
            try {
                AssignmentCompleted.this.processJSONResult(new JSONObject(result));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.listview, container, false);
        this.listView = (ListView) this.rootView.findViewById(R.id.listview);
        loadData();
        return this.rootView;
    }

    public void loadData() {
        this.params.put("assignment_id", getActivity().getIntent().getStringExtra("assignment_id"));
      //  getJsonResponse(URLs.assignments_status_completedList, this.rootView, new C13411());
    }

    public void processJSONResult(JSONObject jsonObject) {
        try {
            this.values.clear();
            JSONArray jsonArray = jsonObject.getJSONArray("Response");
            if (jsonArray.length() > 0) {
                this.rootView.findViewById(R.id.error).setVisibility(8);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    ListData listData = new ListData();
                    if (!jsonObject1.isNull(Utils.imageName)) {
                        listData.setStudent_name(jsonObject1.getString(Utils.imageName));
                    }
                    if (!jsonObject1.isNull("file_path")) {
                        listData.setStudent_photo(jsonObject1.getString("file_path"));
                    }
                    this.values.add(listData);
                }
            } else {
                this.rootView.findViewById(R.id.error).setVisibility(0);
            }
            this.listView.setAdapter(new ListViewAdapter(getActivity(), R.layout.item_layout_assignment_student, this.values));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
