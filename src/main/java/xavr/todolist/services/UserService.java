package xavr.todolist.services;



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xavr.todolist.domain.Exceprions.CustomEmptyDataException;
import xavr.todolist.domain.PlainObjects.UserPojo;
import xavr.todolist.domain.User;
import xavr.todolist.repositories.UserRepository;
import xavr.todolist.services.interfaces.IUserService;
import xavr.todolist.utilits.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService implements IUserService {

	private final UserRepository userRepository;
	private final Converter converter;

    public UserService(UserRepository userRepository, Converter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }


    @Override
	@Transactional
	public UserPojo CreateUser(User user) {
		userRepository.save(user);
		return converter.userToPojo(user);
	}

	@Override
	@Transactional(readOnly = true)
	public UserPojo getUser(long id) {
		Optional<User> foundUser = userRepository.findById(id);
		System.out.println("Clear " + foundUser);
		System.out.println("GET "+ foundUser.get());

			return converter.userToPojo(foundUser.get());
	}

	@Override
	@Transactional
	public UserPojo findUserByEmailAndPassword(String email, String password) {
		Optional<User> userOptional = userRepository.findByEmailAndPassword(email, password);
		if (!userOptional.isPresent()) {
			return converter.userToPojo(userOptional.get());
		}else {
//			throw new CustomEmptyDataException("Password or Login incorrect" );
			return null;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<UserPojo> getAllUsers() {
		List<User> listOfUsers = userRepository.findAll();
		return  listOfUsers.stream().map(user -> converter.userToPojo(user)).collect(Collectors.toList());

	}

	@Override
	@Transactional
	public UserPojo updateUser(User source, long id) {
		Optional<User> userForUpdate = userRepository.findById(id);
		if (userForUpdate.isPresent()) {
			User target = userForUpdate.get();
			target.setEmail(source.getEmail());
			target.setPassword(source.getPassword());
			userRepository.save(target);
			return converter.userToPojo(target);
		}else{
			throw new CustomEmptyDataException("unable to update user" );
		}
	}

	@Override
	@Transactional
	public String deleteUser(long id) {
			Optional<User> userForDelete = userRepository.findById(id);
			if (userForDelete.isPresent()) {
				userRepository.delete(userForDelete.get());
				return "User with id: " + id + " deleted successfully";
			}else {
				throw new CustomEmptyDataException("unable to delete user" );
			}

	}

}
