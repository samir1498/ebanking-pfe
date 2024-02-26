<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.sql.Connection"%>
<%@ page import="connexion.util.SingletonConnection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>

<%

    if (request.getParameter("cpass") != null && request.getParameter("cid") != null) //get "uname" jQuery & Ajax part this line 'data:"uname="+username' from index.jsp file check not null
    {
        String user_name = request.getParameter("cpass");
        int id = Integer.parseInt(request.getParameter("cid"));

        try {

            Connection con = SingletonConnection.getConnection();

            PreparedStatement pstmt = null; //create statement

            pstmt = con.prepareStatement("SELECT * from account where ownerid= ? and accountname= ?"); //sql select query
            pstmt.setInt(1, id); //set where cluase condition username set is new user_name variable
            pstmt.setString(2, user_name);
            ResultSet rs = pstmt.executeQuery(); //execute query and set in ResultSet object "rs".

            if (rs.next()) {
                out.print("true");
            } else {
                out.print("false");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>