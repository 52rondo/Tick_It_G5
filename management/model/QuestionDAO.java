package tw.com.tibame.management.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class QuestionDAO implements QuestionDAOInterface{
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/TICK_IT_TEST?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "password";
	
	private static final String INSERT_STMT = 
			"INSERT INTO common_qa (commonName, commonContent, commonCreateDate, sort) VALUES (?, ?, ? , ?)";
	public void insert(QuestionVO bean) {
		// TODO Auto-generated method st

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			//可以考慮用DATASOURCE取代 
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, bean.getCommonName());
			pstmt.setString(2, bean.getCommonContent());
			java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
			pstmt.setTimestamp(3, timestamp);
			pstmt.setInt(4, bean.getSort());
			pstmt.executeUpdate();
			System.out.println("insert done?");
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
	public void update(QuestionVO bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer commonID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public QuestionVO findByPrimaryKey(Integer commonID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuestionVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuestionVO> searchOrganizerByAll(String searchString) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
