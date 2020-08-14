package com.philipstudio.pizzaplanadmin.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class NguoiDung {
    private String idNguoiDung;
    private String emailorPhoneNumber;
    private String anhdaidien;
    private String matkhau;
    private String tennguoidung;
    private String diachi;
    private String ngaysinh;
    private String gioitinh;

    public NguoiDung(){}

    public NguoiDung(String idNguoiDung, String emailorPhoneNumber, String anhdaidien, String matkhau, String tennguoidung, String diachi, String ngaysinh, String gioitinh) {
        this.idNguoiDung = idNguoiDung;
        this.emailorPhoneNumber = emailorPhoneNumber;
        this.anhdaidien = anhdaidien;
        this.matkhau = matkhau;
        this.tennguoidung = tennguoidung;
        this.diachi = diachi;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
    }

    public String getIdNguoiDung() {
        return idNguoiDung;
    }

    public String getEmailorPhoneNumber() {
        return emailorPhoneNumber;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public String getTennguoidung() {
        return tennguoidung;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public String getAnhdaidien() {
        return anhdaidien;
    }

    public String getGioitinh() {
        return gioitinh;
    }
}
