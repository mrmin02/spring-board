package com.busyvacation.aboard.controller;

import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.busyvacation.aboard.db.dto.Post;
import com.busyvacation.aboard.db.dto.Reply;
import com.busyvacation.aboard.db.repository.PostJpaRepository;
import com.busyvacation.aboard.db.repository.ReplyJapRepository;
import com.busyvacation.aboard.db.service.StorageService;
import com.busyvacation.aboard.exception.StorageFileNotFoundException;

@Controller
@RequestMapping("/posts")
public class PostController {
	
	@Autowired
	PostJpaRepository postRepository;

	@Autowired 
	ReplyJapRepository replyRepository;
	
	@Autowired  
	private StorageService storageService; 
	
	@RequestMapping(value= {"/create","/create/"})
	public String addForm() {
		System.out.println("ㅇㅇ");
		return "createPost";
	}
	
	@RequestMapping(value= {"","/"}, method=RequestMethod.POST)
	public String addPost(Principal user, @RequestParam("title") String title, 
			@RequestParam("content") String content,@RequestParam("file") MultipartFile file) {
		Post data = new Post(user.getName(),title,content);
		
		if(!file.isEmpty()) {
			System.out.println("사진 있음");
//			data.setAttach(file);
			storageService.store(file);
			String filename = StringUtils.cleanPath(file.getOriginalFilename());
			data.setAttach(filename);
			System.out.println("사진 저장 완료");
		}
		
		postRepository.saveAndFlush(data);

		return "redirect:/";
	}
	@RequestMapping(value= {"/{postid}","/{postid}/"})
	public String show(Model model,@PathVariable("postid") int id) {//@PathVariable("postid") int id 
		Post post = postRepository.findByPostid(id);
		model.addAttribute("post",post);
		model.addAttribute("replyList",replyRepository.findByPostid(id));
		
		
//		Stream<Path> img  =
		
//		model.addAttribute("files", 		  
//			  storageService.loadAll().map(	
//			  path -> MvcUriComponentsBuilder.fromMethodName(PostController.class,                  
//					  "serveFile", path.getFileName().toString()).build().toString())              
//			  .collect(Collectors.toList())); 
		
//		model.addAttribute("file",post.getAttach());
		
		return "show";
	}
//	@RequestMapping(value="/files/{filename:.+}", method=RequestMethod.GET)
//	public ResponseEntity<Resource> serveFile(@PathVariable String filename){
//		// 다운로드
//		Resource file = storageService.loadAsResource(filename);
//		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getFilename()+"\"").body(file);
//	}
	
	
	
	
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc){
		return ResponseEntity.notFound().build();
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
