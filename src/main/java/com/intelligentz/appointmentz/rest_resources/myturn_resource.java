package com.intelligentz.appointmentz.rest_resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.appointmentz.constants.AuthorizationTypes;
import com.intelligentz.appointmentz.constants.ContentTypes;
import com.intelligentz.appointmentz.constants.URLs;
import com.intelligentz.appointmentz.controllers.DeviceController;
import com.intelligentz.appointmentz.controllers.SMSSender;
import com.intelligentz.appointmentz.exception.IdeabizException;
import com.intelligentz.appointmentz.handler.IdeaBizAPIHandler;
import com.intelligentz.appointmentz.handler.SMSHandler;
import com.intelligentz.appointmentz.model.Device;
import com.intelligentz.appointmentz.model.RequestMethod;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Lakshan on 2017-04-08.
 */
@Path("/")
public class myturn_resource {
    public static Logger logger = Logger.getLogger(myturn_resource.class);

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    @Path("/")
    public void get(String request) {
        System.out.println(request);
        JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
        JsonObject mergeData = jsonObject.get("merge_data").getAsJsonObject();
        int patient_no = Integer.parseInt(mergeData.get("patent_no").getAsString());
        String device_serial = jsonObject.get("mac").getAsString();

        Device device = DeviceController.getDevice(device_serial);
        if (device != null) {
            int last_number = device.getLast_number();
            String room_id = device.getRoom_id();
            int hospital_id = device.getHospital_id();

            if (last_number < patient_no) {
                for (int i = last_number + 1; i <= patient_no; i++) {
                    sendToDocLK(i, room_id, hospital_id);
                }
            } else sendToDocLK(patient_no, room_id, hospital_id);

            DeviceController.updateDeviceLastNumber(device_serial, patient_no);

            new SMSSender().sendSMStoPatients(patient_no);
        }
    }

    private void sendToDocLK(int patient_no, String room_id, int hospital_id){
        String URL = URLs.DOCLK_RUNNING_NUMBER_URL;
        JsonObject bodyJson = new JsonObject();
        bodyJson.addProperty("room",room_id);
        bodyJson.addProperty("hospital",hospital_id);
        bodyJson.addProperty("number",patient_no);
        String reqBody  = new Gson().toJson(bodyJson);
        try {
            String response = new IdeaBizAPIHandler().sendAPICall(URL, RequestMethod.POST, reqBody,"", ContentTypes.TYPE_JSON,ContentTypes.TYPE_JSON, AuthorizationTypes.TYPE_BEARER);
            System.out.println("DocLK response======= \n" + response);
        } catch (IdeabizException e) {
            logger.error(e.getMessage());
        }
    }

}
