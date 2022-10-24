package servlets;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import entidades.Arbol;
import datos.Dt_Arbol;
import negocio.Ng_Arbol;
/**
 * Servlet implementation class Sl_GestionArbol
 */
@WebServlet("/Sl_GestionArbol")
public class Sl_GestionArbol extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionArbol() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		int idArbol =0;
		idArbol = Integer.parseInt(request.getParameter("idA"));
		Dt_Arbol dta = new Dt_Arbol();
		
		if(dta.eliminarArbol(idArbol)) {
        	response.sendRedirect("GestionArbol.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionArbol.jsp?msj=6");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				
		//CONSTRUIR EL OBJETO ARBOL
		Dt_Arbol dta = new Dt_Arbol();
		Arbol ar = new Arbol();
		Ng_Arbol nga = new Ng_Arbol();
		
		int opc = 0;
		int arbolid =0;
		String txtNombreComun = null;
		String txtNombreCientifico = null;
		String txtDescripcionArbol = null;
		String GeneroID = null;
		String FamiliaID = null;
		String FloracionID = null;
		String rutaFichero = null;
		String usuarioid= null;
		String url_foto = null;	
		
		//Controlador
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
					}else if(key.equals("arbolid")){
						arbolid = Integer.parseInt(valor);
					}else if(key.equals("txtNombreComun")){
						txtNombreComun = valor;
					}else if(key.equals("txtNombreCientifico")){
						txtNombreCientifico = valor;
					}else if(key.equals("txtDescripcionArbol")){
						txtDescripcionArbol = valor;
					}else if(key.equals("GeneroID")){
						GeneroID = valor;
					}else if(key.equals("FamiliaID")){
						FamiliaID = valor;				
					}else if(key.equals("FloracionID")){
						FloracionID = valor;
					}else if(key.equals("url_foto")){
						url_foto = valor;
					}else if(key.equals("usuarioid")){
						usuarioid = valor;
					}					
				}
			}
			if(txtNombreComun.trim().isEmpty() || txtNombreCientifico.trim().isEmpty() || txtDescripcionArbol.trim().isEmpty()){
	        	response.sendRedirect("GestionArbol.jsp?msj=2");			
			}else{
				control = true;
			}

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
				    valorImagen = arbolid; 
					
					if(formatos.contains(uploaded.getContentType()))
					{
						System.out.println("Filetype: "+uploaded.getContentType());
						
						rutaFichero = "fotosArbol"+valorImagen+".jpg";
						path = "C:\\payara5\\glassfish\\fotosArbolAbra\\";
						System.out.println(path+rutaFichero);
						
						fichero = new File(path+rutaFichero);
						System.out.println(path+rutaFichero);
						
						///////// GUARDAR EN EL SERVIDOR //////////////
						uploaded.write(fichero);
						
						System.out.println("SERVIDOR: FOTO GUARDADA CON EXITO!!!");
						/////// ACTUALIZAMOS EL CAMPO URLFOTO EN LA BASE DE DATOS
						String url = "fotosArbolAbra/"+rutaFichero;
						ar.setMultimedia(url);
					}
					else
					{
						System.out.println("SERVIDOR: VERIFIQUE QUE EL ARCHIVO CUMPLA CON LAS ESPECIFICACIONES REQUERIDAS!!!");
						response.sendRedirect("GestionArbol.jsp?msj=2");
						control = false;
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
		ar.setNombreComun(txtNombreComun);
		ar.setNombreCientifico(txtNombreCientifico);
		ar.setDescripcion(txtDescripcionArbol);
		ar.setGeneroID(Integer.parseInt(GeneroID));		
		ar.setFamiliaID(Integer.parseInt(FamiliaID));
		ar.setFloracionID(Integer.parseInt(FloracionID));
		ar.setUsuarioId(Integer.parseInt(usuarioid));
		if(ar.getMultimedia()==null){
			ar.setMultimedia(url_foto);
		}
	
		switch (opc){
		case 1:{
				//PARA GUARDAR LA FECHA Y HORA DE CREACION
		        Date fechaSistema = new Date();
		        ar.setFcreacion(new java.sql.Timestamp(fechaSistema.getTime()));			  
	            try {
	            	if(nga.existeArbol(ar.getNombreComun())){
		        	   	response.sendRedirect("GestionArbol.jsp?msj=existe");	
	            	}else {
	            		if(nga.existeNombre(ar.getNombreCientifico())){
			        	   	response.sendRedirect("GestionArbol.jsp?msj=existe");	
		            	}else {	
			        if(	dta.guardarArbol(ar)) {
			        	response.sendRedirect("GestionArbol.jsp?msj=1");
			        }
			        else {
			        	response.sendRedirect("GestionArbol.jsp?msj=2");
			        }	  
		          	}
	            	}
	            }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionArbol, el error es: " + e.getMessage());
					e.printStackTrace();
		        }  
				break;
			}
		case 2:{
			try {
				ar.setArbolID(arbolid);
				if(nga.existeActualizarArbol(ar.getArbolID(),ar.getNombreComun())){
		          	response.sendRedirect("GestionArbol.jsp?msj=existe");
		        }else {
		        	if(nga.existeActualizarNombre(ar.getArbolID(),ar.getNombreCientifico())){
			          	response.sendRedirect("GestionArbol.jsp?msj=existe");
			        }else {
		        if(dta.modificarArbol(ar)) {
		        	response.sendRedirect("GestionArbol.jsp?msj=3");
		        }
		        else {
		        	response.sendRedirect("GestionArbol.jsp?msj=4");
		        }        
				}
		        }
			}
	        catch(Exception e) {
	        	System.out.println("Sl_GestionArbol, el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;
				
			}
		
		default:
			response.sendRedirect("GestionArbol.jsp?msj=5");	
			break;
		 }
	   }
	}
}
