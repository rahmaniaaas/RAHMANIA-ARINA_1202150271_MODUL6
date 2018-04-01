package com.rahmaniaaas.popotoan.Model;

/**
 * Created by USER on 01/04/2018.
 */

public class Post {
    String id;
    private String userID;
    private String username;
    private String titlePost;
    private String post;
    private String imagePost;
    public Long timestamp;


    //Wajib kasih Constructor Kosong
    public Post() {
    }

    public Post(String id, String userID, String username, String mImagePost, String titlePost, String post, Long timestamp) {
        this.id = id;
        this.username = username;
        this.imagePost = mImagePost;
        this.titlePost = titlePost;
        this.post = post;
        this.userID = userID;
        this.timestamp=timestamp;

    }

    public String getImagePost() {
        return imagePost;
    }

    public String getUserID() {
        return userID;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTitlePost() {
        return titlePost;
    }

    public String getPost() {
        return post;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
