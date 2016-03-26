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

@Path("events")
@RequestScoped
public class EventsResource {
//    TODO: Fetch Data from a DB instead
    private List<String> eventNames;
    
    public EventsResource() {
        eventNames = new ArrayList<String>();
        eventNames.add("AYB Recruitment One");
        eventNames.add("AYB Recruitment Two");
        eventNames.add("AYB Recruitment Three");
        eventNames.add("AYB Recruitment Four");
        
    }
    
    @GET
    @Produces("application/json")
    public JsonArray getEventList() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(String name: eventNames) {
            jab.add(name);
        }
        
        return jab.build();
    }
}
