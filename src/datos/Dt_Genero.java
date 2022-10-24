package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import entidades.Genero;


public class Dt_Genero {
	//Atributos
		PoolConexion pc = PoolConexion.getInstance(); 
		Connection c = null;
		private ResultSet rsGenero = null;
		private ResultSet rs = null;
		private PreparedStatement ps = null;
		
		// Metodo para llenar el RusultSet
		public void llenaRsGenero(Connection c){
			try{
				ps = c.prepareStatement("select generoid, nombre, descripcion, estado from genero where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rsGenero = ps.executeQuery();
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR GENERO "+ e.getMessage());
				e.printStackTrace();
			}
		}
		//Metodo para visualizar el genero
			public ArrayList<Genero> listaGenero(){
				ArrayList<Genero> listGenero = new ArrayList<Genero>();
				try{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select generoid, nombre, descripcion, estado from genero  where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					rs = ps.executeQuery();
					while(rs.next()){
						Genero genero = new Genero();
						genero.setGeneroID(rs.getInt("generoid"));
						genero.setNombre(rs.getString("nombre"));
						genero.setDescripcion(rs.getString("descripcion"));
						genero.setEstado(rs.getInt("estado"));	
						listGenero.add(genero);
					}
				}
				catch (Exception e){
					System.out.println("DATOS: ERROR EN LISTAR GENERO "+ e.getMessage());
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
				return listGenero;
			}
			//Metodo para almacenar nuevo genero
			public boolean guardarGenero(Genero genero){
				boolean guardado = false;
				
				try{
					c = PoolConexion.getConnection();
					this.llenaRsGenero(c);
					rsGenero.moveToInsertRow();
					rsGenero.updateString("Nombre", genero.getNombre());				
					rsGenero.updateString("Descripcion", genero.getDescripcion());			
					rsGenero.updateInt("Estado", 1);
					rsGenero.insertRow();
					rsGenero.moveToCurrentRow();
					guardado = true;
				}
				catch (Exception e) {
					System.err.println("ERROR AL GUARDAR PAIS "+e.getMessage());
					e.printStackTrace();
				}
				finally{
					try {
						if(rsGenero != null){
							rsGenero.close();
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
			
			// Metodo para visualizar los datos de un genero especifico
			public Genero getGenero(int idGenero)
			{
				Genero gn = new Genero();
				try
				{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select generoid, nombre, descripcion, estado from genero where estado <> 3 and generoid = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					ps.setInt(1, idGenero);
					rs = ps.executeQuery();
					if(rs.next())
					{
						gn.setGeneroID(idGenero);
						gn.setNombre(rs.getString("nombre"));
						gn.setDescripcion(rs.getString("descripcion"));						
						gn.setEstado(rs.getInt("estado"));
					}
				}
				catch (Exception e)
				{
					System.out.println("DATOS ERROR GENERO: "+ e.getMessage());
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
				
				return gn;
			}
			// Metodo para eliminar genero
						public boolean eliminarGenero(int idGenero)
						{
							boolean eliminado=false;	
							try
							{
								c = PoolConexion.getConnection();
								this.llenaRsGenero(c);
								rsGenero.beforeFirst();
								while (rsGenero.next())
								{
									if(rsGenero.getInt(1)==idGenero)
									{
										rsGenero.updateInt("estado", 3);
										rsGenero.updateRow();
										eliminado=true;
										break;
									}
								}
							}
							catch (Exception e)
							{
								System.err.println("ERROR AL ElIMINAR GENERO"+e.getMessage());
								e.printStackTrace();
							}
							finally
							{
								try {
									if(rsGenero != null){
										rsGenero.close();
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
						
						// Metodo para modificar genero
						public boolean modificarGenero (Genero gn)
						{
							boolean modificado=false;	
							try
							{
								c = PoolConexion.getConnection();
								this.llenaRsGenero(c);
								rsGenero.beforeFirst();
								while (rsGenero.next())
								{
									if(rsGenero.getInt(1)==gn.getGeneroID())
									{
										rsGenero.updateString("nombre", gn.getNombre());
										rsGenero.updateString("descripcion", gn.getDescripcion());	
										rsGenero.updateInt("estado", 2);
										rsGenero.updateRow();
										modificado=true;
										break;
									}
								}
							}
							catch (Exception e)
							{
								System.err.println("ERROR AL ACTUALIZAR GENERO"+e.getMessage());
								e.printStackTrace();
							}
							finally
							{
								try {
									if(rsGenero != null){
										rsGenero.close();
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
