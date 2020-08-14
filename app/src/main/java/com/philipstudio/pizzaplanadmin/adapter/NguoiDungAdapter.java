package com.philipstudio.pizzaplanadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.philipstudio.pizzaplanadmin.R;
import com.philipstudio.pizzaplanadmin.model.NguoiDung;

import java.util.ArrayList;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.ViewHolder> {

    ArrayList<NguoiDung> arrayList;
    Context context;

    OnNguoiDungItemClickListener onNguoiDungItemClickListener;

    public NguoiDungAdapter(ArrayList<NguoiDung> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setOnNguoiDungItemClickListener(OnNguoiDungItemClickListener onNguoiDungItemClickListener) {
        this.onNguoiDungItemClickListener = onNguoiDungItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nguoidung, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtEmail.setText(arrayList.get(position).getEmailorPhoneNumber());
        holder.txtTen.setText(arrayList.get(position).getTennguoidung());
        holder.txtMatkhau.setText(arrayList.get(position).getMatkhau());
        Glide.with(context).load(arrayList.get(position).getAnhdaidien()).into(holder.imgAnh);

        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNguoiDungItemClickListener != null){
                    onNguoiDungItemClickListener.onClickButtonSuaMatKhau(position);
                }
            }
        });

        holder.btnXemDanhSachHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNguoiDungItemClickListener != null){
                    onNguoiDungItemClickListener.onClickButtonXemDanhSachHoaDon(arrayList.get(position).getIdNguoiDung());
                }
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNguoiDungItemClickListener != null){
                    onNguoiDungItemClickListener.onClickButtonXoa(arrayList.get(position).getIdNguoiDung());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgAnh;
        TextView txtEmail, txtTen, txtMatkhau;
        Button btnSua, btnXoa, btnXemDanhSachHoaDon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAnh = itemView.findViewById(R.id.item_imageview);
            txtEmail = itemView.findViewById(R.id.item_textview_email);
            txtTen = itemView.findViewById(R.id.item_textview_username);
            txtMatkhau = itemView.findViewById(R.id.item_textview_password);
            btnSua = itemView.findViewById(R.id.button_sua);
            btnXoa = itemView.findViewById(R.id.button_xoa);
            btnXemDanhSachHoaDon = itemView.findViewById(R.id.button_xem_danh_sach_hoa_don);
        }
    }

    public interface OnNguoiDungItemClickListener{
        void onClickButtonSuaMatKhau(int position);
        void onClickButtonXemDanhSachHoaDon(String text);
        void onClickButtonXoa(String text);
    }
}
