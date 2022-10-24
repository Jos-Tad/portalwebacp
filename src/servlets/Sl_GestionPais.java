package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import datos.Dt_Pais;
import entidades.Pais;
import negocio.Ng_Pais;

/**
 * Servlet implementation class Sl_GestionPais
 */
@WebServlet("/Sl_GestionPais")
public class Sl_GestionPais extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionPais() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		int idPais =0;
		idPais = Integer.parseInt(request.getParameter("idP"));
		Dt_Pais dtp = new Dt_Pais();
		
		if(dtp.eliminarPais(idPais)) {
        	response.sendRedirect("GestionPais.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionPais.jsp?msj=6");
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
		
		//Construir objeto pais
		Dt_Pais dtp = new Dt_Pais();
		Pais p = new Pais();
		Ng_Pais ngp = new Ng_Pais();
		
		p.setNombre(request.getParameter("txtNombrePais"));
		p.setDescripcion(request.getParameter("txtDescripcionPais"));
		p.setRegionID(Integer.parseInt(request.getParameter("txtNombreRegion")));
		
		//Variables de control
		String nombre = request.getParameter("txtNombrePais");
		String desc = request.getParameter("txtDescripcionPais");
		
		if(nombre.trim().isEmpty() || desc.trim().isEmpty()){
        	response.sendRedirect("GestionPais.jsp?msj=2");					
		}
		else{
		//Setear info a objeto
		p.setNombre(nombre);
		p.setDescripcion(desc);
		

		switch (opc){
		case 1:{
			    try {
	        	if(ngp.existePais(p.getNombre())){
		        	response.sendRedirect("GestionPais.jsp?msj=existe");
		        }
		        else {			        
			        if(	dtp.guardarPais(p)) {
			        	response.sendRedirect("GestionPais.jsp?msj=1");
			        }
			        else {
			        	response.sendRedirect("GestionPais.jsp?msj=2");
			        }
		          } 		        	
		        }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionPais, el error es: " + e.getMessage());
					e.printStackTrace();
		        }
		        
				break;
			}
		case 2:{
			p.setPaisID(Integer.parseInt(request.getParameter("idpais")));
     		try { 
     			if(ngp.existeActualizarPais(p.getPaisID(),p.getNombre())){
		          	response.sendRedirect("GestionPais.jsp?msj=existe");
		        }else {
				   if(dtp.modificarPais(p)) {
		        	response.sendRedirect("GestionPais.jsp?msj=3");
		        }
		        else {
		        	response.sendRedirect("GestionPais.jsp?msj=4");
		        }
		        }
		        
	        }
	        catch(Exception e) {
	        	System.out.println("Sl_GestionPais, el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;
				
			}
		default:
			response.sendRedirect("GestionPais.jsp?msj=7");	
			break;
		}
		
		}
	}
}
