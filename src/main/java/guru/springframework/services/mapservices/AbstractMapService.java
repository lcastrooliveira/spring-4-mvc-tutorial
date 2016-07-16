package guru.springframework.services.mapservices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guru.springframework.domain.DomainObject;

public abstract class AbstractMapService<T extends DomainObject> {

	
	protected Map<Integer, T> objectsBag;
	
	public AbstractMapService() {
		objectsBag = new HashMap<>();
	}
	
	public List<T> listAllObjects() {
		return new ArrayList<T>(objectsBag.values());
	}
	
	public T getObjectById(Integer id) {
		return objectsBag.get(id);
	}
	
	public T saveOrUpdateObject(T object) {
		if(object != null) {
			if(object.getId() != null) {
				object.setId(getNextKey());
			}
			objectsBag.put(object.getId(), object);
			return object;
		}
		else throw new RuntimeException("Object can't be null");
	}
	
	public void deleteObject(Integer id) {
        objectsBag.remove(id);
    }

    private Integer getNextKey(){
        return Collections.max(objectsBag.keySet()) + 1;
    }
	
}
