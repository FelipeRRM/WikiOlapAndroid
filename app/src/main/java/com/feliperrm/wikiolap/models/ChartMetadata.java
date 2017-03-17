package com.feliperrm.wikiolap.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.feliperrm.wikiolap.enums.AggregationFunctions;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;

/**
 * Created by felip on 02/03/2017.
 */

public class ChartMetadata implements Parcelable {

    private static final String CHART_KEY = "CHART";
    private String creator_id;
    private String id;
    private String title;
    private String thumbnail;
    private String tableId;
    private ArrayList<String> xColumnIds;
    private String yColumnId;
    private AggregationFunctions aggregationFunction;

    public ChartMetadata() {
    }

    @Exclude
    public AggregationFunctions getAggregationAsEnum() {
        return aggregationFunction;
    }


    public void setAggregationFunction(String aggregationFunctionString) {
        if (aggregationFunctionString == null) {
            this.aggregationFunction = null;
        } else {
            this.aggregationFunction = AggregationFunctions.getFunctionFromString(aggregationFunctionString);
        }
    }

    public String getAggregationFunction() {
        if (aggregationFunction == null) {
            return null;
        } else {
            return aggregationFunction.toString();
        }
    }

    public ArrayList<String> getxColumnIds() {
        return xColumnIds;
    }

    public String getYColumnId() {
        return yColumnId;
    }

    public void setYColumnId(String yColumnId) {
        this.yColumnId = yColumnId;
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


    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
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
        dest.writeString(this.creator_id);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.thumbnail);
        dest.writeString(this.tableId);
        dest.writeStringList(this.xColumnIds);
        dest.writeString(this.yColumnId);
        dest.writeInt(this.aggregationFunction == null ? -1 : this.aggregationFunction.ordinal());
    }

    protected ChartMetadata(Parcel in) {
        this.creator_id = in.readString();
        this.id = in.readString();
        this.title = in.readString();
        this.thumbnail = in.readString();
        this.tableId = in.readString();
        this.xColumnIds = in.createStringArrayList();
        this.yColumnId = in.readString();
        int tmpAggregationFunction = in.readInt();
        this.aggregationFunction = tmpAggregationFunction == -1 ? null : AggregationFunctions.values()[tmpAggregationFunction];
    }

    public static final Creator<ChartMetadata> CREATOR = new Creator<ChartMetadata>() {
        @Override
        public ChartMetadata createFromParcel(Parcel source) {
            return new ChartMetadata(source);
        }

        @Override
        public ChartMetadata[] newArray(int size) {
            return new ChartMetadata[size];
        }
    };

    public String getGroupByString() {
        String returnString = "";
        try{
            StringBuilder builder = new StringBuilder(returnString);
            for(String str : xColumnIds){
                builder.append(str);
                builder.append(",");
            }
            returnString = builder.toString();
            return returnString.substring(0, returnString.length()-1);
        }
        catch (Exception e){
            return returnString;
        }
    }
}
