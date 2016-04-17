/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guc.iot.iotbackend.rest.server;

import com.guc.iot.iotbackend.rest.server.entity.Event;
import com.guc.iot.iotbackend.rest.server.entity.User;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author eslam
 */

@Path("users")
@RequestScoped
public class UsersResource {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private List<User> users;
    
    public UsersResource(){}
    
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public JsonArray getUser(final @PathParam("id") String id) throws Exception {
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
            String select_query = "select * from iotproject.USER where id=" + id + ";";
            
            
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery(select_query);

            users = new ArrayList<User>();

            while (resultSet.next()) {
                User tmp = new User();
                tmp.setId(resultSet.getLong("id"));
                if (resultSet.getString("username") != null) {
                    tmp.setUsername(resultSet.getString("username"));
                } else {
                    tmp.setUsername("empty user name");
                }
                if (resultSet.getString("password") != null) {
                    tmp.setPassword(resultSet.getString("password"));
                }

                if (resultSet.getString("major") != null) {
                    tmp.setMajor(resultSet.getString("major"));
                } else {
                    tmp.setMajor("null");
                }
                if (resultSet.getString("gradYear") != null) {
                    tmp.setGradYear(resultSet.getInt("gradYear"));
                } else {
                    tmp.setGradYear(2016);
                }
                
                users.add(tmp);
            }

            JsonArrayBuilder jab = Json.createArrayBuilder();
            for (User user : users) {
                jab.add(Json.createObjectBuilder()
                        .add("id", user.getId())
                        .add("username", user.getUsername())
                        .add("password", user.getPassword())
                        .add("major", user.getMajor())
                        .add("gradYear", user.getGradYear())
                );
            }

            return jab.build();

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
    
    @GET
    @Produces("application/json")
    public JsonArray getUserList() throws Exception {
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
            String select_query = "select * from iotproject.USER";
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery(select_query);

            users = new ArrayList<User>();

            while (resultSet.next()) {
                User tmp = new User();
                tmp.setId(resultSet.getLong("id"));
                if (resultSet.getString("username") != null) {
                    tmp.setUsername(resultSet.getString("username"));
                } else {
                    tmp.setUsername("empty user name");
                }
                if (resultSet.getString("password") != null) {
                    tmp.setPassword(resultSet.getString("password"));
                }

                if (resultSet.getString("major") != null) {
                    tmp.setMajor(resultSet.getString("major"));
                } else {
                    tmp.setMajor("null");
                }
                if (resultSet.getString("gradYear") != null) {
                    tmp.setGradYear(resultSet.getInt("gradYear"));
                } else {
                    tmp.setGradYear(2016);
                }
                
                users.add(tmp);
            }

            JsonArrayBuilder jab = Json.createArrayBuilder();
            for (User user : users) {
                jab.add(Json.createObjectBuilder()
                        .add("id", user.getId())
                        .add("username", user.getUsername())
                        .add("password", user.getPassword())
                        .add("major", user.getMajor())
                        .add("gradYear", user.getGradYear())
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
     * {
  "username": "eslamigo",
  "major" :"MET",
  "gradYear": 2015,
  "password":"123456"
}
     * @param jsonEvent
     * @return
     * @throws Exception 
     */
    @POST
    @Consumes("application/json")
    public Response createUser(JsonObject jsonEvent) throws Exception {

        String username = (jsonEvent.getString("username"));
//        int gradYear =  (int) (jsonEvent.getInt("gradYear"));
        String major = (jsonEvent.getString("major"));
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
                    .prepareStatement("insert into  iotproject.USER(username,password,major,gradYear) values (?,?,?,?)");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, jsonEvent.getString("username"));
            preparedStatement.setString(2, jsonEvent.getString("password"));
            preparedStatement.setString(3, jsonEvent.getString("major"));
            int gradYear = (int) jsonEvent.getInt("gradYear");
            preparedStatement.setInt(4, gradYear);
            
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
