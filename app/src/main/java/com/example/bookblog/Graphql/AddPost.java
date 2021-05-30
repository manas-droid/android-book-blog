package com.example.bookblog.Graphql;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


import apolloSchema.CreatePostMutation;
import apolloSchema.S3SignatureMutation;
public class AddPost {
    ApolloClient client;
    private static final String TAG = "AddPost";
    private Context context;
    private String bookName , description;
    public AddPost(Context context , String bookName , String description){
        client = ApolloInstance.getInstance();
        this.context = context;

        this.bookName = bookName;
        this.description = description;
    }



    public void createPost(InputStream inputStream , String fileName , String fileType){

        this.client.mutate(S3SignatureMutation.builder()
        .filename(fileName).filetype(fileType).build())
        .enqueue(new ApolloCall.Callback<S3SignatureMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<S3SignatureMutation.Data> response) {
                if(!response.hasErrors()){
                    Log.d(TAG, "onResponse: url "+response.getData().s3Signature().url() + " signed signature: "+response.getData().s3Signature().signedRequest());
                    addImageToS3(response.getData().s3Signature().url() , response.getData().s3Signature().signedRequest() , inputStream , fileType);
                }else{
                    for( Error error : response.getErrors()){
                        Log.d(TAG, "onResponse: "+error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.e(TAG, "onFailure: ",e);
            }
        });
    }

    public void addImageToS3(String url , String signedRequest , InputStream inputStream , String fileType){
        OutputStream out = null;
        try {
            URL link = new URL(signedRequest);
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", fileType);
            out = connection.getOutputStream();
            byte [] inputData    =   getBytes(inputStream);
            out.write(inputData);
            addPostToDatabase(url);

            Log.d(TAG, "addImageToS3: "+ Arrays.toString(inputData));
            Log.d(TAG, "addImageToS3 "+connection.getResponseCode()+" "+connection.getResponseMessage());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if(out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    private void addPostToDatabase(String imageUrl){
        this.client.mutate(CreatePostMutation.builder()
                .bookname(this.bookName)
                .description(this.description)
                .imageUrl(imageUrl).build())
                .enqueue(new ApolloCall.Callback<CreatePostMutation.Data>() {

                    @Override
                    public void onResponse(@NotNull Response<CreatePostMutation.Data> response) {
                            if(!response.hasErrors()){
                                Log.d(TAG, "onResponse: "+response.getData().createPost().ok());
                                Log.d(TAG, "onResponse: "+response.getData().createPost().errors());
                            }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
    }
}
