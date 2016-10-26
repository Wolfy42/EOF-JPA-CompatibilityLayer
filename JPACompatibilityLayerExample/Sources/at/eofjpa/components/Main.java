package at.eofjpa.components;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import at.eofjpa.model.User;

public class Main extends BaseComponent {

	private static final long serialVersionUID = 1L;

	public Main(WOContext context) {
		super(context);
	}

	public NSArray<User> userList;
	public User userItem;
	
	public String error;
	
	@Override
	public void awake() {
		super.awake();
		error = null;
	}
	
	public void init() {
		refetchUsers();
	}
	
	public NSArray<User> getUserList() {
		if (userList == null) {
			refetchUsers();
		}
		return userList;
	}

	private void refetchUsers() {
		userList = User.fetchAllUsers(ec());
	}

	private EOEditingContext ec() {
		// just for demonstration purposes. Logic in a page should be in a Page-EditingContext
		return session().defaultEditingContext();
	}
	
	public WOActionResults addUser() {
		User user = new User();
		ec().insertObject(user);
		userList.add(user);
		return null;
	}
	
	public WOActionResults save() {
		try {
			ec().saveChanges();
		} catch (Exception e) {
			// just for demonstration purpose. log4j would be requried...
			e.printStackTrace();
			error = e.getMessage();
		}
		return null;
	}
	
	public WOActionResults deleteUser() {
		ec().deleteObject(userItem);
		userList.remove(userItem);
		return null;
	}
	
	public WOActionResults revert() {
		ec().revert();
		refetchUsers();
		return null;
	}
	
}
