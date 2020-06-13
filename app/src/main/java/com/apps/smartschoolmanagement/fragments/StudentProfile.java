package com.apps.smartschoolmanagement.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonFragment;
import com.apps.smartschoolmanagement.utils.LocaleManager;

import java.util.Locale;

public class StudentProfile extends JsonFragment  {
    public static ImageView profilePic;
    LinearLayout root;
    SharedPreferences sp;
    View view;
    Locale locale;

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
//        loadLocale();


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
        LinearLayout rollNo = view.findViewById(R.id.layout_roll);
        rollNo.setVisibility(View.VISIBLE);
//        loadLocale();
        CardView email = view.findViewById(R.id.layout_email);
        CardView language = view.findViewById(R.id.layout_language);
        language.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            showChangeLangDialog();
                                        }
                                    }
        );
        email.setVisibility(View.GONE);
        CardView phone = view.findViewById(R.id.layout_phone);
        phone.setVisibility(View.GONE);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.root.setVisibility(0);

        String name = (sp.getString("stuname", ""));
        String lastName = (sp.getString("stulname", ""));
        String dob = (sp.getString("studob", ""));
        String gender = (sp.getString("stugender", ""));
        String address = (sp.getString("stuaddress", ""));
        String std = (sp.getString("stustd", ""));
        String div = (sp.getString("studiv", ""));
        int roll = (sp.getInt("roll", 0));
        TextView pname = this.view.findViewById(R.id.name);
        pname.setText(name + " " + lastName);
        TextView pclassname = this.view.findViewById(R.id.className);
        pclassname.setText(std);
        TextView pdiv = this.view.findViewById(R.id.div_name);
        pdiv.setText(div);

        TextView proll = this.view.findViewById(R.id.roll_name);
        proll.setText(String.valueOf(roll));

        TextView tname = this.view.findViewById(R.id.names);
        tname.setText(name + " " + lastName);
        TextView tdob = this.view.findViewById(R.id.birthday);
        tdob.setText(dob);
        TextView paddress = this.view.findViewById(R.id.address);
        paddress.setText(address);
        TextView tgender = this.view.findViewById(R.id.gender);
        tgender.setText(gender);
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
//    private void showChangeLanguageDialog() {
//        final String[] listitems = {"English", "हिंदी", "ગુજરાતી"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Choose Language...");
//        builder.setSingleChoiceItems(listitems, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (i == 0) {
//                    setLocale("en");
//                    getActivity().recreate();
//                } else if (i == 1) {
//                    setLocale("hi");
//                    getActivity().recreate();
//                } else if (i == 2) {
//                    setLocale("hi");
//                    getActivity().recreate();
//                }
//
//                dialogInterface.dismiss();
//
//            }
//        });
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//    }

//    private void setLocale(String lang) {
//        Locale locale = new Locale(lang);
//        Locale.setDefault(locale);
//
//        Configuration configuration = new Configuration();
//        configuration.locale = locale;
//        getActivity().getBaseContext().getResources().updateConfiguration(configuration, getActivity().getBaseContext().getResources().getDisplayMetrics());
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("MyLang", lang);
//        editor.apply();
//
//    }

//    public void setLocale(String lang) {
//        Locale myLocale = new Locale(lang);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
//        res.updateConfiguration(conf, dm);
//        getActivity().getBaseContext().getResources().updateConfiguration(conf,  getActivity().getBaseContext().getResources().getDisplayMetrics());
//        getActivity().invalidateOptionsMenu();
//        getActivity().recreate();
//                SharedPreferences.Editor editor = sp.edit();
//        editor.putString("MyLang", lang);
//        editor.commit();
//    }

//    public void loadLocale() {
//        String lang = sp.getString("MyLang", "en");
//        setLocale(lang);
//    }
public void showChangeLangDialog() {
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = this.getLayoutInflater();
    final View dialogView = inflater.inflate(R.layout.language_dialog, null);
    dialogBuilder.setView(dialogView);

    final Spinner spinner1 = (Spinner) dialogView.findViewById(R.id.spinner1);

    dialogBuilder.setTitle(getResources().getString(R.string.language_setting));
//    dialogBuilder.setMessage(getResources().getString(R.string.lang_dialog_message));
    dialogBuilder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            int langpos = spinner1.getSelectedItemPosition();
            switch(langpos) {
                case 0: //English
//                    sp.edit().putString("LANG", "en").apply();
//                    LocaleUtils.setLocale(Locale.forLanguageTag(LAN_ENGLISH));
//                    setNewLocale(this, LocaleManager.);
//                    getActivity().recreate();
//                    LocalizationActivity.setLanguage(Locale.ENGLISH);
                    return;
                case 1: //Hindi
//                    sp.edit().putString("LANG", "hi").apply();
//                    LocaleUtils.setLocale("hi");
//                    LocaleUtils.setLocale(Locale.forLanguageTag(LAN_Hindi));
//                    getActivity().recreate();
                    return;
                case 2: //Hindi
//                    sp.edit().putString("LANG", "gu").apply();
//                    setLocale("gu");
//                    LocaleUtils.setLocale(Locale.forLanguageTag(LAN_Gujrati));
//                    getActivity().recreate();
                    return;
                default: //By default set to english
//                    sp.edit().putString("LANG", "en").apply();
//                    setLocale("en");
//                    LocaleUtils.setLocale(Locale.forLanguageTag(LAN_ENGLISH));
                    return;
            }
        }
    });
    dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            //pass
        }
    });
    AlertDialog b = dialogBuilder.create();
    b.show();
}
    private void setNewLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(getActivity(), language);
        Intent intent = mContext.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

//    public void setLangRecreate(String langval) {
//        Configuration config = getActivity().getBaseContext().getResources().getConfiguration();
//        locale = new Locale(langval);
//        Locale.setDefault(locale);
//        config.locale = locale;
//        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
//        getActivity().recreate();
//    }

//        public void setLocale(String lang) {
//        Locale myLocale = new Locale(lang);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
//        res.updateConfiguration(conf, dm);
//        getActivity().getBaseContext().getResources().updateConfiguration(conf,  getActivity().getBaseContext().getResources().getDisplayMetrics());
//        getActivity().invalidateOptionsMenu();
//        getActivity().recreate();
////                SharedPreferences.Editor editor = sp.edit();
////        editor.putString("MyLang", lang);
////        editor.commit();
//    }
}
