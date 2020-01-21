package com.cn.entity;

public class Admin {
	private Integer id;
	private String name;
	private Integer password;
	private String job;
	
	public Admin() {
		
	}
	
	public Admin(Integer id, String name, Integer password, String job) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.job = job;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPassword() {
		return password;
	}

	public void setPassword(Integer password) {
		this.password = password;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

}
