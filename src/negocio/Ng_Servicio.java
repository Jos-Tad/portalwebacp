package negocio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import datos.Dt_Servicio;
import datos.PoolConexion;
import entidades.Servicio;

public class Ng_Servicio {
		//Atributos
		PoolConexion pc = PoolConexion.getInstance(); 
		Connection c = null;
		private ResultSet rs = null;
		private PreparedStatement ps = null;
		
		// Metodo para validar el servicio 
		public boolean existeServicio(String servicio){
			boolean existe = false;
			try{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("select * from servicio where nombre=? and estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				ps.setString(1, servicio);
				rs = ps.executeQuery();
				if(rs.next()){
					existe=true;
				}
			}
			catch (Exception e){
				System.out.println("DATOS ERROR existeServicio(): "+ e.getMessage());
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
	
		public boolean validarVisibilidad(){
			//Bandera
			 boolean control = false;
			 ArrayList<Servicio> listServicio = new ArrayList<Servicio>();
		     Dt_Servicio dts = new Dt_Servicio();
			 listServicio = dts.listarServicio(); 	 	 
			 if(listServicio.size() == 0 || listServicio.stream().allMatch(x -> x.getEstadoservicio() == 2)){
				 control = true;
			 }else {
				 control = false;
			 }	
			 return control;
		}
		
		// Metodo para validar el actualizar servicio
		public boolean existeServicioActualizar(int servicioid, String nombre){
			boolean existe = false;
			try{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("SELECT * FROM servicio WHERE servicioid != ? AND nombre = ? and estado <>3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				ps.setInt(1, servicioid);				
				ps.setString(2, nombre);
				rs = ps.executeQuery();
				if(rs.next()){
					existe=true;
				}
			}
			catch (Exception e){
				System.out.println("DATOS error existeServicioActualizar(): "+ e.getMessage());
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
