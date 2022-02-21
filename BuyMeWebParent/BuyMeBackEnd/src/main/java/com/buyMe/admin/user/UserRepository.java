package com.buyMe.admin.user;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.buyMe.common.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	
	//custom query for getting email
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	//method 
	public Long countById(Integer id);
	
	//update enabled status of spec user
	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	//because this is update query add modifying
	@Modifying
	//add transaction annotation in user service
	public void updateEnabledStatus(Integer id, boolean enabled);
	
	//method for implementing the search functionality
	@Query("SELECT u FROM User u WHERE CONCAT(u.id,' ',u.firstname, ' ',u.lastname,' ',u.email) LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
	
}
