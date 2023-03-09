package tw.com.tibame.event.model;


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
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import tw.com.tibame.util.common.Common;

public class SoldTicketsJDBCDAO implements SoldTicketsDAO_interface {
	
    private static final String selectByEventNumberSQL = 
             " SELECT s.* "
            +" FROM sold_tickets s "
            +" INNER JOIN `order` o ON o.orderID = s.orderID "
            +" INNER JOIN EVENT e ON e.eventNumber = o.eventNumber "
            +" WHERE e.eventNumber = ?";
    private static final String selectByEventAndTicketSQL = 
            " SELECT s.* "
           +" FROM sold_tickets s "
           +" INNER JOIN `order` o ON o.orderID = s.orderID "
           +" INNER JOIN EVENT e ON e.eventNumber = o.eventNumber "
           +" WHERE e.eventNumber = ? and s.ticketID = ?";
    
    private static final String selectByOrderIDSQL = 
            " SELECT s.* "
           +" FROM sold_tickets s "
           +" INNER JOIN `order` o ON o.orderID = s.orderID "
           +" WHERE o.orderID = ?";
	
    private static final String insertSQL = 
              " INSERT INTO sold_tickets ( orderID, seatID, ticketID, isUse, orderPrice, ticketQR) "
            + " VALUES "
            + " ( ?, NULL, ?, ?, ?, NULL ) "
            ;
    private static final String updateSQL = 
            " UPDATE sold_tickets set %s where ticketNumber = ? " ;
    
    private static final String deleteByOrderId = 
            " delete from sold_tickets where orderID = ? ";
    private static final String selectByIdSQL = 
            " select * from sold_tickets where ticketNumber = ? " ;

    
    
    @Override
    public void deleteByOrderId(int orderID) {
        Connection conn = null;
        PreparedStatement ps = null;
        int rowCount = 0;
        try {
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            ps = conn.prepareStatement(deleteByOrderId);
            ps.setInt(1,orderID);
            rowCount = ps.executeUpdate();
            
            System.out.println(rowCount + " row(s) deleted!!");
            
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

    @Override
    public int insert(SoldTicketsVO vo,Connection conn) {
        PreparedStatement ps = null;
        int rowCount = 0;
        try {
            ps = conn.prepareStatement(insertSQL);
            
            ps.setInt(1,vo.getOrderID());
            ps.setInt(2,vo.getTicketID());
            ps.setBoolean(3,vo.isUse());
            ps.setInt(4,vo.getOrderPrice());
            
            rowCount = ps.executeUpdate();
            
            System.out.println(rowCount + " Sold inserted!!");
            
        // Handle any driver errors
        } catch (SQLException se) {
			if (conn != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-Sold");
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
        return rowCount;
    
        
    }
    
    @Override
    public void updateUsed(int ticketNumber, Boolean used) {

        Connection conn = null;
        PreparedStatement ps = null;
        String setSQL = "";
        try {
            
            setSQL += " isUse = ? ,";
            
            setSQL = setSQL.substring(0,setSQL.lastIndexOf(","));
            
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            String finalSQL = String.format(updateSQL, setSQL);
            System.out.println("finalSQL:" + finalSQL);
            ps = conn.prepareStatement(finalSQL);
            
            ps.setBoolean(1, used);
            
            ps.setInt(2,ticketNumber);
            
            ps.executeUpdate();
            
        } catch (Exception se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
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

    @Override
    public void updateQRData(int ticketNumber, byte[] qr) {

        Connection conn = null;
        PreparedStatement ps = null;
        String setSQL = "";
        try {
            
            setSQL += " ticketQR = ? ,";
            
            setSQL = setSQL.substring(0,setSQL.lastIndexOf(","));
            
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            String finalSQL = String.format(updateSQL, setSQL);
            System.out.println("finalSQL:" + finalSQL);
            ps = conn.prepareStatement(finalSQL);
            
            ps.setBytes(1, qr);
            
            ps.setInt(2,ticketNumber);
            
            ps.executeUpdate();
            
        } catch (Exception se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
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

    @Override
    public int update(SoldTicketsVO vo) {
        Connection conn = null;
        PreparedStatement ps = null;
        int rowCount = 0;
        String setSQL = "";
        try {
            if(vo.getOrderID() != null) {
                setSQL += " orderID = ? ,";
            }
            if(vo.getSeatID() != null) {
                setSQL += " seatID = ? ,";
            }
            if(vo.getTicketID() != null) {
                setSQL += " ticketID = ? ,";
            }
            if(vo.isUse() != null) {
                setSQL += " isUse = ? ,";
            }
            if(vo.getOrderPrice() != null) {
                setSQL += " orderPrice = ? ,";
            }
            if(vo.getTicketQR() != null) {
                setSQL += " ticketQR = ? ,";
            }
            
            setSQL = setSQL.substring(0,setSQL.lastIndexOf(","));
            
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            String finalSQL = String.format(updateSQL, setSQL);
            System.out.println("finalSQL:" + finalSQL);
            ps = conn.prepareStatement(finalSQL);
            int idx = 1;
            if(vo.getOrderID() != null) {
                ps.setInt(idx,vo.getOrderID());
                idx++;
            }
            if(vo.getSeatID() != null) {
                ps.setInt(idx,vo.getSeatID());
                idx++;
            }
            if(vo.getTicketID() != null) {
                ps.setInt(idx,vo.getTicketID());
                idx++;
            }
            if(vo.isUse() != null) {
                ps.setBoolean(idx,vo.isUse());
                idx++;
            }
            if(vo.getOrderPrice() != null) {
                ps.setInt(idx,vo.getOrderPrice());
                idx++;
            }
            if(vo.getTicketQR() != null) {
                ps.setBytes(idx, vo.getTicketQR());
                idx++;
            }
            
            ps.setInt(idx,vo.getTicketNumber());
            
            rowCount = ps.executeUpdate();
            
            System.out.println(" SoldTicketsVO updated:" + vo);
            
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
        return rowCount;
    }
    
    @Override
    public SoldTicketsVO queryById(int ticketNumber) {

        
        SoldTicketsVO vo = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            ps = conn.prepareStatement(selectByIdSQL);
            ps.setInt(1, ticketNumber);
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
    public List<SoldTicketsVO> selectByOrderID(int orderId) {
        
        List<SoldTicketsVO>  list = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            ps = conn.prepareStatement(selectByOrderIDSQL);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                SoldTicketsVO vo = buildVO(rs);
                list.add(vo);
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
        
        return list;
    
        
    }
    
    private SoldTicketsVO buildVO (ResultSet rs) throws Exception {
        SoldTicketsVO vo = new SoldTicketsVO();
        
        vo.setOrderID(rs.getInt("orderID"));
        vo.setTicketNumber(rs.getInt("ticketNumber"));
        vo.setSeatID(rs.getInt("seatID"));
        vo.setTicketID(rs.getInt("ticketID"));
        vo.setUse(rs.getBoolean("isUse"));
        vo.setOrderPrice(rs.getInt("orderPrice"));
        vo.setTicketQR(rs.getBytes("ticketQR"));
        
        return vo;
    }

    @Override
    public List<SoldTicketsVO> selectByEventNumber(int eventNumber) {
        
        List<SoldTicketsVO>  list = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            ps = conn.prepareStatement(selectByEventNumberSQL);
            ps.setInt(1, eventNumber);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                SoldTicketsVO vo = buildVO(rs);
                list.add(vo);
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
        
        return list;
    
        
    }

    @Override
    public List<SoldTicketsVO> selectByEventAndTicket(int eventNumber, int ticketId) {
        
        List<SoldTicketsVO>  list = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(Common.driver);
            conn = DriverManager.getConnection(Common.URL,Common.USER,Common.PASSWORD);
            ps = conn.prepareStatement(selectByEventAndTicketSQL);
            ps.setInt(1, eventNumber);
            ps.setInt(2, ticketId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                SoldTicketsVO vo = buildVO(rs);
                list.add(vo);
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
        
        return list;
    
        
    
    }
    
    

}
