package at.eofjpa;

import er.extensions.appserver.ERXApplication;
import er.extensions.eof.ERXEC;
import er.extensions.jpa.JPAFactory;

public class Application extends ERXApplication {
	public static void main(String[] argv) {
		ERXApplication.main(argv, Application.class);
	}

	public Application() {
		ERXApplication.log.info("Welcome to " + name() + " !");
		/* ** put your initialization code in here ** */
		setAllowsConcurrentRequestHandling(true);	
		
		ERXEC.setFactory(new JPAFactory());
	}
}
