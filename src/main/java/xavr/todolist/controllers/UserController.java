package xavr.todolist.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import xavr.todolist.domain.Exceprions.CustomEmptyDataException;
import xavr.todolist.domain.PlainObjects.UserPojo;
import xavr.todolist.domain.Todo;
import xavr.todolist.domain.User;
import xavr.todolist.security.TokenManager;
import xavr.todolist.security.TokenPayload;
import xavr.todolist.services.interfaces.IUserService;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.EmptyStackException;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
public class UserController {

    private final IUserService userService;
    private final TokenManager tokenManager;

    @Autowired
    public UserController(IUserService userService, TokenManager tokenManager) {
        this.userService = userService;
        this.tokenManager = tokenManager;
    }


    @PostMapping(path = "/registration")
    public ResponseEntity<UserPojo> createUser(@RequestBody User user) {
        UserPojo result = userService.CreateUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping(path = "/authentication")
    public ResponseEntity<String> authentication(@RequestBody User user) {
        UserPojo authenticatedUser = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (authenticatedUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = tokenManager.createToken(new TokenPayload(authenticatedUser.getId(), authenticatedUser.getEmail(), Calendar.getInstance().getTime()));
        return new ResponseEntity<>(token, HttpStatus.OK);


    }

    @GetMapping(path = "/user/{id}")
//	@GetMapping(path = "/user/1")
//	public ResponseEntity<UserPojo> getUser(@PathVariable Long id){
    public ResponseEntity<UserPojo> getUser(@PathVariable("id") Long id) {
        System.out.println(id);
//		public ResponseEntity<UserPojo> getUser(){
        UserPojo result = userService.getUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(path = "/users")
    public ResponseEntity<List<UserPojo>> getUserAll() {
        List<UserPojo> result = userService.getAllUsers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<UserPojo> updateUser(@PathVariable("id") Long id, @RequestBody User source) {
        UserPojo result = userService.updateUser(source, id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }


    /**
     * Test Endpopint
     */

    @GetMapping(value = "/404")
    public String notFoud(Model model) {
        return "404";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testMethod(Model model) {
        model.addAttribute("greeting", "Hello World 2");
        return "welcome";
    }

    @PostMapping(path = "/todo")
    public ResponseEntity<Todo> createTask(@RequestBody Todo todo) {
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    /**
     * Exception Handling
     */

    @ExceptionHandler
    public ResponseEntity<String> onConflictUserMail(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR!!!! onConflictUserMail "
                + e.getMessage() + " Classs "
                + ClassUtils.getShortName(e.getClass()));
    }

    @ExceptionHandler
    public ResponseEntity<String> onMissingUserId(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR!!!! onMissingUserId "
                + e.getMessage() + " Classs "
                + ClassUtils.getShortName(e.getClass()));
    }

    @ExceptionHandler
    public ResponseEntity<String> onMissingUser(EmptyStackException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR!!!! onMissingUser "
                + e.getMessage()
                + e.getLocalizedMessage()
                + " Classs "
                + ClassUtils.getShortName(e.getClass()));
    }

    @ExceptionHandler
    public ResponseEntity<String> SQlProblems(SQLException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("SQL ERROR USER !!!! SQlProblems "
                + e.getSQLState()
                + e.getLocalizedMessage()
                + e.getMessage() + " Classs "
                + ClassUtils.getShortName(e.getClass()));
    }

    @ExceptionHandler
    public ResponseEntity<String> customExceptionHandler(CustomEmptyDataException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR!!!! customExceptionHandler "
                + e.getMessage()
                + e.getCause()
                + e.getLocalizedMessage()
                + " Classs "
                + ClassUtils.getShortName(e.getClass()));
    }


}