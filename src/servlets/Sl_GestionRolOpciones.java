package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datos.Dt_RolOpcion;
import entidades.RolOpcion;
import negocio.Ng_RolOpcion;

/**
 * Servlet implementation class Sl_GestionRolOpciones
 */
@WebServlet("/Sl_GestionRolOpciones")
public class Sl_GestionRolOpciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionRolOpciones() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		int idRolOpc =0;
		idRolOpc = Integer.parseInt(request.getParameter("rolopcID"));
		Dt_RolOpcion dtru = new Dt_RolOpcion(); 
		
		if(dtru.eliminaRO(idRolOpc)) {
        	response.sendRedirect("GestionRolOpcion.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionRolOpcion.jsp?msj=6");
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
		RolOpcion ro = new RolOpcion();		
		Dt_RolOpcion dtro = new Dt_RolOpcion(); 
		Ng_RolOpcion ngp = new Ng_RolOpcion();
		
		ro.setId_opc(Integer.parseInt(request.getParameter("cbxOpc")));

		switch (opc){
		case 1:{
		        try {
		    		ro.setRolid(Integer.parseInt(request.getParameter("cbxRol")));
		        	if(ngp.existeOpcionAsignada(ro.getRolid(), ro.getId_opc())){
			        	response.sendRedirect("FormRolOpcion.jsp?msj=existe");
			        }
			        else {			      
			        if(dtro.guardarRolOpc(ro)) {
			        	response.sendRedirect("GestionRolOpcion.jsp?msj=1");
			        }
			        else {
			        	response.sendRedirect("GestionRolOpcion.jsp?msj=2");
			        }
			       }        	
		        }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionRolOpcion, el error es: " + e.getMessage());
					e.printStackTrace();
		        }
				break;
			}
		
		case 2:{
				
			try {
				ro.setRolid(Integer.parseInt(request.getParameter("cbxBRol")));				
	        	ro.setIdrol_opc(Integer.parseInt(request.getParameter("rolOpcionid")))	;
	        	if(ngp.existeActualizarRolOpcion(ro.getIdrol_opc(),ro.getRolid(),ro.getId_opc() )){
		          	response.sendRedirect("FormEditarRolOpcion.jsp?rolopcID="+ro.getIdrol_opc()+"&msj=existe");
		        }
	        	else{
	        	if(dtro.modificarRolOpcion(ro)) {
		        	response.sendRedirect("GestionRolOpcion.jsp?msj=3");
		        }
		        else {
		        	response.sendRedirect("GestionRolOpcion.jsp?msj=4");
		        }
	        	}
	        }
	        catch(Exception e) {
	        	System.out.println("Sl_GestionRolOpciones, el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;
				
			}
		default:
			response.sendRedirect("GestionRolOpciones.jsp?msj=5");	
			break;
	}
  }
}
