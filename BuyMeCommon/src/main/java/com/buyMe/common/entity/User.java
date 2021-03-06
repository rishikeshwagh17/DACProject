package com.buyMe.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


//user table creation
@Entity
@Table(name = "users")
public class User {
	//id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	//mail must be unique
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	//password also we need to encrypt it so length must be 64 min
	@Column(length = 64, nullable = false)
	private String password;
	//fname
	@Column(name = "first_name", length = 45, nullable = false)
	private String firstname;
	//lname
	@Column(name = "last_name", length = 45, nullable = false)
	private String lastname;

	@Column(length = 64)
	private String photos;
	//enabled status
	private boolean enabled;
	
	//relation between roles and user & hashset to avoid duplicate entries
	//unidiretional from user to role
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {

	}
	//constructor for user with specific fields
	public User(String email, String password, String firstname, String lastname) {
		super();
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	//method to add role
	public void addRole(Role role) {
		this.roles.add(role);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", roles=" + roles + "]";
	}
	
	//method to return photo image path
	@Transient
	public String getPhotosImagePath() {
		if (id == null || photos == null) {
			return "/images/default-user.png";
		}
		return "/user-photos/" + this.id + "/" + this.photos;
	}
}
