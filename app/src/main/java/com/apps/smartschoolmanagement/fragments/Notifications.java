package com.apps.smartschoolmanagement.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.ListViewAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.URLs;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Notifications extends JsonFragment {
//    ListView listView;
//    ArrayList<ListData> values = new ArrayList();
    View view;
    TextView notification_titles;

    /* renamed from: com.apps.smartschoolmanagement.fragments.Notifications$1 */
//    class C13551 implements VolleyCallback {
//        C13551() {
//        }

//        public void onSuccess(String result) {
//            try {
//                Notifications.this.processJSONResult(new JSONObject(result));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.temp_notice_board, container, false);
//        this.listView = (ListView) this.view.findViewById(R.id.listview);
//        getJsonResponse(URLs.notifications, this.view, new C13551());
        notification_titles = view.findViewById(R.id.notification_titles);
        return this.view;
    }

//    public void processJSONResult(JSONObject jsonObject) {
//        try {
//            this.values.clear();
//            JSONArray jsonArray = jsonObject.getJSONArray("notices");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                String title = jsonObject1.getString("notice_title");
//                String msg = jsonObject1.getString("notice");
//                String timestamp = jsonObject1.getString("create_timestamp");
//                ListData listData = new ListData();
//                listData.setNotify_message(msg);
//                listData.setNotify_title(title);
//                String[] splited;
//                try {
//                    splited = getDateCurrentTimeZone(Long.parseLong(timestamp)).split("\\s+");
//                    listData.setNotify_date(splited[0]);
//                    listData.setNotify_time(splited[1]);
//                } catch (NumberFormatException e) {
//                    splited = timestamp.split("\\s+");
//                    listData.setNotify_date(splited[0]);
//                    listData.setNotify_time(splited[1]);
//                }
//                this.values.add(listData);
//            }
//            this.listView.setAdapter(new ListViewAdapter(getActivity(), R.layout.item_layout_notifications, this.values));
//        } catch (JSONException e2) {
//            e2.printStackTrace();
//        }
//    }
//
//    public String getDateCurrentTimeZone(long timestamp) {
//        try {
//            Calendar calendar = Calendar.getInstance();
//            TimeZone tz = TimeZone.getDefault();
//            calendar.setTimeInMillis(1000 * timestamp);
//            calendar.add(14, tz.getOffset(calendar.getTimeInMillis()));
//            return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(calendar.getTime());
//        } catch (Exception e) {
//            return "";
//        }
//    }
}
