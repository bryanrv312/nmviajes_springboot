package com.nmviajes.app.servicio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.Vuelo;
import com.nmviajes.app.modelo.VueloDTO;
import com.nmviajes.app.repositorio.VueloRepo;
  

@Service
public class VueloServicioImpl {
    @Autowired
    private VueloRepo vueloRepo;

    public Vuelo guardar(VueloDTO registroDTO) {
		// public Vuelo(Long id, String origen, String destino, String aerolinea, Integer pasajeros, Date fecha,
         //   Double precio) {
			Vuelo usuario = new Vuelo(registroDTO.getOrigen(), registroDTO.getDestino(),
			registroDTO.getAerolinea(),registroDTO.getPasajeros(),registroDTO.getFecha(),registroDTO.getPrecio());
		return vueloRepo.save(usuario);
	}

	public Vuelo guardarEditado(VueloDTO registroDTO) {
		// public Vuelo(Long id, String origen, String destino, String aerolinea, Integer pasajeros, Date fecha,
		//Double precio) {
			Vuelo usuario = new Vuelo(registroDTO.getId(),registroDTO.getOrigen(), registroDTO.getDestino(),
			registroDTO.getAerolinea(),registroDTO.getPasajeros(),registroDTO.getFecha(),registroDTO.getPrecio());
		return vueloRepo.save(usuario);
	}

	public List<Vuelo> listarVuelo() {
		return vueloRepo.findAll();
	}

	public Vuelo buscarUsuarioPorId(Long id) {
		Vuelo u = vueloRepo.findById(id).get();
		return u;
	}
	public void eliminarVuelo(Long id){
		vueloRepo.deleteById(id);
	}
	
	public List<Vuelo> buscarByExample(Example<Vuelo> example){
		return vueloRepo.findAll(example);
	}
	
	public Vuelo guardarVuelo(Vuelo vu) {
		return vueloRepo.save(vu);
	}
	
	public Vuelo guardarEditado_2(Vuelo vuelo) {
		return vueloRepo.save(vuelo);
	}
	
	
	//EXPORTAR EXCEL
  	public ByteArrayInputStream exportAllVuelos() throws Exception {
  			
		String[] columns = {"id", "Origen", "Destino", "Aerolinea", "Fecha_hora_salida(ida)", "Fecha_hora_llegada(ida)", 
				"Fecha_hora_salida(regreso)", "Fecha_hora_llegada(regreso)", "Pasajeros", "Precio"};
		
		//creando nuevo libro
		Workbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		
		Sheet sheet = workbook.createSheet("Vuelos");//creamos una nueva hoja llamada Clientes
		
		Row row_titulo = sheet.createRow(0);
		Cell cell_titulo = row_titulo.createCell(0);
		cell_titulo.setCellValue("REPORTE DE VUELOS");
		
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
	    
	    
	    CellStyle style_columns = workbook.createCellStyle();
	    Font font_columns = workbook.createFont();
	    font_columns.setBold(true);
	    
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
		
		
		Row row = sheet.createRow(4);//creamos registro en posicion 4
		//creando celdas para encabezado
		for(int i=0; i<columns.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(style_columns);
			cell.setCellValue(columns[i]);
			// Ajustar el ancho de la columna automáticamente
		    sheet.autoSizeColumn(i);
		}
		
		List<Vuelo> lista_vuelo = vueloRepo.findAll();
		int initRow = 5;//fila inicial
		
		for (Vuelo vu : lista_vuelo) {
			row = sheet.createRow(initRow);
			row.createCell(0).setCellValue(vu.getId());
			row.createCell(1).setCellValue(vu.getOrigen());
			row.createCell(2).setCellValue(vu.getDestino());
			row.createCell(3).setCellValue(vu.getAerolinea());
			
			Cell fechaHoraSalida_Ida_Cell = row.createCell(4);
			fechaHoraSalida_Ida_Cell.setCellValue(vu.getFechaPartida().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "(" +
									      vu.getHoraPartida().format(DateTimeFormatter.ofPattern("HH:mm"))+ ")");
			
			Cell fechaHoraLlegada_Ida_Cell = row.createCell(5);
			fechaHoraLlegada_Ida_Cell.setCellValue( vu.getFechaPartida2().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "(" +
										  vu.getHoraPartida2().format(DateTimeFormatter.ofPattern("HH:mm"))+ ")");
			
			Cell fechaHoraSalida_Regreso_Cell = row.createCell(6);
			fechaHoraSalida_Regreso_Cell.setCellValue(vu.getFechaRegreso().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "(" +
									      vu.getHoraRegreso().format(DateTimeFormatter.ofPattern("HH:mm"))+ ")");
			
			Cell fechaHoraLlegada_Regreso_Cell = row.createCell(7);
			fechaHoraLlegada_Regreso_Cell.setCellValue(vu.getFechaRegreso2().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "(" +
									      vu.getHoraRegreso2().format(DateTimeFormatter.ofPattern("HH:mm"))+ ")");
					    
			row.createCell(8).setCellValue(vu.getPasajeros());
			row.createCell(9).setCellValue(vu.getPrecio());
			
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
