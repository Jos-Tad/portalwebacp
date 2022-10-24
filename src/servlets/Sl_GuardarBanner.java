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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.util.Date;

import entidades.Banner;
import negocio.Ng_Banner;
import datos.Dt_Banner;

/**
 * Servlet implementation class Sl_GestionBanner
 */
@WebServlet("/Sl_GuardarBanner")
public class Sl_GuardarBanner extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GuardarBanner() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Construir Objetos
		Dt_Banner dtb = new Dt_Banner();
		Banner bn = new Banner();
		Ng_Banner ngb = new Ng_Banner();
		
		int opc = 0;
		String posicion = null;
		String txtTituloBanner = null;
		String txtDescripcionBanner = null;
		String usuarioid = null;
		String rutaFichero = null;
		
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
					else if(key.equals("posicion")){
						posicion = valor;
					}else if(key.equals("txtTituloBanner")){
						txtTituloBanner = valor;
					}else if(key.equals("txtDescripcionBanner")){
						txtDescripcionBanner = valor;
					}else if(key.equals("usuarioid")){
						usuarioid = valor;
					}					
				}
			}
			
			//Validar si los campos estan vacios
			if(txtTituloBanner.trim().isEmpty() || txtDescripcionBanner.trim().isEmpty()){
	        	response.sendRedirect("GestionBanner.jsp?msj=2");			
			}else{
				control = true;
			}
			
			if(control){
			for(FileItem item : items)
			{
				FileItem uploaded = item;
				if(!uploaded.isFormField())
				{
					/////////TAMAÑO DEL ARCHIVO ////////
					long size = uploaded.getSize();
					System.out.println("size: "+size);
					
					/////// GUARDAMOS EN UN ARREGLO LOS FORMATOS QUE SE DESEAN PERMITIR
					List<String> formatos = Arrays.asList("image/jpeg");
					
					////// COMPROBAR SI EL TAMAÑO Y FORMATO SON PERMITIDOS //////////
					int valorPosicion = Integer.parseInt(posicion) + 1; 		
					
					if(formatos.contains(uploaded.getContentType()))
					{
						System.out.println("Filetype: "+uploaded.getContentType());
						
						rutaFichero = "fotosBanner"+valorPosicion+".jpg";
						path = "C:\\payara5\\glassfish\\fotosBannerAbra\\";
						System.out.println(path+rutaFichero);
						
						fichero = new File(path+rutaFichero);
						System.out.println(path+rutaFichero);
						
						///////// GUARDAR EN EL SERVIDOR //////////////
						uploaded.write(fichero);
						
						System.out.println("SERVIDOR: FOTO GUARDADA CON EXITO!!!");
						/////// ACTUALIZAMOS EL CAMPO URLFOTO EN LA BASE DE DATOS
						String url = "fotosBannerAbra/"+rutaFichero;
						
						//Setear el url foto
						bn.setMultimedia(url);									
					}
					else
					{
						System.out.println("SERVIDOR: VERIFIQUE QUE EL ARCHIVO CUMPLA CON LAS ESPECIFICACIONES REQUERIDAS!!!");
						response.sendRedirect("GestionBanner.jsp?msj=2");
						control=false;
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
			bn.setPosicion(Integer.parseInt(posicion));
			bn.setTitulobanner(txtTituloBanner);
			bn.setDescripcion(txtDescripcionBanner);
			bn.setUsuarioID(Integer.parseInt(usuarioid));
			
			//Opciones
			switch (opc){
			case 1:{
				       try {
				    	Date fechaSistema = new Date();
					    bn.setFcreacion(new java.sql.Timestamp(fechaSistema.getTime()));						 
			        	if(ngb.existeBanner(bn.getTitulobanner())) {
			   	        	response.sendRedirect("FormBanner.jsp?msj=existe");
			   	        }
			   	        else {
						
				        if(dtb.guardarBanner(bn)) {
				        	response.sendRedirect("GestionBanner.jsp?msj=1");
				        }
				        else {
				        	response.sendRedirect("GestionBanner.jsp?msj=2");
				        } 
			   	        }
			        }			        
			        catch(Exception e) {
			        	System.out.println("Sl_GestionBanner, el error es: " + e.getMessage());
						e.printStackTrace();
			        }
			        
					break;
	    		}					
				default:
					response.sendRedirect("GestionBanner.jsp?msj=7");	
					break;
			}
		}
	}	
}
