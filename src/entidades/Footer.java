package entidades;

import java.sql.Timestamp;

public class Footer {
	
	//Atributos
	private int footerID; 
	private String correo;
	private String descripcion;
	private String telefono;
	private String extencion;
	private String logo;
	private int estado;
	private int usuarioID;
	private Timestamp fmodificacion;
	
	//Metodos
	public int getFooterID() {
		return footerID;
	}
	public void setFooterID(int footerID) {
		this.footerID = footerID;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getExtencion() {
		return extencion;
	}
	public void setExtencion(String extencion) {
		this.extencion = extencion;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getUsuarioID() {
		return usuarioID;
	}
	public void setUsuarioID(int usuarioID) {
		this.usuarioID = usuarioID;
	}
	public Timestamp getFmodificacion() {
		return fmodificacion;
	}
	public void setFmodificacion(Timestamp fmodificacion) {
		this.fmodificacion = fmodificacion;
	}		
}
