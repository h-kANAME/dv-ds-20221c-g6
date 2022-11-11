package ar.edu.davinci.dvds20221cg6.controller.view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.edu.davinci.dvds20221cg6.Constantes;
import ar.edu.davinci.dvds20221cg6.controller.TiendaApp;
import ar.edu.davinci.dvds20221cg6.domain.Negocio;
import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;
import ar.edu.davinci.dvds20221cg6.service.NegocioService;

@Controller
public class NegocioController extends TiendaApp{
	private final Logger LOGGER = LoggerFactory.getLogger(NegocioController.class);

	@Autowired
	private NegocioService negocioService;
	
	@GetMapping(path = "/negocios/list")
	public String showNegocioPage(Model model) {
		Pageable pageable = PageRequest.of(0, 20);
		Page<Negocio> negocios = negocioService.list(pageable);
		model.addAttribute("listNegocios", negocios);
		
		return "negocios/list_negocios";
	}
	
	@GetMapping(path = "/negocios/profit/{id}")
	public String showProfitNegocioPage(Model model, @PathVariable(name = "id") Long negocioId){
		Negocio negocio = null;
		String date = null;
		try {
			negocio = negocioService.findById(negocioId);
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("negocio", negocio);
		model.addAttribute("fecha", date);
		return "negocios/profit_negocios";
	}
	
	@PostMapping(path = "/negocios/profit/{id}")
	public String showProfitDateNegocioPage(Model model, @ModelAttribute("fecha") String date,@PathVariable(name = "id") Long negocioId) throws ParseException{
		Negocio negocio = null;
		try {
			
			negocio = negocioService.findById(negocioId);
			Date formattedFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA).parse(date);
			List<Venta> ventas = negocio.getVentas().stream().filter(v -> v.esDeFecha(formattedFecha)).collect(Collectors.toList());
			negocio.setVentas(ventas);
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("negocio", negocio);
		model.addAttribute("fecha", date);
		return "negocios/profit_negocios";
	}
	
	@PostMapping(value = "/negocios/save")
	public String saveNegocio(@ModelAttribute("negocio") Negocio negocio) {
		try {
			if (negocio.getId() == null) {
				negocio.setName("ngo_test_view");
				negocioService.save(negocio);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/tienda/negocios/list";
	}
}
