package com.busyvacation.aboard.db.repository;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import com.busyvacation.aboard.db.dto.Reply;

public interface ReplyJapRepository extends JpaRepository<Reply,String>{
	public ArrayList<Reply> findByPostid(int postId);
	public Reply findByReplyid(int replyId);
}
