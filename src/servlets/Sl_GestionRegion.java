	package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import entidades.Region;
import datos.Dt_Region;
import negocio.Ng_Region;
/**
 * Servlet implementation class Sl_GestionRegion
 */
@WebServlet("/Sl_GestionRegion")
public class Sl_GestionRegion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionRegion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int idRegion =0;
		idRegion = Integer.parseInt(request.getParameter("idR"));
		Dt_Region dtr = new Dt_Region();
		
		if(dtr.eliminarRegion(idRegion)) {
        	response.sendRedirect("GestionRegion.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionRegion.jsp?msj=6");
        }
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//obtenemos el valor de opcion
				int opc = 0;
				opc = Integer.parseInt(request.getParameter("opcion"));
				
				//CONSTRUIR EL OBJETO Region
				Dt_Region dtf = new Dt_Region();
				Region rg = new Region();
				Ng_Region ngr = new Ng_Region();
				
				rg.setNombre(request.getParameter("txtNombreRegion"));
				rg.setDescripcion(request.getParameter("txtDescripcionRegion"));
				
				//Variables de control
				String nombre = request.getParameter("txtNombreRegion");
				String desc = request.getParameter("txtDescripcionRegion");
				
				if(nombre.trim().isEmpty() || desc.trim().isEmpty()){
		        	response.sendRedirect("GestionRegion.jsp?msj=2");					
				}
				else{
				//Setear info a objeto
				rg.setNombre(nombre);
				rg.setDescripcion(desc);
				
				switch (opc){
				case 1:{
					
				        try {
				        	if(ngr.existeRegion(rg.getNombre())){
					        	response.sendRedirect("GestionRegion.jsp?msj=existe");
					        }
					        else {
					        if(	dtf.guardarRegion(rg)) {
					        	response.sendRedirect("GestionRegion.jsp?msj=1");
					        }
					        else {
					        	response.sendRedirect("GestionRegion.jsp?msj=2");
					        }
					      }				        	
				        }
				        catch(Exception e) {
				        	System.out.println("Sl_GestionRegion, el error es: " + e.getMessage());
							e.printStackTrace();
				        }
				        
						break;
				}
				case 2:{
					rg.setRegionID(Integer.parseInt(request.getParameter("idregion")));
		     		try {
		     			if(ngr.existeActualizarRegion(rg.getRegionID(),rg.getNombre())){
				          	response.sendRedirect("GestionRegion.jsp?msj=existe");
				        }else {
		     			   if(dtf.modificarRegion(rg)) {
				        	response.sendRedirect("GestionRegion.jsp?msj=3");
				        }
				        else {
				        	response.sendRedirect("GestionRegion.jsp?msj=4");
				        }
				        
				        }
			        }
			        catch(Exception e) {
			        	System.out.println("Sl_GestionRegion, el error es: " + e.getMessage());
						e.printStackTrace();
			        }
						break;
						
					}
				default:
					response.sendRedirect("GestionRegion.jsp?msj=7");	
					break;
				}
				
		}	
	}
}
