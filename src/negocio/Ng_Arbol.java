package negocio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import datos.PoolConexion;

public class Ng_Arbol {
	//Atributos
			PoolConexion pc = PoolConexion.getInstance(); 
			Connection c = null;
			private ResultSet rs = null;
			private PreparedStatement ps = null;
			
			// Metodo para validar el nombre comun
			public boolean existeArbol(String Arbol){
				boolean existe = false;
				try{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select * from arbol where nombrecomun=? and estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					ps.setString(1, Arbol);
					rs = ps.executeQuery();
					if(rs.next()){
						existe=true;
					}
				}
				catch (Exception e){
					System.out.println("DATOS ERROR existeArbol(): "+ e.getMessage());
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
			
			// Metodo para validar nombre cientifico
						public boolean existeNombre(String Arbol){
							boolean existe = false;
							try{
								c = PoolConexion.getConnection();
								ps = c.prepareStatement("select * from arbol where nombrecientifico=? and estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
								ps.setString(1, Arbol);
								rs = ps.executeQuery();
								if(rs.next()){
									existe=true;
								}
							}
							catch (Exception e){
								System.out.println("DATOS ERROR existeNombre(): "+ e.getMessage());
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
			// Metodo para validar actualizar nombre comun
			public boolean existeActualizarArbol(int arbolid, String nombreComun){
				boolean existe = false;
				try{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("SELECT * FROM arbol WHERE arbolid != ? AND nombrecomun = ? and estado <>3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					ps.setInt(1, arbolid);				
					ps.setString(2, nombreComun);
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
			// Metodo para validar actualizar nombre cientifico
						public boolean existeActualizarNombre(int arbolid, String nombreCientifico){
							boolean existe = false;
							try{
								c = PoolConexion.getConnection();
								ps = c.prepareStatement("SELECT * FROM arbol WHERE arbolid != ? AND nombrecientifico = ? and estado <>3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
								ps.setInt(1, arbolid);				
								ps.setString(2, nombreCientifico);
								rs = ps.executeQuery();
								if(rs.next()){
									existe=true;
								}
							}
							catch (Exception e){
								System.out.println("DATOS ERROR existeActualizaNombre(): "+ e.getMessage());
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

