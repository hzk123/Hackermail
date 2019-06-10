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
public interface ClockDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Clock clock);

    @Update
    void update(Clock... clocks);

    @Delete
    void deleteClock(Clock clock);

    @Query("DELETE FROM clock_table")
    void deleteAll();

    @Query("SELECT * from clock_table LIMIT 1")
    Clock[] getAnyclock();


    @Query("SELECT * from clock_table ORDER BY id ASC")
    LiveData<List<Clock>> getAllclocks();
}
