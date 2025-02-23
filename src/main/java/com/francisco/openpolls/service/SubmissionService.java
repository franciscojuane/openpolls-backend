package com.francisco.openpolls.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.francisco.openpolls.dto.SubmissionAnswerRequest;
import com.francisco.openpolls.dto.SubmissionRequest;
import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.model.Submission;
import com.francisco.openpolls.model.SubmissionAnswer;
import com.francisco.openpolls.repository.SubmissionRepository;
import com.francisco.openpolls.repository.aggregated.AnswerCount;

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
		submission.setEmailAddress(submissionRequest.getEmailAddress());
		submission.setPoll(pollService.getReferenceById(submissionRequest.getPollId()));
		submission = submissionRepository.save(submission);

		for (SubmissionAnswerRequest submissionAnswerRequest : submissionRequest.getSubmissionAnswers()) {
			Question question = questionService.getReferenceById(submissionAnswerRequest.getQuestionId());
			String answer = submissionAnswerRequest.getAnswer();
			SubmissionAnswer submissionAnswer = SubmissionAnswer.builder().question(question).answer(answer)
					.submission(submission).build();
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

	public Page<Submission> findByPollIdOrderById(Long pollId, Pageable pageable) {
		// TODO Auto-generated method stub
		return submissionRepository.findByPollIdOrderById(pollId, pageable);
	}
	
	public Page<AnswerCount> generateAnswerCountByPollIdAndQuestionId(Long pollId, Long questionId, Pageable pageable) {
		return submissionRepository.getAnswerCountByPollIdAndQuestionId(pollId, questionId, pageable);
	}

	public Page<String> generateAnswersByPollIdAndQuestionId(Long pollId, Long questionId, Pageable pageable) {
		return submissionRepository.getAnswersByPollIdAndQuestionId(pollId, questionId, pageable);
	}
	
	public Long getAmountOfSubmissionsByPollIdAndEmailAddress(Long pollId, String emailAddress) {
		return submissionRepository.getAmountOfSubmissionsByPollIdAndEmailAddress(pollId, emailAddress);
	}
	
	public Long getAmountOfSubmissionsByPollIdAndIpAddress(Long pollId, String emailAddress) {
		return submissionRepository.getAmountOfSubmissionsByPollIdAndIpAddress(pollId, emailAddress);
	}

}
