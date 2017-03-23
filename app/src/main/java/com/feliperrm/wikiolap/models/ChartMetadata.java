package com.feliperrm.wikiolap.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.feliperrm.wikiolap.enums.AggregationFunctions;
import com.feliperrm.wikiolap.interfaces.ChartUpdateInterface;
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
    private int chartType;
    private transient ChartUpdateInterface updateInterface;

    public ChartMetadata() {
    }

    @Exclude
    public AggregationFunctions getAggregationAsEnum() {
        return aggregationFunction;
    }

    @Exclude
    public void setAggregationFunctionAsEnum(AggregationFunctions aggregationFunction){
        this.aggregationFunction = aggregationFunction;
        update();
    }


    public void setAggregationFunction(String aggregationFunctionString) {
        if (aggregationFunctionString == null) {
            this.aggregationFunction = null;
        } else {
            this.aggregationFunction = AggregationFunctions.getFunctionFromString(aggregationFunctionString);
        }
        update();
    }

    public String getAggregationFunction() {
        if (aggregationFunction == null) {
            return null;
        } else {
            return aggregationFunction.toString();
        }
    }

    public int getChartType() {
        return chartType;
    }

    public void setChartType(int chartType) {
        this.chartType = chartType;
        update();
    }

    public ArrayList<String> getxColumnIds() {
        return xColumnIds;
    }

    public void setxColumnIds(ArrayList<String> xColumnIds) {
        this.xColumnIds = xColumnIds;
        update();
    }

    public String getYColumnId() {
        return yColumnId;
    }

    public void setYColumnId(String yColumnId) {
        this.yColumnId = yColumnId;
        update();
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
        update();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        update();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        update();
    }


    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
        update();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        update();
    }



    @Exclude
    private void update(){
        if(updateInterface!=null){
            updateInterface.onChartUpdated();
        }
    }

    @Exclude
    public ChartUpdateInterface getUpdateInterface() {
        return updateInterface;
    }

    @Exclude
    public void setUpdateInterface(ChartUpdateInterface updateInterface) {
        this.updateInterface = updateInterface;
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
