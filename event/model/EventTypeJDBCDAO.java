package tw.com.tibame.event.model;

import tw.com.tibame.util.common.Common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventTypeJDBCDAO implements EventTypeDAO_interface{
	private static final String eventTypeAllSQL = "select * from `EVENT_TYPE` where `eventClassState` != ?;";
	private static final String queryTypeSQL = "select * from EVENT_TYPE where eventClassNumber = ?;";
	@Override
	public int insert(EventTypeVO eventTypevo) {

		return 0;
	}

	@Override
	public int update(EventTypeVO eventTypevo) {

		return 0;
	}
	
	@Override
	public List<EventTypeVO> selectTypeIsON() {
		List<EventTypeVO> list = new ArrayList<EventTypeVO>();
		EventTypeVO eventtypevo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
			pstmt = con.prepareStatement(eventTypeAllSQL);
			
			pstmt.setBoolean(1,false);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventtypevo = new EventTypeVO();
				eventtypevo.setEventClassNumber(rs.getInt(1));
				eventtypevo.setEventClassName(rs.getString(2));
				eventtypevo.setEventClassState(rs.getBoolean(3));
				list.add(eventtypevo);
			}
			

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public EventTypeVO selectByEventClassNumber(Integer eventClassNumber) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EventTypeVO eventtypevo =null;
		try {

			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
			pstmt = con.prepareStatement(queryTypeSQL);
			
			pstmt.setInt(1, eventClassNumber);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				eventtypevo = new EventTypeVO();
				eventtypevo.setEventClassNumber(rs.getInt(1));
				eventtypevo.setEventClassName(rs.getString(2));
				eventtypevo.setEventClassState(rs.getBoolean(3));	
			}
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return eventtypevo;
	}

	@Override
	public List<EventTypeVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
