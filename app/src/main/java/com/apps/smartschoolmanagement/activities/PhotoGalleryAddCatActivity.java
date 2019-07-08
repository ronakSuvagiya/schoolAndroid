package com.apps.smartschoolmanagement.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.smartschoolmanagement.R;
import com.kaopiz.kprogresshud.KProgressHUD;

public class PhotoGalleryAddCatActivity extends AppCompatActivity {
    private EditText Cat;
    private Button uploadCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery_add_cat);
        setTitle("Add Category");

        Cat = (EditText) findViewById(R.id.edtCat);
        uploadCat = (Button) findViewById(R.id.btnSave);

        uploadCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cat.getText().length() <= 0) {
                    Cat.setError("Add Category");
                    Cat.requestFocus();
                    return;
                } else {
                    Toast.makeText(PhotoGalleryAddCatActivity.this,"save successfully!",Toast.LENGTH_LONG).show();
                }
            }
        });
        }
    }
