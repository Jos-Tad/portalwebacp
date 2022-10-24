package entidades;

public class RolUsuario {
	//Atributos
	private int idrol_usuario;
	private int usuarioid;	
	private int rolid;
	private int confirmemail;
	
	//Metodos
	public int getIdrol_usuario() {
		return idrol_usuario;
	}
	public void setIdrol_usuario(int idrol_usuario) {
		this.idrol_usuario = idrol_usuario;
	}
	public int getUsuarioid() {
		return usuarioid;
	}
	public void setUsuarioid(int usuarioid) {
		this.usuarioid = usuarioid;
	}
	public int getRolid() {
		return rolid;
	}
	public void setRolid(int rolid) {
		this.rolid = rolid;
	}
	public int getConfirmemail() {
		return confirmemail;
	}
	public void setConfirmemail(int confirmemail) {
		this.confirmemail = confirmemail;
	}
}
