package servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datos.Dt_Rol;
import datos.Dt_Usuario;
import entidades.Rol;
import negocio.Ng_Rol;

/**
 * Servlet implementation class Sl_GetionRol
 */
@WebServlet("/Sl_GestionRol")
public class Sl_GestionRol extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionRol() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		int idRol =0;
		idRol = Integer.parseInt(request.getParameter("idR"));
		Dt_Rol dtr = new Dt_Rol();
		
		if(dtr.eliminarRol(idRol)) {
        	response.sendRedirect("GestionRol.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionRol.jsp?msj=6");
        }	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		//obtenemos el valor de opcion
		int opc = 0;
		opc = Integer.parseInt(request.getParameter("opcion"));
		
		//CONSTRUIR EL OBJETO ROL
		Dt_Rol dtr = new Dt_Rol();
		Ng_Rol ngr = new Ng_Rol();
		Rol r = new Rol();
		
		//Variable
		String rol = request.getParameter("txtRol");
		String desc =request.getParameter("txtRolDesc"); 
		
		if(rol.trim().isEmpty() || desc.trim().isEmpty()){
        	response.sendRedirect("GestionRol.jsp?msj=2");
		}else {
		
		//Setear valores
		r.setRol(rol);
		r.setDesc_rol(desc);
	
		switch (opc){
		case 1:{
			
		        try {
		        	//PARA GUARDAR LA FECHA Y HORA DE CREACION
			        Date fechaSistema = new Date();
			        r.setFcreacion(new java.sql.Timestamp(fechaSistema.getTime()));
			        if(ngr.existeRol(r.getRol())) {
			        	response.sendRedirect("FormRol.jsp?msj=existe");
			        }
			        else {
			         if(dtr.guardarrol(r)) {
			        	response.sendRedirect("GestionRol.jsp?msj=1");
			        }
			        else {
			        	response.sendRedirect("GestionRol.jsp?msj=2");
			        }
			      }		        	
		        }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionRol, el error es: " + e.getMessage());
					e.printStackTrace();
		        }
		        
				break;
			}
		
		case 2:{
				
			try {
				r.setIdRol(Integer.parseInt(request.getParameter("idRol")));	
				if(ngr.existeActualizarRol(r.getIdRol(),r.getRol()))
				{
		          	response.sendRedirect("FormEditarRol.jsp?rolID="+r.getIdRol()+"&msj=existe");

				}else{
				 if(dtr.modificarRol(r)) {
		        	response.sendRedirect("GestionRol.jsp?msj=3");
		        }
		        else {
		        	response.sendRedirect("GestionRol.jsp?msj=4");
		        }
			  }
		      }
	        catch(Exception e) {
	        	System.out.println("Sl_GestionRol, el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;				
			}
		
		default:
			response.sendRedirect("GestionRol.jsp?msj=5");	
			break;
	 }	
	}
   }
}
