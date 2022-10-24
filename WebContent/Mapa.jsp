<%@ page contentType="text/html; charset=UTF-8" import = "entidades.*, datos.*,vistas.* ,java.util.*;"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portal Arboreto Carmelo Palma - Mapa Interactivo</title>
    
     <!-- Icon -->
	 <jsp:include page="imgShortIcon.jsp" />  
	

    <!-- CSS boostrap-->
    <link rel="stylesheet" href="css/bootstrap.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/styles2.css">
    <link rel="stylesheet" href="css/styles4.css">
    <link rel="stylesheet" href="css/imageMapa.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">

    <!-- BOOTSTRAP V.4 CSS-->
    <link rel="stylesheet" href="./style/style.css">

    <!-- FONT AWESOME -->
    <script src="https://kit.fontawesome.com/78a455df4c.js" crossorigin="anonymous"></script>

    <!-- CSS MAP -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.3/leaflet.css" />
    <link rel="stylesheet" href="css/leaflet.groupedlayercontrol.css" />

</head>

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

<body class="bg-faded">

<div class="wrapper mb-5">

    <!-- Menu -->
    <jsp:include page="mainMenus.jsp" />
    
  	  <%
    	  ArrayList<ViewCoordenadaArbol> listCoA = new ArrayList<ViewCoordenadaArbol>();
    	  Dt_CoordenadaArbol dth = new Dt_CoordenadaArbol();
    	  listCoA = dth.listarCoordenadaArbol();
        %> 	
 	 	 <%for(ViewCoordenadaArbol vcoa: listCoA){%>
 	 	   <input type="hidden" class="NombreComun"  value="<%=vcoa.getNombrecomun()%>">	
 		   		   
 		   <input type="hidden" class="NombreCientifico" value="<%=vcoa.getNombrecientifico()%>">	

		   <input type="hidden" class="Descripcion" value="<%=vcoa.getDescar()%>">	

		   <input type="hidden" class="Multimedia" value="<%=vcoa.getMultimedia()%>">	
		   
		   <input type="hidden" class="Latitud" value="<%=vcoa.getLatitud()%>">	
		   
		   <input type="hidden" class="Longitud" value="<%=vcoa.getLongitud()%>">	
		    			 	 		 
 	 	 <%}%>	

    <!--Maps-->
    <div class="container mt-5 mb-5">
     		 <!-- Header -->
            <h1 class="my-4 text-center">
                Mapa interactivo
            </h1>
            <hr class="bg-dark">
   		  <!-- End of Header -->
   		  
            <div class="card p-2 rounded-3 border-dark">
            <div id="map" style="height: 610px; padding: 0; margin: 0; "></div>
        </div>
    </div>
    </div>


   

    <!-- Js Mapa Source -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.3/leaflet.js"></script>
    <script src="js/leaflet.groupedlayercontrol.js"></script>
    <script src="js/Mapa.js"></script>

    <!-- Js Mapa Custom -->
    <script>
        var map = L.map('map', {
            center: [12.126932040970864, -86.27038300037383],
            zoom: 19,
            layers: [Mapa.Basemaps.Calles, Mapa.LayerGroups.Arboles]
        });
        
        //test      
        var PhotoImg = '<img src="https://i.imgur.com/TBFb9zI.jpg" height="150px" width="150px" class="center" />';
        var PhotoImg1 = '<img src = "https://i.imgur.com/0grafib.jpg" height="150px" width="150px" class="center" />';
        var PhotoImg2 = '<img src = "https://i.imgur.com/WGGHrxW.jpg" height="150px" width="150px" class="center" />';
        var LeafIcon = L.Icon.extend({
            options: {
                shadowUrl: 'https://i.imgur.com/lsGlWbl.png',
                iconSize:     [38, 95],
                shadowSize:   [50, 64],
                iconAnchor:   [22, 94],
                shadowAnchor: [4, 62],
                popupAnchor:  [-3, -76]
            }
        });
        var greenIcon = new LeafIcon({iconUrl: 'https://i.imgur.com/kCbcOsA.png'}),
        	orangeIcon = new LeafIcon({iconUrl: 'https://i.imgur.com/ANYFjzm.png'});
        
       	
        var nombreComun = document.getElementsByClassName('NombreComun');
        var nombreCientifico = document.getElementsByClassName('NombreCientifico');
        var descripcion = document.getElementsByClassName('Descripcion');
        var multimedia = document.getElementsByClassName('Multimedia');
        var latitud = document.getElementsByClassName('Latitud');
        var longitud = document.getElementsByClassName('Longitud');

        
        for (let step = 0; step < nombreComun.length; step++) { 
        	la = parseFloat (latitud[step].value);
        	lo = parseFloat (longitud[step].value);   
        	var marker1 = L.marker([lo, la], {icon: greenIcon});
        	marker1.addTo(map);
        	marker1.on('mouseover', function(e){
        	    e.target.bindPopup("<center><b>"+nombreComun[step].value+"</b></center><center>"+nombreCientifico[step].value+
            			"</center>"+"<img class=center border border-dark height=80px width=100px src="+multimedia[step].value+">"+"<center>"+descripcion[step].value+"</center>").openPopup();
  
        	  }); 
        	
     	     
        }
        
      //end of the test

        // Capa para ver en vista satelite 
        var groupedOverlays = {
        };

        //Controlador de groudLayers"
        L.control.groupedLayers(Mapa.Basemaps, groupedOverlays).addTo(map);
    </script>
      	
    <!-- Javascript -->
    <link rel="stylesheet" href="js/bootstrap.min.js">
    <script defer src="./js/index.js"></script>
    
        <!-- footer -->
     <jsp:include page="mainFooter.jsp" />

    <!-- BOOTSTRAP V.4 -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.6.0/dist/umd/popper.min.js"
        integrity="sha384-KsvD1yqQ1/1+IA7gi3P0tyJcT3vR+NdBTt13hSJ2lnve8agRGXTTyNaBYmCR/Nwi"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.min.js"
        integrity="sha384-nsg8ua9HAw1y0W1btsyWgBklPnCUAFLuTMS2G72MMONqmOymq585AcH49TLBQObG"
        crossorigin="anonymous"></script>
</body>
</html>