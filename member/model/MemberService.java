package tw.com.tibame.member.model;

import java.util.*;
import java.util.stream.Collectors;
import java.sql.*;
import java.sql.Date;

public class MemberService {
	
	private MemberDAOinterface dao;
	
	public MemberService() {
		dao = new MemberDAO();
	}

	
	public MemberVO insertMember(String account,String password,String email,Date birthday,String name,String phoneNumber) {
		
		MemberVO memberVO = new MemberVO();
		memberVO.setAccount(account);
		memberVO.setPassword(password);
		memberVO.setEmail(email);
		memberVO.setBirthday(birthday);
		memberVO.setName(name);
		memberVO.setPhoneNumber(phoneNumber);
	
		System.out.println("member Insert before");
		dao.insert(memberVO);
		System.out.println("member Insert after");
		
		return memberVO;
	}
	
	public MemberVO update(MemberVO memberVO) {
		System.out.println("### updateMember service");
		dao.update(memberVO);
		return memberVO;
}
	// 預留給 Struts 2 用的
	
	public void deleteMember(Integer number) {
		dao.delete(number);
	}
	
	public MemberVO getOneMember(Integer number) {
		return dao.findByPrimaryKey(number);
	}
	
	public List<MemberVO> getAll(){
		return dao.getAll();
	}
	
	public List<MemberVO> findByAccount(String account) {
		// TODO Auto-generated method stub
		return dao.getAll().stream()
				.filter(e -> e.getAccount().equals(account))
				.collect(Collectors.toList());
	}
	public MemberVO findByPrimaryKey(Integer number) {
		// TODO Auto-generated method stub
		return dao.findByPrimaryKey(number);
	}
	public List<MemberVO> getEmail(String email) {
		// TODO Auto-generated method stub
		return dao.getAll().stream()
				.filter(e -> e.getEmail().equals(email))
				.collect(Collectors.toList());
	}
	
	public List<MemberVO> findByPhoneNumber (String phoneNumber) {
		// TODO Auto-generated method stub
		return dao.getAll().stream()
				.filter(e -> e.getPhoneNumber().equals(phoneNumber))
				.collect(Collectors.toList());
	}
	public MemberVO findByAccount2(String account) {
		return dao.findByAccount2(account);
	}
	public MemberVO findByEmail(String email) {
		return dao.findByEmail(email);
	}
	public MemberVO findByPhoneNumber2(String phoneNumber) {
		return dao.findByPhoneNumber2(phoneNumber);
	}
	public String pwdhash(String password) {
		return dao.pwdhash(password);
	}
	public String pwdhash2(String password) {
		return dao.pwdhash(password);
	}


	public MemberVO updateMember(MemberVO memberVO) {
		System.out.println("### updateMember service");
		dao.updatePassword(memberVO);
		return memberVO;
	}


	public MemberVO updatePassword(MemberVO memberVO) {
		System.out.println("### updatePassword service");
		dao.update(memberVO);
		return memberVO;
	}


	public MemberVO updatePassword2(MemberVO memberVO) {
		System.out.println("### updatePassword2 service");
		dao.update(memberVO);
		return memberVO;
	}


	public List<MemberVO> getEmail2() {
		return  dao.getEmail2();
	}

	
	



}
