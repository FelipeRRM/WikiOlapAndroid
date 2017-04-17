package com.feliperrm.wikiolap.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;

/**
 * Created by felip on 23/02/2017.
 */

public class DatasetMetadata implements Serializable, Parcelable {

    String title;
    String description;
    ArrayList<String> aliasColumns;
    String email;
    ArrayList<Hierarchy> hierarchies;
    String source;
    ArrayList<String> originalColumns;
    ArrayList<String> dbColumns;
    // TODO: 23/02/2017 Verificar qual é o tipo desse array.
    ArrayList<Object> tags;
    TimeStamp created_at;
    TimeStamp updated_at;
    String tableId;

    public TimeStamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(TimeStamp created_at) {
        this.created_at = created_at;
    }

    public TimeStamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(TimeStamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getAliasColumns() {
        return aliasColumns;
    }

    public void setAliasColumns(ArrayList<String> aliasColumns) {
        this.aliasColumns = aliasColumns;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Hierarchy> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(ArrayList<Hierarchy> hierarchies) {
        this.hierarchies = hierarchies;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ArrayList<String> getOriginalColumns() {
        return originalColumns;
    }

    public ArrayList<String> getDbColumns(){
        if(dbColumns == null){
            dbColumns = new ArrayList<>();
            for (String column : originalColumns){
                column = column.toLowerCase();
                column = column.replace(' ', '_');
                column = Normalizer.normalize(column, Normalizer.Form.NFD);
                column = column.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                dbColumns.add(column);
            }
        }
        return dbColumns;
    }

    public void setOriginalColumns(ArrayList<String> originalColumns) {
        this.originalColumns = originalColumns;
    }

    public ArrayList<Object> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Object> tags) {
        this.tags = tags;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeStringList(this.aliasColumns);
        dest.writeString(this.email);
        dest.writeList(this.hierarchies);
        dest.writeString(this.source);
        dest.writeStringList(this.originalColumns);
        dest.writeList(this.tags);
        dest.writeSerializable(this.created_at);
        dest.writeSerializable(this.updated_at);
        dest.writeString(this.tableId);
    }

    public DatasetMetadata() {
    }

    protected DatasetMetadata(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.aliasColumns = in.createStringArrayList();
        this.email = in.readString();
        this.hierarchies = new ArrayList<Hierarchy>();
        in.readList(this.hierarchies, Hierarchy.class.getClassLoader());
        this.source = in.readString();
        this.originalColumns = in.createStringArrayList();
        this.tags = new ArrayList<Object>();
        in.readList(this.tags, Object.class.getClassLoader());
        this.created_at = (TimeStamp) in.readSerializable();
        this.updated_at = (TimeStamp) in.readSerializable();
        this.tableId = in.readString();
    }

    public static final Parcelable.Creator<DatasetMetadata> CREATOR = new Parcelable.Creator<DatasetMetadata>() {
        @Override
        public DatasetMetadata createFromParcel(Parcel source) {
            return new DatasetMetadata(source);
        }

        @Override
        public DatasetMetadata[] newArray(int size) {
            return new DatasetMetadata[size];
        }
    };
}
