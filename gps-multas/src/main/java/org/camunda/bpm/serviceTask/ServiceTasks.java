package org.camunda.bpm.serviceTask;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

public class ServiceTasks {

    private static final Logger LOGGER = Logger.getLogger(ServiceTasks.class.getName());

    // Calcula el precio penalizado
    private static Integer calcularPrecioPenalizado(Integer precioInfraccion) {
        return precioInfraccion * 2;
    }

    // Tarea de servicio (External Task) para añadir penalización
    public static void AñadirPenalizacion(ExternalTaskClient client) {

        client.subscribe("añadir-penalizacion")
            .lockDuration(10000)
            .handler((externalTask, externalTaskService) -> {

                // 1) Leer variable existente
                Integer precioInfraccion = (Integer) externalTask.getVariable("precio_infraccion");

                if (precioInfraccion == null) {
                    throw new RuntimeException("Falta la variable 'precio_infraccion'");
                }

                // 2) Calcular penalización
                Integer precioPenalizacion = calcularPrecioPenalizado(precioInfraccion);

                // 3) Put de la nueva variable
                Map<String, Object> variables = new HashMap<>();
                variables.put("precio_penalizacion", precioPenalizacion);

                // 4) Complete enviando variables
                externalTaskService.complete(externalTask, variables);

                LOGGER.info("precio_infraccion=" + precioInfraccion
                        + " -> precio_penalizacion=" + precioPenalizacion);
            })
            .open();
    }
}

