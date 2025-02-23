package com.francisco.openpolls.dto.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.francisco.openpolls.dto.PollCreateRequest;
import com.francisco.openpolls.dto.PollResponse;
import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.service.QuestionService;

@Component
public class PollMapper {

	@Autowired
	QuestionService questionService;
	
	public PollResponse pollToPollResponse(Poll poll) {
		PollResponse pollResponse = new PollResponse();
		pollResponse.setCreatedByUserId(poll.getCreatedByUser().getId());
		pollResponse.setName(poll.getName());
		pollResponse.setPollKey(poll.getPollKey());
		pollResponse.setDescription(poll.getDescription());
		pollResponse.setAmountOfQuestions(questionService.amountOfQuestionsForPoll(poll));
		pollResponse.setEffectiveDate(poll.getEffectiveDate());
		pollResponse.setExpirationDate(poll.getExpirationDate());
		pollResponse.setId(poll.getId());
		pollResponse.setSubmissionLimitCriteria(poll.getSubmissionLimitCriteria());
		return pollResponse;
	}
	
	public Poll pollRequestToPoll(PollCreateRequest pollResponse) {
	    Poll poll = Poll.builder()
	        .name(pollResponse.getName())
	        .description(pollResponse.getDescription())
	        .build();
	    
	    poll.setId(pollResponse.getId());
	    poll.setEffectiveDate(pollResponse.getEffectiveDate());
        poll.setExpirationDate(pollResponse.getExpirationDate());
        poll.setSubmissionLimitCriteria(pollResponse.getSubmissionLimitCriteria());
        return poll;
	}
}
