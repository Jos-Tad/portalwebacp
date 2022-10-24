package datos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import entidades.Usuario;
import vistas.ViewRolUsuario;

public class Dt_Usuario {
	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsUsuario = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	// Metodo para llenar el RusultSet
	public void llenaRsUsuario(Connection c){
		try{
			ps = c.prepareStatement("SELECT usuarioid, nombres, apellidos, email, usuario, contra, telefono,estado, url_foto,cod_verificacion,key_encriptacion,fcreacion, fmodificacion, feliminacion FROM usuario where estado<>3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rsUsuario = ps.executeQuery();
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN LISTAR USUARIOS "+ e.getMessage());
			e.printStackTrace();
		}
	}
	//Metodo para visualizar usuarios registrados y activos
		public ArrayList<Usuario> listaUserActivos(){
			ArrayList<Usuario> listUser = new ArrayList<Usuario>();
			try{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("SELECT usuarioid, nombres, apellidos, email, telefono,usuario, contra, estado, url_foto,cod_verificacion, key_encriptacion, fcreacion, fmodificacion, feliminacion FROM usuario where estado<>3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rs = ps.executeQuery();
				while(rs.next()){
					Usuario user = new Usuario();
					user.setIdUser(rs.getInt("usuarioid"));
					user.setUser(rs.getString("usuario"));
					user.setPwd(rs.getString("contra"));
					user.setNombre(rs.getString("nombres"));
					user.setApellido(rs.getString("apellidos"));
					user.setfCreacion(rs.getTimestamp("fcreacion"));
					user.setEmail(rs.getString("email"));
					user.setTelefono(rs.getString("telefono"));
					user.setEstado(rs.getInt("estado"));
					listUser.add(user);
				}
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR USUARIOS "+ e.getMessage());
				e.printStackTrace();
			}
			finally{
				try {
					if(rs != null){
						rs.close();
					}
					if(ps != null){
						ps.close();
					}
					if(c != null){
						PoolConexion.closeConnection(c);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			return listUser;
		}
		
		// Metodo para visualizar los datos de un usuario especifico
		public Usuario getUsuario(int idUsuario)
		{
			Usuario user = new Usuario();
			try
			{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("select usuarioid, nombres, apellidos, email, telefono ,usuario, contra, estado, url_foto, cod_verificacion, key_encriptacion, fcreacion, fmodificacion, feliminacion from usuario where estado <> 3 and usuarioid=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				ps.setInt(1, idUsuario);
				rs = ps.executeQuery();
				if(rs.next())
				{
					user.setIdUser(idUsuario);
					user.setUser(rs.getString("usuario"));
					user.setPwd(rs.getString("contra"));
					user.setNombre(rs.getString("nombres"));
					user.setApellido(rs.getString("apellidos"));
					user.setUrl_foto(rs.getString("url_foto"));
					user.setEmail(rs.getString("email"));
					user.setTelefono(rs.getString("telefono"));
					user.setCod_verificacion(rs.getString("cod_verificacion"));	
					user.setKey_encriptacion("key_encriptacion");
					user.setEstado(rs.getInt("estado"));
				}
			}
			catch (Exception e)
			{
				System.out.println("DATOS ERROR getUsuario(): "+ e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rs != null){
						rs.close();
					}
					if(ps != null){
						ps.close();
					}
					if(c != null){
						PoolConexion.closeConnection(c);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return user;
		}
		
		//Metodo para almacenar nuevo usuario
		public boolean guardarUser(Usuario user){
			boolean guardado = false;
			
			try{
				c = PoolConexion.getConnection();
				this.llenaRsUsuario(c);
				rsUsuario.moveToInsertRow();
				rsUsuario.updateString("usuario", user.getUser());
				rsUsuario.updateString("contra", user.getPwd());
				rsUsuario.updateString("nombres", user.getNombre());
				rsUsuario.updateString("apellidos", user.getApellido());
				rsUsuario.updateTimestamp("fcreacion", user.getfCreacion());
				rsUsuario.updateString("email", user.getEmail());
				rsUsuario.updateString("telefono",user.getTelefono());
				rsUsuario.updateString("url_foto", "img/undraw_profile.svg");
				rsUsuario.updateString("cod_verificacion", user.getCod_verificacion());
				rsUsuario.updateString("key_encriptacion", user.getKey_encriptacion());
				rsUsuario.updateInt("estado", 0);
				rsUsuario.insertRow();
				rsUsuario.moveToCurrentRow();
				guardado = true;
			}
			catch (Exception e) {
				System.err.println("ERROR AL GUARDAR Usuario "+e.getMessage());
				e.printStackTrace();
			}
			finally{
				try {
					if(rsUsuario != null){
						rsUsuario.close();
					}
					if(c != null){
						PoolConexion.closeConnection(c);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return guardado;
		}
		// Metodo para modificar usuario
		public boolean modificarUser(Usuario user)
		{
			boolean modificado=false;	
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsUsuario(c);
				rsUsuario.beforeFirst();
				while (rsUsuario.next())
				{
					if(rsUsuario.getInt(1)==user.getIdUser())
					{
						rsUsuario.updateString("usuario", user.getUser());
						rsUsuario.updateString("nombres", user.getNombre());
						rsUsuario.updateString("apellidos", user.getApellido());
						rsUsuario.updateTimestamp("fmodificacion", user.getfModificacion());
						rsUsuario.updateString("email", user.getEmail());
						rsUsuario.updateString("telefono",user.getTelefono());	
						if(user.getPwd().equals("")==false){
						rsUsuario.updateString("contra", user.getPwd());
						}
						if(user.getKey_encriptacion().equals("")==false){
						rsUsuario.updateString("key_encriptacion", user.getKey_encriptacion());
						}
						if(user.getEstado()!=0){
						rsUsuario.updateInt("estado", 2);						
						}
						rsUsuario.updateRow();
						modificado=true;
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.err.println("ERROR AL ACTUALIZAR USUARIO "+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsUsuario != null){
						rsUsuario.close();
					}
					if(c != null){
						PoolConexion.closeConnection(c);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return modificado;
		}
		
		// Metodo para actualizar perfil usuario
		public boolean actualizarPerfil(Usuario user)
		{
			boolean modificado=false;	
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsUsuario(c);
				rsUsuario.beforeFirst();
				while (rsUsuario.next())
				{
					if(rsUsuario.getInt(1)==user.getIdUser())
					{
						rsUsuario.updateString("usuario", user.getUser());
						rsUsuario.updateString("nombres", user.getNombre());
						rsUsuario.updateString("apellidos", user.getApellido());
						rsUsuario.updateTimestamp("fmodificacion", user.getfModificacion());
						rsUsuario.updateString("email", user.getEmail());
						rsUsuario.updateString("telefono",user.getTelefono());	
						rsUsuario.updateString("url_foto", user.getUrl_foto());						
						rsUsuario.updateRow();
						modificado=true;
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.err.println("ERROR AL ACTUALIZAR PERFIL USUARIO "+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsUsuario != null){
						rsUsuario.close();
					}
					if(c != null){
						PoolConexion.closeConnection(c);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return modificado;
		}
		
		// Metodo para eliminar usuario
		public boolean eliminarUser(int idUsuario)
		{
			boolean eliminado=false;	
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsUsuario(c);
				rsUsuario.beforeFirst();
				Date fechaSistema = new Date();
				while (rsUsuario.next())
				{
					if(rsUsuario.getInt(1)==idUsuario)
					{
						rsUsuario.updateTimestamp("feliminacion", new java.sql.Timestamp(fechaSistema.getTime()));
						rsUsuario.updateInt("estado", 3);
						rsUsuario.updateRow();
						eliminado=true;
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.err.println("ERROR AL ELIMINAR USUARIO "+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsUsuario != null){
						rsUsuario.close();
					}
					if(c != null){
						PoolConexion.closeConnection(c);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return eliminado;
		}
		// Metodo para guardar la foto del Usuario
		public boolean guardarFotoUser(int idUser, String urlFoto)
		{
			boolean actualizado = false;
			
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsUsuario(c);	
				rsUsuario.beforeFirst();
				while(rsUsuario.next())
				{
					if(rsUsuario.getInt(1)==idUser)
					{
						
						rsUsuario.updateString("url_foto", urlFoto);
						rsUsuario.updateRow();
						actualizado = true;
						break;
					}
				}
			}
			catch (Exception e) 
			{
				System.err.println("ERROR AL GUARDAR FOTO "+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsUsuario != null){
						rsUsuario.close();
					}
					if(c != null){
						PoolConexion.closeConnection(c);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return actualizado;
		}
		
		// Metodo para verificar usuario, contrasena y rol
		public boolean dtverificarLogin(String login, String clave, int rol)
		{
			boolean existe=false;
			String SQL = ("SELECT * FROM vw_usuario_rol WHERE usuario=? AND contra=? AND rolid=? AND estado<>0");
					
			try{
				/////// DESENCRIPTACION DE LA PWD //////////
				ViewRolUsuario vwru = new ViewRolUsuario();
				Encrypt enc = new Encrypt();
				vwru = this.dtGetRU(login);
				String pwdDecrypt = "";
				String pwdEncrypt = "";
				
				pwdEncrypt = vwru.getContra();
				pwdDecrypt = enc.getAESDecrypt(pwdEncrypt,vwru.getKey_encriptacion());
				
				
				c = PoolConexion.getConnection();
				ps = c.prepareStatement(SQL);
				ps.setString(1, login);
				
				if(clave.equals(pwdDecrypt)){
					ps.setString(2, pwdEncrypt);
				}
				else {
					ps.setString(2, clave);
				}
				
				ps.setInt(3, rol);
				rs = ps.executeQuery();
				if(rs.next()){
					existe=true;
				}
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR AL VERIFICAR EL LOGIN "+ e.getMessage());
				e.printStackTrace();
			}
			finally {
				try {
					if(rs != null){
						rs.close();
					}
					if(ps != null){
						ps.close();
					}
					if(c != null){
						PoolConexion.closeConnection(c);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return existe;
		}
		
		// Metodo para verificar usuario, contrasena, rol y codigo verificacion
		public boolean dtverificarLogin2(String login, String clave, int rol, String codigo)
		{
			boolean existe=false;
			String SQL = ("SELECT * FROM vw_usuario_rol WHERE usuario=? AND contra=? AND rolid=? AND cod_verificacion=?");
			try{
				/////// DESENCRIPTACION DE LA PWD //////////
				ViewRolUsuario vwru = new ViewRolUsuario();
				Encrypt enc = new Encrypt();
				vwru = this.dtGetRU(login);
				String pwdDecrypt = "";
				String pwdEncrypt = "";
				
				pwdEncrypt = vwru.getContra();
				pwdDecrypt = enc.getAESDecrypt(pwdEncrypt,vwru.getKey_encriptacion());				
				
				c = PoolConexion.getConnection();
				ps = c.prepareStatement(SQL);
				ps.setString(1, login);
				
				if(clave.equals(pwdDecrypt)){
					ps.setString(2, pwdEncrypt);
				}
				else {
					ps.setString(2, clave);
				}
				
				ps.setInt(3, rol);
				ps.setString(4, codigo);
				
				rs = ps.executeQuery();
				if(rs.next()){
					existe=true;
					actualizarEstadoUsuario(login);
				}
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR AL VERIFICAR EL LOGIN Y VERIFICAR USUARIO "+ e.getMessage());
				e.printStackTrace();
			}
			finally {
				try {
					if(rs != null){
						rs.close();
					}
					if(ps != null){
						ps.close();
					}
					if(c != null){
						PoolConexion.closeConnection(c);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
			return existe;
		}
	
		// Metodo para actualizar estado del Usuario
		public boolean actualizarEstadoUsuario(String login)
		{
			boolean actualizado = false;
			
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsUsuario(c);	
				rsUsuario.beforeFirst();
				while(rsUsuario.next())
				{
					if(rsUsuario.getString("usuario").equals(login))
					{						
						rsUsuario.updateInt("estado", 1);
						rsUsuario.updateRow();
						actualizado = true;
						break;
					}
				}
			}
			catch (Exception e) 
			{
				System.err.println("ERROR AL ACTUALIZAR ESTADO USUARIO "+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsUsuario != null){
						rsUsuario.close();
					}
					if(c != null){
						PoolConexion.closeConnection(c);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return actualizado;
		}
		
		//Metodo para obtener un objeto de la vista rol usuario
		public ViewRolUsuario dtGetRU(String login)
		{
			ViewRolUsuario vwru = new ViewRolUsuario();
		String SQL = ("SELECT * FROM vw_usuario_rol where usuario=?");
		try{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement(SQL);
			ps.setString(1, login);
			rs = ps.executeQuery();
			if(rs.next()){
				vwru.setIdrol_usuario(rs.getInt("idrol_usuario"));
				vwru.setUsuarioid(rs.getInt("usuarioid"));
				vwru.setRolid(rs.getInt("rolid"));
				vwru.setUsuario(rs.getString("usuario"));
				vwru.setRol(rs.getString("rol"));
				vwru.setContra(rs.getString("contra"));
				vwru.setNombres(rs.getString("nombres"));
				vwru.setApellidos(rs.getString("apellidos"));
				vwru.setUrl_foto(rs.getString("url_foto"));
				vwru.setEmail(rs.getString("email"));
				vwru.setTelefono(rs.getString("telefono"));
				vwru.setKey_encriptacion(rs.getString("key_encriptacion"));
			}
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN dtGetRU "+ e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				if(rs != null){
					rs.close();
				}
				if(ps != null){
					ps.close();
				}
				if(c != null){
					PoolConexion.closeConnection(c);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vwru;
	}
		
	// Metodo para obtener los datos de un usuario en proceso de recuperacion de pwd
	public Usuario getUserPwd(String userName, String userEmail){
		Usuario user = new Usuario();
		try{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("select * from usuario where estado <> 3 AND email=? AND usuario=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			ps.setString(1, userEmail);
			ps.setString(2, userName);
			rs = ps.executeQuery();
			if(rs.next()){
				user.setIdUser(rs.getInt("usuarioid"));
				user.setUser(rs.getString("usuario"));
				user.setPwd(rs.getString("contra"));
				user.setEmail(rs.getString("email"));
				user.setNombre(rs.getString("nombres"));
				user.setApellido(rs.getString("apellidos"));
				user.setUrl_foto(rs.getString("url_foto"));
				user.setCod_verificacion(rs.getString("cod_verificacion"));
				user.setKey_encriptacion(rs.getString("key_encriptacion"));
				user.setEstado(rs.getInt("estado"));
			}
			else {
				user.setIdUser(0);
			}
		}
		catch (Exception e){
			System.out.println("DATOS ERROR getUserPwd(): "+ e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(ps != null){
					ps.close();
				}
				if(c != null){
					PoolConexion.closeConnection(c);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return user;
	}
	
	// Metodo para actualizar la contraseña del usuario
	public boolean updatePwd(Usuario user, String newPwd)
	{
		boolean modificado=false;	
		try{
			c = PoolConexion.getConnection();
			/////// ENCRIPTACION DE LA PWD //////////
			Encrypt enc = new Encrypt();
			String key = "";
			String pwdEncrypt = "";
			key=enc.generarLLave();
			pwdEncrypt = enc.getAES(newPwd,key);
			user.setPwd(pwdEncrypt);
			user.setKey_encriptacion(key);
			/////////////////////////////////////////
			Date fechaSistema = new Date();
  	        user.setfModificacion(new java.sql.Timestamp(fechaSistema.getTime()));
			
			this.llenaRsUsuario(c);
			rsUsuario.beforeFirst();
			while (rsUsuario.next()){
				if(rsUsuario.getInt(1)==user.getIdUser()){
					rsUsuario.updateString("contra", user.getPwd());
					rsUsuario.updateString("key_encriptacion", user.getKey_encriptacion());
					rsUsuario.updateTimestamp("fmodificacion", user.getfModificacion());
					rsUsuario.updateInt("estado", 2);
					rsUsuario.updateRow();
					modificado=true;
					break;
				}
			}
		}
		catch (Exception e){
			System.err.println("ERROR AL ACTUALIZAR CONTRASEÑA DE USUARIO "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				if(rsUsuario != null){
					rsUsuario.close();
				}
				if(c != null){
					PoolConexion.closeConnection(c);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return modificado;
	}
	
	// Metodo para traer vista Rol-Usuario
	public ViewRolUsuario getVwUsuarioRol(int idUsuario)
	{
		ViewRolUsuario vu = new ViewRolUsuario();
		try
		{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("SELECT * FROM vw_usuario_rol WHERE usuarioid=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			ps.setInt(1, idUsuario);
			rs = ps.executeQuery();
			if(rs.next())
			{
				vu.setUsuarioid(idUsuario);
				vu.setUsuario(rs.getString("usuario"));
				vu.setContra(rs.getString("contra"));
				vu.setEmail(rs.getString("email"));
				vu.setCod_verificacion(rs.getString("cod_verificacion"));
				vu.setKey_encriptacion(rs.getString("key_encriptacion"));
				vu.setIdrol_usuario(rs.getInt("idrol_usuario"));
				vu.setRol(rs.getString("rol"));	
			}
		}
		catch (Exception e)
		{
			System.out.println("DATOS: ERROR AL TRAER VISTA ROL-USUARIO: "+ e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rs != null){
					rs.close();
				}
				if(ps != null){
					ps.close();
				}
				if(c != null){
					PoolConexion.closeConnection(c);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return vu;
	}
				
	//METODO PARA GENERAR UN CODIGO DE VERIFICACION //	
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static String randomAlphaNumeric(int count) 
	{
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) 
		{
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}	
}
