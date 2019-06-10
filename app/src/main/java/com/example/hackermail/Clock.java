package com.example.hackermail;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.io.Serializable;


@Entity(tableName = "clock_table")
public class Clock implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "ToEmail")
    private String toemail;

    @NonNull
    @ColumnInfo(name = "Time")
    private String time;

    @NonNull
    @ColumnInfo(name = "Content")
    private String content;

    public Clock() {
    }

    @Ignore
    public Clock(@NonNull String mToemail, @NonNull String mTime, @NonNull String mContent) {
        this.toemail = mToemail;
        this.time = mTime;
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
    public String getToemail() {
        return toemail;
    }
    public void setToemail(@NonNull String toemail) {
        this.toemail = toemail;
    }

    @NonNull
    public String getTime() { return time; }
    public void setTime(@NonNull String time) {
        this.time = time;
    }

    @NonNull
    public String getContent() {
        return content;
    }
    public void setContent(@NonNull String content) {
        this.content = content;
    }
}

