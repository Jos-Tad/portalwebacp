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

import datos.Dt_Evento;
import entidades.Evento;
import negocio.Ng_Evento;

/**
 * Servlet implementation class Sl_GestionEvento
 */
@WebServlet("/Sl_GestionEvento")
public class Sl_GestionEvento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_GestionEvento() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int idEvento =0;
		idEvento = Integer.parseInt(request.getParameter("idE"));
		Dt_Evento dts = new	Dt_Evento();
		
		if(dts.eliminarEvento(idEvento)) {
        	response.sendRedirect("GestionEvento.jsp?msj=5");
        }
        else {
        	response.sendRedirect("GestionEvento.jsp?msj=6");
        }
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Dt_Evento dte = new Dt_Evento();
		Evento ev = new Evento();
		Ng_Evento nge = new Ng_Evento();
			
		int opc = 0;
		int eventoid =0;
		String txtNombreEvento = null;
		String txtDescripcionEvento = null;
		String datefInicioEvento = null;
		String timehoraInicioEvento = null;
		String datefFinalEvento = null;
		String timehoraFinEvento = null;
		String cbxTipoEvento = null;
		String multEvento = null;
		String txthipervinculoEvento = null;
		String txtUbicacionEvento = null;		
		String rutaFichero = null;
		String usuarioid = null;
		
		boolean control = false;//Variable
				
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
					}else if(key.equals("eventoid")){
						eventoid = Integer.parseInt(valor);
					}else if(key.equals("txtNombreEvento")){
						txtNombreEvento = valor;
					}else if(key.equals("txtDescripcionEvento")){
						txtDescripcionEvento = valor;
					}else if(key.equals("datefInicioEvento")){
						datefInicioEvento = valor;
					}else if(key.equals("timehoraInicioEvento")){
						timehoraInicioEvento = valor;
					}else if(key.equals("datefFinalEvento")){
						datefFinalEvento = valor;
					}else if(key.equals("timehoraFinEvento")){
						timehoraFinEvento = valor;
					}else if(key.equals("cbxTipoEvento")){
						cbxTipoEvento = valor;
					}else if(key.equals("url_foto")){
						multEvento = valor;
					}else if(key.equals("txthipervinculoEvento")){
						txthipervinculoEvento = valor;
					}else if(key.equals("txtUbicacionEvento")){
						txtUbicacionEvento = valor;				
					}else if(key.equals("usuarioid")){
						usuarioid = valor;
					}
					
				}
			}
			if(txtNombreEvento.trim().isEmpty()||txtDescripcionEvento.trim().isEmpty()|| cbxTipoEvento.trim().isEmpty()||
					datefInicioEvento.trim().isEmpty()||timehoraInicioEvento.trim().isEmpty()||
					datefFinalEvento.trim().isEmpty()||timehoraFinEvento.trim().isEmpty()){
				response.sendRedirect("GestionEvento.jsp?msj=2");
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
				    valorImagen = eventoid; 
					
					if(formatos.contains(uploaded.getContentType()))
					{
						System.out.println("Filetype: "+uploaded.getContentType());
						
						rutaFichero = "fotosEvento"+valorImagen+".jpg";
						path = "C:\\payara5\\glassfish\\fotosEventoAbra\\";
						System.out.println(path+rutaFichero);
						
						fichero = new File(path+rutaFichero);
						System.out.println(path+rutaFichero);
						
						///////// GUARDAR EN EL SERVIDOR //////////////
						uploaded.write(fichero);
						
						System.out.println("SERVIDOR: FOTO GUARDADA CON EXITO!!!");
						/////// ACTUALIZAMOS EL CAMPO URLFOTO EN LA BASE DE DATOS
						String url = "fotosEventoAbra/"+rutaFichero;
						ev.setMultimedia(url);
					}
					else
					{
						System.out.println("SERVIDOR: VERIFIQUE QUE EL ARCHIVO CUMPLA CON LAS ESPECIFICACIONES REQUERIDAS!!!");
						response.sendRedirect("GestionEvento.jsp?msj=2");				
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
		
		if(control = true){			
		ev.setNombre(txtNombreEvento);
		ev.setDescripcion(txtDescripcionEvento);
		ev.setFechainicio(datefInicioEvento);
		ev.setHorainicio(timehoraInicioEvento);
		ev.setFechafin(datefFinalEvento);
		ev.setHorafin(timehoraFinEvento);
		ev.setTipoevento(Integer.parseInt(cbxTipoEvento));
		ev.setHipervinculo(txthipervinculoEvento);
		ev.setUbicacion(txtUbicacionEvento);
		ev.setUsuarioid(Integer.parseInt(usuarioid));
		if(ev.getMultimedia()==null){
			ev.setMultimedia(multEvento);
		}
		
		switch (opc){
		case 1:{
			//PARA GUARDAR LA FECHA Y HORA DE CREACION
	        Date fechaSistema = new Date();
	        ev.setFcreacion(new java.sql.Timestamp(fechaSistema.getTime()));			  
		        try {
		        	if(nge.existeEvento(ev.getNombre())) {
		        		response.sendRedirect("GestionEvento.jsp?msj=existe");
		        	}else if(nge.colisionEventoInicio(ev.getFechainicio(),  ev.getHorainicio())){
		         		response.sendRedirect("GestionEvento.jsp?msj=colision");		    		    
		        	}
		        	else {
		        		if(dte.guardarEventos(ev)) {
				        	response.sendRedirect("GestionEvento.jsp?msj=1");
		        	}
			        else {
			        	response.sendRedirect("GestionEvento.jsp?msj=2");
			        }
		        	}
		        }
		        catch(Exception e) {
		        	System.out.println("Sl_GestionEvento, el error es: " + e.getMessage());
					e.printStackTrace();
		        }
		        
				break;
			}
		case 2:{
			//PARA GUARDAR LA FECHA Y HORA DE MODIFICACION
	        Date fechaSistema = new Date();
	        ev.setFmodificacion(new java.sql.Timestamp(fechaSistema.getTime()));
			ev.setEventoid(eventoid);
     		try {
				   if(dte.modificarEvento(ev)) {
		        	response.sendRedirect("GestionEvento.jsp?msj=3");
		        }
		        else {
		        	response.sendRedirect("GestionEvento.jsp?msj=4");
		        }       	
	        }
	        catch(Exception e) {
	        	System.out.println("Sl_GestionEvento, el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;
				
			}
			default:
			response.sendRedirect("GestionEvento.jsp?msj=5");	
			break;
			}//Fin Switch	
		}
	}
}