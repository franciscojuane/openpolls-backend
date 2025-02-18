package com.francisco.openpolls.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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
		Question question = requestToQuestion(questionRequest, pollId);
		question = questionService.save(question);
		return ResponseEntity.ok(questionToResponse(question));
	}
	
	@PatchMapping("/{questionId}")
	public ResponseEntity<?> updateQuestionForPoll(@RequestBody QuestionRequest questionRequest,@PathVariable Long pollId, @PathVariable Long questionId) {
		questionRequest.setId(questionId);
		Question question = questionService.update(requestToQuestion(questionRequest, pollId));
		System.out.println(question.getPoll());
		return ResponseEntity.ok(questionToResponse(question));
	}
	
	@DeleteMapping("/{questionId}")
	public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId){
		questionService.deleteById(questionId);
		return ResponseEntity.ok().build();
	}
	
	
	
	
	   private QuestionResponse questionToResponse(Question question) {
	        QuestionResponse response = QuestionResponse.builder()
	            .text(question.getText())
	            .subText(question.getSubText())
	            .questionType(question.getQuestionType())
	            .minAmountOfSelections(question.getMinAmountOfSelections())
	            .maxAmountOfSelections(question.getMaxAmountOfSelections())
	            .pollId(question.getPoll().getId())
	            .minValue(question.getMinValue())
	            .maxValue(question.getMaxValue())
	            .scale(question.getScale())
	            .rank(question.getRank())
	            .minLength(question.getMinLength())
	            .maxLength(question.getMaxLength())
	            .build();
	        
	        if (question.getOptions()!=null) {
	            response.setOptions(question.getOptions().stream().map(elem -> elem.getText()).toList());
	        }
	        
	        response.setId(question.getId());
	        return response;
	    }
	   
	   private Question requestToQuestion(QuestionRequest request, Long pollId) {
		    Question question = Question.builder()
		        .text(request.getText())
		        .subText(request.getSubText())
		        .questionType(request.getQuestionType())
		        .minAmountOfSelections(request.getMinAmountOfSelections())
		        .maxAmountOfSelections(request.getMaxAmountOfSelections())
		        .minValue(request.getMinValue())
		        .maxValue(request.getMaxValue())
		        .scale(request.getScale())
		        .rank(request.getRank())
		        .minLength(request.getMinLength())
		        .maxLength(request.getMaxLength())
		        .build();

		        question.setId(request.getId());
		        
		        question.setPoll(pollService.findById(pollId));
		        
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
