<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="vistas.*, entidades.*, datos.*, java.util.*;"%>
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
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
 <meta name="description" content="">
 <meta name="author" content="">
<title>Video Publicación</title>
<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link
    href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
    rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/sb-admin-2.min.css" rel="stylesheet">

 <!-- Icon -->
<jsp:include page="imgShortIcon.jsp" /> 

</head>
<body class="bg-dark">

<div class="container">

        <div class="card o-hidden border-0 shadow-lg my-5">
            <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row">
                    
                    <div class="col-lg-12">
                        <div class="p-5">
                            <div class="text-center">
                                <h1 class="h1 text-gray-900 mb-4"><b>Video Publicación</b></h1>
                                <hr class="bg-dark w-100 ml-0">                            
                                
                            </div>
                            <div id="foto" class="panel">
								<form class="form" name="frm-video" method="post" action="./Sl_VideoPublicacion">
                   			 	<input name="opcion" type="hidden" value="2" />																	
								<%
									int idPublicacion = 0;
									Publicacion post = new Publicacion();
									Dt_Publicacion dtp = new Dt_Publicacion();
									idPublicacion = Integer.parseInt(request.getParameter("idIP"));
									post = dtp.getPublicacion(idPublicacion);
								%>
								<div class="container" align="center">
						  	   </div>
								<br>
								<div>								
								<%if(post.getMultimedia3()!=null){%>
								<div class="embed-responsive embed-responsive-21by9">
								<iframe width="964" height="542" src="<%=post.getMultimedia3()%>" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
								<%}else{%>
								<div class="container" align="center">
								<img id="Video" class="img-fluid" src="img/video.svg"  alt="Video"
											style="width: 300px; height: 200px; border-bottom-color: white; margin: 2px;" />
								</div>
								<%}%>																
								</div>	
								<br>					 								
								<input type="text" name="urlVideo" class="form-control">	
						 		<input type="hidden" name="idpublicacion" value="<%=idPublicacion%>">						 		
						 		<hr>
	                            <div class="text-center">
	                                <input class="btn btn-primary btn-user btn-block" type="submit" value="Guardar" />
	                            </div>
	                            <br>
	                            <div style="text-align: center; "><a href="GestionPublicacion.jsp"><i
                                    class="fas fa-arrow-circle-left"></i>&nbsp;Volver a la tabla</a></div>
								</form>
							</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>
       
<script>
	    $(document).ready(function() 
		{
//////////////////////////////////FUNCION PARA PREVISUALIZAR LA FOTO QUE SUBE EL USUARIO//////////////////////////
	    	Test = {
	    	        UpdatePreview: function(obj)
	    	        {
	    	          // if IE < 10 doesn't support FileReader
	    	          if(!window.FileReader)
	    	          {
	    	             
	    	          } 
	    	          else 
	    	          {
	    	             var reader = new FileReader();
	    	             var target = null;
	    	             
	    	             reader.onload = function(e) 
	    	             {
	    	              target =  e.target || e.srcElement;
	    	               $("#preview").prop("src", target.result);
	    	             };
	    	              reader.readAsDataURL(obj.files[0]);
	    	          }
	    	        }
	    	    };
		});
</script>  
</body>
</html>