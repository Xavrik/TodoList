package xavr.todolist.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "USER_NAME", unique = true, nullable = false)
	private String username;


	@Column(name="EMAIL", unique = true, nullable = false)	
	private String email;
	
	@Column(name="PASSWORD", nullable = false)
	private String password;

	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Todo> todolist = new HashSet<>();

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles) && Objects.equals(todolist, user.todolist);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, email, password, roles, todolist);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", roles=" + roles +
				", todolist=" + todolist +
				'}';
	}
}
