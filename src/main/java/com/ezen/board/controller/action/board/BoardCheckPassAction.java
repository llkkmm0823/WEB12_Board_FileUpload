package com.ezen.board.controller.action.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezen.board.controller.action.Action;
import com.ezen.board.dao.BoardDao;
import com.ezen.board.dto.BoardDto;

public class BoardCheckPassAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 전달된 게시물번호로 게시물을 조회 & 조회된 결과에서 비번 조회
		// 전달된 비번과 조회한 비번을 비교
		int num = Integer.parseInt( request.getParameter("num") );  // 게시물 번호
		String pass = request.getParameter("pass");  // 입력한 비밀번호
		BoardDao bdao  = BoardDao.getInstance();
		BoardDto bdto = bdao.getBoard( num );
		
		// bdto.getPass() 와 pass 에 담긴 두값을 비교 처리 합니다
		String url = null;
		if( bdto.getPass()==null) { // 데이터베이스 오류. 
			request.setAttribute("message", "비밀번호오류 관리에게 문의 하세요");
			url = "board/boardCheckPass.jsp";
		} else if( bdto.getPass().equals( pass) ) {	// 둘이 같다면, checkSuccess.jsp 로 이동
			url = "board/checkSuccess.jsp";
			HttpSession session = request.getSession();
			session.setAttribute("pass", "T");
		} else {
			request.setAttribute("message", "비밀번호가 틀렸습니다.");
			url = "board/boardCheckPass.jsp"; // 비번 입력하는곳으로 되돌아 갑니다
		}
		RequestDispatcher dp = request.getRequestDispatcher(url);
		dp.forward(request, response);
	}

}
