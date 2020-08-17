package com.philipstudio.pizzaplanadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.philipstudio.pizzaplanadmin.R;
import com.philipstudio.pizzaplanadmin.model.GioHang;

import java.util.ArrayList;

public class ThongKeTheoMonAnAdapter extends RecyclerView.Adapter<ThongKeTheoMonAnAdapter.ViewHolder>{

    ArrayList<GioHang> arrayList;
    Context context;

    public ThongKeTheoMonAnAdapter(ArrayList<GioHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongke_monan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTenmonan, txtSoluong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTenmonan = itemView.findViewById(R.id.item_textview_tenmonan);
            txtSoluong = itemView.findViewById(R.id.item_textview_soluong);
        }
    }
}
