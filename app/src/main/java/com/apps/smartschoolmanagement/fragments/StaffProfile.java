package com.apps.smartschoolmanagement.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.ProfileEditActivity;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.AppSingleton;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StaffProfile extends JsonFragment {
    public static ImageView profilePic;
    LinearLayout root;
    View view;

    /* renamed from: com.apps.smartschoolmanagement.fragments.StaffProfile$1 */
    class C13601 implements OnClickListener {
        C13601() {
        }

        public void onClick(View view) {
            Intent intent = new Intent(StaffProfile.this.getActivity(), ProfileEditActivity.class);
            intent.putExtra("user", "teacher");
            StaffProfile.this.startActivity(intent);
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.StaffProfile$2 */
    class C13612 implements ImageListener {
        C13612() {
        }

        public void onResponse(ImageContainer imageContainer, boolean b) {
            Bitmap bitmap = imageContainer.getBitmap();
            if (bitmap != null) {
                StaffProfile.profilePic.setImageBitmap(bitmap);
            }
        }

        public void onErrorResponse(VolleyError volleyError) {
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.StaffProfile$3 */
    class C13623 implements VolleyCallback {
        C13623() {
        }

        public void onSuccess(String result) {
            StaffProfile.this.processJsonResponse(result);
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.layout_profile_view, container, false);
        this.view.findViewById(R.id.layout_subject).setVisibility(0);
        this.root = (LinearLayout) this.view.findViewById(R.id.root_layout);
        this.view.findViewById(R.id.btn_edit).setOnClickListener(new C13601());
        if (UserStaticData.user_type != 2) {
            this.view.findViewById(R.id.layout_ctc).setVisibility(8);
        }
        profilePic = (ImageView) this.view.findViewById(R.id.file_path);
        if (ProfileInfo.getInstance().getLoginData().get("userPic") != null && ((String) ProfileInfo.getInstance().getLoginData().get("userPic")).length() > 50) {
            profilePic.setImageBitmap(ImageBase64.decode((String) ProfileInfo.getInstance().getLoginData().get("userPic")));
        } else if (ProfileInfo.getInstance().getLoginData().get("file_path") != null) {
            AppSingleton.getInstance(getActivity()).getImageLoader().get((String) ProfileInfo.getInstance().getLoginData().get("file_path"), new C13612());
        }
        this.params.put("teacher_id", ProfileInfo.getInstance().getLoginData().get("userId"));
//        getJsonResponse(URLs.userprofile, this.view, new C13623());
        return this.view;
    }

    public void processJsonResponse(String response) {
        if (response != null && !response.equalsIgnoreCase("")) {
            this.responseParams.clear();
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                JSONArray jsonArray = jsonObject1.getJSONArray("staffprofile");
                Iterator<String> keysIterator = jsonArray.getJSONObject(0).keys();
                while (keysIterator.hasNext()) {
                    String keyStr = (String) keysIterator.next();
                    this.responseParams.put(keyStr, jsonArray.getJSONObject(0).getString(keyStr));
                }
                JSONArray jsonArray1 = jsonObject1.getJSONArray("attendance");
                keysIterator = jsonArray1.getJSONObject(0).keys();
                while (keysIterator.hasNext()) {
                    if ("Absent".equals(jsonArray1.getJSONObject(0).getString((String) keysIterator.next()))) {
                        this.view.findViewById(R.id.attendance_present).setVisibility(8);
                        this.view.findViewById(R.id.attendance_absent).setVisibility(0);
                    } else {
                        this.view.findViewById(R.id.attendance_present).setVisibility(0);
                        this.view.findViewById(R.id.attendance_absent).setVisibility(8);
                    }
                }
                if (jsonArray1.length() < 1) {
                    this.view.findViewById(R.id.attendance_present).setVisibility(8);
                    this.view.findViewById(R.id.attendance_absent).setVisibility(8);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loadData(this.root, this.responseParams);
        }
    }

    public void loadData(ViewGroup parent, HashMap<String, String> returnData) {
        if (returnData != null) {
            this.root.setVisibility(0);
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof ViewGroup) {
                    loadData((ViewGroup) child, returnData);
                } else if (parent.getChildAt(i) instanceof TextView) {
                    TextView et = (TextView) parent.getChildAt(i);
                    if (et.getId() > 0 && returnData.containsKey(getResources().getResourceEntryName(et.getId()))) {
                        et.setText((CharSequence) returnData.get(getResources().getResourceEntryName(et.getId())));
                    }
                }
            }
            return;
        }
        Toast.makeText(getActivity(), "No records found", 0).show();
    }
}
