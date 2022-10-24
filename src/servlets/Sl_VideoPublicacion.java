package servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import datos.Dt_Publicacion;
import entidades.Publicacion;

/**
 * Servlet implementation class Sl_VideoPublicacion
 */
@WebServlet("/Sl_VideoPublicacion")
public class Sl_VideoPublicacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_VideoPublicacion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Construir objeto Banner
		Dt_Publicacion dtp = new Dt_Publicacion();
		Publicacion post = new Publicacion();
		
		int opc = 0;
		String videoUrl = null;
		
		opc = Integer.parseInt(request.getParameter("opcion"));
		videoUrl = request.getParameter("urlVideo");
		
		if(videoUrl.trim().isEmpty()){
        	response.sendRedirect("GestionPublicacion.jsp?msj=2");
		}else{ 
		post.setMultimedia3(videoUrl);

		switch (opc){		
			case 2:{
				post.setPublicacionid(Integer.parseInt(request.getParameter("idpublicacion")));				
				//PARA GUARDAR LA FECHA Y HORA DE MODIFICACION
		        Date fechaSistema = new Date();
		        post.setFmodificacion(new java.sql.Timestamp(fechaSistema.getTime()));
				try {
					   if(dtp.guardarVideoPublicacion(post.getPublicacionid(),post.getMultimedia3())) {
			        	response.sendRedirect("GestionPublicacion.jsp?msj=3");
			        }
			        else {
			        	response.sendRedirect("GestionPublicacion.jsp?msj=4");
			        }        		        	
		        }
	        catch(Exception e) {
	        	System.out.println("Sl_VideoPublicacion el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;						
			}
			default:
			response.sendRedirect("GestionPublicacion.jsp?msj=5");	
			break;
			}	
		}
	}

}
