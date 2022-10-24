package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.Opcion;

public class Dt_Opcion {
	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsOpc = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	
	//Metodo para visualizar paises 
		public ArrayList<Opcion> listarOpc(){
			ArrayList<Opcion> listOpc = new ArrayList<Opcion>();
			try{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("select id_opc, opcion, desc_opc, estado from opciones where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rs = ps.executeQuery();
				while(rs.next()){
					Opcion opc = new Opcion();
					opc.setId_opc(rs.getInt("id_opc"));
					opc.setOpcion(rs.getString("opcion"));
					opc.setDesc_opc(rs.getString("desc_opc"));				
					opc.setEstado(rs.getInt("Estado"));		
					listOpc.add(opc);
				}
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR OPCION "+ e.getMessage());
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
			return listOpc;
		}
}
