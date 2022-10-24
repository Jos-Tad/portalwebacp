package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.Producto;
import vistas.ViewProducto;

public class Dt_Producto {
	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsProducto = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	// Metodo para llenar el ResultSet
	public void llenarRsProducto(Connection c){
		try{
			ps = c.prepareStatement("select productoid, nombre, descripcion, multimedia, estado, estadoproducto, tipoproductoid, fcreacion, fmodificacion, feliminacion, usuarioid from producto", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rsProducto = ps.executeQuery();
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN LISTAR PRODUCTOS "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Metodo para visualizar Productos 
	public ArrayList<ViewProducto> listarProductos(){
		ArrayList<ViewProducto> listProductos = new ArrayList<ViewProducto>();
		try{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("select * from viewproducto where estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rs = ps.executeQuery();
			while(rs.next()){
				ViewProducto vp = new ViewProducto();
				vp.setProductoid(rs.getInt("productoid"));
				vp.setProducto(rs.getString("producto"));
				vp.setDescripcion(rs.getString("descripcion"));
				vp.setMultimedia(rs.getString("multimedia"));				
				vp.setEstado(rs.getInt("estado"));
				vp.setEstadoproductoid(rs.getInt("estadoproducto"));
				vp.setUsuarioid(rs.getInt("usuarioid"));	
				vp.setTipoproducto(rs.getString("tipoproducto"));								
				listProductos.add(vp);
			}
		}
		catch (Exception e){
			System.out.println("DATOS: ERROR EN LISTAR PRODUCTOS "+ e.getMessage());
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
		return listProductos;
	}
	
	//Metodo para almacenar Producto
	public boolean guardarProducto(Producto	pr){
		boolean guardado = false;
		
		try{
			c = PoolConexion.getConnection();
			this.llenarRsProducto(c);
			rsProducto.moveToInsertRow();
			rsProducto.updateString("nombre", pr.getProducto());
			rsProducto.updateString("descripcion", pr.getDescripcion());
			rsProducto.updateString("multimedia", pr.getMultimedia());
			rsProducto.updateInt("estadoproducto", pr.getEstadoproductoid());
			rsProducto.updateInt("estado", 1);
			rsProducto.updateTimestamp("fcreacion", pr.getFcreacion());
			rsProducto.updateInt("tipoproductoid", pr.getTipoproductoid());
			rsProducto.updateInt("usuarioid", pr.getUsuarioid());
			rsProducto.insertRow();
			rsProducto.moveToCurrentRow();
			guardado = true;
		}
		catch (Exception e) {
			System.err.println("ERROR AL GUARDAR PRODUCTO "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				if(rsProducto != null){
					rsProducto.close();
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
	
	// Metodo para modificar Producto
	public boolean modificarProducto(Producto pr)
	{
		boolean modificado=false;	
		try
		{
			c = PoolConexion.getConnection();
			this.llenarRsProducto(c);
			rsProducto.beforeFirst();
			while (rsProducto.next())
			{
				if(rsProducto.getInt(1)==pr.getProductoid())
				{
					rsProducto.updateString("nombre", pr.getProducto());
					rsProducto.updateString("descripcion", pr.getDescripcion());
					rsProducto.updateString("multimedia", pr.getMultimedia());
					rsProducto.updateInt("estadoproducto", pr.getEstadoproductoid());	
					rsProducto.updateInt("tipoproductoid", pr.getTipoproductoid());	
					rsProducto.updateTimestamp("fmodificacion", pr.getFmodificacion());
					rsProducto.updateInt("usuarioid", pr.getUsuarioid());
					rsProducto.updateInt("estado", 2);
					rsProducto.updateRow();
					modificado=true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("ERROR AL ACTUALIZAR PRODUCTO "+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rsProducto != null){
					rsProducto.close();
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
	
	// Metodo para visualizar los datos especifico de un producto
	public Producto getProducto(int idProducto)
	{
		Producto tp = new Producto();
		try
		{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("select productoid, nombre, descripcion, multimedia, estado, estadoproducto, tipoproductoid, usuarioid from producto where estado <> 3 and productoid = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			ps.setInt(1, idProducto);
			rs = ps.executeQuery();
			if(rs.next())
			{
				tp.setProductoid(idProducto);
				tp.setProducto(rs.getString("nombre"));
				tp.setDescripcion(rs.getString("descripcion"));
				tp.setMultimedia(rs.getString("multimedia"));
				tp.setEstadoproductoid(rs.getInt("estadoproducto"));
				tp.setTipoproductoid(rs.getInt("tipoproductoid"));
				tp.setEstado(rs.getInt("estado"));
			}
		}
		catch (Exception e)
		{
			System.out.println("DATOS ERROR PRODUCTO: "+ e.getMessage());
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
	
	// Metodo para eliminar Producto
	public boolean eliminarProducto(int idProducto)
				{
					boolean eliminado=false;	
					try		
					{
						c = PoolConexion.getConnection();
						this.llenarRsProducto(c);
						rsProducto.beforeFirst();
						while (rsProducto.next())
						{
							if(rsProducto.getInt(1)==idProducto)
							{
								rsProducto.updateInt("estado", 3);
								rsProducto.updateRow();
								eliminado=true;
								break;
							}
						}
					}
					catch (Exception e)
					{
						System.err.println("ERROR AL ElIMINAR PRODUCTO "+e.getMessage());
						e.printStackTrace();
					}
					finally
					{
						try {
							if(rsProducto != null){
								rsProducto.close();
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
