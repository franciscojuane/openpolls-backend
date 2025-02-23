package com.francisco.openpolls.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.model.Submission;
import com.francisco.openpolls.model.SubmissionAnswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionTableResponse {
	
	@Default
	List<SubmissionTableResponseRow> rows = new ArrayList<>();

	public void addRow(Submission submission, List<SubmissionAnswer> submissionAnswers, List<Question> questions) {
		SubmissionTableResponseRow submissionTableResponseRow = new SubmissionTableResponseRow();
		submissionTableResponseRow.setIpAddress(submission.getIpAddress());
		submissionTableResponseRow.setEmailAddress(submission.getEmailAddress());
		submissionTableResponseRow.setPollId(submission.getPoll().getId());

		List<SubmissionAnswerResponse> data = new ArrayList<>();
		for(Question question : questions) {
			 List<SubmissionAnswer> submissionAnswersInt = submissionAnswers.stream().filter(elem -> elem.getQuestion().getId() == question.getId()).toList();
			 String answer = submissionAnswersInt.stream().map(elem -> elem.getAnswer().toString()).collect(Collectors.joining(","));
			 data.add(SubmissionAnswerResponse.builder().answer(answer).questionId(question.getId()).build());
		}
		
		submissionTableResponseRow.setData(data);
		rows.add(submissionTableResponseRow);
	}

}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class SubmissionTableResponseRow {

	private String ipAddress;

	private String emailAddress;

	private Long pollId;

	public List<SubmissionAnswerResponse> data;

}