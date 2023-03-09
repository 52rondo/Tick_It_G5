package tw.com.tibame.management.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class ContactDAO implements ContactDAOInterface{
	String driver = "com.mysql.cj.jdbc.Driver"; 
	String url = "jdbc:mysql://localhost:3306/TICK_IT_TEST?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "password";


	private static final String INSERT_STMT = 
			"INSERT INTO contact_us (contactTitle, contactContent, contactCreateDate) VALUES (?, ?, ?)";
	public void insert(ContactVO bean) {
		Connection con = null;
		PreparedStatement ps1 = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			ps1 = con.prepareStatement(INSERT_STMT);
			ps1.setString(1, bean.getContactTitle());
			ps1.setString(2, bean.getContactContent());
			//get current time as a sql.Date object;
			java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
			ps1.setTimestamp(3, timestamp);
			ps1.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(ContactVO bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer contactID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ContactVO findByPrimaryKey(Integer contactID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ContactVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
