/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guc.iot.iotbackend.rest.server;

import com.guc.iot.iotbackend.rest.server.entity.Notification;
import com.guc.iot.iotbackend.rest.server.entity.Organization;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
@Path("notifications")
@RequestScoped
public class NotificationsResource {
    
    private List<Notification> notifications;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public NotificationsResource() {
//        notifications = new ArrayList<Notification>();
//        Notification n1 = new Notification(1L, "Hopa de awl notification");
//        Notification n2 = new Notification(2L, "Hopa de l tania, TANIA!");
//        notifications.add(n1);
//        notifications.add(n2);
    }
    
    
    
    @GET
    @Produces("application/json")
    public JsonArray getNotificationList() throws Exception {
//        JsonArrayBuilder jab = Json.createArrayBuilder();
//        for(Notification note: notifications) {
//            jab.add(Json.createObjectBuilder()
//                    .add("id", note.getId())
//                    .add("body", note.getBody())
//                   
//            );
//        }
//        return jab.build();
//    }

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
            String select_query = "select * from iotproject.NOTIFICATION";
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery(select_query);
            
            notifications = new ArrayList<Notification>();
            
            while(resultSet.next()) {
                Notification tmp = new Notification();
                tmp.setId(resultSet.getLong("id"));
                
                if (resultSet.getString("body") != null) {
                    tmp.setBody(resultSet.getString("body"));
                } else {
                    tmp.setBody("empty-body");
                }
                
                
                notifications.add(tmp);
                
            }
            
            JsonArrayBuilder jab = Json.createArrayBuilder();
            for(Notification notification : notifications) {
                jab.add(Json.createObjectBuilder()
                        .add("id", notification.getId())
                        .add("name", notification.getBody())
                        
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
    
    @POST
    @Consumes("application/json")
    public Response createNotification(JsonObject jsonEvent) throws Exception {
        Notification notification = new Notification();
        String notificationBody = jsonEvent.getString("body");
        notification.setBody(notificationBody);
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
            
            // PreparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement("insert into  iotproject.NOTIFICATION(body) values (?)");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, notificationBody.toString());
            
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
