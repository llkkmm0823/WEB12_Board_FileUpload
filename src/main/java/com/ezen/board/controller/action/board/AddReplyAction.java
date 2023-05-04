package com.ezen.board.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezen.board.controller.action.Action;
import com.ezen.board.dao.BoardDao;
import com.ezen.board.dto.ReplyDto;

public class AddReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ReplyDto rdto = new ReplyDto();
		// 댓글 추가후 돌아갈 게시물 번호 저장
		int boardnum = Integer.parseInt( request.getParameter("boardnum") );
		
		// 아이디, 내용, 게시물 번호를 rdto 에 저장
		rdto.setUserid(request.getParameter("userid"));
		rdto.setContent(request.getParameter("reply"));
		rdto.setBoardnum( boardnum );
		
		BoardDao bdao = BoardDao.getInstance();
		bdao.insertReply(rdto);
		
		// 조회수가 늘어나지 않는 boardViewNoCount 를 생성해서 실행
		response.sendRedirect("board.do?command=boardViewNoCount&num="+boardnum);

	}

}
