package com.apps.smartschoolmanagement.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
//import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.apps.smartschoolmanagement.R;

public class Login_GooglePlus extends AppCompatActivity implements OnConnectionFailedListener, OnClickListener {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private TextView mStatusTextView;

    /* renamed from: com.apps.smartschoolmanagement.activities.Login_GooglePlus$1 */
//    class C12261 implements ResultCallback<GoogleSignInResult> {
//        C12261() {
//        }

//        public void onResult(GoogleSignInResult googleSignInResult) {
//            Login_GooglePlus.this.hideProgressDialog();
//            Login_GooglePlus.this.handleSignInResult(googleSignInResult);
//        }
//    }

    /* renamed from: com.apps.smartschoolmanagement.activities.Login_GooglePlus$2 */
//    class C12272 implements ResultCallback<Status> {
//        C12272() {
//        }

//        public void onResult(Status status) {
//            Login_GooglePlus.this.updateUI(false);
//        }
//    }

    /* renamed from: com.apps.smartschoolmanagement.activities.Login_GooglePlus$3 */
//    class C12283 implements ResultCallback<Status> {
//        C12283() {
//        }

//        public void onResult(Status status) {
//            Login_GooglePlus.this.updateUI(false);
//        }
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);
        this.mStatusTextView = (TextView) findViewById(R.id.status);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);
        findViewById(R.id.btn_optional_action).setOnClickListener(this);
//        this.mGoogleApiClient = new Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()).build();
        ((SignInButton) findViewById(R.id.sign_in_button)).setSize(0);
    }

    public void onStart() {
        super.onStart();
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(this.mGoogleApiClient);
//        if (opr.isDone()) {
//            Log.d(TAG, "Got cached sign-in");
//            handleSignInResult((GoogleSignInResult) opr.get());
//            return;
//        }
//        showProgressDialog();
//        opr.setResultCallback(new C12261());
    }

    protected void onResume() {
        super.onResume();
        hideProgressDialog();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
//            handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

//    private void handleSignInResult(GoogleSignInResult result) {
//        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
//        if (result.isSuccess()) {
//            Toast.makeText(this, "Connected Successfully", 0).show();
//            findViewById(R.id.btn_optional_action).setVisibility(0);
//            GoogleSignInAccount acct = result.getSignInAccount();
//            this.mStatusTextView.setText(getString(R.string.signed_in_fmt, new Object[]{acct.getDisplayName()}));
//            updateUI(true);
//            return;
//        }
//        updateUI(false);
//    }

    private void signIn() {
//        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.mGoogleApiClient), RC_SIGN_IN);
    }

    private void signOut() {
//        Auth.GoogleSignInApi.signOut(this.mGoogleApiClient).setResultCallback(new C12272());
    }

    private void revokeAccess() {
//        Auth.GoogleSignInApi.revokeAccess(this.mGoogleApiClient).setResultCallback(new C12283());
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    protected void onStop() {
        super.onStop();
        if (this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (this.mProgressDialog == null) {
            this.mProgressDialog = new ProgressDialog(this);
            this.mProgressDialog.setMessage(getString(R.string.loading));
            this.mProgressDialog.setIndeterminate(true);
        }
        this.mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(8);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(0);
            return;
        }
        this.mStatusTextView.setText(R.string.signed_out);
        findViewById(R.id.btn_optional_action).setVisibility(8);
        findViewById(R.id.sign_in_button).setVisibility(0);
        findViewById(R.id.sign_out_and_disconnect).setVisibility(8);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_optional_action:
                startActivity(new Intent(this, WebViewActivity.class));
                finish();
                return;
            case R.id.disconnect_button:
                revokeAccess();
                return;
            case R.id.sign_in_button:
                signIn();
                return;
            case R.id.sign_out_button:
                signOut();
                return;
            default:
                return;
        }
    }
}
