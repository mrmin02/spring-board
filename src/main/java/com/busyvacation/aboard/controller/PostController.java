package com.busyvacation.aboard.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.busyvacation.aboard.db.dto.Post;
import com.busyvacation.aboard.db.dto.Reply;
import com.busyvacation.aboard.db.repository.PostJpaRepository;
import com.busyvacation.aboard.db.repository.ReplyJapRepository;


@Controller
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	PostJpaRepository postRepository;

	@Autowired 
	ReplyJapRepository replyRepository;
	
	@RequestMapping(value= {"/create","/create/"})
	public String addForm() {
		System.out.println("ㅇㅇ");
		return "createPost";
	}
	
	@RequestMapping(value= {"","/"}, method=RequestMethod.POST)
	public String addPost(Principal user, @RequestParam("title") String title, @RequestParam("content") String content) {
		
		Post data = new Post(user.getName(),title,content);
		postRepository.saveAndFlush(data);

		return "redirect:/";
	}
	@RequestMapping(value= {"/{postid}","/{postid}/"})
	public String show(Model model,@PathVariable("postid") int id) {//@PathVariable("postid") int id 
		model.addAttribute("post",postRepository.findByPostid(id));
		model.addAttribute("replyList",replyRepository.findByPostid(id));
		return "show";
	}
	
	
	@RequestMapping(value={"/{postid}/reply","/{postid}/reply/"})
	public String addReply(Model model,Principal user,@PathVariable("postid") int id, @RequestParam("reply") String comment) {
		
//		int postid, String memberid, String comment
		Reply data = new Reply(id,user.getName(),comment);
		replyRepository.saveAndFlush(data);
		
		Post postInfo = postRepository.findByPostid(id);
		ArrayList<Reply> replyList = replyRepository.findByPostid(id);
		
		model.addAttribute("post",postInfo);
		model.addAttribute("replyList",replyList);
		
		return "replyAjax";
	}
	
	@RequestMapping(value= {"/{postid}/modify","/{postid}/modify/"})
	public String modifyPost(@PathVariable("postid") int id, Model model) {
		model.addAttribute("post",postRepository.findByPostid(id));
		return "modify";
	}
	@RequestMapping(value= {"/{postid}","/{postid}/"}, method=RequestMethod.PUT)
	@ResponseBody
	public int modifyCommitPost(@PathVariable("postid") int id,@RequestParam("title") String title,
			@RequestParam("content") String content) {
		
		Post form = postRepository.findByPostid(id);
		form.setTitle(title);
		form.setContents(content);
		postRepository.saveAndFlush(form);
		
		return 1;
	}
	@RequestMapping(value= {"/{postid}","/{postid}/"}, method=RequestMethod.DELETE)
	@ResponseBody
	public int deletePost(@PathVariable("postid") int id) {
		Post data = postRepository.findByPostid(id);
		postRepository.delete(data);
		
		return 1;
	}
	
	@RequestMapping(value= {"/{postid}/reply/{replyId}","/{postid}/reply/{replyId}"}, method=RequestMethod.PUT)
//	@ResponseBody
	public String modifyReply(Model model,@PathVariable("postid") int postId,@PathVariable("replyId") int replyId, @RequestParam("newReply") String newReply) {
		
		System.out.println(postId);
		Reply form = replyRepository.findByReplyid(replyId);
		form.setComment(newReply);
		replyRepository.saveAndFlush(form);
		
		Post postInfo = postRepository.findByPostid(postId);
		ArrayList<Reply> replyList = replyRepository.findByPostid(postId);
		
		model.addAttribute("post",postInfo);
		model.addAttribute("replyList",replyList);
		
		return "replyAjax";
	}
	@RequestMapping(value= {"/{postid}/reply/{replyId}","/{postid}/reply/{replyId}"}, method=RequestMethod.DELETE)
//	@ResponseBody
	public String deleteReply(Model model,@PathVariable("postid") int postId,@PathVariable("replyId") int replyId) {
		
		Reply data = replyRepository.findByReplyid(replyId);
		replyRepository.delete(data);
		
		Post postInfo = postRepository.findByPostid(postId);
		ArrayList<Reply> replyList = replyRepository.findByPostid(postId);
		
		model.addAttribute("post",postInfo);
		model.addAttribute("replyList",replyList);
		
		return "replyAjax";
	}
}
