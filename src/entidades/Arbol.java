package entidades;

import java.sql.Timestamp;

public class Arbol {
	
//Atributos	
private int ArbolID;
private String NombreComun;
private String NombreCientifico;
private String Descripcion;		
private String Geom;
private String Multimedia;
private int FloracionID;
private int UsuarioId;
private int GeneroID;
private int FamiliaID;			
private int Estado;
private Timestamp fcreacion;
private Timestamp fmodificacion;
private Timestamp feliminacion;

//Metodos
public String getNombreComun() {
	return NombreComun;
}
public void setNombreComun(String nombreComun) {
	NombreComun = nombreComun;
}
public String getNombreCientifico() {
	return NombreCientifico;
}
public void setNombreCientifico(String nombreCientifico) {
	NombreCientifico = nombreCientifico;
}
public String getDescripcion() {
	return Descripcion;
}
public void setDescripcion(String descripcion) {
	Descripcion = descripcion;
}
public int getFloracionID() {
	return FloracionID;
}
public void setFloracionID(int floracionID) {
	FloracionID = floracionID;
}
public String getMultimedia() {
	return Multimedia;
}
public void setMultimedia(String multimedia) {
	Multimedia = multimedia;
}
public int getUsuarioId() {
	return UsuarioId;
}
public void setUsuarioId(int usuarioId) {
	UsuarioId = usuarioId;
}
public int getGeneroID() {
	return GeneroID;
}
public void setGeneroID(int generoID) {
	GeneroID = generoID;
}
public int getFamiliaID() {
	return FamiliaID;
}
public void setFamiliaID(int familiaID) {
	FamiliaID = familiaID;
}
public int getEstado() {
	return Estado;
}
public void setEstado(int estado) {
	Estado = estado;
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
public String getGeom() {
	return Geom;
}
public void setGeom(String geom) {
	Geom = geom;
}
public int getArbolID() {
	return ArbolID;
}
public void setArbolID(int arbolID) {
	ArbolID = arbolID;
}




}