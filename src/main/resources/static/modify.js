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

//
//function modify(postid){
//	
//	var csrf = $(".token").val();
//	var title = $(".title").val();
//	var content = $(".content").val();
//	var file = $(".file").val();
//	
//	var token = $("meta[name='_csrf']").attr("content");
//	var header = $("meta[name='_csrf_header']").attr("content");
//	
//	
//	
//	if(!title || !content){
//		return alert("제목과 내용을 입력해주세요.");
//	}
//	
////	var data = new FormData();
////	data.append("title",title);
////	data.append("comment",content);
////	data.append("file",file);
//	console.log(file);
//		
//	$.ajax({
//        url: "/posts/"+postid,
//        type: "PUT",
//        data: {title,content},
//        enctype: 'multipart/form-data',
//        processData: false, //prevent jQuery from automatically transforming the data into a query string
//        contentType: false,
//        cache: false,
//        beforeSend : function(xhr)
//        {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
//			xhr.setRequestHeader(header, token);
//        },
//	
//    }).done(function(data){
//    	console.log(data);
//    	location.href="/posts/"+postid;
//    });
//	
//}
