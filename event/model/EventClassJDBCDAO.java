package tw.com.tibame.event.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.com.tibame.util.common.Common;

public class EventClassJDBCDAO implements EventClassDAO_interface{
	private static final String evclassInsertSQL="insert into EVENT_CLASS value(?,?);";
	private static final String evclassByPKSQL="select * from `EVENT_CLASS` where eventNumber=?;";
//	private static final String evclassUpdateSQL="update EVENT_CLASS set eventClassNumber =? where eventNumber=?;";
	private static final String evclassDeleteSQL="delete from EVENT_CLASS where eventNumber=?;";
	@Override
	public List<EventClassVO> selectByeventNumber(Integer eventNumber) {
		List<EventClassVO> eventClass = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
			pstmt = con.prepareStatement(evclassByPKSQL);

			pstmt.setInt(1, eventNumber);

			rs = pstmt.executeQuery();
			
			eventClass= new ArrayList<EventClassVO>();
			while (rs.next()) {
				EventClassVO eventclassvo= new EventClassVO();
				eventclassvo.setEventNumber(rs.getInt("eventNumber"));
				eventclassvo.setEventClassNumber(rs.getInt("eventClassNumber"));
				eventClass.add(eventclassvo);
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
		return eventClass;
	}

//	@Override
//	public int update(EventClassVO eventclassvo, Connection con) {
//		PreparedStatement ps = null;
//		int rowCount = 0;
//		try {
//			ps = con.prepareStatement(evclassUpdateSQL);
//     		ps.setInt(1,eventclassvo.getEventClassNumber());
//     		ps.setInt(2,eventclassvo.getEventNumber());
//
//     		rowCount=ps.executeUpdate();
//     		return rowCount;
//			// Handle any driver errors
//		} catch (SQLException se) {
//			if (con != null) {
//				try {
//					// 3●設定於當有exception發生時之catch區塊內
//					System.err.print("Transaction is being ");
//					System.err.println("rolled back-由-EventClass_update");
//					con.rollback();
//				} catch (SQLException excep) {
//					throw new RuntimeException("rollback error occured. "
//							+ excep.getMessage());
//				}
//			}
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (ps != null) {
//				try {
//					ps.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//		}
//	}

	
	@Override
	public int insert(EventClassVO eventclassvo, Connection con) {
		PreparedStatement ps = null;
		int rowCount = 0;
		try {

     		ps = con.prepareStatement(evclassInsertSQL);
     		ps.setInt(1,eventclassvo.getEventNumber());
     		ps.setInt(2,eventclassvo.getEventClassNumber());
     		
//     		Statement stmt=con.createStatement();
     		rowCount = ps.executeUpdate();
     		System.out.println(rowCount + " EventClass inserted!!");
     		return rowCount;
		  // Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-EventClass");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public int deleteByEventNumber(Integer eventNumber, Connection con) {
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			pstmt = con.prepareStatement(evclassDeleteSQL);
			pstmt.setInt(1, eventNumber);

			rowCount = pstmt.executeUpdate();
			System.out.println(rowCount + " EventClass deleted!!");
			return rowCount;
			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-EventClass_delete");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
}
