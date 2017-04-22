/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelligentz.appointmentz.controllers;


import com.intelligentz.appointmentz.database.DBConnection;
import java.beans.PropertyVetoException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class addR extends HttpServlet{  
    private static PreparedStatement preparedStmt;
    private static ResultSet resultSet;
    private static java.sql.Connection connection;
    private static final Logger LOGGER = Logger.getLogger( addR.class.getName() );
    
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)  throws ServletException,IOException  
    {  
        try {
            String room_number = req.getParameter("room_number");
            String hospital_id = req.getParameter("hospital_id");
            connection = DBConnection.getDBConnection().getConnection();
            String SQL12 = "select room_number from room where room_number=? and hospital_id=?";
            preparedStmt = connection.prepareStatement(SQL12);
            preparedStmt.setString (1, room_number);
            preparedStmt.setString (2, hospital_id);
            // execute the preparedstatement
            resultSet = preparedStmt.executeQuery();
            if(resultSet.next()){
                res.sendRedirect("./addRoom.jsp?status=This Room Number Already Available!&room_number="+room_number);
                return;
            }
            
            String SQL1 = "insert into room ( hospital_id, room_number) VALUES (?,?)";
            preparedStmt = connection.prepareStatement(SQL1);
            preparedStmt.setString (1, hospital_id);
            preparedStmt.setString (2, room_number);
            // execute the preparedstatement
            preparedStmt.execute();
            res.sendRedirect("./home?status=Room successfully added!");
        }
        catch (SQLException | PropertyVetoException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            res.sendRedirect("./error.jsp?error=Error in adding room!\n+"+ex.toString()+"");
        }
        finally 
        {
            try {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(preparedStmt);
            DbUtils.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(register.class.getName()).log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }
}  

