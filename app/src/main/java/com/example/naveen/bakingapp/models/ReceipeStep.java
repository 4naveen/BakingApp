package com.example.naveen.bakingapp.models;

/**
 * Created by User on 6/3/2017.
 */

public class ReceipeStep {

    String description,video_url,full_description;
    int receipe_id,video_id,receipe_image;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReceipe_id() {
        return receipe_id;
    }

    public void setReceipe_id(int receipe_id) {
        this.receipe_id = receipe_id;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getFull_description() {
        return full_description;
    }

    public void setFull_description(String full_description) {
        this.full_description = full_description;
    }

    public int getReceipe_image() {
        return receipe_image;
    }

    public void setReceipe_image(int receipe_image) {
        this.receipe_image = receipe_image;
    }
}
