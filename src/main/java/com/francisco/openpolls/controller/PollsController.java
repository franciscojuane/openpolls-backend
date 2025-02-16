package com.francisco.openpolls.controller;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.francisco.openpolls.dto.PollResponse;
import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.repository.PollRepository;

@Controller
@RequestMapping("/polls")
public class PollsController {

	@Autowired
	PollRepository pollRepository;
	
	@GetMapping("/")
	public ResponseEntity<?> getPolls(Pageable pageable){
		Page<Poll> pollPage = pollRepository.findAll(pageable);
		Page<PollResponse> pollResponsePage = pollPage.map(new Function<Poll, PollResponse>() {

			@Override
			public PollResponse apply(Poll poll) {
				// TODO Auto-generated method stub
				PollResponse pollResponse = new PollResponse();
				pollResponse.setCreatedByUserId(poll.getCreatedByUser().getId());
				pollResponse.setName(poll.getName());
				pollResponse.setDescription(poll.getDescription());
				return pollResponse;
			}
			
		});
		
		return ResponseEntity.ok(pollResponsePage);
	}
}
