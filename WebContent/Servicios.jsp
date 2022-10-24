<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
 import="entidades.Servicio, datos.Dt_Servicio, negocio.Ng_Servicio, java.util.*;" %>
   
<!DOCTYPE html>            
<html lang="en">
<head>
  <meta charset="ISO-8859-1">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Portal Arboreto Carmelo Palma - Servicios</title>
  
   <!-- Icon -->
   <jsp:include page="imgShortIcon.jsp" />  
	

  <!-- CSS boostrap-->
  <link rel="stylesheet" href="css/bootstrap.min.css">

  <!-- Custom CSS -->
  <link rel="stylesheet" href="css/styles2.css">

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">

  <!-- BOOTSTRAP V.4 CSS-->
  <link rel="stylesheet" href="./style/style.css">

  <!-- FONT AWESOME -->
  <script src="https://kit.fontawesome.com/78a455df4c.js" crossorigin="anonymous"></script>

  <!-- CSS IMAGE -->  
  <style type="text/css">	
	.marco {
	  width: 200px;
	  height: 100px;
	}
		
	.fill {
	  object-fit: fill;
	}
	
    html, body {
	  height: 100%;
	  margin: 0;
	}
	.wrapper {
	  min-height: 100%;
	  margin-bottom: -50px;
	}
</style>
</head>
<body>
<div class="wrapper mb-5">
<!-- Menu -->
<jsp:include page="mainMenus.jsp" />

<!-- Contenido -->
<div class="container mb-5">
<br>
<h2 class="text-center">Servicios</h2>
<hr>

<div class="row">
		<%       			
		//Construir objecto negocio
		Ng_Servicio ngs = new Ng_Servicio();
		if(ngs.validarVisibilidad()){%>
		 <div class="row">
           <div class="col-md-4">
               <figure class="card card-product">
                   <div class="img-wrap"><img class="img-guide" src="img/Defecto.jpeg"></div>
                   <figcaption class="info-wrap">
                       <h4 class="title">amet consectetur</h4>
                       <p class="desc">Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum dolorum earum
                           accusamus optio, iusto accusantium deleniti quod alias error nulla voluptate a eaque soluta!
                           Esse iure vel minus consectetur ad?, Lorem ipsum dolor, sit amet consectetur adipisicing
                           elit. Sapiente reiciendis quod fuga </p>
                   </figcaption>
                   <div class="bottom-wrap">
                       <a href="" class="btn btn-sm btn-primary float-right">Contactar</a>
                   </div> <!-- bottom-wrap.// -->
               </figure>
           </div> <!-- col // -->
           <div class="col-md-4">
               <figure class="card card-product">
                   <div class="img-wrap"><img class="img-guide" src="img/Defecto.jpeg"> </div>
                   <figcaption class="info-wrap">
                       <h4 class="title">accusantium iusto</h4>
                       <p class="desc">Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum dolorum earum
                           accusamus optio, iusto accusantium deleniti quod alias error nulla voluptate a eaque soluta!
                           Esse iure vel minus consectetur ad?, Lorem ipsum dolor, sit amet consectetur adipisicing
                           elit. Sapiente reiciendis quod fuga </p>
                   </figcaption>
                   <div class="bottom-wrap">
                       <a href="" class="btn btn-sm btn-primary float-right">Contactar</a>
                   </div> <!-- bottom-wrap.// -->
               </figure>
           </div> <!-- col // -->
           <div class="col-md-4">
               <figure class="card card-product">
                   <div class="img-wrap"><img class="img-guide" src="img/Defecto.jpeg"></div>
                   <figcaption class="info-wrap">
                       <h4 class="title">ipsum dolor</h4>
                       <p class="desc">SLorem ipsum dolor sit amet consectetur adipisicing elit. Eum dolorum earum
                           accusamus optio, iusto accusantium deleniti quod alias error nulla voluptate a eaque soluta!
                           Esse iure vel minus consectetur ad?, Lorem ipsum dolor, sit amet consectetur adipisicing
                           elit. Sapiente reiciendis quod fuga </p>
                   </figcaption>
                   <div class="bottom-wrap">
                       <a href="" class="btn btn-sm btn-primary float-right">Contactar</a>
                   </div> <!-- bottom-wrap.// -->
               </figure>
	           </div> <!-- col // -->
	       </div> <!-- row.// -->	    
	    <!--container.//-->     	    
	      <%}else{   
	     	 ArrayList<Servicio> listServicio = new ArrayList<Servicio>();
			 Dt_Servicio dts = new Dt_Servicio();
			 listServicio = dts.listarServicio(); 
		   	
	     	  for(Servicio sr: listServicio){                    	
	          if(sr.getEstadoservicio()!=2){
	          String desc = sr.getDescripcion();	    
	          %>			          
			  
			  <div class="col-md-4">
			    <figure class="card card-product border border-dark">
			        <div class="card-img">
			        <img class="img-fluid marco fill" src="<%=sr.getMultimedia()%>">
			        </div>
			        <figcaption class="info-wrap">
			            <h4 class="card-title"><%=sr.getNombre()%></h4>
			            <p class="card-text descServicio" onClick="getValue()" title="<%=sr.getDescripcion()%>"><%=desc%></p>
			        </figcaption>
			        <div class="bottom-wrap">
			            <a href="Contacto.jsp" class="btn btn-sm btn-primary float-right">Contactar</a>
			        </div> <!-- bottom-wrap.// -->
			    </figure>
				</div> <!-- col // -->				       
			<%
			  }//Fin if
		     }//Fin For
		    }//Fin else
		   %>  
		</div>	            
<!--container.//-->
</div>
</div>	
	
<!-- footer -->
<jsp:include page="mainFooter.jsp" />

<!-- Javascript -->
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