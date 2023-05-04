package com.ezen.board.controller.action.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezen.board.controller.action.Action;
import com.ezen.board.dao.BoardDao;
import com.ezen.board.dto.BoardDto;
import com.ezen.board.dto.ReplyDto;

public class BoardViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 전달받은 게시물 번호로 게시물을 조회해서 boardDto에 리턴받고, 이를 다시 request에 저장해서
		// boardView.jsp 로 포워딩합니다
		int num = Integer.parseInt( request.getParameter("num") );
		BoardDao bdao = BoardDao.getInstance();
		
		bdao.plusOneReadcount( num ); //조회수 증가 메서드 호출
		
		ArrayList<ReplyDto> list = bdao.selectReply( num );
		request.setAttribute("replyList", list);
		
		BoardDto bdto = bdao.getBoard(num);
		request.setAttribute("board", bdto);
		
		HttpSession session = request.getSession();
		session.removeAttribute("pass");
		
		RequestDispatcher rd = request.getRequestDispatcher("board/boardView.jsp");
		rd.forward(request, response);

	}
}



