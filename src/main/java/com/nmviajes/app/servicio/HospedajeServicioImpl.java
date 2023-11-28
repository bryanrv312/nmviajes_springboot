package com.nmviajes.app.servicio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.Hospedaje;
import com.nmviajes.app.entidad.Role;
import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.entidad.Vuelo;
import com.nmviajes.app.modelo.HospedajeDTO;
import com.nmviajes.app.repositorio.HospedajeRepo;
  
@Service
public class HospedajeServicioImpl {
    @Autowired
    private HospedajeRepo hospedajeRepo;

    public Hospedaje guardar(HospedajeDTO registroDTO) {
		//public HospedajeDTO(Long id, String nombre, String ubicacion, Double precio) {
			Hospedaje hospedaje = new Hospedaje(registroDTO.getNombre(), registroDTO.getUbicacion(),
				registroDTO.getPrecio(), registroDTO.getHabitaciones(), registroDTO.getPersonas());
		return hospedajeRepo.save(hospedaje);
	}

	public Hospedaje guardarEditado(HospedajeDTO registroDTO) {
		//public HospedajeDTO(Long id, String nombre, String ubicacion, Double precio) {
			Hospedaje usuario = new Hospedaje(registroDTO.getId(),registroDTO.getNombre(), registroDTO.getUbicacion(),
				registroDTO.getPrecio(), registroDTO.getHabitaciones(), registroDTO.getPersonas());
		return hospedajeRepo.save(usuario);
	}
	
	public List<Hospedaje> listarHospedaje() {
		return hospedajeRepo.findAll();
	}

	public void eliminarHospedaje(Long id){
		hospedajeRepo.deleteById(id);
	}

	
	public Hospedaje buscarUsuarioPorId(Long id) {
		Hospedaje u = hospedajeRepo.findById(id).get();
		return u;
	}
	
	public List<Hospedaje> buscarHotelesPorUbicacion(String ciudad) {
	    return hospedajeRepo.findByUbicacionContaining(ciudad);
	}
	
	public Hospedaje guardarHospedaje(Hospedaje hos) {
		return hospedajeRepo.save(hos);
	}
	
	
	//EXPORTAR EXCEL
	public ByteArrayInputStream exportAllHospedajes() throws Exception {
			
			String[] columns = {"id", "Nombre", "Dirección", "Habitaciones", "Personas", "Precio"};
			
			//creando nuevo libro
			Workbook workbook = new HSSFWorkbook();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			
			
			Sheet sheet = workbook.createSheet("Hoteles");//creamos una nueva hoja llamada Clientes
			
			Row row_titulo = sheet.createRow(0);
			Cell cell_titulo = row_titulo.createCell(0);
			cell_titulo.setCellValue("REPORTE DE HOSPEDAJES");
			
			Row row_user = sheet.createRow(1);
			Cell cell_user = row_user.createCell(0);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			cell_user.setCellValue("Descargado por: " + username);
			
			Row row_fecha_hora = sheet.createRow(2);
			Cell cell_fecha_hora = row_fecha_hora.createCell(0);
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		    String formattedDateTime = now.format(formatter);
		    cell_fecha_hora.setCellValue("Fecha y hora: " + formattedDateTime);
		    
		 // Crear un estilo de celda y establecer negrita
		    CellStyle style = workbook.createCellStyle();
		    Font font = workbook.createFont();
		    font.setBold(true);
		    font.setFontHeightInPoints((short) 14); // Tamaño de la fuente
		    style.setFont(font);
		    style.setAlignment(HorizontalAlignment.CENTER);
		    style.setVerticalAlignment(VerticalAlignment.CENTER);

		    // Aplicar el estilo
		    cell_fecha_hora.setCellStyle(style);
		    cell_user.setCellStyle(style);
		    cell_titulo.setCellStyle(style);
		    
		    // Combinar las celdas
		    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.length - 1));
		    sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columns.length - 1));
		    sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, columns.length - 1));
		    sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, columns.length - 1));
			
			
			Row row = sheet.createRow(4);//creamos registro en posicion 0
			//creando celdas para encabezado
			for(int i=0; i<columns.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(columns[i]);
				// Ajustar el ancho de la columna automáticamente
			    sheet.autoSizeColumn(i);
			}
			
			List<Hospedaje> lista_hospedaje = hospedajeRepo.findAll();
			int initRow = 5;//fila inicial
			
			for (Hospedaje hos : lista_hospedaje) {
				row = sheet.createRow(initRow);
				row.createCell(0).setCellValue(hos.getId());
				row.createCell(1).setCellValue(hos.getNombre());
				row.createCell(2).setCellValue(hos.getUbicacion());
				row.createCell(3).setCellValue(hos.getHabitaciones());
				row.createCell(4).setCellValue(hos.getPersonas());
				row.createCell(5).setCellValue(hos.getPrecio());
				
				// Ajustar el ancho de la columna automáticamente para todas las celdas de esta fila
			    for (int i = 0; i < columns.length; i++) {
			        sheet.autoSizeColumn(i);
			    }
				initRow++;
			}
			
			workbook.write(stream);
			workbook.close();
			//obtener el outputstream y transformarlo en inputstream
			return new ByteArrayInputStream(stream.toByteArray());
		}
    
}
