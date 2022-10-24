package vistas;

public class ViewPais {
	
	//Atributos
	private int PaisID;
	private String Nombre;
	private String Descripcion;
	private int Estado;
	private String Region;
	
	public int getPaisID() {
		return PaisID;
	}
	public void setPaisID(int paisid) {
		this.PaisID = paisid;
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
	public String getRegion() {
		return Region;
	}
	public void setRegion(String region) {
		Region = region;
	}
}