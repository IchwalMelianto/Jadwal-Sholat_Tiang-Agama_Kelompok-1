package com.example.tiangagamaproject.Catatan.View;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tiangagamaproject.Catatan.Entity.AppDatabase;
import com.example.tiangagamaproject.Catatan.Entity.CatatanQuranModel;

import java.util.List;

    public class CatatanQuranPresenter implements CatatanQuranContact.presenter {
    private CatatanQuranContact.view view;

    public CatatanQuranPresenter(CatatanQuranContact.view view) { this.view = view; }

    class InsertData extends AsyncTask<Void,Void,Long>{
        private AppDatabase appDatabase;
        private CatatanQuranModel catatanQuranModel;

        public InsertData(AppDatabase appDatabase, CatatanQuranModel catatanQuranModel) {
            this.appDatabase = appDatabase;
            this.catatanQuranModel = catatanQuranModel;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return appDatabase.dao().insertData(catatanQuranModel);
        }

        protected void onPostExecute(Long along){
            super.onPostExecute(along);
            view.successAdd();
        }
    }
    @Override
    public void insertData(String tanggal, String surat, String catatan, AppDatabase database) {
        final CatatanQuranModel catatanQuranModel = new CatatanQuranModel();
        catatanQuranModel.setTanggal(tanggal);
        catatanQuranModel.setSurat(surat);
        catatanQuranModel.setCatatan(catatan);
        new InsertData(database, catatanQuranModel).execute();
    }

    @Override
    public void readData(AppDatabase database) {
        List<CatatanQuranModel> list;
        list = database.dao().getData();
        view.getData(list);
    }

    class EditData extends AsyncTask<Void,Void,Integer>{
        private AppDatabase appDatabase;
        private CatatanQuranModel catatanQuranModel;

        public EditData(AppDatabase appDatabase, CatatanQuranModel catatanQuranModel) {
            this.appDatabase = appDatabase;
            this.catatanQuranModel = catatanQuranModel;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return appDatabase.dao().updateData(catatanQuranModel);
        }

        protected void onPostExecute(Integer integer){
            super.onPostExecute(integer);
            Log.d("integer db","onPostExecute : "+integer);
            view.successAdd();
        }
    }
    @Override
    public void editData(String tanggal, String surat, String catatan, int id, AppDatabase database) {
        final CatatanQuranModel catatanQuranModel = new CatatanQuranModel();
        catatanQuranModel.setTanggal(tanggal);
        catatanQuranModel.setSurat(surat);
        catatanQuranModel.setCatatan(catatan);
        catatanQuranModel.setId(id);
        new EditData(database, catatanQuranModel).execute();
    }

    class DeleteData extends AsyncTask<Void,Void,Long>{
        private AppDatabase appDatabase;
        private CatatanQuranModel catatanQuranModel;

        public DeleteData(AppDatabase appDatabase, CatatanQuranModel catatanQuranModel) {
            this.appDatabase = appDatabase;
            this.catatanQuranModel = catatanQuranModel;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            appDatabase.dao().deleteData(catatanQuranModel);
            return null;
        }

        protected void onPostExecute(Long along){
            super.onPostExecute(along);
            view.successDelete();
        }
    }
    @Override
    public void deleteData(CatatanQuranModel catatanQuranModel, AppDatabase database) {
        new DeleteData(database, catatanQuranModel).execute();
    }
}
