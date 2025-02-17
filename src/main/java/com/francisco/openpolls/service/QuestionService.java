package com.francisco.openpolls.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.repository.QuestionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class QuestionService {

	@Autowired
    private QuestionRepository questionRepository;

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
        existingQuestion.setPoll(updatedQuestion.getPoll());
        existingQuestion.setMinAmountOfSelections(updatedQuestion.getMinAmountOfSelections());
        existingQuestion.setMaxAmountOfSelections(updatedQuestion.getMaxAmountOfSelections());
        existingQuestion.setOptions(updatedQuestion.getOptions());
        existingQuestion.setMinValue(updatedQuestion.getMinValue());
        existingQuestion.setMaxValue(updatedQuestion.getMaxValue());
        existingQuestion.setScale(updatedQuestion.getScale());
        existingQuestion.setMinLength(updatedQuestion.getMinLength());
        existingQuestion.setMaxLength(updatedQuestion.getMaxLength());

        return questionRepository.save(existingQuestion);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new EntityNotFoundException("Question not found for id " + id);
        }
        questionRepository.deleteById(id);
    }
    
    
    public Long amountOfQuestionsForPoll(Poll poll) {
    	return questionRepository.countByPoll(poll);
    }

	public Page<Question> findByPollId(Long pollId, Pageable pageable) {
		return questionRepository.findByPollId(pollId, pageable);
	}
}
