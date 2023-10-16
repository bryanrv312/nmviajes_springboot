package com.nmviajes.app.servicio.utils;


import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;



import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class GenerarReportes {
	
	 public void generarYDescargarInforme(HttpServletResponse response, Map<String, Object> params, JRBeanCollectionDataSource dataSource,String nombreArchivo) {
		 try {
			    // Cargar el archivo .jasper (compilado) del informe Jasper
			    InputStream jasperStream = this.getClass().getResourceAsStream("/report1.jasper");

			    // Llenar el informe con datos y parámetros
			    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, params, dataSource);

			    // Configurar la respuesta HTTP para descargar el PDF
			    response.setContentType("application/pdf");
			    response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo);

			    // Exportar el informe Jasper a PDF y escribirlo en la respuesta
			    JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			} catch (Exception e) {
			    // Manejar cualquier excepción que pueda ocurrir durante la generación del informe
			    e.printStackTrace(); // Puedes cambiar esto para registrar adecuadamente las excepciones

			    // Envía una respuesta de error al usuario
			    response.setContentType("text/plain");
			    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Código de error 500
			    //response.getWriter().write("Ocurrió un error al generar el informe.");
			}


	    }
	 
	 
	 public byte[] generarYenviarReporte(Map<String, Object> params, JRBeanCollectionDataSource dataSource, String nombreArchivo) {
		 try {
			 
             // Cargar el archivo .jasper (compilado) del informe Jasper
             InputStream jasperStream = this.getClass().getResourceAsStream("/report1.jasper");
             // Llenar el informe con datos y parámetros
             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, params, dataSource);
             
             byte[] pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);
             
             return pdfReport;
	            
		 }catch(Exception e){
			 e.printStackTrace();
			 return null;
		 } 
	 }

}
