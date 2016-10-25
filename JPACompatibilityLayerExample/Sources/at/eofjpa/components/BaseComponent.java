package at.eofjpa.components;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXComponent;

import at.eofjpa.Application;
import at.eofjpa.Session;

public class BaseComponent extends ERXComponent {

	private static final long serialVersionUID = 1L;

	public BaseComponent(WOContext context) {
		super(context);
	}
	
	@Override
	public Application application() {
		return (Application)super.application();
	}
	
	@Override
	public Session session() {
		return (Session)super.session();
	}
}
