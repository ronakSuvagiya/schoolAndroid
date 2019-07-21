package com.apps.smartschoolmanagement.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import android.preference.PreferenceManager;
import android.util.Log;
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
import com.apps.smartschoolmanagement.activities.BusTrackingActivity;
import com.apps.smartschoolmanagement.activities.ProfileEditActivity;
import com.apps.smartschoolmanagement.utils.AppSingleton;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class StudentProfile extends JsonFragment {
    public static ImageView profilePic;
    LinearLayout root;
    SharedPreferences sp;
    View view;

    /* renamed from: com.apps.smartschoolmanagement.fragments.StudentProfile$1 */
//    class C13721 implements OnClickListener {
//        C13721() {
//        }
//
//        public void onClick(View view) {
//            StudentProfile.this.startActivity(new Intent(StudentProfile.this.getActivity(), ProfileEditActivity.class));
//        }
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.StudentProfile$2 */
//    class C13732 implements OnClickListener {
//        C13732() {
//        }
//
//        public void onClick(View v) {
//            StudentProfile.this.startActivity(new Intent(StudentProfile.this.getActivity(), BusTrackingActivity.class));
//        }
//    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.StudentProfile$3 */
//    class C13743 implements ImageListener {
//        C13743() {
//        }
//
//        public void onResponse(ImageContainer imageContainer, boolean b) {
//            Bitmap bitmap = imageContainer.getBitmap();
//            if (bitmap != null) {
//                StudentProfile.profilePic.setImageBitmap(bitmap);
//            }
//        }
//
//        public void onErrorResponse(VolleyError volleyError) {
//        }
//    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.StudentProfile$4 */
//    class C13754 implements VolleyCallback {
//        C13754() {
//        }
//
//        public void onSuccess(String result) {
//            StudentProfile.this.processJsonResponse(result);
//        }
//    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.layout_profile_view, container, false);
        this.root = (LinearLayout) this.view.findViewById(R.id.root_layout);

        this.root.setVisibility(0);

//        TextView aav = this.view.findViewById(R.id.teacherName);
//        aav.setText(name);
//        aav.setTextColor(R.color.white);
//

//        this.view.findViewById(R.id.btn_edit).setOnClickListener(new C13721());
//        profilePic = (ImageView) this.view.findViewById(R.id.file_path);
//        this.view.findViewById(R.id.layout_joining_date).setVisibility(8);
//        this.view.findViewById(R.id.layout_experience).setVisibility(8);
//        this.view.findViewById(R.id.layout_ctc).setVisibility(8);




//        if (ProfileInfo.getInstance().getLoginData().get("userPic") != null && ((String) ProfileInfo.getInstance().getLoginData().get("userPic")).length() > 50) {
//            profilePic.setImageBitmap(ImageBase64.decode((String) ProfileInfo.getInstance().getLoginData().get("userPic")));
//        } else if (ProfileInfo.getInstance().getLoginData().get("file_path") != null) {
//            AppSingleton.getInstance(getActivity()).getImageLoader().get((String) ProfileInfo.getInstance().getLoginData().get("file_path"), new C13743());
//        }
//        this.params.put("student_id", ProfileInfo.getInstance().getLoginData().get("userId"));
//        getJsonResponse(URLs.userprofile, this.view, new C13754());
        return this.view;
    }

//    public void processJsonResponse(String response) {
//        if (response != null && !response.equalsIgnoreCase("")) {
//            this.responseParams.clear();
//            try {
//                JSONObject jsonObject1 = new JSONObject(response);
//                JSONArray jsonArray = jsonObject1.getJSONArray("staffprofile");
//                Iterator<String> keysIterator = jsonArray.getJSONObject(0).keys();
//                while (keysIterator.hasNext()) {
//                    String keyStr = (String) keysIterator.next();
//                    this.responseParams.put(keyStr, jsonArray.getJSONObject(0).getString(keyStr));
//                }
//                JSONArray jsonArray1 = jsonObject1.getJSONArray("attendance");
//                keysIterator = jsonArray1.getJSONObject(0).keys();
//                while (keysIterator.hasNext()) {
//                    if ("Absent".equals(jsonArray1.getJSONObject(0).getString((String) keysIterator.next()))) {
//                        this.view.findViewById(R.id.attendance_present).setVisibility(8);
//                        this.view.findViewById(R.id.attendance_absent).setVisibility(0);
//                    } else {
//                        this.view.findViewById(R.id.attendance_present).setVisibility(0);
//                        this.view.findViewById(R.id.attendance_absent).setVisibility(8);
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            loadData(this.root, this.responseParams);
//        }
//    }

//    public void loadData(ViewGroup parent, HashMap<String, String> returnData) {
//        if (returnData != null) {
//            for (int i = 0; i < parent.getChildCount(); i++) {
//                View child = parent.getChildAt(i);
//                if (child instanceof ViewGroup) {
//                    loadData((ViewGroup) child, returnData);
//                } else if (parent.getChildAt(i) instanceof TextView) {
//                    TextView et = (TextView) parent.getChildAt(i);
//                    if (et.getId() > 0 && returnData.containsKey(getResources().getResourceEntryName(et.getId()))) {
//                        et.setText((CharSequence) returnData.get(getResources().getResourceEntryName(et.getId())));
//                    }
//                }
//            }
//            return;
//        }
//        Toast.makeText(getActivity(), "No record found", 0).show();
//    }
}
