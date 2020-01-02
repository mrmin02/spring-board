
function modify(postid){
	
	var csrf = $(".token").val();
	var title = $(".title").val();
	var content = $(".content").val();
	if(!title || !content){
		return alert("제목과 내용을 입력해주세요.");
	}
	
	$.ajax({
        url: "/posts/"+postid,
        type: "PUT",
        data: {postid,title,content,"_csrf":csrf},
    }).done(function(data){
    	console.log(data);
    	location.href="/posts/"+postid;
    });
	
}
