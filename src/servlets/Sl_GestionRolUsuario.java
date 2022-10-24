package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datos.Dt_RolUsuario;
import datos.Dt_Usuario;
import datos.Dt_enviarEmailUsuario;
import datos.Encrypt;
import entidades.RolUsuario;
import vistas.ViewRolUsuario;
import negocio.Ng_RolUsuario;

/**
 * Servlet implementation class Sl_GestionRolUsuario
 */
@WebServlet("/Sl_GestionRolUsuario")
public class Sl_GestionRolUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionRolUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int idRolUser =0;
		idRolUser = Integer.parseInt(request.getParameter("idRU"));
		Dt_RolUsuario dtru = new Dt_RolUsuario(); 
		
		if(dtru.eliminaRU(idRolUser)) {
        	response.sendRedirect("GestionRolUsuario.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionRolUsuario.jsp?msj=6");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		//obtenemos el valor de opcion
		int opc = 0;
		opc = Integer.parseInt(request.getParameter("opcion"));
				
		//CONSTRUIR EL OBJETO ROL-USER
		RolUsuario ru = new RolUsuario();		
		Dt_RolUsuario dtru = new Dt_RolUsuario();
		Dt_enviarEmailUsuario dte = new Dt_enviarEmailUsuario();
		Dt_Usuario dtu = new Dt_Usuario();
		ViewRolUsuario vu = new ViewRolUsuario();
		Encrypt enc = new Encrypt();
		Ng_RolUsuario ngr = new Ng_RolUsuario();
		
		//Recuperamos la id del rol
		ru.setRolid(Integer.parseInt(request.getParameter("cbxRol")));		
				
		switch (opc){
		case 1:{
					ru.setUsuarioid(Integer.parseInt(request.getParameter("cbxUser")));
				
					try {					
					if(ngr.existeRolAsignado(ru.getUsuarioid(), ru.getRolid())){
			        	response.sendRedirect("FormRolUsuario.jsp?msj=existe");
			        }
			        else {
			        		boolean control = false;
			        		if(dtru.guardarRolUser(ru)) {
			        			control=true;
				        	}
				        	else {
				        	response.sendRedirect("GestionRolUsuario.jsp?msj=2");
				        	} 
			        		if(control){
			        			if(ngr.existeAsignacionRol(ru.getUsuarioid())){			        			
					    			response.sendRedirect("GestionRolUsuario.jsp?msj=1");
			        			}else{
			        				vu = dtu.getVwUsuarioRol(ru.getUsuarioid());
									
									vu.setContra(enc.getAESDecrypt(vu.getContra(), vu.getKey_encriptacion()));					
									
				        			if(dte.enviarEmailVerificacion(vu.getUsuario(), vu.getContra(), vu.getEmail(), vu.getCod_verificacion(),vu.getRol())){
				        				if(dtru.confirmEmail(vu.getIdrol_usuario())){
					        			    response.sendRedirect("GestionRolUsuario.jsp?msj=em");
				        				}				        				
						    		}else {
						        	response.sendRedirect("GestionRolUsuario.jsp?msj=2");
						    		}				        			
			        			}
			        		}
			    		}
			        	  	
		          }
				catch(Exception e) {
		        	System.out.println("Sl_GestionRolUsuario, el error es: " + e.getMessage());
					e.printStackTrace();
		        }
		        
				break;
			}
		case 2:{
			ru.setUsuarioid(Integer.parseInt(request.getParameter("cbxBUser")));	
			try {
	        	ru.setIdrol_usuario(Integer.parseInt(request.getParameter("rolusuarioid")))	;
	        	
	        	if(ngr.existeActualizarRolUsuario(ru.getIdrol_usuario(),ru.getUsuarioid(), ru.getRolid())){
		          	response.sendRedirect("FormEditarRolUsuario.jsp?idRu="+ru.getIdrol_usuario()+"&msj=existe");
		        }
	        	else{
		        if(dtru.modificarRolUsuario(ru)) {
		        	response.sendRedirect("GestionRolUsuario.jsp?msj=3");
		        }
		        else {
		        	response.sendRedirect("GestionRolUsuario.jsp?msj=4");
		        }
	        	}       		        	
	        }
	        catch(Exception e) {
	        	System.out.println("Sl_GestionRolUser, el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;
				
			}
		
		default:
			response.sendRedirect("GestionRolUsuario.jsp?msj=5");	
			break;
	  }
	}
 }

