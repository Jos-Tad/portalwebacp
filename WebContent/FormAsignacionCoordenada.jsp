<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" 
import="datos.*,entidades.*,negocio.*,vistas.*,java.util.*;"%>
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
	//Variable de control de mensajes
	String varMsj = request.getParameter("msj")==null?"":request.getParameter("msj");
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
  
    <title>Portal ACP - Formulario Mapa</title>
    
     <!-- Icon -->
	 <jsp:include page="imgShortIcon.jsp" />  
    
    <link rel="icon" href="img/logo_jesuita.ico" type="img/ico">
  
    <!-- Custom fonts for this template -->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    
    <!-- jAlert css  -->
	<link rel="stylesheet" href="jAlert/dist/jAlert.css" />
	
    <!-- CSS MAP -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.3/leaflet.css" />
    <link rel="stylesheet" href="css/leaflet.groupedlayercontrol.css" />
</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Menus -->
  		 <jsp:include page="adminMenus.jsp" />  
  		 
  		   <%
    	  ArrayList<Coordenada> listC = new ArrayList<Coordenada>();
    	  Dt_CoordenadaArbol dth = new Dt_CoordenadaArbol();
    	  listC = dth.listarCoordenadas();
        %> 	
 	 	 <%for(Coordenada ca: listC){%>
 	 	   <input type="hidden" class="Nombre" value="<%=ca.getNombre()%>">	
		 	 	 		 	 		   
		   <input type="hidden" class="Latitud" value="<%=ca.getLatitud()%>">	
		   
		   <input type="hidden" class="Longitud" value="<%=ca.getLongitud()%>">	
		   
		   
		    			 	 		 
 	 	 <%}%>	
 	 	 
 	    <%
    	  ArrayList<ViewCoordenadaArbol> listCoA = new ArrayList<ViewCoordenadaArbol>();
    	  Dt_CoordenadaArbol dth1 = new Dt_CoordenadaArbol();
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
        
                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Formulario -->
   			   		 <div class="container">
                        <div class="row">
                            <div class="col-lg-10 mx-auto m-auto">
                                <div class="card rounded shadow border-0">
                                    <div class="card-header">
                                        <h2>
                                            Formulario Mapa
                                        </h2>
                                    </div>
                                    <div class="card-body bg-white rounded">
                                     <div class="card p-2 rounded-3 border-dark">
								          <div id="map" style="height: 300px; width:150px padding: 0; margin: 0; "></div>
								      </div>
								      <br>
                                       <form class="AsignacionCoordenada" method="post" action="./Sl_GestionCoordenadasArbol">
                      					<input name="opcion" type="hidden" value="1" />
                                         <div class="form-group">                                                
                                            <div class="col-sm-12 mb-3">
                                            <%
		                                	ArrayList<Coordenada> listCo = new ArrayList<Coordenada>();
		                                	Dt_CoordenadaArbol dtco = new Dt_CoordenadaArbol();
		                                	listCo = dtco.listarCoordenadas();
                                			%>
                                			<label for="formGroupExampleInput">Coordenada:</label>
	                                    	<select class="form-control" name="cbxCoordenada" id="cbxCoordenada" required>	                                    	
	                                 		<option value="" selected disabled>Seleccionar</option>                                  			                                            	                                    		
	                                    	<%
	                                    		for(Coordenada c: listCo){
	                                    	%>	
	                                    		<option value="<%=c.getCoordenadaID()%>"><%=c.getNombre()%></option>
	                                    	<%
	                                    		}
	                                    	%>
	                                    	</select>
	                                    	</div>
                                            <div class="col-sm-12 mb-3">
                                            <%
		                                	ArrayList<ViewArbol> listArbol = new ArrayList<ViewArbol>();
		                                	Dt_Arbol dta = new Dt_Arbol();
		                                	listArbol = dta.listaArbol();
	                                		%>
	                                		<label for="formGroupExampleInput">Arbol:</label>
	                                    	<select class="form-control" name="cbxArbol" id="cbxArbol" required>
											<option value="" selected disabled>Seleccionar</option>                                    			                                            	
                                   			<%
	                                    		for(ViewArbol va: listArbol){
	                                    	%>	
	                                    		<option value="<%=va.getArbolID()%>"><%=va.getNombreComun()%></option>
	                                    	<%
	                                    		}
	                                    	%>                                    	
                                   			</select>
                                            </div>                                               
                                                
                                            </div>
                                         	 <div class="text-center">
				                                <input class="btn btn-primary btn-user btn-block" type="submit" value="Guardar" />
				                            </div> 
				                            <br> 
                                         <div style="text-align:center;" ><a href="GestionMapa.jsp"><i
                                                        class="fas fa-arrow-circle-left"></i>&nbsp;Volver a la tabla</a></div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Termina Formulario -->

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
        
    <!-- JAVASCRIPTS -->
    <link rel="stylesheet" href="vendor/datatables/jquery.dataTables.js">

    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="js/demo/datatables-demo.js"></script>
    
     <!-- jAlert js -->
	<script src="jAlert/dist/jAlert.min.js"></script>
	<script src="jAlert/dist/jAlert-functions.min.js"></script>
	
	
	  <!-- Js Mapa Source -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.3/leaflet.js"></script>
    <script src="js/leaflet.groupedlayercontrol.js"></script>
    <script src="js/Mapa.js"></script>

    <!-- Js Mapa Custom -->
    <script>
        var map = L.map('map', {
            center: [12.126932040970864, -86.27038300037383],
            maxNativeZoom: 19,
            zoom: 19,
            layers: [Mapa.Basemaps.Calles, Mapa.LayerGroups.Arboles]
        });
        
        //test      
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
        
        var nombre = document.getElementsByClassName('Nombre');
        var latitud = document.getElementsByClassName('Latitud');
        var longitud = document.getElementsByClassName('Longitud');
        var nombreComun = document.getElementsByClassName('NombreComun');
        var nombreCientifico = document.getElementsByClassName('NombreCientifico');
        var multimedia = document.getElementsByClassName('Multimedia');
        var descripcion = document.getElementsByClassName('Descripcion');
        	
        
        for (let step = 0; step < nombre.length; step++) { 
        	la = parseFloat (latitud[step].value);
        	lo = parseFloat (longitud[step].value);       	
        	L.marker([lo, la], {icon: greenIcon}).addTo(map).bindPopup("<center><b>"+nombre[step].value+"</b></center>"
     		);
     	     
        }   
        


        // Capa para ver en vista satelite 
        var groupedOverlays = {
        };

        //Controlador de groudLayers"
        L.control.groupedLayers(Mapa.Basemaps, groupedOverlays).addTo(map);
    </script>
    
	<script type="text/javascript">
	
	    $(document).ready(function ()
	    {

			/////////// VARIABLE DE CONTROL MSJ ///////////
	        var mensaje = "";
	        mensaje = "<%=varMsj%>";
	
	        if(mensaje == "existe")
	        {
	        	 $.jAlert({
	                 'title': 'Error',
	                 'content': '¡Asignación de coordenada ya existe!',
	                 'theme': 'red',
	                 'onClose': function(OnClose) {               
	                     window.location = "FormAsignacionCoordenada.jsp";
	                 }
	               });
	        }
	        if(mensaje == "existe1")
	        {
	        	 $.jAlert({
	                 'title': 'Error',
	                 'content': '¡La asignación del arbol ya existe!',
	                 'theme': 'red',
	                 'onClose': function(OnClose) {               
	                     window.location = "FormAsignacionCoordenada.jsp";
	                 }
	               });
	        }
	    });
	</script>
</body>
</html>