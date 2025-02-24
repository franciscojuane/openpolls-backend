package com.francisco.openpolls.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.francisco.openpolls.dto.SubmissionTableResponse;
import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.model.Submission;
import com.francisco.openpolls.model.SubmissionAnswer;
import com.francisco.openpolls.repository.aggregated.AnswerCount;
import com.francisco.openpolls.service.QuestionService;
import com.francisco.openpolls.service.SubmissionAnswerService;
import com.francisco.openpolls.service.SubmissionService;

@Controller
@RequestMapping("/polls/{pollId}/submissions")
public class SubmissionsController {

	@Autowired
	SubmissionService submissionService;
	
	@Autowired
	SubmissionAnswerService submissionAnswerService;
	
	@Autowired
	QuestionService questionService;
	
	
	@GetMapping("/table")
	public ResponseEntity<?> generateTable(@PathVariable Long pollId, Pageable pageable){
		SubmissionTableResponse submissionsTable = generateSubmissionsMapForPollId(pollId, pageable);
		return ResponseEntity.ok(submissionsTable);
	}
	
	@PreAuthorize("hasAuthority('RESULTS_READ')")
	public SubmissionTableResponse generateSubmissionsMapForPollId(Long pollId, Pageable pageable) {
		SubmissionTableResponse submissionTableResponse = new SubmissionTableResponse();
		
		List<Question> questions = questionService.findByPollId(pollId);
		Page<Submission> submissions = submissionService.findByPollIdOrderById(pollId, pageable);
		for (Submission submission: submissions) {
			List<SubmissionAnswer> submissionAnswers = submissionAnswerService.findBySubmissionIdOrderById(submission.getId());
			submissionTableResponse.addRow(submission, submissionAnswers, questions);
		}
		return submissionTableResponse;
	}
	
	@GetMapping("/answerCountByQuestion/{questionId}")
	public ResponseEntity<?> answerCountByQuestion(@PathVariable Long pollId, @PathVariable Long questionId, Pageable pageable){
		Page<AnswerCount> answerCount = submissionService.generateAnswerCountByPollIdAndQuestionId(pollId, questionId, pageable);
		return ResponseEntity.ok(answerCount);
	}
	
	@GetMapping("/answersByQuestion/{questionId}")
	public ResponseEntity<?> answerByQuestion(@PathVariable Long questionId, @PathVariable Long pollId, Pageable pageable){
		Page<String> answers = submissionService.generateAnswersByPollIdAndQuestionId(pollId, questionId, pageable);
		return ResponseEntity.ok(answers);
	}
	
}
