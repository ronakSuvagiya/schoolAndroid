package com.apps.smartschoolmanagement.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.apps.smartschoolmanagement.activities.PrincipalStatementActivity;
import com.apps.smartschoolmanagement.activities.ProfileEditActivity;
import com.apps.smartschoolmanagement.utils.AnimationSlideUtil;
import com.apps.smartschoolmanagement.utils.AppSingleton;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ManagerProfile extends JsonFragment {
    public static ImageView profilePic;
    LinearLayout root;
    View view;

    /* renamed from: com.apps.smartschoolmanagement.fragments.ManagerProfile$1 */
    class C13511 implements OnClickListener {
        C13511() {
        }

        public void onClick(View view) {
            ManagerProfile.this.startActivity(new Intent(ManagerProfile.this.getActivity(), ProfileEditActivity.class));
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.ManagerProfile$2 */
    class C13522 implements ImageListener {
        C13522() {
        }

        public void onResponse(ImageContainer imageContainer, boolean b) {
            Bitmap bitmap = imageContainer.getBitmap();
            if (bitmap != null) {
                ManagerProfile.profilePic.setImageBitmap(bitmap);
            }
        }

        public void onErrorResponse(VolleyError volleyError) {
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.ManagerProfile$3 */
    class C13533 implements VolleyCallback {
        C13533() {
        }

        public void onSuccess(String result) {
            ManagerProfile.this.processJsonResponse(result);
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.fragments.ManagerProfile$4 */
    class C13544 implements OnClickListener {
        C13544() {
        }

        public void onClick(View v) {
            ManagerProfile.this.startActivity(new Intent(ManagerProfile.this.getActivity(), PrincipalStatementActivity.class));
            AnimationSlideUtil.activityZoom(ManagerProfile.this.getActivity());
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.layout_profile_view, container, false);
//        this.view.findViewById(R.id.layout_ctc).setVisibility(8);
        this.view.findViewById(R.id.layout_birthday).setVisibility(0);

        this.view.findViewById(R.id.layout_joining_date).setVisibility(8);
        this.view.findViewById(R.id.layout_experience).setVisibility(8);
        this.root = (LinearLayout) this.view.findViewById(R.id.root_layout);
        this.view.findViewById(R.id.btn_edit).setOnClickListener(new C13511());
//        this.view.findViewById(R.id.layout_hm).setVisibility(0);
        this.view.findViewById(R.id.layout_class).setVisibility(8);
        this.view.findViewById(R.id.coordinatorlayout).setBackgroundColor(Color.parseColor("#33546E7A"));
        profilePic = (ImageView) this.view.findViewById(R.id.file_path);
        if (ProfileInfo.getInstance().getLoginData().get("userPic") != null && ((String) ProfileInfo.getInstance().getLoginData().get("userPic")).length() > 50) {
            profilePic.setImageBitmap(ImageBase64.decode((String) ProfileInfo.getInstance().getLoginData().get("userPic")));
        } else if (ProfileInfo.getInstance().getLoginData().get("file_path") != null) {
            AppSingleton.getInstance(getActivity()).getImageLoader().get((String) ProfileInfo.getInstance().getLoginData().get("file_path"), new C13522());
        }
        this.params.put("admin_id", ProfileInfo.getInstance().getLoginData().get("userId"));
//        getJsonResponse(URLs.userprofile, this.view, new C13533());

        return this.view;
    }

    public void processJsonResponse(String response) {
        if (response != null && !response.equalsIgnoreCase("")) {
            this.responseParams.clear();
            try {
                JSONArray jsonArray = new JSONObject(response).getJSONArray("staffprofile");
                Iterator<String> keysIterator = jsonArray.getJSONObject(0).keys();
                while (keysIterator.hasNext()) {
                    String keyStr = (String) keysIterator.next();
                    this.responseParams.put(keyStr, jsonArray.getJSONObject(0).getString(keyStr));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loadData(this.root, this.responseParams);
        }
    }

    public void loadData(ViewGroup parent, HashMap<String, String> returnData) {
        if (returnData != null) {
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
        Toast.makeText(getActivity(), "No record found", 0).show();
    }
}
