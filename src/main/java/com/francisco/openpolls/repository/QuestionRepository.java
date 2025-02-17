package com.francisco.openpolls.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

	Long countByPoll(Poll poll);
	
	List<Poll> findByPoll(Poll poll);

	Page<Question> findByPollId(Long pollId, Pageable pageable);
	
}
