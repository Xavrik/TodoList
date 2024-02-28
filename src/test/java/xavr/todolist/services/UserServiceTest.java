package xavr.todolist.services;



//import org.junit.jupiter.api.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import xavr.todolist.domain.PlainObjects.UserPojo;
import xavr.todolist.domain.User;
import xavr.todolist.services.interfaces.IUserService;

//import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.junit.Assert.*;



public class UserServiceTest {

    private final String EMAIL = "test@test.com";
    private final String PASSWORD = "password";

    private ApplicationContext applicationContext;
    private IUserService userService;


    @Before
    public void init(){
        this.applicationContext = new ClassPathXmlApplicationContext("/mainTest.xml");
        this.userService =  applicationContext.getBean("userService", IUserService.class);
    }

    @After
    public void cleanDB(){
        userService.getAllUsers().forEach(userPojo -> userService.deleteUser(userPojo.getId()));
    }


    @Test
    public void createUserTest(){
        User newUser = new User();
        newUser.setEmail(EMAIL);
        newUser.setPassword(PASSWORD);

        UserPojo actual =  userService.CreateUser(newUser);

        assertEquals(EMAIL, actual.getEmail());
        assertEquals(PASSWORD, actual.getPassword());
        assertNotNull(actual.getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUniqueUserEmail(){
        User userOne = new User();
        userOne.setEmail(EMAIL);
        userOne.setPassword(PASSWORD);
        userService.CreateUser(userOne);

        User userTwo = new User();
        userTwo.setEmail(EMAIL);
        userTwo.setPassword(PASSWORD);
        userService.CreateUser(userTwo);
    }

    @Test
    public void getUserTest(){
        User newUser = new User();
        newUser.setEmail(EMAIL);
        newUser.setPassword(PASSWORD);

        UserPojo actual =  userService.CreateUser(newUser);
        UserPojo current = userService.getUser(actual.getId());
        assertEquals(actual.getId(), current.getId());
        assertEquals(EMAIL, current.getEmail());
        assertEquals(PASSWORD, current.getPassword());

    }

    @Test
    public void findUserByEmailAndPasswordTest(){
        User newUser = new User();
        newUser.setEmail(EMAIL);
        newUser.setPassword(PASSWORD);

        UserPojo actual =  userService.CreateUser(newUser);
        UserPojo current = userService.findUserByEmailAndPassword(EMAIL, PASSWORD);

        assertEquals(actual.getId(), current.getId());
        assertEquals(EMAIL, current.getEmail());
        assertEquals(PASSWORD, current.getPassword());
    }

    @Test
    public void updateUserTest(){
        User newUser = new User();
        newUser.setEmail(EMAIL);
        newUser.setPassword(PASSWORD);

        User updateUser = new User();
        newUser.setEmail("testDONE@test.com");
        newUser.setPassword("passwordUpdated");

        UserPojo actual =  userService.CreateUser(newUser);
        UserPojo updatedUser = userService.updateUser(updateUser, actual.getId());

        assertEquals(actual.getId(), updatedUser.getId());
        assertEquals("testDONE@test.com", updatedUser.getEmail());
        assertEquals("passwordUpdated", updatedUser.getPassword());
    }


    @Test
    public void deleteUserTest(){
        User newUser = new User();
        newUser.setEmail(EMAIL);
        newUser.setPassword(PASSWORD);

        UserPojo actual =  userService.CreateUser(newUser);

        List<UserPojo> userListAfterCreate = userService.getAllUsers();
        assertEquals(1, userListAfterCreate.size());

        userService.deleteUser(actual.getId());


        List<UserPojo> userListAfterDelete = userService.getAllUsers();
        assertEquals(0, userListAfterDelete.size());
    }
}
