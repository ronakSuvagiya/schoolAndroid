package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;

public class PrincipalStatementActivity extends JsonClass {

    /* renamed from: com.apps.smartschoolmanagement.activities.PrincipalStatementActivity$1 */
    class C12671 implements OnClickListener {

        /* renamed from: com.apps.smartschoolmanagement.activities.PrincipalStatementActivity$1$1 */
        class C12661 implements VolleyCallback {
            C12661() {
            }

            public void onSuccess(String result) {
                Toast.makeText(PrincipalStatementActivity.this, "Posted Successfully", 0).show();
                PrincipalStatementActivity.this.finish();
            }
        }

        C12671() {
        }

        public void onClick(View view) {
            if (TextUtils.isEmpty(((EditText) PrincipalStatementActivity.this.findViewById(R.id.edit_quote)).getText().toString())) {
                ((EditText) PrincipalStatementActivity.this.findViewById(R.id.edit_quote)).setError("Enter Something Here...");
                ((EditText) PrincipalStatementActivity.this.findViewById(R.id.edit_quote)).requestFocus();
                return;
            }
            PrincipalStatementActivity.this.params.clear();
            PrincipalStatementActivity.this.params.put("admin_id", ProfileInfo.getInstance().getLoginData().get("userId"));
            PrincipalStatementActivity.this.params.put("statement", ((EditText) PrincipalStatementActivity.this.findViewById(R.id.edit_quote)).getText().toString());
          //  PrincipalStatementActivity.this.getJsonResponse(URLs.principalstatement_post, PrincipalStatementActivity.this, new C12661());
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_statement);
        findViewById(R.id.btn_submit).setOnClickListener(new C12671());
    }
}
