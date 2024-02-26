<%@page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@page import="java.sql.Connection"%>
<%@page import="connexion.util.SingletonConnection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>

<%

    if (request.getParameter("ouserName") != null) //get "uname" jQuery & Ajax part this line 'data:"uname="+username' from index.jsp file check not null
    {
        String user_name = request.getParameter("ouserName"); //getable "uname" store in new "user_name variable"

        try {

            Connection con = SingletonConnection.getConnection();

            PreparedStatement pstmt = null; //create statement

            pstmt = con.prepareStatement("SELECT * from client where upper(username)=upper(?)"); //sql select query
            pstmt.setString(1, user_name); //set where cluase condition username set is new user_name variable
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