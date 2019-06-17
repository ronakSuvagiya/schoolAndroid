package com.apps.smartschoolmanagement.utils;

import
        android.content.Context;
import android.graphics.Bitmap;
import androidx.collection.LruCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

public class AppSingleton {
    private static InternetDetector internetDetector;
    private static Context mCtx;
    private static AppSingleton mInstance;
    private ImageLoader mImageLoader;
    static Context newCon;
    private RequestQueue mRequestQueue = getRequestQueue();
    /* renamed from: com.apps.smartschoolmanagement.utils.AppSingleton$1 */
    class C13881 implements ImageCache {
        private final LruCache<String, Bitmap> cache = new LruCache(20);

        C13881() {
        }

        public Bitmap getBitmap(String url) {
            return (Bitmap) this.cache.get(url);
        }

        public void putBitmap(String url, Bitmap bitmap) {
            this.cache.put(url, bitmap);
        }
    }
    private AppSingleton(Context context) {
        mCtx = context;
        internetDetector = new InternetDetector(context);
        this.mImageLoader = new ImageLoader(this.mRequestQueue, new C13881());
    }

    public static synchronized AppSingleton getInstance(Context context) {
        newCon = context;
        AppSingleton appSingleton;
        synchronized (AppSingleton.class) {
            if (mInstance == null) {
                mInstance = new AppSingleton(context);
            }
        }
        return mInstance;
    }

    public static InternetDetector getInternetDetector() {
        return internetDetector;
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(newCon);
        }
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
