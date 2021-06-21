package com.example.tiangagamaproject.Sholat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiangagamaproject.APIMain.ApiMain;
import com.example.tiangagamaproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SholatActivity extends AppCompatActivity {
    private ArrayList<Kota> listKota;
    private ArrayAdapter<Kota> mKotaAdapter;
    private Spinner spKota;
    private TextView tvTanggal, tvSubuh, tvDzuhur, tvAsar, tvMaghrib, tvIsya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jadwal_sholat);

        spKota = (Spinner) findViewById(R.id.kota);
        tvTanggal = (TextView) findViewById(R.id.tv_tanggal);
        tvSubuh = (TextView) findViewById(R.id.tv_subuh);
        tvDzuhur = (TextView) findViewById(R.id.tv_dzuhur);
        tvAsar = (TextView) findViewById(R.id.tv_ashar);
        tvMaghrib = (TextView) findViewById(R.id.tv_maghrib);
        tvIsya = (TextView) findViewById(R.id.tv_isya);

        listKota = new ArrayList<Kota>();
        mKotaAdapter = new ArrayAdapter<Kota>(this, android.R.layout.simple_spinner_item, listKota);
        mKotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKota.setAdapter(mKotaAdapter);
        spKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?>  adapterView, View view, int position, long id) {
                Kota kota = mKotaAdapter.getItem(position);
                loadJadwal(kota.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        loadKota();
    }

    private void loadKota() {
        try {
            String api = "/kota";
            ApiMain task = new ApiMain(this, api, new ApiMain.OnPostExecuteListener(){
                @Override
                public void onPostExecute(String result) {
                    Log.d("kotaData", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("kota");
                        Kota kota = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            kota = new Kota();
                            kota.setId(obj.getInt("id"));
                            kota.setNama(obj.getString("nama"));
                            listKota.add(kota);
                        }
                        mKotaAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void loadJadwal(int id) {
        try {
            String id_kota = String.valueOf(id);
            SimpleDateFormat current = new SimpleDateFormat("yyyy-MM-dd");
            String tanggal = current.format(new Date());

            String api_url = "/jadwal/kota/" + id_kota + "/tanggal/" + tanggal;
            ApiMain task = new ApiMain(this, api_url, new ApiMain.OnPostExecuteListener(){
                @Override
                public void onPostExecute(String result) {
                    Log.d("JadwalData", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject objJadwal = jsonObject.getJSONObject("jadwal");
                        JSONObject obData = objJadwal.getJSONObject("data");
                        tvTanggal.setText(obData.getString("tanggal"));
                        tvSubuh.setText(obData.getString("subuh"));
                        tvDzuhur.setText(obData.getString("dzuhur"));
                        tvAsar.setText(obData.getString("ashar"));
                        tvMaghrib.setText(obData.getString("maghrib"));
                        tvIsya.setText(obData.getString("isya"));

                        Log.d("dataJadwal", obData.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}