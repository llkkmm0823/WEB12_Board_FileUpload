package com.ezen.board.controller.action.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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

public class UpdateBoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		BoardDao bdao = BoardDao.getInstance();
		BoardDto bdto = new BoardDto();
		
		/*
		bdto.setUserid( request.getParameter("userid") ); 
		bdto.setPass(request.getParameter("pass") ); 
		bdto.setEmail( request.getParameter("email")); 
		bdto.setTitle( request.getParameter("title") ); 
		bdto.setContent(request.getParameter("content") ); 
		bdto.setNum( Integer.parseInt(request.getParameter("num") ) );
		 */

		// MultipartRequest를 생성해서 게시물을 수정해주세요
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String path = context.getRealPath("upload");
		
		MultipartRequest multi = new MultipartRequest(
				request,path,20*1024*1024,"UTF-8",new DefaultFileRenamePolicy()
		);
		
		bdto.setUserid(multi.getParameter("userid"));
		bdto.setPass(multi.getParameter("pass"));
		bdto.setEmail(multi.getParameter("email"));
		bdto.setTitle(multi.getParameter("title"));
		bdto.setContent(multi.getParameter("content"));
		bdto.setNum(Integer.parseInt(multi.getParameter("num")));
		//이미지 파일이 로드가 되지 않을 땐 system.out.println으로 newfile을 출력해본 후 오류를 고친다
		
		if(multi.getFilesystemName("newFile")==null)
			bdto.setImgfilename(multi.getParameter("oldFile"));
		else
			bdto.setImgfilename(multi.getFilesystemName("newFile"));
		
		bdao.updateBoard( bdto );
		String url  = "board.do?command=boardViewNoCount&num=" + bdto.getNum();
		RequestDispatcher dp = request.getRequestDispatcher(url);
		dp.forward(request, response);
	}

}
