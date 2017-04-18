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
    
    public static String getRooms(String hospital_id){
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        String rooms = "";
        try 
        {

        connection = DBConnection.getDBConnection().getConnection();
        //String SQL = "select room_number,room_id from appointmentz.room where hospital_id = ?";
        String SQL = "select room_number from appointmentz.room where hospital_id = ?";
        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, hospital_id);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next( )){
            String room_number = resultSet.getString("room_number");
            //String room_id = resultSet.getString("room_id");
            rooms+="<option value=\""+room_number+"\">"+room_number+"</option>";
        }

        } catch (SQLException |IOException | PropertyVetoException e) {
            //throw new IllegalStateException
            rooms = "Error";       
        }
        return rooms;
    }
    public static String getRooms(String hospital_id,String room_number_default){
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        String rooms = "";
        try 
        {

        connection = DBConnection.getDBConnection().getConnection();
        //String SQL = "select room_number,room_id from appointmentz.room where hospital_id = ?";
        String SQL = "select room_number from appointmentzv1.room where hospital_id = ?";
        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, hospital_id);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next( )){
            String room_number = resultSet.getString("room_number");
            //String room_id = resultSet.getString("room_id");
            if(room_number_default.equals(room_number))
                rooms+="<option selected=\"selected\" value=\""+room_number+"\">"+room_number+"</option>";
            else    
                rooms+="<option value=\""+room_number+"\">"+room_number+"</option>";
        }

        } catch (SQLException |IOException | PropertyVetoException e) {
            //throw new IllegalStateException
            rooms = "Error";       
        }
        return rooms;
    }
    public static String equipmentsGetRooms(String hospital_id){
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        String rooms = "";
        try 
        {

        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "select * from appointmentzv1.room where hospital_id = ?";
        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, hospital_id);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next( )){
            //String room_id = resultSet.getString("room_id");
            String room_number = resultSet.getString("room_number");
            rooms+="<tr><form action='./deleteRoom' method='post'><td>"+room_number+"</td><input type='hidden' name='room_number' value='"+room_number+"'>";
            rooms+="<td><button type=\"submit\" onClick=\"return confirm('Do you wish to delete the Room. Ref: Room number = "+room_number+" ');\" style='color:red'>delete</button></td>";
            rooms+="</form></tr>";
        }
        } catch (SQLException | IOException | PropertyVetoException e) {
        //throw new IllegalStateException
         rooms = "Error";
        }
        return rooms;
    }
    
    public static String equipmentsGetRPI(String hospital_id){
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        String rpi = "";
        try 
        {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "select * from appointmentzv1.rpi natural join appointmentzv1.room where hospital_id = ?";
        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, hospital_id);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next( )){
            String auth = resultSet.getString("auth");
            String serial = resultSet.getString("serial");
            //String room_id = resultSet.getString("room_id");
            String room_number = resultSet.getString("room_number");
            rpi += "<tr><form action='./deleteRPI' method='post'><td>"+auth+"</td><input type='hidden' name='auth' value='"+auth+"'>";
            rpi += "<td>"+serial+"</td><input type='hidden' name='serial' value='"+serial+"'><td>"+room_number+"</td>";
            rpi += "<input type='hidden' name='room_number' value='"+room_number+"'>";
            rpi += "<td><button type=\"submit\" onClick=\"return confirm('Do you wish to delete the RPI. Ref: Serial = "+serial+", rel: Room: "+room_number+" ');\" style='color:red'>delete</button></td>";
            rpi +="</form>";
            rpi +="<form action='./editRPI' method='post'><input type='hidden' name='room_number' value='"+room_number+"'><input type='hidden' name='serial' value='"+serial+"'><input type='hidden' name='auth' value='"+auth+"'>";
            rpi += "<td><button type=\"submit\" style='color:red'>edit</button></td>";
            rpi += "</form></tr>";
        }
        } catch (SQLException | IOException | PropertyVetoException e) {
        //throw new IllegalStateException
            rpi = "Error";
            
        }
        return rpi;
    }
    
    public static String checkRoomId(String room_number, String hospital_id){
        
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        String check = "default";
        try 
        {
        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "select room_number from room where hospital_id = ? and room_number = ?";
        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, hospital_id);
        preparedStatement.setString(2, room_number);
        resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            check = "Available";
        }else{
            check = "Unavailable";
        }

        } catch (SQLException |IOException | PropertyVetoException e) {
            check = "Error";
        }
        return check;
        
    }
    
    public static String checkHospitalId(String hospital_id){
        
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Connection connection;
        String check = "default";
        try 
        {

        connection = DBConnection.getDBConnection().getConnection();
        String SQL = "select hospital_id from hospital where hospital_id = ?";
        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, hospital_id);
        resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            check = "Available";
        }else{
            check = "Unavailable";
        }

        } catch (SQLException |IOException | PropertyVetoException e) {
            check = "Error";
        }
        return check;
        
    }
}
