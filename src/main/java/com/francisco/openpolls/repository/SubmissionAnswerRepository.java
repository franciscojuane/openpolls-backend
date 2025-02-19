package com.francisco.openpolls.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.SubmissionAnswer;

public interface SubmissionAnswerRepository extends JpaRepository<SubmissionAnswer, Long>{

	void deleteBySubmissionId(Long id);
	
	void deleteByQuestionId(Long questionId);
	
	void deleteByQuestion_PollId(Long pollId);

	List<SubmissionAnswer> findBySubmissionId(Long submissionId);

	List<SubmissionAnswer> findBySubmissionIdOrderById(Long submissionId);
}
