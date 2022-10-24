<%@ page contentType="text/html; charset=UTF-8" import = "entidades.Evento, datos.Dt_Evento ,java.util.*;"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portal Arboreto Carmelo Palma - Eventos</title>
    
    <!-- Icon -->
	<jsp:include page="imgShortIcon.jsp" />  

    <!-- CSS boostrap-->
    <link rel="stylesheet" href="css/bootstrap.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/styles2.css">
    <link rel="stylesheet" href="css/styles4.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">

    <!-- BOOTSTRAP V.4 CSS-->
    <link rel="stylesheet" href="./style/style.css">

    <!-- FONT AWESOME -->
    <script src="https://kit.fontawesome.com/78a455df4c.js" crossorigin="anonymous"></script>

    <!-- Calendar CSS -->
    <link rel="stylesheet" href="css/evo-calendar.midnight-blue.min.css">
    <link rel="stylesheet" href="css/evo-calendar.min.css">
    <!-- Add jQuery -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.min.js"></script>
    
    <style type="text/css">
	html, body {
	  height: 100%;
	  margin: 0;
	}
	.wrapper {
	  min-height: 100%;
	  margin-bottom: -50px;
	}	
	.marco {
	  width: 200px;
	  height: 100px;
	}
		
	.fill {
	  object-fit: fill;
	}
	
	
	</style>

</head>

<body>

<div class="wrapper mb-5">
    <!-- Menu -->
   <jsp:include page="mainMenus.jsp" />
  
    <!-- Contenido -->
    <div class="container">
    	
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
        
   		  <!-- Header -->
            <h1 class="my-4 text-center">
                Eventos
            </h1>
            <hr class="bg-dark">
   		  <!-- End of Header -->
   		  
        <div class="card mt-4 mb-5">
            <div id="calendar" style="border: 3px;"></div>
        </div>
    </div>
    <!-- Fin Contenido -->

</div>
    <!-- footer -->
     <jsp:include page="mainFooter.jsp" />
   

    <!-- js Calendar -->
    <script src="js/evo-calendar.js"></script>
    <script src="js/evo-calendar.min.js"></script>

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
  			                   color: "#ff8303", // Event custom color (optional)  
  							}				
  			);
  			}
  	    }  		
  		MostrarDatos();
  		
  		   	 $("#calendar").evoCalendar({ 
	   		 theme: "Midnight Blue",
	     	  language: "es",
	     	   calendarEvents: fechasJSON 		  	 		     	
	   	  });	
	   	 
   	});


    </script>

    <!-- Javascript -->
    <link rel="stylesheet" href="js/bootstrap.min.js">
    <script defer src="./js/index.js"></script>

    <!-- BOOTSTRAP V.4 -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.6.0/dist/umd/popper.min.js"
        integrity="sha384-KsvD1yqQ1/1+IA7gi3P0tyJcT3vR+NdBTt13hSJ2lnve8agRGXTTyNaBYmCR/Nwi"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.min.js"
        integrity="sha384-nsg8ua9HAw1y0W1btsyWgBklPnCUAFLuTMS2G72MMONqmOymq585AcH49TLBQObG"
        crossorigin="anonymous"></script>

</body>
</html>