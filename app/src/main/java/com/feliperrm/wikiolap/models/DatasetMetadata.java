package com.feliperrm.wikiolap.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by felip on 23/02/2017.
 */

public class DatasetMetadata implements Serializable {

    String title;
    String description;
    ArrayList<String> aliasColumns;
    String email;
    ArrayList<Hierarchy> hierarchies;
    String source;
    ArrayList<String> originalColumns;
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
}
