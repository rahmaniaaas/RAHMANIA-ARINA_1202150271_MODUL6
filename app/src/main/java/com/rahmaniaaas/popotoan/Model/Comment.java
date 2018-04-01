package com.rahmaniaaas.popotoan.Model;

import java.io.Serializable;

/**
 * Created by USER on 01/04/2018.
 */



public class Comment  {

    String id;
    private String username;
    private String comment;
    public Long timestamp;

    public Comment() {
    }

    public Comment(String id, String username, String comment, Long timestamp) {
        this.id=id;
        this.username = username;
        this.comment = comment;
        this.timestamp=timestamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }
}