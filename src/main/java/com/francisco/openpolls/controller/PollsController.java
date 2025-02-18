package com.francisco.openpolls.controller;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.francisco.openpolls.dto.PollCreateRequest;
import com.francisco.openpolls.dto.PollResponse;
import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.model.User;
import com.francisco.openpolls.service.PollService;
import com.francisco.openpolls.service.QuestionService;
import com.francisco.openpolls.utils.resolver.SelectedUser;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/polls")
public class PollsController {

	@Autowired
	PollService pollService;
	
	@Autowired 
	QuestionService questionService;

	@GetMapping("")
	public ResponseEntity<?> getPolls(Pageable pageable) {
		Page<Poll> pollPage = pollService.findAll(pageable);
		Page<PollResponse> pollResponsePage = pollPage.map(new Function<Poll, PollResponse>() {
			@Override
			public PollResponse apply(Poll poll) {
				return pollToPollResponse(poll);
			}
		});
		return ResponseEntity.ok(pollResponsePage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPollById(@PathVariable Long id) {
		Poll poll = pollService.findById(id);
		PollResponse pollResponse = pollToPollResponse(poll);
		return ResponseEntity.ok(pollResponse);
	}

	@PostMapping("")
	@Transactional
	public ResponseEntity<?> createPoll(@SelectedUser User user, @RequestBody PollCreateRequest pollCreateRequest) {
		Poll poll = Poll.builder().name(pollCreateRequest.getName()).description(pollCreateRequest.getDescription())
				.createdByUser(user)
				.build();
		poll.setEffectiveDate(pollCreateRequest.effectiveDate);
		poll.setExpirationDate(pollCreateRequest.expirationDate);
		
		poll = pollService.save(poll);
		return ResponseEntity.ok(pollToPollResponse(poll));
	}
	
	@PatchMapping("/{pollId}")
	public ResponseEntity<?> patchPoll(@RequestBody PollCreateRequest pollCreateRequest, @PathVariable Long pollId) {
		Poll updatedPoll = pollService.update(requestToPoll(pollCreateRequest));
		return ResponseEntity.ok(pollToPollResponse(updatedPoll));
	}
	
	@DeleteMapping("/{pollId}")
	public ResponseEntity<?> deletePoll(@PathVariable Long pollId){
		pollService.deleteById(pollId);
		return ResponseEntity.ok().build();
	}
	

	private PollResponse pollToPollResponse(Poll poll) {
		PollResponse pollResponse = new PollResponse();
		pollResponse.setCreatedByUserId(poll.getCreatedByUser().getId());
		pollResponse.setName(poll.getName());
		pollResponse.setDescription(poll.getDescription());
		pollResponse.setAmountOfQuestions(questionService.amountOfQuestionsForPoll(poll));
		pollResponse.setEffectiveDate(poll.getEffectiveDate());
		pollResponse.setExpirationDate(poll.getExpirationDate());
		pollResponse.setId(poll.getId());
		return pollResponse;
	}
	
	private Poll requestToPoll(PollCreateRequest pollResponse) {
	    Poll poll = Poll.builder()
	        .name(pollResponse.getName())
	        .description(pollResponse.getDescription())
	        .build();
	    
	    poll.setId(pollResponse.getId());
	    poll.setEffectiveDate(pollResponse.getEffectiveDate());
        poll.setExpirationDate(pollResponse.getExpirationDate());
        
        return poll;
	}

}
