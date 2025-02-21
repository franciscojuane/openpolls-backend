package com.francisco.openpolls.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.francisco.openpolls.dto.QuestionResponse;
import com.francisco.openpolls.dto.mappers.QuestionMapper;
import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.service.QuestionService;

@Controller
@RequestMapping("/public/polls/{pollKey}/questions")
public class PublicQuestionsController {
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	QuestionMapper questionMapper;
	
	@GetMapping("/")
	public ResponseEntity<?> getQuestionsByPollKey(@PathVariable String pollKey) {
		List<Question> questions = questionService.findByPollKey(pollKey);
		List<QuestionResponse> questionResponse = questions.stream().map(elem -> questionMapper.questionToResponse(elem)).toList();
		return ResponseEntity.ok(questionResponse);
	}

	
	
}
