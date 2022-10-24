package entidades;

import java.sql.Timestamp;

public class Evento {
	//Atributos
	private int eventoid;
	private String nombre;
	private String  descripcion;
	private String fechainicio;
	private String horainicio;
	private String fechafin;
	private String horafin;
	private int tipoevento;
	private String multimedia;
	private String  hipervinculo;
	private String ubicacion;
	private Timestamp fcreacion;
	private Timestamp fmodificacion;
	private Timestamp feliminacion;
	private int estado;
	private int usuarioid;
	
	//Metodos
	public int getEventoid() {
		return eventoid;
	}
	public void setEventoid(int eventoid) {
		this.eventoid = eventoid;
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
	public String getFechainicio() {
		return fechainicio;
	}
	public void setFechainicio(String fechainicio) {
		this.fechainicio = fechainicio;
	}
	public String getHorainicio() {
		return horainicio;
	}
	public void setHorainicio(String horainicio) {
		this.horainicio = horainicio;
	}
	public String getFechafin() {
		return fechafin;
	}
	public void setFechafin(String fechafin) {
		this.fechafin = fechafin;
	}
	public String getHorafin() {
		return horafin;
	}
	public void setHorafin(String horafin) {
		this.horafin = horafin;
	}
	public int getTipoevento() {
		return tipoevento;
	}
	public void setTipoevento(int tipoevento) {
		this.tipoevento = tipoevento;
	}
	public String getMultimedia() {
		return multimedia;
	}
	public void setMultimedia(String multimedia) {
		this.multimedia = multimedia;
	}
	public String getHipervinculo() {
		return hipervinculo;
	}
	public void setHipervinculo(String hipervinculo) {
		this.hipervinculo = hipervinculo;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
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
