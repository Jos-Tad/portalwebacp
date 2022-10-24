package servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import datos.Encrypt;
import entidades.Usuario;
import negocio.Ng_Usuario;
import datos.Dt_Usuario;

/**
 * Servlet implementation class Sl_GestionUsuario
 */
@WebServlet("/Sl_GestionUsuario")
public class Sl_GestionUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		int idUser =0;
		idUser = Integer.parseInt(request.getParameter("idU"));
		Dt_Usuario dtu = new Dt_Usuario();
		
		if(dtu.eliminarUser(idUser)) {
        	response.sendRedirect("GestionUsuario.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionUsuario.jsp?msj=6");
        }	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		
		//obtenemos el valor de opcion
		int opc = 0;
		opc = Integer.parseInt(request.getParameter("opcion"));
		
		//CONSTRUIR EL OBJETO USUARIO
		Dt_Usuario dtu = new Dt_Usuario();
		Usuario user = new Usuario();
		Encrypt enc = new Encrypt();
		Ng_Usuario ngu = new Ng_Usuario();
		
		//Control Variables
		String nombres = null;
		String apellidos = null;
		String usuario = null;
		String pwd= null;
		String email = null;
		String telefono = null;
		String url_foto = null;
		
		 nombres = request.getParameter("txtNombres");
		 apellidos = request.getParameter("txtApellidos");
		 usuario = request.getParameter("txtUserName");
		 pwd= request.getParameter("txtPwd");
		 email = request.getParameter("txtEmail");
		 telefono = request.getParameter("txtTelefono");
		 url_foto = request.getParameter("url_foto");
	 
		if(nombres.trim().isEmpty()|| apellidos.trim().isEmpty()|| usuario.trim().isEmpty()){
        	response.sendRedirect("GestionUsuario.jsp?msj=2");
		}else{
		user.setNombre(nombres);
		user.setApellido(apellidos);
		user.setUser(usuario);		
		user.setPwd(pwd);
		user.setEmail(email);
		user.setTelefono(telefono);
		user.setUrl_foto(url_foto);		

		if(user.getPwd().equals("")==false){
			/////// ENCRIPTACION DE LA PWD //////////
			String key = "";
			String pwdEncrypt = "";
			key=enc.generarLLave();
			pwdEncrypt = enc.getAES(user.getPwd(),key);
			user.setPwd(pwdEncrypt);
			user.setKey_encriptacion(key);
		}else {
		     user.setCod_verificacion("");
		     user.setKey_encriptacion("");
		    
		}

	    if(user.getTelefono() == null || user.getTelefono().isEmpty()){
			user.setTelefono("-");			
		}else{
			user.setTelefono(request.getParameter("txtTelefono"));			
		}
		
		switch (opc){
			case 1:{
				
			        try {
			        	
			        	 //GENERAMOS EL CODIGO DE VERIFICACION Y LO ASIGNAMOS AL OBJETO
			    		user.setCod_verificacion(dtu.randomAlphaNumeric(10)); // 10 PORQUE ES LA CANTIDAD DE CARACTERES QUE SOPORTA LA BD
			    	
			        	//PARA GUARDAR LA FECHA Y HORA DE CREACION
				        Date fechaSistema = new Date();
				        user.setfCreacion(new java.sql.Timestamp(fechaSistema.getTime()));
				        if(ngu.existeUser(user.getUser())) {
				        	response.sendRedirect("FormUsuario.jsp?msj=existe");
				        }
				        else {
				        	if(ngu.existeCorreoUsuario(user.getEmail())){
					        	response.sendRedirect("FormUsuario.jsp?msj=existec");
				        	}else{
					        	if(dtu.guardarUser(user)) {
					        		response.sendRedirect("GestionUsuario.jsp?msj=1");
						        }
						        else {
						        	response.sendRedirect("GestionUsuario.jsp?msj=2");
						        }
				        	}
				        }			    	  
			        }
			        catch(Exception e) {
			        	System.out.println("Sl_GestionUsuario, el error es: " + e.getMessage());
						e.printStackTrace();
			        }			        
					break;
				}
			
			case 2:{
					
				try {
					user.setIdUser(Integer.parseInt(request.getParameter("idUsuario")));
					user.setEstado(Integer.parseInt(request.getParameter("estado")));
		        	//PARA GUARDAR LA FECHA Y HORA DE MODIFICACION
			        Date fechaSistema = new Date();
			        user.setfModificacion(new java.sql.Timestamp(fechaSistema.getTime()));
			        if(ngu.existeActualizarUsuario(user.getIdUser(),user.getUser())){
			          	response.sendRedirect("FormEditarUsuario.jsp?userID="+user.getIdUser()+"&msj=existe");
			        }else {
		        	if(ngu.existeActualizarCorreoUsuario(user.getIdUser(),user.getEmail())){
			        	response.sendRedirect("FormEditarUsuario.jsp?userID="+user.getIdUser()+"&msj=existec");
		        	}else{
		        		if(dtu.modificarUser(user)) {
			        	response.sendRedirect("GestionUsuario.jsp?msj=3");
			        	}
		        		else {
			        	response.sendRedirect("GestionUsuario.jsp?msj=4");
			        	}
		        	  }
			         }
					}
		        catch(Exception e) {
		        	System.out.println("Sl_GestionUsuario, el error es: " + e.getMessage());
					e.printStackTrace();
		        }
					break;
					
				}
			
			 default:
				response.sendRedirect("GestionUsuario.jsp?msj=7");	
				break;
		}	
	}
  }
}
