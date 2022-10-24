package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.Distribucion;
import entidades.Familia;
import entidades.Pais;
import vistas.ViewPais;

public class Dt_Pais {
	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsPais = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	// Metodo para llenar el RusultSet
	public void llenaRsPais(Connection c){
		try{
			ps = c.prepareStatement("select paisid,nombre,descripcion,estado,regionid from pais where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rsPais = ps.executeQuery();
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN LISTAR PAIS "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	// listar para combobox
	public ArrayList<Pais> listaPais(){
		ArrayList<Pais> listPais = new ArrayList<Pais>();
		try{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("select paisid,nombre,descripcion,estado,regionid from pais where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rs = ps.executeQuery();
			while(rs.next()){
				Pais pais = new Pais();
				pais.setPaisID(rs.getInt("paisid"));
				pais.setNombre(rs.getString("nombre"));
				pais.setDescripcion(rs.getString("descripcion"));
				pais.setRegionID(rs.getInt("regionid"));
				pais.setEstado(rs.getInt("estado"));
				listPais.add(pais);
			}
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN LISTAR PAIS"+ e.getMessage());
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
		return listPais;
	}

	//Metodo para visualizar region
	public ArrayList<ViewPais> listaViewPais(){
		ArrayList<ViewPais> listPais = new ArrayList<ViewPais>();
		try{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("select paisid, nombre, descripcion,  estado, region from viewpais where estado <> 3"
					+ "", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rs = ps.executeQuery();
			while(rs.next()){
				ViewPais pais = new ViewPais();
				pais.setPaisID(rs.getInt("paisid"));
				pais.setNombre(rs.getString("nombre"));
				pais.setDescripcion(rs.getString("descripcion"));
				pais.setEstado(rs.getInt("estado"));	
				pais.setRegion(rs.getString("region"));						
				listPais.add(pais);
			}
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN LISTAR PAIS "+ e.getMessage());
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
		return listPais;
	}
	
	

	//Metodo para almacenar nuevo pais
		public boolean guardarPais(Pais pais){
			boolean guardado = false;
			
			try{
				c = PoolConexion.getConnection();
				this.llenaRsPais(c);
				rsPais.moveToInsertRow();
				rsPais.updateString("Nombre", pais.getNombre());				
				rsPais.updateString("Descripcion", pais.getDescripcion());
				rsPais.updateInt("RegionID", pais.getRegionID());
				rsPais.updateInt("Estado", 1);
				rsPais.insertRow();
				rsPais.moveToCurrentRow();
				guardado = true;
			}
			catch (Exception e) {
				System.err.println("ERROR AL GUARDAR PAIS "+e.getMessage());
				e.printStackTrace();
			}
			finally{
				try {
					if(rsPais != null){
						rsPais.close();
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
		
				// Metodo para visualizar los datos de una familia especifica
					public Pais getPais(int idPais)
					{
						Pais p = new Pais();
						try
						{
							c = PoolConexion.getConnection();
							ps = c.prepareStatement("select paisid, nombre, descripcion, estado, regionid from pais where estado <> 3 and paisid = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
							ps.setInt(1, idPais);
							rs = ps.executeQuery();
							if(rs.next())
							{
								p.setPaisID(idPais);
								p.setNombre(rs.getString("nombre"));
								p.setDescripcion(rs.getString("descripcion"));
								p.setRegionID(rs.getInt("regionid"));
								p.setEstado(rs.getInt("estado"));
							}
						}
						catch (Exception e)
						{
							System.out.println("DATOS ERROR AL OBTENER PAIS: "+ e.getMessage());
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
						
						return p;
					}
					
					// Metodo para modificar pais
					public boolean modificarPais (Pais p)
					{
						boolean modificado=false;	
						try
						{
							c = PoolConexion.getConnection();
							this.llenaRsPais(c);
							rsPais.beforeFirst();
							while (rsPais.next())
							{
								if(rsPais.getInt(1)==p.getPaisID())
								{
									rsPais.updateString("nombre", p.getNombre());
									rsPais.updateString("descripcion", p.getDescripcion());
									rsPais.updateInt("regionid", p.getRegionID());
									rsPais.updateInt("estado", 2);
									rsPais.updateRow();
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
								if(rsPais != null){
									rsPais.close();
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
					
					
					
					// Metodo para eliminar Pais
					public boolean eliminarPais(int idPais)
					{
						boolean eliminado=false;	
						try
						{
							c = PoolConexion.getConnection();
							this.llenaRsPais(c);
							rsPais.beforeFirst();
							while (rsPais.next())
							{
								if(rsPais.getInt(1)==idPais)
								{
									rsPais.updateInt("estado", 3);
									rsPais.updateRow();
									eliminado=true;
									break;
								}
							}
						}
						catch (Exception e)
						{
							System.err.println("ERROR AL ElIMINAR PAIS "+e.getMessage());
							e.printStackTrace();
						}
						finally
						{
							try {
								if(rsPais != null){
									rsPais.close();
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

}

