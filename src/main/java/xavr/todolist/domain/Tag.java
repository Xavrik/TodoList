package xavr.todolist.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name= "TAG")
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	
	@Column(name = "NAME", nullable = false)
	private String name;


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

	public void setTodoList(Set<Todo> todoList) {
		this.todoList = todoList;
	}
	
	@ManyToMany(mappedBy = "tagList")
	private Set<Todo> todoList = new HashSet<Todo>();
	
	public Set<Todo> getTodoList(){
		return todoList;
	}
	
	public void addTodo(Todo todo) {
		addTodo(todo,false);
		
	}
	
	public void addTodo(Todo todo,boolean otherSideHasBeenSet) {
		this.getTodoList().add(todo);
		if (otherSideHasBeenSet) {
			return;
		}
		
		todo.addTag(this, true);
	}	
	
	public void removeTodo(Todo todo) {
		removeTodo(todo,false);
		
	}
	
	public void removeTodo(Todo todo,boolean otherSideHasBeenSet) {
		this.getTodoList().remove(todo);
		if (otherSideHasBeenSet) {
			return;
		}
		todo.removeTag(this,true);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tag tag = (Tag) o;
		return Objects.equals(id, tag.id) && Objects.equals(name, tag.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
