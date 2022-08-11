package ar.edu.davinci.dvds20221cg6.controller.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import org.springframework.web.servlet.ModelAndView;

import ar.edu.davinci.dvds20221cg6.Constantes;
import ar.edu.davinci.dvds20221cg6.controller.TiendaApp;
import ar.edu.davinci.dvds20221cg6.controller.response.VentaEfectivoResponse;
import ar.edu.davinci.dvds20221cg6.controller.response.VentaResponse;
import ar.edu.davinci.dvds20221cg6.controller.response.VentaTarjetaResponse;
import ar.edu.davinci.dvds20221cg6.controller.view.request.VentaEfectivoCreateRequest;
import ar.edu.davinci.dvds20221cg6.controller.view.request.VentaItemCreateRequest;
import ar.edu.davinci.dvds20221cg6.controller.view.request.VentaTarjetaCreateRequest;
import ar.edu.davinci.dvds20221cg6.domain.Item;
import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.domain.VentaEfectivo;
import ar.edu.davinci.dvds20221cg6.domain.VentaTarjeta;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;
import ar.edu.davinci.dvds20221cg6.service.ItemService;
import ar.edu.davinci.dvds20221cg6.service.VentaService;
import ma.glasnost.orika.MapperFacade;


@Controller
public class VentaController extends TiendaApp {
	private final Logger LOGGER = LoggerFactory.getLogger(VentaController.class);

	
	@Autowired
	private VentaService ventaService;
	
	@Autowired
	private ItemService itemService;
    
    @Autowired
    private MapperFacade mapper;
    
	@GetMapping(path = "ventas/list")
	public String showVentaPage(Model model) {
		LOGGER.info("GET - showVentaPage  - /ventas/list");
		
		Pageable pageable = PageRequest.of(0, 20);
		Page<Venta> ventas = ventaService.list(pageable);
		LOGGER.info("GET - showVentaPage venta importe final: " + ventas.getContent().toString());
		
		model.addAttribute("listVentas", ventas);

		LOGGER.info("ventas.size: " + ventas.getNumberOfElements());
		return "ventas/list_ventas";
	}

		
	@GetMapping(path = "/ventas/efectivo/new")
	public String showNewVentaEfectivoPage(Model model) {
		LOGGER.info("GET - showNewVentaPage - /ventas/efectivo/new");
		
        VentaEfectivoCreateRequest venta = new VentaEfectivoCreateRequest();
        Calendar calendar = Calendar.getInstance();
        Date toDay = calendar.getTime();
        
		DateFormat formatearFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA);
        String fecha = formatearFecha.format(toDay);
		venta.setFecha(fecha);
		
        model.addAttribute("venta", venta);

        LOGGER.info("ventas: " + venta.toString());

		return "ventas/new_ventas_efectivo";
	}
	
	@GetMapping(path = "/ventas/tarjeta/new")
	public String showNewVentaTarjetaPage(Model model) {
		LOGGER.info("GET - showNewVentaPage - /ventas/tarjeta/new");
		VentaTarjetaCreateRequest venta = new VentaTarjetaCreateRequest();
        Calendar calendar = Calendar.getInstance();
        Date toDay = calendar.getTime();
        
		DateFormat formatearFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA);
        String fecha = formatearFecha.format(toDay);
		venta.setFecha(fecha);
		
        model.addAttribute("venta", venta);

        LOGGER.info("ventas: " + venta.toString());

		return "ventas/new_ventas_tarjeta";
	}
	
	@GetMapping(path = "/ventas/{ventaId}/item/new")
	public String showNewItemPage(Model model, @PathVariable(name = "ventaId") Long ventaId) {
		LOGGER.info("GET - showNewItemPage - /venta/"+ventaId+"/item/new");
		VentaItemCreateRequest item = new VentaItemCreateRequest();
        
		item.setVentaId(ventaId);
		
		
        model.addAttribute("item", item);

        LOGGER.info("item: " + item.toString());

		return "ventas/new_ventas_item";
	}

	
	
	@PostMapping(value = "/ventas/efectivo/save")
	public String saveVentaEfectivo(@ModelAttribute("venta") VentaEfectivoCreateRequest datosVenta) {
		LOGGER.info("POST - saveVenta - /ventas/efectivo/save");
		LOGGER.info("datosVenta: " + datosVenta.toString());
        VentaEfectivo venta = mapper.map(datosVenta, VentaEfectivo.class);
        // Grabar el nuevo Venta
        try {
            venta = ventaService.save(venta);
        } catch (Exception e) {
            e.printStackTrace();
        }

		return "redirect:/tienda/ventas/list";
	}
	
	@PostMapping(value = "/ventas/tarjeta/save")
	public String saveVentaTarjeta(@ModelAttribute("venta") VentaTarjetaCreateRequest datosVenta) {
		LOGGER.info("POST - saveVenta - /ventas/tarjeta/save");
		LOGGER.info("venta: " + datosVenta.toString());

        VentaTarjeta venta = mapper.map(datosVenta, VentaTarjeta.class);

        // Grabar el nuevo Venta
        try {
            venta = ventaService.save(venta);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return "redirect:/tienda/ventas/list";
	}
	
	@RequestMapping(value = "/ventas/edit/{id}", method = RequestMethod.GET)
	public ModelAndView showEditVentaPage(@PathVariable(name = "id") Long ventaId) {
		LOGGER.info("GET - showEditVentaPage - /ventas/edit/{id}");
		LOGGER.info("venta: " + ventaId);

		ModelAndView mav = new ModelAndView("ventas/edit_ventas");
		try {
			
			Venta venta = ventaService.findById(ventaId);
			mav.addObject("venta", venta);
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mav;
	}
	
	@PostMapping(value = "/ventas/item/save")
	public String saveVentaItem(@ModelAttribute("item") VentaItemCreateRequest datosVentaItem) {
		LOGGER.info("POST - saveVentaItem - ventas/item/save");
		LOGGER.info("datosVentaItem: " + datosVentaItem.toString());

        Item item = mapper.map(datosVentaItem, Item.class);

        // Grabar el nuevo Venta
        Venta venta = null;
        try {
            venta = ventaService.addItem(datosVentaItem.getVentaId(), item);
        } catch (Exception e) {
            e.printStackTrace();
        }
		LOGGER.info("Venta Grabada: " + venta.toString());
		LOGGER.info("redirect:/ventas/edit/"+datosVentaItem.getVentaId().toString());

		return "redirect:/tienda/ventas/show/"+datosVentaItem.getVentaId().toString();
	}
	
	
	@GetMapping(path = "/ventas/show/{id}")
	public ModelAndView showVentaPage(@PathVariable(name = "id") Long ventaId) {
		LOGGER.info("GET - showVentaPage  - /tienda/ventas/show/{id}");
		LOGGER.info("venta: " + ventaId);

		ModelAndView mav = new ModelAndView("ventas/show_ventas");

		
		Venta venta = null;
		try {
			venta = ventaService.findById(ventaId);
			mav.addObject("venta", venta);
		} catch (BusinessException e1) {
            LOGGER.error(e1.getMessage());
			e1.printStackTrace();
		}

        List<VentaResponse> ventasToResponse = new ArrayList<VentaResponse>();
        
        	// Convertir Venta en VentaResponse
            try {
                if (venta instanceof VentaEfectivo) {
                    VentaEfectivoResponse ventaResponse = mapper.map((VentaEfectivo) venta, VentaEfectivoResponse.class);
                    ventasToResponse.add(ventaResponse);
                } else if (venta instanceof VentaTarjeta) {
                	VentaTarjetaResponse ventaResponse = mapper.map((VentaTarjeta) venta, VentaTarjetaResponse.class);
                    ventasToResponse.add(ventaResponse);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        

            mav.addObject("listVentas", ventasToResponse);

		
		return mav;
	}

	@RequestMapping(value = "/ventas/delete/{id}", method = RequestMethod.GET)
	public String deleteVenta(@PathVariable(name = "id") Long ventaId) {
		LOGGER.info("GET - deleteVenta - /ventas/delete/{id}");
		LOGGER.info("venta: " + ventaId);
		ventaService.delete(ventaId);
		return "redirect:/tienda/ventas/list";
	}
	
	@RequestMapping(value = "/item/delete/{id}", method = RequestMethod.GET)
	public String deleteItem(@PathVariable(name= "id")Long itemId){
		LOGGER.info("Get - deleteItem - /item/delete/{id}");
		LOGGER.info("item: " + itemId);
		itemService.delete(itemId);
		return "redirect:/tienda/ventas/list";
	}
	
}

