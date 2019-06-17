package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.BaseFinishActivity;

public class ProfitLossActivity extends BaseFinishActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_loss);
        setTitle("Profit & Loss");
    }
}
