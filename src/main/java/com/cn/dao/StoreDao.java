package com.cn.dao;

import java.util.List;

import com.cn.entity.CommonUser;
import com.cn.entity.Store;
import org.apache.ibatis.annotations.Param;

public interface StoreDao {
    List<Store> FindAll();      //查询所有电商数据
    Store FindById(Integer id);  //通过id查找对用电商数据
    int UpdateStore(Store store); //更新电商数据
    int DelStore(String store_name);  //删除电商数据
    int AddStore(Store store);      //添加电商数据
    Store IsExist(String store_name);   //判断电商名是否存在
    List<Store> Query(@Param("store_name") String store_name,@Param("city") String city,@Param("business_scope") String business_scope,@Param("phone") String phone);
}
