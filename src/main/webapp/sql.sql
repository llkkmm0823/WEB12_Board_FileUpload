create table board(

	num	number(5) primary key,
	pass varchar2(30),	--게시물의 수정 삭제를 위한 비밀번호
	userid varchar2(30),
	email varchar2(30),
	title varchar2(30),
	content varchar2(1000),
	readcount number(4) default 0,	--조회수
	writedate date default sysdate	--작성일자
);
alter table board add imgfilename varchar2(255);


create sequence board_seq start with 1 increment by 1;


select*from board;

delete from board where num=125;

insert into board(num,userid,email,pass,title,content)
values(board_seq.nextVal,'hong','abc@naver.com','1234','첫방문입니다','반갑습니다. 앞으로 많은 격려와 지도편달부탁드립니다.');
insert into board(num,userid,email,pass,title,content)
values(board_seq.nextVal,'somi','somi@naver.com','1234','게시판개설','게시판을 개설했습니다. 무궁한 발전을 기원할게요');
insert into board(num,userid,email,pass,title,content)
values(board_seq.nextVal,'sang123','sang123@naver.com','1234','등업해주세요','어떻게하면 등업이 가능한지 알려주세요. 꼭 등업을 하고 싶습니다.');
insert into board(num,userid,email,pass,title,content)
values(board_seq.nextVal,'light','light@naver.com','1234','돼지골마을','사장님이 맛있고 돼지가 친절해요');
insert into board(num,userid,email,pass,title,content)
values(board_seq.nextVal,'hana','hana@naver.com','1234','우리교실진짜','시베리아벌판입니다. 정말 춥습니다. 다들 러시아 불곰들입니다.');
insert into board(num,userid,email,pass,title,content)
values(board_seq.nextVal,'hana','hana@naver.com','1234','하나둘','셋넷다섯여섯일곱여덟아홉열열하나열둘입니다요');
insert into board(num,userid,email,pass,title,content)
values(board_seq.nextVal,'zzadol','zzadol@naver.com','1234','멍멍이입니다','우리집 둘째(막내) 우짜돌입니다. 우짜돌은 귀엽습니다. 하는 행동마다 사랑스러우며 가끔 고집을 피우기도 합니다');
insert into board(num,userid,email,pass,title,content)
values(board_seq.nextVal,'sarang','sarang@naver.com','1234','우리집첫째','사랑이는 굉장히 도도한 강아지입니다. 잘 짖고 잘 뭅니다');
insert into board(num,userid,email,pass,title,content)
values(board_seq.nextVal,'ming','ming@naver.com','1234','밍구밍구리','반갑습니다 밍구밍구리입니다. 오 정말 집에 가고싶습니다. 글씨가 많으니 몹시 어지럽군요');
insert into board(num,userid,email,pass,title,content)
values(board_seq.nextVal,'young','young@naver.com','1234','맘스땃쥐에요','맘스터치로 시작해 맘스땃쥐가 된 찍찍이입니다. 배가고픕니까? 예 배가고픕니다요');

commit
create table reply(
	replynum	number(7)primary key, --댓글 순번
	boardnum number(5),	--댓글의 해당 게시물 번호
	userid varchar2(20),		--댓글 쓰니
	writedate date default sysdate,	--작성일
	content varchar2(1000)		--작성내용
);
select*from reply;
--댓글은 board 테이블에 저장되지 않음.
--한 두개의 댓글만 달리고 말것이라면 board 테이블에 댓글 필드를 두 세개 생성하고 저장해도 되지만, 
--게시판에 있는 각 게시물들에 대한 댓글은 작성될 수 있는 갯수가 제한이 없기 때문에, 모든 댓글을 하나의 테이블에 저장함
--이 대 반드시 저장되는 댓글에는 어느 게시물의 댓글인지 게시물 번호를 같이 저장해야함
--그래야 해당 게시물이 화면에 표시될 때 그 게시물의 댓글만 조회(검색)해서 따로 화면에 표시할 수 있음

create sequence reply_seq start with 1 increment by 1;

insert into reply values(reply_seq.nextVal,1,'somi',sysdate,'게시판 개설을 축하드립니다.');
insert into reply values(reply_seq.nextVal,2,'light',sysdate,'첫 글 축하드립니다.');
insert into reply values(reply_seq.nextVal,3,'scott',sysdate,'맛있게보입니다.');


select*from member;
--시험문제 출제 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
select *from(
	select *from(
		select rownum as rn , b.* from(
			(select *from board order by num desc) b
		)
	) where rn>=1
)where rn<=10;
