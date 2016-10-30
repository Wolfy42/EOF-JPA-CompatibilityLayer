package at.eofjpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXFetchSpecification;
import er.extensions.eof.ERXQ;
import er.extensions.eof.ERXSortOrdering.ERXSortOrderings;
import er.extensions.jpa.JPAFactory;

public class FetchSpecTest {

	@BeforeClass
	public static void beforeClass() throws Exception {
		ERXEC.setFactory(new JPAFactory());
	}
	
	@Test
	public void testFetching() {
		EOEditingContext ec = ERXEC.newEditingContext();
		
		User user = createUser(ec, "FetchSpecTester");
		Post p1 = createPost(user, 1, "A");
		Post p2 = createPost(user, 2, "B");
		Post p3 = createPost(user, 3, "B");
		Post p4 = createPost(user, 4, "A");
		
		ec.saveChanges();
		
		EOQualifier qualifier = ERXQ.and(
			Post.USER.is(user),
			Post.TEXT.is("B")
		);
		ERXSortOrderings sortOrderings = Post.NR.ascs();
		ERXFetchSpecification<Post> fetchSpec = new ERXFetchSpecification<>(
			Post.ENTITY_NAME, qualifier, sortOrderings);
		
		NSArray<Post> posts = fetchSpec.fetchObjects(ec);
		
		assertEquals(2, posts.size());
		assertTrue(p2 == posts.get(0));
		assertTrue(p3 == posts.get(1));
		
		// test qualifier-path and reverse-ordering
		EOQualifier qualifier2 = ERXQ.and(
			Post.USER.dot(User.FIRSTNAME).is("FetchSpecTester"),
			Post.TEXT.isNot("B")
		);
		ERXSortOrderings sortOrderings2 = Post.NR.descs();
		ERXFetchSpecification<Post> fetchSpec2 = new ERXFetchSpecification<>(
			Post.ENTITY_NAME, qualifier2, sortOrderings2);
		
		NSArray<Post> posts2 = fetchSpec2.fetchObjects(ec);
		
		assertEquals(2, posts2.size());
		assertTrue(p4 == posts2.get(0));
		assertTrue(p1 == posts2.get(1));
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
