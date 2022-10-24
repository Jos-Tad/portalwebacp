package entidades;

import java.sql.Timestamp;

public class Banner {	
	
	//Atributos	
	private int bannerID;
	private String titulobanner;
	private String descripcion;
	private String multimedia;
	private int posicion;
	private int estado;
	private Timestamp fcreacion;
	private Timestamp fmodificacion;
	private int usuarioID;
	
	//Metodos
	public int getBannerID() {
		return bannerID;
	}
	public void setBannerID(int bannerID) {
		this.bannerID = bannerID;
	}
	public String getTitulobanner() {
		return titulobanner;
	}
	public void setTitulobanner(String titulobanner) {
		this.titulobanner = titulobanner;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMultimedia() {
		return multimedia;
	}
	public void setMultimedia(String multimedia) {
		this.multimedia = multimedia;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
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
	public Timestamp getFcreacion() {
		return fcreacion;
	}
	public void setFcreacion(Timestamp fcreacion) {
		this.fcreacion = fcreacion;
	}
	public Timestamp getFmodificacion() {
		return fmodificacion;
	}
	public void setFmodificacion(Timestamp fmodificacion) {
		this.fmodificacion = fmodificacion;
	}	
}