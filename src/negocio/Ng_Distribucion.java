package negocio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import datos.PoolConexion;

public class Ng_Distribucion {
	        //Atributos
			PoolConexion pc = PoolConexion.getInstance(); 
			Connection c = null;
			private ResultSet rs = null;
			private PreparedStatement ps = null;
			
			// Metodo para validar el distribucion 
			public boolean existeDistribucion(String Distribucion){
				boolean existe = false;
				try{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select * from distribucion where nombre=? and estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					ps.setString(1, Distribucion);
					rs = ps.executeQuery();
					if(rs.next()){
						existe=true;
					}
				}
				catch (Exception e){
					System.out.println("DATOS ERROR existeDistribucion(): "+ e.getMessage());
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
				return existe;
			}
			
			// Metodo para validar actualizar distribucion
						public boolean existeActualizarDistribucion(int arbolid, String distribucion){
							boolean existe = false;
							try{
								c = PoolConexion.getConnection();
								ps = c.prepareStatement("SELECT * FROM distribucion WHERE distribucionid != ? AND nombre = ? and estado <>3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
								ps.setInt(1, arbolid);				
								ps.setString(2, distribucion);
								rs = ps.executeQuery();
								if(rs.next()){
									existe=true;
								}
							}
							catch (Exception e){
								System.out.println("DATOS ERROR existeActualizarArbol(): "+ e.getMessage());
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
							return existe;
						}
}
