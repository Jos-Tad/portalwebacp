package negocio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import datos.Dt_Producto;
import datos.Dt_Servicio;
import datos.PoolConexion;
import entidades.Producto;
import entidades.Servicio;
import vistas.ViewProducto;

public class Ng_Producto {
		//Atributos
		PoolConexion pc = PoolConexion.getInstance(); 
		Connection c = null;
		private ResultSet rs = null;
		private PreparedStatement ps = null;
		
		// Metodo para validar el producto 
		public boolean existeProducto(String Producto){
			boolean existe = false;
			try{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("select * from producto where nombre=? and estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				ps.setString(1, Producto);
				rs = ps.executeQuery();
				if(rs.next()){
					existe=true;
				}
			}
			catch (Exception e){
				System.out.println("DATOS ERROR existeProducto(): "+ e.getMessage());
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
		// Metodo para validar visibilidad del producto
		public boolean validarVisibilidadProd(){
			//Bandera
			 boolean control = false;
			 ArrayList<ViewProducto> listProd = new ArrayList<ViewProducto>();
		     Dt_Producto dtp = new Dt_Producto();
			 listProd = dtp.listarProductos(); 	 	 
			 if(listProd.size() == 0 || listProd.stream().allMatch(x -> x.getEstadoproductoid() == 2)){
				 control = true;
			 }else {
				 control = false;
			 }	
			 return control;
		}
		
		// Metodo para validar el actualizar del Nombre Titulo
				public boolean existeActualizarProducto(int productoid, String Producto){
					boolean existe = false;
					try{
						c = PoolConexion.getConnection();
						ps = c.prepareStatement("SELECT * FROM producto WHERE productoid != ? AND nombre = ? and estado <>3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
						ps.setInt(1, productoid);				
						ps.setString(2, Producto);
						rs = ps.executeQuery();
						if(rs.next()){
							existe=true;
						}
					}
					catch (Exception e){
						System.out.println("DATOS ERROR existeActualizarProducto(): "+ e.getMessage());
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
