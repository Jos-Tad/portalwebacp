package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.RolOpcion;
import entidades.RolUsuario;
import vistas.ViewRolOpcion;

public class Dt_RolOpcion {
	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsRolOpc = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
		//Metodo para llenar el RusultSet
		public void llenaRsRolUser(Connection c){
			try{
				ps = c.prepareStatement("select idrol_opc, rolid, id_opc from rol_opcion", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rsRolOpc = ps.executeQuery();
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR ROL_OPC "+ e.getMessage());
				e.printStackTrace();
			}
		}
	
		//METODO PARA VISUALIZAR LA LISTA DE ROLES ASIGNADOS A LOS USUARIOS
		public ArrayList<ViewRolOpcion> listaRolOpcion(){
			ArrayList<ViewRolOpcion> listRU = new ArrayList<ViewRolOpcion>();
			try{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("SELECT * FROM vw_rol_opciones", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rs = ps.executeQuery();
				while(rs.next()){
					ViewRolOpcion vwro = new ViewRolOpcion();
					vwro.setIdrol_opc(rs.getInt("idrol_opc"));
					vwro.setRolid(rs.getInt("rolid"));
					vwro.setRol(rs.getString("rol"));
					vwro.setId_opc(rs.getInt("id_opc"));
					vwro.setOpcion(rs.getString("opcion"));
					listRU.add(vwro);
				}
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR ROL OPCIONES "+ e.getMessage());
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
		public boolean guardarRolOpc(RolOpcion ro){
			boolean guardado = false;
			
			try{
				c = PoolConexion.getConnection();
				this.llenaRsRolUser(c);
				rsRolOpc.moveToInsertRow();
				rsRolOpc.updateInt("rolid", ro.getRolid());
				rsRolOpc.updateInt("id_opc", ro.getId_opc());
				rsRolOpc.insertRow();
				rsRolOpc.moveToCurrentRow();
				guardado = true;
			}
			catch (Exception e) {
				System.err.println("ERROR AL guardarRolOpc "+e.getMessage());
				e.printStackTrace();
			}
			finally{
				try {
					if(rsRolOpc != null){
						rsRolOpc.close();
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
		public boolean eliminaRO(int idRO)
		{
			boolean eliminado=false;	
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsRolUser(c);
				rsRolOpc.beforeFirst();
				while (rsRolOpc.next())
				{
					if(rsRolOpc.getInt(1)==idRO)
					{
						rsRolOpc.deleteRow();
						eliminado=true;
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.err.println("ERROR AL ELIMINAR ROL_OPC "+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsRolOpc != null){
						rsRolOpc.close();
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
		public RolOpcion getRolOpcion(int idrolopc)
		{
			RolOpcion ru = new RolOpcion();
			try
			{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("select * from rol_opcion where idrol_opc=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				ps.setInt(1, idrolopc);
				rs = ps.executeQuery();
				if(rs.next())
				{
					ru.setIdrol_opc(idrolopc);
					ru.setId_opc(rs.getInt("id_opc"));
					ru.setRolid(rs.getInt("rolid"));
				}
			}
			catch (Exception e)
			{
				System.out.println("DATOS ERROR getRolOpcion(): "+ e.getMessage());
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
		
		// Metodo para modificar Rol Opciones
		public boolean modificarRolOpcion (RolOpcion ro)
		{
			boolean modificado=false;	
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsRolUser(c);
				rsRolOpc.beforeFirst();
				while (rsRolOpc.next())
				{
					if(rsRolOpc.getInt(1)==ro.getIdrol_opc())
					{
						rsRolOpc.updateInt("id_opc", ro.getId_opc());
						rsRolOpc.updateInt("rolid", ro.getRolid());	
						rsRolOpc.updateRow();
						modificado=true;
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.err.println("ERROR AL ACTUALIZAR Pais"+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsRolOpc != null){
						rsRolOpc.close();
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
		
		//Metodo para visualizar las opciones de un rol
		public ArrayList<ViewRolOpcion> listaRolOpc(int idRol){
			ArrayList<ViewRolOpcion> listropc = new ArrayList<ViewRolOpcion>();
			try{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("select * from vw_rol_opciones where rolid=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				ps.setInt(1, idRol);
				rs = ps.executeQuery();
				while(rs.next()){
					ViewRolOpcion vwrop = new ViewRolOpcion();
					vwrop.setIdrol_opc(rs.getInt("idrol_opc"));
					vwrop.setRolid(rs.getInt("rolid"));
					vwrop.setRol(rs.getString("rol"));
					vwrop.setId_opc(rs.getInt("id_opc"));
					vwrop.setOpcion(rs.getString("opcion"));
					listropc.add(vwrop);
				}
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN listaRolOpc "+ e.getMessage());
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
			return listropc;
		}
}
