package com.buyMe.admin.user;


import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.buyMe.common.entity.Role;
import com.buyMe.common.entity.User;

@Service
@Transactional
public class UserService {
	public static final int USERS_PER_PAGE = 4;
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public User getByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}
	
	public List<User> listAll(){
		return (List<User>) userRepo.findAll();
		
	}
//	
	public List<Role> listRoles(){
		return (List<Role>) roleRepo.findAll();
	}

	public User save(User user) {
		//check user is come for edit
		boolean isUpdatingUser = (user.getId() != null);
		if(isUpdatingUser) {
			//if true then find user by id
			User existingUser = userRepo.findById(user.getId()).get();
			//if password field empty in edit phase then pass not change
			if(user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				//else change password
				encodePassword(user);
			}
		}else {
			//if user is not updating user mean new user simply encode password
			encodePassword(user);
		}
		
		return userRepo.save(user);
		
	}
	
	private void encodePassword(User user) {
		//encode password using encode method get password from user class
		String encodePassword = passwordEncoder.encode(user.getPassword());
		//set password the updated password  check db
		user.setPassword(encodePassword);
			}
	
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		
		if(userByEmail == null) 
			return true;
		//if id null then user is new, else user id is edited
		boolean isCreatingNew = (id == null);
		
		if(isCreatingNew) {
			//in create new user if not null then there user with that mail
			if(userByEmail != null) 
				return false;
		} else {
			if(userByEmail.getId() != id) {
				return false;
			}
		}
		return true;
		
	}
// if id is not there then throw exp 
	public User get(Integer id) throws UserNotFoundException {
		try {
		return userRepo.findById(id).get();
		}
		catch(NoSuchElementException e) {
			throw new UserNotFoundException("Could not find with id" + id);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		//if user is not in db
		if(countById==null || countById == 0) {
			throw new UserNotFoundException("Could not find user with ID " + id);
		}
		//no custom query bec of jpa repo
		userRepo.deleteById(id);
		}
	
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}
	
	//method to return page of user object
	//modifying it for sorting
	public Page<User> listByPage(int pageNum ,String sortField,String sortDir,String keyword){
		//creating sort object spring framework
		Sort sort = Sort.by(sortField);
		//check ascending or desc
		sort  = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		// add a param sort
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE ,sort);
		//if keyword not null then show users by keyword
		if (keyword != null) {
			return userRepo.findAll(keyword, pageable);
		}
		//else return simply users
		return userRepo.findAll(pageable);
	}
		
}

