package com.apps.smartschoolmanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.URLs;
import org.json.JSONException;
import org.json.JSONObject;

public class ResetPasswordActivity extends JsonClass {
    EditText confirmpswd;
    EditText newpswd;
    EditText otp;

    /* renamed from: com.apps.smartschoolmanagement.activities.ResetPasswordActivity$1 */
    class C12881 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.ResetPasswordActivity$1$1 */
        class C12871 implements VolleyCallback {
            C12871() {
            }

            public void onSuccess(String result) {
                Toast.makeText(ResetPasswordActivity.this, result.toString(), 1).show();
                try {
                    if (new JSONObject(result).getInt(NotificationCompat.CATEGORY_STATUS) == 1) {
                        ResetPasswordActivity.this.startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                        ResetPasswordActivity.this.finishAffinity();
                        return;
                    }
                    Toast.makeText(ResetPasswordActivity.this, result, 0).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        C12881() {
        }

        public void onClick(View view) {
            if (ResetPasswordActivity.this.otp.getText().toString().trim().equalsIgnoreCase("")) {
                ResetPasswordActivity.this.otp.setError("Enter OTP");
                ResetPasswordActivity.this.otp.requestFocus();
            } else if (ResetPasswordActivity.this.newpswd.getText().toString().trim().equalsIgnoreCase("") || ResetPasswordActivity.this.newpswd.getText().toString().length() < 6) {
                ResetPasswordActivity.this.newpswd.setError("Password should be atleast 6 characters length");
                ResetPasswordActivity.this.newpswd.requestFocus();
            } else if (ResetPasswordActivity.this.isPasswordMatched()) {
                ResetPasswordActivity.this.params.put("otp", ResetPasswordActivity.this.otp.getText().toString());
                ResetPasswordActivity.this.params.put("pwd", ResetPasswordActivity.this.newpswd.getText().toString());
                ResetPasswordActivity.this.params.put("confirm_password", ResetPasswordActivity.this.confirmpswd.getText().toString());
            //    ResetPasswordActivity.this.getJsonResponse(URLs.resetpassword, ResetPasswordActivity.this, new C12871());
            } else {
                ResetPasswordActivity.this.confirmpswd.setError("Passwords did not match");
                ResetPasswordActivity.this.confirmpswd.requestFocus();
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        setTitle("Reset Password");
        this.otp = (EditText) findViewById(R.id.otp);
        this.newpswd = (EditText) findViewById(R.id.new_password);
        this.confirmpswd = (EditText) findViewById(R.id.confirm_new_password);
        findViewById(R.id.btn_reset).setOnClickListener(new C12881());
    }

    public boolean isPasswordMatched() {
        if (this.newpswd.getText().toString().trim().equals(this.confirmpswd.getText().toString().trim())) {
            return true;
        }
        return false;
    }
}
