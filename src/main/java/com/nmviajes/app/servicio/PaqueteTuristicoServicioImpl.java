package com.nmviajes.app.servicio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
  			
		String[] columns = {"id", "Aerolinea", "Origen", "Destino", "Fecha", "Hotel", "Direccion", "Personas", "Habitaciones", "Precio"};
		
		//creando nuevo libro
		Workbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		
		Sheet sheet = workbook.createSheet("Paquetes");//creamos una nueva hoja llamada Clientes
		Row row = sheet.createRow(0);//creamos registro en posicion 0
		
		//creando celdas para encabezado
		for(int i=0; i<columns.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columns[i]);
			// Ajustar el ancho de la columna automáticamente
		    sheet.autoSizeColumn(i);
		}
		
		List<PaqueteTuristico> lista_paquete = paqueteTuristicoRepo.findAll();
		int initRow = 1;//fila inicial
		
		for (PaqueteTuristico pq : lista_paquete) {
			row = sheet.createRow(initRow);
			row.createCell(0).setCellValue(pq.getId());
			row.createCell(1).setCellValue(pq.getVuelo().getAerolinea());
			row.createCell(2).setCellValue(pq.getVuelo().getOrigen());
			row.createCell(3).setCellValue(pq.getVuelo().getDestino());
			
			
			row.createCell(4).setCellValue(pq.getVuelo().getFechaPartida().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			
			row.createCell(5).setCellValue(pq.getHospedaje().getNombre());
			row.createCell(6).setCellValue(pq.getHospedaje().getUbicacion());
			row.createCell(7).setCellValue(pq.getPasajeros());
			row.createCell(8).setCellValue(pq.getHabitaciones());
			row.createCell(9).setCellValue(pq.getPrecio());
			
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
