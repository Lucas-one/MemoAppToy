package com.example.memotoy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MemoYJDao {
    @Query("SELECT * FROM MemoYJ")
    List<MemoYJ> getAll();

    @Insert
    void insert(MemoYJ memoYJ);

    @Update
    void update(MemoYJ memoYJ);

    @Delete
    void delete(MemoYJ memoYJ);

}
