package com.example.bookblog;

import androidx.annotation.InspectableProperty;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bookblog.Graphql.AddPost;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;

public class AddPostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView uploadImage;
    private Button chooseFile , submitPost;
    private TextInputEditText bookName , description;
    private Uri imageUri = null;
    private static final String TAG = "AddPostActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        bookName = findViewById(R.id.bookName);
        description = findViewById(R.id.description);
        chooseFile = findViewById(R.id.chooseFile);
        submitPost = findViewById(R.id.submitPost);
        uploadImage = findViewById(R.id.imageUpload);

        chooseFile.setOnClickListener(v -> {
            onSelectFile();
        });
        submitPost.setOnClickListener(v->{
            onSubmit();
        });
    }

    private void onSubmit(){
        String mimeType = getContentResolver().getType(imageUri);

        InputStream inputStream = null;
        try {
             inputStream = getContentResolver().openInputStream(imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


      Date date = new Date();
        String intermediate = bookName.getText().toString()+date.toString().substring(0,10);
      String imageTitle = "images/"+intermediate.replace(' ','-')+String.valueOf(Math.random()*100000).substring(0,5);
      Log.d(TAG, "onSubmit: "+imageTitle);

      AddPost post = new AddPost(this , bookName.getText().toString() , description.getText().toString());
      post.createPost(inputStream ,imageTitle, mimeType);
    }


    private void onSelectFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData() != null){
             imageUri = data.getData();
             uploadImage.setVisibility(View.VISIBLE);
             uploadImage.setImageURI(imageUri);
        }
    }



}