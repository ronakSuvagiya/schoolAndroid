package com.apps.smartschoolmanagement.activities;

import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.apps.smartschoolmanagement.R;

public class NetworkDialogActivity_ViewBinding implements Unbinder {
    private NetworkDialogActivity target;
    private View view2131361862;
    private View view2131361863;

    @UiThread
    public NetworkDialogActivity_ViewBinding(NetworkDialogActivity target) {
        this(target, target.getWindow().getDecorView());
    }

    @UiThread
    public NetworkDialogActivity_ViewBinding(final NetworkDialogActivity target, View source) {
        this.target = target;
        target._message = (TextView) Utils.findRequiredViewAsType(source, R.id.message, "field '_message'", TextView.class);
        target._button = (Button) Utils.findRequiredViewAsType(source, R.id.button, "field '_button'", Button.class);
        View view = Utils.findRequiredView(source, R.id.btn_connect, "method 'onConnectClick'");
        this.view2131361863 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onConnectClick();
            }
        });
        view = Utils.findRequiredView(source, R.id.btn_close, "method 'onCloseClick'");
        this.view2131361862 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onCloseClick();
            }
        });
    }

    @CallSuper
    public void unbind() {
        NetworkDialogActivity target = this.target;
        if (target == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target._message = null;
        target._button = null;
        this.view2131361863.setOnClickListener(null);
        this.view2131361863 = null;
        this.view2131361862.setOnClickListener(null);
        this.view2131361862 = null;
    }
}
