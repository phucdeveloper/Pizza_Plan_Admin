package com.philipstudio.pizzaplanadmin;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.ViewHolder> {

    ArrayList<NguoiDung> arrayList;
    Context context;

    OnNguoiDungItemClickListener onNguoiDungItemClickListener;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dataRef;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtEmail.setText(arrayList.get(position).getEmailorPhoneNumber());
        holder.txtTen.setText(arrayList.get(position).getTennguoidung());
        holder.txtMatkhau.setText(arrayList.get(position).getMatkhau());
        Glide.with(context).load(arrayList.get(position).getAnhdaidien()).into(holder.imgAnh);

        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNguoiDungItemClickListener != null){
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
        Button btnSua, btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAnh = itemView.findViewById(R.id.item_imageview);
            txtEmail = itemView.findViewById(R.id.item_textview_email);
            txtTen = itemView.findViewById(R.id.item_textview_username);
            txtMatkhau = itemView.findViewById(R.id.item_textview_password);
            btnSua = itemView.findViewById(R.id.button_sua);
            btnXoa = itemView.findViewById(R.id.button_xoa);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onNguoiDungItemClickListener != null){
                        onNguoiDungItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    public interface OnNguoiDungItemClickListener{
        void onItemClick(int position);
    }
}
