package com.apps.smartschoolmanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.adapters.GridAdapter;
import com.apps.smartschoolmanagement.models.UserStaticData;
import com.apps.smartschoolmanagement.utils.JsonClass;
import com.apps.smartschoolmanagement.utils.ProfileInfo;
import java.util.ArrayList;
import java.util.Iterator;

public class PhotoAlbumActivity extends JsonClass {
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    Bitmap bmp = null;
    ArrayList<String> image_paths = new ArrayList();
    int[] images = null;
    String[] titles = null;

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
        ProfileInfo.getInstance().getAlbum_images().clear();
        if (getIntent().getStringExtra("title") != null) {
            setTitle(getIntent().getStringExtra("title"));
        }
        KProgressHUD progressHUD = KProgressHUD.create(this).setLabel("Loding Images..");
        if (getIntent().getStringArrayListExtra("images") != null) {
            this.image_paths = getIntent().getStringArrayListExtra("images");
            if (this.image_paths.size() > 0) {
                progressHUD.show();
                ProfileInfo.getInstance().getAlbum_images().clear();
                ArrayList<String> nonDupList = new ArrayList();
                Iterator<String> dupIter = this.image_paths.iterator();
                while (dupIter.hasNext()) {
                    String path = (String) dupIter.next();
                    if (nonDupList.contains(path)) {
                        dupIter.remove();
                    } else {
                        nonDupList.add(path);
                    }
                }
                this.image_paths = nonDupList;
            }
        }
        progressHUD.dismiss();
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new GridAdapter((Context) this, (int) R.layout.item_layout_photo_album, this.image_paths));
        gridView.setOnItemClickListener(new C12501());
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
