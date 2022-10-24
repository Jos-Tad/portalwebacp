package datos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.Distribucion;
import entidades.Familia;
import entidades.Pais;
import entidades.Region;
import vistas.ViewPais;


public class Dt_Region {
	
	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsRegion = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	// Metodo para llenar el RusultSet
				public void llenaRsRegion(Connection c){
					try{
						ps = c.prepareStatement("select regionid, nombre, descripcion, estado from region where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
						rsRegion = ps.executeQuery();
					}
					catch (Exception e){
						System.out.println("DATOS: ERROR EN LISTAR REGION "+ e.getMessage());
						e.printStackTrace();
					}
				}
				
				
						
	
				//Metodo para almacenar nueva region
				public boolean guardarRegion(Region region){
					boolean guardado = false;
					
					try{
						c = PoolConexion.getConnection();
						this.llenaRsRegion(c);
						rsRegion.moveToInsertRow();
						rsRegion.updateString("Nombre", region.getNombre());											
						rsRegion.updateString("Descripcion", region.getDescripcion());	
						rsRegion.updateInt("Estado", 1);
						rsRegion.insertRow();
						rsRegion.moveToCurrentRow();
						guardado = true;
					}
					catch (Exception e) {
						System.err.println("ERROR AL GUARDAR REGION "+e.getMessage());
						e.printStackTrace();
					}
					finally{
						try {
							if(rsRegion != null){
								rsRegion.close();
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

				
	
				//Metodo para visualizar region
				public ArrayList<Region> listaRegion(){
					ArrayList<Region> listRegion = new ArrayList<Region>();
					try{
						c = PoolConexion.getConnection();
						ps = c.prepareStatement("select regionid, nombre, descripcion, estado  from region where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
						rs = ps.executeQuery();
						while(rs.next()){
							Region region = new Region();
							region.setRegionID(rs.getInt("RegionID"));
							region.setNombre(rs.getString("Nombre"));
							region.setDescripcion(rs.getString("Descripcion"));							
							region.setEstado(rs.getInt("Estado"));		
							listRegion.add(region);
						}
					}
					catch (Exception e){
						System.out.println("DATOS: ERROR EN LISTAR REGION "+ e.getMessage());
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
					return listRegion;
				}
				// Metodo para visualizar los datos de una region especifica
				public Region getRegion(int idRegion)
				{
					Region rg = new Region();
					try
					{
						c = PoolConexion.getConnection();
						ps = c.prepareStatement("select regionid, nombre, descripcion,  estado from region where estado <> 3 and regionid = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
						ps.setInt(1, idRegion);
						rs = ps.executeQuery();
						if(rs.next())
						{
							rg.setRegionID(idRegion);
							rg.setNombre(rs.getString("nombre"));
							rg.setDescripcion(rs.getString("descripcion"));							
							rg.setEstado(rs.getInt("estado"));
						}
					}
					catch (Exception e)
					{
						System.out.println("DATOS ERROR EN OBTENER REGION: "+ e.getMessage());
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
					
					return rg;
				}
				
				// Metodo para eliminar region
				public boolean eliminarRegion(int idRegion)
				{
					boolean eliminado=false;	
					try
					{
						c = PoolConexion.getConnection();
						this.llenaRsRegion(c);
						rsRegion.beforeFirst();
						while (rsRegion.next())
						{
							if(rsRegion.getInt(1)==idRegion)
							{
								rsRegion.updateInt("estado", 3);
								rsRegion.updateRow();
								eliminado=true;
								break;
							}
						}
					}
					catch (Exception e)
					{
						System.err.println("ERROR AL ElIMINAR REGION "+e.getMessage());
						e.printStackTrace();
					}
					finally
					{
						try {
							if(rsRegion != null){
								rsRegion.close();
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
				// Metodo para modificar region
				public boolean modificarRegion (Region rg)
				{
					boolean modificado=false;	
					try
					{
						c = PoolConexion.getConnection();
						this.llenaRsRegion(c);
						rsRegion.beforeFirst();
						while (rsRegion.next())
						{
							if(rsRegion.getInt(1)==rg.getRegionID())
							{
								rsRegion.updateString("nombre", rg.getNombre());
								rsRegion.updateString("descripcion", rg.getDescripcion());								
								rsRegion.updateInt("estado", 2);
								rsRegion.updateRow();
								modificado=true;
								break;
							}
						}
					}
					catch (Exception e)
					{
						System.err.println("ERROR AL ACTUALIZAR REGION"+e.getMessage());
						e.printStackTrace();
					}
					finally
					{
						try {
							if(rsRegion != null){
								rsRegion.close();
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
