package com.example.tiangagamaproject.Catatan.Entity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CatatanQuranModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CatatanQuranDAO dao();
    public static AppDatabase appDatabase;
    public static AppDatabase inidb(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "catatanquran"
            ).allowMainThreadQueries().build();
        }
        return appDatabase;
    }
}
