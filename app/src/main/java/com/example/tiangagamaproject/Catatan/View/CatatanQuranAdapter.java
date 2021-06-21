package com.example.tiangagamaproject.Catatan.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiangagamaproject.Catatan.Entity.AppDatabase;
import com.example.tiangagamaproject.Catatan.Entity.CatatanQuranModel;
import com.example.tiangagamaproject.R;

import java.util.ArrayList;

public class CatatanQuranAdapter extends RecyclerView.Adapter<CatatanQuranAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CatatanQuranModel> catatanQurans = new ArrayList<>();
    private CatatanQuranContact.view aView;
    private AppDatabase appDatabase;
    public Listener listener;

    public CatatanQuranAdapter(Context context) {
        this.context = context;
        appDatabase = AppDatabase.inidb(this.context);
    }

    public void setData(ArrayList<CatatanQuranModel> items) {
        catatanQurans.clear();
        catatanQurans.addAll(items);
        notifyDataSetChanged();
    }

    public interface Listener {
        void onClick(int position);
        void onClick1(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_catatan,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatatanQuranAdapter.ViewHolder holder, int position) {
        holder.tvTanggal.setText(catatanQurans.get(position).getTanggal());
        holder.tvNama_Surat.setText(catatanQurans.get(position).getSurat());
        holder.tvCatatan.setText(catatanQurans.get(position).getCatatan());
        holder.id.setText(Integer.toString(catatanQurans.get(position).getId()));

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {

                    CatatanQuranModel catatanQuranModel = new CatatanQuranModel();

                    catatanQuranModel.setId(catatanQurans.get(position).getId());
                    catatanQuranModel.setTanggal(catatanQurans.get(position).getTanggal());
                    catatanQuranModel.setSurat(catatanQurans.get(position).getSurat());
                    catatanQuranModel.setCatatan(catatanQurans.get(position).getCatatan());

                    appDatabase.dao().deleteData(catatanQuranModel);
                    if (listener != null) {
                        listener.onClick(position);
                    }

                    Log.d("Catatan Adapter ", "Sukses Dihapus");
                    Toast.makeText(context, "Sukses Dihapus", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("Catatan Adapter ", "Gagal Menghapus , msg : " + e.getMessage());
                    Toast.makeText(context, "Gagal Menghapus", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick1(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return catatanQurans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTanggal,tvNama_Surat,tvCatatan,id;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggal  =   itemView.findViewById(R.id.tv_item_tanggal);
            tvNama_Surat  =   itemView.findViewById(R.id.tv_item_namasurat);
            tvCatatan  =   itemView.findViewById(R.id.tv_item_catatan);
            id  =   itemView.findViewById(R.id.tv_item_id);
            cardView  =   itemView.findViewById(R.id.cv_item);
        }
    }
}
