package entidades;

public class Floracion {
	//Atributos
			private int FloracionID;
			private String Nombre;
			private String Descripcion;
			private int Temporada;
			private int Estado;
			
			//Metodos
			public int getFloracionID() {
				return FloracionID;
			}
			public void setFloracionID(int FloracionID) {
				this.FloracionID = FloracionID;
			}
			public String getNombre() {
				return Nombre;
			}
			public void setNombre(String Nombre) {
				this.Nombre = Nombre;
			}
			public String getDescripcion() {
				return Descripcion;
			}
			public void setDescripcion(String Descripcion) {
				this.Descripcion = Descripcion;
			}	
			public int getTemporada() {
				return Temporada;
			}
			public void setTemporada(int Temporada) {
				this.Temporada = Temporada;
			}	
			public int getEstado() {
				return Estado;
			}
			public void setEstado(int Estado) {
				this.Estado = Estado;
			}

}
