package com.cn.dao;

import java.util.List;

import com.cn.entity.Admin;
import com.cn.entity.UserInfo;

public interface UserDao {
	UserInfo login(UserInfo user);
	List<UserInfo> findall();		//查找数据
	int adduser(UserInfo user);		//添加用户
	UserInfo findByid(Integer id);	//通过id查找用户
	UserInfo findByUsername(String username);
	int updateUser(UserInfo user);	//更新用户信息
	int delUser(Integer id);		//删除用户
	UserInfo isexist(String username);
}
