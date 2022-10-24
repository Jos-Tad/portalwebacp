package datos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidades.Floracion;

public class Dt_Floracion {
		//Atributos
			PoolConexion pc = PoolConexion.getInstance(); 
			Connection c = null;
			private ResultSet rsFloracion = null;
			private ResultSet rs = null;
			private PreparedStatement ps = null;
			
			// Metodo para llenar el RusultSet
			public void llenaRsFloracion(Connection c){
				try{
					ps = c.prepareStatement("select * from floracion", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					rsFloracion = ps.executeQuery();
				}
				catch (Exception e){
					System.out.println("DATOS: ERROR EN LISTAR FLORACION "+ e.getMessage());
					e.printStackTrace();
				}
			}
			
			//Metodo para visualizar usuarios registrados y activos
			public ArrayList<Floracion> listaFloracion(){
				ArrayList<Floracion> listFloracion = new ArrayList<Floracion>();
				try{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select * from Floracion", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					rs = ps.executeQuery();
					while(rs.next()){
						Floracion floracion = new Floracion();
						floracion.setFloracionID(rs.getInt("FloracionID"));
						floracion.setNombre(rs.getString("Nombre"));
						floracion.setDescripcion(rs.getString("Descripcion"));	
						floracion.setTemporada(rs.getInt("Temporada"));	
						floracion.setEstado(rs.getInt("Estado"));		
						listFloracion.add(floracion);
					}
				}
				catch (Exception e){
					System.out.println("DATOS: ERROR EN LISTAR FLORACION "+ e.getMessage());
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
				return listFloracion;
			}
	
}
