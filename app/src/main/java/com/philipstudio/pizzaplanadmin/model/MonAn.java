package com.philipstudio.pizzaplanadmin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MonAn implements Parcelable {
    private String idMonAn;
    private String tenMonAn;
    private String anh;
    private String nguyenLieu;
    private double gia;

    public MonAn(){}

    public MonAn(String idMonAn, String tenMonAn, String anh, String nguyenLieu, double gia) {
        this.idMonAn = idMonAn;
        this.tenMonAn = tenMonAn;
        this.anh = anh;
        this.nguyenLieu = nguyenLieu;
        this.gia = gia;
    }

    protected MonAn(Parcel in) {
        idMonAn = in.readString();
        tenMonAn = in.readString();
        anh = in.readString();
        nguyenLieu = in.readString();
        gia = in.readDouble();
    }

    public static final Creator<MonAn> CREATOR = new Creator<MonAn>() {
        @Override
        public MonAn createFromParcel(Parcel in) {
            return new MonAn(in);
        }

        @Override
        public MonAn[] newArray(int size) {
            return new MonAn[size];
        }
    };

    public String getTenMonAn() {
        return tenMonAn;
    }

    public String getAnh() {
        return anh;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getNguyenLieu() {
        return nguyenLieu;
    }

    public void setNguyenLieu(String nguyenLieu) {
        this.nguyenLieu = nguyenLieu;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getIdMonAn() {
        return idMonAn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idMonAn);
        dest.writeString(tenMonAn);
        dest.writeString(anh);
        dest.writeString(nguyenLieu);
        dest.writeDouble(gia);
    }
}
