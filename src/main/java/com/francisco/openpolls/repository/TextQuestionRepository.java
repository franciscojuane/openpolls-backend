package com.francisco.openpolls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.questionTypes.TextQuestion;

public interface TextQuestionRepository extends JpaRepository<TextQuestion, Long> {

}
