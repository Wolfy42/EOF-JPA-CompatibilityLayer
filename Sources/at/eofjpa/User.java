package at.jpaeof.model;

import org.apache.log4j.Logger;

public class User extends _User{
  private static Logger log = Logger.getLogger(User.class);

	public String description() {
		if (firstname() == null || lastname() == null) {
			return null;
		}
		return firstname() + " " + lastname();
	}
	
	@Override
	public String toString() {
		return description();
	}
}
