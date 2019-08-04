package com.apps.smartschoolmanagement.photoutil;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class GalleryMultiPhoto {
    final String TAG = getClass().getSimpleName();
    private ClipData clipData;
    private Context context;
    private Uri uri;

    public void setPhotoData(ClipData clipData, Uri uri) {
        this.clipData = clipData;
        this.uri = uri;
    }

    public GalleryMultiPhoto(Context context) {
        this.context = context;
    }

    public Intent openMultiPhotoGalleryIntent() throws IllegalAccessException {
        if (VERSION.SDK_INT >= 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
            intent.setAction("android.intent.action.GET_CONTENT");
            return Intent.createChooser(intent, getChooserTitle());
        }
        throw new IllegalAccessException("This feature of multiple images selection is only available for KITKAT (API 19) or above.");
    }

    public String getChooserTitle() {
        return "Select Pictures";
    }

    public List<String> getPhotoPathList() throws IllegalAccessException {
        List<String> pathList = new ArrayList();
        if (VERSION.SDK_INT >= 19) {
            if (this.clipData != null) {
                Log.d(this.TAG, "clipData");
                for (int i = 0; i < this.clipData.getItemCount(); i++) {
                    pathList.add(RealPathUtil.getRealPathFromURI_API19(this.context, this.clipData.getItemAt(i).getUri()));
                }
            } else {
                Log.d(this.TAG, "Uri");
                pathList.add(RealPathUtil.getRealPathFromURI_API19(this.context, this.uri));
            }
            return pathList;
        }
        throw new IllegalAccessException("This feature of multiple images selection is only available for KITKAT (API 19) or above.");
    }
}
