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
import negocio.Ng_Publicacion;

/**
 * Servlet implementation class Sl_GestionPublicacion
 */
@WebServlet("/Sl_GestionPublicacion")
public class Sl_GestionPublicacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionPublicacion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int idPublicacion =0;
		idPublicacion = Integer.parseInt(request.getParameter("idP"));
		Dt_Publicacion dts = new Dt_Publicacion();
		
		if(dts.eliminarPublicacion(idPublicacion)) {
        	response.sendRedirect("GestionPublicacion.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionPublicacion.jsp?msj=6");
        }	response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				//Construir objeto Banner
				Dt_Publicacion dtp = new Dt_Publicacion();
				Publicacion post = new Publicacion();
				Ng_Publicacion  ngp = new Ng_Publicacion();
				
				int opc = 0;
				int publicacionid = 0;
				String txtTituloPost = null;
				String txtDescripcionPost = null;
				int cbxEstadoPost = 0;
				int usuarioid = 0;		
				
				opc = Integer.parseInt(request.getParameter("opcion"));
				txtTituloPost = request.getParameter("txtTituloPost");
				txtDescripcionPost = request.getParameter("txtDescripcionPost");
				cbxEstadoPost = Integer.parseInt(request.getParameter("cbxEstadoPost"));
				usuarioid  = Integer.parseInt(request.getParameter("usuarioid"));
				
				
				if(txtTituloPost.trim().isEmpty()|| txtDescripcionPost.trim().isEmpty()){
		        	response.sendRedirect("GestionPublicacion.jsp?msj=2");
				}else{ 
				post.setTitulo(txtTituloPost);
				post.setDescripcion(txtDescripcionPost);
				post.setEstadopublicacion(cbxEstadoPost);
				post.setUsuarioid(usuarioid);			
					
				switch (opc){
				case 1:{						
						try {
				        	//PARA GUARDAR LA FECHA Y HORA DE MODIFICACION
					        Date fechaSistema = new Date();
					        post.setFcreacion(new java.sql.Timestamp(fechaSistema.getTime()));
					        System.out.println("Post.getFechaCreacion(): "+post.getFcreacion());
					        if(ngp.existePublacion(post.getTitulo())){
					        	response.sendRedirect("GestionPublicacion.jsp?msj=existe");							
							}else{					        
					        if(dtp.guardarPost(post)) {
					        	response.sendRedirect("GestionPublicacion.jsp?msj=1");
					        }
					        else {
					        	response.sendRedirect("GestionPublicacion.jsp?msj=2");
					        }	
						  }
				        }	
				        catch(Exception e) {
				        	System.out.println("Sl_GestionPublicacion, el error es: " + e.getMessage());
							e.printStackTrace();
				        }
				        
						break;
					}
					case 2:{
						post.setPublicacionid(Integer.parseInt(request.getParameter("publicacionid")));
						//PARA GUARDAR LA FECHA Y HORA DE MODIFICACION
				        Date fechaSistema = new Date();
				        post.setFmodificacion(new java.sql.Timestamp(fechaSistema.getTime()));
						try {
							if(ngp.existeActualizarPublicacion(post.getPublicacionid(), post.getTitulo())){
					        	response.sendRedirect("GestionPublicacion.jsp?idP="+publicacionid+"&msj=existe");
				    		}else {
							   if(dtp.modificarPublicacion(post)) {
					        	response.sendRedirect("GestionPublicacion.jsp?msj=3");
					        }
					        else {
					        	response.sendRedirect("GestionPublicacion.jsp?msj=4");
					        }
				    		}
				        }
			        catch(Exception e) {
			        	System.out.println("Sl_GestionPublicacion el error es: " + e.getMessage());
						e.printStackTrace();
			        }
						break;						
					}
					default:
					response.sendRedirect("GestionPublicacion.jsp?msj=5");	
					break;
			}//Fin Switch
		  }
  }
}

