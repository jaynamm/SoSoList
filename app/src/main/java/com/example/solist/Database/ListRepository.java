package com.example.solist.Database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ListRepository {

    private static final String TAG = "ListRepository";
    private ListDAO listDAO;
    private LiveData<List<ListVO>> allLists;
    private LiveData<List<ListVO>> allUnfinishedLists;

    public ListRepository(Application application) {
        ListDatabase database = ListDatabase.getInstance(application);
        listDAO = database.listDAO();
        allLists = listDAO.getAll();
        allUnfinishedLists = listDAO.getUnfinishedData();
    }

    public void insert(ListVO listVO) {
        new InsertListAsyncTask(listDAO).execute(listVO);
    }

    public void update(ListVO listVO) {
        new UpdateListAsyncTask(listDAO).execute(listVO);
    }

    public void delete(ListVO listVO) {
        new DeleteListAsyncTask(listDAO).execute(listVO);
    }

    public void deleteAllLists() {
        new DeleteAllListAsyncTask(listDAO).execute();
    }

    public LiveData<List<ListVO>> getAllLists() { return allLists; }

    public LiveData<List<ListVO>> getUnfinishedData() { return allUnfinishedLists; }

    private static class InsertListAsyncTask extends AsyncTask<ListVO, Void, Void> {
        private ListDAO listDAO;

        private InsertListAsyncTask(ListDAO listDAO) {
            this.listDAO = listDAO;
        }

        @Override
        protected Void doInBackground(ListVO... listVOS) {
            listDAO.insert(listVOS[0]);
            return null;
        }
    }

    private static class UpdateListAsyncTask extends AsyncTask<ListVO, Void, Void> {
        private ListDAO listDAO;

        private UpdateListAsyncTask(ListDAO listDAO) {
            this.listDAO = listDAO;
        }

        @Override
        protected Void doInBackground(ListVO... listVOS) {
            listDAO.update(listVOS[0]);
            return null;
        }
    }

    private static class DeleteListAsyncTask extends AsyncTask<ListVO, Void, Void> {
        private ListDAO listDAO;

        private DeleteListAsyncTask(ListDAO listDAO) {
            this.listDAO = listDAO;
        }

        @Override
        protected Void doInBackground(ListVO... listVOS) {
            listDAO.delete(listVOS[0]);
            return null;
        }
    }

    private static class DeleteAllListAsyncTask extends AsyncTask<ListVO, Void, Void> {
        private ListDAO listDAO;

        private DeleteAllListAsyncTask(ListDAO listDAO) {
            this.listDAO = listDAO;
        }

        @Override
        protected Void doInBackground(ListVO... listVOS) {
            listDAO.deleteAllLists();
            return null;
        }
    }
}
