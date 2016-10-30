package at.eofjpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXEC;
import er.extensions.jpa.JPAFactory;

public class QualifierSortTest {

	@BeforeClass
	public static void beforeClass() throws Exception {
		ERXEC.setFactory(new JPAFactory());
	}
	
	@Test
	public void testQualifiers() {
		EOEditingContext ec = ERXEC.newEditingContext();
		
		User user = createUser(ec, "Jack");
		Post p1 = createPost(user, 1, "A");
		Post p2 = createPost(user, 2, "B");
		Post p3 = createPost(user, 3, "B");
		Post p4 = createPost(user, 4, "A");
		
		NSArray<Post> posts5 = user.posts(Post.NR.eq(5));
		assertTrue(posts5.isEmpty());
		
		NSArray<Post> posts2 = user.posts(Post.NR.eq(2));
		assertEquals(1, posts2.size());
		assertTrue(p2 == posts2.get(0));
		
		NSArray<Post> postsReverse = user.posts(Post.TEXT.inObjects("A", "B"), 
			Post.NR.descs(), false);
		
		assertEquals(4, postsReverse.size());
		assertTrue(p4 == postsReverse.get(0));
		assertTrue(p3 == postsReverse.get(1));
		assertTrue(p2 == postsReverse.get(2));
		assertTrue(p1 == postsReverse.get(3));
	}
	
	private User createUser(EOEditingContext ec, String firstname) {
		User user = new User();
		ec.insertObject(user);
		user.setFirstname(firstname);
		user.setTestuser(true);
		return user;
	}
	
	private Post createPost(User user, int nr, String text) {
		Post post = new Post();
		user.editingContext().insertObject(post);
		post.setUserRelationship(user);
		post.setNr(nr);
		post.setText(text);
		
		return post;
	}
}
