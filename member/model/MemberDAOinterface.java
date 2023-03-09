package tw.com.tibame.member.model;

import java.util.List;



public interface MemberDAOinterface {
	public void insert(MemberVO memberVO);
	public void update(MemberVO memberVO);
	public void updatePassword(MemberVO memberVO);
	public void updatePassword2(MemberVO memberVO);
	public void delete(Integer number);
	public List<MemberVO> getEmail(String email);
	public MemberVO findByPrimaryKey(Integer number);
	public MemberVO findByAccount2(String account);
	public MemberVO findByEmail(String email);
	public MemberVO findByPhoneNumber2(String phoneNumber);
	public List<MemberVO> getAll();
	public String pwdhash(String password);
	public String pwdhash2(String password);
	public List<MemberVO> getEmail2();

}
