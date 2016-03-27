/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guc.iot.iotbackend.rest.server;

import com.guc.iot.iotbackend.rest.server.entity.Event;
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

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private List<Event> events;

    //empty constructor
    public EventsResource() {
    }

    /**
     * fetch all events using a GET request to the events endpoint
     *
     * @return
     * @throws Exception
     */
    @GET
    @Produces("application/json")
    public JsonArray getEventList() throws Exception {
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
            String select_query = "select * from iotproject.EVENT";
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery(select_query);

            events = new ArrayList<Event>();

            while (resultSet.next()) {
                Event tmp = new Event();
                tmp.setId(resultSet.getLong("id"));
                if (resultSet.getString("name") != null) {
                    tmp.setName(resultSet.getString("name"));
                } else {
                    tmp.setName("null");
                }
                if (resultSet.getString("organization") != null) {
                    tmp.setOrganization(resultSet.getLong("organization"));
                }

                if (resultSet.getString("description") != null) {
                    tmp.setDescription(resultSet.getString("description"));
                } else {
                    tmp.setDescription("null");
                }
                if (resultSet.getString("location") != null) {
                    tmp.setLocation(resultSet.getString("location"));
                } else {
                    tmp.setLocation("null");
                }
                if (resultSet.getDate("creationDate") != null) {
                    tmp.setCreationDate(resultSet.getDate("creationDate"));
                }
                if (resultSet.getDate("expirationDate") != null) {
                    tmp.setExpirationDate(resultSet.getDate("expirationDate"));
                }
                if (resultSet.getString("hasBooth") != null) {
                    tmp.setHasBooth(resultSet.getBoolean("hasBooth"));
                } else {
                    tmp.setHasBooth(Boolean.FALSE);
                }
                if (resultSet.getString("imgURL") != null) {
                    tmp.setImgURL(resultSet.getString("imgURL"));
                } else {
                    tmp.setImgURL("empty");
                }
                events.add(tmp);
            }

            JsonArrayBuilder jab = Json.createArrayBuilder();
            for (Event event : events) {
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
