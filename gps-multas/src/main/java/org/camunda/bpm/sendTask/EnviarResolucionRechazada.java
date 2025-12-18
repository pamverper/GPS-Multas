package org.camunda.bpm.sendTask;

import java.util.logging.Logger;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;

public class EnviarResolucionRechazada implements ExternalTaskHandler{
	
	//Handler correo
    private static final Logger LOGGER = Logger.getLogger(EnviarResolucionRechazada.class.getName());
    
    private MailGenerico mailWorker;

    public EnviarResolucionRechazada() {
        this.mailWorker = new MailGenerico();
    }

    
    //DATOS CORREO:
    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {

        // CONFIGURA los valores específicos para este correo
    	String destinatario = "lauritagaro14@gmail.com";
        Integer idInfractor = Integer.parseInt(externalTask.getVariable("id_infractor"));
        Integer idInfraccion = Integer.parseInt(externalTask.getVariable("id_infraccion"));

        // Construye el mensaje específico de confirmación de pagoS
        String asunto = "Multas - Penalizacion Incremento Multa";
        String mensaje = "\nDebido a que ha sobrepasado el limite de 20 dias, su cargo ha sido incrementado." +
                         "\n - Id infractor: " + idInfractor +
                         "\n - Id de la infraccion: " + idInfraccion + 
                         "\n\nEste mensaje ha sido generado automaticamente.";

        LOGGER.info("CONTENIDO DEL MENSAJE A ENVIAR: " + mensaje);

        try {
            mailWorker.enviarCorreo(destinatario, asunto, mensaje);
            LOGGER.info(">> El correo electrónico ha sido enviado");
            externalTaskService.complete(externalTask);
        } catch (Exception e) {
            LOGGER.warning(">> Error al enviar correo: " + e.getMessage());
            externalTaskService.handleFailure(externalTask,
                "Error al enviar correo",
                e.getMessage(),
                0,
                1000);
        }

        System.out.println(" >> ----------\n");
    }



}
