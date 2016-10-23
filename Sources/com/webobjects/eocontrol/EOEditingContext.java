package com.webobjects.eocontrol;

import com.webobjects.foundation.NSArray;

public abstract class EOEditingContext {

	public static final Class<?> _CLASS = EOEditingContext.class;

	private UndoManager um;
	private EOObjectStore os;
    
    public EOEditingContext() {
		this.um = new UndoManager();
		this.os = new EOObjectStore();
	}
    
	public UndoManager undoManager() {
		return um;
	}
	public EOObjectStore rootObjectStore() {
		return os;
	}
	
	public static void setUsesContextRelativeEncoding(boolean value) {
	}
	
	public void lock() {
	}
	public void unlock() {
	}
	
	public abstract void insertObject(EOEnterpriseObject eo);

	public abstract void deleteObject(EOEnterpriseObject eo);

	public abstract void saveChanges();

	public abstract void revert();
	
	public abstract NSArray<?> objectsWithFetchSpecification(EOFetchSpecification fetchSpec);
}
