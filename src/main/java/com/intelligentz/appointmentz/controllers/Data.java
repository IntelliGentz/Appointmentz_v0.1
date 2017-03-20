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
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ndine
 */
public class Data {
    
    public static String getRooms(HttpServletResponse res, String hospital_id){
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        String rooms = "";
        try 
        {

        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "select room_number,room_id from appointmentz.room where hospital_id = ?";
        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, hospital_id);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next( )){
            String room_number = resultSet.getString("room_number");
            String room_id = resultSet.getString("room_id");
            rooms+="<option value=\""+room_id+"\">"+room_number+"</option>";
        }

        } catch (SQLException |IOException | PropertyVetoException e) {
            //throw new IllegalStateException
            res.setHeader("Location", "error.jsp?error=MYSQL connection failed!");        
        }
        return rooms;
    }
    public static String equipmentsGetRooms(HttpServletResponse res, String hospital_id){
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        String rooms = "";
        try 
        {

        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "select * from appointmentz.room where hospital_id = ?";
        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, hospital_id);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next( )){
            String room_id = resultSet.getString("room_id");
            String room_number = resultSet.getString("room_number");
            rooms+="<tr><form action='./deleteRoom' method='post'><td>"+room_number+"</td><input type='hidden' name='room_number' value='"+room_number+"'><td>"+room_id+"</td><input type='hidden' name='room_id' value='"+room_id+"'><td>";
            rooms+="<button type=\"submit\" onClick=\"return confirm('Do you wish to delete the Room. Ref: RoomId = "+room_id+", Room number = "+room_number+" ');\" style='color:red'>delete</button></td>";
            rooms+="</form></tr>";
        }
        } catch (SQLException | IOException | PropertyVetoException e) {
        //throw new IllegalStateException
        res.setHeader("Location", "error.jsp?error=error occured try again!"); 
        }
        return rooms;
    }
    
    public static String equipmentsGetRPI(HttpServletResponse res, String hospital_id){
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        String rpi = "";
        try 
        {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "select * from appointmentz.rpi natural join appointmentz.room where hospital_id = ?";
        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, hospital_id);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next( )){
            String auth = resultSet.getString("auth");
            String serial = resultSet.getString("serial");
            String room_id = resultSet.getString("room_id");
            String room_number = resultSet.getString("room_number");
            rpi += "<tr><form action='./deleteRPI' method='post'><td>"+auth+"</td><input type='hidden' name='auth' value='"+auth+"'>";
            rpi += "<td>"+serial+"</td><input type='hidden' name='serial' value='"+serial+"'><td>"+room_number+"</td>";
            rpi += "<input type='hidden' name='room_number' value='"+room_number+"'><td>"+room_id+"</td><input type='hidden' name='room_id' value='"+room_id+"'>";
            rpi += "<td><button type=\"submit\" onClick=\"return confirm('Do you wish to delete the RPI. Ref: Serial = "+serial+", rel: Room: "+room_number+" ');\" style='color:red'>delete</button></td>";
            rpi +="</form>";
            rpi +="<form action='./editRPI' method='post'><input type='hidden' name='room_number' value='"+room_number+"'><input type='hidden' name='room_id' value='"+room_id+"'>";
            rpi += "<td><button type=\"submit\" style='color:red'>edit</button></td>";
            rpi += "</form></tr>";
        }
        } catch (SQLException | IOException | PropertyVetoException e) {
        //throw new IllegalStateException
            res.setHeader("Location", "error.jsp?error=MYSQL connection failed!"); 
        }
        return rpi;
    }
    
    public static String checkHospitalId(String hospital_id){
        
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        String check = "Unavailable";
        try 
        {

        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "select hospital_id from hospital where hospital_id = ?";
        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, hospital_id);
        resultSet = preparedStatement.executeQuery();

        if(resultSet.next( )){
            check = "Available";
        }

        } catch (SQLException |IOException | PropertyVetoException e) {
            check = "Error";
        }
        return check;
        
    }
}
