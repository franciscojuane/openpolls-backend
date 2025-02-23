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
import com.francisco.openpolls.dto.mappers.PollMapper;
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
	
	@Autowired
	PollMapper pollMapper;

	@GetMapping("")
	public ResponseEntity<?> getPolls(Pageable pageable) {
		Page<Poll> pollPage = pollService.findAll(pageable);
		Page<PollResponse> pollResponsePage = pollPage.map(new Function<Poll, PollResponse>() {
			@Override
			public PollResponse apply(Poll poll) {
				return pollMapper.pollToPollResponse(poll);
			}
		});
		return ResponseEntity.ok(pollResponsePage);
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<?> getPollById(@PathVariable Long id) {
		Poll poll = pollService.findById(id);
		PollResponse pollResponse = pollMapper.pollToPollResponse(poll);
		return ResponseEntity.ok(pollResponse);
	}

	@PostMapping("")
	@Transactional
	public ResponseEntity<?> createPoll(@SelectedUser User user, @RequestBody PollCreateRequest pollCreateRequest) {
		Poll poll = pollMapper.pollRequestToPoll(pollCreateRequest);
		poll.setCreatedByUser(user);
		poll = pollService.save(poll);
		return ResponseEntity.ok(pollMapper.pollToPollResponse(poll));
	}
	
	@PatchMapping("/{pollId}")
	public ResponseEntity<?> patchPoll(@RequestBody PollCreateRequest pollCreateRequest, @PathVariable Long pollId) {
		Poll updatedPoll = pollService.update(pollMapper.pollRequestToPoll(pollCreateRequest));
		return ResponseEntity.ok(pollMapper.pollToPollResponse(updatedPoll));
	}
	
	@DeleteMapping("/{pollId}")
	public ResponseEntity<?> deletePoll(@PathVariable Long pollId){
		pollService.deleteById(pollId);
		return ResponseEntity.ok().build();
	}
	


}
