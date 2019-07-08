package com.apps.smartschoolmanagement.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.activities.AppointmentsActivity;
import com.apps.smartschoolmanagement.activities.AppointmentsAdminActivity;
import com.apps.smartschoolmanagement.activities.AssignmentsTeacherActivity;
import com.apps.smartschoolmanagement.activities.AttendanceActivity;
import com.apps.smartschoolmanagement.activities.BusesListActivity;
import com.apps.smartschoolmanagement.activities.ExamScheduleActivity;
import com.apps.smartschoolmanagement.activities.FeeDetailsActivity;
import com.apps.smartschoolmanagement.activities.HealthStatusActivity;
import com.apps.smartschoolmanagement.activities.HomeActivity;
import com.apps.smartschoolmanagement.activities.LibraryActivity;
import com.apps.smartschoolmanagement.activities.ManageLeavesActivity;
import com.apps.smartschoolmanagement.activities.MarksListActivity;
import com.apps.smartschoolmanagement.activities.MyHealthStatusActivity;
import com.apps.smartschoolmanagement.activities.OnlineMaterialActivity;
import com.apps.smartschoolmanagement.activities.PendingLeaves_HM_Activity;
import com.apps.smartschoolmanagement.activities.PhotoGalleryActivity;
import com.apps.smartschoolmanagement.activities.PhotoGalleryAddActivity;
import com.apps.smartschoolmanagement.activities.PostMaterialActivity;
import com.apps.smartschoolmanagement.activities.PostRemarkActivity;
import com.apps.smartschoolmanagement.activities.PrincipalMessageActivity;
import com.apps.smartschoolmanagement.activities.ProfitLossActivity;
import com.apps.smartschoolmanagement.activities.RemarksActivity;
import com.apps.smartschoolmanagement.activities.StaffAttendanceActivity;
import com.apps.smartschoolmanagement.activities.StaffPayRollActivity;
import com.apps.smartschoolmanagement.activities.StaffProfileActivity;
import com.apps.smartschoolmanagement.activities.StudentAttendanceActivity;
import com.apps.smartschoolmanagement.activities.StudentLeavesActivity;
import com.apps.smartschoolmanagement.activities.StudentProfileActivity;
import com.apps.smartschoolmanagement.adapters.GridViewAdapter;
import com.apps.smartschoolmanagement.utils.AnimationSlideUtil;

public class Home_Fragment extends Fragment {
    View view;

    /* renamed from: com.apps.smartschoolmanagement.fragments.Home_Fragment$1 */
    class C13461 implements OnItemClickListener {
        C13461() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String clickedText = null;
            TextView mtext = (TextView) view.findViewById(R.id.image_title);
            if (mtext != null) {
                clickedText = mtext.getText().toString();
            }
            if (clickedText != null) {
                int obj = -1;
                switch (clickedText.hashCode()) {
                    case -2095501965:
                        if (clickedText.equals("Notification")) {
                            obj = 18;
                            break;
                        }
                        break;
                    case -2031827164:
                        if (clickedText.equals("Photo Gallery")) {
                            obj = 11;
                            break;
                        }
                        break;
                    case -2022869828:
                        if (clickedText.equals("Leaves")) {
                            obj = 6;
                            break;
                        }
                        break;
                    case -2002773932:
                        if (clickedText.equals("Online Material")) {
                            obj = 8;
                            break;
                        }
                        break;
                    case -1827105402:
                        if (clickedText.equals("Schedule")) {
                            obj = 19;
                            break;
                        }
                        break;
                    case -1675962311:
                        if (clickedText.equals("Profit & Loss")) {
                            obj = 28;
                            break;
                        }
                        break;
                    case -1585309300:
                        if (clickedText.equals("Complaint")) {
                            obj = 5;
                            break;
                        }
                        break;
                    case -1562769941:
                        if (clickedText.equals("Track Bus")) {
                            obj = 14;
                            break;
                        }
                        break;
                    case -1538898733:
                        if (clickedText.equals("Remarks")) {
                            obj = 17;
                            break;
                        }
                        break;
                    case -1348367228:
                        if (clickedText.equals("Student Profile")) {
                            obj = 27;
                            break;
                        }
                        break;
                    case -1252916872:
                        if (clickedText.equals("Exam Schedule")) {
                            obj = 7;
                            break;
                        }
                        break;
                    case -1202169118:
                        if (clickedText.equals("My Health Status")) {
                            obj = 16;
                            break;
                        }
                        break;
                    case -1072262802:
                        if (clickedText.equals("Staff Payrolls")) {
                            obj = 25;
                            break;
                        }
                        break;
                    case -877724890:
                        if (clickedText.equals("Assignments")) {
//                            obj = null;
                            break;

                        }
                        break;
                    case -858405955:
                        if (clickedText.equals("Principal Statement")) {
                            obj = 21;
                            break;
                        }
                        break;
                    case -700942960:
                        if (clickedText.equals("My Leaves")) {
                            obj = 4;
                            break;
                        }
                        break;
                    case -455068195:
                        if (clickedText.equals("My Attendance")) {
                            obj = 22;
                            break;
                        }
                        break;
                    case -404111607:
                        if (clickedText.equals("Attendance")) {
                            obj = 24;
                            break;
                        }
                        break;
                    case -250228695:
                        if (clickedText.equals("Staff Profile")) {
                            obj = 26;
                            break;
                        }
                        break;
                    case -173394355:
                        if (clickedText.equals("Post Assignment")) {
                            obj = 1;
                            break;
                        }
                        break;
                    case 74113830:
                        if (clickedText.equals("Marks")) {
                            obj = 3;
                            break;
                        }
                        break;
                    case 205327465:
                        if (clickedText.equals("Staff Attendance")) {
                            obj = 23;
                            break;
                        }
                        break;
                    case 288879783:
                        if (clickedText.equals("Post Material")) {
                            obj = 9;
                            break;
                        }
                        break;
                    case 544837967:
                        if (clickedText.equals("Student's Fee Details")) {
                            obj = 12;
                            break;
                        }
                        break;
                    case 588763576:
                        if (clickedText.equals("Student's Mark List")) {
                            obj = 2;
                            break;
                        }
                        break;
                    case 722088502:
                        if (clickedText.equals("Holiday List")) {
                            obj = 15;
                            break;
                        }
                        break;
                    case 1387885416:
                        if (clickedText.equals("Fee Details")) {
                            obj = 13;
                            break;
                        }
                        break;
                    case 1684106452:
                        if (clickedText.equals("Event List")) {
                            obj = 20;
                            break;
                        }
                        break;
                    case 1830861979:
                        if (clickedText.equals("Library")) {
                            obj = 29;
                            break;
                        }
                        break;
                    case 1953465797:
                        if (clickedText.equals("Add Photo Gallery")) {
                            obj = 10;
                            break;
                        }
                        break;
                }
                switch (obj) {
                    case 1:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), AssignmentsTeacherActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 2:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), MarksListActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 3:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), MarksListActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 4:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), StudentLeavesActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 5:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), ManageLeavesActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 6:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), PendingLeaves_HM_Activity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 7:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), ExamScheduleActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 8:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), OnlineMaterialActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 9:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), PostMaterialActivity.class));
                        AnimationSlideUtil.activityZoom(Home_Fragment.this.getActivity());
                        return;
                    case 10:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), PhotoGalleryActivity.class));
                        AnimationSlideUtil.activityZoom(Home_Fragment.this.getActivity());
                        return;
                    case 11:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), PhotoGalleryActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 12:
                    case 13:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), FeeDetailsActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 14:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), BusesListActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 15:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), HealthStatusActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 16:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), MyHealthStatusActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 17:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), RemarksActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 18:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), PostRemarkActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 19:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), AppointmentsActivity.class));
                        AnimationSlideUtil.activityZoom(Home_Fragment.this.getActivity());
                        return;
                    case 20:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), AppointmentsAdminActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 21:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), PrincipalMessageActivity.class));
                        AnimationSlideUtil.activityZoom(Home_Fragment.this.getActivity());
                        return;
                    case 22:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), StudentAttendanceActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 23:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), StaffAttendanceActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 24:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), AttendanceActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 25:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), StaffPayRollActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 26:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), StaffProfileActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 27:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), StudentProfileActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 28:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), ProfitLossActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    case 29:
                        Home_Fragment.this.startActivity(new Intent(Home_Fragment.this.getActivity(), LibraryActivity.class));
                        Home_Fragment.this.getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        GridView gridView = (GridView) this.view.findViewById(R.id.gridview_home);
        if (HomeActivity.titles != null) {
            gridView.setAdapter(new GridViewAdapter(getActivity(), HomeActivity.titles, HomeActivity.images, R.layout.item_layout_home));
        }
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);
        set.addAnimation(animation);
        animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
        animation.setDuration(300);
        set.addAnimation(animation);
        gridView.setLayoutAnimation(new LayoutAnimationController(set, 0.5f));
        gridView.setOnItemClickListener(new C13461());
        return this.view;
    }
}
