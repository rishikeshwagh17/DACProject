package com.buyMe.admin.user;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buyMe.admin.FileUploadUtil;
import com.buyMe.common.entity.Role;
import com.buyMe.common.entity.User;



@Controller
public class UserController {
	//reference to user service
	@Autowired
	private UserService service;

	// method to list all users in our users page

	//	@GetMapping("/users")
//	public String listAll(Model model) {
//		List<User> listUsers = service.listAll();
//		model.addAttribute("listUsers", listUsers);
//		return "users";
//	}  
	
	@GetMapping("/users")
	public String listFirstPage(Model model) {
		//adding sorting parameter so that it by default sort by fname
		return listByPage(1, model, "firstname","asc");
	} 
	
	//handling method for creating new user when user click on create new user we have to show him form
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = service.listRoles();
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create new User");
		return "user_form";
	}
    
	//method for form posting check user_html where form has method=post
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multiPartFile) throws IOException {
		if(!multiPartFile.isEmpty()) {
			String fileName =StringUtils.cleanPath(multiPartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = service.save(user);
			String uploadDir ="user-photos/" + savedUser.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multiPartFile);
		}else {
			if (user.getPhotos().isEmpty()) {
				user.setPhotos(null);
			}
			service.save(user);
		}
		
		////service.save(user);
		//message flash
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
		return "redirect:/users";

	}
	
//  handling method for editing the user with path var id
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			//get user by id call service get id
			User user = service.get(id);
			List<Role> listRoles = service.listRoles();

			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
			model.addAttribute("listRoles", listRoles);
			//return to form for edit
			return "user_form";
		} catch (UserNotFoundException e) {
			//then falsh message user not found
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			//redirect to user with msg
			return "redirect:/users";
		}

	}
	//delete user by id here id is path variable
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "The User ID has been deleted successfully " + id);
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			
		}
		//after delete redirect to the the user page
		return "redirect:/users";
	}
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String updatenabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,RedirectAttributes redirectAttributes) {
		service.updateUserEnabledStatus(id, enabled);
		String status= enabled ? "enabled" : "Disabled";
		String message="The user ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/users";
	}
	
//	//method to code listbypage and list first page
//	@GetMapping("/users/page/{pageNum}")
//	public String listByPage(@PathVariable(name ="pageNum") int pageNum,Model model) {
//		Page<User> page= service.listByPage(pageNum);
//		List<User> listUsers = page.getContent();
////		System.out.println("pageNum = " + pageNum);
////		System.out.println("total element = " + page.getTotalElements());
////		System.out.println("total pages = " + page.getTotalPages());
//		long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
//		long endCount = startCount + UserService.USERS_PER_PAGE - 1;
//		if(endCount > page.getTotalElements()) {
//			endCount = page.getTotalElements();
//		}
//		model.addAttribute("currentPage", pageNum);
//		model.addAttribute("totalPages", page.getTotalPages());
//		model.addAttribute("startCount", startCount);
//		model.addAttribute("endCount", endCount);
//		model.addAttribute("totalItems", page.getTotalElements());
//		
//		model.addAttribute("listUsers", listUsers);
//		
//		System.out.println("total pages = " + page.getTotalPages());
//		return "users";
//		
//	}
	
	//new modified method
	//method to code listbypage and list first page
	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name ="pageNum") int pageNum,Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir) {
		Page<User> page= service.listByPage(pageNum ,sortField,sortDir);
		//debug sortFiled and sortDir
		System.out.println("sort field and sort Order" + sortField + " " + sortDir);
		List<User> listUsers = page.getContent();
//		System.out.println("pageNum = " + pageNum);
//		System.out.println("total element = " + page.getTotalElements());
//		System.out.println("total pages = " + page.getTotalPages());
		long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("listUsers", listUsers);
		
		System.out.println("total pages = " + page.getTotalPages());
		return "users";
		
	}
}
