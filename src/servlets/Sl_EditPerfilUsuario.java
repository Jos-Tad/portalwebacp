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

import datos.Dt_Usuario;
import datos.Encrypt;
import datos.PoolConexion;
import entidades.Usuario;
import negocio.Ng_Usuario;
import vistas.ViewRolUsuario;

/**
 * Servlet implementation class Sl_EditPerfilUsuario
 */
@WebServlet("/Sl_EditPerfilUsuario")
public class Sl_EditPerfilUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_EditPerfilUsuario() {
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
		
		//Construir objeto Usuario
		Dt_Usuario dtu = new Dt_Usuario();
		Usuario user = new Usuario();
		Ng_Usuario ngu = new Ng_Usuario();
			
		int opc = 0;
		int usuarioid =0;
		String txtNombres = null;
		String txtApellidos = null;
		String txtTelefono = null;
		String txtUsuario = null;
		String txtCorreo = null;
		String txtContra = null;
		String rutaFichero = null;
		String url_foto = null;	
		String usuarioV = null;
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
					}else if(key.equals("txtNombres")){
						txtNombres = valor;
					}else if(key.equals("txtApellidos")){
						txtApellidos = valor;
					}else if(key.equals("txtTelefono")){
						txtTelefono = valor;
					}else if(key.equals("txtUsuario")){
						txtUsuario = valor;
					}else if(key.equals("txtCorreo")){
						txtCorreo = valor;
					}else if(key.equals("url_foto")){
						url_foto = valor;
					}else if(key.equals("txtContra")){
						txtContra = valor;
					}else if(key.equals("usuarioid")){
						usuarioid = Integer.parseInt(valor);
					}else if(key.equals("usuarioV")){
						usuarioV = valor;
					}					
				}
			}
			
			ViewRolUsuario vwru = new ViewRolUsuario();
			Encrypt enc = new Encrypt();
			vwru = dtu.dtGetRU(usuarioV);
			String pwdDecrypt = "";
			String pwdEncrypt = "";
			boolean contraV = false;
			
			pwdEncrypt = vwru.getContra();
			pwdDecrypt = enc.getAESDecrypt(pwdEncrypt,vwru.getKey_encriptacion());
			
			if(txtContra.equals(pwdEncrypt)){
				contraV= true;
			}else if(txtContra.equals(pwdDecrypt)){
				contraV = true;
			}
			
			if(contraV){
				if(txtNombres.trim().isEmpty() || txtApellidos.trim().isEmpty() || txtTelefono.trim().isEmpty()|| txtUsuario.trim().isEmpty()){
		        	response.sendRedirect("Perfil.jsp?msj=2");			
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
					    valorImagen = usuarioid; 
						
						if(formatos.contains(uploaded.getContentType()))
						{
							System.out.println("Filetype: "+uploaded.getContentType());
							
							rutaFichero = "fotoUsuario"+valorImagen+".jpg";
							path = "C:\\payara5\\glassfish\\fotosUsuarioAbra\\";
							System.out.println(path+rutaFichero);
							
							fichero = new File(path+rutaFichero);
							System.out.println(path+rutaFichero);
							
							///////// GUARDAR EN EL SERVIDOR //////////////
							uploaded.write(fichero);
							
							System.out.println("SERVIDOR: FOTO GUARDADA CON EXITO!!!");
							/////// ACTUALIZAMOS EL CAMPO URLFOTO EN LA BASE DE DATOS
							String url = "fotosUsuarioAbra/"+rutaFichero;
							user.setUrl_foto(url);
						}
						else
						{
							System.out.println("SERVIDOR: VERIFIQUE QUE EL ARCHIVO CUMPLA CON LAS ESPECIFICACIONES REQUERIDAS!!!");
							response.sendRedirect("Perfil.jsp?msj="+2+"&guardado=3");						
						}
					  }
				   }
				}
			}
			else {
	        	response.sendRedirect("Perfil.jsp?msj=pwdE");			
			}
		}
		catch(Exception e)
		{
			System.out.println("SERVLET: ERROR AL SUBIR LA FOTO: " + e.getMessage());
		}	
		if(control){	
		
		user.setIdUser(usuarioid);
		user.setNombre(txtNombres);
		user.setApellido(txtApellidos);
		user.setTelefono(txtTelefono);
		user.setUser(txtUsuario);
		user.setEmail(txtCorreo);
		if(user.getUrl_foto()==null){
			user.setUrl_foto(url_foto);
		}
	
		switch (opc){
		case 1:{			
			try {
		        Date fechaSistema = new Date();
		        user.setfModificacion(new java.sql.Timestamp(fechaSistema.getTime()));
		        if(ngu.existeActualizarUsuario(user.getIdUser(),user.getUser())){
		          	response.sendRedirect("Perfil.jsp?userID="+user.getIdUser()+"&msj=existe");
		        }else {
		        if(dtu.actualizarPerfil(user)){
		        	response.sendRedirect("Perfil.jsp?msj=1");
		        }
		        else {
		        	response.sendRedirect("Perfil.jsp?msj=2");
		        }
		        }
				}
	        catch(Exception e) {
	        	System.out.println("Sl_EditPerfilUsuario, el error es: " + e.getMessage());
				e.printStackTrace();
	        }
				break;	
			}
		default:
			response.sendRedirect("Perfil.jsp?msj=3");	
			break;
		 }
	   }		
	}
}
