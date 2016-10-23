package er.extensions.jpa;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGenericRecord;

public abstract class JPAEnterpriseObject<T extends JPAEntity> extends EOGenericRecord {

	private static Map<String, JPAEntityDef> mapNameToEntityDef = Collections.synchronizedMap(new HashMap<>());
	
	protected static void addEntityDef(String name, JPAEntityDef def) {
		mapNameToEntityDef.put(name, def);
	}
	
	public static JPAEntityDef getEntityDefFor(String entityName) {
		return mapNameToEntityDef.get(entityName);
	}
	
	public JPAEditingContext ec;
	
	protected T entity;
	
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public JPAEditingContext editingContext() {
		return ec;
	}
	public void setEc(JPAEditingContext ec) {
		this.ec = ec;
	}
	
	protected abstract Class<? extends JPAEntity> getJPAEntityClass();
	
	protected abstract void createJPAEntity();
	
	protected abstract void resetEnterpriseObject();
	
	//TODO: call with lifecycle-listeners from the JPA-Entities
	public void awakeFromInsertion(EOEditingContext ec) {
		
	}
	public void willUpdate() {
		
	}
}
