package com.francisco.openpolls.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long>{
	
	void deleteByPollId(Long id);
	
	Page<Submission> findByPollId(Long pollId, Pageable pageable);

	Page<Submission> findByPollIdOrderById(Long pollId, Pageable pageable);
	
}
