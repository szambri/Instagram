package com.example.instagram;

import android.app.Application;

import com.example.instagram.models.Comment;
import com.example.instagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Comment.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("sz-instagram")
                .clientKey("thisisthemasterkey")
                .server("http://sophiazambri-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }



}
