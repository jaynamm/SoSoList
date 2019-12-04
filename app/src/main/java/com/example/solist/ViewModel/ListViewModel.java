package com.example.solist.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.solist.Database.ListDatabase;
import com.example.solist.Database.ListRepository;
import com.example.solist.Database.ListVO;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private static final String TAG = "ListViewModel";
    private ListRepository repository;
    private LiveData<List<ListVO>> allLists;
    private LiveData<List<ListVO>> allUnfinishedLists;
    private LiveData<List<ListVO>> allListsForDate;

    private String selectedDate;

    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = new ListRepository(application);
        allLists = repository.getAllLists();
        allUnfinishedLists = repository.getUnfinishedData();
        allListsForDate = repository.getListsForDate();
    }

    public void insert(ListVO listVO) {
        repository.insert(listVO);
    }

    public void update(ListVO listVO) {
        repository.update(listVO);
    }

    public void delete(ListVO listVO) {
        repository.delete(listVO);
    }

    public void deleteAll(ListVO listVO) {
        repository.deleteAllLists();
    }

    public LiveData<List<ListVO>> getAllLists() {
        return allLists;
    }

    public LiveData<List<ListVO>> getUnfinishedData() {
        return allUnfinishedLists;
    }

    public LiveData<List<ListVO>> getAllListsForDate() {
        Log.d(TAG, "getAllListsForDate: " + allListsForDate);
        return allListsForDate;
    }

    // 선택한 날짜 가져오기
    public void setDate(String selectedDate) {
        Log.d(TAG, "setDate: " + selectedDate);
        this.selectedDate = selectedDate;
        repository.setSelectedDate(selectedDate);
    }

}
