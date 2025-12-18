package org.camunda.bpm.sendTask;

import java.util.logging.Logger;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class MailGenerico {
	
	private static final Logger LOGGER = Logger.getLogger(MailGenerico.class.getName());

    // Configuración SMTP de Gmail
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_USER = "p123456789vera@gmail.com";
    private static final String SMTP_PASSWORD = "tesizlkbgntmelow";
    private static final int SMTP_PORT = 587;


    public void enviarCorreo(String destinatario, String asunto, String cuerpoMensaje) throws Exception {

        if (destinatario == null || destinatario.isEmpty()) {
            throw new IllegalArgumentException("Email destinatario no puede estar vacío");
        }

        LOGGER.info("Preparando envío de correo a: " + destinatario);

        Email msg = new SimpleEmail();

        msg.setHostName(SMTP_HOST);
        msg.setAuthenticator(new DefaultAuthenticator(SMTP_USER, SMTP_PASSWORD));
        msg.setSmtpPort(SMTP_PORT);
        msg.setSSLOnConnect(false);
        msg.setStartTLSEnabled(true);
        msg.setStartTLSRequired(true);

        msg.getMailSession().getProperties().put("mail.smtp.ssl.protocols", "TLSv1.2");
        msg.getMailSession().getProperties().put("mail.smtp.ssl.trust", SMTP_HOST);

        msg.setFrom(SMTP_USER, "[GPS]");
        msg.setSubject(asunto);
        msg.setMsg(cuerpoMensaje);
        msg.addTo(destinatario);
        
        msg.send();

        LOGGER.info("✓ Correo enviado exitosamente a: " + destinatario);
    }

}
