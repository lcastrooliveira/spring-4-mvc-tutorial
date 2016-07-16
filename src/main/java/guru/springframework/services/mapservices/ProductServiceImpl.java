package guru.springframework.services.mapservices;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Product;
import guru.springframework.services.interfaces.ProductService;

@Service
@Profile("map")
public class ProductServiceImpl extends AbstractMapService<Product> implements ProductService {

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	public ProductServiceImpl() {
		loadAllProducts();
	}

	private void loadAllProducts() {
		EntityManager em = emf.createEntityManager();
		List<Product> loadedProducts = em.createQuery("from Product", Product.class).getResultList();
		for(Product product : loadedProducts) {
			super.objectsBag.put(product.getId(), product);
		}
	}
		
	@Override
	public Product saveOrUpdate(Product product) {
		return super.saveOrUpdateObject(product);
	}

	@Override
	public List<Product> listAll() {
		return super.listAllObjects();
	}

	@Override
	public Product findById(Integer id) {
		return super.getObjectById(id);
	}

	@Override
	public void delete(Integer id) {
		super.deleteObject(id);
	}
}
