package com.intelligentz.appointmentz.controllers;

import com.google.gson.JsonArray;
import com.intelligentz.appointmentz.constants.DefaultMessages;
import com.intelligentz.appointmentz.constants.IdeaBizConstants;
import com.intelligentz.appointmentz.exception.IdeabizException;
import com.intelligentz.appointmentz.handler.SMSHandler;
import com.intelligentz.appointmentz.handler.SubscriptionHandler;

/**
 * Created by Lakshan on 2017-04-08.
 */
public class SMSSender {
    public void sendSMStoPatients(int current_no, String room_id, String hospital_name) {
        String nextMessage = String.format(DefaultMessages.NEXT_MESSAGE, "A. B. C. Perera", room_id, hospital_name, String.valueOf(current_no));
        String fifthMessage = String.format(DefaultMessages.FIFTH_MESSAGE, "A. B. C. Perera", room_id, hospital_name, String.valueOf(current_no));
        sendSMStoNext(current_no+1, nextMessage);
        sendSMStoNext(current_no+5, fifthMessage);
    }

    private void sendSMStoNext(int next_no, String message){
        String mobileNo = SMSHandler.patients.get(String.valueOf(next_no));
        if (mobileNo != null) {
            mobileNo = IdeaBizConstants.MSISDN_PREFIX+mobileNo;
            JsonArray numbers = new JsonArray();
            numbers.add(mobileNo);
            try {
                if (new SubscriptionHandler().suscribe(mobileNo)) {
                    new SMSHandler().sendSMS(numbers, message);
                } else {
                    throw new IdeabizException("User Could Not be Subscribed");
                }
            } catch (IdeabizException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
