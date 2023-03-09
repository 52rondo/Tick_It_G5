package tw.com.tibame.event.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import tw.com.tibame.util.common.Common;

public class SeatJDBCDAO implements SeatDAO_interface{
	private static final String seatInsertSQL = "insert into SEAT(eventNumber,seatNumber,seatSet) value(?,?,?);";
//	private static final String seatUpdateSQL = "update SEAT set seatNumber=?, seatSet=? where eventNumber=?;";
	private static final String selectByEventNumber = "select * from SEAT where eventNumber = ? order by seatSet";
	private static final String seatDeleteSQL = "delete from SEAT where eventNumber=?;";
	private static final String queryByEventNumberSQL = 
            " select * from SEAT where EVENTNUMBER = ? order by SEATNUMBER ";
    private static final String queryByIdSQL = " select * from SEAT where seatID = ? ";
    private static final String updateBySeatSetAndEventNumverSQL = 
            " update SEAT set seatType = ? where eventNumber = ? and seatSet = ? ";
    private static final String queryBySetAndEventNumberSQL = "select * from SEAT where eventNumber = ? and seatSet = ? ";
    private static final String updateSQL = " update SEAT set %s where seatID = ? ";
    
	@Override
	public int insert(SeatVO aseat,Connection conn) {
		PreparedStatement ps = null;
		int rowCount = 0;
		try {
			ps = conn.prepareStatement(seatInsertSQL);
			ps.setInt(1, aseat.getEventNumber());
			ps.setInt(2, aseat.getSeatNumber());
			ps.setInt(3,aseat.getSeatSet());
			
			rowCount = ps.executeUpdate();
			System.out.println(rowCount + " Seat inserted!!");
			return rowCount;
		} catch (SQLException se) {
			if (conn != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-Seat");
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
		}
	}

	

	@Override
	public int updateByseatType() {

		return 0;
	}

	@Override
	public SeatVO eventNumber() {

		return null;
	}
	
	public List<SeatVO> selectByEventNumber(Integer eventNumber){
		List<SeatVO> seatvolist = new ArrayList<SeatVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
			pstmt = con.prepareStatement(selectByEventNumber);

			pstmt.setInt(1, eventNumber);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				SeatVO seatvo = new SeatVO();
				seatvo.setSeatID(rs.getInt("seatID"));
				seatvo.setEventNumber(rs.getInt("eventNumber"));
				seatvo.setSeatType(rs.getBoolean("seatType"));
				seatvo.setSeatNumber(rs.getInt("seatNumber"));
				seatvo.setSeatSet(rs.getInt("seatSet"));
				seatvolist.add(seatvo);
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
		return seatvolist;
	}
	public int deleteByEventNumber(Integer eventNumber, Connection con) {
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			pstmt = con.prepareStatement(seatDeleteSQL);
			pstmt.setInt(1, eventNumber);

			rowCount = pstmt.executeUpdate();
			System.out.println(rowCount + " SEAT deleted!!");
			return rowCount;
			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-SEAT_delete");
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
	
	private SeatVO buildVO (ResultSet rs) throws Exception {
        SeatVO vo = new SeatVO();
        
        vo.setSeatID(rs.getInt("seatID"));
        vo.setEventNumber(rs.getInt("eventNumber"));
        vo.setSeatNumber(rs.getInt("seatNumber"));
        vo.setSeatSet(rs.getInt("seatSet"));
        vo.setSeatType(rs.getBoolean("seatType"));
        
        return vo;
    }
    
    
    
    @Override
    public SeatVO queryBySetAndEventNumber(int seatSet, int eventNumber) {
        SeatVO vo = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            ps = conn.prepareStatement(queryBySetAndEventNumberSQL);
            ps.setInt(1, eventNumber);
            ps.setInt(2, seatSet);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                vo = buildVO(rs);
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



    @Override
    public int updateBySeatSetAndEventNumver(int seatSet, int eventNumber ,Boolean seatType) {


        Connection conn = null;
        PreparedStatement ps = null;
        int res = 0;
        try {
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            ps = conn.prepareStatement(updateBySeatSetAndEventNumverSQL);
            
            ps.setBoolean(1,seatType);
            ps.setInt(2,eventNumber);
            ps.setInt(3,seatSet);
            res = ps.executeUpdate();
            
            
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
    
        return res;
    }





    @Override
    public SeatVO queryById(int id) {
        SeatVO vo = null;
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
                vo = buildVO(rs);
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

	@Override
	public void updateSeat(SeatVO vo) {


        Connection conn = null;
        PreparedStatement ps = null;
        int rowCount = 0;
        String setSQL = "";
        try {
            if(vo.getSeatType() != null) {
                setSQL += " seatType = ? ,";
            }
            
            setSQL = setSQL.substring(0,setSQL.lastIndexOf(","));
            
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            ps = conn.prepareStatement(String.format(updateSQL, setSQL));
            int idx = 1;
            if(vo.getSeatType() != null) {
                ps.setBoolean(idx, vo.getSeatType());
                idx++;
            }
            ps.setInt(idx,vo.getSeatID());
            
            ps.executeUpdate();
            System.out.println("SeatVO "+vo+" update");
            
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
    
	}
}
