package com.francisco.openpolls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

}
