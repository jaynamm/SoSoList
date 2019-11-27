package com.example.solist.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.solist.Database.ListRepository;
import com.example.solist.Database.ListVO;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private ListRepository repository;
    private LiveData<List<ListVO>> allLists;

    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = new ListRepository(application);
        allLists = repository.getAllLists();
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
}
