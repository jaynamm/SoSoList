package com.example.solist.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ListVO.class}, version = 1, exportSchema = false)
public abstract class ListDatabase extends RoomDatabase {

    public abstract ListDAO listDAO();

    private static ListDatabase instance;

    public static synchronized ListDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ListDatabase.class, "list_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback  roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private ListDAO listDAO;

        private PopulateDbAsyncTask(ListDatabase db) {
            listDAO = db.listDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            listDAO.insert(new ListVO("cccc", "ing", "2019-11-27"));
            return null;
        }
    }
}
