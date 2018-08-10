package org.btm.getApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecondServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String sph=req.getParameter("ph");
		long phone=Long.parseLong(sph);
		PrintWriter out=resp.getWriter();
		
		
		//Connecting to DataBase
		
		Connection con=null;
		PreparedStatement pstmt=null;
		String qry="select * from muzeeb.details where phone=?";
		ResultSet rs=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=muzeeb");
			pstmt=con.prepareStatement(qry);
			pstmt.setLong(1, phone);
			rs=pstmt.executeQuery();
			if(rs.next())
			{
				String name=rs.getString("name");
				long Aadhar=rs.getLong("Aadhar");
				out.println("<html><body bgcolor='orange'>"
						+ "<h1>Your name is "+name+" & you aadhar number is "+Aadhar+"</h1>"
								+ "</body></html>");
			}		
			else
			{
				out.println("<html><body bgcolor='red'>"
						+ "<h1>YOUR DETAILS ARE NOT AVAILABLE</h1>"
						+ "</body></html>");
			}
			out.flush();
			out.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null)
			{
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
