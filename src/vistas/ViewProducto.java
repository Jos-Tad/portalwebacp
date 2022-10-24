package vistas;

public class ViewProducto {
	//Atributos
	private int productoid;
	private String producto;
	private String descripcion;
	private String multimedia;
	private int estado;
	private int estadoproductoid;
	private int usuarioid;
	private String tipoproducto;
	
	//Metodos
	public int getProductoid() {
		return productoid;
	}
	public void setProductoid(int productoid) {
		this.productoid = productoid;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
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
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getEstadoproductoid() {
		return estadoproductoid;
	}
	public void setEstadoproductoid(int estadoproductoid) {
		this.estadoproductoid = estadoproductoid;
	}
	public int getUsuarioid() {
		return usuarioid;
	}
	public void setUsuarioid(int usuarioid) {
		this.usuarioid = usuarioid;
	}
	public String getTipoproducto() {
		return tipoproducto;
	}
	public void setTipoproducto(String tipoproducto) {
		this.tipoproducto = tipoproducto;
	}
}
