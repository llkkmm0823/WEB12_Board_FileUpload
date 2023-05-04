package com.ezen.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ezen.board.dto.BoardDto;
import com.ezen.board.dto.ReplyDto;
import com.ezen.board.util.Dbm;
import com.ezen.board.util.Paging;

public class BoardDao {

	private BoardDao() {}
	private static BoardDao itc = new BoardDao();
	public static BoardDao getInstance() { return itc; }
	
	Connection con=null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public ArrayList<BoardDto> selectBoard( Paging paging ) {
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		// String sql = "select * from board order by num desc";
		String sql = "select * from( "
				+ " select * from(	"
				+ " select rownum as rn, b.* from( ( select * from board order by num desc ) b ) "
				+ " ) where rn >=? "
				+ " ) where rn<=? ";
		con = Dbm.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  paging.getStartNum() );
			pstmt.setInt(2,  paging.getEndNum() );
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDto bdto = new BoardDto();
				bdto.setNum(  rs.getInt("num") );
				bdto.setPass(  rs.getString("pass") );
				bdto.setUserid(  rs.getString("userid") );
				bdto.setEmail(  rs.getString("email") );
				bdto.setTitle(  rs.getString("title") );
				bdto.setContent(  rs.getString("content") );
				bdto.setReadcount(  rs.getInt("readcount") );
				bdto.setWritedate(  rs.getTimestamp("writedate") );
				bdto.setImgfilename(  rs.getString("imgfilename") );

				list.add(bdto);
			}
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbm.close(con, pstmt, rs);
		}
		return list;
	}

	public void insertBoard(BoardDto bdto) {
		
		String sql = "insert into board(num, userid, pass, email, title, content, imgfilename) "
				+ " values( board_seq.nextVal , ?,?,?,?,?,?)";
		con = Dbm.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  bdto.getUserid() );
			pstmt.setString(2,  bdto.getPass() );
			pstmt.setString(3,  bdto.getEmail() );
			pstmt.setString(4,  bdto.getTitle() );
			pstmt.setString(5,  bdto.getContent() );	
			pstmt.setString(6,  bdto.getImgfilename() );			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {  e.printStackTrace();
		} finally { Dbm.close(con, pstmt, rs);   }
		
	}

	public BoardDto getBoard(int num) {
		BoardDto bdto = new BoardDto();
		con = Dbm.getConnection();
		// String sql = "update board set readcount = readcount + 1 where num=?";
		String sql = "select * from board where num=?";
		try {
			/*/-------------------------------------------
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  num);
			pstmt.executeUpdate();
			pstmt.close();
			
			sql = "select * from board where num=?";
			//--------------------------------------------*/
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bdto.setNum(  rs.getInt("num") ) ;
				bdto.setPass(  rs.getString("pass") ) ;
				bdto.setUserid(  rs.getString("userid") ) ;
				bdto.setEmail(  rs.getString("email") ) ;
				bdto.setTitle(  rs.getString("title") ) ;
				bdto.setContent(  rs.getString("content") ) ;
				bdto.setReadcount(  rs.getInt("readcount") ) ;
				bdto.setWritedate(  rs.getTimestamp("writedate") ) ;
				bdto.setImgfilename(  rs.getString("imgfilename") ) ;

			}
		} catch (SQLException e) {  e.printStackTrace();
		} finally {  Dbm.close(  con, pstmt, rs);    }
		return bdto;
	}

	public void plusOneReadcount(int num) {
		con = Dbm.getConnection();
		String sql = "update board set readcount = readcount+1 where num=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  num);
			pstmt.executeUpdate();
		} catch (SQLException e) {  e.printStackTrace();
		} finally {  Dbm.close(  con, pstmt, rs);    }
	}

	public ArrayList<ReplyDto> selectReply(int num) {
		ArrayList<ReplyDto> list = new ArrayList<ReplyDto>();
		con = Dbm.getConnection();
		String sql = "select * from reply where boardnum=? order by replynum desc";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  num);
			rs = pstmt.executeQuery();
			while( rs.next() ) {
				ReplyDto rdto = new ReplyDto();
				rdto.setReplynum(rs.getInt("replynum"));
				rdto.setBoardnum( rs.getInt("boardnum") );
				rdto.setUserid(rs.getString("userid"));
				rdto.setWritedate(rs.getTimestamp("writedate") );
				rdto.setContent(rs.getNString("content"));
				list.add(rdto);
			}
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbm.close(con, pstmt, rs);   }
		
		return list;
	}

	public void insertReply(ReplyDto rdto) {
		String sql = "insert into reply( replynum, boardnum, userid, content ) "
				+ " values( reply_seq.nextVal, ? , ? , ? )";
		con = Dbm.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  rdto.getBoardnum());
			pstmt.setString(2, rdto.getUserid());
			pstmt.setString(3, rdto.getContent());
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbm.close(con, pstmt, rs);  }
		
	}

	public void deleteReply(String replynum) {
		
		String sql = "delete from reply where replynum=?";
		con = Dbm.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt( 1,  Integer.parseInt( replynum ) );
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbm.close(con, pstmt, rs);  }	
		
	}

	public void updateBoard(BoardDto bdto) {
		String sql = "update board set userid=?, pass=?, email=?, title=?, content=?, imgfilename=? where num=? ";
		con = Dbm.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bdto.getUserid());
			pstmt.setString(2, bdto.getPass());
			pstmt.setString(3, bdto.getEmail());
			pstmt.setString(4, bdto.getTitle());
			pstmt.setString(5, bdto.getContent());
			pstmt.setString(6, bdto.getImgfilename());
			pstmt.setInt(7, bdto.getNum());
			pstmt.executeUpdate();
		} catch (SQLException e) { 	e.printStackTrace();
		} finally {	Dbm.close(con, pstmt, rs); }		
	}

	public void deleteBoard(int num) {
		String sql = "delete from board where num=?";
		con = Dbm.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbm.close(con, pstmt, rs);  }		
	}

	public void deleteReplyByboardnum(int num) {
		String sql = "delete from reply where boardnum=?";
		con = Dbm.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt( 1,  num );
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbm.close(con, pstmt, rs);  }	
		
	}

	public int getAllCount() {
		int count = 0;
		con = Dbm.getConnection();
		String sql = "select count(*) as cnt from board";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if( rs.next() ) 
				count = rs.getInt("cnt");
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbm.close(con, pstmt, rs);  }
		return count;
	}

	public int getReplycnt(int num) {
		int count=0;
		con = Dbm.getConnection();
		String sql = "select count(*) as cnt from reply where boardnum=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if( rs.next() ) count = rs.getInt("cnt");
		} catch (SQLException e) { e.printStackTrace();
		} finally { Dbm.close(con, pstmt, rs);  }
		
		return count;	
	}

}









