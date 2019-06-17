package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.fragments.Admin_Teacher_Leaves;
import com.apps.smartschoolmanagement.utils.JsonClass;

public class PendingLeaves_HM_Activity extends JsonClass {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        getSupportActionBar().setElevation(0.0f);
        setTitle("Teacher Leaves");
        setFragment(new Admin_Teacher_Leaves());
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragments, fragment);
        fragmentTransaction.commit();
    }
}
