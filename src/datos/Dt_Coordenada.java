package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.Banner;
import entidades.Coordenada;

public class Dt_Coordenada {
	
	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsCoordenada = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
			
	// Metodo para llenar el RusultSet
	public void llenaRsCoordenada(Connection c){
		try{
			ps = c.prepareStatement("SELECT coordenadaid, nombre, descripcion, latitud, longitud, estado, usuarioid FROM coordenada;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rsCoordenada = ps.executeQuery();
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN LISTAR COORDENADA "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Metodo para visualizar vista coordenada
	public ArrayList<Coordenada> listarCoordenada(){
		ArrayList<Coordenada> listCoordenada = new ArrayList<Coordenada>();
		try{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("SELECT coordenadaid, nombre, descripcion, latitud, longitud, usuarioid, estado <> 3 FROM coordenada", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rs = ps.executeQuery();
			while(rs.next()){
				Coordenada co = new Coordenada();				
				co.setCoordenadaID(rs.getInt("coordenadaid"));				
				co.setNombre(rs.getString("nombre"));
				co.setDescripcion(rs.getString("descripcion"));
				co.setLatitud(rs.getString("latitud"));
				co.setLongitud(rs.getString("longitud"));
				co.setUsuarioID(rs.getInt("usuarioid"));
				listCoordenada.add(co);
			}
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN LISTAR VIEW COORDENADA ARBOL "+ e.getMessage());
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
		return listCoordenada;
	}
	// Metodo para eliminar Coordenada
	public boolean eliminarCoordenada(int idC)
	{
		boolean eliminado=false;	
		try
		{
			c = PoolConexion.getConnection();
			this.llenaRsCoordenada(c);
			rsCoordenada.beforeFirst();
			while (rsCoordenada.next())
			{
				if(rsCoordenada.getInt(1)==idC)
				{
					rsCoordenada.deleteRow();
					eliminado=true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("ERROR AL ELIMINAR COORDENADA "+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rsCoordenada != null){
					rsCoordenada.close();
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
	
	
	//Metodo para almacenar nuevo coordenada
	public boolean guardarCoordenada(Coordenada Co){
		boolean guardado = false;
		
		try{
			c = PoolConexion.getConnection();
			this.llenaRsCoordenada(c);
			rsCoordenada.moveToInsertRow();
			rsCoordenada.updateString("nombre", Co.getNombre());
			rsCoordenada.updateString("descripcion", Co.getDescripcion());
			rsCoordenada.updateString("latitud", Co.getLatitud());
			rsCoordenada.updateString("longitud", Co.getLongitud());				
			rsCoordenada.updateInt("estado", 1);
			rsCoordenada.updateInt("usuarioid", Co.getUsuarioID());
			rsCoordenada.insertRow();
			rsCoordenada.moveToCurrentRow();
			guardado = true;
		}
		catch (Exception e) {
			System.err.println("Error al guardar Coordenada "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				if(rsCoordenada != null){
					rsCoordenada.close();
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
}
