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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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

//    private EntityManager em;
    //empty constructor
    public EventsResource() {
    }

    
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public JsonArray getEvent(final @PathParam("id") String id) throws Exception {
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
            String select_query = "select * from iotproject.EVENT where id=" + id + ";";
            
            
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
                if(resultSet.getString("major") != null) {
                    tmp.setMajor(resultSet.getString("major"));
                }
                else {
                    tmp.setMajor("empty");
                }
                if(resultSet.getString("minor") != null) {
                    tmp.setMinor(resultSet.getString("minor"));
                } else {
                    tmp.setMinor("empty");
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
                        .add("major", event.getMajor())
                        .add("minor", event.getMinor())
                        
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
                if(resultSet.getString("major") != null) {
                    tmp.setMajor(resultSet.getString("major"));
                }
                else {
                    tmp.setMajor("empty");
                }
                if(resultSet.getString("minor") != null) {
                    tmp.setMinor(resultSet.getString("minor"));
                } else {
                    tmp.setMinor("empty");
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
                        .add("major", event.getMajor())
                        .add("minor", event.getMinor())
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
     * 
     * {
                "organization": 2,
                "name": "AYB is recruiting",
                "description": "Event description goes here",
                "location": "pronto",
                "creationDate": "2016-10-11",
                "expirationDate":"2016-11-10",
                "hasBooth": true,
                "imgURL": "some url goes here"
}
     * 
     * @param jsonEvent
     * @return
     * @throws Exception 
     */
    @POST
    @Consumes("application/json")
    public Response createEvent(JsonObject jsonEvent) throws Exception {
        DateFormat formatter = new SimpleDateFormat("yyyy/mm/dd");
        Event event = new Event();
        String eventName = jsonEvent.getString("name");
        event.setName(eventName);
        Long eventOrganization = (Long.parseLong(jsonEvent.getInt("organization") + ""));
        event.setOrganization(eventOrganization);
        String eventCreationDate = "not-defined";
        String eventExpirationDate = "not-defined";
        String eventDescription = "not-defined";
        Boolean eventHasBooth = Boolean.FALSE;
        String eventImgURL = "https://pbs.twimg.com/profile_images/1290301369/AYB-GUC_Logo_2.png";
        String eventLocation = "not-defined";
        try {
            eventCreationDate = jsonEvent.getString("creationDate");
        } catch (Exception e) {
        }
        try {
            event.setCreationDate((Date) formatter.parse(eventCreationDate));
        } catch (Exception e) {
        }
        eventExpirationDate = jsonEvent.getString("expirationDate");
        try {
            event.setExpirationDate((Date) formatter.parse(eventExpirationDate));
        } catch (Exception e) {
        }
        try {
            eventDescription = jsonEvent.getString("description");
        } catch (Exception e) {
        }
        try {
            event.setDescription(eventCreationDate);
        } catch (Exception e) {
        }
        try {
            eventHasBooth = jsonEvent.getBoolean("hasBooth");
        } catch (Exception e) {
        }
        try {
            event.setHasBooth(eventHasBooth);
        } catch (Exception e) {
        }
        try {
            eventImgURL = jsonEvent.getString("imgURL");
        } catch (Exception e) {
        }
        try {
            event.setImgURL(eventImgURL);
        } catch (Exception e) {
        }
        try {
            eventLocation = jsonEvent.getString("location");
        } catch (Exception e) {
        }
        try {
            event.setLocation(eventLocation);

        } catch (Exception e) {
        }

//        em.persist(event);
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
            // PreparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement("insert into  iotproject.EVENT(organization,name,description,location,hasBooth,creationDate, expirationDate,imgURL) values (?,?, ?, ?, ?, ?,?,?)");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, eventOrganization.toString());
            preparedStatement.setString(2, eventName);
            preparedStatement.setString(3, eventDescription);
            preparedStatement.setString(4, eventLocation);
//      preparedStatement.setDate(5, new java.sql.Date(2009, 12, 11));
//      preparedStatement.setDate(6, new java.sql.Date(2009, 12, 11));

            int flag = eventHasBooth ? 1 : 0;
            preparedStatement.setString(5, flag + "");
            preparedStatement.setString(6, eventCreationDate);
            preparedStatement.setString(7, eventExpirationDate);
            preparedStatement.setString(8, eventImgURL);
            preparedStatement.executeUpdate();

            return Response.status(201).build();

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
        //return a 201 response
    }
    
    @GET
    @Path("/search/{major}/{minor}")
    @Produces("application/json")
    public JsonArray getMajorMinor(final @PathParam("major") String major, final @PathParam("minor") String minor) throws Exception {
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
            String select_query = "SELECt * FROM  iotproject.EVENT WHERE major=" + major +" AND minor=" + minor+";";
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
                if(resultSet.getString("major") != null) {
                    tmp.setMajor(resultSet.getString("major"));
                }
                else {
                    tmp.setMajor("empty");
                }
                if(resultSet.getString("minor") != null) {
                    tmp.setMinor(resultSet.getString("minor"));
                } else {
                    tmp.setMinor("empty");
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
                        .add("major", event.getMajor())
                        .add("minor", event.getMinor())
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
