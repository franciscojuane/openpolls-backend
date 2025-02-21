package com.francisco.openpolls.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.francisco.openpolls.model.Submission;
import com.francisco.openpolls.repository.aggregated.AnswerCount;

public interface SubmissionRepository extends JpaRepository<Submission, Long>{
	
	void deleteByPollId(Long id);
	
	Page<Submission> findByPollId(Long pollId, Pageable pageable);

	Page<Submission> findByPollIdOrderById(Long pollId, Pageable pageable);
	
	@Query(value = "SELECT sa.answer AS answer, COUNT(sa.id) AS count FROM SubmissionAnswer sa where sa.submission.poll.id = :pollId and sa.question.id = :questionId GROUP BY sa.answer")
	Page<AnswerCount> getAnswerCountByPollIdAndQuestionId(@Param("pollId") Long pollId, @Param("questionId") Long questionId, Pageable pageable);

	@Query(value = "SELECT sa.answer AS answer FROM SubmissionAnswer sa where sa.submission.poll.id = :pollId and sa.question.id = :questionId")
	Page<String> getAnswersByPollIdAndQuestionId(@Param("pollId") Long pollId, @Param("questionId") Long questionId, Pageable pageable);
	
	
}
