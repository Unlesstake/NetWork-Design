package com.cn.dao;

import com.cn.entity.Admin;
import com.cn.entity.CommonUser;

public interface AdminDao {
	public Admin login(CommonUser common);
	Admin findByUsername(String username);
	int updateUser(Admin admin);
	public int addAdmin(Admin admin);

}
