package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidades.Familia;



public class Dt_Familia{

	//Atributos
		PoolConexion pc = PoolConexion.getInstance(); 
		Connection c = null;
		private ResultSet rsFamilia = null;
		private ResultSet rs = null;
		private PreparedStatement ps = null;
		
		// Metodo para llenar el RusultSet
		public void llenaRsFamilia(Connection c){
			try{
				ps = c.prepareStatement("select familiaid, nombre, descripcion, estado from familia where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rsFamilia = ps.executeQuery();
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR FAMILIA "+ e.getMessage());
				e.printStackTrace();
			}
		}
		
		//Metodo para visualizar familia
		public ArrayList<Familia> listaFamilia(){
			ArrayList<Familia> listFamilia = new ArrayList<Familia>();
			try{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("select familiaid, nombre, descripcion, estado from familia  where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rs = ps.executeQuery();
				while(rs.next()){
					Familia familia = new Familia();
					familia.setFamiliaID(rs.getInt("familiaID"));
					familia.setNombre(rs.getString("nombre"));
					familia.setDescripcion(rs.getString("descripcion"));			
					familia.setEstado(rs.getInt("estado"));		
					listFamilia.add(familia);
				}
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR FAMILIA "+ e.getMessage());
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
			return listFamilia;
		}
		
			//Metodo para almacenar nueva familia
			public boolean guardarFamilia(Familia fm){
				boolean guardado = false;
				
				try{
					c = PoolConexion.getConnection();
					this.llenaRsFamilia(c);
					rsFamilia.moveToInsertRow();
					rsFamilia.updateString("nombre", fm.getNombre());
					rsFamilia.updateString("descripcion", fm.getDescripcion());
					rsFamilia.updateInt("estado", 1);
					rsFamilia.insertRow();
					rsFamilia.moveToCurrentRow();
					guardado = true;
				}
				catch (Exception e) {
					System.err.println("ERROR AL GUARDAR FAMILIA "+e.getMessage());
					e.printStackTrace();
				}
				finally{
					try {
						if(rsFamilia != null){
							rsFamilia.close();
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
		
			
			// Metodo para visualizar los datos de una familia especifica
			public Familia getFamilia(int idFamilia)
			{
				Familia fm = new Familia();
				try
				{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select familiaid, nombre, descripcion, estado from familia where estado <> 3 and familiaid = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					ps.setInt(1, idFamilia);
					rs = ps.executeQuery();
					if(rs.next())
					{
						fm.setFamiliaID(idFamilia);
						fm.setNombre(rs.getString("nombre"));
						fm.setDescripcion(rs.getString("descripcion"));					
						fm.setEstado(rs.getInt("estado"));
					}
				}
				catch (Exception e)
				{
					System.out.println("DATOS ERROR FAMILIA: "+ e.getMessage());
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
				
				return fm;
			}
			
			// Metodo para modificar familia
			public boolean modificarFamilia (Familia fm)
			{
				boolean modificado=false;	
				try
				{
					c = PoolConexion.getConnection();
					this.llenaRsFamilia(c);
					rsFamilia.beforeFirst();
					while (rsFamilia.next())
					{
						if(rsFamilia.getInt(1)==fm.getFamiliaID())
						{
							rsFamilia.updateString("nombre", fm.getNombre());
							rsFamilia.updateString("descripcion", fm.getDescripcion());	
							rsFamilia.updateInt("estado", 2);
							rsFamilia.updateRow();
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
						if(rsFamilia != null){
							rsFamilia.close();
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
			
			// Metodo para eliminar Familia
			public boolean eliminarFamila(int idFamilia)
			{
				boolean eliminado=false;	
				try
				{
					c = PoolConexion.getConnection();
					this.llenaRsFamilia(c);
					rsFamilia.beforeFirst();
					while (rsFamilia.next())
					{
						if(rsFamilia.getInt(1)==idFamilia)
						{
							rsFamilia.updateInt("estado", 3);
							rsFamilia.updateRow();
							eliminado=true;
							break;
						}
					}
				}
				catch (Exception e)
				{
					System.err.println("ERROR AL ElIMINAR FAMILIA "+e.getMessage());
					e.printStackTrace();
				}
				finally
				{
					try {
						if(rsFamilia != null){
							rsFamilia.close();
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
