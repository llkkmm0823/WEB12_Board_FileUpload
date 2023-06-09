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

public class UpdateMemberAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MemberDto mdto = new MemberDto();
		mdto.setUserid(request.getParameter("userid"));
		mdto.setName(request.getParameter("name"));
		mdto.setPwd(request.getParameter("pwd"));
		mdto.setPhone(request.getParameter("phone"));
		mdto.setEmail(request.getParameter("email"));
		mdto.setAdmin( Integer.parseInt( request.getParameter("admin") ) );

		MemberDao mdao = MemberDao.getInstance();
		int result = mdao.updateMember(mdto);
		
		if( result == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", mdto);
		}
		
		RequestDispatcher dp = request.getRequestDispatcher("board.do?command=main");
		dp.forward(request, response);

	}

}
