package org.camunda.bpm.sendTask;
import java.util.logging.Logger;
import org.camunda.bpm.client.ExternalTaskClient;
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
    }
}
