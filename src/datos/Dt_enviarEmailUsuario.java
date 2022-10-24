package datos;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Dt_enviarEmailUsuario {
	
	//ATRIBUTOS
	/*---------------------- Configuración Localhost------------------------------*/
	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final String SMTP_AUTH_USER = "portalwebacp@gmail.com";
	private static final String SMTP_AUTH_PWD = "PortalACP2@21";
	
	//Enlace
    String linkHR = "http://165.98.12.158:9090/PortalWebACP/login.jsp";
    
    String linkHR2 = "http://165.98.12.158:9090/PortalWebACP/CambiarContra.jsp";
    
    
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
 
  public boolean enviarEmailVerificacion(String usuario, String clave, String correo, String codigo, String rol) throws MessagingException{
	
		 boolean debug=false;		 
		 
	   // Correo del solicitante
		String email_solicitante = correo;
	
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
		  message.setSubject("Proceso de verificación de usuario - Portal Arboreto Carmelo Palma");
	        
	      //Cuerpo del correo  
	        String myMsg = "<meta charset=\"utf-8\">";
	      	myMsg += "A continuaci&oacute;n se detallan los datos: <br><br>";
	      	myMsg += "<strong>Usuario: </strong> "+usuario+"<br>";
	      	myMsg += "<strong>Correo electr&oacute;nico: </strong> "+ correo+"<br>";
	      	myMsg += "<strong>Contraseña: </strong> "+clave+"<br>";
	      	myMsg += "<strong>Rol: </strong> "+rol+"<br>";
       	    myMsg += "Enlace de verificaci&oacute;n: "  + linkHR + "?codverif="+ codigo + "<br>";
       	    myMsg += "Aseg&uacute;rate de hacer clic en el enlace de verificaci&oacute;n que has recibido para que podamos activar tu cuenta, en caso de no haber solicitado una cuenta, por favor hacer caso omiso al presente correo.";
	      	myMsg += "<br>----------------------------------------------------------<br>";
	      	myMsg += "Administrador de Arborerto Carmelo Palma<br>";
	      	myMsg += "Universidad Centroamericana";
	      
	      	message.setContent(myMsg, "text/html");
	      
	      //Enviar Correo
	      Transport transport = session.getTransport("smtp");
	      transport.connect(SMTP_HOST_NAME, SMTP_AUTH_USER, SMTP_AUTH_PWD);
	      Transport.send(message);
	      debug = true;
	      return debug;
	}
  
  /*----------------------------------------------------------------------------*/
	
	//METODO QUE ENVIA EL EMAIL PARA RECUPERAR CONTRASEÑA
	public boolean recuperarContra(String usuario, String correo) throws MessagingException{
	
		boolean debug=false;
		
	   // Correo del solicitante
		String email_solicitante = correo;
	
	   // Correo del remitente
		String email_remitente = SMTP_HOST_NAME;
	
	   // Obtener propiedades del sistema
	   Properties properties = new Properties();
	   
	   
	   /*---------------------- Configuracion del servidor de correo---------------------------*/ 
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
	        message.setSubject("Proceso para recuperar la contraseña - Portal Arboreto Carmelo Palma");
	      
	        
	      //Cuerpo del correo  
	        String myMsg = "<strong>Proceso para recuperar la contraseña - Portal Arboreto Carmelo Palma</strong><br><br>";
	      	myMsg += "Estimado usuario, usted ha solicitado recuperar su contraseña,";
	      	myMsg += "a continuaci&oacute;n se detallan los datos enviados: <br><br>";
	      	myMsg += "<strong><u> Datos del usuario </u></strong> <br><br>";
	      	myMsg += "<strong>Usuario: </strong> "+usuario+"<br>";
	      	myMsg += "<strong>Correo electr&oacute;nico: </strong> "+email_solicitante+"<br>";
	      	myMsg += "Enlace de recuperaci&oacute;n: "  + linkHR2 + "?us="+usuario+ "&em="+email_solicitante+"<br>";
	      	myMsg += "Aseg&uacute;rate de hacer clic en el enlace de recuperaci&oacute;n que has recibido para que podamos reactivar tu cuenta.";
	      	myMsg += "<br>----------------------------------------------------------<br>";
	    	myMsg += "Administrador de Arborerto Carmelo Palma<br>";
	      	myMsg += "Universidad Centroamericana";
	      
	      message.setContent(myMsg, "text/html");
	      
	      // Enviar Correo
	      Transport transport = session.getTransport("smtp");
	      transport.connect(SMTP_HOST_NAME, SMTP_AUTH_USER, SMTP_AUTH_PWD);
	      Transport.send(message);
	      debug = true;
	      return debug;
	}

}
