package com.apps.smartschoolmanagement.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

public class OnClickDelayListener implements OnClickListener {
    Context mContext;
    Intent mIntent;

    /* renamed from: com.apps.smartschoolmanagement.utils.OnClickDelayListener$1 */
    class C14461 implements Runnable {
        C14461() {
        }

        public void run() {
            OnClickDelayListener.this.mContext.startActivity(OnClickDelayListener.this.mIntent);
        }
    }

    public OnClickDelayListener(Context context, Intent intent) {
        this.mContext = context;
        this.mIntent = intent;
    }

    public void onClick(View view) {
        new Handler().postDelayed(new C14461(), 150);
    }
}
