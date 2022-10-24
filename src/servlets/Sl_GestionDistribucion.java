package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entidades.Distribucion;
import negocio.Ng_Distribucion;
import datos.Dt_Distribucion;

/**
 * Servlet implementation class Sl_GestionDistribucion
 */
@WebServlet("/Sl_GestionDistribucion")
public class Sl_GestionDistribucion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionDistribucion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		int idDistribucion =0;
		idDistribucion = Integer.parseInt(request.getParameter("idD"));
		Dt_Distribucion dtr = new Dt_Distribucion();
		
		if(dtr.eliminarDistribucion(idDistribucion)) {
        	response.sendRedirect("GestionDistribucion.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionDistribucion.jsp?msj=6");
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
		
		//CONSTRUIR EL OBJETO 
		Dt_Distribucion dtf = new Dt_Distribucion();
		Distribucion ds = new Distribucion();
		Ng_Distribucion ngd = new Ng_Distribucion();
		
		ds.setNombre(request.getParameter("txtNombreDistribucion"));
		ds.setDescripcion(request.getParameter("txtDescripcionDistribucion"));
		ds.setPaisID(Integer.parseInt(request.getParameter("txtNombrePais")));
		
		//Variables de control
		String nombre = request.getParameter("txtNombreDistribucion");
		String desc = request.getParameter("txtDescripcionDistribucion");
		
		if(nombre.trim().isEmpty() || desc.trim().isEmpty()){
        	response.sendRedirect("GestionDistribucion.jsp?msj=2");					
		}
		else{
		//Setear info a objeto
		ds.setNombre(nombre);
		ds.setDescripcion(desc);
		
		
		switch (opc){
		case 1:{
			
		        try {
		        	if(ngd.existeDistribucion(ds.getNombre())){
			        	response.sendRedirect("GestionDistribucion.jsp?msj=existe");
			        }
			        else {	
			        if(	dtf.guardarDistribucion(ds)) {
			        	response.sendRedirect("GestionDistribucion.jsp?msj=1");
			        }
			        else {
			        	response.sendRedirect("GestionDistribucion.jsp?msj=2");
			        }
			      }			        	
		        }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionDistribucion, el error es: " + e.getMessage());
					e.printStackTrace();
		        }
		        
				break;
			}
		case 2:{
			ds.setDistribucionID(Integer.parseInt(request.getParameter("iddistribucion")));
     		try {
     			if(ngd.existeActualizarDistribucion(ds.getDistribucionID(),ds.getNombre())){
		          	response.sendRedirect("GestionDistribucion.jsp?msj=existe");
		        }else {
				   if(dtf.modificarDistribucion(ds)) {
		        	response.sendRedirect("GestionDistribucion.jsp?msj=3");
		        }
		        else {
		        	response.sendRedirect("GestionDistribucion.jsp?msj=4");
		        }
		        
		        }
	        }
	        catch(Exception e) {
	        	System.out.println("Sl_GestionDistribucion, el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;				
			}
		default:
			response.sendRedirect("GestionDistribucion.jsp?msj=7");	
			break;
		}
		
		}
		}
}
