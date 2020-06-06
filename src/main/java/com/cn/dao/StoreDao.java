package com.cn.dao;

import java.util.List;
import java.util.Map;

import com.cn.entity.CommonUser;
import com.cn.entity.Store;
import com.cn.entity.StoreAndSales;
import org.apache.ibatis.annotations.Param;

public interface StoreDao {
    List<Store> FindAll(Map<String,Integer> map);      //查询所有电商数据
    List<StoreAndSales> StoreAndSales();
    Store FindById(Integer id);  //通过id查找电商数据
    int UpdateStore(Store store); //更新电商数据
    int DelStore(Integer id);  //删除电商数据
    int AddStore(Store store);      //添加电商数据
    List<Store> Query(@Param("store_name") String store_name,@Param("city") String city,@Param("business_scope") String business_scope,@Param("phone") String phone,Integer startIndex);
}
