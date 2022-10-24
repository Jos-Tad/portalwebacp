package entidades;

import java.sql.Timestamp;

public class Home {

	//Atributos
	private int homeID;
	private String historia;
	private String mision;
	private String vision;
	private String img_historia;
	private String img_vision;
	private String img_mision;
	private Timestamp fmodificacion;
	private int estado;
	private int	usuarioID;
	
	//Metodos
	public int getHomeID() {
		return homeID;
	}
	public void setHomeID(int homeID) {
		this.homeID = homeID;
	}
	public String getHistoria() {
		return historia;
	}
	public void setHistoria(String historia) {
		this.historia = historia;
	}
	public String getMision() {
		return mision;
	}
	public void setMision(String mision) {
		this.mision = mision;
	}
	public String getVision() {
		return vision;
	}
	public void setVision(String vision) {
		this.vision = vision;
	}
	public String getImg_historia() {
		return img_historia;
	}
	public void setImg_historia(String img_historia) {
		this.img_historia = img_historia;
	}
	public String getImg_vision() {
		return img_vision;
	}
	public void setImg_vision(String img_vision) {
		this.img_vision = img_vision;
	}
	public String getImg_mision() {
		return img_mision;
	}
	public void setImg_mision(String img_mision) {
		this.img_mision = img_mision;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getUsuarioID() {
		return usuarioID;
	}
	public void setUsuarioID(int usuarioID) {
		this.usuarioID = usuarioID;
	}
	public Timestamp getFmodificacion() {
		return fmodificacion;
	}
	public void setFmodificacion(Timestamp fmodificacion) {
		this.fmodificacion = fmodificacion;
	}

}

