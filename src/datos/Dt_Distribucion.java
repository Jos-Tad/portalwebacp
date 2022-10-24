package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.Distribucion;
import entidades.Familia;
import vistas.ViewDistribucion;


public class Dt_Distribucion {
	//Atributos
		PoolConexion pc = PoolConexion.getInstance(); 
		Connection c = null;
		private ResultSet rsDistribucion = null;
		private ResultSet rs = null;
		private PreparedStatement ps = null;
		
		// Metodo para llenar el RusultSet
		public void llenaRsDistribucion(Connection c){
			try{
				ps = c.prepareStatement("select distribucionid,nombre,descripcion,estado,paisid from distribucion where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rsDistribucion = ps.executeQuery();
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR DISTRIBUCION "+ e.getMessage());
				e.printStackTrace();
			}
		}
		

		//Metodo para visualizar distribucion para combobox
				public ArrayList<Distribucion> listaDistribucion(){
					ArrayList<Distribucion> listDistribucion = new ArrayList<Distribucion>();
					try{
						c = PoolConexion.getConnection();
						ps = c.prepareStatement("select distribucionid,nombre,descripcion,estado,paisid from distribucion where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
						rs = ps.executeQuery();
						while(rs.next()){
							Distribucion distribucion = new Distribucion();
							distribucion.setDistribucionID(rs.getInt("distribucionid"));
							distribucion.setNombre(rs.getString("nombre"));
							distribucion.setDescripcion(rs.getString("descripcion"));
							distribucion.setPaisID(rs.getInt("Paisid"));
							distribucion.setEstado(rs.getInt("estado"));
							listDistribucion.add(distribucion);
						}
					}
					catch (Exception e){
						System.out.println("DATOS: ERROR EN LISTAR DISTRIBUCION "+ e.getMessage());
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
					return listDistribucion;
				}
		
				
		//Metodo para visualizar vista distribucion
		public ArrayList<ViewDistribucion> listaViewDistribucion(){
			ArrayList<ViewDistribucion> listDistribucion = new ArrayList<ViewDistribucion>();
			try{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("select distribucionid,estado,descripcion,distribucion,pais from viewdistribucion where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rs = ps.executeQuery();
				while(rs.next()){
					ViewDistribucion distribucion = new ViewDistribucion();
					distribucion.setDistribucionID(rs.getInt("distribucionid"));
					distribucion.setDistribucion(rs.getString("distribucion"));
					distribucion.setDescripcion(rs.getString("descripcion"));						
					distribucion.setPais(rs.getString("pais"));
					distribucion.setEstado(rs.getInt("estado"));
					listDistribucion.add(distribucion);
				}
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR VIEWDISTRIBUCION "+ e.getMessage());
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
			return listDistribucion;
		}
		
	
		//Metodo para almacenar nueva distribucion
		public boolean guardarDistribucion(Distribucion distribucion){
			boolean guardado = false;
			
			try{
				c = PoolConexion.getConnection();
				this.llenaRsDistribucion(c);
				rsDistribucion.moveToInsertRow();
				rsDistribucion.updateString("Nombre", distribucion.getNombre());								
				rsDistribucion.updateString("Descripcion", distribucion.getDescripcion());			
				rsDistribucion.updateInt("PaisID", distribucion.getPaisID());
				rsDistribucion.updateInt("Estado", 1);
				rsDistribucion.insertRow();
				rsDistribucion.moveToCurrentRow();
				guardado = true;
			}
			catch (Exception e) {
				System.err.println("ERROR AL GUARDAR DISTRIBUCION "+e.getMessage());
				e.printStackTrace();
			}
			finally{
				try {
					if(rsDistribucion != null){
						rsDistribucion.close();
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
		
		// Metodo para visualizar los datos de una distribucion especifica
			public Distribucion getDistribucion(int idDistribucion)
			{
				Distribucion ds = new Distribucion();
				try
				{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select distribucionid, nombre, descripcion, paisid, estado from distribucion where estado <> 3 and distribucionid = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					ps.setInt(1, idDistribucion);
					rs = ps.executeQuery();
					if(rs.next())
					{
						ds.setDistribucionID(idDistribucion);
						ds.setNombre(rs.getString("nombre"));
						ds.setDescripcion(rs.getString("descripcion"));	
						ds.setPaisID(rs.getInt("paisid"));
						ds.setEstado(rs.getInt("estado"));
					}
				}
				catch (Exception e)
				{
					System.out.println("DATOS ERROR DISTRIBUCION: "+ e.getMessage());
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
						
						return ds;
			}
			
			
			
		// Metodo para eliminar region
		public boolean eliminarDistribucion(int idDistribucion)
		{
			boolean eliminado=false;	
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsDistribucion(c);
				rsDistribucion.beforeFirst();
				while (rsDistribucion.next())
				{
					if(rsDistribucion.getInt(1)==idDistribucion)
					{
						rsDistribucion.updateInt("estado", 3);
						rsDistribucion.updateRow();
						eliminado=true;
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.err.println("ERROR AL ElIMINAR Distribucion "+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsDistribucion != null){
						rsDistribucion.close();
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
		// Metodo para modificar familia
		public boolean modificarDistribucion (Distribucion ds)
		{
			boolean modificado=false;	
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsDistribucion(c);
				rsDistribucion.beforeFirst();
				while (rsDistribucion.next())
				{
					if(rsDistribucion.getInt(1)==ds.getDistribucionID())
					{
						rsDistribucion.updateString("nombre", ds.getNombre());
						rsDistribucion.updateString("descripcion", ds.getDescripcion());	
						rsDistribucion.updateInt("paisid", ds.getPaisID());
						rsDistribucion.updateInt("estado", 2);
						rsDistribucion.updateRow();
						modificado=true;
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.err.println("ERROR AL ACTUALIZAR DISTRIBUCION"+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsDistribucion != null){
						rsDistribucion.close();
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
		

	
	

}
