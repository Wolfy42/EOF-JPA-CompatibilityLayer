package er.extensions.jpa;

import er.extensions.ERXFrameworkPrincipal;

public class JPACompatibilityLayer extends ERXFrameworkPrincipal {
	protected static JPACompatibilityLayer sharedInstance;
	@SuppressWarnings("unchecked")
	public final static Class<? extends ERXFrameworkPrincipal> REQUIRES[] = new Class[] {};

	static {
		setUpFrameworkPrincipalClass(JPACompatibilityLayer.class);
	}

	public static JPACompatibilityLayer sharedInstance() {
		if (sharedInstance == null) {
			sharedInstance = sharedInstance(JPACompatibilityLayer.class);
		}
		return sharedInstance;
	}

	@Override
	public void finishInitialization() {
		log.debug("JPACompatibilityLayer loaded");
	}
}
