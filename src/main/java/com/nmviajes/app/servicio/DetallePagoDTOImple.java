package com.nmviajes.app.servicio;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.nmviajes.app.entidad.Orden;
import com.nmviajes.app.entidad.Usuario;

import com.nmviajes.app.modelo.DetallePagoDTO;
import com.nmviajes.app.repositorio.OrdenRepo;


import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;
import java.util.Date;
 
@Service
public class DetallePagoDTOImple implements DetallePagoServicio{

	@Autowired
	private OrdenRepo ordenRepo;
	
	private List<DetallePagoDTO> detalles = new ArrayList<>();
	DetallePagoDTO u;
	
	
	
	@Override
    public void setDetallePago(DetallePagoDTO registroDTO){	
			this.u=registroDTO;	
	}
	
	@Override
	public Orden saveOrden(DetallePagoDTO d, Usuario u){
		Orden o = new Orden(null, u.getNombre(),u.getApellido(), u.getEmail(),d.getNombre(), new Date(), d.getTotal());
		return ordenRepo.save(o);
	}
	
	

	@Override
	public void listar(int c){
		List<Orden> slist = listAll();
        slist.sort(Comparator.comparing(Orden::getMontoTotal).reversed());
       
	   for (int i = 0; i < 3; i++) {
		slist.set(i, slist.get(i));
		System.out.println(slist.set(i, slist.get(i)));
	  }
	 
	}

	public List<Orden>listarCinco(){
		List<Orden> slist = listAll();
        slist.sort(Comparator.comparing(Orden::getMontoTotal).reversed());
       List<Orden> n = new ArrayList<>();
       
	   for (int i = 0; i < 5; i++) {
		slist.set(i, slist.get(i));
		n.add(slist.set(i, slist.get(i)));
		System.out.println(slist.set(i, slist.get(i)));
	  }
	  
		return n;
	}

	/*@Override
	public Usuario save(UsuarioRegistroDTO registroDTO) {
		Usuario usuario = new Usuario(registroDTO.getNombre(), registroDTO.getApellido(),
				registroDTO.getEmail(),
				registroDTO.getPassword());
		return repo.save(usuario);

	}
	
	
	@Override
	public List<Usuario> listarUsuarios() {
		return repo.findAll();

	}

	*/
	@Override
	public DetallePagoDTO getUsuario(){
		return this.u;
	}

	@Override
	public List<Orden> listAll(){
		return ordenRepo.findAll();
	}
	
	
	public List<DetallePagoDTO> getDetalles() {
        return this.detalles;
    }
	public void agregarDetalle(List<DetallePagoDTO> detal) {
        detalles = detal;
    }

	
	
	
	@Override
	public ByteArrayInputStream exportMejores() throws Exception {
		
		String[] columns = {"id", "Nombre", "Apellido", "Correo", "Detalle", "Fecha", "Total"};
		
		//creando nuevo libro
		Workbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
			
		Sheet sheet = workbook.createSheet("MejoresVentas");//creamos una nueva hoja llamada Clientes
		Row row = sheet.createRow(0);//creamos registro en posicion 0
		
		//creando celdas para encabezado
		for(int i=0; i<columns.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columns[i]);
			// Ajustar el ancho de la columna automáticamente
		    sheet.autoSizeColumn(i);
		}
		
		List<Orden> lista_detallePago = listarCinco();
		int initRow = 1;//fila inicial
		
		for (Orden orden : lista_detallePago) {
			row = sheet.createRow(initRow);
			row.createCell(0).setCellValue(orden.getId());
			row.createCell(1).setCellValue(orden.getUsuario());
			row.createCell(2).setCellValue(orden.getApellido());
			row.createCell(3).setCellValue(orden.getCorreo());
			row.createCell(4).setCellValue(orden.getLugar());

			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			String fecha = formato.format(orden.getFecha());
			row.createCell(5).setCellValue(fecha);
			
			row.createCell(6).setCellValue(orden.getMontoTotal());
			
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

	@Override
	public ByteArrayInputStream exportAllVentas() throws Exception {
		
		String[] columns = {"id", "Nombre", "Apellido", "Correo", "Detalle", "Fecha", "Total"};
		
		//creando nuevo libro
		Workbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		Sheet sheet = workbook.createSheet("Ventas");//creamos una nueva hoja llamada Clientes
		Row row = sheet.createRow(0);//creamos registro en posicion 0
		
		//creando celdas para encabezado
		for(int i=0; i<columns.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columns[i]);
			// Ajustar el ancho de la columna automáticamente
		    sheet.autoSizeColumn(i);
		}
		
		List<Orden> lista_detallePago = ordenRepo.findAll();
		int initRow = 1;//fila inicial
		
		for (Orden orden : lista_detallePago) {
			row = sheet.createRow(initRow);
			row.createCell(0).setCellValue(orden.getId());
			row.createCell(1).setCellValue(orden.getUsuario());
			row.createCell(2).setCellValue(orden.getApellido());
			row.createCell(3).setCellValue(orden.getCorreo());
			row.createCell(4).setCellValue(orden.getLugar());
			
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			String fecha = formato.format(orden.getFecha());
			row.createCell(5).setCellValue(fecha);
			
			row.createCell(6).setCellValue(orden.getMontoTotal());
			
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
