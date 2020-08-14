package com.philipstudio.pizzaplanadmin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {

    ArrayList<DonHang> arrayList;
    Context context;

    OnItemClickLichSuThanhToanListener onItemClickLichSuThanhToanListener;

    public DonHangAdapter(ArrayList<DonHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setOnItemClickLichSuThanhToanListener(OnItemClickLichSuThanhToanListener onItemClickLichSuThanhToanListener) {
        this.onItemClickLichSuThanhToanListener = onItemClickLichSuThanhToanListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtIdDonhang.setText(arrayList.get(position).getIdDonHang());
        holder.txtThoigian.setText(arrayList.get(position).getThoigian());
        holder.txtDiachi.setText(arrayList.get(position).getDiadiem());

        holder.txtChitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickLichSuThanhToanListener != null){
                    onItemClickLichSuThanhToanListener.OnItemClick(context, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtChitiet, txtIdDonhang, txtThoigian, txtDiachi;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtChitiet = itemView.findViewById(R.id.item_textview_chitietdonhang);
            txtIdDonhang = itemView.findViewById(R.id.item_textview_iddathang);
            txtDiachi = itemView.findViewById(R.id.item_textview_diachigiaohang);
            txtThoigian = itemView.findViewById(R.id.item_textview_thoigian);
            imageView = itemView.findViewById(R.id.item_imageview_anhdaidien);
        }
    }

    public interface OnItemClickLichSuThanhToanListener{
        void OnItemClick(Context context, int position);
    }
}
