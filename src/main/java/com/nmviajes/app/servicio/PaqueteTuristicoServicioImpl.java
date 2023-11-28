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
import org.springframework.data.domain.Example;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.Hospedaje;
import com.nmviajes.app.entidad.PaqueteTuristico;
import com.nmviajes.app.entidad.Usuario;
import com.nmviajes.app.entidad.Vuelo;
import com.nmviajes.app.modelo.HospedajeDTO;
import com.nmviajes.app.modelo.PaqueteTuristicoDTO;
import com.nmviajes.app.repositorio.PaqueteTuristicoRepo;

@Service
public class PaqueteTuristicoServicioImpl {
	
    @Autowired
    private PaqueteTuristicoRepo paqueteTuristicoRepo;
    

    public PaqueteTuristico save(PaqueteTuristicoDTO registroDTO) {
		//Long id, String lugarTuristico, Integer estado, Integer detalles, Usuario usuario
		PaqueteTuristico usuario = new PaqueteTuristico(registroDTO.getLugarTuristico(), registroDTO.getEstado(),
				registroDTO.getDetalles());
		return paqueteTuristicoRepo.save(usuario);
	}
    

	public List<PaqueteTuristico> listarPaqueteTuristicos() {
		return paqueteTuristicoRepo.findAll();
	}
	
	
	public PaqueteTuristico buscarPaqueteTuristicoPorId(Long id) {
		PaqueteTuristico pt = paqueteTuristicoRepo.findById(id).get();
		return pt;
	}
	
	public List<PaqueteTuristico> buscarByExample_pq(Example<PaqueteTuristico> example){
		return paqueteTuristicoRepo.findAll(example);
	}
	
	public List<PaqueteTuristico> buscarPorOrigen(String origen) {
		return paqueteTuristicoRepo.findByVueloOrigen(origen);
    }
	
	public List<PaqueteTuristico> buscarPorDestino(String destino) {
        return paqueteTuristicoRepo.findByVueloDestino(destino);
    }

    public List<PaqueteTuristico> buscarPorOrigenYDestino(String origen, String destino) {
        return paqueteTuristicoRepo.findByVueloOrigenAndVueloDestino(origen, destino);
    }
    
    public PaqueteTuristico guardar(PaqueteTuristico paquete) {
		return paqueteTuristicoRepo.save(paquete);
	}
    
    public PaqueteTuristico guardarEditado(PaqueteTuristico paquete) {
		return paqueteTuristicoRepo.save(paquete);
	}
    
    public void eliminarPaquete(Long id){
    	paqueteTuristicoRepo.deleteById(id);
	}
    
  //EXPORTAR EXCEL
  	public ByteArrayInputStream exportAllPaquetes() throws Exception {
  			
		String[] columns = {"id", "Aerolinea", "Origen", "Destino", "Fecha_hora_salida(ida)", "Fecha_hora_llegada(ida)", 
				"Fecha_hora_salida(regreso)", "Fecha_hora_llegada(regreso)", "Hotel", "Direccion", "Personas", 
				"Habitaciones", "Precio"};
		
		//creando nuevo libro
		Workbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		
		Sheet sheet = workbook.createSheet("Paquetes");//creamos una nueva hoja llamada Clientes
		
		Row row_titulo = sheet.createRow(0);
		Cell cell_titulo = row_titulo.createCell(0);
		cell_titulo.setCellValue("REPORTE DE PAQUETES TURÍSTICOSS");
		
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
		
		
		Row row = sheet.createRow(4);//creamos registro en posicion 4
		//creando celdas para encabezado
		for(int i=0; i<columns.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columns[i]);
			// Ajustar el ancho de la columna automáticamente
		    sheet.autoSizeColumn(i);
		}
		
		List<PaqueteTuristico> lista_paquete = paqueteTuristicoRepo.findAll();
		int initRow = 5;//fila inicial
		
		for (PaqueteTuristico pq : lista_paquete) {
			row = sheet.createRow(initRow);
			row.createCell(0).setCellValue(pq.getId());
			row.createCell(1).setCellValue(pq.getVuelo().getAerolinea());
			row.createCell(2).setCellValue(pq.getVuelo().getOrigen());
			row.createCell(3).setCellValue(pq.getVuelo().getDestino());
			
			
			row.createCell(4).setCellValue(pq.getVuelo().getFechaPartida().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "(" +
					pq.getVuelo().getHoraPartida().format(DateTimeFormatter.ofPattern("HH:mm"))+ ")");
			
			row.createCell(5).setCellValue(pq.getVuelo().getFechaPartida2().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "(" +
					pq.getVuelo().getHoraPartida2().format(DateTimeFormatter.ofPattern("HH:mm"))+ ")");
			
			row.createCell(6).setCellValue(pq.getVuelo().getFechaRegreso().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "(" +
					pq.getVuelo().getHoraRegreso().format(DateTimeFormatter.ofPattern("HH:mm"))+ ")");
			
			row.createCell(7).setCellValue(pq.getVuelo().getFechaRegreso2().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "(" +
					pq.getVuelo().getHoraRegreso2().format(DateTimeFormatter.ofPattern("HH:mm"))+ ")");
			
			row.createCell(8).setCellValue(pq.getHospedaje().getNombre());
			row.createCell(9).setCellValue(pq.getHospedaje().getUbicacion());
			row.createCell(10).setCellValue(pq.getPasajeros());
			row.createCell(11).setCellValue(pq.getHabitaciones());
			row.createCell(12).setCellValue(pq.getPrecio());
			
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
