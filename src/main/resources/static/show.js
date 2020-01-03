function deletePost(postid){
	var csrf = $(".token").val();
	
	$.ajax({
        url: "/posts/"+postid,
        type: "DELETE",
        data: {"_csrf":csrf},
    }).done(function(data){
    	console.log(data);
    	location.href="/";
    });
	
}

function addReply(postId){
	var reply = $(".reply").val();
	var csrf = $(".token").val();
	
	if(reply.length==0){
		return alert("등록할 댓글의 내용을 확인해 주세요.");
	}
	$.ajax({
        url: "/posts/"+postId+"/reply",
        type: "POST",
        data: {reply,"_csrf":csrf},
    }).done(function(data){
    	console.log("등록 ajax 성공");
    	$(".reply").val('');
    	$(".replyUl").html('');
    	$(".replyUl").append(data);
    });
}

function modi_click(replyId,postId){
	console.log("ㅁㅁ");
	var preReply = $(".answer"+replyId).html();
	console.log(preReply);
	$(".div"+replyId).html('');
	
	
	$(".div"+replyId).append(`<p>본인</p><textArea class="newReply${replyId}">${preReply}</textArea><br>`);
	$(".div"+replyId).append(`<button type="button" onclick="modi_commit(${replyId},${postId})">수정완료</button><button type="button" onclick="delete_ans(${replyId},${postId})">삭제</button>`);
}


function modi_commit(replyId,postId){
	var csrf = $(".Reply_token").val();
	
	var newReply = $(".newReply"+replyId).val();
	var token = $(".Reply_token").val();
	
	if(newReply.length==0){
		return alert("변경할 내용을 입력해주세요.");
	}
	
	console.log(replyId);
	$.ajax({
        url: "/posts/"+postId+"/reply/"+replyId,
        type: "PUT",
        data: {newReply,"_csrf":csrf},
    }).done(function(data){
    	console.log("수정 ajax 성공");
    	$(".replyUl").html('');
    	$(".replyUl").append(data);
    });
}


function delete_ans(replyId,postId){
var csrf = $(".Reply_token").val();
	
	$.ajax({
		url: "/posts/"+postId+"/reply/"+replyId,
        type: "DELETE",
        data: {"_csrf":csrf},
    }).done(function(data){
    	console.log("삭제 ajax 성공");
    	$(".replyUl").html('');
    	$(".replyUl").append(data);
    });
}