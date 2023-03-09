package tw.com.tibame.member.model;

import java.beans.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;




public class MemberDAO implements MemberDAOinterface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/TICK_IT?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "password";

	private static final String INSERT_STMT = "INSERT INTO MEMBER (account,password,email,birthday,name,phoneNumber)VALUES (?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT number,account,password,email,birthday,name,phoneNumber,subscription,IDNumber FROM member";
	private static final String GET_EMAIL_STMT = "SELECT number,account,password,email,birthday,name,phoneNumber,subscription,IDNumber FROM member where upper(email) like upper(?)";
	private static final String GET_EMAIL2_STMT = "SELECT email,name,subscription FROM member where subscription=1";
	private static final String GET_ACCOUNT_STMT = "SELECT number,account,password,email,birthday,name,phoneNumber,subscription,IDNumber FROM member where upper(account) like upper(?)";
	private static final String GET_PASSWORD_STMT = "SELECT number,account,password,email,birthday,name,phoneNumber,subscription,IDNumber FROM member where upper(password) like upper(?)";
//	private static final String UPDATE = "UPDATE member set account=?,password=?,email=?,birthday=?,name=?,phoneNumber=?,subscription=?,IDNumber=? where number = ?";
	private static final String UPDATE = "UPDATE member set email=?,password=?,birthday=?,name=?,phoneNumber=?,subscription=?,IDNumber=? where number = ?";
	private static final String GET_ONE_STMT = "SELECT number,account,password,email,birthday,name,phoneNumber,subscription,IDNumber FROM MEMBER where number = ?";
	private static final String UPDATE_PASSWORD = "UPDATE member set password=? where email = ?";
	private static final String UPDATE_PASSWORD2 = "UPDATE member set password=? where number = ?";
	@Override
	public void insert(MemberVO memberVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, memberVO.getAccount());
			pstmt.setString(2, memberVO.getPassword());
			pstmt.setString(3, memberVO.getEmail());
			pstmt.setDate(4, memberVO.getBirthday());
			pstmt.setString(5, memberVO.getName());
			pstmt.setString(6, memberVO.getPhoneNumber());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
	@Override
	public void update(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, memberVO.getEmail());
			pstmt.setString(2, memberVO.getPassword());
			pstmt.setDate(3, memberVO.getBirthday());
			pstmt.setString(4, memberVO.getName());
			pstmt.setString(5, memberVO.getPhoneNumber());
			pstmt.setBoolean(6, memberVO.getSubscription());
			pstmt.setString(7, memberVO.getIDNumber());
			pstmt.setInt(8, memberVO.getNumber());

			pstmt.executeUpdate();
			System.out.println("我到這裡了");
			
			// Handle any SQL errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	public void updatePassword(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_PASSWORD);
						
			pstmt.setString(1, memberVO.getPassword());
			pstmt.setString(2, memberVO.getEmail());


			pstmt.executeUpdate();
			System.out.println("我到這裡了");
			
			// Handle any SQL errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	public void updatePassword2(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_PASSWORD2);
						
			pstmt.setString(1, memberVO.getPassword());



			pstmt.executeUpdate();
			System.out.println("我到這裡了");
			
			// Handle any SQL errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	@Override
	public void delete(Integer number) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public MemberVO findByPrimaryKey(Integer number) {
		
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, number);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				memberVO = new MemberVO();
				
				memberVO.setNumber(rs.getInt("number"));
				memberVO.setAccount(rs.getString("account"));
				memberVO.setPassword(rs.getString("password"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setBirthday(rs.getDate("birthday"));
				memberVO.setName(rs.getString("name"));
				memberVO.setPhoneNumber(rs.getString("phoneNumber"));
				memberVO.setIDNumber(rs.getString("IDNumber"));
				memberVO.setSubscription(rs.getBoolean("subscription"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return memberVO;
	}
	public List<MemberVO> findByAccount(String account) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<MemberVO> getAll() {
		
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setNumber(rs.getInt("number"));
				memberVO.setAccount(rs.getString("account"));
				memberVO.setPassword(rs.getString("password"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setBirthday(rs.getDate("birthday"));
				memberVO.setName(rs.getString("name"));
				memberVO.setPhoneNumber(rs.getString("phoneNumber"));
				memberVO.setSubscription(rs.getBoolean("subscription"));
				memberVO.setIDNumber(rs.getString("IDNumber"));
				list.add(memberVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		}	catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	@Override
	public List<MemberVO> getEmail(String email) {
		 List<MemberVO> list = new ArrayList<MemberVO>();
		 MemberVO memberVO = null;
		  
		  Connection con = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null; 
		  
		  try {
		   Class.forName(driver);
           con = DriverManager.getConnection(url, userid, passwd);

		   pstmt = con.prepareStatement(GET_EMAIL_STMT);
		   pstmt.setString(1, email);
		   rs = pstmt.executeQuery();

		   while (rs.next()) {
		    
			   memberVO = new MemberVO();
			   	memberVO.setNumber(rs.getInt("number"));
				memberVO.setAccount(rs.getString("account"));
				memberVO.setPassword(rs.getString("password"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setBirthday(rs.getDate("birthday"));
				memberVO.setName(rs.getString("name"));
				memberVO.setPhoneNumber(rs.getString("phoneNumber"));
				memberVO.setSubscription(rs.getBoolean("subscription"));
				memberVO.setIDNumber(rs.getString("IDNumber"));
		    list.add(memberVO); // Store the row in the list
		   }

		   // Handle any driver errors
		  } catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
				// Handle any SQL errors
		}catch (SQLException se) {
		   throw new RuntimeException("A database error occured. "
		     + se.getMessage());
		   // Clean up JDBC resources
		  } finally {
		   if (rs != null) {
		    try {
		     rs.close();
		    } catch (SQLException se) {
		     se.printStackTrace(System.err);
		    }
		   }
		   if (pstmt != null) {
		    try {
		     pstmt.close();
		    } catch (SQLException se) {
		     se.printStackTrace(System.err);
		    }
		   }
		   if (con != null) {
		    try {
		     con.close();
		    } catch (Exception e) {
		     e.printStackTrace(System.err);
		    }
		   }
		  }
		  return list;
	}
	
	
	@Override
	public MemberVO findByAccount2(String account) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			 Class.forName(driver);
	         con = DriverManager.getConnection(url, userid, passwd);

			pstmt = con.prepareStatement(GET_ACCOUNT_STMT);
			System.out.println("Connecting to database successfully findByAccount! ");

			pstmt.setString(1, account);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setNumber(rs.getInt("number"));
				memberVO.setAccount(rs.getString("account"));
				memberVO.setPassword(rs.getString("password"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setBirthday(rs.getDate("birthday"));
				memberVO.setName(rs.getString("name"));
				memberVO.setPhoneNumber(rs.getString("phoneNumber"));
				memberVO.setSubscription(rs.getBoolean("subscription"));
				memberVO.setIDNumber(rs.getString("IDNumber"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return memberVO;
	}
	@Override
	public MemberVO findByEmail(String email) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			 Class.forName(driver);
	         con = DriverManager.getConnection(url, userid, passwd);

			pstmt = con.prepareStatement(GET_EMAIL_STMT);
			System.out.println("Connecting to database successfully findByEmail! ");

			pstmt.setString(1, email);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setNumber(rs.getInt("number"));
				memberVO.setAccount(rs.getString("account"));
				memberVO.setPassword(rs.getString("password"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setBirthday(rs.getDate("birthday"));
				memberVO.setName(rs.getString("name"));
				memberVO.setPhoneNumber(rs.getString("phoneNumber"));
				memberVO.setSubscription(rs.getBoolean("subscription"));
				memberVO.setIDNumber(rs.getString("IDNumber"));
				
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return memberVO;
	}
	@Override
	public MemberVO findByPhoneNumber2(String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	public String pwdhash(String password) {

		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			 Class.forName(driver);
	         con = DriverManager.getConnection(url, userid, passwd);

			pstmt = con.prepareStatement(GET_PASSWORD_STMT);
			System.out.println("Connecting to database successfully pwdhash! ");

			pstmt.setString(1, password);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setNumber(rs.getInt("number"));
				memberVO.setAccount(rs.getString("account"));
				memberVO.setPassword(rs.getString("password"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setBirthday(rs.getDate("birthday"));
				memberVO.setName(rs.getString("name"));
				memberVO.setSubscription(rs.getBoolean("subscription"));
				memberVO.setPhoneNumber(rs.getString("phoneNumber"));
				memberVO.setIDNumber(rs.getString("IDNumber"));
				
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return password;
	}
	
	public String pwdhash2(String password) {
		try {
			Base64.Encoder enc = Base64.getEncoder();
			String newPwd = enc.encodeToString(password.getBytes());
			System.out.println("加密後的密碼: ====="+newPwd+"=====");
			return newPwd;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main (String args[]){
		MemberDAO dao = new MemberDAO();
		MemberVO updateTest = new MemberVO();
		  updateTest.setAccount("qaz00023");
		  updateTest.setPassword("123456");
		  updateTest.setEmail("q2a0z9@gmail.com");
		  updateTest.setBirthday(java.sql.Date.valueOf("2022-05-05"));
		  updateTest.setName("蔡安罧");
		  updateTest.setPhoneNumber("0975798318");
		  updateTest.setSubscription(true);
		  updateTest.setIDNumber("F229052175");
		  updateTest.setNumber(1);
		dao.update(updateTest);
		System.out.println("修改成功");
	}
	public List<MemberVO> getEmail2(){
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_EMAIL2_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setEmail(rs.getString("email"));
				memberVO.setName(rs.getString("name"));
				memberVO.setSubscription(rs.getBoolean("subscription"));
				list.add(memberVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		}	catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}	

	}



