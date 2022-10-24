package negocio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import datos.PoolConexion;

public class Ng_Coordenada {
	//Atributos
			PoolConexion pc = PoolConexion.getInstance(); 
			Connection c = null;
			private ResultSet rs = null;
			private PreparedStatement ps = null;
			
			// Metodo para validar el nombre de la coordenada
			public boolean existeCoordenada(String Coordenada){
				boolean existe = false;
				try{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select * from coordenada where nombre=? and estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					ps.setString(1, Coordenada);
					rs = ps.executeQuery();
					if(rs.next()){
						existe=true;
					}
				}
				catch (Exception e){
					System.out.println("DATOS ERROR existeCoordenada(): "+ e.getMessage());
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
			
			// Metodo para validar la latitud de la coordenada
						public boolean existeLatitud(String Latitud){
							boolean existe = false;
							try{
								c = PoolConexion.getConnection();
								ps = c.prepareStatement("select * from coordenada where latitud=? and estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
								ps.setString(1, Latitud);
								rs = ps.executeQuery();
								if(rs.next()){
									existe=true;
								}
							}
							catch (Exception e){
								System.out.println("DATOS ERROR existeLatitud(): "+ e.getMessage());
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
						
			// Metodo para validar la longitud de la coordenada
						public boolean existeLongitud(String Longitud){
							boolean existe = false;
							try{
								c = PoolConexion.getConnection();
								ps = c.prepareStatement("select * from coordenada where longitud=? and estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
								ps.setString(1, Longitud);
								rs = ps.executeQuery();
								if(rs.next()){
									existe=true;
								}
							}
							catch (Exception e){
								System.out.println("DATOS ERROR existeLongitud(): "+ e.getMessage());
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