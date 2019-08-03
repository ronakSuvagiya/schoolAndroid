package com.apps.smartschoolmanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.apps.smartschoolmanagement.adapters.ImgGridAdapter;
import com.apps.smartschoolmanagement.models.PhotoAlbum;
import com.apps.smartschoolmanagement.utils.URLs;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.GridAdapter;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.ProfileInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class PhotoAlbumActivity extends JsonClass {
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    Bitmap bmp = null;
    ArrayList<String> image_paths = new ArrayList();
    int[] images = null;
    String[] titles = null;
    SharedPreferences sp;
    String channel,CatID;
    GridView gridView;
    /* renamed from: com.apps.smartschoolmanagement.activities.PhotoAlbumActivity$1 */
    class C12501 implements OnItemClickListener {
        C12501() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            PhotoAlbumActivity.this.presentActivity(view, position, PhotoAlbumActivity.this.image_paths);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        CatID = getIntent().getStringExtra("catKd");
//        ProfileInfo.getInstance().getAlbum_images().clear();
        if (getIntent().getStringExtra("title") != null) {
            setTitle(getIntent().getStringExtra("title"));
        }


        sp = PreferenceManager.getDefaultSharedPreferences(PhotoAlbumActivity.this);
        channel = (sp.getString("schoolid", ""));

        getJsonResponse(URLs.getImg + channel , this, new PhotoAlbumActivity.getImage());

        KProgressHUD progressHUD = KProgressHUD.create(this).setLabel("Loding Images..");

//        if (getIntent().getStringExtra("images") != null) {
//            this.image_paths = getIntent().getStringArrayListExtra("images");
//            if (this.image_paths.size() > 0) {
//                progressHUD.show();
//                ProfileInfo.getInstance().getAlbum_images().clear();
//                ArrayList<String> nonDupList = new ArrayList();
//                Iterator<String> dupIter = this.image_paths.iterator();
//                while (dupIter.hasNext()) {
//                    String path = (String) dupIter.next();
//                    if (nonDupList.contains(path)) {
//                        dupIter.remove();
//                    } else {
//                        nonDupList.add(path);
//                    }
//                }
//                this.image_paths = nonDupList;
//            }
//        }
        progressHUD.dismiss();
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new C12501());
    }

    class getImage implements JsonClass.VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++)
                try {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String img = jsonObject1.getString("image_name");
                    String images = "http://quickedu.co.in/image/" + img;
                    String cat = jsonObject1.getString("cat");
                    JSONObject catitem = new JSONObject(cat);
                    String id = catitem.getString("id");
                    if (id.equals(CatID)) {
                    image_paths.add(images);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            gridView.setAdapter(new ImgGridAdapter(PhotoAlbumActivity.this, (int) R.layout.item_layout_photo_album, image_paths));
        }
    }

    public void presentActivity(View view, int position, ArrayList<String> paths) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + ((float) (view.getWidth() / 2)));
        int revealY = (int) (view.getY() + ((float) (view.getHeight() / 2)));
        Intent intent = new Intent(this, ViewPagerActivity.class);
        intent.putExtra("EXTRA_CIRCULAR_REVEAL_X", revealX);
        intent.putExtra("EXTRA_CIRCULAR_REVEAL_Y", revealY);
        intent.putExtra("id", position);
        intent.putExtra("paths", paths);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
