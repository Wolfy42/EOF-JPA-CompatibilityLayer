package at.eofjpa.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSValidation;

import er.extensions.eof.ERXEC;
import er.extensions.jpa.JPAFactory;

public class UserTest {

	@BeforeClass
	public static void beforeClass() throws Exception {
		ERXEC.setFactory(new JPAFactory());
		
		EOEditingContext ec = ERXEC.newEditingContext();
		NSArray<User> users = User.fetchAllUsers(ec);
		for (User user : new ArrayList<>(users)) {
			if (user.testuser()) {
				ec.deleteObject(user);
			}
		}
		ec.saveChanges();
	}
	
	@Test
	public void testCreateUser() {
		EOEditingContext ec = ERXEC.newEditingContext();
		
		User user = new User();
		ec.insertObject(user);
		user.setTestuser(true);
		
		boolean exc = false;
		try {
			ec.saveChanges();
		} catch (NSValidation.ValidationException e) {
			exc = true;
		}
		assertTrue(exc);
		
		user.setFirstname("test2");
		ec.saveChanges();
	}
	
	@Test
	public void testCreateAndDeleteUser() {
		EOEditingContext ec = ERXEC.newEditingContext();
		
		User user = new User();
		ec.insertObject(user);
		user.setFirstname("test");
		user.setTestuser(true);
		
		ec.saveChanges();
		
		assertNotNull(user.id());
		
		ec.deleteObject(user);
		ec.saveChanges();
	}
	
	@Test
	public void testCreateAndUpdateUser() {
		EOEditingContext ec = ERXEC.newEditingContext();
		
		User user = new User();
		ec.insertObject(user);
		user.setFirstname("otherbefore");
		user.setTestuser(true);
		
		ec.saveChanges();
		
		Integer id = user.id();
		
		EOEditingContext ec2 = ERXEC.newEditingContext();
		User user2 = (User) EOUtilities.objectWithPrimaryKeyValue(ec2, User.ENTITY_NAME, id);
		user2.setFirstname("otherafter");
		
		ec2.saveChanges();
	}
}
