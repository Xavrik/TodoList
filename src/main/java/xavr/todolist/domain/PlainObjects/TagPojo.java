package xavr.todolist.domain.PlainObjects;


import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class TagPojo {

    private Long id;
    private String name;
    private Set<Long> todoList = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getTodoList() {
        return todoList;
    }

    public void setTodoListIds(Set<Long> todoList) {
        this.todoList = todoList;
    }
}
