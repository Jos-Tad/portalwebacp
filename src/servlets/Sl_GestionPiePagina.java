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

import datos.Dt_Footer;
import entidades.Footer;

/**
 * Servlet implementation class Sl_GestionPiePagina
 */
@WebServlet("/Sl_GestionPiePagina")
public class Sl_GestionPiePagina extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionPiePagina() {
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
		
		Dt_Footer dtf = new Dt_Footer();
		Footer ft = new Footer();
		
		int opc = 0;
		String FooterID = null;
		String UsuarioID = null;
		String direccionFooter = null;
		String correoFooter = null;
		String telefonoFooter = null;
		String extensionFooter = null;
		String rutaFichero = null;
		String logoMultimedia = null;
		
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
					else if(key.equals("idFooter")){
						FooterID = valor;
					}else if(key.equals("idUsuario")){
						UsuarioID = valor;
					}else if(key.equals("direccionFooter")){
						direccionFooter = valor;
					}else if(key.equals("correoFooter")){
						correoFooter = valor;
					}
					else if(key.equals("telefonoFooter")){
						telefonoFooter = valor;
					}
					else if(key.equals("extensionFooter")){
						extensionFooter = valor;
					}
					else if(key.equals("url_foto")){
						logoMultimedia = valor;
					}
				}
			}
			//Validar si los campos estan vacios
			if(FooterID.trim().isEmpty()|| UsuarioID.trim().isEmpty() || direccionFooter.trim().isEmpty() || correoFooter.trim().isEmpty()|| telefonoFooter.trim().isEmpty()||extensionFooter.trim().isEmpty()){
	        	response.sendRedirect("GestionPiePagina.jsp?msj=2");			
			}else{
				control = true;
			}
			if(control){
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
					List<String> formatos = Arrays.asList("image/png");
					
					////// COMPROBAR SI EL TAMAÑO Y FORMATO SON PERMITIDOS //////////
					int valorImagen = Integer.parseInt(FooterID); 
					
					if(formatos.contains(uploaded.getContentType()))
					{
						String urlFotos = uploaded.getFieldName();
						
						System.out.println("Filetype: "+uploaded.getContentType());
						
						rutaFichero = "fotosPiePagina"+valorImagen+".png";
						path = "C:\\payara5\\glassfish\\fotosPiePaginaAbra\\";
						System.out.println(path+rutaFichero);
						
						fichero = new File(path+rutaFichero);
						System.out.println(path+rutaFichero);
						
						///////// GUARDAR EN EL SERVIDOR //////////////
						uploaded.write(fichero);
						
						System.out.println("SERVIDOR: FOTO GUARDADA CON EXITO!!!");
						/////// ACTUALIZAMOS EL CAMPO URLFOTO EN LA BASE DE DATOS
						String url = "fotosPiePaginaAbra/"+rutaFichero;
						
						ft.setLogo(url);										
					}
					else
					{						
						System.out.println("SERVIDOR: VERIFIQUE QUE EL ARCHIVO CUMPLA CON LAS ESPECIFICACIONES REQUERIDAS!!!");
						response.sendRedirect("GestionPiePagina.jsp?msj=2");				
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
		ft.setFooterID(Integer.parseInt(FooterID));
		ft.setDescripcion(direccionFooter);
		ft.setCorreo(correoFooter);
		ft.setTelefono(telefonoFooter);	
		ft.setExtencion(extensionFooter);	
		ft.setUsuarioID(Integer.parseInt(UsuarioID));
		if(ft.getLogo()==null){
			ft.setLogo(logoMultimedia);
		}

    	switch(opc) {
		case 1:{
			
			try {
				Date fechaSistema = new Date();
			    ft.setFmodificacion(new java.sql.Timestamp(fechaSistema.getTime()));						     
		        if(dtf.modificarFooter(ft)) {
		        	response.sendRedirect("GestionPiePagina.jsp?msj=1");
		        }
		        else {
		        	response.sendRedirect("GestionPiePagina.jsp?msj=2");
		        }
		        	
	        	
	        }
	        catch(Exception e) {
	        	System.out.println("Sl_GestionPiePagina, el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;
				
			}
		
		default:
			response.sendRedirect("GestionPiePagina.jsp?msj=3");	
			break;
	}	
  }	
 }
}
