package com.intelligentz.appointmentz.model;

/**
 * Created by Lakshan on 2017-04-19.
 */
public class Device {
    private String room_id;
    private int last_number;
    private int hospital_id;

    public Device() {
    }

    public Device(String room_id, int last_number) {
        this.room_id = room_id;
        this.last_number = last_number;
    }

    public Device(String room_id, int last_number, int hospital_id) {
        this.room_id = room_id;
        this.last_number = last_number;
        this.hospital_id = hospital_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public int getLast_number() {
        return last_number;
    }

    public void setLast_number(int last_number) {
        this.last_number = last_number;
    }

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }
}
