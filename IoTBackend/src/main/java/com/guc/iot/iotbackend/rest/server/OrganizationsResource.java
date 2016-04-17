/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guc.iot.iotbackend.rest.server;

import com.guc.iot.iotbackend.rest.server.entity.Organization;
import com.guc.iot.iotbackend.rest.server.entity.Event;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author eslam
 */
@Path("organizations")
@RequestScoped
public class OrganizationsResource {
    
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    
    private List<Organization> organizations;
    List<Event> events;
    
    
    public OrganizationsResource(){}
    
//    public OrganizationsResource() {
//        organizations = new ArrayList<Organization>();
//        events = new ArrayList<Event>();
////        Event e2 = new Event(2L, "SomeOrgName" ,"EventTwo", "Description For EventTwo", "GUC", new Date(), new Date(), false, "someImageURL");
////        Event e3 = new Event(2L, "SomeOrgName" ,"EventTwo", "Description For EventTwo", "GUC", new Date(), new Date(), false, "someImageURL");
////        events.add(e2);
////        events.add(e3);
//        Organization o1 = new Organization(1L, "AYB GUC", "ayb@guc.com", "somePassword", "someImgUrl", "someDescription",events);
//        Organization o2 = new Organization(2L, "AYB GUC2", "ayb2@guc.com", "somePassword2", "someImgUrl2", "someDescriptio2n",events);
//        organizations.add(o1);
//        organizations.add(o2);
//    }
    
    
    @GET
    @Produces("application/json")
    public JsonArray getOrganizationList() throws Exception {
        
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            // TODO: put the username and password in a seperate file ignored on git
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/iotproject?"
                            + "user=root&password=toor");
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            String select_query = "select * from iotproject.ORGANIZTION";
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery(select_query);
            
            organizations = new ArrayList<Organization>();
            
            while(resultSet.next()) {
                Organization tmp = new Organization();
                tmp.setId(resultSet.getLong("id"));
                
                if (resultSet.getString("name") != null) {
                    tmp.setName(resultSet.getString("name"));
                } else {
                    tmp.setName("empty-name");
                }
                if (resultSet.getString("description") != null) {
                    tmp.setDescription(resultSet.getString("description"));
                } else {
                    tmp.setDescription("empty descriotion");
                }
                
                if (resultSet.getString("imgURL") != null) {
                    tmp.setImgURL(resultSet.getString("imgURL"));
                } else {
                    tmp.setImgURL("emptyImgUrl");
                }
                
                organizations.add(tmp);
                
            }
            
            JsonArrayBuilder jab = Json.createArrayBuilder();
            for(Organization organization : organizations) {
                jab.add(Json.createObjectBuilder()
                        .add("id", organization.getId())
                        .add("name", organization.getName())
                        .add("description", organization.getDescription())
                        .add("imgURL", organization.getImgURL())
                );
            }
            
            return jab.build();
            
        }
        catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
    
    /**
     * 
     * 
        {
                "orgName": "ayb",
                "orgEmail": "ayb@gmail.com",
                "password": "elPassword"
        }
     * 
     * @param jsonEvent
     * @return
     * @throws Exception 
     */
    @POST
    @Consumes("application/json")
    public Response createOrganization(JsonObject jsonEvent) throws Exception {

        String orgName = (jsonEvent.getString("orgName"));
//        int gradYear =  (int) (jsonEvent.getInt("gradYear"));
        String orgEmail = (jsonEvent.getString("orgEmail"));
        String password = jsonEvent.getString("password");
        
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            // TODO: put the username and password in a seperate file ignored on git
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/iotproject?"
                            + "user=root&password=toor");
            // Statements allow to issue SQL queries to the database
            
            // PreparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement("insert into  iotproject.ORGANIZTION(name,email,password) values (?,?,?)");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, jsonEvent.getString("orgName"));
            preparedStatement.setString(2, jsonEvent.getString("orgEmail"));
            preparedStatement.setString(3, jsonEvent.getString("password"));
            
            preparedStatement.executeUpdate();

            return Response.status(201).build();

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
    
    
    /**
     * helper method to close all DB connections
     */
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
    
}
