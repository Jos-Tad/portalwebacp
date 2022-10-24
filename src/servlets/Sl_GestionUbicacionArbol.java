package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datos.Dt_Genero;
import datos.Dt_UbicacionArbol;
import negocio.Ng_UbicacionArbol;
import entidades.UbicacionArbol;

/**
 * Servlet implementation class Sl_GestionUbicacionArbol
 */
@WebServlet("/Sl_GestionUbicacionArbol")
public class Sl_GestionUbicacionArbol extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionUbicacionArbol() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int idUbicacionArbol =0;
		idUbicacionArbol = Integer.parseInt(request.getParameter("idUA"));
		Dt_UbicacionArbol dtg = new Dt_UbicacionArbol();
		
		if(dtg.eliminarUbicacionArbol(idUbicacionArbol)) {
        	response.sendRedirect("GestionUbicacionArbol.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionUbicacionArbol.jsp?msj=6");
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
		Dt_UbicacionArbol dtua = new Dt_UbicacionArbol();
		UbicacionArbol ua = new UbicacionArbol();
		Ng_UbicacionArbol ngua = new Ng_UbicacionArbol();
		ua.setArbolid(Integer.parseInt(request.getParameter("cbxArbol")));
		ua.setDistribucionid(Integer.parseInt(request.getParameter("cbxDistribucion")));
		
		switch (opc){
		case 1:{			
		        try {		        	
		        	if(ngua.existeUbicacionArbol(ua.getArbolid(), ua.getDistribucionid()))
		        	{
		        		response.sendRedirect("GestionUbicacionArbol.jsp?msj=existe");									
		        	}else
			        if(dtua.guardarArbol(ua)) {
			        	response.sendRedirect("GestionUbicacionArbol.jsp?msj=1");
			        }
			        else {
			        	response.sendRedirect("GestionUbicacionArbol.jsp?msj=2");
			        }		        		        	
		        }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionUbicacionArbol, el error es: " + e.getMessage());
					e.printStackTrace();
		        }		        
				break;
			}
		default:
			response.sendRedirect("GestionUbicacionArbol.jsp?msj=7");	
			break;
		}
		
		
		
	}
}
