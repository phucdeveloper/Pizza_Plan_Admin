package com.philipstudio.pizzaplanadmin.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.philipstudio.pizzaplanadmin.R;
import com.philipstudio.pizzaplanadmin.adapter.ChiTietMonAnAdapter;
import com.philipstudio.pizzaplanadmin.model.MonAn;

import java.util.ArrayList;

public class QuanLyMenuFragment extends Fragment {
    RecyclerView recyclerView;
    EditText edtGiatien, edtTenmonan, edtNguyenlieu;
    Button btnThem, btnSua, btnXoa;
    ImageView imgAnh;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference dataRef = firebaseDatabase.getReference().child("MonAn");

    FirebaseStorage firebaseStorage;
    StorageReference stoRef;

    static final int REQUEST_CODE = 123;
    Uri uri;
    ArrayList<MonAn> arrayList = new ArrayList<>();
    ChiTietMonAnAdapter anAdapter;
    int vitri;
    String linkAnh;
    double gia;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlymenu, container, false);
        initView(view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        Intent intent = getActivity().getIntent();
        if (intent != null){
            arrayList = intent.getParcelableArrayListExtra("dataListMonAn");
            if (arrayList != null){
                anAdapter = new ChiTietMonAnAdapter(arrayList, getContext());
                recyclerView.setAdapter(anAdapter);

                anAdapter.setOnItemClicklistener(new ChiTietMonAnAdapter.OnItemClicklistener() {
                    @Override
                    public void onItemClick(int position, MonAn monAn) {
                        edtTenmonan.setText(monAn.getTenMonAn());
                        edtGiatien.setText(String.valueOf(monAn.getGia()));
                        edtNguyenlieu.setText(monAn.getNguyenLieu());
                        Glide.with(getContext()).load(monAn.getAnh()).into(imgAnh);
                        vitri = position;
                    }
                });
            }
        }

        imgAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moThuVienChonAnh();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = String.valueOf(System.currentTimeMillis());
                String tenmonan = edtTenmonan.getText().toString();
                String data = edtGiatien.getText().toString();
                if (!TextUtils.isEmpty(data)){
                    gia = Double.parseDouble(data);
                }
                String nguyenlieu = edtNguyenlieu.getText().toString();
                if (!(TextUtils.isEmpty(tenmonan) || TextUtils.isEmpty(nguyenlieu)&&uri != null)){
                    themAnhVaoFirebaseStorage(id, tenmonan, uri, nguyenlieu, gia);
                }
                else{
                    Toast.makeText(getContext(), "Dữ liệu thêm vào còn đang rỗng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonAn monAn = arrayList.get(vitri);
                String ten = edtTenmonan.getText().toString();
                String data = edtGiatien.getText().toString();
                if (!TextUtils.isEmpty(data)){
                   gia = Double.parseDouble(data);
                }

                String nguyenlieu = edtNguyenlieu.getText().toString();
                if (uri != null){
                    themAnhVaoFirebaseStorage(uri);
                }

                if (!(TextUtils.isEmpty(data) || TextUtils.isEmpty(nguyenlieu))){
                    suaMonAn(monAn.getIdMonAn(), ten, nguyenlieu, gia, linkAnh);
                }
                else{
                    Toast.makeText(getContext(), "Bạn không có dữ liệu để cập nhật", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtTenmonan.getText().toString())|| TextUtils.isEmpty(edtGiatien.getText().toString())
                || TextUtils.isEmpty(edtNguyenlieu.getText().toString()) || uri == null){
                    Toast.makeText(getContext(), "Bạn chưa chọn item để xoá", Toast.LENGTH_SHORT).show();
                }
                else{
                    MonAn monAn = arrayList.get(vitri);
                    xoaMonAn(monAn);
                }
            }
        });

        return view;
    }

    private void moThuVienChonAnh(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void themAnhVaoFirebaseStorage(final String id, final String tenmonan, Uri uri, final String nguyenlieu, final double giatien){
        firebaseStorage = FirebaseStorage.getInstance();
        stoRef = firebaseStorage.getReference().child("anh_monan");
        final StorageReference imageFilePath = stoRef.child(uri.getLastPathSegment());
        imageFilePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        linkAnh = uri.toString();
                        MonAn monAn = new MonAn(id, tenmonan, linkAnh, nguyenlieu, giatien);
                        themMonAn(monAn);
                        getDanhSachCacMonAn();
                    }
                });
            }
        });
    }

    private void themAnhVaoFirebaseStorage(Uri uri){
        firebaseStorage = FirebaseStorage.getInstance();
        stoRef = firebaseStorage.getReference().child("anh_monan");
        final StorageReference imageFilePath = stoRef.child(uri.getLastPathSegment());
        imageFilePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        linkAnh = uri.toString();
                    }
                });
            }
        });
    }

    private void themMonAn(MonAn monAn){
        dataRef.child(monAn.getIdMonAn()).setValue(monAn);
    }

    private void suaMonAn(String id, String str1, String str2, double str3, String str4){
        dataRef.child(id).child("tenMonAn").setValue(str1);
        dataRef.child(id).child("nguyenLieu").setValue(str2);
        dataRef.child(id).child("gia").setValue(str3);
        dataRef.child(id).child("anh").setValue(str4);
    }

    private void xoaMonAn(MonAn monAn){
        dataRef.child(monAn.getIdMonAn()).removeValue();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE && data != null){
            uri = data.getData();
            imgAnh.setImageURI(uri);
//            themAnhVaoFirebaseStorage(uri);
        }
    }

    private void getDanhSachCacMonAn(){
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MonAn> arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MonAn monAn = dataSnapshot.getValue(MonAn.class);
                    arrayList.add(monAn);
                }

                anAdapter.notifyItemInserted(arrayList.size() + 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initView(View v){
        recyclerView = v.findViewById(R.id.recyclerview_danhsach_monan);
        edtTenmonan = v.findViewById(R.id.edittext_tenmonan);
        edtGiatien = v.findViewById(R.id.edittext_giatien);
        edtNguyenlieu = v.findViewById(R.id.edittext_nguyenlieu);
        btnThem = v.findViewById(R.id.button_them);
        btnSua = v.findViewById(R.id.button_sua);
        btnXoa = v.findViewById(R.id.button_xoa);
        imgAnh = v.findViewById(R.id.imageview_anh);
    }
}
