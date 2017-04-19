<%-- 
    Document   : checkHospitalId
    Created on : 19-Mar-2017, 16:55:30
    Author     : ndine
--%>
<%@ page session="true" %>
<%@page import="com.intelligentz.appointmentz.controllers.Data"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title></title>
    </head>
    <body>
        <%
           response.addHeader("STATE", Data.checkRoomId((String)request.getParameter("room_id"),(String)session.getAttribute("hospital_id")));
        %>
    </body>
</html>
