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

import entidades.Home;
import datos.Dt_Home;
/**
 * Servlet implementation class Sl_GestionInicio
 */


@WebServlet("/Sl_GestionInicio")
public class Sl_GestionInicio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionInicio() {
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
					
		Dt_Home dth = new Dt_Home();
		Home hm = new Home();
		
		int opc = 0;
		String idInicio = null;
		String idUsuario = null;
		String descripcionHis = null;
		String descripcionMis = null;
		String descripcionVis = null;
		String rutaFichero = null;
		String multHistoria = null;
		String multMision = null;
		String multVision = null;
		
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
					}
					else if(key.equals("idInicio")){
						idInicio = valor;
					}else if(key.equals("idUsuario")){
						idUsuario = valor;
					}else if(key.equals("descripcionHis")){
						descripcionHis = valor;
					}else if(key.equals("descripcionMis")){
						descripcionMis = valor;
					}
					else if(key.equals("descripcionVis")){
						descripcionVis = valor;
					}
					else if(key.equals("urlfotoHistoria")){
						multHistoria = valor;
					}
					else if(key.equals("urlfotoMision")){
						multMision = valor;
					}else if(key.equals("urlfotoVision")){
						multVision = valor;
					}
				}
			}
			
			int valorImagen = 0;
			String urls = null;
			
			//Validar si los campos estan vacios
			if(idInicio.trim().isEmpty()|| idUsuario.trim().isEmpty() || descripcionHis.trim().isEmpty() || descripcionMis.trim().isEmpty()|| descripcionVis.trim().isEmpty()){
	        	response.sendRedirect("GestionInicio.jsp?msj=2");			
			}else{
				control = true;
			}
		
			if(control){
			for(FileItem item : items )
			{ 
				FileItem uploaded = item;
				
				if(uploaded.getName()!=""){
				if(!uploaded.isFormField())
				{
					String urlFotos = uploaded.getFieldName();
					
					if(urlFotos.equals("multHistoria")){
						valorImagen = 1;
					}
					if(urlFotos.equals("multMision")){
						valorImagen = 2;						
					}
					if(urlFotos.equals("multVision")){
						valorImagen = 3;
					}
																							
					/////////TAMAÑO DEL ARCHIVO ////////
					long size = uploaded.getSize();
					System.out.println("size: "+size);
					
					/////// GUARDAMOS EN UN ARREGLO LOS FORMATOS QUE SE DESEAN PERMITIR
					List<String> formatos = Arrays.asList("image/jpeg");
					
					////// COMPROBAR SI EL TAMAÑO Y FORMATO SON PERMITIDOS //////////
					
					if(formatos.contains(uploaded.getContentType()))
					{
						System.out.println("Filetype: "+uploaded.getContentType());
						
						rutaFichero = "fotosInicio"+valorImagen+".jpg";
						path = "C:\\payara5\\glassfish\\fotosInicioAbra\\";
						System.out.println(path+rutaFichero);
						
						fichero = new File(path+rutaFichero);
						System.out.println(path+rutaFichero);
						
						///////// GUARDAR EN EL SERVIDOR //////////////
						uploaded.write(fichero);
						
						System.out.println("SERVIDOR: FOTO GUARDADA CON EXITO!!!");
						/////// ACTUALIZAMOS EL CAMPO URLFOTO EN LA BASE DE DATOS
						String url = "fotosInicioAbra/"+rutaFichero;
						urls =url;
						
						if(urlFotos.equals("multHistoria")){
							hm.setImg_historia(urls);
						}else if(urlFotos.equals("multMision")){
							hm.setImg_mision(urls);		
						}else if(urlFotos.equals("multVision")){
							hm.setImg_vision(urls);
						}						
						}
					else
					{	
						//url_foto = logoMultimedia;
						System.out.println("SERVIDOR: VERIFIQUE QUE EL ARCHIVO CUMPLA CON LAS ESPECIFICACIONES REQUERIDAS!!!");
						response.sendRedirect("GestionInicio.jsp?msj=2");
						control=false;
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
		//Setear valores al objeto para guardar en la bd
		hm.setHomeID(Integer.parseInt(idInicio));
		hm.setHistoria(descripcionHis);
		hm.setMision(descripcionMis);
		hm.setVision(descripcionVis);
		hm.setUsuarioID(Integer.parseInt(idUsuario));
		
		if(hm.getImg_historia() == null){
			hm.setImg_historia(multHistoria);
		}
		if(hm.getImg_mision() == null) {
			hm.setImg_mision(multMision);
		}
		if(hm.getImg_vision() == null){
			hm.setImg_vision(multVision);
		}
						
			switch(opc) {
			case 1:{				
				try {
					Date fechaSistema = new Date();
				    hm.setFmodificacion(new java.sql.Timestamp(fechaSistema.getTime()));						     		    
			        if(dth.modificarInicio(hm)) {
			        	response.sendRedirect("GestionInicio.jsp?msj=1");
			        }
			        else {
			        	response.sendRedirect("GestionInicio.jsp?msj=2");
			        }     	
		        }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionInicio, el error es: " + e.getMessage());
					e.printStackTrace();
		        }
					break;					
				}			
			default:
				response.sendRedirect("GestionIncio.jsp?msj=2");	
				break;
		}
	}
   }
}
