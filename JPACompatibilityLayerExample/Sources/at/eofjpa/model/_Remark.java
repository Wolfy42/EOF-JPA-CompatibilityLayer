// DO NOT EDIT.  Make changes to Remark.java instead.
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
public abstract class _Remark extends  JPAEnterpriseObject<_Remark.RemarkEntity> {
	public static final String ENTITY_NAME = "Remark";
	public static final JPAEntityDef ENTITY_DEF = new JPAEntityDef(Remark.class, RemarkEntity.class);
	static {
		addEntityDef(ENTITY_NAME, ENTITY_DEF);
	}

	// Attribute Keys
	public static final ERXKey<Integer> ID = new ERXKey<Integer>("id");
	public static final ERXKey<String> TEXT = new ERXKey<String>("text");
	// Relationship Keys
	public static final ERXKey<at.eofjpa.model.Post> POST = new ERXKey<at.eofjpa.model.Post>("post");

	// Attributes
	public static final String ID_KEY = ID.key();
	public static final String TEXT_KEY = TEXT.key();
	// Relationships
	public static final String POST_KEY = POST.key();

	private static Logger LOG = Logger.getLogger(_Remark.class);

	// Relationships Enterprise-Objects
	private at.eofjpa.model.Post post;

	protected Class<? extends JPAEntity> getJPAEntityClass() {
		return RemarkEntity.class;
	}

	protected void createJPAEntity() {
		entity = new RemarkEntity();
	}

	protected void resetEnterpriseObject() {
		resetPost();
	}

	protected void resetPost() {
		post = null;
	}
	
	public Remark localInstanceIn(EOEditingContext ec) {
		JPAEditingContext jpaEc = (JPAEditingContext) ec;
		return jpaEc.getEnterpriseObject(ENTITY_DEF, jpaEc.getEm().merge(getEntity()));
	}

	public Integer id() {
		return entity.getId();
	}

	public void setId(Integer value) {
		entity.setId(value);
	}

	public String text() {
		return entity.getText();
	}

	public void setText(String value) {
		entity.setText(value);
	}

	public at.eofjpa.model.Post post() {
		return getPost();
	}
	
	public at.eofjpa.model.Post getPost() {
		if (post == null) {
			post = editingContext().getEnterpriseObject(at.eofjpa.model.Post.ENTITY_DEF, entity.getPost());
		}
		return post;
	}
	
	public void setPost(at.eofjpa.model.Post post) {
		setPostRelationship(post);
	}
  
	public void setPostRelationship(at.eofjpa.model.Post value) {
		if (value != null) {
			entity.setPost(value.getEntity());
			value.getEntity().getRemarks().add(entity);
			value.resetRemarks();
		} else {
			entity.setPost(null);
			if (post != null) {
				post.getEntity().getRemarks().remove(entity);
			}
		}
		resetPost();
	}
  

	public static Remark createRemark(EOEditingContext editingContext, Integer id
, String text
, at.eofjpa.model.Post post) {
		Remark eo = (Remark) JPAUtilities.createAndInsertInstance(editingContext, _Remark.ENTITY_DEF);    
		eo.setId(id);
		eo.setText(text);
		eo.setPostRelationship(post);
		return eo;
	}

  public static ERXFetchSpecification<Remark> fetchSpec() {
    return new ERXFetchSpecification<Remark>(_Remark.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<Remark> fetchAllRemarks(EOEditingContext ec) {
	  JPAEditingContext jpaEc = (JPAEditingContext)ec;
	  List<RemarkEntity> results = new RemarkDAO(jpaEc.getEm()).fetchAllRemarks();
	  return jpaEc.getEnterpriseObjects(Remark.ENTITY_DEF, results);
  }

  public static NSArray<Remark> fetchAllRemarks(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _Remark.fetchRemarks(editingContext, null, sortOrderings);
  }

  public static NSArray<Remark> fetchRemarks(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<Remark> fetchSpec = new ERXFetchSpecification<Remark>(_Remark.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<Remark> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static Remark fetchRemark(EOEditingContext editingContext, String keyName, Object value) {
    return _Remark.fetchRemark(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Remark fetchRemark(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<Remark> eoObjects = _Remark.fetchRemarks(editingContext, qualifier, null);
    Remark eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Remark that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Remark fetchRequiredRemark(EOEditingContext editingContext, String keyName, Object value) {
    return _Remark.fetchRequiredRemark(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Remark fetchRequiredRemark(EOEditingContext editingContext, EOQualifier qualifier) {
    Remark eoObject = _Remark.fetchRemark(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Remark that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

	public static Remark localInstanceIn(EOEditingContext ec, Remark eo) {
		JPAEditingContext jpaEc = (JPAEditingContext) ec;
		return jpaEc.getEnterpriseObject(ENTITY_DEF, jpaEc.getEm().merge(eo.getEntity()));
	}

	@StaticMetamodel(RemarkEntity.class)
	public static class RemarkEntity_ extends JPAEntity_ {
		
		public static volatile SingularAttribute<Remark, String> text;
		public static volatile SingularAttribute<Remark, _Post.PostEntity> post;
	}

	@Entity
	@Table(name = "Remark")
	public static class RemarkEntity extends JPAEntityImpl {

		@Column(name = "text", nullable = false)
		@NotNull
		private String text;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "postID", nullable = false)
		@NotNull
		private _Post.PostEntity post;

		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public _Post.PostEntity getPost() {
			return post;
		}
		public void setPost(_Post.PostEntity post) {
			this.post = post;
		}
	}

	public static class RemarkDAO {
		
		private EntityManager em;
		public RemarkDAO(EntityManager em) {
			this.em = em;
		}
		
		public List<RemarkEntity> fetchAllRemarks() {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<RemarkEntity> q = cb.createQuery(RemarkEntity.class);
			Root<RemarkEntity> r = q.from(RemarkEntity.class);
			q.select(r);
			TypedQuery<RemarkEntity> tq = em.createQuery(q);
			return tq.getResultList();
		}
		
	}
}
