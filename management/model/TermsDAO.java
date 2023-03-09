package tw.com.tibame.management.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TermsDAO implements TermsDAOIntereface{
	String driver = "com.mysql.cj.jdbc.Driver"; 
	String url = "jdbc:mysql://localhost:3306/TICK_IT_TEST?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "password";

	private static final String INSERT_STMT = 
			"INSERT INTO terms_of_service (termsTitle, termsContent, termsCreateDate) VALUES (?, ?, ?)";
	@Override
	public void insert(TermsVO bean) {
		Connection con = null;
		PreparedStatement ps1 = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			ps1 = con.prepareStatement(INSERT_STMT);
			ps1.setString(1, bean.getTermsTitle());
			ps1.setString(2, bean.getTermsContent());
			//get current time as a sql.Date object;
			java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
			ps1.setTimestamp(3, timestamp);
			ps1.executeUpdate();
			System.out.println("DAO: execute update, insert done");
//		Date convertToDateViaInstant(LocalDateTime dateToConvert) {
//    return java.util.Date
//      .from(dateToConvert.atZone(ZoneId.systemDefault())
//      .toInstant());
//			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void update(TermsVO bean) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Integer empno) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public TermsVO findByPrimaryKey(Integer termsID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<TermsVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
