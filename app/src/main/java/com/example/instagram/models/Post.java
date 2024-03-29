package com.example.instagram.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("Post")
public class Post extends ParseObject {

    public Post() {}

    public static final String KEY_CAPTION ="caption";
    public static final String KEY_IMAGE ="photoFile";
    public static final String KEY_USER ="user";
    public static final String KEY_LIKED = "liked";
    public static final String KEY_LIKES = "likes";

    public String getCaption() {
        return getString(KEY_CAPTION);
    }

    public void setCaption(String description) {
        put(KEY_CAPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }

    public boolean getLiked() {
        return getBoolean(KEY_LIKED);
    }

    public void setLiked(boolean liked) {
        put(KEY_LIKED, liked);
    }

    public Number getLikes() {
        return getNumber(KEY_LIKES);
    }

    public void setLikes(Number number) {
        put(KEY_LIKES, number);
    }

}
