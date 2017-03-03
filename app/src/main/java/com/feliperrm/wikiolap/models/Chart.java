package com.feliperrm.wikiolap.models;

import java.util.Date;

/**
 * Created by felip on 02/03/2017.
 */

public class Chart  {

    private static final String CHART_KEY = "CHART";
    private String craator_id;
    private String id;
    private String title;
    private String thumbnail;

    public Chart() {
    }

    public Chart(String craator_id, String title, String thumbnail) {
        this.craator_id = craator_id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.id = CHART_KEY + "_" + craator_id +  "_" + new Date().getTime();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCraator_id() {
        return craator_id;
    }

    public void setCraator_id(String craator_id) {
        this.craator_id = craator_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
