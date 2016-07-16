package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Order;
import guru.springframework.services.interfaces.OrderService;

@Service
@Profile("jpadao")
public class OrderServiceJpaDaoImpl implements OrderService {

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Override
	public List<Order> listAll() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("from Orders", Order.class).getResultList();
	}

	@Override
	public Order findById(Integer id) {
		EntityManager em = emf.createEntityManager();
		return em.find(Order.class, id);
	}

	@Override
	public Order saveOrUpdate(Order element) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Order savedOrder = em.merge(element);
		em.getTransaction().commit();
		return savedOrder;
	}

	@Override
	public void delete(Integer id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(Order.class, id));
		em.getTransaction().commit();
	}

	
	
}
