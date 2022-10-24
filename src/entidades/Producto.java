package entidades;

import java.sql.Timestamp;

public class Producto {
	//Atributos
	private int productoid;
	private String producto;
	private String descripcion;
	private String multimedia;
	private int estado;
	private int estadoproductoid;
	private Timestamp fcreacion;
	private Timestamp fmodificacion;
	private Timestamp feliminacion;
	private int usuarioid;
	private int tipoproductoid;
	
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
	public int getTipoproductoid() {
		return tipoproductoid;
	}
	public void setTipoproductoid(int tipoproductoid) {
		this.tipoproductoid = tipoproductoid;
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
