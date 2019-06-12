package com.example.hackermail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TemplateViewModel extends AndroidViewModel {

    private TemplateRepository mRepository;

    private LiveData<List<Template>> mAllTemplates;

    public TemplateViewModel(Application application) {
        super(application);
        mRepository = new TemplateRepository(application);
        mAllTemplates = mRepository.getmAllTemplates();
    }

    LiveData<List<Template>> getmAllTemplates() {
        return mAllTemplates;
    }

    public void insert(Template Template) { mRepository.insert(Template); }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteWord(Template Template) { mRepository.deleteTemplate(Template); }

    public void updateWord(Template Template) { mRepository.updateTemplate(Template); }
}
