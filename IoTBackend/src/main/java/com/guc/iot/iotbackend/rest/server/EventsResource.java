/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guc.iot.iotbackend.rest.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    private List<Event> events;
    
    public EventsResource() {
        events = new ArrayList<Event>();
        Event e1;
        e1 = new Event(1L, "SomeOrgName","EventOne", "Description For EventOne", "GUC", new Date(), new Date(), false, "someImageURL");
        Event e2 = new Event(2L, "SomeOrgName" ,"EventTwo", "Description For EventTwo", "GUC", new Date(), new Date(), false, "someImageURL");
        Event e3 = new Event(3L, "SomeOrgName", "EventThree", "Description For EventThree", "GUC", new Date(), new Date(), false, "someImageURL");
        Event e4 = new Event(4L, "SomeOrgName", "EventFour", "Description For EventFour", "GUC", new Date(), new Date(), false, "someImageURL");
        Event e5 = new Event(5L, "SomeOrgName", "EventFive", "Description For EventFive", "GUC", new Date(), new Date(), false, "someImageURL");
        
        events.add(e1);
        events.add(e2);
        events.add(e3);
        events.add(e4);
        events.add(e5);
           
    }
    
    @GET
    @Produces("application/json")
    public JsonArray getEventList() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(Event event: events) {
            jab.add(Json.createObjectBuilder()
                    .add("id", event.getId())
                    .add("organization", event.getOrganization())
                    .add("name", event.getName())
                    .add("description", event.getDescription())
                    .add("location", event.getLocation())
                    .add("creationDate", event.getCreationDate().toString())
                    .add("expirationDate", event.getExpirationDate().toString())
                    .add("hasBooth", event.getHasBooth())
                    .add("imageURL", event.getImgURL())
            );
        }
        
        return jab.build();
    }
}
