package servlets;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import entidades.Servicio;
import negocio.Ng_Servicio;
import datos.Dt_Servicio;

/**
 * Servlet implementation class Sl_GestionServicio
 */
@WebServlet("/Sl_GestionServicio")
public class Sl_GestionServicio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionServicio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int idServicio =0;
		idServicio = Integer.parseInt(request.getParameter("idS"));
		Dt_Servicio dts = new Dt_Servicio();
		
		if(dts.eliminarServicio(idServicio)) {
        	response.sendRedirect("GestionServicio.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionServicio.jsp?msj=6");
        }	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Dt_Servicio dts = new Dt_Servicio();
		Ng_Servicio ngs = new Ng_Servicio();
		Servicio sr = new Servicio();		
		
		int opc = 0;
		int servicioid =0;
		String nombreServicio = null;
		String descripcionServicio = null;
		String cbxEstadoServicio = null;
		String rutaFichero = null;
		String usuarioid = null;		
		String url_foto = null;
		boolean control = false;
		
		try
		{
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			String path = getServletContext().getRealPath("/");
			List<FileItem> items = upload.parseRequest(request);
			File fichero = null;
					
			for(FileItem item: items)
			{
				FileItem uploaded = item;
				if(uploaded.isFormField())
				{	
					String key = uploaded.getFieldName();
					String valor = uploaded.getString();
					if(key.equals("opcion")){
						opc = Integer.parseInt(valor);
					}else if(key.equals("servicioid")){
						servicioid = Integer.parseInt(valor);
					}else if(key.equals("nombreServicio")){
						nombreServicio = valor;
					}else if(key.equals("descripcionServicio")){
						descripcionServicio = valor;
					}else if(key.equals("cbxEstadoServicio")){
						cbxEstadoServicio = valor;
					}else if(key.equals("url_foto")){
						url_foto = valor;
					}else if(key.equals("usuarioid")){
						usuarioid = valor;
					}					
				}
			}
			
			if(nombreServicio.trim().isEmpty() || descripcionServicio.trim().isEmpty()){
				response.sendRedirect("GestionServicio.jsp?msj=2"); 
			}else {
				control = true;
			}
			if(control){
			int valorImagen = 0;
			for(FileItem item : items)
			{
			
				FileItem uploaded = item;
				if(uploaded.getName()!=""){					
				if(!uploaded.isFormField())
				{
					/////////TAMAÑO DEL ARCHIVO ////////
					long size = uploaded.getSize();
					System.out.println("size: "+size);
					
					/////// GUARDAMOS EN UN ARREGLO LOS FORMATOS QUE SE DESEAN PERMITIR
					List<String> formatos = Arrays.asList("image/jpeg");
					
					////// COMPROBAR SI EL TAMAÑO Y FORMATO SON PERMITIDOS //////////
				    valorImagen = servicioid; 
					
					if(formatos.contains(uploaded.getContentType()))
					{
						System.out.println("Filetype: "+uploaded.getContentType());
						
						rutaFichero = "fotosServicio"+valorImagen+".jpg";
						path = "C:\\payara5\\glassfish\\fotosServicioAbra\\";
						System.out.println(path+rutaFichero);
						
						fichero = new File(path+rutaFichero);
						System.out.println(path+rutaFichero);
						
						///////// GUARDAR EN EL SERVIDOR //////////////
						uploaded.write(fichero);
						
						System.out.println("SERVIDOR: FOTO GUARDADA CON EXITO!!!");
						/////// ACTUALIZAMOS EL CAMPO URLFOTO EN LA BASE DE DATOS
						String url = "fotosServicioAbra/"+rutaFichero;
						sr.setMultimedia(url);
					}
					else
					{
						System.out.println("SERVIDOR: VERIFIQUE QUE EL ARCHIVO CUMPLA CON LAS ESPECIFICACIONES REQUERIDAS!!!");
						response.sendRedirect("GestionServicio.jsp?msj=2");
						control = false;
					}
				  }
			   }
			}
		  }
		}
		catch(Exception e)
		{
			System.out.println("SERVLET: ERROR AL SUBIR LA FOTO: " + e.getMessage());
		}
		
		if(control){
		//Setear Objeto con sus valores
		sr.setNombre(nombreServicio);
		sr.setDescripcion(descripcionServicio);
		sr.setEstadoservicio(Integer.parseInt(cbxEstadoServicio));
		sr.setUsuarioid(Integer.parseInt(usuarioid));
			if(sr.getMultimedia()==null){
			sr.setMultimedia(url_foto);
		}
		
		switch (opc){
		case 1:{	
		        try {
			        if(ngs.existeServicio(sr.getNombre())) {
			        	response.sendRedirect("FormServicio.jsp?msj=existe");
			        }
			        else {
		        	//PARA GUARDAR LA FECHA Y HORA DE CREACION
			        Date fechaSistema = new Date();
			        sr.setFcreacion(new java.sql.Timestamp(fechaSistema.getTime()));			  
		        	if(dts.guardarServicio(sr)) {
			        	response.sendRedirect("GestionServicio.jsp?msj=1");
			        }
			   
			         else {
			        	response.sendRedirect("GestionServicio.jsp?msj=2");
			        }		        	
		        }
		        }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionServicio, el error es: " + e.getMessage());
					e.printStackTrace();
		        }    
				break;
			}
			case 2:{
				sr.setServicioid(servicioid);
				//PARA GUARDAR LA FECHA Y HORA DE MODIFICACION
		        Date fechaSistema = new Date();
		        sr.setFmodificacion(new java.sql.Timestamp(fechaSistema.getTime()));
				try {
					if(ngs.existeServicioActualizar(sr.getServicioid(), sr.getNombre())){
			          	response.sendRedirect("FormEditarServicio.jsp?idS="+sr.getServicioid()+"&msj=existe");

		        	}else {
					   if(dts.modificarServicio(sr)) {
			        	response.sendRedirect("GestionServicio.jsp?msj=3");
			        }
			        else {
			        	response.sendRedirect("GestionServicio.jsp?msj=4");
			        }
		        	}
		        }
	        catch(Exception e) {
	        	System.out.println("Sl_GestionServicio, el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;
				
			}
		default:
			response.sendRedirect("GestionServicio.jsp?msj=7");	
			break;
		}		
	}
  }
}
