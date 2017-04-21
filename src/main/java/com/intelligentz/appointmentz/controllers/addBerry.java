/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelligentz.appointmentz.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.appointmentz.constants.AuthorizationTypes;
import com.intelligentz.appointmentz.constants.ContentTypes;
import com.intelligentz.appointmentz.constants.URLs;
import com.intelligentz.appointmentz.database.DBConnection;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.io.IOException;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intelligentz.appointmentz.exception.IdeabizException;
import com.intelligentz.appointmentz.handler.IdeaBizAPIHandler;
import com.intelligentz.appointmentz.model.RequestMethod;
import com.intelligentz.appointmentz.rest_resources.myturn_resource;
import org.apache.commons.dbutils.DbUtils;


/**
 *
 * @author ndine
 */
public class addBerry extends HttpServlet{  
    private static PreparedStatement preparedStmt;
    //private static ResultSet resultSet;
    private static Connection connection;
    private static final Logger LOGGER = Logger.getLogger( addBerry.class.getName() );
    public static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(addBerry.class);
    
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)  throws ServletException,IOException  
    {  
        try {

            String room_number = req.getParameter("room_number");
            String hospital_id = req.getParameter("hospital_id");
            String auth = req.getParameter("auth");
            String serial = req.getParameter("serial");

            JsonObject jsonRequest = new JsonObject();
            jsonRequest.addProperty("serial",serial);
            jsonRequest.addProperty("auth_code",auth);
            jsonRequest.addProperty("url", URLs.DEVICE_CALL_BACK_URL);
            jsonRequest.addProperty("event", "PRESSED");
            String reqBody  = new Gson().toJson(jsonRequest);

            String response = new IdeaBizAPIHandler().sendAPICall(URLs.DEVICE_REG_URL, RequestMethod.POST, reqBody,"", ContentTypes.TYPE_JSON,ContentTypes.TYPE_TEXT, AuthorizationTypes.TYPE_BEARER);
            logger.info("Device Add====== Request: "+reqBody+"====== Response: " + response);
            if (response.contains("Invalid")) {
                JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
                String errorMessage = jsonObject.get("desc").getAsString();
                res.sendRedirect("./error.jsp?error=Error in adding device!\n+"+errorMessage+"");
            } else {
                connection = DBConnection.getDBConnection().getConnection();
                String SQL1 = "insert into rpi ( room_number, hospital_id, auth, serial) VALUES (?,?,?,?)";
                preparedStmt = connection.prepareStatement(SQL1);
                preparedStmt.setString(1, room_number);
                preparedStmt.setString(2, hospital_id);
                preparedStmt.setString(3, auth);
                preparedStmt.setString(4, serial);
                // execute the preparedstatement
                preparedStmt.execute();


                res.sendRedirect("./home");
            }
        }
        catch (SQLException | PropertyVetoException | JsonIOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            res.sendRedirect("./error.jsp?error=Error in adding device!\n+"+ex.toString()+"");
        } catch (IdeabizException e) {
            e.printStackTrace();
        } finally
        {
            try {
            //DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(preparedStmt);
            DbUtils.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(register.class.getName()).log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }
}  
