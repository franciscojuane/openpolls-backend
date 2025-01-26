package com.francisco.openpolls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.questionTypes.NumericQuestion;

public interface NumericQuestionRepository extends JpaRepository<NumericQuestion, Long> {

}
