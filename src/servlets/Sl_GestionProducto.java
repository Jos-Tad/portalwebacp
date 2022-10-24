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

import datos.Dt_Producto;
import entidades.Producto;
import negocio.Ng_Producto;

/**
 * Servlet implementation class Sl_GestionProducto
 */
@WebServlet("/Sl_GestionProducto")
public class Sl_GestionProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionProducto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int idProducto =0;
		idProducto = Integer.parseInt(request.getParameter("idP"));
		Dt_Producto dtp = new Dt_Producto();
		
		if(dtp.eliminarProducto(idProducto)) {
        	response.sendRedirect("GestionProductos.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionProductos.jsp?msj=6");
        }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Dt_Producto dts = new Dt_Producto();
		Producto pr = new Producto();
		Ng_Producto ngp = new Ng_Producto();
				
		int opc = 0;
		int productoid =0;
		String nombreProducto = null;
		String descripcionProducto = null;
		String cbxEstadoProducto = null;
		String cbxTipoProducto = null;
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
					}else if(key.equals("productoid")){
						productoid = Integer.parseInt(valor);
					}else if(key.equals("nombreProducto")){
						nombreProducto = valor;
					}else if(key.equals("descripcionProducto")){
						descripcionProducto = valor;
					}else if(key.equals("cbxEstadoProducto")){
						cbxEstadoProducto = valor;
					}else if(key.equals("cbxTipoProducto")){
						cbxTipoProducto = valor;
					}else if(key.equals("url_foto")){
						url_foto = valor;
					}else if(key.equals("usuarioid")){
						usuarioid = valor;
					}					
				}
			}
			if(nombreProducto.trim().isEmpty()||descripcionProducto.trim().isEmpty()){
		      	response.sendRedirect("GestionProductos.jsp?msj=2");
			}else{
				control= true;
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
				    valorImagen = productoid; 
					
					if(formatos.contains(uploaded.getContentType()))
					{
						System.out.println("Filetype: "+uploaded.getContentType());
						
						rutaFichero = "fotosProducto"+valorImagen+".jpg";
						path = "C:\\payara5\\glassfish\\fotosProductoAbra\\";
						System.out.println(path+rutaFichero);
						
						fichero = new File(path+rutaFichero);
						System.out.println(path+rutaFichero);
						
						///////// GUARDAR EN EL SERVIDOR //////////////
						uploaded.write(fichero);
						
						System.out.println("SERVIDOR: FOTO GUARDADA CON EXITO!!!");
						/////// ACTUALIZAMOS EL CAMPO URLFOTO EN LA BASE DE DATOS
						String url = "fotosProductoAbra/"+rutaFichero;
						pr.setMultimedia(url);
					}
					else
					{
						System.out.println("SERVIDOR: VERIFIQUE QUE EL ARCHIVO CUMPLA CON LAS ESPECIFICACIONES REQUERIDAS!!!");
						response.sendRedirect("GestionProducto.jsp?msj="+valorImagen+"&guardado=3");						
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
		pr.setProducto(nombreProducto);
		pr.setDescripcion(descripcionProducto);
		pr.setEstadoproductoid(Integer.parseInt(cbxEstadoProducto));		
		pr.setTipoproductoid(Integer.parseInt(cbxTipoProducto));
		pr.setUsuarioid(Integer.parseInt(usuarioid));
			if(pr.getMultimedia()==null){
			pr.setMultimedia(url_foto);
		}
			
			
		switch (opc){
		case 1:{
				//PARA GUARDAR LA FECHA Y HORA DE CREACION
		        Date fechaSistema = new Date();
		        pr.setFcreacion(new java.sql.Timestamp(fechaSistema.getTime()));			  	     
		        try {
		        	if(ngp.existeProducto(pr.getProducto())){
		        	   	response.sendRedirect("FormProducto.jsp?msj=existe");						  
		        	}else {
		        		if(dts.guardarProducto(pr)){
				        	response.sendRedirect("GestionProductos.jsp?msj=1");
				        }
				   
				         else {
				        	response.sendRedirect("GestionProductos.jsp?msj=2");
				        }
		        	}
		        }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionProducto, el error es: " + e.getMessage());
					e.printStackTrace();
		        }
		        
				break;
			}
	   	case 2:{
			pr.setProductoid(productoid);
			//PARA GUARDAR LA FECHA Y HORA DE MODIFICACION
	        Date fechaSistema = new Date();
	        pr.setFmodificacion(new java.sql.Timestamp(fechaSistema.getTime()));		
     		try {
     			if(ngp.existeActualizarProducto(pr.getProductoid(), pr.getProducto())){
		        	response.sendRedirect("FormEditarProducto.jsp?idP="+productoid+"&msj=existe");
	    		}else{   
     			if(dts.modificarProducto(pr)) {
		        	response.sendRedirect("GestionProductos.jsp?msj=3");
		        }
				   
		        else {
		        	response.sendRedirect("GestionProductos.jsp?msj=4");
		        }}  	
	        }
	        catch(Exception e) {
	        	System.out.println("Sl_GestionProducto, el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;
				
			}
	   	
			default:
			response.sendRedirect("GestionProductos.jsp?msj=7");	
			break;
		}
	  }
	}
}
