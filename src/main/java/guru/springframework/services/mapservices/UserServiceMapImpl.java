package guru.springframework.services.mapservices;

import java.util.List;

import guru.springframework.domain.User;
import guru.springframework.services.interfaces.UserService;

public class UserServiceMapImpl extends AbstractMapService<User> implements UserService {

	@Override
	public List<User> listAll() {
		return super.listAllObjects();
	}	

	@Override
	public User findById(Integer id) {
		return super.getObjectById(id);
	}

	@Override
	public User saveOrUpdate(User domainObject) {
		return (User) super.saveOrUpdateObject(domainObject);
	}

	@Override
	public void delete(Integer id) {
		super.deleteObject(id);
	}

	

	

}
