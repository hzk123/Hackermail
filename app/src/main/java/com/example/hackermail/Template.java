package com.example.hackermail;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;


@Entity(tableName = "template_table")
public class Template implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;


    @NonNull
    @ColumnInfo(name = "Content")
    private String content;

    public Template() {
    }

    @Ignore
    public Template(@NonNull String mContent) {
        this.content = mContent;
    }

    @NonNull
    public int getId() {
        return id;
    }
    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getContent() {
        return content;
    }
    public void setContent(@NonNull String content) {
        this.content = content;
    }
}

