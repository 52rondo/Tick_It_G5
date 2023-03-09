package tw.com.tibame.event.model;

import tw.com.tibame.util.common.Common;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class EventJDBCDAO implements EventDAO_interface {
	private static final String eventInsertSQL = "insert into `EVENT`(organizerNumber,eventName,eventStartDate,eventEndDate,"
			+ "peopleNumber,eventPlace,eventP2,eventSummary,eventDescribe,bigImg,smallImg,"
			+ "collectType,isON,eventType,needSeat,seatX,seatY) " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private static final String eventUpdateSQL = "update `EVENT` set eventName=?, eventStartDate=?, eventEndDate=?, "
			+ "peopleNumber=?, eventPlace=?, eventP2=?, eventSummary=?, eventDescribe=?, "
			+ "bigImg=?, smallImg=?, collectType=?, isON=?, eventType=?, needSeat=?, seatX=?, seatY=? "
			+ "where eventNumber=?;";
	private static final String selectByPKSQL="select * from `EVENT` where eventNumber=?;";
	private static final String findAllForDebugSQL = 
	          " SELECT                                              "
	        + " e.eventnumber,                                      "
	        + " e.eventName,                                        "
	        + " e.seatX,e.seatY,                                    "
	        + " COUNT(t.ticketID) AS ticketCount                    "
	        + "  FROM EVENT e                                       "
	        + "  INNER JOIN TICKET t                                "
	        + "    ON t.eventNumber = e.eventNumber                 "
	        + "  GROUP BY e.eventNumber,e.eventName,e.seatX,e.seatY ";
	
	private static final String organizerNumberSQL = " select organizerName from organizer where organizerNumber = ? ";
	
	@Override
	public int insert(EventVO eventvo,List<TicketVO> ticketlist,List<EventClassVO> eventclasslist,List<SeatVO> seatlist) {
		Connection conn = null;
		PreparedStatement ps = null;
		int recount = 0;
		try {
			Class.forName(Common.driver);
			conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
			
			conn.setAutoCommit(false);
			//新增event table
			String[] cols = {"EVENT"};
			ps = conn.prepareStatement(eventInsertSQL,cols);
			
			ps.setInt(1,eventvo.getOrganizerNumber());
			ps.setString(2,eventvo.getEventName());
			ps.setTimestamp(3,eventvo.getEventStartDate());
			ps.setTimestamp(4,eventvo.getEventEndDate());
			ps.setInt(5,eventvo.getPeopleNumber());
			ps.setString(6,eventvo.getEventPlace());
			ps.setString(7,eventvo.getEventP2());
			ps.setString(8,eventvo.getEventSummary());
			ps.setString(9,eventvo.getEventDescribe());
			ps.setBytes(10,eventvo.getBigImg());
			ps.setBytes(11,eventvo.getSmallImg());
			ps.setBoolean(12,eventvo.getCollectType());
			ps.setBoolean(13,eventvo.getIsON());
			ps.setString(14,eventvo.getEventType());
			ps.setBoolean(15,eventvo.getNeedSeat());
			ps.setInt(16,eventvo.getSeatX());
			ps.setInt(17,eventvo.getSeatY());
			
			Statement stmt=conn.createStatement();
//			stmt.executeUpdate("set auto_increment_offset=1;");//自增主鍵-初始值
//			stmt.executeUpdate("set auto_increment_increment=1");//自增主鍵-遞增
			int rowCount = ps.executeUpdate();
			String eventNumber_ = null;
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				eventNumber_ = rs.getString(1);
				System.out.println("自增主鍵值= " + eventNumber_);
			}else {
				System.out.println("未取得自增主鍵值");
			}
			System.out.println(rowCount + " EVENT inserted!!");
			rs.close();
			
			//新增event class table
			EventClassJDBCDAO eventclassdao = new EventClassJDBCDAO();
			int evclasscount = 0;
			for(EventClassVO aeventclass:eventclasslist) {
				aeventclass.setEventNumber(Integer.valueOf(eventNumber_));
				int re =eventclassdao.insert(aeventclass, conn);
				evclasscount = evclasscount+re;
			}
			
			//新增Ticket table
			TicketJDBCDAO ticketdao = new TicketJDBCDAO();
			int ticketcount = 0;
			for(TicketVO aticket:ticketlist) {
				aticket.setEventNumber(Integer.valueOf(eventNumber_));
				int re =ticketdao.insert(aticket, conn);
				ticketcount = ticketcount+re;
			}
			
			//needSeat is true 新增seat table
			int seatcount = 0;
			if(eventvo.getNeedSeat()) {
				SeatJDBCDAO seatdao = new SeatJDBCDAO();
				for(SeatVO aseat:seatlist) {
					aseat.setEventNumber(Integer.valueOf(eventNumber_));
					int re = seatdao.insert(aseat, conn);
					seatcount = seatcount+re;
				}
			}
			
			conn.commit();
			conn.setAutoCommit(true);
			
			if(rowCount > 0 && evclasscount > 0 && ticketcount > 0 && seatcount > 0) {
				recount =1;
			}else if(rowCount > 0 && evclasscount > 0 && ticketcount > 0 && !eventvo.getNeedSeat()) {
				recount =1;
			}
			
		// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (conn != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-EventJDBCDAO");
					conn.rollback();
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
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return recount;
	}

//	
//	private static final String GET_ALL_STMT = "SELECT organizerNumber, OAccount, Opassword, organizerName, windowName, windowPhone, windowEmail " 
//			+ "FROM organizer order by organizerNumber";
	private static final String GET_ALL_STMT = "SELECT * FROM EVENT order by eventNumber" ;
			


	@Override
	public List<EventVO> selectAll() {
		
		List<EventVO> list = new ArrayList<EventVO>();
		EventVO eventvo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
 
			while (rs.next()) {
				eventvo = new EventVO();
				eventvo.setEventNumber(rs.getInt("eventNumber"));
				eventvo.setOrganizerNumber(rs.getInt("organizerNumber"));
				eventvo.setEventName(rs.getString("eventName"));
				eventvo.setEventStartDate(rs.getTimestamp("eventStartDate"));
				eventvo.setEventEndDate(rs.getTimestamp("eventEndDate"));
				eventvo.setPeopleNumber(rs.getInt("peopleNumber"));
				eventvo.setEventPlace(rs.getString("eventPlace"));
				eventvo.setEventP2(rs.getString("eventP2"));
				eventvo.setEventSummary(rs.getString("eventSummary"));
				eventvo.setEventDescribe(rs.getString("eventDescribe"));
//				eventvo.setBigImg(rs.getBytes("bigImg"));
//				eventvo.setSmallImg(rs.getBytes("smallImg"));
//				eventvo.setVideo(rs.getBytes("video"));
//				eventvo.setOtherImg1(rs.getBytes("otherImg1"));
//				eventvo.setOtherImg2(rs.getBytes("otherImg2"));
				eventvo.setCollectType(rs.getBoolean("collectType"));
				eventvo.setBanner(rs.getInt("banner"));
				eventvo.setFocus(rs.getInt("focus"));
				eventvo.setTotalClick(rs.getInt("totalClick"));
				eventvo.setIsON(rs.getBoolean("isON"));
				eventvo.setEventType(rs.getString("eventType"));
				eventvo.setNeedSeat(rs.getBoolean("needSeat"));
				eventvo.setSeatX(rs.getInt("seatX"));
				eventvo.setSeatY(rs.getInt("seatY"));
				list.add(eventvo); // Store the row in the list
			}
			System.out.println("event dao used select all");
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

	@Override
	public EventVO selectByeventNumber(Integer eventNumber) {
		EventVO eventvo = null;
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
				eventvo = new EventVO();
				eventvo.setEventNumber(rs.getInt("eventNumber"));
				eventvo.setOrganizerNumber(rs.getInt("organizerNumber"));
				eventvo.setEventName(rs.getString("eventName"));
				eventvo.setEventStartDate(rs.getTimestamp("eventStartDate"));
				eventvo.setEventEndDate(rs.getTimestamp("eventEndDate"));
				eventvo.setPeopleNumber(rs.getInt("peopleNumber"));
				eventvo.setEventPlace(rs.getString("eventPlace"));
				eventvo.setEventP2(rs.getString("eventP2"));
				eventvo.setEventSummary(rs.getString("eventSummary"));
				eventvo.setEventDescribe(rs.getString("eventDescribe"));
				eventvo.setBigImg(rs.getBytes("bigImg"));
				eventvo.setSmallImg(rs.getBytes("smallImg"));
				eventvo.setCollectType(rs.getBoolean("collectType"));
				eventvo.setBanner(rs.getInt("banner"));
				eventvo.setFocus(rs.getInt("focus"));
				eventvo.setIsON(rs.getBoolean("isON"));
				eventvo.setEventType(rs.getString("eventType"));
				eventvo.setNeedSeat(rs.getBoolean("needSeat"));
				eventvo.setSeatX(rs.getInt("seatX"));
				eventvo.setSeatY(rs.getInt("seatY"));
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
		return eventvo;
	}

	@Override
	public EventVO selectByeventName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EventVO> selectByorganizerNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EventVO> selectByeventType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EventVO> selectByDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(EventVO eventvo,List<TicketVO> ticketlist,List<EventClassVO> eventclasslist,List<SeatVO> seatlist) {
		Connection con = null;
		PreparedStatement ps = null;
		int recount = 0;
		try {

			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);

			con.setAutoCommit(false);
			//修改event table
			ps = con.prepareStatement(eventUpdateSQL);

			ps.setString(1,eventvo.getEventName());
			ps.setTimestamp(2,eventvo.getEventStartDate());
			ps.setTimestamp(3,eventvo.getEventEndDate());
			ps.setInt(4,eventvo.getPeopleNumber());
			ps.setString(5,eventvo.getEventPlace());
			ps.setString(6,eventvo.getEventP2());
			ps.setString(7,eventvo.getEventSummary());
			ps.setString(8,eventvo.getEventDescribe());
			ps.setBytes(9,eventvo.getBigImg());
			ps.setBytes(10,eventvo.getSmallImg());
			ps.setBoolean(11,eventvo.getCollectType());
			ps.setBoolean(12,eventvo.getIsON());
			ps.setString(13,eventvo.getEventType());
			ps.setBoolean(14,eventvo.getNeedSeat());
			ps.setInt(15,eventvo.getSeatX());
			ps.setInt(16,eventvo.getSeatY());
			ps.setInt(17,eventvo.getEventNumber());

			int rowCount = ps.executeUpdate();

			System.out.println(rowCount + " EVENT updated!!");
			
			//修改event class table(先刪後新增)
			EventClassJDBCDAO eventclassdao = new EventClassJDBCDAO();
			int evclasscount = 0;
			eventclassdao.deleteByEventNumber(eventvo.getEventNumber(), con);
			for(EventClassVO aeventclass:eventclasslist) {
				aeventclass.setEventNumber(eventvo.getEventNumber());
				int re =eventclassdao.insert(aeventclass, con);
				evclasscount = evclasscount+re;
			}
			
			//修改Ticket table
			TicketJDBCDAO ticketdao = new TicketJDBCDAO();
			int ticketcount = 0;
			for(TicketVO aticket:ticketlist) {
				int re = 0;
				if(aticket.getTicketID() !=null) {
					re =ticketdao.update(aticket, con);
				}else {
					aticket.setEventNumber(eventvo.getEventNumber());
					re =ticketdao.insert(aticket, con);
				}
				ticketcount = ticketcount+re;
			}
			
			//needSeat is true 修改seat table
			int seatcount = 0;
			if(eventvo.getNeedSeat()) {
				SeatJDBCDAO seatdao = new SeatJDBCDAO();
				seatdao.deleteByEventNumber(eventvo.getEventNumber(), con);
				for(SeatVO aseat:seatlist) {
					aseat.setEventNumber(eventvo.getEventNumber());
					int re = seatdao.insert(aseat, con);
					seatcount = seatcount+re;
				}
			}
			
			con.commit();
			con.setAutoCommit(true);
			
			if(rowCount > 0 && evclasscount > 0 && ticketcount > 0 && seatcount > 0) {
				recount =1;
			}else if(rowCount > 0 && evclasscount > 0 && ticketcount > 0 && !eventvo.getNeedSeat()) {
				recount =1;
			}

			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-EventJDBCDAO_update");
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return recount;
	}
	
	@Override
    public String getOrganizerName(int organizerNumber) {

        
        String name = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            ps = conn.prepareStatement(organizerNumberSQL);
            ps.setInt(1, organizerNumber);
            rs = ps.executeQuery();
            if(rs.next()) {
                name = rs.getString("organizerName");
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
        
        return name;
        
    
    }


    @Override
	public List<Map<String,Object>> findAllForDebug(){
	    
	    List<Map<String,Object>> res = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            ps = conn.prepareStatement(findAllForDebugSQL);
            rs = ps.executeQuery();
            while(rs.next()) {
                
                Map<String,Object> m = new HashMap<>();
                
                m.put("eventNumber", rs.getInt("eventNumber"));
                m.put("eventName", rs.getString("eventName"));
                m.put("seatX", rs.getInt("seatX"));
                m.put("seatY", rs.getInt("seatY"));
                m.put("ticketCount", rs.getInt("ticketCount"));
                
                res.add(m);
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
        
        return res;
	    
	}
    
    
//    "EventVO [eventNumber=" + eventNumber + ", organizerNumber=" + organizerNumber + ", eventName="
//	+ eventName + ", eventStartDate=" + eventStartDate + ", eventEndDate=" + eventEndDate
//	+ ", peopleNumber=" + peopleNumber + ", eventPlace=" + eventPlace + ", eventP2=" + eventP2
//	+ ", eventSummary=" + eventSummary + ", eventDescribe=" + eventDescribe + ", bigImg="
//	+ Arrays.toString(bigImg) + ", smallImg=" + Arrays.toString(smallImg) + ", video="
//	+ Arrays.toString(video) + ", otherImg1=" + Arrays.toString(otherImg1) + ", otherImg2="
//	+ Arrays.toString(otherImg2) + ", collectType=" + collectType + ", banner=" + banner + ", focus="
//	+ focus + ", totalClick=" + totalClick + ", isON=" + isON + ", eventType=" + eventType + ", needSeat="
//	+ needSeat + ", seatX=" + seatX + ", seatY=" + seatY + "]";
    
    private static final String SEARCH_ALL = 
			"SELECT eventNumber, eventName, eventStartDate, eventEndDate, peopleNumber, eventPlace, eventType,"
    		+" isOn, collectType "
			+"FROM EVENT "
			+ "where eventName like  CONCAT('%', ? , '%') " 
			 +"   or eventPlace like CONCAT('%', ? , '%') "
			 +"   or eventDescribe like CONCAT('%', ? , '%') "
			+ "	or eventSummary like CONCAT('%', ? , '%') ";

	@Override
	public List<EventVO> searchBy(String searchString) {
		List<EventVO> list = new ArrayList<EventVO>();
		EventVO eventvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			pstmt = con.prepareStatement(SEARCH_ALL);
			pstmt.setString(1, searchString);
			pstmt.setString(2, searchString);
			pstmt.setString(3, searchString);
			pstmt.setString(4, searchString);
			rs = pstmt.executeQuery();
 
			while (rs.next()) {
				eventvo = new EventVO();
				eventvo.setEventNumber(rs.getInt("eventNumber"));
				eventvo.setEventName(rs.getString("eventName"));
				eventvo.setEventStartDate(rs.getTimestamp("eventStartDate"));
				eventvo.setEventEndDate(rs.getTimestamp("eventEndDate"));
				eventvo.setPeopleNumber(rs.getInt("peopleNumber"));
				eventvo.setEventPlace(rs.getString("eventPlace"));
				eventvo.setEventType(rs.getString("eventType"));
				eventvo.setCollectType(rs.getBoolean("collectType"));
				eventvo.setIsON(rs.getBoolean("isON"));
				list.add(eventvo); // Store the row in the list
			}
			System.out.println("event dao used select all");
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

	private static final String SEARCH_BANNER = 
			"SELECT eventNumber FROM EVENT "
					+ "where banner = 1 " ;

					
	@Override
	public List<Integer> getBanner() {
			List<Integer> bannerInt = new ArrayList<Integer>();
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				Class.forName(Common.driver);
				con = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
				pstmt = con.prepareStatement(SEARCH_BANNER);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					bannerInt.add(rs.getInt("eventNumber"));
				}
				System.out.println("event dao used get banner");
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
		return bannerInt;
	}

	@Override
	public EventVO selectSingleEvent(Integer eventNumber) {
		EventVO eventvo = null;
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
				eventvo = new EventVO();
				eventvo.setEventNumber(rs.getInt("eventNumber"));
				eventvo.setOrganizerNumber(rs.getInt("organizerNumber"));
				eventvo.setEventName(rs.getString("eventName"));
				eventvo.setEventStartDate(rs.getTimestamp("eventStartDate"));
				eventvo.setEventEndDate(rs.getTimestamp("eventEndDate"));
				eventvo.setPeopleNumber(rs.getInt("peopleNumber"));
				eventvo.setEventPlace(rs.getString("eventPlace"));
				eventvo.setEventP2(rs.getString("eventP2"));
				eventvo.setEventSummary(rs.getString("eventSummary"));
				eventvo.setEventDescribe(rs.getString("eventDescribe"));
//				eventvo.setBigImg(rs.getBytes("bigImg"));
//				eventvo.setSmallImg(rs.getBytes("smallImg"));
				eventvo.setCollectType(rs.getBoolean("collectType"));
//				eventvo.setBanner(rs.getInt("banner"));
				eventvo.setFocus(rs.getInt("focus"));
				eventvo.setIsON(rs.getBoolean("isON"));
				eventvo.setEventType(rs.getString("eventType"));
				eventvo.setNeedSeat(rs.getBoolean("needSeat"));
				eventvo.setSeatX(rs.getInt("seatX"));
				eventvo.setSeatY(rs.getInt("seatY"));
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
		return eventvo;
	}
	
	
}
