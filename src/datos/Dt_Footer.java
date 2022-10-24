package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entidades.Footer;
import entidades.Home;

public class Dt_Footer {
	//Atributos
	PoolConexion pc = PoolConexion.getInstance(); 
	Connection c = null;
	private ResultSet rsFooter = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
		
		
	// Metodo para llenar el ResultSet
	public void llenaRFooter(Connection c){
		try{
			ps = c.prepareStatement("SELECT footerid, descripcion, correo, telefono, extencion, logo, fmodificacion, estado, usuarioid FROM footer", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rsFooter = ps.executeQuery();
		}
		catch (Exception e){
			System.out.println("DATOS: Error en mostrar información del pie de página "+ e.getMessage());
			e.printStackTrace();
		}
	}
			
	//Metodo para visualizar Footer
	public ArrayList<Footer> listFooter(){

		//Crear lista de arreglos
		ArrayList<Footer> listFooter = new ArrayList<Footer>();
		
		try{
			c = PoolConexion.getConnection();
			ps = c.prepareStatement("select * from footer where footer.estado <> 3", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			rs = ps.executeQuery();
			while(rs.next()){
				Footer ft = new Footer();
				ft.setFooterID(rs.getInt("FooterID"));
				ft.setDescripcion(rs.getString("Descripcion"));
				ft.setCorreo(rs.getString("Correo"));
				ft.setTelefono(rs.getString("Telefono"));
				ft.setExtencion(rs.getString("Extencion"));
				ft.setLogo(rs.getString("Logo"));
				ft.setEstado(rs.getInt("Estado"));
				ft.setUsuarioID(rs.getInt("UsuarioID"));
				listFooter.add(ft);
			}
		}
		catch (Exception e){
			System.out.println("DATOS: Error en listar los elementos del Banner "+ e.getMessage());
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
		return listFooter;
	}

	// Metodo para modificar Pie pagina
	public boolean modificarFooter(Footer ft)
	{
		boolean modificado=false;	
		try
		{
			c = PoolConexion.getConnection();
			this.llenaRFooter(c);
			rsFooter.beforeFirst();
			while (rsFooter.next())
			{
				if(rsFooter.getInt(1)==ft.getFooterID())
				{
					rsFooter.updateString("descripcion", ft.getDescripcion());
					rsFooter.updateString("correo", ft.getCorreo());
					rsFooter.updateString("telefono", ft.getTelefono());
					rsFooter.updateString("extencion", ft.getExtencion());
					rsFooter.updateString("logo", ft.getLogo());
					rsFooter.updateTimestamp("fmodificacion", ft.getFmodificacion());
					rsFooter.updateInt("usuarioid", ft.getUsuarioID());							
					rsFooter.updateInt("estado", 2);
					rsFooter.updateRow();
					modificado=true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("Error al actualizar el pie de página "+e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rsFooter != null){
					rsFooter.close();
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
