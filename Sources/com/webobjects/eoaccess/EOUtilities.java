package com.webobjects.eoaccess;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;

import er.extensions.jpa.JPAUtilities;

public class EOUtilities {

	public static final Class<?> _CLASS = EOUtilities.class;
	
	public static EOEnterpriseObject objectWithPrimaryKeyValue(EOEditingContext ec, String entityName, Object value) {
		return JPAUtilities.objectWithPrimaryKeyValue(ec, entityName, value);
	}
	
	public static EOEnterpriseObject createAndInsertInstance(EOEditingContext ec, String entityName) {
		return JPAUtilities.createAndInsertInstance(ec, entityName);
	}

	public static NSArray<NSDictionary> rawRowsForSQL(EOEditingContext ec, String entityName, String query, 
			NSArray<String> keys) {
		
		return JPAUtilities.rawRowsForSQL(ec, entityName, query, keys);
	}
}
