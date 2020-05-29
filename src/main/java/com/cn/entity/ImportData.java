package com.cn.entity;

public class ImportData {
    private String filepath;
    private Integer id;

    public ImportData(String filepath,Integer id){
        this.filepath = filepath;
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
