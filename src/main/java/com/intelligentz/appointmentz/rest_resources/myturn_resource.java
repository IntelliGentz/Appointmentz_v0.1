package com.intelligentz.appointmentz.rest_resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.appointmentz.controllers.SMSSender;

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @POST
    @Path("/")
    public void get(String request) {
        System.out.println(request);
        JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
        JsonObject mergeData = jsonObject.get("merge_data").getAsJsonObject();
        int patient_no = Integer.parseInt(mergeData.get("patent_no").getAsString());
        new SMSSender().sendSMStoPatients(patient_no);
    }

}
