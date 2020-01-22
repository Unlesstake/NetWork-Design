package com.cn.entity;

public class UserInfo {
	private Integer id;
	private String username;
	private Integer age;
	private String sex;
	private String phone;
	private String email;
	private String password;

	public UserInfo() {

	}

	public UserInfo(Integer id, String username, Integer age, String sex, String phone, String email, String password) {
		this.id = id;
		this.username = username;
		this.age = age;
		this.sex = sex;
		this.phone = phone;
		this.email = email;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
