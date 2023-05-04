package com.ezen.board.controller.action.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezen.board.controller.action.Action;
import com.ezen.board.dao.MemberDao;
import com.ezen.board.dto.MemberDto;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		
		MemberDao mdao = MemberDao.getInstance();
		MemberDto mdto = mdao.getMember(userid);
		
		String url = "member/loginForm.jsp";
		
		if( mdto==null) request.setAttribute("message", "아이디가 없습니다");
		else if( mdto.getPwd() == null) request.setAttribute("message", "데이터베이스 오류");
		else if( !mdto.getPwd().equals(pwd) ) request.setAttribute("message", "비밀번호 틀림");
		else if( mdto.getPwd().equals(pwd) ) {
			url = "board.do?command=main";
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", mdto);
		}else request.setAttribute("message", "관리자에게 문의하세요");
		
		RequestDispatcher dp = request.getRequestDispatcher(url);
		dp.forward(request, response);

	}

}
