package com.francisco.openpolls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.Poll;

public interface PollRepository extends JpaRepository<Poll, Long> {

	Poll findByPollKey(String pollKey);

}
