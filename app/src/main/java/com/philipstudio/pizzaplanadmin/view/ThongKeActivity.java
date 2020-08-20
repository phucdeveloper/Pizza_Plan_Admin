package com.philipstudio.pizzaplanadmin.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.philipstudio.pizzaplanadmin.R;
import com.philipstudio.pizzaplanadmin.adapter.ThongKeTheoMonAnAdapter;
import com.philipstudio.pizzaplanadmin.model.ChiTietDonHang;
import com.philipstudio.pizzaplanadmin.model.DonHang;
import com.philipstudio.pizzaplanadmin.model.GioHang;
import com.philipstudio.pizzaplanadmin.model.NguoiDung;
import com.philipstudio.pizzaplanadmin.model.ThongKeMonAn;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ThongKeActivity extends AppCompatActivity {

    TextView txtTongSoDonHang, txtSoTienThuNhap;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dataRef;
    RecyclerView rVThongKeTheoMonAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        initView();

        dataRef = firebaseDatabase.getReference().child("NguoiDung");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<NguoiDung> nguoiDungArrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NguoiDung nguoiDung = dataSnapshot.getValue(NguoiDung.class);
                    nguoiDungArrayList.add(nguoiDung);
                }

                dataRef = firebaseDatabase.getReference().child("DonHang");
                final ArrayList<DonHang> arrayList = new ArrayList<>();
                for (int i = 0; i < nguoiDungArrayList.size(); i++) {
                    dataRef.child(nguoiDungArrayList.get(i).getIdNguoiDung())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        DonHang donHang = dataSnapshot.getValue(DonHang.class);
                                        arrayList.add(donHang);
                                    }

                                    txtTongSoDonHang.setText(String.valueOf(arrayList.size()));
                                    hienThiTongTienCacHoaDon(arrayList);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }

                layTatCaCacItemGioHang(nguoiDungArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void layTatCaCacItemGioHang(ArrayList<NguoiDung> nguoiDungs) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        dataRef = firebaseDatabase.getReference().child("ChiTietDonHang");
        final ArrayList<GioHang> gioHangs = new ArrayList<>();
        for (int i = 0; i < nguoiDungs.size(); i++) {
            dataRef.child(nguoiDungs.get(i).getIdNguoiDung())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<ChiTietDonHang> chiTietDonHangs = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                ChiTietDonHang chiTietDonHang = dataSnapshot.getValue(ChiTietDonHang.class);
                                chiTietDonHangs.add(chiTietDonHang);
                            }

                            for (int i = 0; i < chiTietDonHangs.size(); i++) {
                                gioHangs.addAll(chiTietDonHangs.get(i).getListDanhSachGioHang());
                            }

                            thongKeSoLuongCacMonAnBanDuoc(gioHangs);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }

    private void thongKeSoLuongCacMonAnBanDuoc(ArrayList<GioHang> gioHangs){
        ArrayList<ThongKeMonAn> thongKeMonAns = new ArrayList<>();
        int dem;
        for (int i = 0; i < gioHangs.size(); i++) {
            dem = gioHangs.get(i).getSoluong();
            for (int j = 0; j < gioHangs.size(); j++) {
                if (j != i){
                    if (gioHangs.get(j).getMonAn().getTenMonAn().equals(gioHangs.get(i).getMonAn().getTenMonAn())) {
                        dem = dem + gioHangs.get(j).getSoluong();
                    }
                }
            }
            thongKeMonAns.add(new ThongKeMonAn(gioHangs.get(i).getMonAn().getTenMonAn(), dem));
        }

        for (int i=0; i<thongKeMonAns.size(); i++){
            for (int j=0; j<thongKeMonAns.size(); j++){
                if (j != i){
                    if (thongKeMonAns.get(j).getTenMonAn().equals(thongKeMonAns.get(i).getTenMonAn())) {
                        thongKeMonAns.remove(thongKeMonAns.get(j));
                    }
                }
            }
        }
        rVThongKeTheoMonAn.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ThongKeActivity.this, RecyclerView.VERTICAL, false);
        rVThongKeTheoMonAn.setLayoutManager(layoutManager);
        ThongKeTheoMonAnAdapter thongKeTheoMonAnAdapter = new ThongKeTheoMonAnAdapter(thongKeMonAns, ThongKeActivity.this);
        rVThongKeTheoMonAn.setAdapter(thongKeTheoMonAnAdapter);

    }


    private void hienThiTongTienCacHoaDon(ArrayList<DonHang> donHangs) {
        double tongtien = 0;
        for (int i = 0; i < donHangs.size(); i++) {
            tongtien = tongtien + donHangs.get(i).getTongtien();
        }

        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedGiatien = formatter.format(tongtien);
        txtSoTienThuNhap.setText(formattedGiatien + " đồng");
    }

    private void initView() {
        txtTongSoDonHang = findViewById(R.id.textview_tongso_donhang);
        txtSoTienThuNhap = findViewById(R.id.textview_tongtien_thunhap);
        rVThongKeTheoMonAn = findViewById(R.id.recyclerview_danhsach_thongke_monan);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}