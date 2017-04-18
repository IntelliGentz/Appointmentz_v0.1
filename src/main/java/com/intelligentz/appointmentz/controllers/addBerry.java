/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelligentz.appointmentz.controllers;

import com.intelligentz.appointmentz.database.DBConnection;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ndine
 */
public class addBerry extends HttpServlet{  
    private static PreparedStatement preparedStmt;
    //private static ResultSet resultSet;
    private static Connection connection;
    private static final Logger LOGGER = Logger.getLogger( addBerry.class.getName() );
    
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)  throws ServletException,IOException  
    {  
        try {
            String room_number = req.getParameter("room_number");
            String hospital_id = req.getParameter("hospital_id");
            String auth = req.getParameter("auth");
            String serial = req.getParameter("serial");
            connection = DBConnection.getDBConnection().getConnection();
            String SQL1 = "insert into appointmentzv1.rpi ( room_number, hospital_id, auth, serial) VALUES (?,?,?,?)";
            preparedStmt = connection.prepareStatement(SQL1);
            preparedStmt.setString (1, room_number);
            preparedStmt.setString (2, hospital_id);
            preparedStmt.setString (3, auth);
            preparedStmt.setString (4, serial);
            // execute the preparedstatement
            preparedStmt.execute();
            res.sendRedirect("./home");
        }
        catch (SQLException | PropertyVetoException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            res.sendRedirect("./error.jsp?error=Error in adding device!\n+"+ex.toString()+"");
        }
    }
}  
