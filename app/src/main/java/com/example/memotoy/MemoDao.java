package com.example.memotoy;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MemoDao {
    @Query("SELECT * FROM Memo")
    List<Memo> getAll();

    @Query("SELECT * FROM Memo WHERE seq = :seq")
    Memo selectBySeq(int seq);

    @Query("Delete FROM Memo WHERE seq = :seq")
    void deleteBySeq(int seq);

    @Query("UPDATE Memo SET isDone = :isDone WHERE seq is :seq ")
    void updateIsDone(int seq, int isDone);

    @Insert
    void insert(Memo memo);

    @Update
    void update(Memo memo);

    @Delete
    void delete(Memo memo);

}
