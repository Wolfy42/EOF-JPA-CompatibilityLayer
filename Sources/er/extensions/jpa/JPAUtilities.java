package er.extensions.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import er.extensions.jpa.JPAEditingContext;
import er.extensions.jpa.JPAEnterpriseObject;
import er.extensions.jpa.JPAEntity;
import er.extensions.jpa.JPAEntityDef;

public class JPAUtilities {

	public static final Class<?> _CLASS = JPAUtilities.class;
	
	public static EOEnterpriseObject objectWithPrimaryKeyValue(EOEditingContext ec, String entityName, Object value) {
		return objectWithPrimaryKeyValue(ec, JPAEnterpriseObject.getEntityDefFor(entityName), value);
	}
	
	public static EOEnterpriseObject objectWithPrimaryKeyValue(EOEditingContext ec, JPAEntityDef entityDef, Object value) {
		JPAEditingContext jpaEc = (JPAEditingContext)ec;
		return jpaEc.getEnterpriseObject(entityDef, value);
	}
	
	public static EOEnterpriseObject createAndInsertInstance(EOEditingContext ec, String entityName) {
		return createAndInsertInstance(ec, JPAEnterpriseObject.getEntityDefFor(entityName));
	}
	
	public static EOEnterpriseObject createAndInsertInstance(EOEditingContext ec, JPAEntityDef entityDef) {
		JPAEditingContext jpaEc = (JPAEditingContext)ec;
		try {
			JPAEnterpriseObject<JPAEntity> newO = (JPAEnterpriseObject<JPAEntity>) entityDef.getEoEntity().newInstance();
			jpaEc.insertObject(newO);
			
			return newO;
		} catch (Exception e) {
			throw new IllegalStateException("Could  not create entity", e);
		}
	}

	public static NSArray<NSDictionary> rawRowsForSQL(EOEditingContext ec, 
			String entityname, String query, NSArray<String> keys) {
		
		for (int i=0; i < keys.size()-1; i++) {
			query = query.replaceFirst(",", " as " + keys.get(i) + "SEMICOLON");
		}
		query = query.replaceFirst("from", "as " + keys.get(keys.size()-1) + " from");
		query = query.replaceAll("SEMICOLON", ",");
		
		EntityManager em = ((JPAEditingContext)ec).getEm();
		Query q = em.createNativeQuery(query);
		NSArray<NSDictionary> resultDict = new NSMutableArray<>();
		//TODO: implement case when this is null: keys
		// --> the problem is that duplicate column-names (select a.name, b.name from ...) is ok for EOF,
		//     but JPA is complaining about not unique column-names (therefore the hack above)
		// --> the SQL string must be properly parsed and rewritten according to this rule
		
		if (keys.size() > 1) {
			List<Object[]> rawDataList = q.getResultList();
			for (Object[] rawData : rawDataList) {
				NSDictionary<String, Object> result = new NSMutableDictionary<>();
				resultDict.add(result);
				for (int i=0; i < keys.size(); i++) {
					Object value = convertRawData(rawData[i]);
					
					result.put(keys.get(i), value);
				}
			}
		} else {
			List<Object> rawDataList = q.getResultList();
			for (Object rawData : rawDataList) {
				NSDictionary<String, Object> result = new NSMutableDictionary<>();
				resultDict.add(result);
				Object value = convertRawData(rawData);
				result.put(keys.get(0), value);
			}
		}
		
		return resultDict;
		
	}

	public static Object convertRawData(Object rawData) {
		Object value = rawData;
		if (value == null) {
			value = NSKeyValueCoding.NullValue;
		}
		return value;
	}
}
