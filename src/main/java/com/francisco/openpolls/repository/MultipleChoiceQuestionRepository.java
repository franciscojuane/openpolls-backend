package com.francisco.openpolls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.questionTypes.MultipleChoiceQuestion;

public interface MultipleChoiceQuestionRepository extends JpaRepository<MultipleChoiceQuestion, Long> {

}
