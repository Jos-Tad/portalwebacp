<%@ page contentType="text/html; charset=UTF-8" import = "entidades.Evento, datos.*,vistas.* ,java.util.*;"%>
<%
	response.setHeader( "Pragma", "no-cache" );
	response.setHeader( "Cache-Control", "no-store" );
	response.setDateHeader( "Expires", 0 );
	response.setDateHeader( "Expires", -1 );
	
	ViewRolUsuario vru = new ViewRolUsuario();
	Dt_RolOpcion dtro = new Dt_RolOpcion();
	ArrayList<ViewRolOpcion> listOpc = new ArrayList<ViewRolOpcion>();
	
	//OBTENEMOS LA SESION
	vru = (ViewRolUsuario)session.getAttribute("acceso");
	if(vru==null){
		response.sendRedirect("login.jsp?msj=401");
	}
	else{
		//OBTENEMOS LA LISTA DE OPCIONES ASIGNADAS AL ROL
		listOpc = dtro.listaRolOpc(vru.getRolid());
		
		//RECUPERAMOS LA URL = MI OPCION ACTUAL
		int index = request.getRequestURL().lastIndexOf("/");
		String miPagina = request.getRequestURL().substring(index+1);
		boolean permiso = false; //VARIABLE DE CONTROL
		
		//VALIDAR SI EL ROL CONTIENE LA OPCION ACTUAL DENTRO DE LA MATRIZ DE OPCIONES
		for(ViewRolOpcion vrop : listOpc){
			if(vrop.getOpcion().trim().equals(miPagina.trim())){
				permiso = true; //ACCESO CONCEDIDO
				break;
			}
		}
		
		if(!permiso){
			response.sendRedirect("401.jsp");
		}	
	}
%>	
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Portal ACP - Agenda Pública</title>
    
    <!-- Icon -->
	<jsp:include page="imgShortIcon.jsp" />  
	

    <!-- Custom fonts for this template -->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"
        integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"
        crossorigin="anonymous"></script>

    <!-- Calendar CSS -->
    <link rel="stylesheet" href="css/evo-calendar.min.css">
    <!-- jQuery -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.min.js"></script>

</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

	<!-- Menu -->
	<jsp:include page="adminMenus.jsp" />   
	
                <!-- Begin Page Content -->
                <div class="container-fluid">

 <%
    	  ArrayList<Evento> listEventos = new ArrayList<Evento>();
    	  Dt_Evento dth = new Dt_Evento();
          listEventos = dth.listarEventos();
        %> 	
 	 	 <%for(Evento ev: listEventos){ 
 	 	     if(ev.getTipoevento()==1){
 	 	    	 //Cambio de simbolo de - a /
 	 	    	 StringBuilder fechaInicio = new StringBuilder(ev.getFechainicio());
 	         	 fechaInicio.setCharAt(4, '/');
 	       	     fechaInicio.setCharAt(7, '/');
 	             StringBuilder fechaFin = new StringBuilder(ev.getFechafin());
 	             fechaFin.setCharAt(4, '/');
 	             fechaFin.setCharAt(7, '/'); 	            	 
             %>
 			
 			<!-- Se imprime un input por atributo de cada objeto -->
 		   <input type="hidden" class="nombreEvento"  value="<%=ev.getNombre()%>">	
 		   		   
 		   <input type="hidden" class="descripcionEvento" value="<%=ev.getDescripcion()%>">	
 			
 			<input type="hidden" class="fechaInicio" value="<%=fechaInicio%>">			
            
           <input type="hidden" class="horaInicio" value="<%=ev.getHorainicio()%>">          
           
           <input type="hidden" class="fechaFin" value="<%=fechaFin %>"> 
           
           <input type="hidden" class="horaFin" value="<%=ev.getHorafin() %>"> 
           
           <input type="hidden" class="tipoEvento" value="<%=ev.getTipoevento() %>">
          
           <input type="hidden" class="multimedia" value="<%=ev.getMultimedia() %>"> 
           
           <input type="hidden" class="hipervinculo" value="<%=ev.getHipervinculo() %>"> 
           
           <input type="hidden" class="ubicacion" value="<%=ev.getUbicacion() %>"> 
         
        <%
        }
        }
        %> 
                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Agenda Pública</h1>

                    <!-- Contenido -->
                    <div class="container">
                        <div class="card mt-4">
                            <div id="calendar" style="border: 3px;"></div>
                        </div>
                    </div>
                    <!-- Termina Contenido -->

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

            <!-- Footer -->
      	   <jsp:include page="adminFooter.jsp" />   

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
   <jsp:include page="adminLogOutModal.jsp" />   
   
   <script>
 	//Declaracion de arreglos vacios
  	var nombreEvento = 	[];
  	var horaIevento = [];
  	var horaFevento = [];
  	var descripcionEvento = [];	
	var fechasI = [];
	var fechasF =[];
	var fechasJSON = [];	
	var hipervinculoEvento = [];
   	var ubicacionEvento = [];
   	var multimediaEvento = [];

   	
    
  	$(document).ready(function (){
  			//Funcion MostrarDatos
   	  		const MostrarDatos = () => {
   	  		//Declaracion y obtención de los valores de los inputs	
  	    	var nombre = $(".nombreEvento");
  	    	var descripcion = $(".descripcionEvento");
   	  		var fechaInicio = $(".fechaInicio");  	
  	    	var fechaFinal = $(".fechaFin");
  	    	var horaI = $(".horaInicio");
  	    	var horaF = $(".horaFin");
  	    	var hipervinculo = $(".hipervinculo");  
  	    	var ubicacion = $(".ubicacion");
  	    	var multimedia = $(".multimedia"); 	    	

  	    	//Loops para añadir los valores a los arreglos, antes declarados vacios
  	  		for(var i=0; i<fechaInicio.length; i++){
  	  			nombreEvento.push(nombre[i].value);
  	  			fechasI.push(fechaInicio[i].value);
  	   			fechasF.push(fechaFinal[i].value);
  	   			descripcionEvento.push(descripcion[i].value);
  	   			horaIevento.push(horaI[i].value);
  	  			horaFevento.push(horaF[i].value);
  	  		    hipervinculoEvento.push(hipervinculo[i].value);
  	  			ubicacionEvento.push(ubicacion[i].value);
  	  			multimediaEvento.push(multimedia[i].value);    			
  	  		}
  	   	
  	    	//Se añade al arreglo que carga el calendario y se recorre cada arreglo de según el atributo
  	  		for(var i=0; i<fechasI.length; i++){
  					fechasJSON.push(
  							{
  			   				   name: nombreEvento[i], // Event name (required)
  			                   date: [fechasI[i], fechasF[i]],// Event date (required)
  			                   type: "event", // Event type (required)
  			                   description: "Descripción: " + descripcionEvento[i] + "<br> Duración " + horaIevento[i] + "-" + " "+  horaFevento[i] + "<br> Enlace: " + hipervinculoEvento[i] + "<br> Ubicación: " + ubicacionEvento[i]
  			                   + "<br><br>" +"<img  class=img-thumbnail src="+multimediaEvento[i]+">", 
  			                   color: "#325288", // Event custom color (optional)  
  							}				
  			);
  			}

  	    }
  		
  		MostrarDatos();
  		
  		   	 $("#calendar").evoCalendar({ 
	   	  	  language: "es",
	     	   calendarEvents: fechasJSON 			  	 	
	     	
	   	  });
	   	 
   	});


    </script>
   
   
    <!-- JAVASCRIPTS -->
    <link rel="stylesheet" href="vendor/datatables/jquery.dataTables.js">

    <!-- JS CALENDAR -->
    <script src="js/evo-calendar.js"></script>
    <script src="js/evo-calendar.min.js"></script>

  
    <!-- Bootstrap core JavaScript-->
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="js/demo/datatables-demo.js"></script>




</body>

</html>