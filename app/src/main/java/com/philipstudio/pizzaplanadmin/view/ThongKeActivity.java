package com.philipstudio.pizzaplanadmin.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.philipstudio.pizzaplanadmin.R;
import com.philipstudio.pizzaplanadmin.model.DonHang;
import com.philipstudio.pizzaplanadmin.model.NguoiDung;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ThongKeActivity extends AppCompatActivity {

    TextView txtTongSoDonHang, txtSoTienThuNhap;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dataRef;

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
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    NguoiDung nguoiDung = dataSnapshot.getValue(NguoiDung.class);
                    nguoiDungArrayList.add(nguoiDung);
                }

                dataRef = firebaseDatabase.getReference().child("DonHang");
                final ArrayList<DonHang> arrayList = new ArrayList<>();
                for (int i=0; i<nguoiDungArrayList.size(); i++){
                    dataRef.child(nguoiDungArrayList.get(i).getIdNguoiDung())
                            .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void hienThiTongTienCacHoaDon(ArrayList<DonHang> donHangs){
        double tongtien = 0;
        for (int i=0; i<donHangs.size(); i++){
            tongtien = tongtien + donHangs.get(i).getTongtien();
        }

        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedGiatien = formatter.format(tongtien * 1000);
        txtSoTienThuNhap.setText(formattedGiatien + " đồng");
    }

    private void initView(){
        txtTongSoDonHang = findViewById(R.id.textview_tongso_donhang);
        txtSoTienThuNhap = findViewById(R.id.textview_tongtien_thunhap);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}