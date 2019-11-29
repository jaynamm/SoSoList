package com.example.solist.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ListDAO {

    @Query("SELECT * FROM LIST_TABLE ORDER BY ID ASC")
    LiveData<List<ListVO>> getAll();

    @Query("SELECT * FROM LIST_TABLE WHERE status = 1")
    LiveData<List<ListVO>> getUnfinishedData();

    @Insert
    void insert(ListVO listVO);

    @Update
    void update(ListVO listVO);

    @Delete
    void delete(ListVO listVO);

    @Query("DELETE FROM list_table")
    void deleteAllLists();
}
