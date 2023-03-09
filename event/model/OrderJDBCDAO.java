package tw.com.tibame.event.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

import tw.com.tibame.staff.model.StaffVO;
import tw.com.tibame.util.common.Common;

public class OrderJDBCDAO implements OrderDAO_interface {
	private static final String GET_ORDER_BY_EVENT_NUMBER = "SELECT orderID , number , orderDate, orderType, total , totalTicket , pData ,reason "
			+ "FROM `ORDER` o , `EVENT` e " + "WHERE e.eventNumber = ?;";
	private static final String GET_ORDER_BY_ORDER_DATE = "";
	private static final String GET_ORDER_BY_ORDER_TYPE = "";
	private static final String GET_ORDER_BY_NUMBER = "";
	private static final String GET_ONE_BY_ORDERID = "SELECT o.orderID, o.`number` ,o.eventNumber ,e.eventName  , o.orderDate, o.totalTicket, o.total, o.orderType from "
			+ "    `ORDER` o join `EVENT` e on o.eventNumber = e.eventNumber "
			+ "    where  orderID = ?  and organizerNumber = ?;";
	private static final String GET_BY_ORDERID = "select a.orderID,a.eventName, a.eventStartDate, a.eventPlace, oz.organizerName, a.orderType, a.totalTicket, a.total, a.bigImg ,a.eventNumber\r\n"
			+ "from ORGANIZER oz \r\n"
			+ "join (select e.eventNumber ,e.organizerNumber ,e.eventName, e.eventStartDate, e.eventPlace, o.orderType, o.totalTicket, o.total, o.orderID, e.bigImg \r\n"
			+ "  from `EVENT` e join `ORDER` o on e.eventNumber = o.eventNumber \r\n"
			+ "  where e.eventNumber in(select eventNumber from `ORDER` where `number`=?)) a on oz.organizerNumber = a.organizerNumber;";
	private static final String GET_ORGANIZER_BY_NUMBER = "SELECT eventNumber,eventName,eventType,eventStartDate,eventEndDate  "
			+ " " 
			+ "FROM TICK_IT_TEST.`EVENT` e,ORGANIZER o " 
			+ "where o.organizerNumber = ? "
			+ "and e.organizerNumber = o.organizerNumber;";
	private static final String GET_ORDER_BY_ORGANIZER = "select o.orderID, o.`number` ,o.eventNumber ,e.eventName  , o.orderDate, o.totalTicket, o.total, o.orderType from "
			+ "	`ORDER` o join `EVENT` e on o.eventNumber = e.eventNumber "
			+ "    where e.eventNumber in(select eventNumber from `EVENT` where organizerNumber = ?) "
			+ "	   order by o.orderID desc;";
	private static final String SELECT_BY_ORGANIZERNUMBER = "SELECT eventNumber, eventName, eventType, eventStartDate, eventEndDate "
			+ "FROM `EVENT` where organizerNumber  = ?;";
	
	// elsa
	private static final String insertSQL = " INSERT INTO `order` " + " ( " + "    eventNumber    "
			+ "  , number         " + "  , orderDate      " + "  , orderType      " + "  , total          "
			+ "  , totalTicket    " + "  , pData          " + "  , reason         " + "  , reasonMoney    "
			+ "  , eventScore     " + "  , eventSContent  " + "  , eventSDate     " + "  ) "
			+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, null, NULL, NULL)";
	private static final String queryByOrderIdSQL = "SELECT * from `ORDER` where orderID = ? ";
	private static final String queryByOrderIdAndMemberSQL = "SELECT * from `ORDER` where orderID = ? and number = ?";
	private static final String updateSQL = " UPDATE `ORDER` set %s where orderID = ? ";
	private static final String selectByEventNumberSQL = "select * from `ORDER` where eventNumber = ?";
	

	@Override
	public int insert(OrderVO vo, List<SoldTicketsVO> solList) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int orderId = 0;
		try {
			Class.forName(Common.driver);
			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);

			conn.setAutoCommit(false);
			String[] c = { "orderID" };
			ps = conn.prepareStatement(insertSQL, c);

			ps.setInt(1, vo.getEventNumber());
			ps.setInt(2, vo.getNumber());
			ps.setTimestamp(3, vo.getOrderDate());
			ps.setString(4, vo.getOrderType());
			ps.setInt(5, vo.getTotal());
			ps.setInt(6, vo.getTotalTicket());
			ps.setString(7, vo.getpData());
			ps.setString(8, vo.getReason());
			ps.setInt(9, vo.getReasonMoney());

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
				System.out.println("自增主鍵值= " + orderId);
			} else {
				System.out.println("未取得自增主鍵值");
			}
			System.out.println(" order inserted!!");
			rs.close();

			SoldTicketsJDBCDAO soDao = new SoldTicketsJDBCDAO();
			for (SoldTicketsVO sovo : solList) {
				sovo.setOrderID(orderId);
				soDao.insert(sovo, conn);
			}

			conn.commit();
			conn.setAutoCommit(true);

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (conn != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-Order");
					conn.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return orderId;
	}

	@Override
	public int update(OrderVO vo, List<SoldTicketsVO> solList) {
		Connection conn = null;
		PreparedStatement ps = null;
		int rowCount = 0;
		String setSQL = "";
		try {

			if (vo.getOrderType() != null) {
				setSQL += " orderType = ? ,";
			}
			if (vo.getTotal() != null) {
				setSQL += " total = ? ,";
			}
			if (vo.getTotalTicket() != null) {
				setSQL += " totalTicket = ? ,";
			}
			if (vo.getpData() != null) {
				setSQL += " pData = ? ,";
			}

			setSQL = setSQL.substring(0, setSQL.lastIndexOf(","));

			Class.forName(Common.driver);
			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);

			conn.setAutoCommit(false);

			ps = conn.prepareStatement(String.format(updateSQL, setSQL));
			int idx = 1;
			if (vo.getOrderType() != null) {
				ps.setString(idx, vo.getOrderType());
				idx++;
			}
			if (vo.getTotal() != null) {
				ps.setInt(idx, vo.getTotal());
				idx++;
			}
			if (vo.getTotalTicket() != null) {
				ps.setInt(idx, vo.getTotalTicket());
				idx++;
			}
			if (vo.getpData() != null) {
				ps.setString(idx, vo.getpData());
				idx++;
			}

			ps.setInt(idx, vo.getOrderID());

			rowCount = ps.executeUpdate();

			SoldTicketsJDBCDAO soDao = new SoldTicketsJDBCDAO();
			for (SoldTicketsVO sovo : solList) {
				sovo.setOrderID(vo.getOrderID());
				soDao.insert(sovo, conn);
			}

			conn.commit();
			conn.setAutoCommit(true);

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (conn != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-Order");
					conn.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return rowCount;
	}

	@Override
	public int updateOrder(OrderVO vo) {

		Connection conn = null;
		PreparedStatement ps = null;
		int rowCount = 0;
		String setSQL = "";
		try {

			if (vo.getOrderType() != null) {
				setSQL += " orderType = ? ,";
			}
			if (vo.getTotal() != null) {
				setSQL += " total = ? ,";
			}
			if (vo.getTotalTicket() != null) {
				setSQL += " totalTicket = ? ,";
			}
			if (vo.getpData() != null) {
				setSQL += " pData = ? ,";
			}

			setSQL = setSQL.substring(0, setSQL.lastIndexOf(","));

			Class.forName(Common.driver);
			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);

			conn.setAutoCommit(false);

			ps = conn.prepareStatement(String.format(updateSQL, setSQL));
			int idx = 1;
			if (vo.getOrderType() != null) {
				ps.setString(idx, vo.getOrderType());
				idx++;
			}
			if (vo.getTotal() != null) {
				ps.setInt(idx, vo.getTotal());
				idx++;
			}
			if (vo.getTotalTicket() != null) {
				ps.setInt(idx, vo.getTotalTicket());
				idx++;
			}
			if (vo.getpData() != null) {
				ps.setString(idx, vo.getpData());
				idx++;
			}

			ps.setInt(idx, vo.getOrderID());

			rowCount = ps.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (conn != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-Order");
					conn.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return rowCount;

	}

	@Override
	public List<OrderVO> selectByEventNumber(int eventNumber) {

		List<OrderVO> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName(Common.driver);
			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			ps = conn.prepareStatement(selectByEventNumberSQL);
			ps.setInt(1, eventNumber);
			rs = ps.executeQuery();

			while (rs.next()) {
				OrderVO vo = buildVO(rs);
				list.add(vo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return list;

	}

	private OrderVO buildVO(ResultSet rs) throws Exception {
		OrderVO vo = new OrderVO(rs.getInt("orderID"), rs.getInt("eventNumber"), rs.getInt("number"),
				rs.getTimestamp("orderDate"), rs.getString("orderType"), rs.getInt("total"), rs.getInt("totalTicket"),
				rs.getString("pData"), rs.getString("reason"), rs.getInt("reasonMoney"), rs.getFloat("eventScore"),
				rs.getString("eventSContent"), rs.getTimestamp("eventSDate"));
		return vo;
	}

	@Override
	public OrderVO queryByOrderId(int OrderId) {
		OrderVO vo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName(Common.driver);
			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			ps = conn.prepareStatement(queryByOrderIdSQL);
			ps.setInt(1, OrderId);
			rs = ps.executeQuery();

			while (rs.next()) {
				vo = buildVO(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return vo;
	}

	@Override
	public OrderVO queryByOrderIdAndMember(int OrderId, int memberId) {
		OrderVO vo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName(Common.driver);
			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			ps = conn.prepareStatement(queryByOrderIdAndMemberSQL);
			ps.setInt(1, OrderId);
			ps.setInt(1, memberId);
			rs = ps.executeQuery();

			while (rs.next()) {
				vo = buildVO(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

		return vo;
	}

	@Override
	public List<OrderVO> selectByDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderVO> selectByOrderType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderVO> selectByNumber(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(OrderVO orderVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(OrderVO orderVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<OrderVO> selectByEventNumber(Integer eventNumber) {
		List<OrderVO> list = new ArrayList<OrderVO>();
		// 初值
		OrderVO OrderVO = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			System.out.println("selectByEventNumber jdbc");
			Class.forName(Common.driver);

			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			pstmt = conn.prepareStatement(GET_ORDER_BY_EVENT_NUMBER);
//			pstmt.setInt(1, eventNumber);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderVO = new OrderVO();

				OrderVO.setOrderID(rs.getInt("orderID"));
				OrderVO.setNumber(rs.getInt("number"));
				OrderVO.setOrderDate(rs.getTimestamp("orderdate"));
				OrderVO.setOrderType(rs.getString("orderType"));
				OrderVO.setTotal(rs.getInt("total"));
				OrderVO.setTotalTicket(rs.getInt("totalTicket"));
				OrderVO.setpData(rs.getString("pData"));
				OrderVO.setReason(rs.getString("reason"));

				list.add(OrderVO);
				// 將上述SET完成的vo，塞進去這個集合。Store the row in the list
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		}

		return list;

	}

	@Override
	public List<OrderVO> selectByOrderDate(Timestamp orderDate) {
		List<OrderVO> list = new ArrayList<OrderVO>();
		// 初值
		OrderVO OrderVO = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			System.out.println("selectByorderDate jdbc");
			Class.forName(Common.driver);

			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			pstmt = conn.prepareStatement(GET_ORDER_BY_ORDER_DATE);
			pstmt.setTimestamp(1, orderDate);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderVO = new OrderVO();

//				eventNumber, `number`, orderDate, orderType, total, totalTicket, pData, reason, reasonMoney, eventScore, eventSContent, eventSDate
				OrderVO.setEventNumber(rs.getInt("eventNumber"));
				OrderVO.setNumber(rs.getInt("number"));
				OrderVO.setOrderDate(rs.getTimestamp("orderdate"));
				OrderVO.setOrderType(rs.getString("orderType"));
				OrderVO.setTotal(rs.getInt("total"));
				OrderVO.setTotalTicket(rs.getInt("totalTicket"));
				OrderVO.setpData(rs.getString("pData"));
				OrderVO.setReason(rs.getString("reason"));
				OrderVO.setReasonMoney(rs.getInt("reasonMoney"));
				OrderVO.setEventScore(rs.getFloat("eventScore")); // 原寫getDouble 但資料表定義為float 故改成getFloat
				OrderVO.setEventSContent(rs.getString("eventSContent"));
				OrderVO.setEventSDate(rs.getTimestamp("eventSDate"));

				list.add(OrderVO);
				// 將上述SET完成的vo，塞進去這個集合。Store the row in the list
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		}

		return list;

	}

	@Override
	public List<OrderVO> selectByOrderType(String orderType) {
		List<OrderVO> list = new ArrayList<OrderVO>();
		// 初值
		OrderVO OrderVO = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			System.out.println("selectByorderType jdbc");
			Class.forName(Common.driver);

			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			pstmt = conn.prepareStatement(GET_ORDER_BY_ORDER_TYPE);
			pstmt.setString(1, orderType);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderVO = new OrderVO();

//				eventNumber, `number`, orderDate, orderType, total, totalTicket, pData, reason, reasonMoney, eventScore, eventSContent, eventSDate
				OrderVO.setEventNumber(rs.getInt("eventNumber"));
				OrderVO.setNumber(rs.getInt("number"));
				OrderVO.setOrderDate(rs.getTimestamp("orderdate"));
				OrderVO.setOrderType(rs.getString("orderType"));
				OrderVO.setTotal(rs.getInt("total"));
				OrderVO.setTotalTicket(rs.getInt("totalTicket"));
				OrderVO.setpData(rs.getString("pData"));
				OrderVO.setReason(rs.getString("reason"));
				OrderVO.setReasonMoney(rs.getInt("reasonMoney"));
				OrderVO.setEventScore(rs.getFloat("eventScore"));// 原寫getDouble 但資料表定義為float 故改成getFloat
				OrderVO.setEventSContent(rs.getString("eventSContent"));
				OrderVO.setEventSDate(rs.getTimestamp("eventSDate"));

				list.add(OrderVO);
				// 將上述SET完成的vo，塞進去這個集合。Store the row in the list
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		}

		return list;

	}

	@Override
	public List<OrderVO> selectByNumber(Integer number) {
		List<OrderVO> list = new ArrayList<OrderVO>();
		// 初值
		OrderVO OrderVO = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			System.out.println("selectBynumber jdbc");
			Class.forName(Common.driver);

			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			pstmt = conn.prepareStatement(GET_ORDER_BY_NUMBER);
			pstmt.setInt(1, number);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderVO = new OrderVO();

//				eventNumber, `number`, orderDate, orderType, total, totalTicket, pData, reason, reasonMoney, eventScore, eventSContent, eventSDate
				OrderVO.setEventNumber(rs.getInt("eventNumber"));
				OrderVO.setNumber(rs.getInt("number"));
				OrderVO.setOrderDate(rs.getTimestamp("orderdate"));
				OrderVO.setOrderType(rs.getString("orderType"));
				OrderVO.setTotal(rs.getInt("total"));
				OrderVO.setTotalTicket(rs.getInt("totalTicket"));
				OrderVO.setpData(rs.getString("pData"));
				OrderVO.setReason(rs.getString("reason"));
				OrderVO.setReasonMoney(rs.getInt("reasonMoney"));
				OrderVO.setEventScore(rs.getFloat("eventScore"));// 原寫getDouble 但資料表定義為float 故改成getFloat
				OrderVO.setEventSContent(rs.getString("eventSContent"));
				OrderVO.setEventSDate(rs.getTimestamp("eventSDate"));

				list.add(OrderVO);
				// 將上述SET完成的vo，塞進去這個集合。Store the row in the list
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		}

		return list;

	}

	@Override
	public List<OrderEventVO> findByNumber(Integer number) {
		List<OrderEventVO> list = new ArrayList<OrderEventVO>();
		// 初值
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(Common.driver);

			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			pstmt = conn.prepareStatement(GET_BY_ORDERID);
//			System.out.println("number="+number);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderEventVO orderEventVO = new OrderEventVO();
				orderEventVO.setOrderID(rs.getInt("orderID"));
				orderEventVO.setEventName(rs.getString("eventName"));
				orderEventVO.setEventStartDate(rs.getTimestamp("eventStartDate"));
				orderEventVO.setEventPlace(rs.getString("eventPlace"));
				orderEventVO.setOrganizerName(rs.getString("organizerName"));
				orderEventVO.setOrderType(rs.getString("orderType"));
				orderEventVO.setTotalTicket(rs.getInt("totalTicket"));
				orderEventVO.setTotal(rs.getInt("total"));
				orderEventVO.setBigImg(rs.getBytes("bigImg"));
				orderEventVO.setEventNumber(rs.getInt("eventNumber"));
				list.add(orderEventVO);
				// 將上述SET完成的vo，塞進去這個集合。Store the row in the list
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		}

		return list;

	}

	@Override
	public List<EventVO> findByOrganizerNumber(Integer organizerNumber) {
		List<EventVO> list = new ArrayList<EventVO>();
		// 初值
//		EventVO eventVO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(Common.driver);

			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			pstmt = conn.prepareStatement(SELECT_BY_ORGANIZERNUMBER);
			pstmt.setInt(1, organizerNumber);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				EventVO eventVO = new EventVO();

				eventVO.setEventNumber(rs.getInt("eventNumber"));
				eventVO.setEventName(rs.getString("eventName"));
				eventVO.setEventType(rs.getString("eventType"));
				eventVO.setEventStartDate(rs.getTimestamp("eventStartDate"));
				eventVO.setEventEndDate(rs.getTimestamp("eventEndDate"));

				list.add(eventVO);
				// 將上述SET完成的vo，塞進去這個集合。Store the row in the list
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		}
		return list;
	}

	@Override
	public List<Map> searchByOrderID(Integer orderID,Integer organizerNumber) {
		List<Map> list = new ArrayList<Map>();
		OrderVO orderVO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(Common.driver);
			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			pstmt = conn.prepareStatement(GET_ONE_BY_ORDERID);

			pstmt.setInt(1, orderID);
			pstmt.setInt(2, organizerNumber);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map map = new HashMap();
				map.put("orderID",rs.getInt(1));
				map.put("number",rs.getInt(2));
				map.put("eventNumber",rs.getInt(3));
				map.put("eventName",rs.getString(4));
				map.put("orderDate",rs.getTimestamp(5));
				map.put("totalTicket",rs.getInt(6));
				map.put("total",rs.getInt(7));
				map.put("orderType",rs.getString(8));
				list.add(map);
			}
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	public List<Map> selectOrderByOrganizer(Integer organizerNumber){
		List<Map> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName(Common.driver);
			conn = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
			ps = conn.prepareStatement(GET_ORDER_BY_ORGANIZER);
			ps.setInt(1, organizerNumber);
			rs = ps.executeQuery();

			while (rs.next()) {
				Map map = new HashMap();
				map.put("orderID",rs.getInt(1));
				map.put("number",rs.getInt(2));
				map.put("eventNumber",rs.getInt(3));
				map.put("eventName",rs.getString(4));
				map.put("orderDate",rs.getTimestamp(5));
				map.put("totalTicket",rs.getInt(6));
				map.put("total",rs.getInt(7));
				map.put("orderType",rs.getString(8));
				list.add(map);
			}

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

		return list;
	}
}
