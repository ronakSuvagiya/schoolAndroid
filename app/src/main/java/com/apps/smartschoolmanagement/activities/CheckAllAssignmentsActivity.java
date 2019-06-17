package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.EditText;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.BaseFinishActivity;
import com.apps.smartschoolmanagement.utils.OnClickDateListener;

public class CheckAllAssignmentsActivity extends BaseFinishActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignments_check_all);
        setTitle("All Assignments");
        EditText date = (EditText) findViewById(R.id.date);
        date.setOnClickListener(new OnClickDateListener(date, this));
    }
}
