package com.ezen.board.util;

public class Paging {

	private int page = 1; // 현재 화면에 표시할 페이지 번호
	private int totalCount;   //총 게시물의 갯수.   총갯수가 결정되야  총페이지수도 결정됩니다
	private int displayRow = 10;   //한 페이지에 몇 개의 게시물을 표시할 지를 저장
	private int displayPage = 10;  //이전(◀)과 다음(▶) 버튼 사이에 몇 개의 페이지를 표시할지를 저장
	
	private int beginPage;    	// 현재 페이지에 표시될 시작페이지 번호  1 or 11 or 21 or 31 ...
    private int endPage;      	// 현재 페이지에 표시될  끝페이지 번호  10 or 20 or 30 or 40 ...
    
    private boolean prev;  	// prev 버튼이 보일건지 안보일건지 ( true/false )
    private boolean next;  	// next 버튼이 보일건지 안보일건지 ( true/false )
    
    private int startNum;  		// 현재 페이지에 표시될 게시물 번호의 시작 번호
    private int endNum;   		// 현재 페이지에 표시될 게시물 번호의 끝 번호
    
    // 페이지 표시 예
    // [1] 2 3 4 5 6 7 8 9 10 [next]  - 1페이지부터 표시  prev  없음   게새물 100 개 이상
    // [prev] 11 12 13 14 [15] 16 17 18 19 20 [next]  - 게시물 갯수가 200개 이상인경우
    // [prev] 21 22 23 24 [25] 26 27 28 29 30 [next]  - 게시물 갯수가 300개 이상인경우
    // [prev] 11 12 13 14  - next 없음 , 게시물 갯수가 141~149 개 인경우
    // 본 클래스는 하나의 게시판에 객체하나를 할당해서 전체 게시물 수에 따라 계산된 페이지의 각 요소들을 
    // 각 멤버 변수에 저장하고 화면에 표시할 내용을 사용하는 클래스 입니다....
    private void paging() {
    	// 1. beginPage 와 endPage 계산
    	double temp = page / (double) displayPage;  // 현제페이지 번호를 displayPage 변수로 나눕니다
    	// 1/10->0.1      9/10->0.9    15/10->1.5         25/10->2.5
    
    	// 위 연산결과에서 소수점 첫째자리 올림연산합니다
    	temp = Math.ceil( temp );
    	// 0.1->1.0     0.9->1.0      1.5->2.0      2.5->3.0
   
    	endPage = (int)(temp * displayPage);
    	// 1.0->10     1.0->10      2.0->20      3.0->30
    	// endPage=( (int)( Math.ceil( page/(double)displayPage ) ) )*displayPage;
    	
    	beginPage = endPage - (displayPage - 1);
    	// endPage 가 10 이면 beginPage가 1
    	// endPage 가 20 이면 beginPage가 11
    	// endPage 가 30 이면 beginPage가 21
    	
    	//2. totalPage 계산
    	// 총게시물수(totalCount)를  한개에 화면에 표시될 게시물수(displayRow)로 나누고, 소수점자리 올림해서 계산
    	int totalPage = (int)Math.ceil(totalCount/(double)displayRow);
    	//  108 / 10 -> 10.8  -> 11.0  ->  11   : 총게시물이 108개이면 필요한 총페이지수는 11페이지 까지 필요
    	//  75 / 10 -> 7.5   -> 8.0   -> 8   : 총게시물이 75개이면 필요한 총페이지수는 8페이지 까지 필요
    	
    	//3. next, prev
    	if( totalPage < endPage ) {   // 총페이지가 화면에 표시할 끝페이지보다 작다면
    		endPage = totalPage;        // endPage 를 총페이지수로 대체하고
    		next = false;                    // 다음 버튼은 표시하지 않는것으로 설정
    	} else{  // endPage 뒤로 페이지가 더 있으니. next  를 표시하는 것으로 설정
            next = true;
        }
    	prev = (beginPage==1)? false : true;  // 시작페이지가 1인경우만 표시안함
    	
    	// 4. startNum,  endNum  : ROWNUM 을 대상으로 하여 설장하는 표시될 게시물 번호
    	startNum = (page-1)*displayRow+1;   
    	// 현재 화면의 시작 게시물 번호  1page : 1,  2page:11,  3page:21,  4page: 31
        endNum = page*displayRow;             
        //  현재 화면의 끝 게시물 번호  10, 20, 30, 40
        
        System.out.println(beginPage + " "  + endPage + " "  + startNum + " "  + endNum);
    	
    }
    
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		paging();
	}
	public int getDisplayRow() {
		return displayRow;
	}
	public void setDisplayRow(int displayRow) {
		this.displayRow = displayRow;
	}
	public int getDisplayPage() {
		return displayPage;
	}
	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
    
}
