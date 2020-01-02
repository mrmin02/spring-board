package com.busyvacation.aboard.db.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // DB 와 연결할 클래스
@Table(name="member") // 테이블 이름 지정
public class Member {
	
	@Id //primarykey
	@Column(name="memberid", nullable=false)
	private String memberid;
	
	@Column
	private String password;
	
	
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
