/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guc.iot.iotbackend.rest.server;

import com.guc.iot.iotbackend.rest.server.entity.Organization;
import com.guc.iot.iotbackend.rest.server.entity.Event;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author eslam
 */
@Path("organizations")
@RequestScoped
public class OrganizationsResource {
    private List<Organization> organizations;
    List<Event> events;
    public OrganizationsResource() {
        organizations = new ArrayList<Organization>();
        events = new ArrayList<Event>();
//        Event e2 = new Event(2L, "SomeOrgName" ,"EventTwo", "Description For EventTwo", "GUC", new Date(), new Date(), false, "someImageURL");
//        Event e3 = new Event(2L, "SomeOrgName" ,"EventTwo", "Description For EventTwo", "GUC", new Date(), new Date(), false, "someImageURL");
//        events.add(e2);
//        events.add(e3);
        Organization o1 = new Organization(1L, "AYB GUC", "ayb@guc.com", "somePassword", "someImgUrl", "someDescription",events);
        Organization o2 = new Organization(2L, "AYB GUC2", "ayb2@guc.com", "somePassword2", "someImgUrl2", "someDescriptio2n",events);
        organizations.add(o1);
        organizations.add(o2);
    }
    
    
    @GET
    @Produces("application/json")
    public JsonArray getOrganizationList() {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(Organization organization: organizations) {
            JsonObjectBuilder orgJson = Json.createObjectBuilder()
                    .add("id", organization.getId())
                    .add("name", organization.getName())
                    .add("description", organization.getDescription())
                    .add("imageURL", organization.getImgURL());
        
        JsonArrayBuilder eventsBuilder = Json.createArrayBuilder();
            for(Event event : events) {
                JsonObjectBuilder evJson = Json.createObjectBuilder()
                        .add("name", event.getName())
                        .add("location", event.getLocation())
                        .add("imageURL", event.getImgURL());
                
                eventsBuilder.add(evJson);
            }
        orgJson.add("events", eventsBuilder);
        jab.add(orgJson);
            
          
        }
        
        return jab.build();
    }
}
