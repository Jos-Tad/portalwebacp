package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidades.Publicacion;

public class Dt_Publicacion {
	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsPost = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
				
	// Metodo para llenar el ResultSet
	public void llenaRsPublicacion(Connection c){
		try{
			ps = c.prepareStatement("select publicacionid, titulo, descripcion, fcreacion, fmodificacion, feliminacion, multimedia1,multimedia2,multimedia3, estadopublicacion, estado, usuarioid FROM publicacion where estado <>3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rsPost = ps.executeQuery();
		}
		catch (Exception e){
			System.out.println("DATOS: Error al listar elementos del Publicacion "+ e.getMessage());
			e.printStackTrace();
		}
	}

	//Metodo para visualizar Publicacion
	public ArrayList<Publicacion> ListaPost(){

		//Crear lista de arreglos
		ArrayList<Publicacion> listPost = new ArrayList<Publicacion>();
		
		try{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("select publicacionid, titulo, descripcion, fcreacion, estadopublicacion, multimedia1,multimedia2,multimedia3,estado, usuarioid FROM publicacion where publicacion.estado <>3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rs = ps.executeQuery();
			while(rs.next()){
				Publicacion post = new Publicacion();
				post.setPublicacionid(rs.getInt("publicacionid"));
				post.setTitulo(rs.getString("titulo"));
				post.setDescripcion(rs.getString("descripcion"));
				post.setFcreacion(rs.getTimestamp("fcreacion"));
				post.setEstadopublicacion(rs.getInt("estadopublicacion"));
				post.setMultimedia1(rs.getString("multimedia1"));
				post.setMultimedia2(rs.getString("multimedia2"));
				post.setMultimedia3(rs.getString("multimedia3"));
				post.setEstado(rs.getInt("estado"));
				post.setUsuarioid(rs.getInt("usuarioid"));
				listPost.add(post);
			}
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN LISTAR PUBLICACION "+ e.getMessage());
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
		return listPost;
		
	}
			
	//Metodo para almacenar nueva Publicacion
	public boolean guardarPost(Publicacion post){
		boolean guardado = false;
		
		try{
			c = PoolConexion.getConnection();
			this.llenaRsPublicacion(c);
			rsPost.moveToInsertRow();
			rsPost.updateString("titulo", post.getTitulo());
			rsPost.updateString("descripcion", post.getDescripcion());			
			rsPost.updateTimestamp("fcreacion", post.getFcreacion());						
			rsPost.updateInt("estadopublicacion", post.getEstadopublicacion());						
			rsPost.updateInt("estado", 1);
			rsPost.updateInt("usuarioid", post.getUsuarioid());
			rsPost.insertRow();
			rsPost.moveToCurrentRow();
			guardado = true;
		}
		catch (Exception e) {
			System.err.println("ERROR AL GUARDAR PUBLICACION "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				if(rsPost != null){
					rsPost.close();
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
			
	// Metodo para visualizar los datos de un publicacion especifico
	public Publicacion getPublicacion(int idPublicacion)
	{
		Publicacion post = new Publicacion();
		try
		{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("SELECT publicacionid,titulo,descripcion,estadopublicacion,estado, fcreacion, fmodificacion, feliminacion, multimedia1, multimedia2,multimedia3, usuarioid FROM publicacion where publicacionid = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			ps.setInt(1, idPublicacion);
			rs = ps.executeQuery();
			if(rs.next())
			{
				post.setPublicacionid(idPublicacion);
				post.setTitulo(rs.getString("titulo"));
				post.setDescripcion(rs.getString("descripcion"));
				post.setMultimedia1(rs.getString("multimedia1"));
				post.setMultimedia2(rs.getString("multimedia2"));
				post.setMultimedia3(rs.getString("multimedia3"));
				post.setEstadopublicacion(rs.getInt("estadopublicacion"));
				post.setEstado(rs.getInt("estado"));
			}
		}
		catch (Exception e)
		{
			System.out.println("DATOS ERROR: GetPublicacion "+ e.getMessage());
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
		return post;
	}
			
	//Metodo para modificar publicacion
	public boolean modificarPublicacion(Publicacion post)
	{
		boolean modificado=false;	
		try
		{
			c = PoolConexion.getConnection();
			this.llenaRsPublicacion(c);
			rsPost.beforeFirst();
			while (rsPost.next())
			{
				if(rsPost.getInt(1)==post.getPublicacionid())
				{
					rsPost.updateString("titulo", post.getTitulo());
					rsPost.updateString("descripcion", post.getDescripcion());
					rsPost.updateInt("estadopublicacion", post.getEstadopublicacion());
					rsPost.updateTimestamp("fmodificacion", post.getFmodificacion());
					rsPost.updateInt("usuarioid", post.getUsuarioid());
					rsPost.updateInt("estado", 2);
					rsPost.updateRow();
					modificado=true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("ERROR AL ACTUALIZAR PUBLICACION "+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rsPost != null){
					rsPost.close();
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
			
	//Metodo para eliminar Publicacion
	public boolean eliminarPublicacion(int idP)
	{
		boolean eliminado=false;	
		try
		{
			c = PoolConexion.getConnection();
			this.llenaRsPublicacion(c);
			rsPost.beforeFirst();
			while (rsPost.next())
			{
				if(rsPost.getInt(1)==idP)
				{
					rsPost.deleteRow();
					eliminado=true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("ERROR AL ELIMINAR PUBLICACION "+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rsPost != null){
					rsPost.close();
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
	
	// Metodo para guardar la foto del publicacion
	public boolean guardarImagenPublicacion(int idPublicacion, String urlFoto)
	{
		boolean actualizado = false;
		
		try
		{
			c = PoolConexion.getConnection();
			this.llenaRsPublicacion(c);	
			rsPost.beforeFirst();
			while(rsPost.next())
			{
				if(rsPost.getInt(1)==idPublicacion)
				{
					
					rsPost.updateString("multimedia1", urlFoto);
					rsPost.updateInt("estado", 2);
					rsPost.updateRow();
					actualizado = true;
					break;
				}
			}
		}
		catch (Exception e) 
		{
			System.err.println("ERROR AL GUARDAR FOTO PUBLICACION"+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rsPost != null){
					rsPost.close();
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
	
	// Metodo para guardar la PDF del publicacion
	public boolean guardarPDFPublicacion(int idPublicacion, String pdf)
	{
		boolean actualizado = false;
		
		try
		{
			c = PoolConexion.getConnection();
			this.llenaRsPublicacion(c);	
			rsPost.beforeFirst();
			while(rsPost.next())
			{
				if(rsPost.getInt(1)==idPublicacion)
				{					
					rsPost.updateString("multimedia2", pdf);
					rsPost.updateInt("estado", 2);
					rsPost.updateRow();
					actualizado = true;
					break;
				}
			}
		}
		catch (Exception e) 
		{
			System.err.println("ERROR AL GUARDAR PDF "+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rsPost != null){
					rsPost.close();
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
		
	// Metodo para guardar video de la publicacion
	public boolean guardarVideoPublicacion(int idPublicacion, String video)
	{
		boolean actualizado = false;
		
		try
		{
			c = PoolConexion.getConnection();
			this.llenaRsPublicacion(c);	
			rsPost.beforeFirst();
			while(rsPost.next())
			{
				if(rsPost.getInt(1)==idPublicacion)
				{					
					rsPost.updateString("multimedia3", video);
					rsPost.updateInt("estado", 2);
					rsPost.updateRow();
					actualizado = true;
					break;
				}
			}
		}
		catch (Exception e) 
		{
			System.err.println("ERROR AL GUARDAR VIDEO "+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rsPost != null){
					rsPost.close();
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
}
