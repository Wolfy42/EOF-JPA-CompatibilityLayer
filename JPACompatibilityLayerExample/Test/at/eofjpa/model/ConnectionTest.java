package at.eofjpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXEC;
import er.extensions.jpa.JPAFactory;

public class ConnectionTest {

	@BeforeClass
	public static void beforeClass() throws Exception {
		ERXEC.setFactory(new JPAFactory());
	}
	
	@Test
	public void moreEditingContextsThanConnectionsShouldBePossible() {
		List<EOEditingContext> ecs = new ArrayList<>();
		for (int i = 0; i < 100 ; i++) {
			System.out.println("current:" + i);
			EOEditingContext ec = ERXEC.newEditingContext();
			ecs.add(ec);
			NSArray<User> users = User.fetchAllUsers(ec);
			assertNotNull(users);
		}
		for (EOEditingContext ec : ecs) {
			Post.fetchAllPosts(ec);
		}
		assertEquals(100, ecs.size());
	}
}
