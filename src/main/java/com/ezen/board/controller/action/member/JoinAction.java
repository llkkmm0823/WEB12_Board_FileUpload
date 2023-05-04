package com.ezen.board.controller.action.member;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezen.board.controller.action.Action;
import com.ezen.board.dao.MemberDao;
import com.ezen.board.dto.MemberDto;

public class JoinAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		// 전달인수들을 모두  Dto 에 넣고 insertMember 에 보내줍니다
		MemberDto mdto = new MemberDto();
		mdto.setUserid(request.getParameter("userid"));
		mdto.setName(request.getParameter("name"));
		mdto.setPwd(request.getParameter("pwd"));
		mdto.setEmail(request.getParameter("email"));
		mdto.setPhone(request.getParameter("phone"));
		mdto.setAdmin( Integer.parseInt( request.getParameter("admin") ) );
						
		MemberDao mdao = MemberDao.getInstance();
		int result = mdao.insertMember( mdto );
		
		String message = "";
		if( result==1) message = "회원가입이 완료되었습니다. 로그인하세요";
		else message = "회원가입이 실패했습니다. 관리자에게 문의하세요";
		
		HttpSession session = request.getSession();
		session.setAttribute("message", message);
		// forward 메서드로 이동한 최종 도착 페이지에서는 새로 고침을 하면 데이터도 한번 더 추가되려고 시도합니다. 
		// 새로고침에 의해 포워딩 이전 코드가 다시 실행되지 않으려면  sendRedirect 를 이용합니다
		response.sendRedirect( "board.do?command=loginForm" );
	}

}
