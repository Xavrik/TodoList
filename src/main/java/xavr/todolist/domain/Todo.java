package xavr.todolist.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;




@Entity
@Table(name= "TODO")
public class Todo{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "COMMENT")
	private String comment;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "EMPORTANT")
	private Boolean important;
	
	@Column(name = "PRIORITY")
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	
	@ManyToMany
	@JoinTable(name = "TODO_TAG", joinColumns =  @JoinColumn(name = "TODO_ID"), inverseJoinColumns = @JoinColumn(name = "TAG_ID") )
	private Set<Tag> tagList = new HashSet<Tag>();
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getImportant() {
		return important;
	}

	public void setImportant(Boolean important) {
		this.important = important;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

////@ManyToOne
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		setUser(user, false);		
	}
	
	public void setUser (User user,boolean otherSideHasBeenSet) {
		this.user = user;
		if (otherSideHasBeenSet) {
			return;
		}
		user.addTodo(this,true);
	}
	
	
	public void removeUser(User user) {
		removeUser(user, false);		
	}
	
	public void removeUser (User user,boolean otherSideHasBeenSet) {
		this.user = null;
		if (otherSideHasBeenSet) {
			return;
		}
		user.removeTodo(this,true);
	}
	
//ManytoMany _Tag
	public Set<Tag> getTagList(){
		return tagList;
	}
	
	public void addTag(Tag tag) {
		addTag(tag,false);
		
	}
	
	public void addTag(Tag tag,boolean otherSideHasBeenSet) {
		this.getTagList().add(tag);
		if (otherSideHasBeenSet) {
			return;
		}
		tag.addTodo(this, true);
	}	
	
	public void removeTag(Tag tag) {
		removeTag(tag,false);
		
	}
	
	public void removeTag(Tag tag,boolean otherSideHasBeenSet) {
		this.getTagList().remove(tag);
		if (otherSideHasBeenSet) {
			return;
		}
		tag.removeTodo(this, true);
	}
	
	
	
////@ManyToOne
	@Override
	public String toString() {
		return "Todo [id=" + id + ", name=" + name + ", comment=" + comment + ", startDate=" + startDate + ", endDate="
				+ endDate + ", important=" + important + ", priority=" + priority + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(comment, endDate, id, important, name, priority, startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		return Objects.equals(comment, other.comment) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(id, other.id) && Objects.equals(important, other.important)
				&& Objects.equals(name, other.name) && priority == other.priority
				&& Objects.equals(startDate, other.startDate);
	}
	
	
		



	
	
}
