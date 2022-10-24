package servlets;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Banner;
import entidades.Coordenada;
import entidades.Region;
import negocio.Ng_Banner;
import negocio.Ng_Coordenada;
import negocio.Ng_Region;
import datos.Dt_Banner;
import datos.Dt_Coordenada;
import datos.Dt_Region;

/**
 * Servlet implementation class Sl_EditarCoordenada
 */
@WebServlet("/Sl_GestionCoordenada")
public class Sl_GestionCoordenada extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionCoordenada() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int coordenadaid =0;
		coordenadaid = Integer.parseInt(request.getParameter("idC"));
		Dt_Coordenada dtc = new Dt_Coordenada(); 
		
		if(dtc.eliminarCoordenada(coordenadaid)) {
        	response.sendRedirect("GestionCoordenada.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionCoordenada.jsp?msj=6");
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
		
		//CONSTRUIR EL OBJETO Region
		Dt_Coordenada dtc = new Dt_Coordenada();
		Coordenada co = new Coordenada();
		Ng_Coordenada ngc = new Ng_Coordenada();
			
		//Variables de control
		String nombre = request.getParameter("txtNombre");
		String desc = request.getParameter("txtDescripcion");
		String latitud = request.getParameter("txtLatitud");
		String longitud = request.getParameter("txtLongitud");

		
		
		if(nombre.trim().isEmpty() || desc.trim().isEmpty() || latitud.trim().isEmpty() || longitud.trim().isEmpty()){
        	response.sendRedirect("GestionCoordenada.jsp?msj=2");					
		}
		else{
		//Setear info a objeto
		co.setNombre(nombre);
		co.setDescripcion(desc);;
		co.setLatitud(latitud);
		co.setLongitud(longitud);
		
		switch (opc){
		case 1:{
			
		        try {
		        	if(ngc.existeCoordenada(co.getNombre())){
			        	response.sendRedirect("FormCoordenada.jsp?msj=existe");
			        }
		        	else if(ngc.existeLongitud(co.getLongitud()) || ngc.existeLatitud(co.getLatitud())){
			        	response.sendRedirect("FormCoordenada.jsp?msj=existe1");
			        }
			        else {
			        if(	dtc.guardarCoordenada(co)) {
			        	response.sendRedirect("GestionCoordenada.jsp?msj=1");
			        }
			        else {
			        	response.sendRedirect("GestionCoordenada.jsp?msj=2");
			        }
			      }				        	
		        }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionCoordenada, el error es: " + e.getMessage());
					e.printStackTrace();
		        }
		        
				break;
		}

		default:
			response.sendRedirect("GestionCoordenada.jsp?msj=7");	
			break;
		}	
    }	
   }
}