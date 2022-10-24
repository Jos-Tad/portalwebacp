package entidades;

public class Coordenada {
	//Atributos
	private int CoordenadaID;
	private String Nombre;
	private String Descripcion;
	private String Longitud;
	private String Latitud;
	private int UsuarioID;
	private int Estado;
	
	//Metodos
	public int getCoordenadaID() {
		return CoordenadaID;
	}
	public void setCoordenadaID(int coordenadaID) {
		CoordenadaID = coordenadaID;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getLongitud() {
		return Longitud;
	}
	public void setLongitud(String longitud) {
		Longitud = longitud;
	}
	public String getLatitud() {
		return Latitud;
	}
	public void setLatitud(String latitud) {
		Latitud = latitud;
	}
	public int getEstado() {
		return Estado;
	}
	public void setEstado(int estado) {
		Estado = estado;
	}	
	public int getUsuarioID() {
		return UsuarioID;
	}
	public void setUsuarioID(int usuarioID) {
		UsuarioID = usuarioID;
	}
}