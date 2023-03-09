package tw.com.tibame.member.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tw.com.tibame.event.model.EventVO;
import tw.com.tibame.organizer.model.OrganizerVO;
import tw.com.tibame.util.common.Common;

public class CollectDAO implements CollectDAOinterface{

	private static final String INSERT_STMT = "INSERT INTO COLLECT (number, eventNumber) VALUES (?, ?)";
	@Override
	public void insert(int memberId, int eventId) {
		System.out.println("collect dao used insert one");
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, memberId);
			pstmt.setInt(2, eventId);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	private static final String GET_ALL_STMT = "SELECT eventNumber FROM COLLECT where number = ?";
	@Override
	public List<Integer> selectAll(int memberId) {
		System.out.println("collect dao used select all");
		List<Integer> list = new ArrayList<Integer>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			pstmt.setInt(1, memberId);
 
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getInt("eventNumber"));
			}
			System.out.println("list: " + list);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	
	private static final String DELETE_STMT = "DELETE FROM COLLECT where number = ? and eventNumber = ?";
	@Override
	public void delete(int memberId, int eventId) {
		// TODO Auto-generated method stub
		System.out.println("collect dao used delete one");
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, memberId);
			pstmt.setInt(2, eventId);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	
}
