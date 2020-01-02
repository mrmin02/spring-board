package com.busyvacation.aboard.db.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity // DB 와 연결할 클래스
@Table(name="post") // 테이블 이름 지정
public class Post {
	@Id
	@Column(nullable=true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int postid;
	@Column
	private String title;
	@Column
	private String memberid;
	@Column
	private String contents;
	@Column(nullable=true)
	private String attach;
	
	public Post() {}
	public Post(String memberid, String title, String contents){
		this.title = title;
		this.memberid = memberid;
		this.contents = contents;
	}
	
	public int getPostid() {
		return postid;
	}
	public void setPostid(int postid) {
		this.postid = postid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
}
