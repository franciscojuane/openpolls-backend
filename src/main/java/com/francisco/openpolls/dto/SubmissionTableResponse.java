package com.francisco.openpolls.dto;

import java.util.ArrayList;
import java.util.List;

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

	public void addRow(Submission submission, List<SubmissionAnswer> submissionAnswers) {
		SubmissionTableResponseRow submissionTableResponseRow = new SubmissionTableResponseRow();
		submissionTableResponseRow.setIpAddress(submission.getIpAddress());
		submissionTableResponseRow.setEmailAddress(submission.getEmailAddress());
		submissionTableResponseRow.setPollId(submission.getPoll().getId());

		submissionTableResponseRow.setData(submissionAnswers.stream().map(elem -> {
			return SubmissionAnswerResponse.builder().answer(elem.getAnswer()).questionId(elem.getQuestion().getId()).build();
		}).toList());
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