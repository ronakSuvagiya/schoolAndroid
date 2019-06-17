package com.apps.smartschoolmanagement.photoutil;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import androidx.loader.content.CursorLoader;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.nileshp.multiphotopicker.photopicker.utils.FileUtils;

class RealPathUtil {
    static final String TAG = "GalleryPhoto";

    RealPathUtil() {
    }

    @SuppressLint({"NewApi"})
    public static String getRealPathFromURI_API19(Context context, Uri photoUri) {
        String path = "";
        if (DocumentsContract.isDocumentUri(context, photoUri)) {
            String[] split = new String[0];
            if (isExternalStorageDocument(photoUri)) {
                split = DocumentsContract.getDocumentId(photoUri).split(":");
                return "primary".equalsIgnoreCase(split[0]) ? Environment.getExternalStorageDirectory() + "/" + split[1] : path;
            } else if (isDownloadsDocument(photoUri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(photoUri)).longValue()), null, null);
            } else if (!isMediaDocument(photoUri)) {
                return path;
            } else {
                String type = DocumentsContract.getDocumentId(photoUri).split(":")[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = Media.EXTERNAL_CONTENT_URI;
                } else if (FileUtils.PARAMETER_SHARE_DIALOG_CONTENT_VIDEO.equals(type)) {
                    contentUri = Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = "_id=?";
                return getDataColumn(context, contentUri, "_id=?", new String[]{
                        split[1]});
            }
        } else if (Param.CONTENT.equalsIgnoreCase(photoUri.getScheme())) {
            return getDataColumn(context, photoUri, null, null);
        } else {
            if ("file".equalsIgnoreCase(photoUri.getScheme())) {
                return photoUri.getPath();
            }
            return path;
        }
    }

    @SuppressLint({"NewApi"})
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        Log.d(TAG, "API 11 to 18");
        Cursor cursor = new CursorLoader(context, contentUri, new String[]{"_data"}, null, null, null).loadInBackground();
        if (cursor == null) {
            return null;
        }
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
        Log.d(TAG, "API Below 11");
        String path = "";
        Cursor cursor = context.getContentResolver().query(contentUri, new String[]{"_data"}, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
            String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
