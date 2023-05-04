<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>checkSuccess.jsp</title>
</head>
<body>

<script type="text/javascript">
	if( window.name=='update' ){
		//window.opener.location.href 
		// = "board.do?command=updateBoardForm&num=${param.num}&pass=${param.pass}";
		window.opener.location.href = "board.do?command=updateBoardForm&num=${param.num}";
	}else if( window.name=='delete'){
		var bool = confirm("정말로 삭제할까요?");
		if( bool )
			window.opener.location.href = "board.do?command=deleteBoard&num=${param.num}";
	}
	self.close();
</script>

</body>
</html>