package reportes;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datos.PoolConexion;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * Servlet implementation class Sl_RptArbol
 */
@WebServlet("/Sl_RptArbol")
public class Sl_RptArbol extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sl_RptArbol() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
		PoolConexion pc = PoolConexion.getInstance(); 
		Connection c = PoolConexion.getConnection();
		HashMap hm = new HashMap();
		OutputStream otps = response.getOutputStream();
		ServletContext context = getServletContext();
		String path = context.getRealPath("/");
		System.out.println("Path: "+path);
		String template = "Reportes\\rptArbol.jasper";
		Exporter exporter = new JRPdfExporter();
		JasperPrint jasperPrint = JasperFillManager.fillReport(path+template, hm, c);
		response.setContentType("application/pdf");
//		response.setHeader("Content-Disposition", "inline; filename=\"RptRolUser_"+nima+".pdf\"");
		response.setHeader("Content-Disposition", "inline; filename=\"RptArbol.pdf");
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(otps));
		exporter.exportReport();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("REPORTE: ERROR AL GENERAR REPORTE " + e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
