package com.cn.entity;

public class Store {
    private Integer id;
    private String store_name;
    private String city;
    private Double lon;
    private Double lat;
    private String phone;
    private String business_scope;

    public Store(){

    }

    public Store(Integer id,String store_name,String city,Double lon,Double lat,String phone,String business_scope){
        this.id = id;
        this.store_name = store_name;
        this.city = city;
        this.lon = lon;
        this.lat = lat;
        this.phone = phone;
        this.business_scope = business_scope;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusiness_scope() {
        return business_scope;
    }

    public void setBusiness_scope(String business_scope) {
        this.business_scope = business_scope;
    }
}
