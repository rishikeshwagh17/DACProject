package com.buyMe.admin.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buyMe.admin.FileUploadUtil;
import com.buyMe.admin.security.BuyMeUserDetails;
import com.buyMe.common.entity.User;

@Controller
public class AccountController {
	
	
	@Autowired
	private UserService service;
	
	@GetMapping("/account")
	public String ViewDetails(@AuthenticationPrincipal BuyMeUserDetails loggedUser,Model model) {
		String email=loggedUser.getUsername();
		User user=service.getByEmail(email);
		model.addAttribute("user", user);
		return "account_form";
	}
	
	//same as user controller method just some modification
	@PostMapping("/account/update")
	public String saveDetails(User user, RedirectAttributes redirectAttributes,@AuthenticationPrincipal BuyMeUserDetails loggedUser,
			@RequestParam("image") MultipartFile multiPartFile) throws IOException {
		if(!multiPartFile.isEmpty()) {
			String fileName =StringUtils.cleanPath(multiPartFile.getOriginalFilename());
			user.setPhotos(fileName);
			
			//change this method by update account details
			User savedUser = service.updateAccount(user);
			
			
			String uploadDir ="user-photos/" + savedUser.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multiPartFile);
		}else {
			if (user.getPhotos().isEmpty()) {
				user.setPhotos(null);
			}
			//same update here user account details
			service.updateAccount(user);
		}
		loggedUser.setFirstName(user.getFirstname());
		loggedUser.setLastName(user.getLastname());
		////service.save(user);
		//message flash
		redirectAttributes.addFlashAttribute("message", "Your account details have been updated successfully");
		return "redirect:/account";

	}
}
