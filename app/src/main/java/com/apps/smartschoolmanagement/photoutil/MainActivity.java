package com.apps.smartschoolmanagement.photoutil;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.View.OnClickListener;

import com.apps.smartschoolmanagement.R;

public class MainActivity extends AppCompatActivity {

    /* renamed from: com.kosalgeek.android.photoutil.MainActivity$1 */
    class C08191 implements OnClickListener {
        C08191() {
        }

        public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", 0).setAction("Action", null).show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new C08191());
    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
