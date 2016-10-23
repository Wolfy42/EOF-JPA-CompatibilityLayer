// DO NOT EDIT.  Make changes to User.java instead.
package at.jpaeof.model;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;

import javax.persistence.metamodel.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import at.jpaeof.model.converter.*;

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
	public static final ERXKey<String> EMAIL = new ERXKey<String>("email");
	public static final ERXKey<String> FIRSTNAME = new ERXKey<String>("firstname");
	public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
	public static final ERXKey<String> LASTNAME = new ERXKey<String>("lastname");
	// Relationship Keys
	public static final ERXKey<at.jpaeof.model.UserGroup> USER_GROUP = new ERXKey<at.jpaeof.model.UserGroup>("userGroup");

	// Attributes
	public static final String EMAIL_KEY = EMAIL.key();
	public static final String FIRSTNAME_KEY = FIRSTNAME.key();
	public static final String ID_KEY = ID.key();
	public static final String LASTNAME_KEY = LASTNAME.key();
	// Relationships
	public static final String USER_GROUP_KEY = USER_GROUP.key();

	private static Logger LOG = Logger.getLogger(_User.class);

	// Relationships Enterprise-Objects
	private at.jpaeof.model.UserGroup userGroup;

	protected Class<? extends JPAEntity> getJPAEntityClass() {
		return UserEntity.class;
	}

	protected void createJPAEntity() {
		entity = new UserEntity();
	}

	protected void resetEnterpriseObject() {
		resetUserGroup();
	}
	
	protected void resetUserGroup() {
		userGroup = null;
	}
	
	public User localInstanceIn(EOEditingContext ec) {
		JPAEditingContext jpaEc = (JPAEditingContext) ec;
		return jpaEc.getEnterpriseObject(ENTITY_DEF, jpaEc.getEm().merge(getEntity()));
	}

	public String email() {
		return entity.getEmail();
	}

	public void setEmail(String value) {
		entity.setEmail(value);
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

	public String lastname() {
		return entity.getLastname();
	}

	public void setLastname(String value) {
		entity.setLastname(value);
	}
  
	public at.jpaeof.model.UserGroup userGroup() {
		return getUserGroup();
	}
	
	public at.jpaeof.model.UserGroup getUserGroup() {
		if (userGroup == null) {
			userGroup = editingContext().getEnterpriseObject(at.jpaeof.model.UserGroup.ENTITY_DEF, entity.getUserGroup());
		}
		return userGroup;
	}
	
	public void setUserGroup(at.jpaeof.model.UserGroup userGroup) {
		setUserGroupRelationship(userGroup);
	}
  
	public void setUserGroupRelationship(at.jpaeof.model.UserGroup value) {
		if (value != null) {
			entity.setUserGroup(value.getEntity());
			value.getEntity().getUsers().add(entity);
			value.resetUsers();
		} else {
			entity.setUserGroup(null);
			if (userGroup != null) {
				userGroup.getEntity().getUsers().remove(entity);
			}
		}
		resetUserGroup();
	}

	public static User createUser(EOEditingContext editingContext, String email
, Integer id
) {
		User eo = (User) JPAUtilities.createAndInsertInstance(editingContext, _User.ENTITY_DEF);    
		eo.setEmail(email);
		eo.setId(id);
		return eo;
	}

  public static ERXFetchSpecification<User> fetchSpec() {
    return new ERXFetchSpecification<User>(_User.ENTITY_DEF, null, null, false, true, null);
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
    ERXFetchSpecification<User> fetchSpec = new ERXFetchSpecification<User>(_User.ENTITY_DEF, qualifier, sortOrderings);
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

	public static NSArray<at.jpaeof.model.User> fetchForEmail(EOEditingContext ec, NSDictionary<String, Object> bindings) {
		return fetchForEmail(ec
, (String)bindings.get("email"));
	}
  
	public static NSArray<at.jpaeof.model.User> fetchForEmail(EOEditingContext ec
 , String emailBinding) {	
		JPAEditingContext jpaEc = (JPAEditingContext)ec;
		List<UserEntity> results = new UserDAO(jpaEc.getEm()).fetchForEmail(
emailBinding);
		NSArray<at.jpaeof.model.User> resultsEO = new NSMutableArray<>();
		for (UserEntity o : results) {
			at.jpaeof.model.User result = new at.jpaeof.model.User();
			resultsEO.add(result);
			result.setEntity(o);
			result.setEc(jpaEc);
		}
		return resultsEO;
	}
  
	@StaticMetamodel(UserEntity.class)
	public static class UserEntity_ extends JPAEntity_ {
		
		public static volatile SingularAttribute<User, String> email;
		public static volatile SingularAttribute<User, String> firstname;
		public static volatile SingularAttribute<User, String> lastname;
		public static volatile SingularAttribute<User, _UserGroup.UserGroupEntity> userGroup;
	}

	@Entity
	@Table(name = "Users")
	public static class UserEntity extends JPAEntityImpl {

		@Column(name = "email", nullable = false)
		@NotNull
		private String email;

		@Column(name = "firstname")
		private String firstname;

		@Column(name = "lastname")
		private String lastname;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "userGroupID")
		private _UserGroup.UserGroupEntity userGroup;

		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getLastname() {
			return lastname;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		public _UserGroup.UserGroupEntity getUserGroup() {
			return userGroup;
		}
		public void setUserGroup(_UserGroup.UserGroupEntity userGroup) {
			this.userGroup = userGroup;
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
		
		public List<UserEntity> fetchForEmail(
				String email
				) {
			String emfQuery = "email = $email";
			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(emfQuery, null);
			NSMutableDictionary<String, Object> bindings = new NSMutableDictionary<String, Object>();
			if (email != null) {
				bindings.takeValueForKey(":email", "email");
			}
			EOQualifier qualforBindings = qualifier.qualifierWithBindings(bindings, false);
			String jpqlWhere = qualforBindings.toString();
			jpqlWhere = jpqlWhere.replace("':email'", ":email");
			for (Object key : qualforBindings.allQualifierKeys()) {
				jpqlWhere = jpqlWhere.replace("("+(String)key, "(e." + (String)key);
			}
			String select = "select";
			select = select + " e";
			String jpqlSortOrderings = "";
			if (!jpqlSortOrderings.isEmpty()) {
				jpqlSortOrderings = " ORDER BY " + jpqlSortOrderings;
			}
			String jpqlQuery = select +" from _User$UserEntity e where " + jpqlWhere + jpqlSortOrderings;
			TypedQuery< UserEntity> query = em.createQuery(jpqlQuery, UserEntity.class);
			if (email != null) {
				query.setParameter("email", email);
			}
			return query.getResultList();
		}
	}
}
