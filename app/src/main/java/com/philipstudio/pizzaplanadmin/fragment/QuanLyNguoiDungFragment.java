package com.philipstudio.pizzaplanadmin.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.philipstudio.pizzaplanadmin.R;
import com.philipstudio.pizzaplanadmin.adapter.NguoiDungAdapter;
import com.philipstudio.pizzaplanadmin.model.NguoiDung;
import com.philipstudio.pizzaplanadmin.view.HoaDonActivity;

import java.util.ArrayList;

public class QuanLyNguoiDungFragment extends Fragment {

    RecyclerView recyclerView;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dataRef = firebaseDatabase.getReference().child("NguoiDung");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlynguoidung, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_danhsach_nguoidung);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final ArrayList<NguoiDung> arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NguoiDung nguoiDung = dataSnapshot.getValue(NguoiDung.class);
                    arrayList.add(nguoiDung);
                }

                final NguoiDungAdapter adapter = new NguoiDungAdapter(arrayList, getContext());
                recyclerView.setAdapter(adapter);

                adapter.setOnNguoiDungItemClickListener(new NguoiDungAdapter.OnNguoiDungItemClickListener() {
                    @Override
                    public void onClickButtonSuaMatKhau(int position) {
                        NguoiDung nguoiDung = arrayList.get(position);
                        showDialog(getContext(), nguoiDung);
                    }

                    @Override
                    public void onClickButtonXemDanhSachHoaDon(String text) {
                        Intent intent = new Intent(getContext(), HoaDonActivity.class);
                        intent.putExtra("idNguoiDung", text);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void showDialog(Context context, NguoiDung nguoiDung){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog);

        TextView txtEmail, txtMatkhaucu;
        final EditText edtMatkhaumoi;
        Button btnCapNhat;

        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        dialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);

        txtEmail = dialog.findViewById(R.id.textview_email);
        txtMatkhaucu = dialog.findViewById(R.id.textview_matkhau_cu);
        edtMatkhaumoi = dialog.findViewById(R.id.edittext_matkhau_moi);
        btnCapNhat = dialog.findViewById(R.id.button_capnhat);

        txtMatkhaucu.setText(nguoiDung.getMatkhau());
        txtEmail.setText(nguoiDung.getEmailorPhoneNumber());
        final String id = nguoiDung.getIdNguoiDung();


        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matkhaumoi = edtMatkhaumoi.getText().toString();
                firebaseDatabase = FirebaseDatabase.getInstance();
                dataRef = firebaseDatabase.getReference().child("NguoiDung");
                dataRef.child(id).child("matkhau").setValue(matkhaumoi);
            }
        });

        dialog.show();
    }
}
