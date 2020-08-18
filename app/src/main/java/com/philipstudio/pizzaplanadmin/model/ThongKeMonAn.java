package com.philipstudio.pizzaplanadmin.model;

public class ThongKeMonAn {
    private String tenMonAn;
    private int soluongbanduoc;

    public ThongKeMonAn(String tenMonAn, int soluongbanduoc) {
        this.tenMonAn = tenMonAn;
        this.soluongbanduoc = soluongbanduoc;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public int getSoluongbanduoc() {
        return soluongbanduoc;
    }
}
