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
    private String description;
    private String thumbnail;
    private String tableId;
    private ArrayList<String> xColumnIds;
    private String yColumnId;
    private String yColumnAlias;
    private AggregationFunctions aggregationFunction;
    private int chartType;
    private transient ChartUpdateInterface updateInterface;
    private boolean drawXLines;
    private boolean drawYLines;
    private String xTitle;
    private String yTitle;
    private DatasetMetadata dataset1;
    private DatasetMetadata dataset2;


    public DatasetMetadata getDataset1() {
        return dataset1;
    }

    public void setDataset1(DatasetMetadata dataset1) {
        this.dataset1 = dataset1;
    }

    public DatasetMetadata getDataset2() {
        return dataset2;
    }

    public void setDataset2(DatasetMetadata dataset2) {
        this.dataset2 = dataset2;
    }

    public String getyColumnAlias() {
        return yColumnAlias;
    }

    public void setyColumnAlias(String yColumnAlias) {
        this.yColumnAlias = yColumnAlias;
        update();
    }

    public String getxTitle() {
        return xTitle;
    }

    public void setxTitle(String xTitle) {
        this.xTitle = xTitle;
        update();
    }

    public String getyTitle() {
        return yTitle;
    }

    public void setyTitle(String yTitle) {
        this.yTitle = yTitle;
        update();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDrawXLines() {
        return drawXLines;
    }

    public void setDrawXLines(boolean drawXLines) {
        this.drawXLines = drawXLines;
        update();
    }

    public boolean isDrawYLines() {
        return drawYLines;
    }

    public void setDrawYLines(boolean drawYLines) {
        this.drawYLines = drawYLines;
        update();
    }

    public ChartMetadata() {
    }

    @Exclude
    public AggregationFunctions getAggregationAsEnum() {
        return aggregationFunction;
    }

    @Exclude
    public void setAggregationFunctionAsEnum(AggregationFunctions aggregationFunction) {
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


    public String getyColumnId() {
        return yColumnId;
    }

    public void setyColumnId(String yColumnId) {
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
    private void update() {
        if (updateInterface != null) {
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

    public String getGroupByString() {
        String returnString = "";
        try {
            StringBuilder builder = new StringBuilder(returnString);
            for (String str : xColumnIds) {
                builder.append(str);
                builder.append(",");
            }
            returnString = builder.toString();
            return returnString.substring(0, returnString.length() - 1);
        } catch (Exception e) {
            return returnString;
        }
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
        dest.writeString(this.description);
        dest.writeString(this.thumbnail);
        dest.writeString(this.tableId);
        dest.writeStringList(this.xColumnIds);
        dest.writeString(this.yColumnId);
        dest.writeString(this.yColumnAlias);
        dest.writeInt(this.aggregationFunction == null ? -1 : this.aggregationFunction.ordinal());
        dest.writeInt(this.chartType);
        dest.writeByte(this.drawXLines ? (byte) 1 : (byte) 0);
        dest.writeByte(this.drawYLines ? (byte) 1 : (byte) 0);
        dest.writeString(this.xTitle);
        dest.writeString(this.yTitle);
        dest.writeParcelable(this.dataset1, flags);
        dest.writeParcelable(this.dataset2, flags);
    }

    protected ChartMetadata(Parcel in) {
        this.creator_id = in.readString();
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.thumbnail = in.readString();
        this.tableId = in.readString();
        this.xColumnIds = in.createStringArrayList();
        this.yColumnId = in.readString();
        this.yColumnAlias = in.readString();
        int tmpAggregationFunction = in.readInt();
        this.aggregationFunction = tmpAggregationFunction == -1 ? null : AggregationFunctions.values()[tmpAggregationFunction];
        this.chartType = in.readInt();
        this.drawXLines = in.readByte() != 0;
        this.drawYLines = in.readByte() != 0;
        this.xTitle = in.readString();
        this.yTitle = in.readString();
        this.dataset1 = in.readParcelable(DatasetMetadata.class.getClassLoader());
        this.dataset2 = in.readParcelable(DatasetMetadata.class.getClassLoader());
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
}
