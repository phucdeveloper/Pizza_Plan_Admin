package com.philipstudio.pizzaplanadmin.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.philipstudio.pizzaplanadmin.R;
import com.philipstudio.pizzaplanadmin.adapter.DonHangAdapter;
import com.philipstudio.pizzaplanadmin.adapter.MonAnAdapter;
import com.philipstudio.pizzaplanadmin.model.ChiTietDonHang;
import com.philipstudio.pizzaplanadmin.model.DonHang;
import com.philipstudio.pizzaplanadmin.model.GioHang;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class HoaDonActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dataRef;
    String idNguoiDung, id;
    double giatien;
    int soluong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview_danhsach_hoadon);

        Intent intent = getIntent();
        idNguoiDung = intent.getStringExtra("idNguoiDung");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HoaDonActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dataRef = firebaseDatabase.getReference().child("DonHang");
        dataRef.child(idNguoiDung).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final ArrayList<DonHang> arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DonHang donHang = dataSnapshot.getValue(DonHang.class);
                    arrayList.add(donHang);
                }

                DonHangAdapter adapter = new DonHangAdapter(arrayList, HoaDonActivity.this);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickDonHangListener(new DonHangAdapter.OnItemClickDonHangListener() {
                    @Override
                    public void OnItemClick(Context context, int position) {
                        showDialog(context, position);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDialog(Context context, final int position) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_donhang);

        dialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        final TextView txtOrder, txtTrangthaihoadon, txtThoigian, txtTongtien;
        final RecyclerView recyclerViewMonAn;

        txtOrder = dialog.findViewById(R.id.textview_order);
        txtThoigian = dialog.findViewById(R.id.textview_thoigian);
        txtTongtien = dialog.findViewById(R.id.textview_tongtien);
        txtTrangthaihoadon = dialog.findViewById(R.id.textview_trangthai);
        recyclerViewMonAn = dialog.findViewById(R.id.recyclerview_danhsach_monan);
        if (recyclerViewMonAn != null) {
            recyclerViewMonAn.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            recyclerViewMonAn.setLayoutManager(layoutManager);

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference dataRef = firebaseDatabase.getReference().child("ChiTietDonHang");

            dataRef.child(idNguoiDung).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<ChiTietDonHang> chiTietDonHangArrayList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ChiTietDonHang chiTietDonHang = dataSnapshot.getValue(ChiTietDonHang.class);
                        chiTietDonHangArrayList.add(chiTietDonHang);
                    }

                    MonAnAdapter adapter = new MonAnAdapter(chiTietDonHangArrayList.get(position).getListDanhSachGioHang(), HoaDonActivity.this);
                    recyclerViewMonAn.setAdapter(adapter);

                    txtOrder.setText(chiTietDonHangArrayList.get(position).getIdDonHang());
                    txtThoigian.setText(chiTietDonHangArrayList.get(position).getThoigiandathang());
                    txtTrangthaihoadon.setText("Đã giao hàng");

                    NumberFormat formatter = new DecimalFormat("#,###");
                    double tongtien = hienThiTongTien(chiTietDonHangArrayList.get(position).getListDanhSachGioHang());
                    String formattedGiatien = formatter.format(tongtien);
                    txtTongtien.setText(formattedGiatien + " đồng");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        dialog.show();
    }

    private double hienThiTongTien(ArrayList<GioHang> gioHangs) {
        double tongtien = 0;
        for (int i = 0; i < gioHangs.size(); i++) {
            giatien = gioHangs.get(i).getMonAn().getGia();
            soluong = gioHangs.get(i).getSoluong();
            tongtien = tongtien + giatien * soluong;
        }
        return tongtien;
    }

}