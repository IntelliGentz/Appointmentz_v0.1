/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelligentz.appointmentz.controllers;

import com.intelligentz.appointmentz.database.DBConnection;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.codec.binary.Hex;
/**
 *
 * @author T430
 */
public class authenticate extends HttpServlet{
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static Connection connection;
    private static final Logger LOGGER = Logger.getLogger( authenticate.class.getName() );
    
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)  throws ServletException,IOException  
    {  
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(authenticate.class.getName()).log(Level.SEVERE, "Error:{0}", ex.toString());
            }
            messageDigest.reset();
            messageDigest.update(req.getParameter("form-password").getBytes(Charset.forName("UTF8")));
            final byte[] resultByte = messageDigest.digest();
            //final String password = new String(Hex.encodeHex(resultByte));
            
            String userName = req.getParameter("form-username");
            String password = req.getParameter("form-password");
            authenticate(userName,password,req,res);
            
}
    
private void authenticate(String userName, String password, HttpServletRequest req,HttpServletResponse res){
    boolean status = false;
    try 
    {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL1 = "select * from appointmentz.hospital WHERE hospital_id = ? and password = ?";

        preparedStatement = connection.prepareStatement(SQL1);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            String db_username = resultSet.getString("hospital_id");
            String db_hospital_name = resultSet.getString("hospital_name");
            HttpSession session = req.getSession();
            session.setAttribute( "hospital_id", db_username );
            session.setAttribute( "hospital_name", db_hospital_name);

            res.sendRedirect("./home");
        }
        else{
            res.sendRedirect("./index.jsp?auth=failed");
        }

    } 
    catch (SQLException | IOException | PropertyVetoException ex) 
    {
        LOGGER.log(Level.SEVERE, ex.toString(), ex);
    }
    finally 
    {
        try {
        DbUtils.closeQuietly(resultSet);
        DbUtils.closeQuietly(preparedStatement);
        DbUtils.close(connection);
        } catch (SQLException ex) {
            Logger.getLogger(authenticate.class.getName()).log(Level.SEVERE, ex.toString(), ex);
        }
    }
}
    
}


