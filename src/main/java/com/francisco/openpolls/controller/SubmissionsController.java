package com.francisco.openpolls.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.francisco.openpolls.dto.SubmissionAnswerResponse;
import com.francisco.openpolls.dto.SubmissionRequest;
import com.francisco.openpolls.dto.SubmissionResponse;
import com.francisco.openpolls.model.Submission;
import com.francisco.openpolls.model.SubmissionAnswer;
import com.francisco.openpolls.service.SubmissionService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/polls/{pollId}/submissions")
public class SubmissionsController {

	@Autowired
	SubmissionService submissionService;
	
	@PostMapping("")
	public ResponseEntity<?> submit(@RequestBody SubmissionRequest submissionRequest, @PathVariable Long pollId, HttpServletRequest httpServletRequest){
		submissionRequest.setPollId(pollId);
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
	
	
	@GetMapping("/table")
	public ResponseEntity<?> generateTable(@PathVariable Long pollId, Pageable pageable){
		Map<Submission, List<SubmissionAnswer>> submissionsTable = submissionService.generateSubmissionsMapForPollId(pollId, pageable);
		Map<SubmissionResponse, List<SubmissionAnswerResponse>> submissionTableResponse = new HashMap<>();
		submissionsTable.forEach((submission, submissionAnswers) -> {
			submissionTableResponse.put(toSubmissionResponse(submission), toSubmissionAnswerResponseList(submissionAnswers));	
		});
		return ResponseEntity.ok(submissionTableResponse);
	}
	
	private SubmissionResponse toSubmissionResponse(Submission submission) {
		SubmissionResponse submissionResponse = SubmissionResponse.builder().emailAddress(submission.getEmailAddress()).ipAddress(submission.getIpAddress()).pollId(submission.getPoll().getId()).build();
		submissionResponse.setId(submission.getId());
		return submissionResponse;
	}
	
	
	private List<SubmissionAnswerResponse> toSubmissionAnswerResponseList(List<SubmissionAnswer> list) {
		List<SubmissionAnswerResponse> submissionAnswerResponses = list.stream().map(elem -> {
			SubmissionAnswerResponse submissionAnswerResponse = new SubmissionAnswerResponse();
			submissionAnswerResponse.setQuestionId(elem.getQuestion().getId());
			submissionAnswerResponse.setAnswer(elem.getAnswer());
			return submissionAnswerResponse;
		}).toList();
		
		return submissionAnswerResponses;
	}
	
	
}
