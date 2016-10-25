package er.extensions.jpa;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

@MappedSuperclass
public class JPAEntityImpl implements JPAEntity {

	private static final long serialVersionUID = 1L;

	@TableGenerator(name = "EO_PK_TABLE_GEN", table = "EO_PK_TABLE", 
					pkColumnName = "NAME", valueColumnName = "PK", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "EO_PK_TABLE_GEN")
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
}
