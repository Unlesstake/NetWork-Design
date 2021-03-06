package com.cn.dao;

import java.util.List;

import com.cn.entity.CommonUser;
import com.cn.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
	UserInfo login(CommonUser common);
	List<UserInfo> findall(Integer startIndex);		//查找数据
	int adduser(UserInfo user);		//添加用户
	UserInfo findByid(Integer id);	//通过id查找用户
	UserInfo findByUsername(String username);
	int updateUser(UserInfo user);	//更新用户信息
	int delUser(String username);		//删除用户
	UserInfo isexist(String username);
	List<UserInfo> Query(@Param("username") String username,@Param("age") Integer age,@Param("sex") String sex,@Param("phone") String phone,Integer startIndex);
}
