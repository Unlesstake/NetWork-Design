package com.cn.dao;

import com.cn.entity.Sales;


public interface SalesDao {
	int update(Sales sales);
	Sales find(Integer id);
	int delete(Integer id);
	int add(Sales sales);

}
