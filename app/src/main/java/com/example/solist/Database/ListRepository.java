package com.example.solist.Database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.solist.ViewModel.ListViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ListRepository {

    private static final String TAG = "ListRepository";
    private ListDAO listDAO;
    private LiveData<List<ListVO>> allLists;
    private LiveData<List<ListVO>> allUnfinishedLists;

    private MutableLiveData<String> getDate = new MutableLiveData<>();

    private LiveData<List<ListVO>> getListForDate = Transformations.switchMap(getDate, date ->
            listDAO.getListsForDate(date));

    public ListRepository(Application application) {
        ListDatabase database = ListDatabase.getInstance(application);
        listDAO = database.listDAO();
        allLists = listDAO.getAll();
        allUnfinishedLists = listDAO.getUnfinishedData(getInputDate());
    }

    public void setSelectedDate(String selectedDate) {
        Log.d(TAG, "setSelectedDate: date : " + selectedDate);
        getDate.setValue(selectedDate);
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

    public LiveData<List<ListVO>> getAllLists() {
        return allLists;
    }

    public LiveData<List<ListVO>> getUnfinishedData() {
        return allUnfinishedLists;
    }

    public LiveData<List<ListVO>> getListsForDate() {
        return getListForDate;
    }

    // DB 에 들어갈 날짜 가져오기
    private String getInputDate() {
        //날짜 가져오기
        Calendar cal = new GregorianCalendar();
        String Today;

        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH);
        int day = cal.get(cal.DATE);

        Today = year + "-" + (month+1) + "-" + day;

        Log.d(TAG, "getInputDate: " + Today);

        return Today;
    }


    // 비동기 처리

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
