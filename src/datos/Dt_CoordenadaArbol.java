package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vistas.ViewCoordenadaArbol;
import entidades.Coordenada;
import entidades.CoordenadaArbol;

public class Dt_CoordenadaArbol {
	//Atributos
		PoolConexion pc = PoolConexion.getInstance(); 
		Connection c = null;
		private ResultSet rsCoordenadaArbol = null;
		private ResultSet rs = null;
		private PreparedStatement ps = null;
		
	// Metodo para llenar el RusultSet
			public void llenaRsCoordenadaArbol(Connection c){
				try{
					ps = c.prepareStatement("SELECT coordenadaarbolid, coordenadaid, arbolid,estado FROM coordenadaarbol;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					rsCoordenadaArbol = ps.executeQuery();
				}
				catch (Exception e){
					System.out.println("DATOS: ERROR EN LISTAR COORDENADA ARBOL "+ e.getMessage());
					e.printStackTrace();
				}
			}
	//Metodo para visualizar vista coordenada arbol
	public ArrayList<ViewCoordenadaArbol> listarCoordenadaArbol(){
		ArrayList<ViewCoordenadaArbol> listCoordenadaArbol = new ArrayList<ViewCoordenadaArbol>();
		try{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("select * from vw_coordenadaarbol", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rs = ps.executeQuery();
			while(rs.next()){
				ViewCoordenadaArbol vwCoAr = new ViewCoordenadaArbol();				
				vwCoAr.setCoordenadaid(rs.getInt("coordenadaarbolid"));				
				vwCoAr.setNombre(rs.getString("nombre"));
				vwCoAr.setDescco(rs.getString("descco"));
				vwCoAr.setLatitud(rs.getString("latitud"));
				vwCoAr.setLongitud(rs.getString("longitud"));	
				vwCoAr.setNombrecomun(rs.getString("nombrecomun"));
				vwCoAr.setNombrecientifico(rs.getString("nombrecientifico"));			
				vwCoAr.setDescar(rs.getString("descar"));	
				vwCoAr.setMultimedia(rs.getString("multimedia"));				
				listCoordenadaArbol.add(vwCoAr);
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
		return listCoordenadaArbol;
	}	
	//Metodo para visualizar coordenada
		public ArrayList<Coordenada> listarCoordenadas(){
			ArrayList<Coordenada> listCoordenada = new ArrayList<Coordenada>();
			try{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("SELECT estado, latitud, longitud, descripcion, nombre, coordenadaid from coordenada", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rs = ps.executeQuery();
				while(rs.next()){
					Coordenada Co = new Coordenada();
					Co.setCoordenadaID(rs.getInt("coordenadaid"));
					Co.setNombre(rs.getString("nombre"));
					Co.setDescripcion(rs.getString("descripcion"));
					Co.setLatitud(rs.getString("latitud"));
					Co.setLongitud(rs.getString("longitud"));					
					Co.setEstado(rs.getInt("estado"));			
					listCoordenada.add(Co);
				}
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR COORDENADA "+ e.getMessage());
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
		
		//Metodo para asignar coordenada a arbol
			public boolean guardarCoordenadaArbol(CoordenadaArbol ca){
				boolean guardado = false;
				
				try{
					c = PoolConexion.getConnection();
					this.llenaRsCoordenadaArbol(c);
					rsCoordenadaArbol.moveToInsertRow();
					rsCoordenadaArbol.updateInt("coordenadaid", ca.getCoordenadaid());
					rsCoordenadaArbol.updateInt("arbolid", ca.getArbolid());
					rsCoordenadaArbol.updateInt("estado", 1);
					rsCoordenadaArbol.insertRow();
					rsCoordenadaArbol.moveToCurrentRow();
					guardado = true;
				}
				catch (Exception e) {
					System.err.println("ERROR AL GUARDAR COORDENADA ARBOL "+e.getMessage());
					e.printStackTrace();
				}
				finally{
					try {
						if(rsCoordenadaArbol != null){
							rsCoordenadaArbol.close();
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
				
		
			// Metodo para eliminar registro de coordenada
			public boolean eliminarCoordenadaArbol(int idCoA)
			{
				boolean eliminado=false;	
				try
				{
					c = PoolConexion.getConnection();
					this.llenaRsCoordenadaArbol(c);
					rsCoordenadaArbol.beforeFirst();
					while (rsCoordenadaArbol.next())
					{
						if(rsCoordenadaArbol.getInt(1)==idCoA)
						{
							rsCoordenadaArbol.deleteRow();
							eliminado=true;
							break;
						}
					}
				}
				catch (Exception e)
				{
					System.err.println("ERROR AL ELIMINAR ASIGNACION COORDENADA "+e.getMessage());
					e.printStackTrace();
				}
				finally
				{
					try {
						if(rsCoordenadaArbol != null){
							rsCoordenadaArbol.close();
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