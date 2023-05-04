<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardView.jsp</title>
<link rel="stylesheet" type="text/css" href="css/board.css">
<script src="script/board.js"></script>
</head>
<body>
	<div id="wrap" align="center">
		<h1>게시글 상세보기</h1>
		<table>
			<tr>
				<th width="100">작성자</th>
				<td width="330">${board.userid}</td>
				<th width="100">이메일</th>
				<td>${board.email}</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td><fmt:formatDate value="${board.writedate}" /></td>
				<th>조회수</th>
				<td>${board.readcount }</td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3">${board.title}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="2"><pre>${board.content}</pre></td>
				<td align="center">
					<c:choose>
						<c:when test="${empty board.imgfilename}">
							<img src="upload/noname.jpg" height="300">
						</c:when>
						<c:otherwise>
							<img src="upload/${board.imgfilename}" height="300">
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table><br><br> 
		
		
		<input type="button" value="리스트로"
				onClick="location.href='board.do?command=main'"> 
		<input type="button" value="수정"
				onClick="checkPass('${board.num}' ,  'update');"> 
		<input	type="button" value="삭제"
				onClick="checkPass('${board.num}' ,  'delete');"> <br>
		<br>
		
		
		
		
		
		<!-- 여기서 부터 댓글 작성 영역 -->

		<c:set var="now" value="<%=new java.util.Date()%>" />
		<form action="board.do" method="get" name="frm_reply">
			<input type="hidden" name="command" value="addReply" /> <input
				type="hidden" name="boardnum" value="${board.num}" />
			<table>
				<tr>
					<th width="100">작성자</th>
					<th width="100">작성일시</th>
					<th>내용</th>
					<th width="100">추가/삭제</th>
				</tr>

				<tr align="center">
					<td>${loginUser.userid}<input type="hidden" name="userid"
						value="${loginUser.userid}"></td>
					<td><fmt:formatDate value="${now}" pattern="MM/dd HH:mm" /></td>
					<td><input type="text" name="reply" size="80"></td>
					<td><input type="submit" value="답글 작성"
						onClick="return reply_check();"></td>
				</tr>

				<c:forEach items="${replyList}" var="reply">
					<tr align="center">
						<td>${reply.userid}</td>
						<td><fmt:formatDate value="${reply.writedate}"
								pattern="MM/dd HH:mm" /></td>
						<td align="left">&nbsp;${reply.content}</td>
						<td>
							<c:if test="${reply.userid==loginUser.userid}">
								<input type="button" value="삭제"	
								onClick = "location.href='board.do?command=deleteReply&replynum=${reply.replynum}&boardnum=${board.num}'">
							</c:if>&nbsp;<!-- 로그인 한 유저가 쓴 댓글만 삭제할수 있게 버튼을 표시합니다 --></td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>









