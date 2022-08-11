package ar.edu.davinci.dvds20221cg6.controller.view;

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
	public String showProfitNegocioPage(Model model, @PathVariable(name = "id") Long negocioId) {
		Negocio negocio = null;
		try {
			negocio = negocioService.findById(negocioId);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("negocio", negocio);
		return "negocios/profit_negocios";
	}
	
	@PostMapping(value = "/negocios/save")
	public String saveNegocio(@ModelAttribute("negocio") Negocio negocio) {
		try {
			if (negocio.getId() == null) {
				negocioService.save(negocio);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/tienda/negocios/list";
	}
}
