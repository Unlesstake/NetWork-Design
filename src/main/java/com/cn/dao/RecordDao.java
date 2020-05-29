package com.cn.dao;

import com.cn.entity.SalesRecord;
import com.cn.entity.Store;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RecordDao {
	int add(List<SalesRecord> list);
	List<SalesRecord> find(Integer id);
	int delete(Integer id);
	List<SalesRecord> findAll();

	int deleteById(List list);
	List<SalesRecord> Query(@Param("store_id") Integer store_id, @Param("goods_name") String goods_name, @Param("sale_number") Integer sale_number,@Param("deal_time") String deal_time);

}
