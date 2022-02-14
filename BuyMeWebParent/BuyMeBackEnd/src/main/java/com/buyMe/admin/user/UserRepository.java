package com.buyMe.admin.user;

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
}
