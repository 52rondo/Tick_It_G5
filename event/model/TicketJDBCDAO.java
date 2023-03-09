package tw.com.tibame.event.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import tw.com.tibame.util.common.Common;

public class TicketJDBCDAO implements TicketDAO_interface{
	private static final String ticketInsertSQL = "insert into TICKET(ticketName,eventNumber,price,limitTicket,ticketQuantity,ticketStartTime,ticketEndTime,ticketMIN,ticketMAX,ticketType) value(?,?,?,?,?,?,?,?,?,?);";
	private static final String selectByPKSQL="select * from TICKET where eventNumber=? and ticketType != '已下架';";
	private static final String ticketUpdateSQL="update TICKET set ticketName =?, price=?, limitTicket=?, ticketQuantity=?, ticketStartTime=?, ticketEndTime=?, ticketMIN=?, ticketMAX=?, ticketType=? where ticketID=?;";
	private static final String queryByIdSQL =" select * from ticket e where e.ticketID = ?";
	
	@Override
	public int insert(TicketVO ticketvo, Connection con) {
		PreparedStatement ps = null;
		int rowCount = 0;
		try {
			ps = con.prepareStatement(ticketInsertSQL);
     		ps.setString(1,ticketvo.getTicketName());
     		ps.setInt(2,ticketvo.getEventNumber());
     		ps.setInt(3,ticketvo.getPrice());
     		ps.setBoolean(4,ticketvo.getLimitTicket());
     		ps.setInt(5,ticketvo.getTicketQuantity());
     		ps.setTimestamp(6,ticketvo.getTicketStartTime());
     		ps.setTimestamp(7,ticketvo.getTicketEndTime());
     		ps.setInt(8,ticketvo.getTicketMIN());
     		ps.setInt(9,ticketvo.getTicketMAX());
     		ps.setString(10,ticketvo.getTicketType());
     		
     		rowCount = ps.executeUpdate();
     		System.out.println(rowCount + " Ticket inserted!!");
     		return rowCount;
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-Ticket");
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
	public int update(TicketVO ticketvo, Connection con) {
		PreparedStatement ps = null;
		int rowCount = 0;
		try {
			ps = con.prepareStatement(ticketUpdateSQL);
     		ps.setString(1,ticketvo.getTicketName());
     		ps.setInt(2,ticketvo.getPrice());
     		ps.setBoolean(3,ticketvo.getLimitTicket());
     		ps.setInt(4,ticketvo.getTicketQuantity());
     		ps.setTimestamp(5,ticketvo.getTicketStartTime());
     		ps.setTimestamp(6,ticketvo.getTicketEndTime());
     		ps.setInt(7,ticketvo.getTicketMIN());
     		ps.setInt(8,ticketvo.getTicketMAX());
     		ps.setString(9,ticketvo.getTicketType());
     		ps.setInt(10,ticketvo.getTicketID());

     		rowCount=ps.executeUpdate();
     		System.out.println(rowCount + " Ticket updated!!");
     		return rowCount;
			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-TICKET_update");
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
	public int updateTicket(TicketVO ticketvo) {
		int rowCount = 0;
		
		Connection con = null;
        PreparedStatement ps = null;
		try {
			
			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
			
			ps = con.prepareStatement(ticketUpdateSQL);
     		ps.setString(1,ticketvo.getTicketName());
     		ps.setInt(2,ticketvo.getPrice());
     		ps.setBoolean(3,ticketvo.getLimitTicket());
     		ps.setInt(4,ticketvo.getTicketQuantity());
     		ps.setTimestamp(5,ticketvo.getTicketStartTime());
     		ps.setTimestamp(6,ticketvo.getTicketEndTime());
     		ps.setInt(7,ticketvo.getTicketMIN());
     		ps.setInt(8,ticketvo.getTicketMAX());
     		ps.setString(9,ticketvo.getTicketType());
     		ps.setInt(10,ticketvo.getTicketID());

     		rowCount=ps.executeUpdate();
     		System.out.println(rowCount + " Ticket updated!!");
     		return rowCount;
			// Handle any driver errors
		}  catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
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
	public List<TicketVO> selectByeventNumber(Integer eventNumber) {
		List<TicketVO> ticketvolist = new ArrayList<TicketVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
			pstmt = con.prepareStatement(selectByPKSQL);

			pstmt.setInt(1, eventNumber);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				TicketVO ticketvo = new TicketVO();
				ticketvo.setTicketID(rs.getInt("ticketID"));
				ticketvo.setTicketName(rs.getString("ticketName"));
				ticketvo.setEventNumber(rs.getInt("eventNumber"));
				ticketvo.setPrice(rs.getInt("price"));
				ticketvo.setLimitTicket(rs.getBoolean("limitTicket"));
				ticketvo.setTicketQuantity(rs.getInt("ticketQuantity"));
				ticketvo.setTicketStartTime(rs.getTimestamp("ticketStartTime"));
				ticketvo.setTicketEndTime(rs.getTimestamp("ticketEndTime"));
				ticketvo.setTicketMIN(rs.getInt("ticketMIN"));
				ticketvo.setTicketMAX(rs.getInt("ticketMAX"));
				ticketvo.setTicketType(rs.getString("ticketType"));
				ticketvolist.add(ticketvo);
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
		return ticketvolist;
	}
	
	 @Override
	    public TicketVO queryById(int id) {
	        TicketVO vo= null;
	        
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
	            Class.forName(Common.driver);
	            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
	            ps = conn.prepareStatement(queryByIdSQL);
	            ps.setInt(1, id);
	            rs = ps.executeQuery();
	            
	            if (rs.next()) {
	                vo = new TicketVO();
	                
	                vo.setTicketID(rs.getInt("ticketID"));
	                vo.setTicketName(rs.getString("ticketName"));
	                vo.setEventNumber(rs.getInt("eventNumber"));
	                vo.setPrice(rs.getInt("price"));
                    vo.setLimitTicket(rs.getBoolean("limitTicket"));
                    vo.setTicketQuantity(rs.getInt("ticketQuantity"));
                    vo.setTicketStartTime(rs.getTimestamp("ticketStartTime"));
                    vo.setTicketEndTime(rs.getTimestamp("ticketEndTime"));
                    vo.setTicketMIN(rs.getInt("ticketMIN"));
                    vo.setTicketMAX(rs.getInt("ticketMAX"));
                    vo.setTicketType(rs.getString("ticketType"));
                    
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            try {
	                rs.close();
	            } catch (Exception e) {}
	            try {
	                ps.close();
	            } catch (Exception e) {}
	            try {
	                conn.close();
	            } catch (Exception e) {}
	        }
	        
	        return vo;
	    }
}
