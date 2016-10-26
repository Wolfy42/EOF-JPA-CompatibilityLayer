package at.eofjpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;

import er.extensions.eof.ERXEC;
import er.extensions.jpa.JPAFactory;

public class ModelFetchTest {

	@BeforeClass
	public static void beforeClass() throws Exception {
		ERXEC.setFactory(new JPAFactory());
	}
	
	@Test
	public void testCreateAndDeleteUser() {
		EOEditingContext ec = ERXEC.newEditingContext();
		
		User user = createUser(ec, "Jack");
		createPost(user, 1, "A");
		createPost(user, 2, "B");
		createPost(user, 3, "B");
		
		ec.saveChanges();
		
		EOEditingContext ec2 = ERXEC.newEditingContext();
		User user2 = user.localInstanceIn(ec2);
		
		// Test entity fetches
		NSArray<Post> posts = Post.fetchPostsForUser(ec2, user2);
		assertEquals(3, posts.size());
		
		assertTrue(user2 == posts.get(0).user());
		assertTrue(user2 == posts.get(1).user());
		assertTrue(user2 == posts.get(2).user());
		
		assertEquals(1, posts.get(0).nr().intValue());
		assertEquals(2, posts.get(1).nr().intValue());
		assertEquals(3, posts.get(2).nr().intValue());
		
		assertEquals("A", posts.get(0).text());
		assertEquals("B", posts.get(1).text());
		assertEquals("B", posts.get(2).text());
		
		// Test distinct raw fetches
		NSArray<NSDictionary> rawDistinctTexts = Post.fetchRawDistinctTextForUser(ec2, user2);
		assertEquals(2, rawDistinctTexts.size());
		assertEquals("A", Post.TEXT.valueInObject(rawDistinctTexts.get(0)));
		assertEquals("B", Post.TEXT.valueInObject(rawDistinctTexts.get(1)));
		
		// Test raw with keypath and reverse ordering
		NSArray<NSDictionary> rawWithKeypath = Post.fetchRawForUserAndNrDesc(ec2, 3, 1, user2);
		assertEquals(3, rawWithKeypath.size());
		
		assertEquals(3, rawWithKeypath.get(0).get("nr"));
		assertEquals(2, rawWithKeypath.get(1).get("nr"));
		assertEquals(1, rawWithKeypath.get(2).get("nr"));
		
		assertEquals("B", rawWithKeypath.get(0).get("text"));
		assertEquals("B", rawWithKeypath.get(1).get("text"));
		assertEquals("A", rawWithKeypath.get(2).get("text"));
		
		assertEquals("Jack", rawWithKeypath.get(0).get("user.firstname"));
		assertEquals("Jack", rawWithKeypath.get(1).get("user.firstname"));
		assertEquals("Jack", rawWithKeypath.get(2).get("user.firstname"));
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
