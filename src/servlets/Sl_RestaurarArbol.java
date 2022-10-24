package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datos.Dt_Arbol;

/**
 * Servlet implementation class Sl_RestaurarArbol
 */
@WebServlet("/Sl_RestaurarArbol")
public class Sl_RestaurarArbol extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_RestaurarArbol() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int idArbol =0;
		idArbol = Integer.parseInt(request.getParameter("idA"));
		Dt_Arbol dta = new Dt_Arbol();
		
		if(dta.restaurarArbol(idArbol)) {
        	response.sendRedirect("RestaurarArbol.jsp?msj=1");
        }
        else {
        	response.sendRedirect("RestaurarArbol.jsp?msj=2");
        }	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		///doGet(request, response);
	}

}
