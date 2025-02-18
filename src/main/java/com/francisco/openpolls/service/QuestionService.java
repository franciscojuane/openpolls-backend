package com.francisco.openpolls.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.model.QuestionOption;
import com.francisco.openpolls.repository.QuestionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class QuestionService {

	@Autowired
    private QuestionRepository questionRepository;
	
	@Autowired
	private SubmissionService submissionService;

    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + id));
    }

    @Transactional
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Transactional
    public Question update(Question updatedQuestion) {
        Question existingQuestion = questionRepository.findById(updatedQuestion.getId())
                .orElseThrow(() -> new EntityNotFoundException("Question not found for update with id: " + updatedQuestion.getId()));

        existingQuestion.setRank(updatedQuestion.getRank());
        existingQuestion.setText(updatedQuestion.getText());
        existingQuestion.setSubText(updatedQuestion.getSubText());
        existingQuestion.setQuestionType(updatedQuestion.getQuestionType());
        existingQuestion.setMinAmountOfSelections(updatedQuestion.getMinAmountOfSelections());
        existingQuestion.setMaxAmountOfSelections(updatedQuestion.getMaxAmountOfSelections());
        List<QuestionOption> options = existingQuestion.getOptions();
        options.clear();
        for (QuestionOption option : updatedQuestion.getOptions()) {
        	options.add(option);
        }
        existingQuestion.setOptions(options);
        existingQuestion.setMinValue(updatedQuestion.getMinValue());
        existingQuestion.setMaxValue(updatedQuestion.getMaxValue());
        existingQuestion.setScale(updatedQuestion.getScale());
        existingQuestion.setMinLength(updatedQuestion.getMinLength());
        existingQuestion.setMaxLength(updatedQuestion.getMaxLength());

        return questionRepository.save(existingQuestion);
    }

    @Transactional
    public void deleteById(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new EntityNotFoundException("Question not found for id " + questionId);
        }
    	submissionService.deleteSubmissionsByQuestionId(questionId);
        questionRepository.deleteById(questionId);
    }
    
    
    public Long amountOfQuestionsForPoll(Poll poll) {
    	return questionRepository.countByPoll(poll);
    }

	public List<Question> findByPollId(Long pollId) {
		return questionRepository.findByPollId(pollId);
	}

	@Transactional
	public void deleteByPollId(Long pollId) {
		submissionService.deleteSubmissionsByPollId(pollId);
		questionRepository.deleteByPollId(pollId);
		
	}
}
