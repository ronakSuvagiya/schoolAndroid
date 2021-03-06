package com.apps.smartschoolmanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.apps.smartschoolmanagement.adapters.HolidayListAdapter;
import com.apps.smartschoolmanagement.models.ListData;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.GridAdapter;
import com.apps.smartschoolmanagement.models.PhotoAlbum;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import com.apps.smartschoolmanagement.utils.URLs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PhotoGalleryActivity extends JsonClass {
    ArrayList<PhotoAlbum> albums = new ArrayList();
    GridView gridView;
    SharedPreferences sp;
    String channel;
//    int[] imageIds = new int[]{R.drawable.img_workshop, R.drawable.img_independenceday, R.drawable.img_sportsday, R.drawable.img_youthconference, R.drawable.img_fest};
//    int[] imageIds_workshop = new int[]{R.drawable.img_workshop1, R.drawable.img_workshop2, R.drawable.img_workshop3, R.drawable.img_workshop4, R.drawable.img_workshop5, R.drawable.img_workshop6};
//    String[] imageTitles = new String[]{"Workshop", "Independence Day", "Sports Day", "Youth Conference", "Learning Feet"};
//    String[] imageTitles_workshop = new String[]{"Workshop1", "Workshop2", "Workshop3", "Workshop4", "Workshop5"};
    FloatingActionButton faAddCat,faAddImg;
    FloatingActionMenu action_menu;
    /* renamed from: com.apps.smartschoolmanagement.activities.PhotoGalleryActivity$1 */
//    class C12511 implements VolleyCallback {
//        C12511() {
//        }

//        public void onSuccess(String result) {
//            try {
//                PhotoGalleryActivity.this.processJSONResult(new JSONObject(result));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /* renamed from: com.apps.smartschoolmanagement.activities.PhotoGalleryActivity$2 */
    class C12522 implements OnItemClickListener {
        C12522() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            ArrayList<String> stringList = new ArrayList(Arrays.asList(((PhotoAlbum) PhotoGalleryActivity.this.albums.get(i)).getImagePaths()));
//            Collections.sort(stringList);
            Intent intent = new Intent(PhotoGalleryActivity.this, PhotoAlbumActivity.class);
            intent.putExtra("catKd", albums.get(i).getCatID());
            intent.putExtra("title", ((PhotoAlbum) PhotoGalleryActivity.this.albums.get(i)).getTitle());
//            ProfileInfo.getInstance().getAlbum_images().clear();
            PhotoGalleryActivity.this.startActivity(intent);
            PhotoGalleryActivity.this.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (UserStaticData.user_type == 0) {
            setTheme(R.style.AppTheme1);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        setTitle("Photo Gallery");
        KProgressHUD progressHUD = KProgressHUD.create(this).setLabel("Loading Images..");
        this.gridView = (GridView) findViewById(R.id.gridview);
        // getJsonResponse(URLs.gallery, this, new C12511());
        faAddImg = findViewById(R.id.faAddImg);
        faAddCat = findViewById(R.id.faAddCat);
        action_menu = findViewById(R.id.action_menu);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        channel = (sp.getString("schoolid", ""));
        String usertype = sp.getString("usertype","");
        if(usertype.equals("student"))
        {
          action_menu.setVisibility(View.GONE);
        }
        getJsonResponse(URLs.getImgCategory + channel , this, new PhotoGalleryActivity.getImage());

        faAddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoGalleryActivity.this,PhotoGalleryAddCatActivity.class);
                startActivity(intent);
            }
        });

        faAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoGalleryActivity.this,PhotoGalleryAddActivity.class);
                startActivity(intent);
            }
        });
        this.gridView.setOnItemClickListener(new C12522());
//        PhotoAlbum listData = new PhotoAlbum();
//        listData.setTitle(String.valueOf(imageTitles));
//        listData.setImagePaths(String.valueOf(imageIds));
//        this.albums.add(listData);
//        this.gridView.setAdapter(new GridAdapter((Context) this, this.albums, (int) R.layout.item_layout_photo_gallery));
    }

//    public void processJSONResult(JSONObject jsonObject) {
//        try {
//            this.albums.clear();
//            JSONArray jsonArray = jsonObject.getJSONArray("Response");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                PhotoAlbum listData = new PhotoAlbum();
//                if (!jsonObject1.isNull("album")) {
//                    listData.setTitle(jsonObject1.getString("album"));
//                }
//                if (!jsonObject1.isNull("images")) {
//                    listData.setImagePaths(jsonObject1.getString("images"));
//                    listData.setPathList(listData.getImagePaths().split(","));
//                }
//                this.albums.add(listData);
//            }
//            this.gridView.setAdapter(new GridAdapter((Context) this, this.albums, (int) R.layout.item_layout_photo_gallery));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    class getImage implements JsonClass.VolleyCallbackJSONArray {
        @Override
        public void onSuccess(JSONArray jsonArray) {
            for (int i = 0; i< jsonArray.length(); i++) {
                try {
                    PhotoAlbum listData = new PhotoAlbum();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                    String img= jsonObject1.getString("image_name");
//                        String images = "http://quickedu.co.in/image/" + img;
                    listData.setImagePaths("");
                    listData.setCatID(jsonObject1.getString("id"));
                    listData.setTitle(jsonObject1.getString("name"));
                    albums.add(listData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            gridView.setAdapter(new GridAdapter(PhotoGalleryActivity.this, albums, (int) R.layout.item_layout_photo_gallery));

        }
    }
}
