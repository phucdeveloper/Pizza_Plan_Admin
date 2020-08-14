package com.philipstudio.pizzaplanadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.ViewHolder> {

    ArrayList<MonAn> arrayList;
    Context context;

    OnItemClicklistener onItemClicklistener;

    public MonAnAdapter(ArrayList<MonAn> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setOnItemClicklistener(OnItemClicklistener onItemClicklistener) {
        this.onItemClicklistener = onItemClicklistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet_monan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTenmon.setText(arrayList.get(position).getTenMonAn());
        holder.txtNguyelieu.setText(arrayList.get(position).getNguyenLieu());
        holder.txtGiatien.setText(String.valueOf(arrayList.get(position).getGia()));
        Glide.with(context).load(arrayList.get(position).getAnh()).into(holder.imgAnh);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTenmon, txtGiatien, txtNguyelieu;
        ImageView imgAnh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTenmon = itemView.findViewById(R.id.item_textview_name);
            txtGiatien = itemView.findViewById(R.id.item_textview_price);
            txtNguyelieu = itemView.findViewById(R.id.item_textview_material);
            imgAnh = itemView.findViewById(R.id.item_imageview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClicklistener != null){
                        onItemClicklistener.onItemClick(position, arrayList.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClicklistener{
        void onItemClick(int position, MonAn monAn);
    }
}
