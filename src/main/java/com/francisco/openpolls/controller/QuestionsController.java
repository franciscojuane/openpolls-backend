package com.francisco.openpolls.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.francisco.openpolls.dto.QuestionRequest;
import com.francisco.openpolls.dto.QuestionResponse;
import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.model.QuestionOption;
import com.francisco.openpolls.service.PollService;
import com.francisco.openpolls.service.QuestionService;

@Controller
@RequestMapping("/polls/{pollId}/questions")
public class QuestionsController {

	@Autowired
    private QuestionService questionService;
    
    @Autowired
    private PollService pollService;
	
	@GetMapping("")
	public ResponseEntity<?> getQuestionsByPoll(@PathVariable Long pollId) {
		List<Question> questions = questionService.findByPollId(pollId);
		List<QuestionResponse> questionResponse = questions.stream().map(elem -> questionToResponse(elem)).toList();
		return ResponseEntity.ok(questionResponse);
	}
	
	
	@PostMapping("")
	public ResponseEntity<?> createQuestionForPoll(@PathVariable Long pollId, @RequestBody QuestionRequest questionRequest) {
		Question question = questionService.save(responseToQuestion(questionRequest));
		return ResponseEntity.ok(questionToResponse(question));
	}
	
	@PatchMapping("")
	public ResponseEntity<?> updateQuestionForPoll(@PathVariable Long pollId) {
		List<Question> questions = questionService.findByPollId(pollId);
		List<QuestionResponse> questionResponse = questions.stream().map(elem -> questionToResponse(elem)).toList();
		return ResponseEntity.ok(questionResponse);
	}
	
	
	
	
	   private QuestionResponse questionToResponse(Question question) {
	        QuestionResponse response = QuestionResponse.builder()
	            .text(question.getText())
	            .subText(question.getSubText())
	            .questionType(question.getQuestionType())
	            .minAmountOfSelections(question.getMinAmountOfSelections())
	            .maxAmountOfSelections(question.getMaxAmountOfSelections())
	            .options(question.getOptions().stream().map(elem -> elem.getText()).toList())
	            .minValue(question.getMinValue())
	            .maxValue(question.getMaxValue())
	            .scale(question.getScale())
	            .minLength(question.getMinLength())
	            .maxLength(question.getMaxLength())
	            .build();
	        
	        response.setId(question.getId());
	        return response;
	    }
	   
	   private Question responseToQuestion(QuestionRequest request) {
		    Question question = Question.builder()
		        .text(request.getText())
		        .subText(request.getSubText())
		        .questionType(request.getQuestionType())
		        .minAmountOfSelections(request.getMinAmountOfSelections())
		        .maxAmountOfSelections(request.getMaxAmountOfSelections())
		        .minValue(request.getMinValue())
		        .maxValue(request.getMaxValue())
		        .scale(request.getScale())
		        .minLength(request.getMinLength())
		        .maxLength(request.getMaxLength())
		        .build();

		        question.setId(request.getId());

	
		    if (request.getOptions() != null) {
		        List<QuestionOption> options = request.getOptions().stream()
		            .map(optionText -> QuestionOption.builder()
		                .text(optionText)
		                .question(question) 
		                .build())
		            .toList();
		        question.setOptions(options);
		    }

		    return question;
		}
}
