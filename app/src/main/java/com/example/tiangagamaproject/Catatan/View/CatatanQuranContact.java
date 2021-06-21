package com.example.tiangagamaproject.Catatan.View;

import android.view.View;

import com.example.tiangagamaproject.Catatan.Entity.AppDatabase;
import com.example.tiangagamaproject.Catatan.Entity.CatatanQuranModel;

import java.util.List;

public interface CatatanQuranContact {
    interface view extends View.OnClickListener{
        void successAdd();
        void successDelete();
        void resetForm();
        void getData(List<CatatanQuranModel> list);
        void editData(CatatanQuranModel item);
        void deleteData(CatatanQuranModel item);
    }

    interface presenter{
        void insertData(String tanggal, String nama_surat, String catatan, AppDatabase database);
        void readData(AppDatabase database);
        void editData(String tanggal, String nama_surat, String catatan, int id, AppDatabase database);
        void deleteData(CatatanQuranModel dataBooks, AppDatabase database);
    }
}
