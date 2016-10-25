// DO NOT EDIT.  Make changes to User.java instead.
package at.eofjpa.model;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;

import javax.persistence.metamodel.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import er.extensions.jpa.converter.*;

import java.math.*;
import java.util.*;

import org.apache.log4j.Logger;

import er.extensions.eof.*;
import er.extensions.foundation.*;
import er.extensions.jpa.*;

@SuppressWarnings("all")
public abstract class _User extends  JPAEnterpriseObject<_User.UserEntity> {
	public static final String ENTITY_NAME = "User";
	public static final JPAEntityDef ENTITY_DEF = new JPAEntityDef(User.class, UserEntity.class);
	static {
		addEntityDef(ENTITY_NAME, ENTITY_DEF);
	}

	// Attribute Keys
	public static final ERXKey<NSTimestamp> CREATE_DATE = new ERXKey<NSTimestamp>("createDate");
	public static final ERXKey<String> FIRSTNAME = new ERXKey<String>("firstname");
	public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
	public static final ERXKey<String> INFO = new ERXKey<String>("info");
	public static final ERXKey<Boolean> TESTUSER = new ERXKey<Boolean>("testuser");
	// Relationship Keys
	public static final ERXKey<at.eofjpa.model.Post> POSTS = new ERXKey<at.eofjpa.model.Post>("posts");

	// Attributes
	public static final String CREATE_DATE_KEY = CREATE_DATE.key();
	public static final String FIRSTNAME_KEY = FIRSTNAME.key();
	public static final String ID_KEY = ID.key();
	public static final String INFO_KEY = INFO.key();
	public static final String TESTUSER_KEY = TESTUSER.key();
	// Relationships
	public static final String POSTS_KEY = POSTS.key();

	private static Logger LOG = Logger.getLogger(_User.class);

	// Relationships Enterprise-Objects
	private NSArray<at.eofjpa.model.Post> posts;

	protected Class<? extends JPAEntity> getJPAEntityClass() {
		return UserEntity.class;
	}

	protected void createJPAEntity() {
		entity = new UserEntity();
	}

	protected void resetEnterpriseObject() {
		resetPosts();
	}

	protected void resetPosts() {
		posts = null;
	}
	
	public User localInstanceIn(EOEditingContext ec) {
		JPAEditingContext jpaEc = (JPAEditingContext) ec;
		return jpaEc.getEnterpriseObject(ENTITY_DEF, jpaEc.getEm().merge(getEntity()));
	}

	public NSTimestamp createDate() {
		return entity.getCreateDate();
	}

	public void setCreateDate(NSTimestamp value) {
		entity.setCreateDate(value);
	}

	public String firstname() {
		return entity.getFirstname();
	}

	public void setFirstname(String value) {
		entity.setFirstname(value);
	}

	public Integer id() {
		return entity.getId();
	}

	public void setId(Integer value) {
		entity.setId(value);
	}

	public String info() {
		return entity.getInfo();
	}

	public void setInfo(String value) {
		entity.setInfo(value);
	}

	public Boolean testuser() {
		return entity.getTestuser();
	}

	public void setTestuser(Boolean value) {
		entity.setTestuser(value);
	}

	public NSArray<at.eofjpa.model.Post> posts() {
		if (posts == null) {
			posts = editingContext().getEnterpriseObjects(at.eofjpa.model.Post.ENTITY_DEF, entity.getPosts());
		}
		return posts;
	}

	public NSArray<at.eofjpa.model.Post> posts(EOQualifier qualifier) {
		return posts(qualifier, null, false);
	}

	public NSArray<at.eofjpa.model.Post> posts(EOQualifier qualifier, boolean fetch) {
		return posts(qualifier, null, fetch);
	}

	public NSArray<at.eofjpa.model.Post> posts(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
		NSArray<at.eofjpa.model.Post> results;
		if (fetch) {
			EOQualifier fullQualifier;
			EOQualifier inverseQualifier = new EOKeyValueQualifier(at.eofjpa.model.Post.USER_KEY, EOQualifier.QualifierOperatorEqual, this);
			if (qualifier == null) {
				fullQualifier = inverseQualifier;
			} else {
				NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
				qualifiers.addObject(qualifier);
				qualifiers.addObject(inverseQualifier);
				fullQualifier = new EOAndQualifier(qualifiers);
			}
			results = at.eofjpa.model.Post.fetchPosts(editingContext(), fullQualifier, sortOrderings);
		} else {
			results = posts();
			if (qualifier != null) {
				results = (NSArray<at.eofjpa.model.Post>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
			}
			if (sortOrderings != null) {
				results = (NSArray<at.eofjpa.model.Post>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
			}
		}
		return results;
	}
  
	public void addToPosts(at.eofjpa.model.Post object) {
		addToPostsRelationship(object);
	}

	public void removeFromPosts(at.eofjpa.model.Post object) {
		removeFromPostsRelationship(object);
	}

	public void addToPostsRelationship(at.eofjpa.model.Post object) {
		object.setUserRelationship((User)this);
	}

	public void removeFromPostsRelationship(at.eofjpa.model.Post object) {
		object.setUserRelationship(null);
	}

	public at.eofjpa.model.Post createPostsRelationship() {
		at.eofjpa.model.Post eo = (at.eofjpa.model.Post)JPAUtilities.createAndInsertInstance(editingContext(), at.eofjpa.model.Post.ENTITY_DEF);
		eo.setUserRelationship((User)this);
		return eo;
	}

	public void deletePostsRelationship(at.eofjpa.model.Post object) {
		object.setUserRelationship(null);
		editingContext().deleteObject(object);
	}

	public void deleteAllPostsRelationships() {
		Enumeration<at.eofjpa.model.Post> objects = posts().immutableClone().objectEnumerator();
		while (objects.hasMoreElements()) {
			deletePostsRelationship(objects.nextElement());
		}
	}


	public static User createUser(EOEditingContext editingContext, NSTimestamp createDate
, String firstname
, Integer id
, Boolean testuser
) {
		User eo = (User) JPAUtilities.createAndInsertInstance(editingContext, _User.ENTITY_DEF);    
		eo.setCreateDate(createDate);
		eo.setFirstname(firstname);
		eo.setId(id);
		eo.setTestuser(testuser);
		return eo;
	}

  public static ERXFetchSpecification<User> fetchSpec() {
    return new ERXFetchSpecification<User>(_User.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<User> fetchAllUsers(EOEditingContext ec) {
	  JPAEditingContext jpaEc = (JPAEditingContext)ec;
	  List<UserEntity> results = new UserDAO(jpaEc.getEm()).fetchAllUsers();
	  return jpaEc.getEnterpriseObjects(User.ENTITY_DEF, results);
  }

  public static NSArray<User> fetchAllUsers(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _User.fetchUsers(editingContext, null, sortOrderings);
  }

  public static NSArray<User> fetchUsers(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<User> fetchSpec = new ERXFetchSpecification<User>(_User.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<User> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static User fetchUser(EOEditingContext editingContext, String keyName, Object value) {
    return _User.fetchUser(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static User fetchUser(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<User> eoObjects = _User.fetchUsers(editingContext, qualifier, null);
    User eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one User that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static User fetchRequiredUser(EOEditingContext editingContext, String keyName, Object value) {
    return _User.fetchRequiredUser(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static User fetchRequiredUser(EOEditingContext editingContext, EOQualifier qualifier) {
    User eoObject = _User.fetchUser(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no User that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

	public static User localInstanceIn(EOEditingContext ec, User eo) {
		JPAEditingContext jpaEc = (JPAEditingContext) ec;
		return jpaEc.getEnterpriseObject(ENTITY_DEF, jpaEc.getEm().merge(eo.getEntity()));
	}

	@StaticMetamodel(UserEntity.class)
	public static class UserEntity_ extends JPAEntity_ {
		
		public static volatile SingularAttribute<User, NSTimestamp> createDate;
		public static volatile SingularAttribute<User, String> firstname;
		public static volatile SingularAttribute<User, String> info;
		public static volatile SingularAttribute<User, Boolean> testuser;
		public static volatile ListAttribute<User, _Post.PostEntity> posts;
	}

	@Entity
	@Table(name = "User")
	public static class UserEntity extends JPAEntityImpl {

		@Column(name = "createDate", nullable = false)
		@Convert(converter = er.extensions.jpa.converter.NSTimestampConverter.class)
		@NotNull
		private NSTimestamp createDate;

		@Column(name = "firstname", nullable = false)
		@NotNull
		private String firstname;

		@Column(name = "info")
		private String info;

		@Column(name = "testuser", nullable = false)
		@NotNull
		private Boolean testuser;

		@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
		private List<_Post.PostEntity> posts = new ArrayList<>();

		public NSTimestamp getCreateDate() {
			return createDate;
		}
		public void setCreateDate(NSTimestamp createDate) {
			this.createDate = createDate;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		public Boolean getTestuser() {
			return testuser;
		}
		public void setTestuser(Boolean testuser) {
			this.testuser = testuser;
		}
		public List<_Post.PostEntity> getPosts() {
			return posts;
		}
		public void setPosts(List<_Post.PostEntity> posts) {
			this.posts = posts;
		}
	}

	public static class UserDAO {
		
		private EntityManager em;
		public UserDAO(EntityManager em) {
			this.em = em;
		}
		
		public List<UserEntity> fetchAllUsers() {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<UserEntity> q = cb.createQuery(UserEntity.class);
			Root<UserEntity> r = q.from(UserEntity.class);
			q.select(r);
			TypedQuery<UserEntity> tq = em.createQuery(q);
			return tq.getResultList();
		}
		
	}
}
