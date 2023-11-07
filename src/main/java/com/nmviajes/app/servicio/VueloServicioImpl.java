package com.nmviajes.app.servicio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Example;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
  			
		String[] columns = {"id", "Origen", "Destino", "Aerolinea", "Fecha partida", "Fecha regreso", "Pasajeros", "Precio"};
		
		//creando nuevo libro
		Workbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		
		Sheet sheet = workbook.createSheet("Vuelos");//creamos una nueva hoja llamada Clientes
		Row row = sheet.createRow(0);//creamos registro en posicion 0
		
		//creando celdas para encabezado
		for(int i=0; i<columns.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columns[i]);
			// Ajustar el ancho de la columna automáticamente
		    sheet.autoSizeColumn(i);
		}
		
		List<Vuelo> lista_vuelo = vueloRepo.findAll();
		int initRow = 1;//fila inicial
		
		for (Vuelo vu : lista_vuelo) {
			row = sheet.createRow(initRow);
			row.createCell(0).setCellValue(vu.getId());
			row.createCell(1).setCellValue(vu.getOrigen());
			row.createCell(2).setCellValue(vu.getDestino());
			row.createCell(3).setCellValue(vu.getAerolinea());
			
			Cell fechaPartidaCell = row.createCell(4);
			fechaPartidaCell.setCellValue(vu.getFechaPartida().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			
			Cell fechaRegresoCell = row.createCell(5);
		    fechaRegresoCell.setCellValue(vu.getFechaRegreso().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		    
			row.createCell(6).setCellValue(vu.getPasajeros());
			row.createCell(7).setCellValue(vu.getPrecio());
			
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
