package com.nmviajes.app;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nmviajes.app.entidad.Pago;
import com.nmviajes.app.servicio.PagoServicioImpl;


@Controller
//@RestController
//@RequestMapping("/paypal")
public class PaypalController {

	@Autowired
	private PaypalService servicePaypal;
	
	@Autowired
	private PagoServicioImpl servicePago;
	
	
	 private static final Logger logger = LoggerFactory.getLogger(PaypalController.class);
	
	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	@GetMapping("/formPaypal")
	public String home() {
		return "paypal/formPaypal";
	}
	
	@GetMapping("/success_msg")
	public String pagoCompletado() {
		System.out.println("Pago Completado");
		return "success";
	}
	
	/*
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@PostMapping("/success_1")
	public String obtenerDetallesTransaccion_1(@RequestBody Map<String, Object> transactionData, Model model) {
		
		String orderId = (String) transactionData.get("orderId");
        String payerName = (String) transactionData.get("payerName");
        String payerEmail = (String) transactionData.get("payerEmail");
        String purchaseAmount = (String) transactionData.get("purchaseAmount");
        String currencyCode = (String) transactionData.get("currencyCode");

        System.out.println("ID de la orden: " + orderId);
        System.out.println("Nombre del pagador: " + payerName);
        System.out.println("Email del pagador: " + payerEmail);
        System.out.println("Monto de compra: " + purchaseAmount + " " + currencyCode);
		
		Pago pago = new Pago();
		pago.setOrderId(orderId);
		pago.setPayerName(payerName);
		pago.setPayerEmail(payerEmail);
		pago.setPurchaseAmount(purchaseAmount);
		pago.setCurrencyCode(currencyCode);
		
		servicePago.save(pago);
		servicePago.guardarPagoEnMemoria(pago);//guardar pago en memoria
		
        return "index";
	}*/
	
	/*
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@PostMapping("/success_11")
	public String obtenerDetallesTransaccion_1(@RequestBody Map<String, Object> transactionData, Model model) {
		
		String orderId = (String) transactionData.get("orderId");
        String payerName = (String) transactionData.get("payerName");
        String payerEmail = (String) transactionData.get("payerEmail");
        String purchaseAmount = (String) transactionData.get("purchaseAmount");
        String currencyCode = (String) transactionData.get("currencyCode");
        
        System.out.println("--------------Mostrando Datos del Pago Paypal------------");
        System.out.println("ID de la orden: " + orderId);
        System.out.println("Nombre del pagador: " + payerName);
        System.out.println("Email del pagador: " + payerEmail);
        System.out.println("Monto de compra: " + purchaseAmount + " " + currencyCode);
		
		Pago pago = new Pago();
		pago.setOrderId(orderId);
		pago.setPayerName(payerName);
		pago.setPayerEmail(payerEmail);
		pago.setPurchaseAmount(purchaseAmount);
		pago.setCurrencyCode(currencyCode);
		
		servicePago.save(pago);
		servicePago.guardarPagoEnMemoria(pago);//guardar pago en memoria
		
        return "redirect:/cleanCart";
	}
	*/

	
}
