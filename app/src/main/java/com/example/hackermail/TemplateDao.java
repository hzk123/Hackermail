package com.example.hackermail;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TemplateDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Template template);

    @Update
    void update(Template... templates);

    @Delete
    void deleteTemplate(Template template);

    @Query("DELETE FROM template_table")
    void deleteAll();

    @Query("SELECT * from template_table LIMIT 1")
    Template[] getAnyTemplate();


    @Query("SELECT * from template_table ORDER BY id ASC")
    LiveData<List<Template>> getAllTemplates();
}
