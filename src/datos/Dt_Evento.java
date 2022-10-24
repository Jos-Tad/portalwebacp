package datos;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.Evento;

public class Dt_Evento {
	
			//Atributos
			PoolConexion pc = PoolConexion.getInstance(); 
			Connection c = null;
			private ResultSet rsEvento = null;
			private ResultSet rs = null;
			private PreparedStatement ps = null;
			
			// Metodo para llenar el ResultSet
			public void llenaRsEventos(Connection c){
				try{
					ps = c.prepareStatement("select eventoid, nombre, descripcion, fechainicio, horainicio,fechafin, horafin, tipoevento, multimedia, hipervinculo, ubicacion , fcreacion, fmodificacion, feliminacion,estado, usuarioid from evento", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					rsEvento = ps.executeQuery();
				}
				catch (Exception e){
					System.out.println("DATOS: ERROR EN LISTAR EVENTO "+ e.getMessage());
					e.printStackTrace();
				}
			}
			
			//Metodo para visualizar eventos
			public ArrayList<Evento> listarEventos(){
				ArrayList<Evento> listEvento = new ArrayList<Evento>();
				try{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select eventoid, nombre, descripcion, fechainicio, horainicio,fechafin, horafin, tipoevento, multimedia, hipervinculo, ubicacion ,estado, usuarioid from evento where evento.estado <> 3 order by evento.fechainicio", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					rs = ps.executeQuery();
					while(rs.next()){
						Evento event = new Evento();
						event.setEventoid(rs.getInt("eventoid"));
						event.setNombre(rs.getString("nombre"));
						event.setDescripcion(rs.getString("descripcion"));
						event.setFechainicio(rs.getString("fechainicio"));
						event.setHorainicio(rs.getString("horainicio"));
						event.setFechafin(rs.getString("fechafin"));
						event.setHorafin(rs.getString("horafin"));
						event.setTipoevento(rs.getInt("tipoevento"));
						event.setMultimedia(rs.getString("multimedia"));
						event.setHipervinculo(rs.getString("hipervinculo"));
						event.setUbicacion(rs.getString("ubicacion"));
						event.setEstado(rs.getInt("estado"));
						event.setUsuarioid(rs.getInt("usuarioid"));
						listEvento.add(event);
					}
				}
				catch (Exception e){
					System.out.println("DATOS: ERROR EN LISTAR EVENTO "+ e.getMessage());
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
				return listEvento;
			}
			
			//Metodo para almacenar nuevo evento
			public boolean guardarEventos(Evento ev){
				boolean guardado = false;
				
				try{
					c = PoolConexion.getConnection();
					this.llenaRsEventos(c);
					rsEvento.moveToInsertRow();
					rsEvento.updateString("nombre", ev.getNombre());
					rsEvento.updateString("descripcion", ev.getDescripcion());
					rsEvento.updateString("fechainicio", ev.getFechainicio());
					rsEvento.updateString("horainicio", ev.getHorainicio());
					rsEvento.updateString("fechafin", ev.getFechafin());
					rsEvento.updateString("horafin", ev.getHorafin());
					rsEvento.updateInt("tipoevento", ev.getTipoevento());	
					rsEvento.updateString("multimedia", ev.getMultimedia());
					rsEvento.updateString("hipervinculo", ev.getHipervinculo());
					rsEvento.updateString("ubicacion", ev.getUbicacion());		
					rsEvento.updateTimestamp("fcreacion", ev.getFcreacion());	
					rsEvento.updateInt("estado", 1);
					rsEvento.updateInt("usuarioid", ev.getUsuarioid());
					rsEvento.insertRow();
					rsEvento.moveToCurrentRow();
					guardado = true;
				}
				catch (Exception e) {
					System.err.println("ERROR AL GUARDAR EVENTO "+e.getMessage());
					e.printStackTrace();
				}
				finally{
					try {
						if(rsEvento != null){
							rsEvento.close();
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
			
			// Metodo para eliminar Evento
			public boolean eliminarEvento(int idEvento)
			{
				boolean eliminado=false;	
				try
				{
					c = PoolConexion.getConnection();
					this.llenaRsEventos(c);
					Date fechaSistema = new Date();					
					rsEvento.beforeFirst();
					while (rsEvento.next())
					{
						if(rsEvento.getInt(1)==idEvento)
						{
							rsEvento.updateTimestamp("feliminacion", new java.sql.Timestamp(fechaSistema.getTime()));
							rsEvento.updateInt("estado", 3);
							rsEvento.updateRow();
							eliminado=true;
							break;
						}
					}
				}
				catch (Exception e)
				{
					System.err.println("ERROR AL ElIMINAR EVENTO "+e.getMessage());
					e.printStackTrace();
				}
				finally
				{
					try {
						if(rsEvento != null){
							rsEvento.close();
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
			
			// Metodo para modificar Evento
			public boolean modificarEvento(Evento ev)
			{
				boolean modificado=false;	
				try
				{
					c = PoolConexion.getConnection();
					this.llenaRsEventos(c);
					rsEvento.beforeFirst();
					while (rsEvento.next())
					{
						if(rsEvento.getInt(1)==ev.getEventoid())
						{
							rsEvento.updateString("nombre", ev.getNombre());
							rsEvento.updateString("descripcion", ev.getDescripcion());
							rsEvento.updateString("fechainicio", ev.getFechainicio());
							rsEvento.updateString("horainicio", ev.getHorainicio());
							rsEvento.updateString("fechafin", ev.getFechafin());
							rsEvento.updateString("horafin", ev.getHorafin());
							rsEvento.updateInt("tipoevento", ev.getTipoevento());	
							rsEvento.updateString("multimedia", ev.getMultimedia());
							rsEvento.updateString("hipervinculo", ev.getHipervinculo());
							rsEvento.updateString("ubicacion", ev.getUbicacion());		
							rsEvento.updateTimestamp("fmodificacion", ev.getFmodificacion());	
							rsEvento.updateInt("estado", 1);
							rsEvento.updateInt("usuarioid", ev.getUsuarioid());
							rsEvento.updateRow();
							modificado=true;
							break;
						}
					}
				}
				catch (Exception e)
				{
					System.err.println("ERROR AL ACTUALIZAR EVENTO "+e.getMessage());
					e.printStackTrace();
				}
				finally
				{
					try {
						if(rsEvento != null){
							rsEvento.close();
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
			
			// Metodo para visualizar los datos de un evento especifico
			public Evento getEvento(int idEvento)
			{
				Evento ev = new Evento();
				try
				{
					c = PoolConexion.getConnection();
					ps = c.prepareStatement("select eventoid, nombre, descripcion, fechainicio, horainicio,fechafin, horafin, tipoevento, multimedia, hipervinculo, ubicacion ,estado, usuarioid from evento where estado <> 3 and eventoid = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
					ps.setInt(1, idEvento);
					rs = ps.executeQuery();
					if(rs.next())
					{
						ev.setEventoid(idEvento);
						ev.setNombre(rs.getString("nombre"));
						ev.setDescripcion(rs.getString("descripcion"));
						ev.setMultimedia(rs.getString("multimedia"));
						ev.setFechainicio(rs.getString("fechainicio"));
						ev.setHorainicio(rs.getString("horainicio"));
						ev.setFechafin(rs.getString("fechafin"));
						ev.setHorafin(rs.getString("horafin"));
						ev.setTipoevento(rs.getInt("tipoevento"));					
						ev.setHipervinculo(rs.getString("hipervinculo"));
						ev.setUbicacion(rs.getString("ubicacion"));		
						ev.setEstado(rs.getInt("estado"));
					}
				}
				catch (Exception e)
				{
					System.out.println("DATOS ERROR EVENTO: "+ e.getMessage());
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
				
				return ev;
			}
}
