// DO NOT EDIT.  Make changes to ${entity.classNameWithOptionalPackage}.java instead.
#if ($entity.superclassPackageName)
package $entity.superclassPackageName;

#end
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
public abstract class ${entity.prefixClassNameWithoutPackage} extends #if ($entity.parentClassNameSet)${entity.parentClassName}#elseif ($entity.partialEntitySet)er.extensions.partials.ERXPartial<${entity.partialEntity.className}>#elseif ($entity.parentSet)${entity.parent.classNameWithDefault}#elseif ($EOGenericRecord)${EOGenericRecord}#else JPAEnterpriseObject<${entity.prefixClassNameWithoutPackage}.${entity.classNameWithoutPackage}Entity>#end {
#if ($entity.partialEntitySet)
	public static final String ENTITY_NAME = "$entity.partialEntity.name";
#else
	public static final String ENTITY_NAME = "$entity.classNameWithoutPackage";
	public static final JPAEntityDef ENTITY_DEF = new JPAEntityDef(${entity.classNameWithoutPackage}.class, ${entity.classNameWithoutPackage}Entity.class);
	static {
		addEntityDef(ENTITY_NAME, ENTITY_DEF);
	}
#end

	// Attribute Keys
#foreach ($attribute in $entity.sortedClassAttributes)
	public static final ERXKey<$attribute.javaClassName> ${attribute.uppercaseUnderscoreName} = new ERXKey<$attribute.javaClassName>("$attribute.name");
#end
	// Relationship Keys
#foreach ($relationship in $entity.sortedClassRelationships)
	public static final ERXKey<$relationship.actualDestination.classNameWithDefault> ${relationship.uppercaseUnderscoreName} = new ERXKey<$relationship.actualDestination.classNameWithDefault>("$relationship.name");
#end

	// Attributes
#foreach ($attribute in $entity.sortedClassAttributes)
	public static final String ${attribute.uppercaseUnderscoreName}_KEY = ${attribute.uppercaseUnderscoreName}.key();
#end
	// Relationships
#foreach ($relationship in $entity.sortedClassRelationships)
	public static final String ${relationship.uppercaseUnderscoreName}_KEY = ${relationship.uppercaseUnderscoreName}.key();
#end

	private static Logger LOG = Logger.getLogger(${entity.prefixClassNameWithoutPackage}.class);

	// Relationships Enterprise-Objects
#foreach ($relationship in $entity.sortedClassToOneRelationships)
#if (!$relationship.inherited) 
	private ${relationship.actualDestination.classNameWithDefault} ${relationship.name};
#end
#end
#foreach ($relationship in $entity.sortedClassToManyRelationships)
#if (!$relationship.inherited) 
	private NSArray<${relationship.actualDestination.classNameWithDefault}> ${relationship.name};
#end
#end

	protected Class<? extends JPAEntity> getJPAEntityClass() {
		return ${entity.classNameWithoutPackage}Entity.class;
	}

	protected void createJPAEntity() {
		entity = new ${entity.classNameWithoutPackage}Entity();
	}

	protected void resetEnterpriseObject() {
#foreach ($relationship in $entity.sortedClassToOneRelationships)
#if (!$relationship.inherited) 
		reset${relationship.capitalizedName}();
#end
#end
#foreach ($relationship in $entity.sortedClassToManyRelationships)
#if (!$relationship.inherited) 
		reset${relationship.capitalizedName}();
#end
#end
	}

#foreach ($relationship in $entity.sortedClassToOneRelationships)
#if (!$relationship.inherited) 
	protected void reset${relationship.capitalizedName}() {
		${relationship.name} = null;
	}
	
#end
#end
#foreach ($relationship in $entity.sortedClassToManyRelationships)
#if (!$relationship.inherited) 
	protected void reset${relationship.capitalizedName}() {
		${relationship.name} = null;
	}
	
#end
#end
#if (!$entity.partialEntitySet)
	public $entity.classNameWithOptionalPackage localInstanceIn(EOEditingContext ec) {
		JPAEditingContext jpaEc = (JPAEditingContext) ec;
		return jpaEc.getEnterpriseObject(ENTITY_DEF, jpaEc.getEm().merge(getEntity()));
	}

#end
#foreach ($attribute in $entity.sortedClassAttributes)
#if (!$attribute.inherited)
#if ($attribute.userInfo.ERXConstantClassName)
  public $attribute.userInfo.ERXConstantClassName ${attribute.name}() {
    Number value = (Number)storedValueForKey(${entity.prefixClassNameWithoutPackage}.${attribute.uppercaseUnderscoreName}_KEY);
    return ($attribute.userInfo.ERXConstantClassName)value;
  }

  public void set${attribute.capitalizedName}($attribute.userInfo.ERXConstantClassName value) {
    takeStoredValueForKey(value, ${entity.prefixClassNameWithoutPackage}.${attribute.uppercaseUnderscoreName}_KEY);
  }
#else
	public $attribute.javaClassName ${attribute.name}() {
		return entity.get${attribute.capitalizedName}();
	}

	public void set${attribute.capitalizedName}($attribute.javaClassName value) {
		entity.set${attribute.capitalizedName}(value);
	}
#end

#end
#end
#foreach ($relationship in $entity.sortedClassToOneRelationships)
#if (!$relationship.inherited) 
	public ${relationship.actualDestination.classNameWithDefault} ${relationship.name}() {
		return get${relationship.capitalizedName}();
	}
	
	public ${relationship.actualDestination.classNameWithDefault} get${relationship.capitalizedName}() {
		if (${relationship.name} == null) {
			${relationship.name} = editingContext().getEnterpriseObject(${relationship.actualDestination.classNameWithDefault}.ENTITY_DEF, entity.get${relationship.capitalizedName}());
		}
		return ${relationship.name};
	}
	
	public void set${relationship.capitalizedName}(${relationship.actualDestination.classNameWithDefault} ${relationship.name}) {
		set${relationship.capitalizedName}Relationship(${relationship.name});
	}
  
	public void set${relationship.capitalizedName}Relationship($relationship.actualDestination.classNameWithDefault value) {
		if (value != null) {
			entity.set${relationship.capitalizedName}(value.getEntity());
#if ($relationship.inverseRelationship.classProperty)
			value.getEntity().get${relationship.inverseRelationship.capitalizedName}().add(entity);
			value.reset${relationship.inverseRelationship.capitalizedName}();
#end
		} else {
			entity.set${relationship.capitalizedName}(null);
#if ($relationship.inverseRelationship.classProperty)
			if (${relationship.name} != null) {
				${relationship.name}.getEntity().get${relationship.inverseRelationship.capitalizedName}().remove(entity);
			}
#end
		}
		reset${relationship.capitalizedName}();
	}
  
#end
#end
#foreach ($relationship in $entity.sortedClassToManyRelationships)
#if (!$relationship.inherited) 
	public NSArray<${relationship.actualDestination.classNameWithDefault}> ${relationship.name}() {
		if (${relationship.name} == null) {
			${relationship.name} = editingContext().getEnterpriseObjects(${relationship.actualDestination.classNameWithDefault}.ENTITY_DEF, entity.get${relationship.capitalizedName}());
		}
		return ${relationship.name};
	}

#if (!$relationship.inverseRelationship || $relationship.flattened || !$relationship.inverseRelationship.classProperty)
	public NSArray<${relationship.actualDestination.classNameWithDefault}> ${relationship.name}(EOQualifier qualifier) {
		return ${relationship.name}(qualifier, null);
	}
#else
	public NSArray<${relationship.actualDestination.classNameWithDefault}> ${relationship.name}(EOQualifier qualifier) {
		return ${relationship.name}(qualifier, null, false);
	}

	public NSArray<${relationship.actualDestination.classNameWithDefault}> ${relationship.name}(EOQualifier qualifier, boolean fetch) {
		return ${relationship.name}(qualifier, null, fetch);
	}
#end

	public NSArray<${relationship.actualDestination.classNameWithDefault}> ${relationship.name}(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings#if ($relationship.inverseRelationship && !$relationship.flattened && $relationship.inverseRelationship.classProperty), boolean fetch#end) {
		NSArray<${relationship.actualDestination.classNameWithDefault}> results;
#if ($relationship.inverseRelationship && !$relationship.flattened && $relationship.inverseRelationship.classProperty)
		if (fetch) {
			EOQualifier fullQualifier;
#if (${relationship.actualDestination.genericRecord})
			EOQualifier inverseQualifier = new EOKeyValueQualifier("${relationship.inverseRelationship.name}", EOQualifier.QualifierOperatorEqual, this);
#else
			EOQualifier inverseQualifier = new EOKeyValueQualifier(${relationship.actualDestination.classNameWithDefault}.${relationship.inverseRelationship.uppercaseUnderscoreName}_KEY, EOQualifier.QualifierOperatorEqual, this);
#end
			if (qualifier == null) {
				fullQualifier = inverseQualifier;
			} else {
				NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
				qualifiers.addObject(qualifier);
				qualifiers.addObject(inverseQualifier);
				fullQualifier = new EOAndQualifier(qualifiers);
			}
#if (${relationship.actualDestination.genericRecord})
			EOFetchSpecification fetchSpec = new EOFetchSpecification("${relationship.actualDestination.name}", qualifier, sortOrderings);
			fetchSpec.setIsDeep(true);
			results = (NSArray<${relationship.actualDestination.classNameWithDefault}>)editingContext().objectsWithFetchSpecification(fetchSpec);
#else
			results = ${relationship.actualDestination.classNameWithDefault}.fetch${relationship.actualDestination.pluralName}(editingContext(), fullQualifier, sortOrderings);
#end
		} else {
#end
			results = ${relationship.name}();
			if (qualifier != null) {
				results = (NSArray<${relationship.actualDestination.classNameWithDefault}>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
			}
			if (sortOrderings != null) {
				results = (NSArray<${relationship.actualDestination.classNameWithDefault}>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
			}
#if ($relationship.inverseRelationship && !$relationship.flattened && $relationship.inverseRelationship.classProperty)
		}
#end
		return results;
	}
  
	public void addTo${relationship.capitalizedName}($relationship.actualDestination.classNameWithDefault object) {
		addTo${relationship.capitalizedName}Relationship(object);
	}

	public void removeFrom${relationship.capitalizedName}($relationship.actualDestination.classNameWithDefault object) {
		removeFrom${relationship.capitalizedName}Relationship(object);
	}

	public void addTo${relationship.capitalizedName}Relationship($relationship.actualDestination.classNameWithDefault object) {
		object.set${relationship.inverseRelationship.capitalizedName}Relationship((${entity.classNameWithoutPackage})this);
	}

	public void removeFrom${relationship.capitalizedName}Relationship($relationship.actualDestination.classNameWithDefault object) {
		object.set${relationship.inverseRelationship.capitalizedName}Relationship(null);
	}

	public $relationship.actualDestination.classNameWithDefault create${relationship.capitalizedName}Relationship() {
		${relationship.actualDestination.classNameWithDefault} eo = (${relationship.actualDestination.classNameWithDefault})JPAUtilities.createAndInsertInstance(editingContext(), ${relationship.actualDestination.classNameWithDefault}.ENTITY_DEF);
		eo.set${relationship.inverseRelationship.capitalizedName}Relationship((${entity.classNameWithoutPackage})this);
		return eo;
	}

	public void delete${relationship.capitalizedName}Relationship($relationship.actualDestination.classNameWithDefault object) {
		object.set${relationship.inverseRelationship.capitalizedName}Relationship(null);
		editingContext().deleteObject(object);
	}

	public void deleteAll${relationship.capitalizedName}Relationships() {
		Enumeration<$relationship.actualDestination.classNameWithDefault> objects = ${relationship.name}().immutableClone().objectEnumerator();
		while (objects.hasMoreElements()) {
			delete${relationship.capitalizedName}Relationship(objects.nextElement());
		}
	}

#end
#end

	public #if (!$entity.partialEntitySet)static #end${entity.classNameWithOptionalPackage}#if (!$entity.partialEntitySet) create#else init#end${entity.name}(EOEditingContext editingContext#foreach ($attribute in $entity.sortedClassAttributes)
#if (!$attribute.allowsNull)
#set ($restrictingQualifierKey = 'false')
#foreach ($qualifierKey in $entity.restrictingQualifierKeys)#if ($attribute.name == $qualifierKey)#set ($restrictingQualifierKey = 'true')#end#end
#if ($restrictingQualifierKey == 'false')
#if ($attribute.userInfo.ERXConstantClassName), ${attribute.userInfo.ERXConstantClassName}#else, ${attribute.javaClassName}#end ${attribute.name}
#end
#end
#end
#foreach ($relationship in $entity.sortedClassToOneRelationships)
#if ($relationship.mandatory && !($relationship.ownsDestination && $relationship.propagatesPrimaryKey)), ${relationship.actualDestination.classNameWithDefault} ${relationship.name}#end
#end
) {
		${entity.classNameWithOptionalPackage} eo = (${entity.classNameWithOptionalPackage})#if ($entity.partialEntitySet)this;#else JPAUtilities.createAndInsertInstance(editingContext, ${entity.prefixClassNameWithoutPackage}.ENTITY_DEF);#end
    
#foreach ($attribute in $entity.sortedClassAttributes)
#if (!$attribute.allowsNull)
#set ($restrictingQualifierKey = 'false')
#foreach ($qualifierKey in $entity.restrictingQualifierKeys) 
#if ($attribute.name == $qualifierKey)
#set ($restrictingQualifierKey = 'true')
#end
#end
#if ($restrictingQualifierKey == 'false')
		eo.set${attribute.capitalizedName}(${attribute.name});
#end
#end
#end
#foreach ($relationship in $entity.sortedClassToOneRelationships)
#if ($relationship.mandatory && !($relationship.ownsDestination && $relationship.propagatesPrimaryKey))
		eo.set${relationship.capitalizedName}Relationship(${relationship.name});
#end
#end
		return eo;
	}
#if (!$entity.partialEntitySet)

#if ($entity.parentSet)
  public static ERXFetchSpecification<${entity.classNameWithOptionalPackage}> fetchSpecFor${entity.name}() {
    return new ERXFetchSpecification<${entity.classNameWithOptionalPackage}>(${entity.prefixClassNameWithoutPackage}.ENTITY_NAME, null, null, false, true, null);
  }
#else
  public static ERXFetchSpecification<${entity.classNameWithOptionalPackage}> fetchSpec() {
    return new ERXFetchSpecification<${entity.classNameWithOptionalPackage}>(${entity.prefixClassNameWithoutPackage}.ENTITY_NAME, null, null, false, true, null);
  }
#end

  public static NSArray<${entity.classNameWithOptionalPackage}> fetchAll${entity.pluralName}(EOEditingContext ec) {
	  JPAEditingContext jpaEc = (JPAEditingContext)ec;
	  List<${entity.classNameWithoutPackage}Entity> results = new ${entity.classNameWithoutPackage}DAO(jpaEc.getEm()).fetchAll${entity.pluralName}();
	  return jpaEc.getEnterpriseObjects(${entity.classNameWithOptionalPackage}.ENTITY_DEF, results);
  }

  public static NSArray<${entity.classNameWithOptionalPackage}> fetchAll${entity.pluralName}(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return ${entity.prefixClassNameWithoutPackage}.fetch${entity.pluralName}(editingContext, null, sortOrderings);
  }

  public static NSArray<${entity.classNameWithOptionalPackage}> fetch${entity.pluralName}(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<${entity.classNameWithOptionalPackage}> fetchSpec = new ERXFetchSpecification<${entity.classNameWithOptionalPackage}>(${entity.prefixClassNameWithoutPackage}.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<${entity.classNameWithOptionalPackage}> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static ${entity.classNameWithOptionalPackage} fetch${entity.name}(EOEditingContext editingContext, String keyName, Object value) {
    return ${entity.prefixClassNameWithoutPackage}.fetch${entity.name}(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ${entity.classNameWithOptionalPackage} fetch${entity.name}(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<${entity.classNameWithOptionalPackage}> eoObjects = ${entity.prefixClassNameWithoutPackage}.fetch${entity.pluralName}(editingContext, qualifier, null);
    ${entity.classNameWithOptionalPackage} eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ${entity.name} that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ${entity.classNameWithOptionalPackage} fetchRequired${entity.name}(EOEditingContext editingContext, String keyName, Object value) {
    return ${entity.prefixClassNameWithoutPackage}.fetchRequired${entity.name}(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ${entity.classNameWithOptionalPackage} fetchRequired${entity.name}(EOEditingContext editingContext, EOQualifier qualifier) {
    ${entity.classNameWithOptionalPackage} eoObject = ${entity.prefixClassNameWithoutPackage}.fetch${entity.name}(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ${entity.name} that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

	public static ${entity.classNameWithOptionalPackage} localInstanceIn(EOEditingContext ec, ${entity.classNameWithOptionalPackage} eo) {
		JPAEditingContext jpaEc = (JPAEditingContext) ec;
		return jpaEc.getEnterpriseObject(ENTITY_DEF, jpaEc.getEm().merge(eo.getEntity()));
	}

#end
#foreach ($fetchSpecification in $entity.sortedFetchSpecs)
#if (true || $fetchSpecification.distinctBindings.size() > 0)
	public static NSArray#if ($fetchSpecification.fetchEnterpriseObjects)<${entity.className}>#else<NSDictionary>#end fetch${fetchSpecification.capitalizedName}(EOEditingContext ec, NSDictionary<String, Object> bindings) {
		return fetch${fetchSpecification.capitalizedName}(ec
#foreach ($binding in $fetchSpecification.distinctBindings)
, (${binding.attributePath.childClassName})bindings.get("${binding.name}")#end);
	}
  
#end
	public static NSArray#if ($fetchSpecification.fetchEnterpriseObjects)<${entity.className}>#else<NSDictionary>#end fetch${fetchSpecification.capitalizedName}(EOEditingContext ec
#foreach ($binding in $fetchSpecification.distinctBindings)
 , ${binding.attributePath.childClassName} ${binding.name}Binding#end) {	
		JPAEditingContext jpaEc = (JPAEditingContext)ec;
#if ($fetchSpecification.fetchEnterpriseObjects)
		List<${entity.classNameWithoutPackage}Entity> results = new ${entity.classNameWithoutPackage}DAO(jpaEc.getEm()).fetch${fetchSpecification.capitalizedName}(
#set($sep = "")
#foreach ($binding in $fetchSpecification.distinctBindings)
$sep${binding.name}Binding#if ($binding.attributePath.childClassName.contains(".model.")).getEntity()#end#set($sep = ", ")#end);
		NSArray<${entity.className}> resultsEO = new NSMutableArray<>();
		for (${entity.classNameWithoutPackage}Entity o : results) {
			${entity.className} result = new ${entity.className}();
			resultsEO.add(result);
			result.setEntity(o);
			result.setEc(jpaEc);
		}
		return resultsEO;
#else
		List<Map<String, Object>> results = new ${entity.classNameWithoutPackage}DAO(jpaEc.getEm()).fetch${fetchSpecification.capitalizedName}(
#set($sep = "")
#foreach ($binding in $fetchSpecification.distinctBindings)
$sep${binding.name}Binding#if ($binding.attributePath.childClassName.contains(".model.")).getEntity()#end#set($sep = ", ")#end);
		NSArray<NSDictionary> nsResults = new NSMutableArray<>();
		for (Map<String, Object> result : results) {
			NSDictionary nsResult = new NSMutableDictionary();
			nsResults.add(nsResult);
			for (String key : result.keySet()) {
				nsResult.takeValueForKey(result.get(key), key);
			}
		}
		return nsResults;
#end
	}
  
#end
	@StaticMetamodel(${entity.classNameWithoutPackage}Entity.class)
	public static class ${entity.classNameWithoutPackage}Entity_#if (${entity.parentSet}) extends ${entity.parent.prefixClassNameWithoutPackage}.${entity.parent.classNameWithoutPackage}Entity_#else extends JPAEntity_#end {
		
#foreach ($attribute in $entity.primaryKeyAttributes)
#if (!$attribute.inherited && !${attribute.name} == "id")
		public static volatile SingularAttribute<$entity.classNameWithoutPackage, $attribute.javaClassName> $attribute.name;
#end
#end
#foreach ($attribute in $entity.sortedClassAttributes)
#if (!$entity.primaryKeyAttributes.contains($attribute))
#if (!$attribute.inherited)
		public static volatile SingularAttribute<$entity.classNameWithoutPackage, $attribute.javaClassName> $attribute.name;
#end
#end
#end
#foreach ($relationship in $entity.sortedClassToOneRelationships)
		public static volatile SingularAttribute<$entity.classNameWithoutPackage, ${relationship.actualDestination.prefixClassNameWithoutPackage}.${relationship.actualDestination.classNameWithoutPackage}Entity> $relationship.name;
#end
#foreach ($relationship in $entity.sortedClassToManyRelationships)
		public static volatile ListAttribute<$entity.classNameWithoutPackage, ${relationship.actualDestination.prefixClassNameWithoutPackage}.${relationship.actualDestination.classNameWithoutPackage}Entity> $relationship.name;
#end
	}

	@Entity
	@Table(name = "${entity.externalName}")
	public static#if (${entity.abstractEntity}) abstract#end class ${entity.classNameWithoutPackage}Entity#if (${entity.parentSet}) extends ${entity.parent.prefixClassNameWithoutPackage}.${entity.parent.classNameWithoutPackage}Entity#else extends JPAEntityImpl#end {
#foreach ($attribute in $entity.primaryKeyAttributes)
#if (!$attribute.inherited && !${attribute.name} == "id")

		@Id
		@Column(name = "${attribute.columnName}")
		private $attribute.javaClassName ${attribute.name};
#end
#end
#foreach ($attribute in $entity.sortedClassAttributes)
#if (!$entity.primaryKeyAttributes.contains($attribute))
#if (!$attribute.inherited)

		@Column(name = "${attribute.columnName}"#if (!$attribute.allowsNull), nullable = false#end)
#if ($attribute.javaClassName.equals("NSTimestamp"))
		@Convert(converter = er.extensions.jpa.converter.NSTimestampConverter.class)
#end
#if ($attribute.javaClassName.equals("NSData"))
		@Convert(converter = er.extensions.jpa.converter.NSDataConverter.class)
#end
#if (!$attribute.allowsNull)
		@NotNull
#end
		private $attribute.javaClassName ${attribute.name};
#end
#end
#end
#foreach ($relationship in $entity.sortedClassToOneRelationships)
#if (!$relationship.inherited)

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "#foreach ($join in ${relationship.joins})$join.sourceAttribute.columnName#end"#if ($relationship.isMandatory()), nullable = false#end)
#if ($relationship.isMandatory())
		@NotNull
#end
		private ${relationship.actualDestination.prefixClassNameWithoutPackage}.${relationship.actualDestination.classNameWithoutPackage}Entity $relationship.name;
#end
#end
#foreach ($relationship in $entity.sortedClassToManyRelationships)
#if (!$relationship.inherited)

		@OneToMany(mappedBy="${relationship.inverseRelationship.name}", fetch = FetchType.LAZY)
		private List<${relationship.actualDestination.prefixClassNameWithoutPackage}.${relationship.actualDestination.classNameWithoutPackage}Entity> $relationship.name = new ArrayList<>();
#end
#end

#foreach ($attribute in $entity.primaryKeyAttributes)
#if (!$attribute.inherited && !${attribute.name} == "id")
		public $attribute.javaClassName get${attribute.capitalizedName}() {
			return ${attribute.name};
		}
		public void set${attribute.capitalizedName}($attribute.javaClassName ${attribute.name}) {
			this.${attribute.name} = ${attribute.name};
		}
#end
#end
#foreach ($attribute in $entity.sortedClassAttributes)
#if (!$entity.primaryKeyAttributes.contains($attribute))
#if (!$attribute.inherited)
		public $attribute.javaClassName get${attribute.capitalizedName}() {
			return ${attribute.name};
		}
		public void set${attribute.capitalizedName}($attribute.javaClassName ${attribute.name}) {
			this.${attribute.name} = ${attribute.name};
		}
#end
#end
#end
#foreach ($relationship in $entity.sortedClassToOneRelationships)
#if (!$relationship.inherited) 
		public ${relationship.actualDestination.prefixClassNameWithoutPackage}.${relationship.actualDestination.classNameWithoutPackage}Entity get${relationship.capitalizedName}() {
			return $relationship.name;
		}
		public void set${relationship.capitalizedName}(${relationship.actualDestination.prefixClassNameWithoutPackage}.${relationship.actualDestination.classNameWithoutPackage}Entity ${relationship.name}) {
			this.${relationship.name} = ${relationship.name};
		}
#end
#end
#foreach ($relationship in $entity.sortedClassToManyRelationships)
#if (!$relationship.inherited) 
		public List<${relationship.actualDestination.prefixClassNameWithoutPackage}.${relationship.actualDestination.classNameWithoutPackage}Entity> get${relationship.capitalizedName}() {
			return $relationship.name;
		}
		public void set${relationship.capitalizedName}(List<${relationship.actualDestination.prefixClassNameWithoutPackage}.${relationship.actualDestination.classNameWithoutPackage}Entity> ${relationship.name}) {
			this.${relationship.name} = ${relationship.name};
		}
#end
#end
	}

	public static class ${entity.classNameWithoutPackage}DAO {
		
		private EntityManager em;
		public ${entity.classNameWithoutPackage}DAO(EntityManager em) {
			this.em = em;
		}
		
		public List<${entity.classNameWithoutPackage}Entity> fetchAll${entity.pluralName}() {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<${entity.classNameWithoutPackage}Entity> q = cb.createQuery(${entity.classNameWithoutPackage}Entity.class);
			Root<${entity.classNameWithoutPackage}Entity> r = q.from(${entity.classNameWithoutPackage}Entity.class);
			q.select(r);
			TypedQuery<${entity.classNameWithoutPackage}Entity> tq = em.createQuery(q);
			return tq.getResultList();
		}
		
#foreach ($fetchSpecification in $entity.sortedFetchSpecs)
		public List#if ($fetchSpecification.fetchEnterpriseObjects)<${entity.classNameWithoutPackage}Entity>#else<Map<String, Object>>#end fetch${fetchSpecification.capitalizedName}(
#set($sep = "") 
#foreach ($binding in $fetchSpecification.distinctBindings)
#if ($binding.attributePath.childClassName.contains(".model."))
#set($classname = $binding.attributePath.childClassName)
#set($classnameShort = $classname.substring($classname.lastIndexOf("."), $classname.length()))
#set($classnameWithoutDot = $classnameShort.substring(1))
				$sep$classname.replace($classnameShort, "._")$classnameWithoutDot.${classnameWithoutDot}Entity ${binding.name}
#else
				$sep$binding.attributePath.childClassName ${binding.name}
#end #set($sep = ", ")#end
				) {
			String emfQuery = "$fetchSpecification.qualifier";
			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(emfQuery, null);
#if ($fetchSpecification.distinctBindings.size() > 0)
			NSMutableDictionary<String, Object> bindings = new NSMutableDictionary<String, Object>();
#foreach ($binding in $fetchSpecification.distinctBindings)
			if (${binding.name} != null) {
				bindings.takeValueForKey(":${binding.name}", "${binding.name}");
			}
#end
			EOQualifier qualforBindings = qualifier.qualifierWithBindings(bindings, false);
			String jpqlWhere = qualforBindings.toString();
#foreach ($binding in $fetchSpecification.distinctBindings)
			jpqlWhere = jpqlWhere.replace("':$binding.name'", ":$binding.name");
#end
			for (Object key : qualforBindings.allQualifierKeys()) {
				jpqlWhere = jpqlWhere.replace("("+(String)key, "(e." + (String)key);
			}
			String select = "select";
#if ($fetchSpecification.usesDistinct)
			select = select + " distinct";
#end
#if ($fetchSpecification.fetchEnterpriseObjects)
			select = select + " e";
#else
#set($sep = "")
#foreach ($keyPath in $fetchSpecification.rawRowKeyPaths)
			select = select + "$sep e.$keyPath";
#set($sep = ",")
#end
#end
			String jpqlSortOrderings = "";
#set($sep = "")
#foreach ($sortOrdering in $fetchSpecification.sortOrderings)
			jpqlSortOrderings += "$sep e.${sortOrdering.key}#if(${sortOrdering.isAscending()}) asc #else desc #end";
#set($sep = ",")
#end
			if (!jpqlSortOrderings.isEmpty()) {
				jpqlSortOrderings = " ORDER BY " + jpqlSortOrderings;
			}
			String jpqlQuery = select +" from ${entity.prefixClassNameWithoutPackage}$${entity.classNameWithoutPackage}Entity e where " + jpqlWhere + jpqlSortOrderings;
			TypedQuery<#if ($fetchSpecification.fetchEnterpriseObjects) ${entity.classNameWithoutPackage}Entity#else #if ($fetchSpecification.rawRowKeyPaths.size() > 1)Object[]#else Object#end#end> query = em.createQuery(jpqlQuery,#if ($fetchSpecification.fetchEnterpriseObjects) ${entity.classNameWithoutPackage}Entity.class#else #if ($fetchSpecification.rawRowKeyPaths.size() > 1)Object[]#else Object#end.class#end);
#foreach ($binding in $fetchSpecification.distinctBindings)
			if (${binding.name} != null) {
				query.setParameter("${binding.name}", ${binding.name});
			}
#end
#if ($fetchSpecification.fetchEnterpriseObjects)
			return query.getResultList();
#else
#if ($fetchSpecification.rawRowKeyPaths.size() > 1)
			List<Object[]> rawDataList = query.getResultList();
			List<Map<String, Object>> results = new ArrayList<>(rawDataList.size());
			int idx;
			for (Object[] result :rawDataList) {
				idx = 0;
				Map<String, Object> resultMap = new HashMap<>();
				results.add(resultMap);
#foreach ($keyPath in $fetchSpecification.rawRowKeyPaths)
				resultMap.put("$keyPath", JPAUtilities.convertRawData(result[idx]));
				idx++;
#end			
			}
#else
			List<Object> rawDataList = query.getResultList();
			List<Map<String, Object>> results = new ArrayList<>(rawDataList.size());
			for (Object result :rawDataList) {
				Map<String, Object> resultMap = new HashMap<>();
				results.add(resultMap);
#foreach ($keyPath in $fetchSpecification.rawRowKeyPaths)
				resultMap.put("$keyPath", JPAUtilities.convertRawData(result));
#end
			}
#end
			return results;
#end
#end
		}

#end
	}
}
