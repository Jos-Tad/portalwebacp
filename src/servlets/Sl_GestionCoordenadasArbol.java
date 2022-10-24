package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datos.Dt_CoordenadaArbol;
import entidades.CoordenadaArbol;
import negocio.Ng_Mapa;

/**
 * Servlet implementation class Sl_GestionCoordenadasArbol
 */
@WebServlet("/Sl_GestionCoordenadasArbol")
public class Sl_GestionCoordenadasArbol extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionCoordenadasArbol() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int idCoordenadaArbol=0;
		idCoordenadaArbol = Integer.parseInt(request.getParameter("idCoAr"));
		Dt_CoordenadaArbol dtca = new Dt_CoordenadaArbol(); 
		
		if(dtca.eliminarCoordenadaArbol(idCoordenadaArbol)) {
        	response.sendRedirect("GestionMapa.jsp?msj=3");
        }
        else {
        	response.sendRedirect("GestionMapa.jsp?msj=4");
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
		Ng_Mapa ngm = new Ng_Mapa();

		//CONSTRUIR EL OBJETO ROL-USER
		CoordenadaArbol coa = new CoordenadaArbol();		
		Dt_CoordenadaArbol dtcoa = new Dt_CoordenadaArbol(); 
		
		coa.setCoordenadaid(Integer.parseInt(request.getParameter("cbxCoordenada")));
		coa.setArbolid(Integer.parseInt(request.getParameter("cbxArbol")));

		switch (opc){
		case 1:{
		        try {
		        	if(ngm.existeAsignacionCoordenadaArbol(coa.getCoordenadaid(), coa.getArbolid())){
			        	response.sendRedirect("FormAsignacionCoordenada.jsp?msj=existe");
			        }else if(ngm.existeArbolCor(coa.getCoordenadaid())) {
			        	response.sendRedirect("FormAsignacionCoordenada.jsp?msj=existe1");
			        }
		        	else {
			        if(dtcoa.guardarCoordenadaArbol(coa)) {
			        	response.sendRedirect("GestionMapa.jsp?msj=1");
			        }
			        else {
			        	response.sendRedirect("GestionMapa.jsp?msj=2");
			        }
		        	}
		        }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionCoordenadasArbol, el error es: " + e.getMessage());
					e.printStackTrace();
		        }
				break;
			}
		default:
			response.sendRedirect("GestionMapa.jsp?msj=5");	
			break;
		
  }
 }
}

