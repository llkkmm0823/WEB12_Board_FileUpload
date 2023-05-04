<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardCheckPass.jsp</title>
</head>
<body>

<div align="center">
	<h1>비밀번호 확인</h1>
	<form action="board.do" name="frm" method="get">
			<input type="hidden" name="command" value="boardCheckPass">
			<input type="hidden" name="num" value="${param.num}"><!-- 현재 게시물의 게시물 번호 -->
			<table style="width:80%">
				<tr>
					<th>수정/삭제 비밀번호</th>
					<td><input type="password" name="pass" size="20"></td>	
				</tr>
			</table><br>
			<input type="submit" value="확 인 "	onclick="return passCheck()">
			<!-- submit  을 누르면 hiddne으로 숨어있는 현재 게시물의 게시물 번호와 사용자가 수정삭제하고자 입력한
			비밀번호를 갖고 비교하러   command=boardCheckPass 로 이동합니다. -->
			<br><br>${message}
	</form>
</div>

</body>
</html>