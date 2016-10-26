// DO NOT EDIT.  Make changes to Post.java instead.
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
public abstract class _Post extends  JPAEnterpriseObject<_Post.PostEntity> {
	public static final String ENTITY_NAME = "Post";
	public static final JPAEntityDef ENTITY_DEF = new JPAEntityDef(Post.class, PostEntity.class);
	static {
		addEntityDef(ENTITY_NAME, ENTITY_DEF);
	}

	// Attribute Keys
	public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
	public static final ERXKey<Integer> NR = new ERXKey<Integer>("nr");
	public static final ERXKey<String> TEXT = new ERXKey<String>("text");
	// Relationship Keys
	public static final ERXKey<at.eofjpa.model.Remark> REMARKS = new ERXKey<at.eofjpa.model.Remark>("remarks");
	public static final ERXKey<at.eofjpa.model.User> USER = new ERXKey<at.eofjpa.model.User>("user");

	// Attributes
	public static final String ID_KEY = ID.key();
	public static final String NR_KEY = NR.key();
	public static final String TEXT_KEY = TEXT.key();
	// Relationships
	public static final String REMARKS_KEY = REMARKS.key();
	public static final String USER_KEY = USER.key();

	private static Logger LOG = Logger.getLogger(_Post.class);

	// Relationships Enterprise-Objects
	private at.eofjpa.model.User user;
	private NSArray<at.eofjpa.model.Remark> remarks;

	protected Class<? extends JPAEntity> getJPAEntityClass() {
		return PostEntity.class;
	}

	protected void createJPAEntity() {
		entity = new PostEntity();
	}

	protected void resetEnterpriseObject() {
		resetUser();
		resetRemarks();
	}

	protected void resetUser() {
		user = null;
	}
	
	protected void resetRemarks() {
		remarks = null;
	}
	
	public Post localInstanceIn(EOEditingContext ec) {
		JPAEditingContext jpaEc = (JPAEditingContext) ec;
		return jpaEc.getEnterpriseObject(ENTITY_DEF, jpaEc.getEm().merge(getEntity()));
	}

	public Integer id() {
		return entity.getId();
	}

	public void setId(Integer value) {
		entity.setId(value);
	}

	public Integer nr() {
		return entity.getNr();
	}

	public void setNr(Integer value) {
		entity.setNr(value);
	}

	public String text() {
		return entity.getText();
	}

	public void setText(String value) {
		entity.setText(value);
	}

	public at.eofjpa.model.User user() {
		return getUser();
	}
	
	public at.eofjpa.model.User getUser() {
		if (user == null) {
			user = editingContext().getEnterpriseObject(at.eofjpa.model.User.ENTITY_DEF, entity.getUser());
		}
		return user;
	}
	
	public void setUser(at.eofjpa.model.User user) {
		setUserRelationship(user);
	}
  
	public void setUserRelationship(at.eofjpa.model.User value) {
		if (value != null) {
			entity.setUser(value.getEntity());
			value.getEntity().getPosts().add(entity);
			value.resetPosts();
		} else {
			entity.setUser(null);
			if (user != null) {
				user.getEntity().getPosts().remove(entity);
			}
		}
		resetUser();
	}
  
	public NSArray<at.eofjpa.model.Remark> remarks() {
		if (remarks == null) {
			remarks = editingContext().getEnterpriseObjects(at.eofjpa.model.Remark.ENTITY_DEF, entity.getRemarks());
		}
		return remarks;
	}

	public NSArray<at.eofjpa.model.Remark> remarks(EOQualifier qualifier) {
		return remarks(qualifier, null, false);
	}

	public NSArray<at.eofjpa.model.Remark> remarks(EOQualifier qualifier, boolean fetch) {
		return remarks(qualifier, null, fetch);
	}

	public NSArray<at.eofjpa.model.Remark> remarks(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
		NSArray<at.eofjpa.model.Remark> results;
		if (fetch) {
			EOQualifier fullQualifier;
			EOQualifier inverseQualifier = new EOKeyValueQualifier(at.eofjpa.model.Remark.POST_KEY, EOQualifier.QualifierOperatorEqual, this);
			if (qualifier == null) {
				fullQualifier = inverseQualifier;
			} else {
				NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
				qualifiers.addObject(qualifier);
				qualifiers.addObject(inverseQualifier);
				fullQualifier = new EOAndQualifier(qualifiers);
			}
			results = at.eofjpa.model.Remark.fetchRemarks(editingContext(), fullQualifier, sortOrderings);
		} else {
			results = remarks();
			if (qualifier != null) {
				results = (NSArray<at.eofjpa.model.Remark>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
			}
			if (sortOrderings != null) {
				results = (NSArray<at.eofjpa.model.Remark>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
			}
		}
		return results;
	}
  
	public void addToRemarks(at.eofjpa.model.Remark object) {
		addToRemarksRelationship(object);
	}

	public void removeFromRemarks(at.eofjpa.model.Remark object) {
		removeFromRemarksRelationship(object);
	}

	public void addToRemarksRelationship(at.eofjpa.model.Remark object) {
		object.setPostRelationship((Post)this);
	}

	public void removeFromRemarksRelationship(at.eofjpa.model.Remark object) {
		object.setPostRelationship(null);
	}

	public at.eofjpa.model.Remark createRemarksRelationship() {
		at.eofjpa.model.Remark eo = (at.eofjpa.model.Remark)JPAUtilities.createAndInsertInstance(editingContext(), at.eofjpa.model.Remark.ENTITY_DEF);
		eo.setPostRelationship((Post)this);
		return eo;
	}

	public void deleteRemarksRelationship(at.eofjpa.model.Remark object) {
		object.setPostRelationship(null);
		editingContext().deleteObject(object);
	}

	public void deleteAllRemarksRelationships() {
		Enumeration<at.eofjpa.model.Remark> objects = remarks().immutableClone().objectEnumerator();
		while (objects.hasMoreElements()) {
			deleteRemarksRelationship(objects.nextElement());
		}
	}


	public static Post createPost(EOEditingContext editingContext, Integer id
, Integer nr
, String text
, at.eofjpa.model.User user) {
		Post eo = (Post) JPAUtilities.createAndInsertInstance(editingContext, _Post.ENTITY_DEF);    
		eo.setId(id);
		eo.setNr(nr);
		eo.setText(text);
		eo.setUserRelationship(user);
		return eo;
	}

  public static ERXFetchSpecification<Post> fetchSpec() {
    return new ERXFetchSpecification<Post>(_Post.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<Post> fetchAllPosts(EOEditingContext ec) {
	  JPAEditingContext jpaEc = (JPAEditingContext)ec;
	  List<PostEntity> results = new PostDAO(jpaEc.getEm()).fetchAllPosts();
	  return jpaEc.getEnterpriseObjects(Post.ENTITY_DEF, results);
  }

  public static NSArray<Post> fetchAllPosts(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _Post.fetchPosts(editingContext, null, sortOrderings);
  }

  public static NSArray<Post> fetchPosts(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<Post> fetchSpec = new ERXFetchSpecification<Post>(_Post.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<Post> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static Post fetchPost(EOEditingContext editingContext, String keyName, Object value) {
    return _Post.fetchPost(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Post fetchPost(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<Post> eoObjects = _Post.fetchPosts(editingContext, qualifier, null);
    Post eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Post that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Post fetchRequiredPost(EOEditingContext editingContext, String keyName, Object value) {
    return _Post.fetchRequiredPost(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Post fetchRequiredPost(EOEditingContext editingContext, EOQualifier qualifier) {
    Post eoObject = _Post.fetchPost(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Post that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

	public static Post localInstanceIn(EOEditingContext ec, Post eo) {
		JPAEditingContext jpaEc = (JPAEditingContext) ec;
		return jpaEc.getEnterpriseObject(ENTITY_DEF, jpaEc.getEm().merge(eo.getEntity()));
	}

	public static NSArray<at.eofjpa.model.Post> fetchPostsForUser(EOEditingContext ec, NSDictionary<String, Object> bindings) {
		return fetchPostsForUser(ec
, (at.eofjpa.model.User)bindings.get("user"));
	}
  
	public static NSArray<at.eofjpa.model.Post> fetchPostsForUser(EOEditingContext ec
 , at.eofjpa.model.User userBinding) {	
		JPAEditingContext jpaEc = (JPAEditingContext)ec;
		List<PostEntity> results = new PostDAO(jpaEc.getEm()).fetchPostsForUser(
userBinding.getEntity());
		NSArray<at.eofjpa.model.Post> resultsEO = new NSMutableArray<>();
		for (PostEntity o : results) {
			at.eofjpa.model.Post result = new at.eofjpa.model.Post();
			resultsEO.add(result);
			result.setEntity(o);
			result.setEc(jpaEc);
		}
		return resultsEO;
	}
  
	public static NSArray<NSDictionary> fetchRawDistinctTextForUser(EOEditingContext ec, NSDictionary<String, Object> bindings) {
		return fetchRawDistinctTextForUser(ec
, (at.eofjpa.model.User)bindings.get("user"));
	}
  
	public static NSArray<NSDictionary> fetchRawDistinctTextForUser(EOEditingContext ec
 , at.eofjpa.model.User userBinding) {	
		JPAEditingContext jpaEc = (JPAEditingContext)ec;
		List<Map<String, Object>> results = new PostDAO(jpaEc.getEm()).fetchRawDistinctTextForUser(
userBinding.getEntity());
		NSArray<NSDictionary> nsResults = new NSMutableArray<>();
		for (Map<String, Object> result : results) {
			NSDictionary nsResult = new NSMutableDictionary();
			nsResults.add(nsResult);
			for (String key : result.keySet()) {
				nsResult.takeValueForKey(result.get(key), key);
			}
		}
		return nsResults;
	}
  
	public static NSArray<NSDictionary> fetchRawForUserAndNrDesc(EOEditingContext ec, NSDictionary<String, Object> bindings) {
		return fetchRawForUserAndNrDesc(ec
, (Integer)bindings.get("nrMax"), (Integer)bindings.get("nrMin"), (at.eofjpa.model.User)bindings.get("user"));
	}
  
	public static NSArray<NSDictionary> fetchRawForUserAndNrDesc(EOEditingContext ec
 , Integer nrMaxBinding , Integer nrMinBinding , at.eofjpa.model.User userBinding) {	
		JPAEditingContext jpaEc = (JPAEditingContext)ec;
		List<Map<String, Object>> results = new PostDAO(jpaEc.getEm()).fetchRawForUserAndNrDesc(
nrMaxBinding, nrMinBinding, userBinding.getEntity());
		NSArray<NSDictionary> nsResults = new NSMutableArray<>();
		for (Map<String, Object> result : results) {
			NSDictionary nsResult = new NSMutableDictionary();
			nsResults.add(nsResult);
			for (String key : result.keySet()) {
				nsResult.takeValueForKey(result.get(key), key);
			}
		}
		return nsResults;
	}
  
	@StaticMetamodel(PostEntity.class)
	public static class PostEntity_ extends JPAEntity_ {
		
		public static volatile SingularAttribute<Post, Integer> nr;
		public static volatile SingularAttribute<Post, String> text;
		public static volatile SingularAttribute<Post, _User.UserEntity> user;
		public static volatile ListAttribute<Post, _Remark.RemarkEntity> remarks;
	}

	@Entity
	@Table(name = "Post")
	public static class PostEntity extends JPAEntityImpl {

		@Column(name = "nr", nullable = false)
		@NotNull
		private Integer nr;

		@Column(name = "text", nullable = false)
		@NotNull
		private String text;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "userID", nullable = false)
		@NotNull
		private _User.UserEntity user;

		@OneToMany(mappedBy="post", fetch = FetchType.LAZY)
		private List<_Remark.RemarkEntity> remarks = new ArrayList<>();

		public Integer getNr() {
			return nr;
		}
		public void setNr(Integer nr) {
			this.nr = nr;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public _User.UserEntity getUser() {
			return user;
		}
		public void setUser(_User.UserEntity user) {
			this.user = user;
		}
		public List<_Remark.RemarkEntity> getRemarks() {
			return remarks;
		}
		public void setRemarks(List<_Remark.RemarkEntity> remarks) {
			this.remarks = remarks;
		}
	}

	public static class PostDAO {
		
		private EntityManager em;
		public PostDAO(EntityManager em) {
			this.em = em;
		}
		
		public List<PostEntity> fetchAllPosts() {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<PostEntity> q = cb.createQuery(PostEntity.class);
			Root<PostEntity> r = q.from(PostEntity.class);
			q.select(r);
			TypedQuery<PostEntity> tq = em.createQuery(q);
			return tq.getResultList();
		}
		
		public List<PostEntity> fetchPostsForUser(
				at.eofjpa.model._User.UserEntity user
				) {
			String emfQuery = "user = $user";
			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(emfQuery, null);
			NSMutableDictionary<String, Object> bindings = new NSMutableDictionary<String, Object>();
			if (user != null) {
				bindings.takeValueForKey(":user", "user");
			}
			EOQualifier qualforBindings = qualifier.qualifierWithBindings(bindings, false);
			String jpqlWhere = qualforBindings.toString();
			jpqlWhere = jpqlWhere.replace("':user'", ":user");
			for (Object key : qualforBindings.allQualifierKeys()) {
				jpqlWhere = jpqlWhere.replace("("+(String)key, "(e." + (String)key);
			}
			String select = "select";
			select = select + " e";
			String jpqlSortOrderings = "";
			jpqlSortOrderings += " e.nr asc ";
			if (!jpqlSortOrderings.isEmpty()) {
				jpqlSortOrderings = " ORDER BY " + jpqlSortOrderings;
			}
			String jpqlQuery = select +" from _Post$PostEntity e where " + jpqlWhere + jpqlSortOrderings;
			TypedQuery< PostEntity> query = em.createQuery(jpqlQuery, PostEntity.class);
			if (user != null) {
				query.setParameter("user", user);
			}
			return query.getResultList();
		}

		public List<Map<String, Object>> fetchRawDistinctTextForUser(
				at.eofjpa.model._User.UserEntity user
				) {
			String emfQuery = "user = $user";
			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(emfQuery, null);
			NSMutableDictionary<String, Object> bindings = new NSMutableDictionary<String, Object>();
			if (user != null) {
				bindings.takeValueForKey(":user", "user");
			}
			EOQualifier qualforBindings = qualifier.qualifierWithBindings(bindings, false);
			String jpqlWhere = qualforBindings.toString();
			jpqlWhere = jpqlWhere.replace("':user'", ":user");
			for (Object key : qualforBindings.allQualifierKeys()) {
				jpqlWhere = jpqlWhere.replace("("+(String)key, "(e." + (String)key);
			}
			String select = "select";
			select = select + " distinct";
			select = select + " e.text";
			String jpqlSortOrderings = "";
			jpqlSortOrderings += " e.text asc ";
			if (!jpqlSortOrderings.isEmpty()) {
				jpqlSortOrderings = " ORDER BY " + jpqlSortOrderings;
			}
			String jpqlQuery = select +" from _Post$PostEntity e where " + jpqlWhere + jpqlSortOrderings;
			TypedQuery<  Object> query = em.createQuery(jpqlQuery,  Object.class);
			if (user != null) {
				query.setParameter("user", user);
			}
			List<Object> rawDataList = query.getResultList();
			List<Map<String, Object>> results = new ArrayList<>(rawDataList.size());
			for (Object result :rawDataList) {
				Map<String, Object> resultMap = new HashMap<>();
				results.add(resultMap);
				resultMap.put("text", JPAUtilities.convertRawData(result));
			}
			return results;
		}

		public List<Map<String, Object>> fetchRawForUserAndNrDesc(
				Integer nrMax
				, Integer nrMin
				, at.eofjpa.model._User.UserEntity user
				) {
			String emfQuery = "user = $user and nr >= $nrMin and nr <= $nrMax";
			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(emfQuery, null);
			NSMutableDictionary<String, Object> bindings = new NSMutableDictionary<String, Object>();
			if (nrMax != null) {
				bindings.takeValueForKey(":nrMax", "nrMax");
			}
			if (nrMin != null) {
				bindings.takeValueForKey(":nrMin", "nrMin");
			}
			if (user != null) {
				bindings.takeValueForKey(":user", "user");
			}
			EOQualifier qualforBindings = qualifier.qualifierWithBindings(bindings, false);
			String jpqlWhere = qualforBindings.toString();
			jpqlWhere = jpqlWhere.replace("':nrMax'", ":nrMax");
			jpqlWhere = jpqlWhere.replace("':nrMin'", ":nrMin");
			jpqlWhere = jpqlWhere.replace("':user'", ":user");
			for (Object key : qualforBindings.allQualifierKeys()) {
				jpqlWhere = jpqlWhere.replace("("+(String)key, "(e." + (String)key);
			}
			String select = "select";
			select = select + " e.nr";
			select = select + ", e.text";
			select = select + ", e.user.firstname";
			String jpqlSortOrderings = "";
			jpqlSortOrderings += " e.nr desc ";
			if (!jpqlSortOrderings.isEmpty()) {
				jpqlSortOrderings = " ORDER BY " + jpqlSortOrderings;
			}
			String jpqlQuery = select +" from _Post$PostEntity e where " + jpqlWhere + jpqlSortOrderings;
			TypedQuery< Object[]> query = em.createQuery(jpqlQuery, Object[].class);
			if (nrMax != null) {
				query.setParameter("nrMax", nrMax);
			}
			if (nrMin != null) {
				query.setParameter("nrMin", nrMin);
			}
			if (user != null) {
				query.setParameter("user", user);
			}
			List<Object[]> rawDataList = query.getResultList();
			List<Map<String, Object>> results = new ArrayList<>(rawDataList.size());
			int idx;
			for (Object[] result :rawDataList) {
				idx = 0;
				Map<String, Object> resultMap = new HashMap<>();
				results.add(resultMap);
				resultMap.put("nr", JPAUtilities.convertRawData(result[idx]));
				idx++;
				resultMap.put("text", JPAUtilities.convertRawData(result[idx]));
				idx++;
				resultMap.put("user.firstname", JPAUtilities.convertRawData(result[idx]));
				idx++;
			}
			return results;
		}

	}
}
