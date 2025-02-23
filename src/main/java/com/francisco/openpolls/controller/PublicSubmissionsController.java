package com.francisco.openpolls.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.francisco.openpolls.dto.SubmissionRequest;
import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.service.PollService;
import com.francisco.openpolls.service.SubmissionAnswerService;
import com.francisco.openpolls.service.SubmissionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;

@Controller
@RequestMapping("/public/polls/{pollKey}/submissions")
public class PublicSubmissionsController {

	@Autowired
	SubmissionService submissionService;
	
	@Autowired
	SubmissionAnswerService submissionAnswerService;
	
	@Autowired
	PollService pollService;
	
	@PostMapping("")
	public ResponseEntity<?> submit(@RequestBody SubmissionRequest submissionRequest, @PathVariable String pollKey, HttpServletRequest httpServletRequest){
		Poll poll = pollService.findByPollKey(pollKey);
		if (poll == null) 
			throw new ValidationException("Poll not found for provided key.");
		submissionRequest.setPollId(poll.getId());
		submissionRequest.setIpAddress(getClientIp(httpServletRequest));
		submissionService.submit(submissionRequest);
		return ResponseEntity.ok().build();
	}
	
	
	public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	
}
