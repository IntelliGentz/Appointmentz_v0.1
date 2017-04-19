/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelligentz.appointmentz.controllers;

import com.intelligentz.appointmentz.database.DBConnection;
import java.beans.PropertyVetoException;
import java.io.IOException;
//import java.util.Date;
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

public class deleteRoom extends HttpServlet{  
    private static PreparedStatement preparedStmt;
    //private static ResultSet resultSet;
    private static java.sql.Connection connection;
    private static final Logger LOGGER = Logger.getLogger( deleteRoom.class.getName() );
    
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)  throws ServletException,IOException  
    {  
        try {
            String room_id = req.getParameter("room_id");
            connection = DBConnection.getDBConnection().getConnection();
            String SQL1 = "delete from db_bro.room where room_id=?";
            preparedStmt = connection.prepareStatement(SQL1);
            preparedStmt.setString (1, room_id);
            // execute the preparedstatement
            preparedStmt.execute();
            res.sendRedirect("./equipments");
        }
        catch (SQLException | PropertyVetoException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            res.sendRedirect("./error.jsp?error=Error in deletting room!\n+"+ex.toString()+"");
        }  
    }
}