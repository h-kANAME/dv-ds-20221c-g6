package ar.edu.davinci.dvds20221cg6.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.davinci.dvds20221cg6.domain.Item;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;
import ar.edu.davinci.dvds20221cg6.repository.ItemRepository;


@Service
public class ItemServiceImpl implements ItemService {

	private final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

	private final ItemRepository repository;
	
	@Autowired
	public ItemServiceImpl (final ItemRepository repository) {
		this.repository = repository;
	}

	@Override
	public Item save(Item item) throws BusinessException {
		LOGGER.debug("Grabamos la item con el id: " + item.getId());
		
		if (item.getId() == null) {
			return repository.save(item);
		}
		throw new BusinessException("No se puede crear una item con un id específico");
	}

	@Override
	public Item update(Item item) throws BusinessException {
		LOGGER.debug("Modificamos la item con el id: " + item.getId());
		
		if (item.getId() != null) {
			return repository.save(item);
		}
		throw new BusinessException("No se puede modificar una item no creada");		
	
	}

	@Override
	public void delete(Item item) {
		LOGGER.debug("Borrando la item con el id: " + item.getId());
		
		repository.delete(item);		
	}

	@Override
	public void delete(Long id) {
		LOGGER.debug("Borrando la item con el id: " + id);
		
		repository.deleteById(id);		
	}

	@Override
	public Item findById(Long id) throws BusinessException {
		LOGGER.debug("Busqueda de una item por ID");
		
		Optional<Item> itemOptional = repository.findById(id);
		if (itemOptional.isPresent()) {
			return itemOptional.get();
		}
		
		throw new BusinessException("No se encotró la item por el id: " + id);
	}

	@Override
	public List<Item> listAll() {
		LOGGER.debug("Listado de todas las items");
		
		return repository.findAll();
	}

	@Override
	public Page<Item> list(Pageable pageable) {
		LOGGER.debug("Listado de todas las items paginadas");
		LOGGER.debug("Pageable, offset:" + pageable.getOffset() + ", pageSize: " + pageable.getPageSize()+ ", pageNumber: " + pageable.getPageNumber());
		
		return repository.findAll(pageable);
	}

	@Override
	public long count() {
		return repository.count();
	}
	
}
