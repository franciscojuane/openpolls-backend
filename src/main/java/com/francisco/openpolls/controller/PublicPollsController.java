package com.francisco.openpolls.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.francisco.openpolls.dto.PollResponse;
import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.service.PollService;
import com.francisco.openpolls.service.QuestionService;

@Controller
@RequestMapping("/public/polls")
public class PublicPollsController {
	
	@Autowired
	PollService pollService;
	
	@Autowired
	QuestionService questionService;
	
	
	@GetMapping("/{pollKey}")
	public ResponseEntity<?> getPublicPoll(@PathVariable String pollKey){
		Poll poll = pollService.findByPollKey(pollKey);
		PollResponse response = pollToPollResponse(poll);
		return ResponseEntity.ok(response);
	}

	private PollResponse pollToPollResponse(Poll poll) {
		PollResponse pollResponse = new PollResponse();
		pollResponse.setCreatedByUserId(poll.getCreatedByUser().getId());
		pollResponse.setName(poll.getName());
		pollResponse.setPollKey(poll.getPollKey());
		pollResponse.setDescription(poll.getDescription());
		pollResponse.setAmountOfQuestions(questionService.amountOfQuestionsForPoll(poll));
		pollResponse.setEffectiveDate(poll.getEffectiveDate());
		pollResponse.setExpirationDate(poll.getExpirationDate());
		pollResponse.setId(poll.getId());
		return pollResponse;
	}

}
