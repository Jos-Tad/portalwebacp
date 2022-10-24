<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
import = "vistas.ViewHead,datos.Dt_ViewHead, java.util.*;"%>

<%
   	ArrayList<ViewHead> listViewHead = new ArrayList<ViewHead>();
   	Dt_ViewHead dth = new Dt_ViewHead();
   	listViewHead = dth.ListarViewHead();
     
    String logo = null;
    String titulo = null;
          
	ViewHead vwh = listViewHead.get(0);	
    logo = vwh.getLogo();
    titulo = vwh.getTitulo();	 
%>	
 					
 <!-- Menu -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-success">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">   <img src="img/<%=logo%>" alt="LogoACP" style="width: 200px; height: 70px;" id="imgLogo"><%=titulo %></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="index.jsp">Quiénes somos</a>
                    </li>                 
                          <%
                           	for(ViewHead Vwhead: listViewHead){		
                           %>
		                     <li class="nav-item">
		                        <a class="nav-link" href="<%=Vwhead.getEnlace()%>"><%=Vwhead.getNombrehead()%></a>
		                    </li>
                           <% 
                             }
                            %>
                     <li class="nav-item">
                        <a class="nav-link" href="Contacto.jsp">Contactanos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp"><i class="fas fa-user"></i></a>
                    </li>
                </ul>
               <!--  <form class="d-flex">
                    <input class="form-control me-2 d-none" id="inputSearch1" type="search" placeholder="Buscar"
                        aria-label="Search">
                    <button class="btn btn-outline-light bg-lightº" id="btnSearch" type="submit"><i
                        class="fas fa-search"></i></button>
                </form>
                 -->
            </div>
        </div>
    </nav>
    <!-- Termina Menu -->