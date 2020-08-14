package com.philipstudio.pizzaplanadmin.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.philipstudio.pizzaplanadmin.R;
import com.philipstudio.pizzaplanadmin.model.MonAn;

import java.util.ArrayList;

public class DangNhapActivity extends AppCompatActivity {

    Button btnDangNhap;
    EditText edtTaikhoan, edtMatkhau;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        initView();

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoan = edtTaikhoan.getText().toString();
                String matkhau = edtMatkhau.getText().toString();

                if (TextUtils.isEmpty(taikhoan) || TextUtils.isEmpty(matkhau)){
                    Toast.makeText(DangNhapActivity.this, "Bạn hãy nhập đầy đủ thông tin để đăn nhập", Toast.LENGTH_SHORT).show();
                }
                else if (taikhoan.equals("admin")&&(matkhau.equals("123456"))){
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    dataRef = firebaseDatabase.getReference().child("MonAn");
                    dataRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<MonAn> arrayList = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                MonAn monAn = dataSnapshot.getValue(MonAn.class);
                                arrayList.add(monAn);
                            }

                            if (arrayList.size() != 0){
                                Intent intent = new Intent(DangNhapActivity.this, QuanLyActivity.class);
                                intent.putParcelableArrayListExtra("dataListMonAn", arrayList);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(DangNhapActivity.this, "Tài khoản đăng nhập không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        btnDangNhap = findViewById(R.id.button_dangnhap);
        edtTaikhoan = findViewById(R.id.edittext_taikhoan);
        edtMatkhau = findViewById(R.id.edittext_matkhau);
    }
}