package com.francisco.openpolls.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.francisco.openpolls.dto.SubmissionAnswerRequest;
import com.francisco.openpolls.dto.SubmissionAnswerResponse;
import com.francisco.openpolls.dto.SubmissionRequest;
import com.francisco.openpolls.dto.SubmissionResponse;
import com.francisco.openpolls.dto.SubmissionsResponse;
import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.model.Submission;
import com.francisco.openpolls.model.SubmissionAnswer;
import com.francisco.openpolls.repository.SubmissionRepository;

import jakarta.transaction.Transactional;

@Service
public class SubmissionService {
	
	
	@Autowired
	SubmissionRepository submissionRepository;
	
	@Autowired
	@Lazy
	QuestionService questionService;
	
	@Autowired
	@Lazy
	PollService pollService;
	
	@Autowired
	SubmissionAnswerService submissionAnswerService;
	
	@Autowired
	SubmissionRequestValidationService submissionRequestValidationService;
	
	@Transactional
	public void submit(SubmissionRequest submissionRequest) {
		submissionRequestValidationService.validate(submissionRequest);
		Submission submission = new Submission();
		submission.setIpAddress(submissionRequest.getIpAddress());
		submission.setEmailAddress(submissionRequest.getEmail());
		submission.setPoll(pollService.getReferenceById(submissionRequest.getPollId()));
		submission = submissionRepository.save(submission);
		
		for(SubmissionAnswerRequest submissionAnswerRequest: submissionRequest.getSubmissionAnswers()) {
			Question question = questionService.getReferenceById(submissionAnswerRequest.getQuestionId());
			String answer = submissionAnswerRequest.getAnswer();
			SubmissionAnswer submissionAnswer = SubmissionAnswer.builder().question(question).answer(answer).submission(submission).build();
			submissionAnswer = submissionAnswerService.save(submissionAnswer);
		}
	}
	
	



	public void deleteSubmissionsByPollId(Long pollId) {
		submissionRepository.deleteByPollId(pollId);
	}
	

	public void deleteSubmissionById(Long id) {
		submissionRepository.deleteById(id);
	}
	
	public Page<Submission> findSubmissionsByPollId(Long pollId, Pageable pageable) {
		return submissionRepository.findByPollId(pollId, pageable);
	}
	
	public Map<Submission, List<SubmissionAnswer>> generateSubmissionsMapForPollId(Long pollId, Pageable pageable) {
		Map<Submission, List<SubmissionAnswer>> response = new HashMap<>();
		
		Page<Submission> submissions = submissionRepository.findByPollIdOrderById(pollId, pageable);
		for (Submission submission: submissions) {
			List<SubmissionAnswer> submissionAnswers = submissionAnswerService.findBySubmissionIdOrderById(submission.getId());
			response.put(submission, submissionAnswers);
		}
		return response;
	}
	
	
	
	



}
