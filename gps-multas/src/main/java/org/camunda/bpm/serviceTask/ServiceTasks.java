package org.camunda.bpm.serviceTask;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ServiceTasks implements ExternalTaskHandler {

    private static final Logger LOGGER =
            Logger.getLogger(ServiceTasks.class.getName());

    private Integer calcularPrecioPenalizado(Integer precioInfraccion) {
        return precioInfraccion * 2;
    }

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {

        try {
            // 1) Leer variable existente
            Object raw = externalTask.getVariable("precio_infraccion");
            if (raw == null) {
                throw new RuntimeException("La variable 'precio_infraccion' no existe o está vacía");
            }

            // 2) Normalizar a Integer (por si viene como Double/Long)
            Integer precioInfraccion = ((Number) raw).intValue();

            // 3) Calcular penalización
            Integer precioPenalizacion = calcularPrecioPenalizado(precioInfraccion);

            // 4) Guardar en Camunda (PUT)
            Map<String, Object> variables = new HashMap<>();
            variables.put("precio_penalizacion", precioPenalizacion);

            // 5) COMPLETAR
            externalTaskService.complete(externalTask, variables);

            LOGGER.info("Penalización aplicada: precio_infraccion="
                    + precioInfraccion + " -> precio_penalizacion=" + precioPenalizacion);

        } catch (Exception e) {
            LOGGER.severe("Error en AnadirPenalizacion: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

