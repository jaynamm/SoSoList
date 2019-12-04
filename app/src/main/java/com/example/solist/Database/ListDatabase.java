package com.example.solist.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Database(entities = {ListVO.class}, version = 1, exportSchema = false)
public abstract class ListDatabase extends RoomDatabase {

    public abstract ListDAO listDAO();

    private static ListDatabase instance;

    public static synchronized ListDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ListDatabase.class, "list_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // instance 를 생성할 때 생성된다.
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private ListDAO listDAO;

        Calendar cal = new GregorianCalendar();

        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH);
        int day = cal.get(cal.DATE);

        String Today =year +"-"+(month+1)+"-"+day;

        private PopulateDbAsyncTask(ListDatabase db) {
            listDAO = db.listDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            listDAO.insert(new ListVO("오늘의 할일을 적어보세요.", 1, Today));
            listDAO.insert(new ListVO("아래의 입력창에서 입력할 수 있어요", 1, Today));
            listDAO.insert(new ListVO("직접 추가해보세요", 0, Today));
            listDAO.insert(new ListVO("클릭해서 수정할 수 있어요", 2, Today));
            listDAO.insert(new ListVO("밀어서 삭제할 수 있어요", 0, Today));
            return null;
        }
    }
}
