package vistas;

public class ViewCoordenadaArbol {
	//Atributos
	private int coordenadaid;
	private String nombre;
	private String descco;
	private String latitud;
	private String longitud;
	private String nombrecomun;
	private String nombrecientifico;
	private String descar;
	private String multimedia;
	private int estado;
	
	//Metodos
	public String getNombre() {
		return nombre;
	}
	public int getCoordenadaid() {
		return coordenadaid;
	}
	public void setCoordenadaid(int coordenadaid) {
		this.coordenadaid = coordenadaid;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescco() {
		return descco;
	}
	public void setDescco(String descco) {
		this.descco = descco;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public String getNombrecomun() {
		return nombrecomun;
	}
	public void setNombrecomun(String nombrecomun) {
		this.nombrecomun = nombrecomun;
	}
	public String getNombrecientifico() {
		return nombrecientifico;
	}
	public void setNombrecientifico(String nombrecientifico) {
		this.nombrecientifico = nombrecientifico;
	}
	public String getDescar() {
		return descar;
	}
	public void setDescar(String descar) {
		this.descar = descar;
	}
	public String getMultimedia() {
		return multimedia;
	}
	public void setMultimedia(String multimedia) {
		this.multimedia = multimedia;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
}
