package com.apps.smartschoolmanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import com.apps.smartschoolmanagement.R;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    /* renamed from: com.apps.smartschoolmanagement.activities.SplashActivity$1 */
    class C12891 implements Runnable {
        C12891() {
        }

        public void run() {
            SplashActivity.this.findViewById(R.id.image).setAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.zoomin));
            SplashActivity.this.findViewById(R.id.image).setVisibility(0);
        }
    }

    /* renamed from: com.apps.smartschoolmanagement.activities.SplashActivity$2 */
    class C12902 implements Runnable {
        C12902() {
        }

        public void run() {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, StartActivity.class));
            SplashActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViewById(R.id.layout).setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        new Handler().postDelayed(new C12891(), 500);
        new Handler().postDelayed(new C12902(), (long) SPLASH_TIME_OUT);
    }
}
