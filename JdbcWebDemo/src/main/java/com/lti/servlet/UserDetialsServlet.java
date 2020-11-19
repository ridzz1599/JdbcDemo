package com.lti.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserDetialsServlet
 */
public class UserDetialsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		try {
			this.getUserDetails(request, response, username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void getUserDetails(HttpServletRequest request, HttpServletResponse response, String username)
			throws Exception {

		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");

		PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees WHERE first_name = ?");

		statement.setString(1, username);
		ResultSet resultSet = statement.executeQuery();
		RequestDispatcher rd = null;
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		if (resultSet.next()) {
			writer.println("<P ALIGN='center'>USER DATA <br> <TABLE BORDER=1>");
			writer.println("<TR>");
			writer.println("<TH>EMPLOYEE_ID</TH>");
			writer.println("<TH>NAME</TH>");
			writer.println("<TH>SALARY</TH>");
			writer.println("</TR>");
			while (resultSet.next()) {
				writer.println("<TR>");
				writer.print("<TD>" + resultSet.getInt(1) + "</TD>");
				writer.print("<TD>" + resultSet.getString(2) + "</TD>");
				writer.print("<TD>" + resultSet.getInt(8) + "</TD>");
				writer.println("</TR>");
			}
			writer.println("</TABLE></P>");
		}else{
			rd = request.getRequestDispatcher("failure.html");
			rd.forward(request,response);
		}
	}

}
