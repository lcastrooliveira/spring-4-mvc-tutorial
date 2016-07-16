package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.User;
import guru.springframework.services.interfaces.UserService;
import guru.springframework.services.security.EncryptionService;

@Service
@Profile("jpadao")
public class UserServiceJpaDaoImpl implements UserService {

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Autowired
	private EncryptionService encryptionService;
	
	@Override
	public List<User> listAll() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("from User",User.class).getResultList();
	}

	@Override
	public User findById(Integer id) {
		EntityManager em = emf.createEntityManager();
		return em.find(User.class, id);
	}

	@Override
	public User saveOrUpdate(User element) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(element.getPassword() != null) {
			element.setEncryptedPassword(encryptionService.encryptString(element.getPassword()));
		}
		User savedUser = em.merge(element);
		em.getTransaction().commit();
		return savedUser;
	}

	@Override
	public void delete(Integer id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(User.class, id));
		em.getTransaction().commit();
	}
	
}
