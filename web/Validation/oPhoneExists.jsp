<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.sql.Connection"%>
<%@ page import="connexion.util.SingletonConnection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>

<%
Connection con = null;
	if (request.getParameter("ophone") != null)
	{
		String phoneNumber = request.getParameter("ophone");
        phoneNumber = phoneNumber.split("\\+")[1];

		try {

			con = SingletonConnection.getConnection();

			PreparedStatement pstmt = null; //create statement
			String query = "SELECT * from client where phonenumber=?";

			pstmt = con.prepareStatement(query); //sql select query
			pstmt.setString(1, phoneNumber); //set where cluase condition username set is new user_name variable
			ResultSet rs = pstmt.executeQuery(); //execute query and set in ResultSet object "rs".


			if (rs.next()) {
				out.print("false");
			} else {
				out.print("true");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
%>