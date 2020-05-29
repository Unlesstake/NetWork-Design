package com.cn.entity;


import org.omg.PortableInterceptor.INACTIVE;

import java.util.Date;

public class SalesRecord {
    private Integer Id;
    private Integer StoreId;
    private String StoreName;
    private String GoodsName;
    private Double Price;
    private String Unit;
    private Integer Number;
    private Double TotalPrice;
    private Integer BuyerAge;
    private String DealTime;

    public SalesRecord(){

    }
    public SalesRecord(Integer Id, Integer StoreId, String StoreName, String GoodsName, Double Price, String Unit, Integer Number,
                       Double TotalPrice, Integer BuyerAge, String DealTime){
        this.Id = Id;
        this.StoreId = StoreId;
        this.StoreName = StoreName;
        this.GoodsName = GoodsName;
        this.Price = Price;
        this.Unit = Unit;
        this.Number = Number;
        this.TotalPrice = TotalPrice;
        this.BuyerAge = BuyerAge;
        this.DealTime = DealTime;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public Integer getStoreId() {
        return StoreId;
    }

    public void setStoreId(Integer storeId) {
        this.StoreId = storeId;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        this.StoreName = storeName;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        this.GoodsName = goodsName;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        this.Price = price;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        this.Unit = unit;
    }

    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        this.Number = number;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.TotalPrice = totalPrice;
    }

    public Integer getBuyerAge() {
        return BuyerAge;
    }

    public void setBuyerAge(Integer BuyerAge) {
        this.BuyerAge = BuyerAge;
    }

    public String getDealTime() {
        return DealTime;
    }

    public void setDealTime(String dealTime) {
        this.DealTime = dealTime;
    }
}
