package er.extensions.eof;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;

import er.extensions.jpa.JPAEntityDef;

public class ERXFetchSpecification<T> {

	public static final Class<?> _CLASS = ERXFetchSpecification.class;
	
	public ERXFetchSpecification(JPAEntityDef entityName, Object object, Object object2, boolean b, boolean c, Object object3) {
		// TODO Auto-generated constructor stub
	}

	public ERXFetchSpecification(JPAEntityDef entityName, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
		// TODO Auto-generated constructor stub
	}

	public void setIsDeep(boolean b) {
		// TODO Auto-generated method stub
	}

	public NSArray<T> fetchObjects(EOEditingContext editingContext) {
		// TODO Auto-generated method stub
		return null;
	}
}