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
}
