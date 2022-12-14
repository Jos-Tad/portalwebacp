package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.RolUsuario;
import vistas.ViewRolUsuario;

public class Dt_RolUsuario {
	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsRolUser = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	// Metodo para llenar el RusultSet
	public void llenaRsRolUser(Connection c){
		try{
			ps = c.prepareStatement("select idrol_usuario, usuarioid, rolid, confirmemail from rol_usuario", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rsRolUser = ps.executeQuery();
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN LISTAR rol_usuario "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Metodo para visualizar LA LISTA DE ROLES ASIGNADOS A LOS USUARIOS
	public ArrayList<ViewRolUsuario> listaRolUser(){
		ArrayList<ViewRolUsuario> listRU = new ArrayList<ViewRolUsuario>();
		try{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("SELECT * FROM vw_usuario_rol", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rs = ps.executeQuery();
			while(rs.next()){
				ViewRolUsuario vwru = new ViewRolUsuario();
				vwru.setIdrol_usuario(rs.getInt("idrol_usuario"));
				vwru.setUsuarioid(rs.getInt("usuarioid"));
				vwru.setUsuario(rs.getString("usuario"));
				vwru.setRolid(rs.getInt("rolid"));
				vwru.setRol(rs.getString("rol"));
				vwru.setContra(rs.getString("contra"));
				vwru.setNombres(rs.getString("nombres"));
				vwru.setApellidos(rs.getString("apellidos"));
				listRU.add(vwru);
			}
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN LISTAR ROL USUARIO "+ e.getMessage());
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
		return listRU;
	}
	
	//Metodo para asignar rol a usuario
	public boolean guardarRolUser(RolUsuario ru){
		boolean guardado = false;
		
		try{
			c = PoolConexion.getConnection();
			this.llenaRsRolUser(c);
			rsRolUser.moveToInsertRow();
			rsRolUser.updateInt("usuarioid", ru.getUsuarioid());
			rsRolUser.updateInt("rolid", ru.getRolid());
			rsRolUser.updateInt("confirmemail",1);
			rsRolUser.insertRow();
			rsRolUser.moveToCurrentRow();
			guardado = true;
		}
		catch (Exception e) {
			System.err.println("ERROR AL guardarRolUser "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				if(rsRolUser != null){
					rsRolUser.close();
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
		// Metodo para eliminar RolUsuario
		public boolean eliminaRU(int idRU)
		{
			boolean eliminado=false;	
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsRolUser(c);
				rsRolUser.beforeFirst();
				while (rsRolUser.next())
				{
					if(rsRolUser.getInt(1)==idRU)
					{
						rsRolUser.deleteRow();
						eliminado=true;
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.err.println("ERROR AL ELIMINAR ROL_USER "+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsRolUser != null){
						rsRolUser.close();
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
		
		// Metodo para visualizar los datos de un usuario especifico
		public RolUsuario getRolUsuario(int idrolusuario)
		{
			RolUsuario ru = new RolUsuario();
			try
			{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("select * from rol_usuario where idrol_usuario=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				ps.setInt(1, idrolusuario);
				rs = ps.executeQuery();
				if(rs.next())
				{
					ru.setIdrol_usuario(idrolusuario);
					ru.setUsuarioid(rs.getInt("usuarioid"));
					ru.setRolid(rs.getInt("rolid"));
				
				}
			}
			catch (Exception e)
			{
				System.out.println("DATOS ERROR getRolUsuario(): "+ e.getMessage());
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
			
			return ru;
		}
		
		// Metodo para modificar Rol Usuario
		public boolean modificarRolUsuario (RolUsuario ro)
		{
			boolean modificado=false;	
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsRolUser(c);
				rsRolUser.beforeFirst();
				while (rsRolUser.next())
				{
					if(rsRolUser.getInt(1)==ro.getIdrol_usuario())
					{
						rsRolUser.updateInt("usuarioid", ro.getUsuarioid());
						rsRolUser.updateInt("rolid", ro.getRolid());	
						rsRolUser.updateRow();
						modificado=true;
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.err.println("ERROR AL ACTUALIZAR MODIFICAR"+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsRolUser != null){
						rsRolUser.close();
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
		
		// Metodo para actualizar estado y confirmar envio de correo de rol-usuario
		public boolean confirmEmail(int rolusuarioid)
		{
			boolean actualizado = false;		
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsRolUser(c);	
				rsRolUser.beforeFirst();
				while(rsRolUser.next())
				{
					if(rsRolUser.getInt(1)==rolusuarioid)
					{						
						rsRolUser.updateInt("confirmemail", 2);
						rsRolUser.updateRow();
						actualizado = true;
						break;
					}
				}
			}
			catch (Exception e) 
			{
				System.err.println("ERROR AL ACTUALIZAR ESTADO ROL-USUARIO "+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsRolUser != null){
						rsRolUser.close();
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
