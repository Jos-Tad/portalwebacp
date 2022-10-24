package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import entidades.Genero;
import datos.Dt_Genero;
import negocio.Ng_Genero;

/**
 * Servlet implementation class Sl_GestionGenero
 */
@WebServlet("/Sl_GestionGenero")
public class Sl_GestionGenero extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionGenero() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int idGenero =0;
		idGenero = Integer.parseInt(request.getParameter("idG"));
		Dt_Genero dtg = new Dt_Genero();
		
		if(dtg.eliminarGenero(idGenero)) {
        	response.sendRedirect("GestionGenero.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionGenero.jsp?msj=6");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		//Obtenemos el valor de opcion
		int opc = 0;
		opc = Integer.parseInt(request.getParameter("opcion"));
		
		//Construir objeto genero
		Dt_Genero dtg = new Dt_Genero();
		Ng_Genero ngg = new Ng_Genero();
		Genero gn = new Genero();
		gn.setNombre(request.getParameter("txtNombreGenero"));
		gn.setDescripcion(request.getParameter("txtDescripcionGenero"));		
		
		
		//Variables de control
		String nombre = request.getParameter("txtNombreGenero");
		String desc = request.getParameter("txtDescripcionGenero");
		
		if(nombre.trim().isEmpty() || desc.trim().isEmpty()){
        	response.sendRedirect("GestionGenero.jsp?msj=2");					
		}
		else{
		//Setear info a objeto
	    gn.setNombre(nombre);
		gn.setDescripcion(desc);
		
		switch (opc){
		case 1:{			
		        try {
		        	if(ngg.existeGenero(gn.getNombre()))
		        	{
		        		response.sendRedirect("GestionGenero.jsp?msj=existe");									
		        	}else {	        
			        if(	dtg.guardarGenero(gn)) {
			        	response.sendRedirect("GestionGenero.jsp?msj=1");
			        } 
			        else {
			        	response.sendRedirect("GestionGenero.jsp?msj=2");
			        }
		        	}
		        }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionGenero, el error es: " + e.getMessage());
					e.printStackTrace();
		        }		        
				break;
			}
		case 2:{
			gn.setGeneroID(Integer.parseInt(request.getParameter("idgenero")));
     		try {
     			if(ngg.existeActualizarGenero(gn.getGeneroID(),gn.getNombre())){
		          	response.sendRedirect("GestionGenero.jsp?msj=existe");
		        }else {
				   if(dtg.modificarGenero(gn)) {
		        	response.sendRedirect("GestionGenero.jsp?msj=3");
		        }
		        else {
		        	response.sendRedirect("GestionGenero.jsp?msj=4");
		        }
		        }
	        }
	        catch(Exception e) {
	        	System.out.println("Sl_GestionGenero, el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;
				
			}
		default:
			response.sendRedirect("GestionGenero.jsp?msj=7");	
			break;
		}
		
	}		
	}

}
