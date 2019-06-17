package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.perusudroid.networkchecker.utils.NetworkUtils;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.MyApplication;

public class NetworkDialogActivity extends AppCompatActivity {
    @BindView(2131361888)
    Button _button;
    @BindView(2131362093)
    TextView _message;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_dialog);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("message"))) {
            this._message.setText(getIntent().getStringExtra("message"));
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("action"))) {
            this._button.setText(getIntent().getStringExtra("action"));
        }
    }

    @OnClick({2131361863})
    void onConnectClick() {
        NetworkUtils.showNetworkSettings(this);
    }

    @OnClick({2131361862})
    void onCloseClick() {
        finish();
    }

    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();
    }

    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
    }
}
