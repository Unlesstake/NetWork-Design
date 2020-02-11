package com.cn.entity;

import java.util.ConcurrentModificationException;
/*此实体类用来登录时，作为中间的交接者*/
public class CommonUser {
    String username;
    String password;
    String role;
    public CommonUser(){

    }
    public CommonUser(String username,String password,String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
