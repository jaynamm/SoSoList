package com.example.solist.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import io.realm.Realm;

public class ListViewModel extends ViewModel {
    private Realm mRealm;

    @Override
    protected void onCleared() {
        mRealm.close();
        super.onCleared();
    }

}
