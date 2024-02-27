package xavr.todolist.controllers;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import xavr.todolist.annotations.Authenticational;
import xavr.todolist.domain.PlainObjects.TodoPojo;
import xavr.todolist.domain.Todo;
import xavr.todolist.services.interfaces.ITodoService;

import java.sql.SQLException;
import java.util.EmptyStackException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class TodoController {
    private final ITodoService todoService;
    Long userId;

    @Autowired
    public TodoController(ITodoService todoService) {
        this.todoService = todoService;
    }

    @Authenticational
    @PostMapping(path = "/user/{userId}/todo")
    public ResponseEntity<TodoPojo> createTodo(HttpServletRequest request, @RequestBody Todo todo, @PathVariable("userId") Long userId) {
        System.out.println(userId);
        TodoPojo result = todoService.createTodo(todo, userId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Authenticational
    @GetMapping(path = "/user/{userId}/todo/{id}")
    public ResponseEntity<TodoPojo> getTodo(HttpServletRequest request, @PathVariable("id") Long id) {
        return new ResponseEntity<>(todoService.getTodo(id, userId), HttpStatus.OK);
    }

    @Authenticational
    @GetMapping(path = "/user/{userId}/todos")
    public ResponseEntity<List<TodoPojo>> getAllTodo(HttpServletRequest request) {
        return new ResponseEntity<>(todoService.getAllTodos(userId), HttpStatus.OK);
    }

    @Authenticational
    @PutMapping(path = "/user/{userId}/todo/{todoId}")
    public ResponseEntity<TodoPojo> updateTodo(HttpServletRequest request, @RequestBody Todo source, @PathVariable("todoId") Long todoId) {
        return new ResponseEntity<>(todoService.updateTodo(source, todoId, userId), HttpStatus.OK);
    }

    @Authenticational
    @DeleteMapping(path = "/user/{userId}/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(HttpServletRequest request, @PathVariable("todoId") Long todoId) {
        return new ResponseEntity<>(todoService.deleteTodo(todoId, userId), HttpStatus.OK);
    }

    /**
     * Exceptoin Handle
     */
    @ExceptionHandler
    public ResponseEntity<String> onMissingTodoName(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR!!!! onMissingTodoName "
                + e.getMessage() + " Classs "
                + ClassUtils.getShortName(e.getClass()));
    }

    @ExceptionHandler
    public ResponseEntity<String> onMissingTodoId(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR!!!! onMissingTodoId "
                + e.getMessage() + " Classs "
                + ClassUtils.getShortName(e.getClass()));
    }

    @ExceptionHandler
    public ResponseEntity<String> onMissingTodo(EmptyStackException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR!!!! onMissingTodo "
                + e.getMessage()
                + e.getLocalizedMessage()
                + " Classs "
                + ClassUtils.getShortName(e.getClass()));
    }

    @ExceptionHandler
    public ResponseEntity<String> SQlProblems(SQLException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("SQL ERROR Todo !!!! SQlProblems "
                + e.getSQLState()
                + e.getLocalizedMessage()
                + e.getMessage() + " Classs "
                + ClassUtils.getShortName(e.getClass()));
    }
}
