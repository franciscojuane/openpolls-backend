package com.francisco.openpolls.utils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.francisco.openpolls.dto.UserCreateRequest;
import com.francisco.openpolls.model.Permission;
import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.model.QuestionOption;
import com.francisco.openpolls.model.Role;
import com.francisco.openpolls.model.User;
import com.francisco.openpolls.model.enums.QuestionType;
import com.francisco.openpolls.model.enums.SubmissionLimitCriteria;
import com.francisco.openpolls.repository.PermissionRepository;
import com.francisco.openpolls.repository.RoleRepository;
import com.francisco.openpolls.service.PollService;
import com.francisco.openpolls.service.QuestionOptionService;
import com.francisco.openpolls.service.QuestionService;
import com.francisco.openpolls.service.UserService;

@Component
public class DataLoader implements InitializingBean {

	@Autowired
	UserService userService;
	
	@Autowired
	PollService pollService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	QuestionOptionService questionOptionService;
	
	@Autowired
	PermissionRepository permissionRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public void afterPropertiesSet() throws Exception {
		
		List<SimpleGrantedAuthority> authorities = List.of(
			    new SimpleGrantedAuthority("ROLE_ADMIN"), 
			    new SimpleGrantedAuthority("RESULTS_READ"), 
			    new SimpleGrantedAuthority("POLL_READ"),
			    new SimpleGrantedAuthority("POLL_CREATE"),
			    new SimpleGrantedAuthority("POLL_UPDATE"),
			    new SimpleGrantedAuthority("POLL_DELETE"),
			    new SimpleGrantedAuthority("SUBMISSION_READ"),
			    new SimpleGrantedAuthority("SUBMISSION_DELETE")
			);
		
		 Authentication auth = new UsernamePasswordAuthenticationToken(
		            "admin",
		            null,
		            authorities // Roles
		        );
		        SecurityContextHolder.getContext().setAuthentication(auth);
		
		Permission resultsReadPermission = Permission.builder().name("RESULTS_READ").build();
		resultsReadPermission = permissionRepository.save(resultsReadPermission);
		
		Permission pollReadPermission = Permission.builder().name("POLL_READ").build();
		pollReadPermission = permissionRepository.save(pollReadPermission);
		
		Permission pollCreatePermission = Permission.builder().name("POLL_CREATE").build();
		pollCreatePermission = permissionRepository.save(pollCreatePermission);
		
		Permission pollUpdatePermission = Permission.builder().name("POLL_UPDATE").build();
		pollUpdatePermission = permissionRepository.save(pollUpdatePermission);
		
		Permission pollDeletePermission = Permission.builder().name("POLL_DELETE").build();
		pollDeletePermission = permissionRepository.save(pollDeletePermission);
		
		Permission submissionReadPermission = Permission.builder().name("SUBMISSION_READ").build();
		submissionReadPermission = permissionRepository.save(submissionReadPermission);
		
		Permission submissionDeletePermission = Permission.builder().name("SUBMISSION_DELETE").build();
		submissionDeletePermission = permissionRepository.save(submissionDeletePermission);
		
		
		Set<Permission> adminPermissions = new HashSet<>();
		adminPermissions.add(resultsReadPermission);
		adminPermissions.add(pollReadPermission);
		adminPermissions.add(pollCreatePermission);
		adminPermissions.add(pollUpdatePermission);
		adminPermissions.add(pollDeletePermission);
		adminPermissions.add(submissionReadPermission);
		adminPermissions.add(submissionDeletePermission);
		
		Role adminRole = Role.builder().name("ADMIN").permissions(adminPermissions).build();
		adminRole = roleRepository.save(adminRole);
		
		Set<Permission> viewerPermissions = new HashSet<>();
		viewerPermissions.add(pollReadPermission);
		viewerPermissions.add(submissionReadPermission);
		viewerPermissions.add(resultsReadPermission);
		
		Role viewerRole = Role.builder().name("VIEWER").permissions(viewerPermissions).build();
		viewerRole = roleRepository.save(viewerRole);
		
		UserCreateRequest userCreateRequest = UserCreateRequest.builder().firstName("Francisco")
				.lastName("Juane").email("admin@admin.com").password("admin").roles(Set.of(adminRole))
				.build();
		User user1 = userService.create(userCreateRequest);
		
		UserCreateRequest userCreateRequest2 = UserCreateRequest.builder().firstName("John")
				.lastName("Doe").email("john@doe.com").password("johndoe").roles(Set.of(viewerRole))
				.build();
		User user2 = userService.create(userCreateRequest2);
		
		Poll poll = Poll.builder().name("Poll 1").description("Poll 1 Description").submissionLimitCriteria(SubmissionLimitCriteria.NONE).
				createdByUser(user1).build();

		poll.setEffectiveDate(LocalDateTime.now().minusDays(1));
		poll.setExpirationDate(LocalDateTime.now().plusDays(1));
		poll = pollService.save(poll);
		
		Question question1 = Question.builder()
				.questionType(QuestionType.MULTIPLE_CHOICE)
				.minAmountOfSelections(1)
				.maxAmountOfSelections(1)
				.text("Select your favorite animal")
				.subText("Only one option allowed")
				.poll(poll)
				.rank(1)
				.build();
		
		question1 = questionService.save(question1);
		
		QuestionOption questionOption11 = QuestionOption.builder().text("Cats").question(question1)
				.build();
		questionOption11 = questionOptionService.save(questionOption11);
		
		QuestionOption questionOption12 = QuestionOption.builder().text("Dogs").question(question1)
				.build();
		questionOption12 = questionOptionService.save(questionOption12);
		
		QuestionOption questionOption13 = QuestionOption.builder().text("Iguanas").question(question1)
				.build();
		questionOption13 = questionOptionService.save(questionOption13);
		
		QuestionOption questionOption14 = QuestionOption.builder().text("Parrots").question(question1)
				.build();
		questionOption14 = questionOptionService.save(questionOption14);
		
		Question question2 = Question.builder()
				.questionType(QuestionType.MULTIPLE_CHOICE)
				.minAmountOfSelections(1)
				.maxAmountOfSelections(3)
				.text("Select your favorites musical genres")
				.subText("Up to 3 selections")
				.poll(poll)
				.rank(1)
				.build();
		
		question2 = questionService.save(question2);
		
		QuestionOption questionOption21 = QuestionOption.builder().text("Pop").question(question2)
				.build();
		questionOption21 = questionOptionService.save(questionOption21);
		
		QuestionOption questionOption22 = QuestionOption.builder().text("Rock").question(question2)
				.build();
		questionOption22 = questionOptionService.save(questionOption22);
		
		QuestionOption questionOption23 = QuestionOption.builder().text("Jazz").question(question2)
				.build();
		questionOption23 = questionOptionService.save(questionOption23);
		
		QuestionOption questionOption24 = QuestionOption.builder().text("Classical").question(question2)
				.build();
		questionOption24 = questionOptionService.save(questionOption24);
		
		QuestionOption questionOption25 = QuestionOption.builder().text("Electro").question(question2)
				.build();
		questionOption25 = questionOptionService.save(questionOption25);
		
		QuestionOption questionOption26 = QuestionOption.builder().text("Blues").question(question2)
				.build();
		questionOption26 = questionOptionService.save(questionOption26);
		
		QuestionOption questionOption27 = QuestionOption.builder().text("Samba").question(question2)
				.build();
		questionOption27 = questionOptionService.save(questionOption27);
		
		
		Question question3 = Question.builder()
				.questionType(QuestionType.NUMERIC)
				.text("Enter your age:")
				.minValue(18)
				.maxValue(120)
				.scale(1)
				.poll(poll)
				.build();
		
		question3 = questionService.save(question3);
		
		
		Question question4 = Question.builder()
				.questionType(QuestionType.SCALE)
				.text("Enter the amount of people in your family:")
				.subText("Including yourself")
				.minValue(1)
				.maxValue(20)
				.scale(1)
				.poll(poll)
				.build();
		
		question4 = questionService.save(question4);

		
		Question question5 = Question.builder()
				.questionType(QuestionType.TEXT)
				.text("Enter your commments")
				.minLength(100)
				.maxLength(500)
				.poll(poll)
				.build();
		
		question5 = questionService.save(question5);
		
	}

}
