package org.camunda.bpm.serviceTask;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

public class ServiceTasks {
	
	
	private final static Logger LOGGER = Logger.getLogger(ServiceTasks.class.getName());
	
	
	//Añadir penalizacion
	public static void AñadirPenalizacion(ExternalTaskClient client) {
	    // subscribe to an external task topic as specified in the process
	    client.subscribe("charge-card")
	        .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
	        .handler((externalTask, externalTaskService) -> {
	          // Put your business logic here

	          // Get a process variable
	
	          Integer idInfractor = (Integer) externalTask.getVariable("id_infractor");
	      	  Integer idInfraccion = (Integer) externalTask.getVariable("id_infraccion");

	          LOGGER.info("Añadiendo penalizacion a la infracción con id: " + idInfraccion + 
	        		  " al infractor con id: " + idInfractor + " .");

	          try {
	              Desktop.getDesktop().browse(new URI("https://docs.camunda.org/get-started/quick-start/complete"));
	          } catch (Exception e) {
	              e.printStackTrace();
	          }

	          // Complete the task
	          externalTaskService.complete(externalTask);
	        })
	        .open();
		
	}
	
	//Otra tarea de servicio

}
