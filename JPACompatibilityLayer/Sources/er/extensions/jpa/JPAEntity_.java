package er.extensions.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.dom4j.tree.AbstractEntity;

@StaticMetamodel(JPAEntity.class)
public abstract class JPAEntity_ {

	public static volatile SingularAttribute<AbstractEntity, Integer> id;

}