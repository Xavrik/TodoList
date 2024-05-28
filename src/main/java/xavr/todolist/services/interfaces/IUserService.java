package xavr.todolist.services.interfaces;

import xavr.todolist.domain.PlainObjects.UserPojo;
import xavr.todolist.domain.User;

import java.util.List;

public interface IUserService {
	
	UserPojo CreateUser(User user);
	UserPojo findUserByEmailAndPassword(String email, String password);
	UserPojo getUser(long id);
	List<UserPojo> getAllUsers();
	UserPojo updateUser(User user, long id);
	String deleteUser(long id);
	boolean userExists(String username);

	
	
	

}
