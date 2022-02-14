package com.buyMe.admin.user;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.buyMe.common.entity.Role;
import com.buyMe.common.entity.User;

@DataJpaTest
// to use the test on real database
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	//ref to user repo
	@Autowired
	private UserRepository repo;
	
	//add test entity manager by spring data jpa
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateUser() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User sachin = new User("swap@gnail.com", "swap123", "Swapnil", "Gaikwad");
		sachin.addRole(roleAdmin);

		User savedUser = repo.save(sachin);
		// to check object is persistent or not
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userRavi = new User("ravi@gmail.com", "ravi2020", "ravi", "kumar");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);

		User savedUser = repo.save(userRavi);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}

	@Test
	public void testGetUserbyId() {
		User userName = repo.findById(1).get();
		System.out.println(userName);
		assertThat(userName).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userName = repo.findById(1).get();
		userName.setEnabled(true);
		userName.setEmail("swap@gmail.com");
		repo.save(userName);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userRavi = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		
		userRavi.getRoles().remove(roleEditor);
		userRavi.addRole(roleSalesperson);
		
		repo.save(userRavi);
	}
	@Test
	public void testDeleteUser() {
		Integer userid = 2;
		repo.deleteById(userid);
		//if user id is not there it will thow exception
		repo.findById(userid);
	}
	
	@Test
	public void testGetUserByEmail() {
		//should fail for email not avail in db
		String email="mike@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id=2;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisaledUser() {
		Integer id = 2;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnaledUser() {
		Integer id = 2;
		repo.updateEnabledStatus(id, true);
	}
	
	//test to check the paging of users if more users there
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		 Page<User> page=repo.findAll(pageable);
		  List<User> listUsers= page.getContent();
		  listUsers.forEach(user -> System.out.println(user));
		  assertThat(listUsers.size()).isEqualTo(pageSize);
	}
}
