<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import = "vistas.ViewArbol,datos.Dt_Arbol, java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portal Arboreto Carmelo Palma - Árboles</title>
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
    <!-- Menu -->
    <div class="wrapper">	
   <jsp:include page="mainMenus.jsp" />
   
    <!-- Contenido -->
    <div class="container mb-5">
        <!-- Page Heading -->
        <h1 class="my-4">Árboles
        <hr class="border-dark">
        </h1>
        <div class="row">
  			 <%
               	ArrayList<ViewArbol> listArbol = new ArrayList<ViewArbol>();
               	Dt_Arbol dta = new Dt_Arbol();
               	listArbol = dta.listaArbol();    
               	
               	if(listArbol.isEmpty()){
              %>             
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
               </figure>
	           </div> <!-- col // -->
	       </div> <!-- row.// -->
	    </div>
	    <!--container.//-->     
             <%}           
               	else{for(ViewArbol a: listArbol){%>
                <div class="col-lg-4 col-sm-6 mb-4">
                <div class="card h-100">
                    <a href="ArbolIn.jsp?arbolid=<%=a.getArbolID()%>"><img class="img-guide" src="<%=a.getMultimedia() %>" alt="Arbol <%=a.getNombreComun() %>"></a>
                    <div class="card-body">
                        <h4 class="card-title">
                            <a href="ArbolIn.jsp?arbolid=<%=a.getArbolID()%>" class="fw-bold text-dark"> <%=a.getNombreComun() %></a>
                        </h4>
                        <p class="card-text"><%=a.getDescripcion() %></p>
                    </div>       
                  </div>
               </div>
			    <%}}%>                   
        </div>
        <!-- /.row -->
    </div>
    <!-- /.container -->
</div>
    <!-- footer -->
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