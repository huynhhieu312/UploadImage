package com.loginappfish.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hieuhuynh on 9/2/17.
 */

public class Tour {
    private String hinh;
    private String tentour;
    private String phuongtien;
    private String ngaykhoihanh;
    private String giatour;

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getTentour() {
        return tentour;
    }

    public void setTentour(String tentour) {
        this.tentour = tentour;
    }

    public String getPhuongtien() {
        return phuongtien;
    }

    public void setPhuongtien(String phuongtien) {
        this.phuongtien = phuongtien;
    }

    public String getNgaykhoihanh() {
        return ngaykhoihanh;
    }

    public void setNgaykhoihanh(String ngaykhoihanh) {
        this.ngaykhoihanh = ngaykhoihanh;
    }

    public String getGiatour() {
        return giatour;
    }

    public void setGiatour(String giatour) {
        this.giatour = giatour;
    }

    public Tour(String hinh, String tentour, String phuongtien, String ngaykhoihanh, String giatour) {
        this.hinh = hinh;
        this.tentour = tentour;
        this.phuongtien = phuongtien;
        this.ngaykhoihanh = ngaykhoihanh;
        this.giatour = giatour;
    }
    public Tour(JSONObject json) throws JSONException {
        this.hinh = json.getString("UrlHinh");
        this.tentour = json.getString("NameTour");
        this.phuongtien = json.getString("Vehicle");
        this.ngaykhoihanh = json.getString("Description");
        this.giatour = json.getString("Price");;
    }

    @Override
    public String toString() {
        return this.getNgaykhoihanh();
    }
}
