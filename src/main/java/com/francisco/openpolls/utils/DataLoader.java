package com.francisco.openpolls.utils;

import java.time.LocalDateTime;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.francisco.openpolls.dto.UserCreateRequest;
import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.model.QuestionOption;
import com.francisco.openpolls.model.User;
import com.francisco.openpolls.model.enums.QuestionType;
import com.francisco.openpolls.model.enums.SubmissionLimitCriteria;
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

	@Override
	public void afterPropertiesSet() throws Exception {

		UserCreateRequest userCreateRequestDTO = UserCreateRequest.builder().firstName("Francisco")
				.lastName("Juane").email("admin@admin.com").password("admin").build();
		User user1 = userService.create(userCreateRequestDTO);
		
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
		
		QuestionOption questionOption1 = QuestionOption.builder().text("Cats").question(question1)
				.build();
		questionOption1 = questionOptionService.save(questionOption1);
		
		QuestionOption questionOption2 = QuestionOption.builder().text("Dogs").question(question1)
				.build();
		questionOption2 = questionOptionService.save(questionOption2);
		
		QuestionOption questionOption3 = QuestionOption.builder().text("Iguanas").question(question1)
				.build();
		questionOption3 = questionOptionService.save(questionOption3);
		
		QuestionOption questionOption4 = QuestionOption.builder().text("Parrots").question(question1)
				.build();
		questionOption4 = questionOptionService.save(questionOption4);
		
		Question question2 = Question.builder()
				.questionType(QuestionType.NUMERIC)
				.text("Enter your age:")
				.minValue(18)
				.maxValue(120)
				.scale(1)
				.poll(poll)
				.build();
		
		question2 = questionService.save(question2);
		
		
		Question question3 = Question.builder()
				.questionType(QuestionType.SCALE)
				.text("Enter the amount of people in your family:")
				.subText("Including yourself")
				.minValue(1)
				.maxValue(20)
				.scale(1)
				.poll(poll)
				.build();
		
		question3 = questionService.save(question3);

		
		Question question4 = Question.builder()
				.questionType(QuestionType.TEXT)
				.text("Enter your commments")
				.minLength(100)
				.maxLength(500)
				.poll(poll)
				.build();
		
		question4 = questionService.save(question4);
		
	}

}
