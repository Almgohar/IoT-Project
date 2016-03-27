/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guc.iot.iotbackend.rest.server;

/**
 *
 * @author eslam
 */
import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new java.util.HashSet<>();
    try {
      // Class jsonProvider = Class.forName("org.glassfish.jersey.jackson.JacksonFeature");
      // Class jsonProvider = Class.forName("org.glassfish.jersey.moxy.json.MoxyJsonFeature");
      // Class jsonProvider = Class.forName("org.glassfish.jersey.jettison.JettisonFeature");
      // resources.add(jsonProvider);
    } catch (Exception ex) {
      java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    addRestResourceClasses(resources);
    return resources;
  }

  private void addRestResourceClasses(Set<Class<?>> resources) {
    resources.add(com.guc.iot.iotbackend.rest.server.EventsResource.class);
    resources.add(com.guc.iot.iotbackend.rest.server.OrganizationsResource.class);
    resources.add(com.guc.iot.iotbackend.rest.server.NotificationsResource.class);
  }
}