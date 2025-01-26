package com.francisco.openpolls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.questionTypes.ScaleQuestion;

public interface ScaleQuestionRepository extends JpaRepository<ScaleQuestion, Long> {

}
