package com.example.bookblog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class AddPostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView uploadImage;
    private Button chooseFile , submitPost;
    private TextInputEditText bookName , description;
    private Uri imageUri;
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

            Log.d(TAG, "onActivityResult: "+imageUri+" type "+imageUri.getHost()+imageUri.getPath());
            uploadImage.setVisibility(View.VISIBLE);
             uploadImage.setImageURI(imageUri);
        }
    }
}