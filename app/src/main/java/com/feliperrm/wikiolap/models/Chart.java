package com.feliperrm.wikiolap.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by felip on 02/03/2017.
 */

public class Chart implements Parcelable {

    private static final String CHART_KEY = "CHART";
    private String craator_id;
    private String id;
    private String title;
    private String thumbnail;
    private String tableId;

    public Chart() {
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.craator_id);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.thumbnail);
        dest.writeString(this.tableId);
    }

    protected Chart(Parcel in) {
        this.craator_id = in.readString();
        this.id = in.readString();
        this.title = in.readString();
        this.thumbnail = in.readString();
        this.tableId = in.readString();
    }

    public static final Parcelable.Creator<Chart> CREATOR = new Parcelable.Creator<Chart>() {
        @Override
        public Chart createFromParcel(Parcel source) {
            return new Chart(source);
        }

        @Override
        public Chart[] newArray(int size) {
            return new Chart[size];
        }
    };
}
