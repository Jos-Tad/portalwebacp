package entidades;

public class Distribucion {
	
	//Atributos
	private int DistribucionID;
	private String Nombre;
	private String Descripcion;
	private int Estado;
	private int PaisID;
	
	
	public int getDistribucionID() {
		return DistribucionID;
	}
	public void setDistribucionID(int distribucionID) {
		DistribucionID = distribucionID;
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
	public int getPaisID() {
		return PaisID;
	}
	public void setPaisID(int paisID) {
		PaisID = paisID;
	}
	

}
