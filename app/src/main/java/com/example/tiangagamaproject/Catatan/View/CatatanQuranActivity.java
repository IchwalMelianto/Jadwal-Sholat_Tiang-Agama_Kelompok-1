package com.example.tiangagamaproject.Catatan.View;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiangagamaproject.Catatan.Entity.AppDatabase;
import com.example.tiangagamaproject.Catatan.Entity.CatatanQuranModel;
import com.example.tiangagamaproject.R;

import java.util.List;

public class CatatanQuranActivity extends AppCompatActivity implements CatatanQuranContact.view{
    private AppDatabase appDatabase;
    private CatatanQuranPresenter catatanQuranPresenter;
    private CatatanQuranAdapter catatanQuranAdapter;

    private Button btnSubmit;
    private RecyclerView recyclerView;
    private EditText etTanggal, etNama_Surat, etCatatan;

    private int id = 0;
    boolean edit = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        etTanggal = findViewById(R.id.et_tanggal);
        etNama_Surat = findViewById(R.id.et_nama_surat);
        etCatatan = findViewById(R.id.et_catatan);
        recyclerView = findViewById(R.id.rc_main);

        appDatabase = AppDatabase.inidb(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        catatanQuranPresenter = new CatatanQuranPresenter(this);
        catatanQuranPresenter.readData(appDatabase);
    }

    @Override
    public void successAdd() {
        Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
        catatanQuranPresenter.readData(appDatabase);
    }

    @Override
    public void successDelete() {
        Toast.makeText(this, "Berhasil Menghapus Data", Toast.LENGTH_LONG).show();
        catatanQuranPresenter.readData(appDatabase);
    }

    @Override
    public void resetForm() {
        etTanggal.setText("");
        etNama_Surat.setText("");
        etCatatan.setText("");
        btnSubmit.setText("Submit");
    }

    @Override
    public void getData(List<CatatanQuranModel> list) {
        catatanQuranAdapter = new CatatanQuranAdapter(this);
        recyclerView.setAdapter(catatanQuranAdapter);
    }

    @Override
    public void editData(CatatanQuranModel item) {
        etTanggal.setText(item.getTanggal());
        etNama_Surat.setText(item.getSurat());
        etCatatan.setText(item.getCatatan());
        id = item.getId();
        edit = true;
        btnSubmit.setText("Edit Data");
    }

    @Override
    public void deleteData(CatatanQuranModel item) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }else{
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Menghapus Data")
                .setMessage("Yakin Akan Menghapus Data Ini?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetForm();
                        catatanQuranPresenter.deleteData(item,appDatabase);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_dialer)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (view==btnSubmit){
            if(etTanggal.getText().toString().equals("")||etNama_Surat.getText().toString().equals("")||etCatatan.getText().toString().equals("")){
                Toast.makeText(this, "Semua Data Harus Terisi!", Toast.LENGTH_SHORT).show();
            }
            else{
                if(!edit){
                    catatanQuranPresenter.insertData(etTanggal.getText().toString(),etNama_Surat.getText().toString(),etCatatan.getText().toString(),appDatabase);
                }else{
                    catatanQuranPresenter.editData(etTanggal.getText().toString(),etNama_Surat.getText().toString(),etCatatan.getText().toString(),id,appDatabase);
                    edit = false;
                }
                resetForm();
            }
        }
    }
}
