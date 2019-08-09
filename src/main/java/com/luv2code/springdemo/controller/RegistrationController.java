package com.luv2code.springdemo.controller;

import java.util.List;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luv2code.springdemo.user.CrmUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserDetailsManager userDetailsManager;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private Logger logger = Logger.getLogger(getClass().getName());

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model model) {
		model.addAttribute("crmUser", new CrmUser());

		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser crmUser, BindingResult bindingResult, Model model) {
		String userName = crmUser.getUserName();

		logger.info("Processing registration form for: " + userName);

		// Form validation.
		if (bindingResult.hasErrors()) {
			model.addAttribute("crmUser", new CrmUser());
			model.addAttribute("registrationError", "User name/password cannot be empty.");

			logger.warn("User name/password cannot be empty.");

			return "registration-form";
		}

		// Check the database if user already exists.
		boolean userExists = doesUserExist(userName);

		if (userExists) {
			model.addAttribute("crmUser", new CrmUser());
			model.addAttribute("registrationError", "User name already exists.");

			logger.warn("User name already exists.");

			return "registration-form";
		}

		// Validation checks passed.
		String encodedPassword = passwordEncoder.encode(crmUser.getPassword());

		// Prepend the encoding algorithm id.
		encodedPassword = "{bcrypt}" + encodedPassword;

		// Give user default role of "employee".
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");

		// Create user object (from Spring Security framework).
		User user = new User(userName, encodedPassword, authorities);

		// Save user in the database.
		userDetailsManager.createUser(user);

		logger.info("Successfully created user: " + userName);

		return "registration-confirmation";
	}

	private boolean doesUserExist(String userName) {
		logger.info("Checking if user exists: " + userName);

		// Check the database if user already exists.
		boolean exists = userDetailsManager.userExists(userName);

		logger.info("User: " + userName + ", exists: " + exists);

		return exists;
	}

}
