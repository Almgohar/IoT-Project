/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guc.iot.iotbackend.rest.server;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author eslam
 */
@Path("notifications")
@RequestScoped
public class NotificationsResource {
    
    private List<Notification> notifications;
    
    public NotificationsResource() {
        notifications = new ArrayList<Notification>();
        Notification n1 = new Notification(1L, "Hopa de awl notification");
        Notification n2 = new Notification(2L, "Hopa de l tania, TANIA!");
        notifications.add(n1);
        notifications.add(n2);
    }
    
    
    @GET
    @Produces("application/json")
    public JsonArray getNotificationList() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(Notification note: notifications) {
            jab.add(Json.createObjectBuilder()
                    .add("id", note.getId())
                    .add("body", note.getBody())
                   
            );
        }
        return jab.build();
    }
}
