package com.cn.dao;

import com.cn.entity.Admin;

public interface AdminDao {
	public Admin login(Admin admin);
	
	public int addAdmin(Admin admin);

}
