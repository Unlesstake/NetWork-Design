package com.cn.entity;

public class Sales {
    private Integer id;
    private Integer under_age;
    private Integer puber;
    private Integer young_man;
    private Integer middle_aged;
    private Integer aged;

    public Sales(){

    }

    public Sales(Integer id,Integer under_age,Integer puber,Integer young_man,Integer middle_aged,Integer aged){
        this.id = id;
        this.under_age = under_age;
        this.puber = puber;
        this.young_man = young_man;
        this.middle_aged = middle_aged;
        this.aged = aged;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnder_age() {
        return under_age;
    }

    public void setUnder_age(Integer under_age) {
        this.under_age = under_age;
    }

    public Integer getPuber() {
        return puber;
    }

    public void setPuber(Integer puber) {
        this.puber = puber;
    }

    public Integer getYoung_man() {
        return young_man;
    }

    public void setYoung_man(Integer young_man) {
        this.young_man = young_man;
    }

    public Integer getMiddle_aged() {
        return middle_aged;
    }

    public void setMiddle_aged(Integer middle_aged) {
        this.middle_aged = middle_aged;
    }

    public Integer getAged() {
        return aged;
    }

    public void setAged(Integer aged) {
        this.aged = aged;
    }
}
