package er.extensions.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.eof.ERXEC.DefaultFactory;

public class JPAFactory extends DefaultFactory {
		
	private EntityManagerFactory emFactory;
	
	public JPAFactory() {
		emFactory = Persistence.createEntityManagerFactory("ThePersistenceUnit");
		//TODO: EntityManagerFactory should be closed on shutdown
//			emFactory.close();
	}
	
	@Override
	public EOEditingContext _newEditingContext() {
		return new JPAEditingContext(emFactory);
	}
}
