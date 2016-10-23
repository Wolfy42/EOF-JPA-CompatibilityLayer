package er.extensions.jpa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

public class JPAEditingContext extends EOEditingContext {

	private EntityManagerFactory emFactory;
	private EntityManager em;
	private Map<JPAEntity, JPAEnterpriseObject<JPAEntity>> objectsInEc;
	private List<JPAEnterpriseObject<JPAEntity>> unsavedEnterpriseObjects;
    
    public JPAEditingContext(EntityManagerFactory emFactory) {
		this.emFactory = emFactory;
		this.em = emFactory.createEntityManager();
		
		objectsInEc = Collections.synchronizedMap(new IdentityHashMap<>());
		unsavedEnterpriseObjects = Collections.synchronizedList(new ArrayList<>());
		// TODO: EntityManager should be closed on deletion of EC
//		em.close();
	}
    
    public JPAEnterpriseObject<JPAEntity> getEnterpriseObject(JPAEntityDef entityDef, Object value) {
    	JPAEntity o = em.find(entityDef.getJpaEntity(), value);
    	if (!objectsInEc.containsKey(o)) {
    		try {
    			@SuppressWarnings("unchecked")
    			JPAEnterpriseObject<JPAEntity> newO = (JPAEnterpriseObject<JPAEntity>) entityDef.getEoEntity().newInstance();
    			newO.setEc(this);
    			newO.setEntity(o);
    			
    			objectsInEc.put(o, newO);
    		} catch (Exception e) {
    			throw new IllegalStateException("could not create entity", e);
    		}
    	}
    	return objectsInEc.get(o);
    }
    
	public <T> T getEnterpriseObject(JPAEntityDef entityDef, JPAEntity entity) {
    	if (entity == null) {
    		return null;
    	}
    	if (!objectsInEc.containsKey(entity)) {
    		try {
    			//TODO: search generic solution which does not require the unchecked cast
    			JPAEnterpriseObject<JPAEntity> newO = (JPAEnterpriseObject<JPAEntity>) entityDef.getEoEntity().newInstance();
    			newO.setEc(this);
    			newO.setEntity(entity);
    			
    			objectsInEc.put(entity, newO);
    		} catch (Exception e) {
    			throw new IllegalStateException("could not create entity", e);
    		}
    	}
    	return (T) objectsInEc.get(entity);
    }
    
    public <T> NSArray<T> getEnterpriseObjects(JPAEntityDef entityDef, List<? extends JPAEntity> entities) {
    	if (entities == null) {
    		return null;
    	}
    	NSArray<T> results = new NSMutableArray<>();
    	for (JPAEntity entity : entities) {
    		results.add(getEnterpriseObject(entityDef, entity));
    	}
    	return results;
    }
    
    public EntityManager getEm() {
		return em;
	}
    
	public void insertObject(EOEnterpriseObject eo) {
		@SuppressWarnings("unchecked")
		JPAEnterpriseObject<JPAEntity> jpaEo = (JPAEnterpriseObject<JPAEntity>)eo;
		jpaEo.setEc(this);
		jpaEo.createJPAEntity();
		
		em.persist(jpaEo.getEntity());
		objectsInEc.put(jpaEo.getEntity(), jpaEo);
		unsavedEnterpriseObjects.add(jpaEo);
	}

	public void deleteObject(EOEnterpriseObject eo) {
		JPAEnterpriseObject<JPAEntity> jpaEo = (JPAEnterpriseObject<JPAEntity>)eo;
		em.remove(jpaEo.getEntity());
		jpaEo.setEntity(null);
		jpaEo.resetEnterpriseObject();
	}

	public void saveChanges() {
		em.getTransaction().begin();
		em.getTransaction().commit();
		unsavedEnterpriseObjects.clear();
	}

	public void revert() {
		EntityManager oldEm = em;
		em = emFactory.createEntityManager();
		List<JPAEnterpriseObject<JPAEntity>> eosWithFaults = new ArrayList<>();
		for (JPAEnterpriseObject<JPAEntity> eo : objectsInEc.values()) {
			JPAEntity oldEntity = eo.getEntity();
			eo.resetEnterpriseObject();
			
			if (!unsavedEnterpriseObjects.contains(eo) && oldEntity != null) {
				eo.setEntity(em.getReference(eo.getJPAEntityClass(), oldEntity.getId()));
				eosWithFaults.add(eo);
			} else {
				eo.setEntity(null);
			}
		}
		objectsInEc.clear();
		unsavedEnterpriseObjects.clear();
		for (JPAEnterpriseObject<JPAEntity> eoToAdd : eosWithFaults) {
			objectsInEc.put(eoToAdd.getEntity(), eoToAdd);
		}
		oldEm.close();
	}
	
	public NSArray<?> objectsWithFetchSpecification(EOFetchSpecification fetchSpec) {
		//TODO: execute the FetchSpec wih JPA (same principle as by the modeled fetches)
		return NSArray.emptyArray();
	}
}
