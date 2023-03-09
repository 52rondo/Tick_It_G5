package tw.com.tibame.main;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.JsonObject;

import tw.com.tibame.util.common.Common;

@WebServlet("/DBGifReader")
public class DBGifReader extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
//		System.out.println("dbgifReader running:");
		String eventId = req.getParameter("eventId");
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
				"SELECT bigImg FROM EVENT WHERE eventNumber = " + eventId);

//			JsonObject resBody = new JsonObject();
//			if (list1 != null ) {
//				resBody.addProperty("successful", true);
//				resBody.addProperty("allEvents", gson1.toJson(list1));
//			} else {
//				resBody.addProperty("successful", false);
//			}
//			
//			res.getWriter().write(resBody.toString());
			
			if (rs.next()) {
				InputStream in = new BufferedInputStream(rs.getBinaryStream("bigImg"));
//				byte[] buf = new byte[4 * 1024]; // 4K buffer
//				int len;
//				in.read(buf);
//				while ((len = in.read(buf)) != -1) {
//					out.write(buf, 0, len);
//				}
				byte[] buf = in.readAllBytes(); // 4K buffer
				out.write(buf);
				in.close();
			} else {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void init() throws ServletException {
		try {
			Class.forName(Common.driver);
			con = DriverManager.getConnection(Common.URL, Common.USER, Common.PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new UnavailableException("Couldn't load JdbcOdbcDriver");
		} catch (SQLException e) {
			throw new UnavailableException("Couldn't get db connection");
		}
	}

	public void destroy() {
		try {
			if (con != null) con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}

