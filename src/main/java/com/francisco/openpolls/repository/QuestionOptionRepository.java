package com.francisco.openpolls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.QuestionOption;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {

}
