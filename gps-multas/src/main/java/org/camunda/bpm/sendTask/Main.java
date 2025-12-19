package org.camunda.bpm.sendTask;
import java.util.logging.Logger;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.serviceTask.ServiceTasks;
public class Main {

	private static final Logger LOGGER =
            Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(10000)
                .build();

        LOGGER.info("External Task Client iniciado");
        
        
        client.subscribe("notificar-infractor") 
        .lockDuration(1000)
        .handler(new NotificarInfractor())
        .open();
        
        client.subscribe("añadir-penalizacion")
        .handler(new ServiceTasks())
        .open();

        
        client.subscribe("notificar-incremento") 
        .lockDuration(1000)
        .handler(new NotificarInfractorIncremento())
        .open();
        
        client.subscribe("confirmar-pago-recibido") 
        .lockDuration(1000)
        .handler(new ConfirmacionPago())
        .open();
        
        
        client.subscribe("enviar-resolucion-aceptada") 
        .lockDuration(1000)
        .handler(new EnviarResolucionAceptada())
        .open();
        
        
        client.subscribe("enviar-resolucion-rechazada") 
        .lockDuration(1000)
        .handler(new EnviarResolucionRechazada())
        .open();
        
        
        client.subscribe("confirmar-pago-recargo") 
        .lockDuration(1000)
        .handler(new ConfirmacionPagoRecargo())
        .open();
        
        
        client.subscribe("notificar-recargo") 
        .lockDuration(1000)
        .handler(new NotificarRecargo20())
        .open();
        
        
    }
}
