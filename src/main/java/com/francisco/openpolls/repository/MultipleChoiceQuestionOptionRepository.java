package com.francisco.openpolls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.questionTypes.options.MultipleChoiceQuestionOption;

public interface MultipleChoiceQuestionOptionRepository extends JpaRepository<MultipleChoiceQuestionOption, Long> {

}
