package at.eofjpa.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSTimestamp;

public class User extends _User {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(User.class);
	
	@Override
	public void init(EOEditingContext ec) {
		super.init(ec);
		
		setCreateDate(new NSTimestamp());
		setTestuser(false);
	}
}
