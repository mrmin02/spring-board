package com.busyvacation.aboard.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busyvacation.aboard.db.dto.Member;


public interface MemberJpaRepository extends JpaRepository<Member, String>{
	public Member findByMemberid(String name);

}
