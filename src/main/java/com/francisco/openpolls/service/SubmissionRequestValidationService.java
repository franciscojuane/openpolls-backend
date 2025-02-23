package com.francisco.openpolls.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.francisco.openpolls.dto.SubmissionRequest;
import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.model.enums.QuestionType;
import com.francisco.openpolls.repository.QuestionRepository;

@Service
public class SubmissionRequestValidationService {

	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	@Lazy
	PollService pollService;
	
	@Autowired
	@Lazy
	SubmissionService submissionService;
	
	
	public void validate(SubmissionRequest submissionRequest) {
		checkQuestionsAmountOfAnswers(submissionRequest);
		checkSubmissionLimits(submissionRequest);
	}

	private void checkSubmissionLimits(SubmissionRequest submissionRequest) {
		Poll poll = pollService.findById(submissionRequest.getPollId());
		switch (poll.getSubmissionLimitCriteria()) {
		case EMAIL:
			if (submissionService.getAmountOfSubmissionsByPollIdAndEmailAddress(poll.getId(), submissionRequest.getEmailAddress()) > 0) {
				throw new RuntimeException("Email aldready answered this poll.");
			}
			break;
		case IP:
			if (submissionService.getAmountOfSubmissionsByPollIdAndIpAddress(poll.getId(), submissionRequest.getIpAddress()) > 0) {
				throw new RuntimeException("IP aldready answered this poll.");
			}
			break;
		}
	}

	private void checkQuestionsAmountOfAnswers(SubmissionRequest submissionRequest) {
		List<Question> questions = questionRepository.findByPollId(submissionRequest.getPollId());
		for(Question question: questions) {
			int amountOfAnswersForQuestion = submissionRequest.getSubmissionAnswers().stream().filter(elem -> elem.getQuestionId() == question.getId()).toList().size();
			if (canHaveMoreThanOneAnswer(question)) {
				if (amountOfAnswersForQuestion < question.getMinAmountOfSelections() || amountOfAnswersForQuestion>question.getMaxAmountOfSelections()) {
					throw new RuntimeException("Illegal amount of answers for multiple choice question with id: " + question.getId());
				}
			} else {
			if (amountOfAnswersForQuestion == 0) {
				throw new RuntimeException("No answer found for question with id: " + question.getId());
			}
			if (amountOfAnswersForQuestion > 1) {
				throw new RuntimeException("More than one answer found for question with id: " + question.getId());
			}
			}
		}
	}
	
	private boolean canHaveMoreThanOneAnswer(Question question) {
		return QuestionType.MULTIPLE_CHOICE.equals(question.getQuestionType()) && question.getMaxAmountOfSelections() > 1;
	}
	
	
}
