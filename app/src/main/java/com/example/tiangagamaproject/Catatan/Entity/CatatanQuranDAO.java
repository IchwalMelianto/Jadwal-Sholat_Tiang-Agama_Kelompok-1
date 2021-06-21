package com.example.tiangagamaproject.Catatan.Entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CatatanQuranDAO {
    @Insert
    Long insertData(CatatanQuranModel datacatatanquran);

    @Query("Select * from catq_db")
    List<CatatanQuranModel>getData();

    @Update
    int updateData(CatatanQuranModel item);

    @Delete
    void deleteData(CatatanQuranModel item);
}
