package com.philipstudio.pizzaplanadmin;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class GioHang implements Parcelable {
    private String id;
    private MonAn monAn;
    private int soluong;

    public GioHang() {
    }

    public GioHang(String id, MonAn monAn, int soluong) {
        this.id = id;
        this.monAn = monAn;
        this.soluong = soluong;
    }

    protected GioHang(Parcel in) {
        id = in.readString();
        soluong = in.readInt();
        monAn = (MonAn) in.readSerializable();
    }

    public static final Creator<GioHang> CREATOR = new Creator<GioHang>() {
        @Override
        public GioHang createFromParcel(Parcel in) {
            return new GioHang(in);
        }

        @Override
        public GioHang[] newArray(int size) {
            return new GioHang[size];
        }
    };

    public String getId() {
        return id;
    }

    public MonAn getMonAn() {
        return monAn;
    }

    public int getSoluong() {
        return soluong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(soluong);
        dest.writeSerializable((Serializable) monAn);
    }
}
