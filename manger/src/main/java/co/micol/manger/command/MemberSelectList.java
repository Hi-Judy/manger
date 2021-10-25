package co.micol.manger.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.manger.common.Command;
import co.micol.manger.member.service.MemberService;
import co.micol.manger.member.serviceImpl.MemberServiceImpl;
import co.micol.manger.member.vo.MemberVO;

public class MemberSelectList implements Command {

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		//멤버 목록 가져오기
		MemberService memberDao = new MemberServiceImpl();
		List<MemberVO> list = new ArrayList<MemberVO>();
		list = memberDao.memberSelectList();
		
		request.setAttribute("members", list); //결과를 request 객체에 담는다.(members 라는 이름으로 list 담는다.) 
											   //list 는 실제 넘어온 값. member는 페이지에 넘길 변수 명. -> members 라는 이름으로 list 담는다.
		return "member/memberSelectList";
	}

}
