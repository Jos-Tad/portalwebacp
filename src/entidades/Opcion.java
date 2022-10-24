package entidades;

public class Opcion {
	//Atributos
	private int id_opc;
	private String opcion;
	private String desc_opc;
	private int estado;
	
	//Metodos
	public int getId_opc() {
		return id_opc;
	}
	public void setId_opc(int id_opc) {
		this.id_opc = id_opc;
	}
	public String getOpcion() {
		return opcion;
	}
	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}
	public String getDesc_opc() {
		return desc_opc;
	}
	public void setDesc_opc(String desc_opc) {
		this.desc_opc = desc_opc;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
}
