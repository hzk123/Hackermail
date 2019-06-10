package com.example.hackermail;

import android.arch.lifecycle.AndroidViewModel;
import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ClockViewModel extends AndroidViewModel {

    private ClockRepository mRepository;

    private LiveData<List<Clock>> mAllClocks;

    public ClockViewModel(Application application) {
        super(application);
        mRepository = new ClockRepository(application);
        mAllClocks = mRepository.getmAllClocks();
    }

    LiveData<List<Clock>> getmAllClocks() {
        return mAllClocks;
    }

    public void insert(Clock clock) { mRepository.insert(clock); }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteWord(Clock clock) { mRepository.deleteClock(clock); }

    public void updateWord(Clock clock) { mRepository.updateClock(clock); }
}
