package servlets;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import datos.Dt_TipoProducto;
import entidades.TipoProducto;
import negocio.Ng_TipoProducto;

/**
 * Servlet implementation class Sl_GestionTipoProducto
 */
@WebServlet("/Sl_GestionTipoProducto")
public class Sl_GestionTipoProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionTipoProducto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		int idTipoProducto =0;
		idTipoProducto = Integer.parseInt(request.getParameter("idTp"));
		Dt_TipoProducto dtp = new Dt_TipoProducto();
		
		if(dtp.eliminarTipoProducto(idTipoProducto)) {
        	response.sendRedirect("GestionTipoProductos.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionTipoProductos.jsp?msj=6");
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
				int tipoproductoid =0;
				opc = Integer.parseInt(request.getParameter("opcion"));
				
				
				//Construir objeto estudiante
				Dt_TipoProducto dttp = new Dt_TipoProducto();
				TipoProducto tp = new TipoProducto();
				Ng_TipoProducto ngtp = new Ng_TipoProducto();
				
				
				//Variables de control
				String nombre = request.getParameter("nombreTipoProducto");
				String desc = request.getParameter("descripcionTipoProducto");
				
				if(nombre.trim().isEmpty() || desc.trim().isEmpty()){
		        	response.sendRedirect("GestionTipoProductos.jsp?msj=2");					
				}
				else{
				//Setear info a objeto
				tp.setNombre(nombre);
				tp.setDescripcion(desc);
				
				switch (opc){
				case 1:{					
				        try {
				        	if(ngtp.existeTipoProducto(tp.getNombre()))
				        	{
				        	   	response.sendRedirect("FormTipoProducto.jsp?msj=existe");									
				        	}else {
				        	
				        	if(dttp.guardarTipoProducto(tp)) {
					        	response.sendRedirect("GestionTipoProductos.jsp?msj=1");
					        }
					   
					         else {
					        	response.sendRedirect("GestionTipoProductos.jsp?msj=2");
					        }	
				        	}
				        }
				        catch(Exception e) {
				        	System.out.println("Sl_GestionTipoProducto, el error es: " + e.getMessage());
							e.printStackTrace();
				        }				        
						break;
					}
				case 2:{
					tipoproductoid = Integer.parseInt(request.getParameter("idTipoProducto"));
					tp.setTipoproducotid(tipoproductoid);
		     		try {
		     			if(ngtp.existeActualizarTipoProducto(tp.getTipoproductoid(), tp.getNombre())){
				        	response.sendRedirect("FormEditarTipoProducto.jsp?idTp="+tipoproductoid+"&msj=existe");
			    		}else {   
		     			if(dttp.modificarTipoProducto(tp)) {
				        	response.sendRedirect("GestionTipoProductos.jsp?msj=3");
				        }
				        else {
				        	response.sendRedirect("GestionTipoProductos.jsp?msj=4");
				        }
			    		}
			        }
			        catch(Exception e) {
			        	System.out.println("Sl_GestionTipoProducto, el error es: " + e.getMessage());
						e.printStackTrace();
			        }
						break;
					
					}
				default:
					response.sendRedirect("GestionTipoProducto.jsp?msj=7");	
					break;
				}
		}
	}
}
