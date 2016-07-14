package guru.springframework.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Product;

@Service
@Profile("map")
public class ProductServiceImpl implements ProductService {

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	private Map<Integer, Product> products;

	public ProductServiceImpl() {
		loadAllProducts();
	}

	private void loadAllProducts() {
		products = new HashMap<>();

		EntityManager em = emf.createEntityManager();
		List<Product> loadedProducts = em.createQuery("from Product", Product.class).getResultList();
		for(Product product : loadedProducts) {
			products.put(product.getId(), product);
		}
	}

	@Override
	public List<Product> listAllProducts() {
		return new ArrayList<>(products.values());
	}

	@Override
	public Product getProductById(Integer id) {
		return products.get(id);
	}
	
	@Override
	public Product saveOrUpdate(Product product) {
		if(product != null) {
			if(product.getId() == null) {
				product.setId(getNextKey());
			}
			products.put(product.getId(), product);
			return product;
		} else {
			throw new RuntimeException("Product Can't be nill");
		}
	}
	
	private Integer getNextKey() {
		return Collections.max(products.keySet()) + 1;
	}

	@Override
	public void deleteProduct(Integer id) {
		products.remove(id);
	}
}
