package com.apps.smartschoolmanagement.photoutil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;

public class GalleryPhoto {
    final String TAG = getClass().getSimpleName();
    private Context context;
    private Uri photoUri;

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public GalleryPhoto(Context context) {
        this.context = context;
    }

    public Intent openGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        return Intent.createChooser(intent, getChooserTitle());
    }

    public String getChooserTitle() {
        return "Select Pictures";
    }

    public String getPath() {
        if (VERSION.SDK_INT < 11) {
            return RealPathUtil.getRealPathFromURI_BelowAPI11(this.context, this.photoUri);
        }
        if (VERSION.SDK_INT < 19) {
            return RealPathUtil.getRealPathFromURI_API11to18(this.context, this.photoUri);
        }
        return RealPathUtil.getRealPathFromURI_API19(this.context, this.photoUri);
    }
}
