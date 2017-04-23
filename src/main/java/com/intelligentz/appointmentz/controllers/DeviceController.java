package com.intelligentz.appointmentz.controllers;

import com.intelligentz.appointmentz.database.DBConnection;
import com.intelligentz.appointmentz.model.Device;
import org.apache.commons.dbutils.DbUtils;

import javax.servlet.http.HttpSession;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Lakshan on 2017-04-19.
 */
public class DeviceController {
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static Connection connection;
    private static final Logger LOGGER = Logger.getLogger( DeviceController.class.getName() );

    public static Device getDevice(String device_serial){
        Device device = null;
        try {
            connection = DBConnection.getDBConnection().getConnection();
            String SQL1 = "select r.room_number, p.last_number, h.id from  rpi AS p, hospital AS h, room AS r WHERE p.room_id = r.room_id AND r.hospital_id = h.hospital_id AND p.serial = ?";

            preparedStatement = connection.prepareStatement(SQL1);
            preparedStatement.setString(1, device_serial);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String room_id = resultSet.getString("room_number");
                int last_number = resultSet.getInt("last_number");
                int hospital_id = resultSet.getInt("id");
                device = new Device(room_id,last_number, hospital_id);
            }
        } catch (SQLException | IOException | PropertyVetoException ex)
        {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
        finally
        {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(preparedStatement);
            DbUtils.closeQuietly(connection);
        }
        return device;
    }

    public static void updateDeviceLastNumber(String serial, int last_number){
        try {
            connection = DBConnection.getDBConnection().getConnection();
            String SQL1 = "UPDATE rpi SET last_number = ? WHERE serial = ?";

            preparedStatement = connection.prepareStatement(SQL1);
            preparedStatement.setInt(1, last_number);
            preparedStatement.setString(2, serial);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | PropertyVetoException ex)
        {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
        finally
        {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(preparedStatement);
            DbUtils.closeQuietly(connection);
        }
    }
}
