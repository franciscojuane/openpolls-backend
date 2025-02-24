package com.francisco.openpolls.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.francisco.openpolls.model.QuestionOption;
import com.francisco.openpolls.repository.QuestionOptionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class QuestionOptionService {

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    @PreAuthorize("hasAuthority('POLL_READ')")
    public Page<QuestionOption> findAll(Pageable pageable) {
        return questionOptionRepository.findAll(pageable);
    }

    @PreAuthorize("hasAuthority('POLL_READ')")
    public QuestionOption findById(Long id) {
        return questionOptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("QuestionOption not found with id: " + id));
    }

    @Transactional
    @PreAuthorize("hasAuthority('POLL_UPDATE')")
    public QuestionOption save(QuestionOption questionOption) {
        return questionOptionRepository.save(questionOption);
    }

    @Transactional
    @PreAuthorize("hasAuthority('POLL_UPDATE')")
    public QuestionOption update(QuestionOption updatedQuestionOption) {
        QuestionOption existingOption = questionOptionRepository.findById(updatedQuestionOption.getId())
                .orElseThrow(() -> new EntityNotFoundException("QuestionOption not found for update"));

        existingOption.setText(updatedQuestionOption.getText());
        existingOption.setQuestion(updatedQuestionOption.getQuestion());

        return questionOptionRepository.save(existingOption);
    }

    @Transactional
    @PreAuthorize("hasAuthority('POLL_UPDATE')")
    public void deleteById(Long id) {
        if (!questionOptionRepository.existsById(id)) {
            throw new EntityNotFoundException("QuestionOption not found for id " + id);
        }
        questionOptionRepository.deleteById(id);
    }
}
