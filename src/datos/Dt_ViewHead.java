package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vistas.ViewHead;

public class Dt_ViewHead {
		
	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsViewHead = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
			// Metodo para llenar el ResultSet
				public void llenarHome(Connection c){
					try{
						ps = c.prepareStatement("SELECT * FROM  viewHead order by viewHead.posicion asc", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
						rsViewHead = ps.executeQuery();
					}
					catch (Exception e){
						System.out.println("DATOS: ERROR en listar elementos de la cabecera "+ e.getMessage());
						e.printStackTrace();
					}
				}
				//Metodo para visualizar Head
				public ArrayList<ViewHead> ListarViewHead(){

					//Crear lista de arreglos
					ArrayList<ViewHead> listHead = new ArrayList<ViewHead>();
					
					try{
						c = PoolConexion.getConnection();
						ps = c.prepareStatement("SELECT * FROM viewHead order by viewHead.posicion asc ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
						rs = ps.executeQuery();
						while(rs.next()){
							ViewHead VvHead = new ViewHead();
							VvHead.setTitulo(rs.getString("titulo"));
							VvHead.setLogo(rs.getString("logo"));
							VvHead.setNombrehead(rs.getString("nombrehead"));
							VvHead.setEnlace(rs.getString("enlace"));
							VvHead.setPosicion(rs.getInt("posicion"));
							listHead.add(VvHead);
						}
					}
					catch (Exception e){
						System.out.println("Datos: Error en listar elementos del head "+ e.getMessage());
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
					return listHead;
				}
}
