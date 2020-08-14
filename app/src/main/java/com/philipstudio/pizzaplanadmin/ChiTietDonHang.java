package com.philipstudio.pizzaplanadmin;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class ChiTietDonHang {
    private String idNguoiDung;
    private String idDonHang;
    private ArrayList<GioHang> listDanhSachGioHang;
    private String thoigiandathang;

    public ChiTietDonHang(){}

    public ChiTietDonHang(String idNguoiDung, String idDonHang, ArrayList<GioHang> listDanhSachGioHang, String thoigiandathang) {
        this.idNguoiDung = idNguoiDung;
        this.idDonHang = idDonHang;
        this.listDanhSachGioHang = listDanhSachGioHang;
        this.thoigiandathang = thoigiandathang;
    }

    public String getIdNguoiDung() {
        return idNguoiDung;
    }

    public String getIdDonHang() {
        return idDonHang;
    }

    public ArrayList<GioHang> getListDanhSachGioHang() {
        return listDanhSachGioHang;
    }

    public String getThoigiandathang() {
        return thoigiandathang;
    }
}
