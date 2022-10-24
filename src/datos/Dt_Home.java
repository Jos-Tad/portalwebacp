	package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.Home;

public class Dt_Home {

	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsHome = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	// Metodo para llenar el ResultSet
	public void llenaRHome(Connection c){
		try{
			ps = c.prepareStatement("select homeid, historia, mision, vision, img_historia, img_mision, img_vision, fmodificacion, estado, usuarioid from home", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rsHome = ps.executeQuery();
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN MOSTRA INICIO "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Metodo para visualizar Banners
	public ArrayList<Home> ListarHome(){

		//Crear lista de arreglos
		ArrayList<Home> listHome = new ArrayList<Home>();
		
		try{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("SELECT * FROM Home where Home.Estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rs = ps.executeQuery();
			while(rs.next()){
				Home hm = new Home();
				hm.setHomeID(rs.getInt("HomeID"));
				hm.setHistoria(rs.getString("Historia"));
				hm.setVision(rs.getString("Vision"));
				hm.setMision(rs.getString("Mision"));
				hm.setImg_historia(rs.getString("Img_historia"));
				hm.setImg_vision(rs.getString("Img_vision"));
				hm.setImg_mision(rs.getString("Img_mision"));
				hm.setEstado(rs.getInt("Estado"));
				hm.setUsuarioID(rs.getInt("UsuarioID"));
				listHome.add(hm);
			}
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN LISTAR Elementos del Banner "+ e.getMessage());
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
		return listHome;
	}
			
	// Metodo para modificar Inicio
	public boolean modificarInicio(Home hm)
	{
		boolean modificado=false;	
		try
		{
			c = PoolConexion.getConnection();
			this.llenaRHome(c);
			rsHome.beforeFirst();
			while (rsHome.next())
			{
				if(rsHome.getInt(1)==hm.getHomeID())
				{
					rsHome.updateString("historia", hm.getHistoria());
					rsHome.updateString("mision", hm.getMision());
					rsHome.updateString("vision", hm.getVision());
					rsHome.updateString("img_historia", hm.getImg_historia());
					rsHome.updateString("img_mision", hm.getImg_mision());
					rsHome.updateString("img_vision", hm.getImg_vision());
					rsHome.updateTimestamp("fmodificacion", hm.getFmodificacion());
					rsHome.updateInt("usuarioid", hm.getUsuarioID());							
					rsHome.updateInt("estado", 2);
					rsHome.updateRow();
					modificado=true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("ERROR AL ACTUALIZAR INICIO "+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rsHome != null){
					rsHome.close();
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
