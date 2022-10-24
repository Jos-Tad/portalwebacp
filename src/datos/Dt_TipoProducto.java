package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.TipoProducto;

public class Dt_TipoProducto {

			//Atributos
			PoolConexion pc = PoolConexion.getInstance(); 
			Connection c = null;
			private ResultSet rsTipoProducto = null;
			private ResultSet rs = null;
			private PreparedStatement ps = null;
			
			// Metodo para llenar el ResultSet
			public void llenarRsTipoProducto(Connection c){
				try{
					ps = c.prepareStatement("select tipoproductoid, nombre, descripcion, estado from tipoproducto", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					rsTipoProducto = ps.executeQuery();
				}
				catch (Exception e){
					System.out.println("DATOS: ERROR EN LISTAR TIPO DE PRODUCTOS "+ e.getMessage());
					e.printStackTrace();
				}
			}
			
			//Metodo para visualizar Tipo de Productos 
			public ArrayList<TipoProducto> listarTipoProductos(){
				ArrayList<TipoProducto> listTipoProductos = new ArrayList<TipoProducto>();
				try{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select tipoproductoid, nombre, descripcion, estado from tipoproducto where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					rs = ps.executeQuery();
					while(rs.next()){
						TipoProducto tp = new TipoProducto();
						tp.setTipoproducotid(rs.getInt("tipoproductoid"));
						tp.setNombre(rs.getString("nombre"));
						tp.setDescripcion(rs.getString("descripcion"));
						tp.setEstado(rs.getInt("estado"));		
						listTipoProductos.add(tp);
					}
				}
				catch (Exception e){
					System.out.println("DATOS: ERROR EN LISTAR TIPO DE PRODUCTOS "+ e.getMessage());
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
				return listTipoProductos;
			}
			
			//Metodo para almacenar Tipo Producto
			public boolean guardarTipoProducto(TipoProducto	tp){
				boolean guardado = false;
				
				try{
					c = PoolConexion.getConnection();
					this.llenarRsTipoProducto(c);;;
					rsTipoProducto.moveToInsertRow();
					rsTipoProducto.updateString("nombre", tp.getNombre());
					rsTipoProducto.updateString("descripcion", tp.getDescripcion());
					rsTipoProducto.updateInt("estado", 1);
					rsTipoProducto.insertRow();
					rsTipoProducto.moveToCurrentRow();
					guardado = true;
				}
				catch (Exception e) {
					System.err.println("ERROR AL GUARDAR TIPO PRODUCTO "+e.getMessage());
					e.printStackTrace();
				}
				finally{
					try {
						if(rsTipoProducto != null){
							rsTipoProducto.close();
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
			
			// Metodo para modificar Tipo Productos
			public boolean modificarTipoProducto(TipoProducto tp)
			{
				boolean modificado=false;	
				try
				{
					c = PoolConexion.getConnection();
					this.llenarRsTipoProducto(c);
					rsTipoProducto.beforeFirst();
					while (rsTipoProducto.next())
					{
						if(rsTipoProducto.getInt(1)==tp.getTipoproductoid())
						{
							rsTipoProducto.updateString("nombre", tp.getNombre());
							rsTipoProducto.updateString("descripcion", tp.getDescripcion());
							rsTipoProducto.updateInt("estado", 2);
							rsTipoProducto.updateRow();
							modificado=true;
							break;
						}
					}
				}
				catch (Exception e)
				{
					System.err.println("ERROR AL ACTUALIZAR TIPO DE PRODUCTO "+e.getMessage());
					e.printStackTrace();
				}
				finally
				{
					try {
						if(rsTipoProducto != null){
							rsTipoProducto.close();
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
			
			// Metodo para visualizar los datos especifico de un tipo de producto
			public TipoProducto getTipoProducto(int idTipoProducto)
			{
				TipoProducto tp = new TipoProducto();
				try
				{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("Select  tipoproductoid, nombre, descripcion, estado  from tipoproducto where estado <> 3 and tipoproductoid = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					ps.setInt(1, idTipoProducto);
					rs = ps.executeQuery();
					if(rs.next())
					{
						tp.setTipoproducotid(idTipoProducto);
						tp.setNombre(rs.getString("nombre"));
						tp.setDescripcion(rs.getString("descripcion"));
						tp.setEstado(rs.getInt("estado"));
					}
				}
				catch (Exception e)
				{
					System.out.println("DATOS ERROR TIPO DE PRODUCTO: "+ e.getMessage());
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
				
				return tp;
			}
			
			// Metodo para eliminar Tipo de Producto
			public boolean eliminarTipoProducto(int idTipoProducto)
			{
				boolean eliminado=false;	
				try
				{
					c = PoolConexion.getConnection();
					this.llenarRsTipoProducto(c);
					rsTipoProducto.beforeFirst();
					while (rsTipoProducto.next())
					{
						if(rsTipoProducto.getInt(1)==idTipoProducto)
						{
							rsTipoProducto.updateInt("estado", 3);
							rsTipoProducto.updateRow();
							eliminado=true;
							break;
						}
					}
				}
				catch (Exception e)
				{
					System.err.println("ERROR AL ElIMINAR TIPO DE PRODUCTO "+e.getMessage());
					e.printStackTrace();
				}
				finally
				{
					try {
						if(rsTipoProducto != null){
							rsTipoProducto.close();
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
