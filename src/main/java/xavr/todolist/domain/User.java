package xavr.todolist.domain;


import jakarta.persistence.*;

import java.util.*;



@Entity
@Table(name = "_USER")
public class User {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;
	
	@Column(name="EMAIL", unique = true, nullable = false)	
	private String email;
	
	@Column(name="PASSWORD", nullable = false)
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Todo> todolist = new HashSet<>();
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	



////@ManyToOne	
	public Set<Todo> getTodoList(){
		return todolist;
	}
	
	public void addTodo(Todo todo) {
		addTodo(todo,false);
		
	}
	
	public void addTodo(Todo todo,boolean otherSideHasBeenSet) {
		this.getTodoList().add(todo);
		if (otherSideHasBeenSet) {
			return;
		}
		todo.setUser(this,true);
		
	}	
	
	public void removeTodo(Todo todo) {
		removeTodo(todo,false);
		
	}
	
	public void removeTodo(Todo todo,boolean otherSideHasBeenSet) {
		this.getTodoList().remove(todo);
		if (otherSideHasBeenSet) {
			return;
		}
		todo.removeUser(this,true);
	}
	
	
////@ManyToOne

	@Override
	public int hashCode() {
		return Objects.hash(email, id, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && id == other.id && Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + "]";
	}

}
