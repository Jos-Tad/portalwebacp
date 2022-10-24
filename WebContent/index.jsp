<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "entidades.Home, datos.Dt_Home, entidades.Banner, datos.Dt_Banner, java.util.*;"
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portal Arboreto Carmelo Palma</title>
    
     <!-- Icon -->
	<jsp:include page="imgShortIcon.jsp" />  
	
    <!-- CSS boostrap-->
    <link rel="stylesheet" href="css/bootstrap.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/styles2.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">

    <!-- FONT AWESOME -->
    <script src="https://kit.fontawesome.com/78a455df4c.js" crossorigin="anonymous"></script>
	
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
	<!-- Nav -->
   <jsp:include page="mainMenus.jsp" />
   
   	 <%
   	   ArrayList<Home> listHome = new ArrayList<Home>();
	   Dt_Home dth = new Dt_Home();
	   listHome = dth.ListarHome();
	   
	   ArrayList<Banner> listBanner = new ArrayList<Banner>();
   	   Dt_Banner dtb = new Dt_Banner();
   	   listBanner = dtb.ListarBanner();
   	   		
	   Home hm  = new Home();
	   hm = listHome.get(0);
	   
	if(listBanner.size() == 0){
	%>
	<!-- Carousel -->
    <div id="mycarousel" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#mycarousel" data-bs-slide-to="0" class="active" aria-current="true"
                aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#mycarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#mycarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="https://via.placeholder.com/850" class="d-block w-100 h-50" alt="...">
            </div>
            <div class="carousel-item">
                <img src="https://via.placeholder.com/850" class="d-block w-100 h-50" alt="...">
            </div>
            <div class="carousel-item">
                <img src="https://via.placeholder.com/850" class="d-block w-100 h-50" alt="...">
            </div>
        </div>
        <button class="carousel-control-prev " type="button" data-bs-target="#mycarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="false"></span>
            <span class="visually-hidden">Anterior</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#mycarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon position" aria-hidden="false"></span>
            <span class="visually-hidden">Siguiente</span>
        </button>
    </div>
	<%}
	else{	
	%>
	<!-- Carousel -->
    <div id="mycarousel" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-indicators">            
           <button type="button" data-bs-target="#mycarousel" data-bs-slide-to="0" class="active" aria-current="true"
                aria-label="Slide 1"></button>           
            <%
			 for (int counter = 1; counter < listBanner.size(); counter++) {
	  	 	%>
      	          <button type="button" data-bs-target="#mycarousel" data-bs-slide-to="<%=counter %>>" aria-label="Slide <%=counter %>"></button>
        	  <%
			 }
        	 %>    </div>
          <div class="carousel-inner ">		
	       <div class="carousel-item active">
 			<img src="<%=listBanner.get(0).getMultimedia()+"?t="+System.currentTimeMillis()%>" class="d-block w-100 h-50" alt="Imagen Banner 1">                   <div class="carousel-caption d-none d-md-block">
			        <h5 class= "fw-light"><%=listBanner.get(0).getTitulobanner() %></h5>
			         <p  class= "fw-light"><%=listBanner.get(0).getDescripcion()%></p>
				   </div>
            </div> 
            <%
		    for (int counter = 1; counter < listBanner.size(); counter++) {
            %>
             <div class="carousel-item ">
			<img src="<%=listBanner.get(counter).getMultimedia() +"?t="+System.currentTimeMillis()%>" class="d-block w-100 h-50" alt="imagen <%=counter %>>">
                <div class="carousel-caption d-none d-md-block">
			        <h5 class= "fw-light"><%=listBanner.get(counter).getTitulobanner() %></h5>
					<p class= "fw-light"><%=listBanner.get(counter).getDescripcion()%></p>	
				  </div>
            </div>
            <% }
            %>
        </div>
        
        <button class="carousel-control-prev " type="button" data-bs-target="#mycarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="false"></span>
            <span class="visually-hidden">Anterior</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#mycarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon position" aria-hidden="false"></span>
            <span class="visually-hidden">Siguiente</span>
        </button>
    </div>
    <%}%>

    <!-- Section historia -->
    <div class="container">
        <!-- Titulo de la historia -->
        <h1 class="display-4 mt-4"> Historia </h1>
        <hr class="bg-dark w-25 ml-0">
        <div class="row">
            <div class="col-md-5">
                <img class="img-fluid border border-dark" src="<%= hm.getImg_historia()+"?t="+System.currentTimeMillis()%>" alt="imagen Historia">
            </div>

            <div class="col-md-6">
                <p class= "lead"><%=hm.getHistoria()%></p>           
            </div>
        </div>
    </div>
    <br>
 
    <!-- Section Misión -->
    <div class="container mb-5">
        <div class="row">
            <div class="col-md-6">
                <h3 class="display-4">Misión</h3>
                <hr class="bg-dark w-25 ml-0">
                <p class="lead">
                    <%=hm.getMision()%>
                </p>
            </div>
            <div class="col-md-5 mt-7">
                <img class="img-fluid border border-dark" src="<%=hm.getImg_mision()+"?t="+System.currentTimeMillis()%>" alt="imagen Mision">
            </div>
        </div>
    </div>
 
    <!-- Section Visión -->
    <div class="container mb-5">
        <div class="row ">
			  <div class="col-md-5 mt-7 ">
                <img class="img-fluid border border-dark" src="<%=hm.getImg_vision()+"?t="+System.currentTimeMillis()%>" alt=" imagen Vision">
            </div>
            <div class="col-md-7">
                <h3 class="display-4">Visión</h3>
                <hr class="bg-dark w-25 ml-0">
                <p class="lead">
                 <%=hm.getVision()%>
                </p>
            </div>                    
        </div>
    </div>
</div>
	
    <!-- Footer -->
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