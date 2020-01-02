package com.busyvacation.aboard.db.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.busyvacation.aboard.db.dto.Post;

public interface PostJpaRepository extends JpaRepository<Post,String>{

	public ArrayList<Post> findAll();
	public Post findByPostid(int id);
	public void deleteByPostid(int id);
}
