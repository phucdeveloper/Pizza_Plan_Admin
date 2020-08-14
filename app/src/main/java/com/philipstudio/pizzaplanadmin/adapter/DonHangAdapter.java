package com.philipstudio.pizzaplanadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.philipstudio.pizzaplanadmin.R;
import com.philipstudio.pizzaplanadmin.model.DonHang;

import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {

    ArrayList<DonHang> arrayList;
    Context context;

    OnItemClickDonHangListener onItemClickDonHangListener;

    public DonHangAdapter(ArrayList<DonHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setOnItemClickDonHangListener(OnItemClickDonHangListener onItemClickDonHangListener) {
        this.onItemClickDonHangListener = onItemClickDonHangListener;
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
                if (onItemClickDonHangListener != null){
                    onItemClickDonHangListener.OnItemClick(context, position);
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

    public interface OnItemClickDonHangListener{
        void OnItemClick(Context context, int position);
    }
}
