package guru.springframework.services.interfaces;

import java.util.List;

import guru.springframework.domain.DomainObject;

public interface SimpleBeanCrudService<T extends DomainObject,K> {

	public List<T> listAll();
	
	public T findById(K id);
	
	public T saveOrUpdate(T element);
	
	public void delete(K id);
	
}
