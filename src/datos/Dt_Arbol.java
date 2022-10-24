package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.Arbol;
import entidades.Familia;
import vistas.ViewArbol;

public class Dt_Arbol {
	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsArbol = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	// Metodo para llenar el RusultSet
			public void llenaRsArbol(Connection c){
				try{
					ps = c.prepareStatement("select arbolid,nombrecientifico,nombrecomun,descripcion,estado,familiaid,generoid,floracionid,multimedia,fcreacion,fmodificacion,feliminacion,usuarioid from arbol", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					rsArbol = ps.executeQuery();
				}
				catch (Exception e){
					System.out.println("DATOS: ERROR EN LISTAR ARBOl "+ e.getMessage());
					e.printStackTrace();
				}
			}
			
			//Metodo para visualizar vista arbol
			public ArrayList<ViewArbol> listaArbol(){
				ArrayList<ViewArbol> listArbol = new ArrayList<ViewArbol>();
				try{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select arbolid,nombrecomun,nombrecientifico,descripcion,nombrefam,nombregenero,estado,multimedia,nombreflo from viewarbol where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					rs = ps.executeQuery();
					while(rs.next()){
						ViewArbol arbol = new ViewArbol();
						arbol.setArbolID(rs.getInt("arbolid"));
						arbol.setNombreComun(rs.getString("NombreComun"));
						arbol.setNombreCientifico(rs.getString("NombreCientifico"));
						arbol.setDescripcion(rs.getString("Descripcion"));					
						arbol.setMultimedia(rs.getString("Multimedia"));	
						arbol.setNombreFam(rs.getString("NombreFam"));
						arbol.setNombreFlo(rs.getString("NombreFlo"));
						arbol.setNombreGenero(rs.getString("NombreGenero"));						
						arbol.setEstado(rs.getInt("Estado"));		
						listArbol.add(arbol);
					}
				}
				catch (Exception e){
					System.out.println("DATOS: ERROR EN LISTAR viewARBOL "+ e.getMessage());
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
				return listArbol;
			}	
			
			//Metodo para almacenar nuevo arbol
			public boolean guardarArbol(Arbol arbol){
				boolean guardado = false;
				
				try{
					c = PoolConexion.getConnection();
					this.llenaRsArbol(c);
					rsArbol.moveToInsertRow();
					rsArbol.updateString("NombreComun", arbol.getNombreComun());
					rsArbol.updateString("NombreCientifico", arbol.getNombreCientifico());					
					rsArbol.updateString("Descripcion", arbol.getDescripcion());
					rsArbol.updateString("Multimedia", arbol.getMultimedia());					
					rsArbol.updateInt("FloracionID", arbol.getFloracionID());
					rsArbol.updateInt("FamiliaID", arbol.getFamiliaID());
					rsArbol.updateInt("GeneroID", arbol.getGeneroID());					
					rsArbol.updateTimestamp("fcreacion", arbol.getFcreacion());
					rsArbol.updateInt("UsuarioID", arbol.getUsuarioId());
					rsArbol.updateInt("Estado", 1);
					rsArbol.insertRow();
					rsArbol.moveToCurrentRow();
					guardado = true;
				}
				catch (Exception e) {
					System.err.println("ERROR AL GUARDAR ARBOL "+e.getMessage());
					e.printStackTrace();
				}
				finally{
					try {
						if(rsArbol != null){
							rsArbol.close();
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
			 // Metodo para restaurar arbol
			public boolean restaurarArbol(int idArbol)
			{
				boolean actualizado = false;
				
				try
				{
					c = PoolConexion.getConnection();
					this.llenaRsArbol(c);	
					rsArbol.beforeFirst();
					while(rsArbol.next())
					{
						if(rsArbol.getInt(1)==idArbol)
						{							
							rsArbol.updateInt("estado", 1);
							rsArbol.updateRow();
							actualizado = true;
							break;
						}
					}
				}
				catch (Exception e) 
				{
					System.err.println("ERROR AL RESTAURAR ARBOL "+e.getMessage());
					e.printStackTrace();
				}
				finally
				{
					try {
						if(rsArbol != null){
							rsArbol.close();
						}
						if(c != null){
							PoolConexion.closeConnection(c);
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				return actualizado;
			}	
		// Metodo para eliminar Arbol
			public boolean eliminarArbol(int idArbol)
			{
				boolean eliminado=false;	
				try
				{
					c = PoolConexion.getConnection();
					this.llenaRsArbol(c);
					rsArbol.beforeFirst();
					while (rsArbol.next())
					{
						if(rsArbol.getInt(1)==idArbol)
						{
							rsArbol.updateInt("estado", 3);
							rsArbol.updateRow();
							eliminado=true;
							break;
						}
					}
				}
				catch (Exception e)
				{
					System.err.println("ERROR AL ElIMINAR ARBOL "+e.getMessage());
					e.printStackTrace();
				}
				finally
				{
					try {
						if(rsArbol != null){
							rsArbol.close();
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
						
		// Metodo para visualizar los datos de un arbol especifico
		public Arbol getArbol(int idArbol)
		{
			Arbol ar = new Arbol();
			try
			{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("select arbolid,nombrecientifico,nombrecomun,descripcion,estado,familiaid,generoid,floracionid,multimedia from arbol where estado <> 3 and arbolid = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				ps.setInt(1, idArbol);
				rs = ps.executeQuery();
				if(rs.next())
				{
					ar.setArbolID(idArbol);
					ar.setNombreCientifico(rs.getString("nombrecientifico"));
					ar.setDescripcion(rs.getString("descripcion"));
					ar.setMultimedia(rs.getString("multimedia"));
					ar.setNombreComun(rs.getString("nombrecomun"));
					ar.setFamiliaID(rs.getInt("familiaid"));									
					ar.setFloracionID(rs.getInt("floracionid"));									
					ar.setGeneroID(rs.getInt("generoid"));
					ar.setEstado(rs.getInt("estado"));
				}
			}
			catch (Exception e)
			{
				System.out.println("DATOS ERROR ARBOL: "+ e.getMessage());
				e.printStackTrace();
			}
			finally
			{
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
			return ar;
		}
		
		//Metodos para modificar arbol
		public boolean modificarArbol (Arbol ar)
		{
			boolean modificado=false;	
			try
			{
				c = PoolConexion.getConnection();
				this.llenaRsArbol(c);
				rsArbol.beforeFirst();
				while (rsArbol.next())
				{
					if(rsArbol.getInt(1)==ar.getArbolID())
					{
						rsArbol.updateString("nombrecientifico", ar.getNombreCientifico());
						rsArbol.updateString("nombrecomun", ar.getNombreComun());
						rsArbol.updateString("descripcion", ar.getDescripcion());
						rsArbol.updateString("multimedia", ar.getMultimedia());										
						rsArbol.updateInt("familiaid", ar.getFamiliaID());
						rsArbol.updateInt("generoid", ar.getGeneroID());
						rsArbol.updateInt("floracionid", ar.getFloracionID());	
						rsArbol.updateInt("UsuarioID", ar.getUsuarioId());										
						rsArbol.updateInt("estado", 2);
						rsArbol.updateRow();
						modificado=true;
						break;
					}
				}
			}
			catch (Exception e)
			{
				System.err.println("ERROR AL ACTUALIZAR FAMILIA"+e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				try {
					if(rsArbol != null){
						rsArbol.close();
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
		//Metodo para visualizar vista arbol para restaurar
		public ArrayList<ViewArbol> listaArbolR(){
			ArrayList<ViewArbol> listArbolR = new ArrayList<ViewArbol>();
			try{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("select arbolid,nombrecomun,nombrecientifico,descripcion,nombrefam,nombregenero,estado,multimedia,nombreflo from viewarbol where estado = 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rs = ps.executeQuery();
				while(rs.next()){
					ViewArbol arbol = new ViewArbol();
					arbol.setArbolID(rs.getInt("arbolid"));
					arbol.setNombreComun(rs.getString("NombreComun"));
					arbol.setNombreCientifico(rs.getString("NombreCientifico"));
					arbol.setDescripcion(rs.getString("Descripcion"));					
					arbol.setMultimedia(rs.getString("Multimedia"));	
					arbol.setNombreFam(rs.getString("NombreFam"));
					arbol.setNombreFlo(rs.getString("NombreFlo"));
					arbol.setNombreGenero(rs.getString("NombreGenero"));						
					arbol.setEstado(rs.getInt("Estado"));		
					listArbolR.add(arbol);
				}
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR viewARBOL "+ e.getMessage());
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
			return listArbolR;
		}	
		
		
   
}
