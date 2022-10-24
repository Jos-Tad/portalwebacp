<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "entidades.Publicacion, datos.Dt_Publicacion, java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portal Arboreto Carmelo Palma - Publicaciones</title>
    
     <!-- Icon -->
	 <jsp:include page="imgShortIcon.jsp" />  
	
    <!-- CSS boostrap-->
    <link rel="stylesheet" href="./css/bootstrap.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="./css/styles2.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">

    <!-- BOOTSTRAP V.4 CSS-->
    <link rel="stylesheet" href="./style/style.css">

    <!-- FONT AWESOME -->
    <script src="https://kit.fontawesome.com/78a455df4c.js" crossorigin="anonymous"></script>
   
   <!-- CSS IMAGE -->
  <link rel="stylesheet" href="./css/image.css">
  
  <style type="text/css">
    .ellipsis {
      text-overflow: ellipsis;
      white-space: nowrap;
      overflow: hidden;
     }
     html, body {
	  height: 100%;
	  margin: 0;
	}
	.wrapper {
	  min-height: 100%;
	}
  </style>
</head>
<body>
<div class="wrapper">	
 <!-- Menu -->
  <jsp:include page="mainMenus.jsp" />

    <!-- Contenedor -->
    <div class="container">

        <!-- Cabecera de la pagina -->
        <h1 class="my-4">Publicaciones
            <hr class="bg-dark w-25 ml-0">
            <small></small>
        </h1>
   	    <div class="row" >
  		<%
       		ArrayList<Publicacion> ListaPost = new ArrayList<Publicacion>();
        	Dt_Publicacion dta = new Dt_Publicacion();
        	ListaPost = dta.ListaPost();   
        	
        	if(ListaPost.isEmpty()){
        %>
       		<div class="card w-75 " style="margin-bottom:20px; border:3px solid">
			<div class="card-body">
			  <h5 class="card-title">Tarjeta Prueba</h5>
			  <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
			</div>
		    </div> 
		       
		    <div class="card w-75 " style="margin-bottom:20px;border:3px solid">
			<div class="card-body">
			  <h5 class="card-title">Tarjeta Prueba</h5>
			  <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
			</div>
		    </div>
		    
		    <div class="card w-75 "style="margin-bottom:20px;border:3px solid">
			<div class="card-body">
			  <h5 class="card-title">Tarjeta Prueba</h5>
			  <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
			</div>
		    </div>
		    
		    <div class="card w-75 "style="margin-bottom:20px;border:3px solid">
			<div class="card-body">
			  <h5 class="card-title">Tarjeta Prueba</h5>
			  <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
			</div>
		    </div>
		    
		    <div class="card w-75 "style="margin-bottom:20px;border:3px solid">
			<div class="card-body">
			  <h5 class="card-title">Tarjeta Prueba</h5>
			  <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
			</div>
		    </div>
		    
		    <div class="card w-75"style="margin-bottom:20px;border:3px solid">
			<div class="card-body">
			  <h5 class="card-title">Tarjeta Prueba</h5>
			  <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
			</div>
		    </div>
		    
		    <div class="card w-75 mb-5" style="border:3px solid">
			<div class="card-body">
			  <h5 class="card-title">Tarjeta Prueba</h5>
			  <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
			</div>
		    </div>	
        <%}	
        	else{         
        	for(Publicacion a: ListaPost){
  			if(a.getEstadopublicacion()==1){
         %>            
			<div class="card w-75 ml-2 mt-3" style="border:3px solid; ">
			<div class="card-body">
			  <a style="color:black" href="PublicacionIn.jsp?publicacionid=<%=a.getPublicacionid()%>">
			  <h5 class="card-title"><%=a.getTitulo() %></h5>
			  </a>
			  <p class="card-text ellipsis"><%=a.getDescripcion()%> </p>
			</div>
			</div>		                        
    	<%}%>
  			<%if(a.getEstadopublicacion()==2){%>
  	      	<div class="card w-75 " style="margin-bottom:20px; border:3px solid">
			<div class="card-body">
			  <h5 class="card-title">Tarjeta Prueba</h5>
			  <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
			</div>
		    </div>    
		    <div class="card w-75 " style="margin-bottom:20px;border:3px solid">
			<div class="card-body">
			  <h5 class="card-title">Tarjeta Prueba</h5>
			  <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
			</div>
		    </div>				    
  	   <%}
         }
         }
        %>    
 	</div>
 	</div>
 </div>
 	
<!-- Footer -->
<jsp:include page="mainFooter.jsp" />

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