// DO NOT EDIT.  Make changes to UserGroup.java instead.
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
public abstract class _UserGroup extends  JPAEnterpriseObject<_UserGroup.UserGroupEntity> {
	public static final String ENTITY_NAME = "UserGroup";
	public static final JPAEntityDef ENTITY_DEF = new JPAEntityDef(UserGroup.class, UserGroupEntity.class);
	static {
		addEntityDef(ENTITY_NAME, ENTITY_DEF);
	}

	// Attribute Keys
	// Relationship Keys
	public static final ERXKey<at.jpaeof.model.User> USERS = new ERXKey<at.jpaeof.model.User>("users");

	// Attributes
	// Relationships
	public static final String USERS_KEY = USERS.key();

	private static Logger LOG = Logger.getLogger(_UserGroup.class);

	// Relationships Enterprise-Objects
	private NSArray<at.jpaeof.model.User> users;

	protected Class<? extends JPAEntity> getJPAEntityClass() {
		return UserGroupEntity.class;
	}

	protected void createJPAEntity() {
		entity = new UserGroupEntity();
	}

	protected void resetEnterpriseObject() {
		resetUsers();
	}

	protected void resetUsers() {
		users = null;
	}
	
	public UserGroup localInstanceIn(EOEditingContext ec) {
		JPAEditingContext jpaEc = (JPAEditingContext) ec;
		return jpaEc.getEnterpriseObject(ENTITY_DEF, jpaEc.getEm().merge(getEntity()));
	}

	public NSArray<at.jpaeof.model.User> users() {
		if (users == null) {
			users = editingContext().getEnterpriseObjects(at.jpaeof.model.User.ENTITY_DEF, entity.getUsers());
		}
		return users;
	}

	public NSArray<at.jpaeof.model.User> users(EOQualifier qualifier) {
		return users(qualifier, null, false);
	}

	public NSArray<at.jpaeof.model.User> users(EOQualifier qualifier, boolean fetch) {
		return users(qualifier, null, fetch);
	}

	public NSArray<at.jpaeof.model.User> users(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
		NSArray<at.jpaeof.model.User> results;
		if (fetch) {
			EOQualifier fullQualifier;
			EOQualifier inverseQualifier = new EOKeyValueQualifier(at.jpaeof.model.User.USER_GROUP_KEY, EOQualifier.QualifierOperatorEqual, this);
			if (qualifier == null) {
				fullQualifier = inverseQualifier;
			} else {
				NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
				qualifiers.addObject(qualifier);
				qualifiers.addObject(inverseQualifier);
				fullQualifier = new EOAndQualifier(qualifiers);
			}
			results = at.jpaeof.model.User.fetchUsers(editingContext(), fullQualifier, sortOrderings);
		} else {
			results = users();
			if (qualifier != null) {
				results = (NSArray<at.jpaeof.model.User>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
			}
			if (sortOrderings != null) {
				results = (NSArray<at.jpaeof.model.User>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
			}
		}
		return results;
	}
  
	public void addToUsers(at.jpaeof.model.User object) {
		addToUsersRelationship(object);
	}

	public void removeFromUsers(at.jpaeof.model.User object) {
		removeFromUsersRelationship(object);
	}

	public void addToUsersRelationship(at.jpaeof.model.User object) {
		object.setUserGroupRelationship((UserGroup)this);
	}

	public void removeFromUsersRelationship(at.jpaeof.model.User object) {
		object.setUserGroupRelationship(null);
	}

	public at.jpaeof.model.User createUsersRelationship() {
		at.jpaeof.model.User eo = (at.jpaeof.model.User)JPAUtilities.createAndInsertInstance(editingContext(), at.jpaeof.model.User.ENTITY_DEF);
		eo.setUserGroupRelationship((UserGroup)this);
		return eo;
	}

	public void deleteUsersRelationship(at.jpaeof.model.User object) {
		object.setUserGroupRelationship(null);
		editingContext().deleteObject(object);
	}

	public void deleteAllUsersRelationships() {
		Enumeration<at.jpaeof.model.User> objects = users().immutableClone().objectEnumerator();
		while (objects.hasMoreElements()) {
			deleteUsersRelationship(objects.nextElement());
		}
	}


	public static UserGroup createUserGroup(EOEditingContext editingContext) {
		UserGroup eo = (UserGroup) JPAUtilities.createAndInsertInstance(editingContext, _UserGroup.ENTITY_DEF);    
		return eo;
	}

  public static ERXFetchSpecification<UserGroup> fetchSpec() {
    return new ERXFetchSpecification<UserGroup>(_UserGroup.ENTITY_DEF, null, null, false, true, null);
  }

  public static NSArray<UserGroup> fetchAllUserGroups(EOEditingContext ec) {
	  JPAEditingContext jpaEc = (JPAEditingContext)ec;
	  List<UserGroupEntity> results = new UserGroupDAO(jpaEc.getEm()).fetchAllUserGroups();
	  return jpaEc.getEnterpriseObjects(UserGroup.ENTITY_DEF, results);
  }

  public static NSArray<UserGroup> fetchAllUserGroups(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _UserGroup.fetchUserGroups(editingContext, null, sortOrderings);
  }

  public static NSArray<UserGroup> fetchUserGroups(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<UserGroup> fetchSpec = new ERXFetchSpecification<UserGroup>(_UserGroup.ENTITY_DEF, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<UserGroup> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static UserGroup fetchUserGroup(EOEditingContext editingContext, String keyName, Object value) {
    return _UserGroup.fetchUserGroup(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static UserGroup fetchUserGroup(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<UserGroup> eoObjects = _UserGroup.fetchUserGroups(editingContext, qualifier, null);
    UserGroup eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one UserGroup that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static UserGroup fetchRequiredUserGroup(EOEditingContext editingContext, String keyName, Object value) {
    return _UserGroup.fetchRequiredUserGroup(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static UserGroup fetchRequiredUserGroup(EOEditingContext editingContext, EOQualifier qualifier) {
    UserGroup eoObject = _UserGroup.fetchUserGroup(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no UserGroup that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

	public static UserGroup localInstanceIn(EOEditingContext ec, UserGroup eo) {
		JPAEditingContext jpaEc = (JPAEditingContext) ec;
		return jpaEc.getEnterpriseObject(ENTITY_DEF, jpaEc.getEm().merge(eo.getEntity()));
	}

	@StaticMetamodel(UserGroupEntity.class)
	public static class UserGroupEntity_ extends JPAEntity_ {
		
		public static volatile ListAttribute<UserGroup, _User.UserEntity> users;
	}

	@Entity
	@Table(name = "UserGroups")
	public static class UserGroupEntity extends JPAEntityImpl {

		@OneToMany(mappedBy="userGroup", fetch = FetchType.LAZY)
		private List<_User.UserEntity> users = new ArrayList<>();

		public List<_User.UserEntity> getUsers() {
			return users;
		}
		public void setUsers(List<_User.UserEntity> users) {
			this.users = users;
		}
	}

	public static class UserGroupDAO {
		
		private EntityManager em;
		public UserGroupDAO(EntityManager em) {
			this.em = em;
		}
		
		public List<UserGroupEntity> fetchAllUserGroups() {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<UserGroupEntity> q = cb.createQuery(UserGroupEntity.class);
			Root<UserGroupEntity> r = q.from(UserGroupEntity.class);
			q.select(r);
			TypedQuery<UserGroupEntity> tq = em.createQuery(q);
			return tq.getResultList();
		}
		
	}
}
