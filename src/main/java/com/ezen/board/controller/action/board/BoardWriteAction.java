package com.ezen.board.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezen.board.controller.action.Action;
import com.ezen.board.dao.BoardDao;
import com.ezen.board.dto.BoardDto;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 전송된 항목들은 BoardDto에 담아서 BoardDao 의 insertBoard 메서드로 레코드를 추가해주세요
		BoardDao bdao = BoardDao.getInstance();
		BoardDto bdto = new BoardDto();
		
		/*
		  bdto.setUserid( request.getParameter("userid") ) ; 
		  bdto.setPass(request.getParameter("pass") ); 
		  bdto.setTitle( request.getParameter("title")); 
		  bdto.setEmail( request.getParameter("email") ); 
		  bdto.setContent(request.getParameter("content") );
		 */
		
		//MultipartRequest를 생성하고 전달인수들을 넣어서 dto를 완성하세요
		
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String path = context.getRealPath("upload");
		
		MultipartRequest multi = new MultipartRequest(
				request,path,5*1024*1024,"UTF-8",new DefaultFileRenamePolicy()
		);
		
		bdto.setUserid(multi.getParameter("userid"));
		bdto.setPass(multi.getParameter("pass"));
		bdto.setTitle(multi.getParameter("title"));
		bdto.setEmail(multi.getParameter("email"));
		bdto.setContent(multi.getParameter("content"));
		bdto.setImgfilename(multi.getFilesystemName("uploadFile"));


		bdao.insertBoard( bdto );
		response.sendRedirect("board.do?command=main");

	}

}
