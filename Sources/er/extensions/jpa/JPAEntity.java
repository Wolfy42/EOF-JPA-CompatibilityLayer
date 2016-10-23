package er.extensions.jpa;

import java.io.Serializable;

public interface JPAEntity extends Serializable {

	public Integer getId();
	
	public void setId(Integer id);
}
