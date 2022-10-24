package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import entidades.Servicio;

public class Dt_Servicio {
		//Atributos
		PoolConexion pc = PoolConexion.getInstance(); 
		Connection c = null;
		private ResultSet rsServicio = null;
		private ResultSet rs = null;
		private PreparedStatement ps = null;
		
		// Metodo para llenar el ResultSet
		public void llenarRsServicio(Connection c){
			try{
				ps = c.prepareStatement("select servicioid,nombre, descripcion,multimedia,estadoservicio, fcreacion, fmodificacion, feliminacion,estado, usuarioid from servicio;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rsServicio = ps.executeQuery();
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR SERVICIO "+ e.getMessage());
				e.printStackTrace();
			}
		}
		
		//Metodo para visualizar Servicios 
		public ArrayList<Servicio> listarServicio(){
			ArrayList<Servicio> listServicio = new ArrayList<Servicio>();
			try{
				c = PoolConexion.getConnection();
				ps = c.prepareStatement("select servicioid, nombre, descripcion,multimedia,estadoservicio, estado, usuarioid from servicio where estado <>3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				rs = ps.executeQuery();
				while(rs.next()){
					Servicio sr = new Servicio();
					sr.setServicioid(rs.getInt("servicioid"));
					sr.setNombre(rs.getString("nombre"));
					sr.setDescripcion(rs.getString("descripcion"));
					sr.setMultimedia(rs.getString("multimedia"));
					sr.setEstado(rs.getInt("estado"));		
					sr.setEstadoservicio(rs.getInt("estadoservicio"));	
					sr.setUsuarioid(rs.getInt("usuarioid"));	
					listServicio.add(sr);
				}
			}
			catch (Exception e){
				System.out.println("DATOS: ERROR EN LISTAR SERVICIO "+ e.getMessage());
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
			return listServicio;
		}
		
			//Metodo para almacenar Servicio
				public boolean guardarServicio(Servicio	sr){
					boolean guardado = false;
					
					try{
						c = PoolConexion.getConnection();
						this.llenarRsServicio(c);;
						rsServicio.moveToInsertRow();
						rsServicio.updateString("nombre", sr.getNombre());
						rsServicio.updateString("descripcion", sr.getDescripcion());
						rsServicio.updateString("multimedia", sr.getMultimedia());
						rsServicio.updateInt("estadoservicio", sr.getEstadoservicio());		
						rsServicio.updateTimestamp("fcreacion", sr.getFcreacion());
						rsServicio.updateInt("estado", 1);
						rsServicio.updateInt("usuarioid", sr.getUsuarioid());
						rsServicio.insertRow();
						rsServicio.moveToCurrentRow();
						guardado = true;
					}
					catch (Exception e) {
						System.err.println("ERROR AL GUARDAR SERVICIO "+e.getMessage());
						e.printStackTrace();
					}
					finally{
						try {
							if(rsServicio != null){
								rsServicio.close();
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
		
				// Metodo para modificar Servicio
				public boolean modificarServicio(Servicio sr)
				{
					boolean modificado=false;	
					try
					{
						c = PoolConexion.getConnection();
						this.llenarRsServicio(c);;
						rsServicio.beforeFirst();
						while (rsServicio.next())
						{
							if(rsServicio.getInt(1)==sr.getServicioid())
							{
								rsServicio.updateString("nombre", sr.getNombre());
								rsServicio.updateString("descripcion", sr.getDescripcion());
								rsServicio.updateString("multimedia", sr.getMultimedia());
								rsServicio.updateInt("estadoservicio", sr.getEstadoservicio());
								rsServicio.updateTimestamp("fmodificacion", sr.getFmodificacion());
								rsServicio.updateInt("usuarioid", sr.getUsuarioid());
								rsServicio.updateInt("estado", 2);
								rsServicio.updateRow();
								modificado=true;
								break;
							}
						}
					}
					catch (Exception e)
					{
						System.err.println("ERROR AL ACTUALIZAR SERVICIO "+e.getMessage());
						e.printStackTrace();
					}
					finally
					{
						try {
							if(rsServicio != null){
								rsServicio.close();
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
				
				// Metodo para visualizar los datos de un servicio especifico
				public Servicio getServicio(int idServicio)
				{
					Servicio sr = new Servicio();
					try
					{
						c = PoolConexion.getConnection();
						ps = c.prepareStatement("select servicioid, nombre, descripcion,multimedia,estadoservicio, estado, usuarioid from servicio where estado <> 3 and servicioid = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
						ps.setInt(1, idServicio);
						rs = ps.executeQuery();
						if(rs.next())
						{
							sr.setServicioid(idServicio);
							sr.setNombre(rs.getString("nombre"));
							sr.setDescripcion(rs.getString("descripcion"));
							sr.setMultimedia(rs.getString("multimedia"));
							sr.setEstadoservicio(rs.getInt("estadoservicio"));
							sr.setEstado(rs.getInt("estado"));
						}
					}
					catch (Exception e)
					{
						System.out.println("DATOS ERROR SERVICIO: "+ e.getMessage());
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
					
					return sr;
				}

				
				// Metodo para eliminar Servicio
				public boolean eliminarServicio(int idServicio)
				{
					boolean eliminado=false;	
					try
					{
						c = PoolConexion.getConnection();
						this.llenarRsServicio(c);
						rsServicio.beforeFirst();
						Date fechaSistema = new Date();
						
						while (rsServicio.next())
						{
							if(rsServicio.getInt(1)==idServicio)
							{
								rsServicio.updateTimestamp("feliminacion", new java.sql.Timestamp(fechaSistema.getTime()));
								rsServicio.updateInt("estado", 3);
								rsServicio.updateRow();
								eliminado=true;
								break;
							}
						}
					}
					catch (Exception e)
					{
						System.err.println("ERROR AL ElIMINAR SERVICIO "+e.getMessage());
						e.printStackTrace();
					}
					finally
					{
						try {
							if(rsServicio != null){
								rsServicio.close();
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