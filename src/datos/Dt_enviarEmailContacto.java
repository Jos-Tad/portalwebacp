package datos;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;


public class Dt_enviarEmailContacto {
	//ATRIBUTOS
	/*---------------------- Configuración Localhost------------------------------*/
	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final String SMTP_AUTH_USER = "portalwebacp@gmail.com";
	private static final String SMTP_AUTH_PWD = "PortalACP2@21";
	
    
    //DECLARAMOS UNA CLASE PRIVADA COMO ATRIBUTO QUE HEREDA JAVAX.MAIL.AUTHENTICATOR
    private class SMTPAuthenticator extends javax.mail.Authenticator 
	{
		public PasswordAuthentication getPasswordAuthentication() 
		{
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}
    
/*----------------------------------------------------------------------------*/

  //METODO QUE ENVIA EL EMAIL DE VERIFICACION
	public boolean enviarEmailVerificacion(String nombrescompleto, String asunto, String correo, String descripcion) throws MessagingException{
	
		 boolean debug=false;
		
	   // Correo del solicitante
		String email_solicitante = SMTP_AUTH_USER;
	
	   // Correo del remitente
		String email_remitente = SMTP_HOST_NAME;
	
	   // Obtener propiedades del sistema
	   Properties properties = new Properties();
	   
	   
	   /*---------------------- Configuración del servidor de correo---------------------------*/ 
  		   properties.setProperty("mail.smtp.host", SMTP_HOST_NAME);
  		   properties.put("mail.smtp.auth", "true");
  		   properties.setProperty("mail.smtp.port", "587");
  		   properties.put("mail.smtp.starttls.enable", "true");
	   /*--------------------------------------------------------------------------------------*/
	   
	   Authenticator auth = new SMTPAuthenticator();

	     Session session = Session.getInstance(properties,auth);
		 session.setDebug(debug);
	   
	      // Create a default MimeMessage object.
	      	MimeMessage message = new MimeMessage(session);
	
	      // Establecer De (remitente)
	      	message.setFrom(new InternetAddress(email_remitente));
	
	      // Establecer Para (solicitante)
	      	message.addRecipient(Message.RecipientType.TO, new InternetAddress(email_solicitante));
	      
	      // Asunto: encabezado del archivo
	        message.setSubject(asunto);  		      
	        
	      //Cuerpo del correo  
	        String myMsg = "<meta charset=\"utf-8\">";	        
	      	myMsg += "<strong>Formulario de contacto - Arboreto Carmelo Palma UCA:</strong><br><br>";
	        myMsg += "A continuaci&oacute;n se detallan los datos enviados: <br><br>";
	      	myMsg += "<strong>Nombres y apellidos: </strong> "+nombrescompleto+"<br>";
	      	myMsg += "<strong>Correo electr&oacute;nico: </strong> "+ correo+"<br>";
	      	myMsg += "<strong>Descripción: </strong>"+descripcion+"<br>";
	      
	      	message.setContent(myMsg, "text/html");
	      
	      //Enviar Correo
	      Transport transport = session.getTransport("smtp");
	      transport.connect(SMTP_HOST_NAME, SMTP_AUTH_USER, SMTP_AUTH_PWD);
	      Transport.send(message);
	      debug = true;
	      System.out.println("El mensaje fue enviado con éxito");
	      return debug;
	}
}
