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
import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author ndine
 */
public class editBerry extends HttpServlet{  
    private static PreparedStatement preparedStmt;
    //private static ResultSet resultSet;
    private static Connection connection;
    private static final Logger LOGGER = Logger.getLogger( editBerry.class.getName() );
    
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)  throws ServletException,IOException  
    {  
        try {
            String room_id = req.getParameter("room_id");
            //String room_number = req.getParameter("room_number");
            String auth = req.getParameter("auth_hidden");
            String serial = req.getParameter("serial_hidden");
            connection = DBConnection.getDBConnection().getConnection();
            String SQL1 = "update rpi set room_id = ? where serial= ?";
            preparedStmt = connection.prepareStatement(SQL1);
            preparedStmt.setString (1, room_id);
            preparedStmt.setString (2, serial);
            // execute the preparedstatement
            preparedStmt.execute();
            res.sendRedirect("./equipments?status=Successfully Updated Device Details Serial:"+serial+" -> Room_id:"+room_id);
        }
        catch (SQLException | PropertyVetoException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            res.sendRedirect("./error.jsp?error=Error in adding device!\n+"+ex.toString()+"");
        }
        finally 
        {
            try {
           // DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(preparedStmt);
            DbUtils.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(register.class.getName()).log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }
}  
