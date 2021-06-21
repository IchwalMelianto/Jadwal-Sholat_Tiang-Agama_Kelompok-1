package com.example.tiangagamaproject.Catatan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiangagamaproject.R;
import com.example.tiangagamaproject.Catatan.Entity.AppDatabase;
import com.example.tiangagamaproject.Catatan.Entity.CatatanQuranModel;

public class EditCatatanQuranActivity extends AppCompatActivity {
    private Button btnEdit;
    private EditText etTanggal, etSurat, etCatatan;
    private int id;
    private String tanggal,surat,catatan;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_catatan);

        btnEdit = findViewById(R.id.btn_updateOnEdit);
        etTanggal = findViewById(R.id.et_tanggal);
        etSurat = findViewById(R.id.et_nama_surat);
        etCatatan = findViewById(R.id.et_catatan);

        id = getIntent().getIntExtra("catatan_id",0);
        tanggal = getIntent().getStringExtra("catatan_tanggal");
        surat = getIntent().getStringExtra("catatan_surat");
        catatan= getIntent().getStringExtra("catatan_isi");

        etTanggal.setText(tanggal);
        etSurat.setText(surat);
        etCatatan.setText(catatan);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int idEdit = id;
                    String tanggalEdit = etTanggal.getText().toString().trim();
                    String suratEdit = etSurat.getText().toString().trim();
                    String catatanEdit = etCatatan.getText().toString().trim();

                    boolean isEmptyFields = false;

                    if (TextUtils.isEmpty(tanggalEdit)) {
                        isEmptyFields = true;
                        etTanggal.setError("Field can't be empty");
                    }

                    if (TextUtils.isEmpty(suratEdit)) {
                        isEmptyFields = true;
                        etSurat.setError("Field can't be empty");
                    }

                    if(!isEmptyFields) {
                        CatatanQuranModel catatanQuranModel = new CatatanQuranModel();
                        catatanQuranModel.setId(idEdit);
                        catatanQuranModel.setTanggal(tanggalEdit);
                        catatanQuranModel.setSurat(suratEdit);
                        catatanQuranModel.setCatatan(catatanEdit);

                        appDatabase = AppDatabase.inidb(getApplicationContext());
                        appDatabase.dao().updateData(catatanQuranModel);
                        Intent intent = new Intent(getApplicationContext(), LihatCatatanQuranActivity.class);
                        startActivity(intent);
                        finish();

                        Log.d("Edit Catatan Data ", "Update Sukses");
                        Toast.makeText(getApplicationContext(), "Update Sukses", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    Log.e("Edit Catatan Data", "Edit Data Gagal , msg : " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Edit Data Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
