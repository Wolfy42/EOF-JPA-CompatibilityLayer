package er.extensions.eof;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOKeyValueQualifier;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;

import er.extensions.jpa.JPAEditingContext;
import er.extensions.jpa.JPAEnterpriseObject;
import er.extensions.jpa.JPAEntity;
import er.extensions.jpa.JPAEntityDef;

public class ERXFetchSpecification<T> {

	public static final Class<?> _CLASS = ERXFetchSpecification.class;
	
	private String entityName;
	private EOQualifier qualifier;
	private NSArray<EOSortOrdering> sortOrderings;
	
	public ERXFetchSpecification(String entityName, Object object, Object object2, boolean b, boolean c, Object object3) {
		// TODO Auto-generated constructor stub
	}

	public ERXFetchSpecification(String entityName, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
		this.entityName = entityName;
		this.qualifier = qualifier;
		this.sortOrderings = sortOrderings;
	}

	public void setIsDeep(boolean b) {
		// TODO Auto-generated method stub
	}

	public NSArray<T> fetchObjects(EOEditingContext ec) {
		JPAEditingContext jpaEc = (JPAEditingContext) ec;
		EntityManager em = jpaEc.getEm();
		JPAEntityDef entityDef = JPAEnterpriseObject.getEntityDefFor(entityName);
		
		Map<String, Object> bindings = new HashMap<>();
		for (EOKeyValueQualifier oldQual : ERXQ.extractKeyValueQualifiers(qualifier)) {
			bindings.put(oldQual.key().replaceAll("\\.", "_"), oldQual.value());
			EOKeyValueQualifier newQual = new EOKeyValueQualifier(oldQual.key(), oldQual.selector(), ":"+oldQual.key());
			qualifier = ERXQ.replaceQualifierWithQualifier(qualifier, oldQual, newQual);
			
		}
		String jpqlWhere = qualifier.toString();
		
		for (Object qualifier : qualifier.allQualifierKeys()) {
			String key = (String)qualifier;
			jpqlWhere = jpqlWhere.replace("':"+key+"'", ":"+key.replaceAll("\\.", "_"));
			jpqlWhere = jpqlWhere.replace("("+key, "(e." + key);
		}
		String select = "select";
		//TODO: add check for 'usesDistinct'
//				select = select + " distinct";
		//TODO: add check for 'fetchEnterpriseObjects'
				select = select + " e";
		//TODO: if not 'fetchEnterpriseObjects' then detect 'rawRowKeyPaths'
		//		select = select + " e.$keyPath";
		String jpqlSortOrderings = "";
		for (EOSortOrdering sortOrdering : sortOrderings) {
			jpqlSortOrderings += sortOrdering.key() + (sortOrdering.selector() == EOSortOrdering.CompareAscending ? " asc" : " desc");
		}
		if (!jpqlSortOrderings.isEmpty()) {
			jpqlSortOrderings = " ORDER BY " + jpqlSortOrderings;
		}
		String jpqlQuery = select +" from " +entityDef.getJpaEntity().getName() + " e where " + jpqlWhere + jpqlSortOrderings;

		//TODO: add raw-fetch-typedQuery-option (see _JPAEntity)
		TypedQuery<? extends JPAEntity> query = em.createQuery(jpqlQuery, entityDef.getJpaEntity());
		for (String binding : bindings.keySet()) {
			Object para = bindings.get(binding);
			if (para instanceof JPAEnterpriseObject) {
				para = ((JPAEnterpriseObject<?>)para).getEntity();
			}
			query.setParameter(binding, para);
		}
		List<? extends JPAEntity> entities = query.getResultList();
		return jpaEc.getEnterpriseObjects(entityDef, entities);
	}
}
