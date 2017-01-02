package solarSystemtest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/test")
	public class test extends GenericServlet {
		private static final long serialVersionUID = 1L;
		@Override
		public void service(ServletRequest request, ServletResponse response)
				throws ServletException, IOException {
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost/test", //JDBC URL
						"root",	// DBMS 사용자 아이디
						"2580");	// DBMS 사용자 암호
				stmt = conn.createStatement();
				rs = stmt.executeQuery(
						"SELECT *" + 
						" FROM test" 
						);
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<html><head><title>센서값</title></head>");
				out.println("<body><h1>센서목록</h1>");
				while(rs.next()) {
					out.println(
						rs.getInt("num") + " , " +
						rs.getDouble("h_solar_rad") + " , " +
						rs.getDouble("s_solar_rad") + " , " +
						rs.getDouble("module_temp") + " , " +
						rs.getDouble("ambient_temp") + " , " +
						rs.getDouble("co2") + " , " +
						rs.getDouble("gyro_x") + " , " +
						rs.getDouble("gyro_y") + " , " +
						rs.getDouble("gyro_z") + " , " +
						rs.getDouble("r_phase_current") + " , " +
						rs.getDouble("s_phase_current") + " , " +
						rs.getDouble("t_phase_current") + " , " +
						rs.getDate("time") + " , " + 
						rs.getInt("send") + "<br>"
					);
				}
				out.println("</body></html>");
			} catch (Exception e) {
				throw new ServletException(e);
			} finally {
				try {if (rs != null) rs.close();} catch(Exception e) {}
				try {if (stmt != null) stmt.close();} catch(Exception e) {}
				try {if (conn != null) conn.close();} catch(Exception e) {}
			}
		}
	}

