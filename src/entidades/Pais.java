package entidades;

public class Pais {
	
	//Atributos
	private int PaisID;
	private String Nombre;
	private String Descripcion;
	private int RegionID;
	private int Estado;
	
	//metodos
	public int getPaisID() {
		return PaisID;
	}
	public void setPaisID(int paisID) {
		PaisID = paisID;
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
	public int getEstado() {
		return Estado;
	}
	public void setEstado(int estado) {
		Estado = estado;
	}
	public int getRegionID() {
		return RegionID;
	}
	public void setRegionID(int regionID) {
		RegionID = regionID;
	}
	
}