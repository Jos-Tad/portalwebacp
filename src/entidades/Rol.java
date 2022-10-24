package entidades;

import java.sql.Timestamp;

public class Rol {
	//Atributos
	private int idRol;
	private String rol;
	private String desc_rol;
	private Timestamp fcreacion;
	private Timestamp fmodificacion;
	private int estado;
	
	//Metodos
	public int getIdRol() {
		return idRol;
	}
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getDesc_rol() {
		return desc_rol;
	}
	public void setDesc_rol(String desc_rol) {
		this.desc_rol = desc_rol;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
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