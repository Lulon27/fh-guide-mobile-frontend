package de.fhguide.ui.timetables;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimetablesViewModel extends ViewModel
{
    private MutableLiveData<String> mText;

    public TimetablesViewModel()
    {
        mText = new MutableLiveData<>();
        mText.setValue("This is the timetables fragment");
    }

    public LiveData<String> getText()
    {
        return mText;
    }
}