package com.francisco.openpolls.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.francisco.openpolls.dto.SubmissionRequest;
import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.repository.QuestionRepository;

@Service
public class SubmissionRequestValidationService {

	@Autowired
	QuestionRepository questionRepository;
	
	
	public void validate(SubmissionRequest submissionRequest) {
		checkAllQuestionsAreAnswered(submissionRequest);
	}

	private void checkAllQuestionsAreAnswered(SubmissionRequest submissionRequest) {
		List<Question> questions = questionRepository.findByPollId(submissionRequest.getPollId());
		for(Question question: questions) {
			int amountOfAnswersForQuestion = submissionRequest.getSubmissionAnswers().stream().filter(elem -> elem.getQuestionId() == question.getId()).toList().size();
			if (amountOfAnswersForQuestion == 0) {
				throw new RuntimeException("No answer found for question with id: " + question.getId());
			}
			if (amountOfAnswersForQuestion > 1) {
				throw new RuntimeException("More than one answer found for question with id: " + question.getId());
			}
		}
	}
	
	
}
