package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.UbicacionArbol;
import vistas.ViewUbicacionArbol;

public class Dt_UbicacionArbol {
		//Atributos
		PoolConexion pc = PoolConexion.getInstance(); 
		Connection c = null;
		private ResultSet rsUbicacionArbol = null;
		private ResultSet rs = null;
		private PreparedStatement ps = null;
	
		// Metodo para llenar el RusultSet
				public void llenaRsArbol(Connection c){
					try{
						ps = c.prepareStatement("select * from ubicacionarbol", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
						rsUbicacionArbol = ps.executeQuery();
					}
					catch (Exception e){
						System.out.println("DATOS: ERROR EN LISTAR UBICACION ARBOL "+ e.getMessage());
						e.printStackTrace();
					}
				}
				
		    //Metodo para visualizar ubicacion arbol
			public ArrayList<ViewUbicacionArbol> listViewUbicacionArbol(){
				ArrayList<ViewUbicacionArbol> listViewUbicacionArbol = new ArrayList<ViewUbicacionArbol>();
				try{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select * from viewubicacionarbol where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					rs = ps.executeQuery();
					while(rs.next()){
						ViewUbicacionArbol vua = new ViewUbicacionArbol();
						vua.setUbicacionarbolid(rs.getInt("ubicacionarbolid"));
						vua.setNombrecientifico(rs.getString("nombrecientifico"));
						vua.setNombrecomun(rs.getString("nombrecomun"));
						vua.setNombredistribucion(rs.getString("nombredistribucion"));	
						vua.setNombrepais(rs.getString("nombrepais"));
						vua.setNombreregion(rs.getString("nombreregion"));
						listViewUbicacionArbol.add(vua);
					}
				}
				catch (Exception e){
					System.out.println("DATOS: ERROR EN LISTAR UBICACION ARBOL "+ e.getMessage());
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
				return listViewUbicacionArbol;
			}

				//Metodo para almacenar nueva ubicacion arbol
				public boolean guardarArbol(UbicacionArbol uarbol){
					boolean guardado = false;
					
					try{
						c = PoolConexion.getConnection();
						this.llenaRsArbol(c);
						rsUbicacionArbol.moveToInsertRow();
						rsUbicacionArbol.updateInt("arbolid", uarbol.getArbolid());
						rsUbicacionArbol.updateInt("distribucionid", uarbol.getDistribucionid());						
						rsUbicacionArbol.insertRow();
						rsUbicacionArbol.moveToCurrentRow();
						guardado = true;
					}
					catch (Exception e) {
						System.err.println("ERROR AL GUARDAR UBICACION ARBOL "+e.getMessage());
						e.printStackTrace();
					}
					finally{
						try {
							if(rsUbicacionArbol != null){
								rsUbicacionArbol.close();
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
				
				// Metodo para eliminar Ubicacion Arbol
				public boolean eliminarUbicacionArbol(int idA)
				{
					boolean eliminado=false;	
					try
					{
						c = PoolConexion.getConnection();
						this.llenaRsArbol(c);
						rsUbicacionArbol.beforeFirst();
						while (rsUbicacionArbol.next())
						{
							if(rsUbicacionArbol.getInt(1)== idA)
							{
								rsUbicacionArbol.deleteRow();
								eliminado=true;
								break;
							}
						}
					}
					catch (Exception e)
					{
						System.err.println("ERROR AL ELIMINAR UBICACION ARBOL "+e.getMessage());
						e.printStackTrace();
					}
					finally
					{
						try {
							if(rsUbicacionArbol != null){
								rsUbicacionArbol.close();
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
