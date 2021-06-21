package com.example.tiangagamaproject.Catatan.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tiangagamaproject.Catatan.Activity.LihatCatatanQuranActivity;
import com.example.tiangagamaproject.R;
import com.example.tiangagamaproject.Catatan.Entity.AppDatabase;
import com.example.tiangagamaproject.Catatan.Entity.CatatanQuranModel;

public class CatatanQuranFragment extends Fragment {

    private EditText etTanggal, etSurat, etCatatan;
    private Button btnSimpan, btnLihat;
    private AppDatabase appDatabase;


    public CatatanQuranFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catatan_quran, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTanggal = view.findViewById(R.id.et_tanggal);
        etSurat = view.findViewById(R.id.et_nama_surat);
        etCatatan = view.findViewById(R.id.et_catatan);
        btnSimpan = view.findViewById(R.id.btn_submit);
        btnLihat = view.findViewById(R.id.btn_lihat);

        appDatabase = AppDatabase.inidb(getContext());

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String inputTanggal= etTanggal.getText().toString().trim();
                    String inputCatatan = etCatatan.getText().toString().trim();

                    boolean isEmptyFields = false;

                    if (TextUtils.isEmpty(inputTanggal)) {
                        isEmptyFields = true;
                        etTanggal.setError("Field can't be empty");
                    }

                    if (TextUtils.isEmpty(inputCatatan)) {
                        isEmptyFields = true;
                        etCatatan.setError("Field can't be empty");
                    }

                    if (!isEmptyFields) {
                        CatatanQuranModel catatanQuranModel = new CatatanQuranModel();
                        catatanQuranModel.setTanggal(etTanggal.getText().toString());
                        catatanQuranModel.setSurat(etSurat.getText().toString());
                        catatanQuranModel.setCatatan(etCatatan.getText().toString());

                        appDatabase.dao().insertData(catatanQuranModel);

                        etTanggal.setText("");
                        etSurat.setText("");
                        etCatatan.setText("");

                        Log.d("FragmentCatatan", "Sukses");
                        Toast.makeText(getContext(), "Catatan Tersimpan", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("FragmentCatatan ", "Gagal Menyimpan , msg : " + e.getMessage());
                    Toast.makeText(getContext(), "Gagal Menyimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LihatCatatanQuranActivity.class);
                startActivity(intent);
            }
        });
    }
}