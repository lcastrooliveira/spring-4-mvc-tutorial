package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.security.Role;
import guru.springframework.services.interfaces.RoleService;

@Service
@Profile("jpadao")
public class RoleServiceJpaDaoImpl implements RoleService {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Override
	public List<Role> listAll() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("from Role",Role.class).getResultList();
	}

	@Override
	public Role findById(Integer id) {
		EntityManager em = emf.createEntityManager();
		return em.find(Role.class, id);
	}

	@Override
	public Role saveOrUpdate(Role element) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Role savedRole = em.merge(element);
		em.getTransaction().commit();
		return savedRole;
	}

	@Override
	public void delete(Integer id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(Role.class, id));
		em.getTransaction().commit();
	}
	
}
