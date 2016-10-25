package er.extensions.jpa;

public class JPAEntityDef {

	private final Class<? extends JPAEnterpriseObject<? extends JPAEntity>> eoEntity;
	private final Class<? extends JPAEntity> jpaEntity;

	public JPAEntityDef(Class<? extends JPAEnterpriseObject<? extends JPAEntity>> eoEntity, Class<? extends JPAEntity> jpaEntity) {
		this.eoEntity = eoEntity;
		this.jpaEntity = jpaEntity;
	}

	public Class<? extends JPAEnterpriseObject<? extends JPAEntity>> getEoEntity() {
		return eoEntity;
	}
	public Class<? extends JPAEntity> getJpaEntity() {
		return jpaEntity;
	}
}
