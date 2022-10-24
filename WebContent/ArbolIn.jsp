<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "vistas.ViewUbicacionArbol,datos.Dt_UbicacionArbol, vistas.ViewArbol, datos.Dt_Arbol, java.util.*"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portal Arboreto Carmelo Palma - Publicación</title>
    
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
                                                  
 <%											
 ArrayList<ViewArbol> listArbol = new ArrayList<ViewArbol>();
 Dt_Arbol dta = new Dt_Arbol();
                 	listArbol = dta.listaArbol();    
                 	
                 		
 %> 
</head>

<body>
<div class="wrapper">
 <!-- Menu -->
 <jsp:include page="mainMenus.jsp" />
 
    <br>
    <br>
						
						
		    <div class="row">
		    	<%
		    	String arbolid = "";
				arbolid = request.getParameter("arbolid")==null?"0":request.getParameter("arbolid");
				int MostrarInfo = Integer.parseInt(arbolid);
		    	for(ViewArbol a: listArbol){ 
		    	if(a.getArbolID()==MostrarInfo){%>
		        <div class="col-lg-10 col-xl-8 offset-lg-1 offset-xl-2">
		            <div class="intro">
		                <h1 class="text-center"><%=a.getNombreComun() %></h1>
		                <p class="text-center"><span class="date"><%=a.getNombreCientifico() %> </span></p>
		                <br>
		                <img class="img-fluid mx-auto d-block" src="<%=a.getMultimedia()%>">
		            </div>
		            <br>
		            <div class="text-center">
			                <p>Descripción: <%=a.getDescripcion() %></p>
			           
			               <p>Género: <%=a.getNombreGenero() %></p>
			           
			               <p>Familia: <%=a.getNombreFam() %></p>
			                 
			               <p>Floración: <%=a.getNombreFlo() %></p>              		               
			               
			                        
         	   </div>
       		 </div>
       		 <%
		    	}
       		 } 
       		 %>
    </div>
    <br>
    <br>
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