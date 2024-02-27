package xavr.todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xavr.todolist.domain.Exceprions.CustomEmptyDataException;
import xavr.todolist.domain.PlainObjects.TodoPojo;
import xavr.todolist.domain.Tag;
import xavr.todolist.domain.Todo;
import xavr.todolist.domain.User;
import xavr.todolist.repositories.TodoRepository;
import xavr.todolist.repositories.UserRepository;
import xavr.todolist.services.interfaces.ITagService;
import xavr.todolist.services.interfaces.ITodoService;
import xavr.todolist.utilits.Converter;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TodoService implements ITodoService {


    private final TodoRepository todoRepository;
    private final Converter converter;
    private final ITagService tagService;
    private final UserRepository userRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository, Converter converter, ITagService tagService, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.converter = converter;
        this.tagService = tagService;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public TodoPojo createTodo(Todo todo, Long userId) {
        Optional<User> todoUser = userRepository.findById(userId);

     if (todoUser.isPresent()) {
         Set<Tag> tags = new HashSet<>();
         tags.addAll(todo.getTagList());

         todo.getTagList().clear();
         todo.setUser(todoUser.get());
         todoRepository.save(todo);


         tags.stream().map(tag -> tagService.findOrCreate(tag)).collect(Collectors.toSet()).forEach(todo::addTag);
         return converter.todoToPojo(todo);
     }else {
         throw new CustomEmptyDataException("unable to get user for todo" );
      }
    }

    @Override
    @Transactional
    public TodoPojo getTodo(Long Id, Long userId) {
       Optional<Todo> todoOptional = todoRepository.findById(Id);
       if (todoOptional.isPresent()) {
           return converter.todoToPojo(todoOptional.get());
       }else{
           throw new NoSuchElementException("unable to get todo" );
       }

    }

    @Override
    @Transactional
    public TodoPojo updateTodo(Todo sorce, Long todoId, Long userId) {
        Optional<Todo> findedTodo = todoRepository.findById(todoId);
        if (findedTodo.isPresent()) {
            Todo target = findedTodo.get();
            target.setName(sorce.getName());
            target.setComment(sorce.getComment());
            target.setPriority(sorce.getPriority());
            target.setStartDate(sorce.getStartDate());
            target.setEndDate(sorce.getEndDate());
            target.setImportant(sorce.getImportant());
            todoRepository.save(target);

            return converter.todoToPojo(target);
        }else {
            throw new NoSuchElementException("unable to update todo" );
        }
    }

    @Override
    @Transactional
    public String deleteTodo(Long todoId, Long userId) {

        Optional<Todo> todoForDeleteOptonal = todoRepository.findById(todoId);
        if (todoForDeleteOptonal.isPresent()) {
            Todo todoForDelete = todoForDeleteOptonal.get();
            todoForDelete.getTagList().stream().collect(Collectors.toList()).forEach(tag -> tag.removeTodo(todoForDelete));
            todoRepository.delete(todoForDelete);
            return "Todo " + todoForDelete.getId() + " deleted";
        }else {
            throw new NoSuchElementException("unable to delete todo" );
        }

    }

    @Override
    @Transactional
    public List<TodoPojo> getAllTodos(Long userId) {
       Optional<User>  findedUser = userRepository.findById(userId);
       if (findedUser.isPresent()) {
           return todoRepository.findAllByUser(findedUser.get()).stream().map(todo -> converter.todoToPojo(todo)).collect(Collectors.toList());
       }

        return List.of();
    }
}
