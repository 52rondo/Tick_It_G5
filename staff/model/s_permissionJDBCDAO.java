package tw.com.tibame.staff.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class s_permissionJDBCDAO implements s_permissionVO_interface {

	private static final String INSERT_SPERMISSION = "INSERT INTO S_PERMISSION (staffNumber,permissionNumber) values(?,?)";

	@Override
	public void insert(S_permissionVO s_permissionVO, Connection con) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(INSERT_SPERMISSION);
			ps.setInt(1, s_permissionVO.getStaffNumber());
			ps.setInt(2, s_permissionVO.getPermissionNumber());

			ps.executeUpdate();
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-s_permissionJDBCDAO");
					con.rollback();
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
		}

	}

}
