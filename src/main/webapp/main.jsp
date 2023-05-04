<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${empty loginUser}">
	<jsp:forward page='board.do?command=loginForm' />
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main.jsp</title>
<link rel="stylesheet" type="text/css" href="css/board.css">
</head>
<body>

<div id="wrap" align="center">
	<h1>게시글 리스트</h1>
	<table>
		<tr>
			<td colspan="5" style="border:white;">
				<div style="float:left;">
					${loginUser.name}(${loginUser.userid})님 로그인
					<input type="button" value="회원정보수정" 
						onClick="location.href='board.do?command=updateMemberForm'"/>
					<input type="button" value="로그아웃"	
						onClick="location.href='board.do?command=logout'">
				</div>
				<div style="float:right;"><a href="board.do?command=boardWriteForm">게시글 등록</a></div>
			</td>
		</tr>
		
		<tr><th>번호</th><th>제목</th><th>작성자</th><th>작성일</th><th>조회수</th></tr>
		<c:forEach items="${bList}" var="board">
			<tr align="center">
				<td width="100">${board.num}</td>
				<td align="left">
					<a href="board.do?command=boardView&num=${board.num}">
						${board.title}
					</a>
					
					<c:if test="${board.replycnt>0}">
						<span style ="font-weight:bold; color:red;">[${board.replycnt}]</span>
					</c:if>
					
					<c:if test="${not empty board.imgfilename}">
						<span style ="font-size:90%; font-weight:bold; color:blue;">[img]</span>
					</c:if>
					
				</td>
				<td width="100">${board.userid}</td>
				<td width="200"><fmt:formatDate value="${board.writedate}" /></td>
				<td width="100">${board.readcount}</td>
			</tr>
		</c:forEach>
		
	</table><br><br>
	<!-- 여기서부터 페이지 표시 -->	
	<div id="paging" style="margin:0 auto; font-size:110%; font-weight:bold">
		<!-- 페이지가 클릭될때마다 이동할 링크 기본경로를 JSTL변수에 저장 -->
		<c:url var="action" value="board.do?command=main" />
		
		<!-- prev표시 : 전달된 paging 의 prev 변수가 true 이면 표시, false이면 표시하지 않음 -->
		<c:if test="${paging.prev}">
			<a href="${action}&page=${paging.beginPage-1}">◀</a>&nbsp;
		</c:if>
		<!-- 링크되는 주소 -> board.do?command=main&page=?? -->
		<!-- 맨 왼쪽 페이지(beginPage 보다 1페이지 작은 페이지)로 이동 -->
		
		<!-- beginPage 부터 endPage 값까지 페이지 표시 & 해당페이지에 링크 -->
		<c:forEach begin="${paging.beginPage}"  end="${paging.endPage}"  var="index">
			<!-- 현재 페이지와 페이수가 같으면  [] 안에, 그렇지 않으면 숫자만 표시 -->
			<c:choose>
				<c:when test="${index==paging.page}">[${index}]</c:when>
				<c:otherwise><a href="${action}&page=${index}">&nbsp;${index}&nbsp;</a></c:otherwise>
			</c:choose>
		</c:forEach>
		
		<!-- next표시 : 전달된 paging 의 next 변수가 true 이면 표시, false이면 표시하지 않음 -->
		<c:if test="${paging.next}">
			<a href="${action}&page=${paging.endPage+1}">▶</a>&nbsp;
		</c:if>
		<!-- 맨 오른쪽 페이지(endPage 보다 1페이지 큰 페이지)로 이동 -->
	</div>	
</div>

</body>
</html>












