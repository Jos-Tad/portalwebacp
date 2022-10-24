package entidades;

import java.sql.Timestamp;

public class Servicio {
	//Atributos
	private int servicioid;
	private String nombre;
	private String descripcion;
	private String multimedia;
	private int estadoservicio;
	private int estado;
	private Timestamp fcreacion;
	private Timestamp fmodificacion;
	private Timestamp feliminacion;
	private int usuarioid;
	
	//Metodos
	public int getServicioid() {
		return servicioid;
	}
	public void setServicioid(int servicioid) {
		this.servicioid = servicioid;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public int getEstadoservicio() {
		return estadoservicio;
	}
	public void setEstadoservicio(int estadoservicio) {
		this.estadoservicio = estadoservicio;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getUsuarioid() {
		return usuarioid;
	}
	public void setUsuarioid(int usuarioid) {
		this.usuarioid = usuarioid;
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
	public Timestamp getFeliminacion() {
		return feliminacion;
	}
	public void setFeliminacion(Timestamp feliminacion) {
		this.feliminacion = feliminacion;
	}
}
