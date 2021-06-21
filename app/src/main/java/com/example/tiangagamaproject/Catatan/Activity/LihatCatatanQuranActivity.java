package com.example.tiangagamaproject.Catatan.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiangagamaproject.R;
import com.example.tiangagamaproject.Catatan.Entity.AppDatabase;
import com.example.tiangagamaproject.Catatan.Entity.CatatanQuranModel;
import com.example.tiangagamaproject.Catatan.View.CatatanQuranAdapter;

import java.util.ArrayList;

public class LihatCatatanQuranActivity extends AppCompatActivity {
    private CatatanQuranAdapter catatanQuranAdapter;
    private RecyclerView rvCatatan;
    private AppDatabase appDatabase;
    private ArrayList<CatatanQuranModel> listCatatan = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lihat_catatan);

        rvCatatan = findViewById(R.id.rc_main);
        catatanQuranAdapter = new CatatanQuranAdapter(getApplicationContext());
        catatanQuranAdapter.notifyDataSetChanged();

        if (appDatabase == null) {
            appDatabase = AppDatabase.inidb(getApplicationContext());
        }

        listCatatan.addAll(appDatabase.dao().getData());
        catatanQuranAdapter.setData(listCatatan);

        rvCatatan.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvCatatan.setAdapter(catatanQuranAdapter);

        catatanQuranAdapter.setListener(new CatatanQuranAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getApplicationContext(), LihatCatatanQuranActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onClick1(int position) {
                Intent intent = new Intent(getApplicationContext(), EditCatatanQuranActivity.class);
                intent.putExtra("catatan_id", listCatatan.get(position).getId());
                intent.putExtra("catatan_tanggal", listCatatan.get(position).getTanggal());
                intent.putExtra("catatan_surat", listCatatan.get(position).getSurat());
                intent.putExtra("catatan_isi", listCatatan.get(position).getCatatan());
                startActivity(intent);
                finish();
            }
        });
    }
}