package entidades;

public class Familia {
	
		//Atributos
		private int FamiliaID;
		private String Nombre;
		private String Descripcion;
		private int Estado;
		
		//Metodos
		public int getFamiliaID() {
			return FamiliaID;
		}
		public void setFamiliaID(int FamiliaID) {
			this.FamiliaID = FamiliaID;
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
		
		public int getEstado() {
			return Estado;
		}
		public void setEstado(int Estado) {
			this.Estado = Estado;
		}

}